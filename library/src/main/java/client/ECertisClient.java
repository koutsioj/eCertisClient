package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.koutsioj.CacheHelper;
import model.Criterion;
import model.EvidenceType;
import org.ehcache.Cache;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Contains methods that represent eCertis API calls.
 * They all return a json String that contains information about Criteria and/or Evidences.
 * Caching can be enabled by using the boolean constructor of the class, with a "true" parameter value
 */
@SuppressWarnings("unused")
public class ECertisClient implements IECertisClient {

    public ECertisClient() {
        enableLogging();
    }

    private int heapEntries = 10;
    private int expirationTimeMinutes = 360;
    public ECertisClient(boolean cacheEnabled) {
        enableLogging();
        CacheHelper cacheHelper = new CacheHelper();
        if (cacheEnabled == true) {
            cache = cacheHelper.createHeapCache("eCertisCache", heapEntries, expirationTimeMinutes);
        }
    }

    private static Logger logger = Logger.getLogger(ECertisClient.class.getName());
    private static Cache<String,String> cache;
    private static LogManager logManager;

    private static void enableLogging() {
        File logProperties = new File("logging.properties");
        File logFile = new File("logFile.log");

        //creates the logging.properties file if it doesn't exist and writes the configuration for logFile inside it.
        if (!logProperties.exists()) {
            try {
                logProperties.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Could not create new log file\n" +e);
            }

            FileWriter fileWriter;
            String content = "handlers=java.util.logging.FileHandler\n" +
                    ".level=SEVERE\n" +
                    ".level=INFO\n" +
                    "java.util.logging.FileHandler.pattern=logFile.log\n" +
                    "java.util.logging.FileHandler.limit=50000\n" +
                    "java.util.logging.FileHandler.count=1\n" +
                    "java.util.logging.FileHandler.append=true\n" +
                    "java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter";

            try {
                fileWriter = new FileWriter(logProperties);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(content);
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not write into file 'logging.properties'\n" +e);
            }
        }

        //creates the logFile.log if it doesn't exist.
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Could not create new log file\n" +e);
            }
        }
        //uses the configuration from logging.properties
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Could not read log file\n" +e);
        }
    }

    /**
     * Creates and sends a GET request and deserializes the data from the request to an object.
     * Then serializes the new object and returns it as String.
     * Method used by the other methods of the class.
     *
     * @param uri                  The URI of the request.
     * @param deserializationValue instance of the class used for deserialization
     * @return json String
     */
    private String jsonMarshaller(String uri, Object deserializationValue) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .timeout(Duration.ofSeconds(60))
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String output = response.thenApply(HttpResponse::body).join();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            deserializationValue = mapper.readValue(output, deserializationValue.getClass());
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(deserializationValue);
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "logging : Deserialization unsuccessful. \n" + e.getMessage() , new Exception("JsonProcessingException"));
        }
        System.out.println("jsonMarshaller is finished, URL is : "+uri);

        if (cache!=null) {
            cache.put(uri, json);
            //System.out.println("CACHING ENABLED");
        }
        return json;
    }

    /**
     * It returns a list of all EU criteria that are not parent criteria.
     * Parameters can filter the list.
     * acceptance environment does not allow simultaneous empty 'nationalEntity' and 'type' values.
     *
     * @param uri            The URI of the request. E.g: https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria
     * @param nationalEntity to retrieve national criteria specifying the ID of the country.
     * @param type           to specify criterion type
     * @param updateDate     to specify the oldest point in time in which a criterion has been updated.
     * @param lang           to specify the language. Default is English.
     * @return json String
     */

    @Override
    public String getCriteria(String uri, String nationalEntity, String type, @Nullable LocalDate updateDate, @Nullable String lang) {

        String updateDateFormatted;
        if (updateDate != null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            updateDateFormatted = updateDate.format(format);
        } else //for convenience a null 'LocalDate' parameter becomes an empty String when formatting
        {
            updateDateFormatted = "";
        }
       uri = uri + "?nationalEntity=" + nationalEntity + "&type=" + type + "&updateDate=" + updateDateFormatted + "&lang=" + lang;

        if (cache != null && cache.containsKey(uri)) {
            return cache.get(uri); //gets the value for that key
        }

        Criterion criteriaList = new Criterion();
        return jsonMarshaller(uri, criteriaList);
    }

    /**
     * It returns a list of all EU criteria that are not parent criteria.
     * Parameters can filter the list.
     *
     * @param uri        The URI of the request. E.g: https://ec.europa.eu/growth/tools-databases/ecertisrest/criteria
     * @param scenarioId to specify a scenario.
     * @param domainId   to specify a domain.
     * @return json String
     */
    @Override
    public String getCriteria(String uri, String scenarioId, String domainId, @Nullable String lang) {

        if (scenarioId == null || scenarioId.isBlank() || domainId == null)
        {
            throw new IllegalArgumentException("'scenarioId' can not be null or blank and 'domainId' can not be null");
        }
        uri = uri + "?scenarioId=" + scenarioId + "&domainId=" + domainId + "&lang=" + lang;

        if (cache != null && cache.containsKey(uri)) {
            return cache.get(uri);
        }

        Criterion criteriaList = new Criterion();
        return jsonMarshaller(uri, criteriaList);
    }

    /** Depending on URI
     * Returns a specific version of a criterion, with all sub criterion linked to it and for each one returns all evidences that fulfill the criterion and all laws for all countries.
     * Returns a specific version of a criterion used by ESPD, with all sub criterion linked to it and for each one returns all evidences that fulfill the criterion and all laws for all countries.
     * @param uri           The URI of the request. E.g: https://ec.europa.eu/growth/tools-databases/ecertisrest/criteria/
     *                                                   https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria/espd/
     * @param id            specify the id of a criterion
     * @param version       to specify version. Defaults to first version.
     * @param lang          to specify the language. Default is English.
     * @param countryFilter to specify national criteria.
     * @return json String
     */
    @Override
    public String getCriterion(String uri, String id, String version, @Nullable String lang, String countryFilter) {

        //Some criteria accept null/empty countryFilter values but not all of them so they are not allowed either.
        if (id == null || id.isBlank() || countryFilter == null || countryFilter.isBlank() || version == null) {
            throw new IllegalArgumentException("'id' and 'countryFilter' can not be null or blank. 'version' can not be null");
        }
        uri = uri + id + "/" + version + "?lang=" + lang + "&countryFilter=" + countryFilter;

        if (cache != null && cache.containsKey(uri)) {
            return cache.get(uri); //gets the value for that key
        }

        Criterion criterion = new Criterion();
        return jsonMarshaller(uri, criterion);
    }

    /**
     * Returns a specific version of a criterion, with all the evidences and all laws for all countries.
     *
     * @param uri           The URI of the request. E.g: https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteriaMd/
     * @param id             specify the id of a criterion
     * @param version        to specify version. Defaults to first version.
     * @param lang           to specify the language. Default is English.
     * @param nationalEntity to specify national criteria.
     * @return json String
     */
    @Override
    public String getCriterionSimplified(String uri, String id, String version, @Nullable String lang, String nationalEntity) {

        if (id == null || id.isBlank() || version == null || nationalEntity == null) {
            throw new IllegalArgumentException("'id' can not be null or blank. 'version' and 'nationalEntity' can not be null");
        }

        uri = uri + id + "/" + version + "?lang=" + lang + "&nationalEntity=" + nationalEntity;

        if (cache != null && cache.containsKey(uri)) {
            return cache.get(uri); //gets the value for that key
        }

        Criterion criterion = new Criterion();
        return jsonMarshaller(uri, criterion);
    }

    /**
     * Without any parameters, it returns a list of all evidences.
     * Parameters can filter the list.
     *
     * @param uri            The URI of the request. E.g: https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/evidences
     * @param nationalEntity to retrieve national criteria specifying the ID of the country.
     * @param updateDate     to specify the oldest point in time in which a criterion has been updated.
     * @param lang           to specify the language. Default is English.
     * @return json String
     */
    @Override
    public String getEvidences(String uri, String nationalEntity, @Nullable LocalDate updateDate, @Nullable String lang) {

        String updateDateFormatted;
        if (updateDate != null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            updateDateFormatted = updateDate.format(format);
        } else {
            updateDateFormatted = "";
        }

        if (nationalEntity == null || (nationalEntity.isBlank() && updateDateFormatted.isBlank()))
        {
            throw new IllegalArgumentException("'nationalEntity' cannot be null. Also either 'nationalEntity' or 'updateDate' need a non blank value.");
        }

        uri = uri + "?nationalEntity=" + nationalEntity + "&updateDate=" + updateDateFormatted + "&lang=" + lang;

        if (cache != null && cache.containsKey(uri)) {
            return cache.get(uri); //gets the value for that key
        }

        ArrayList<EvidenceType> evidenceList = new ArrayList<>();
        return jsonMarshaller(uri, evidenceList);
    }

    /**
     * Provides a specific version of an evidence.
     *
     * @param uri     The URI of the request. E.g: https://ec.europa.eu/growth/tools-databases/ecertisrest/evidences/
     * @param id      specify the id of an evidence
     * @param version to specify version. Defaults to first version.
     * @param lang    to specify the language. Default is English.
     * @return json String
     */
    @Override
    public String getEvidence(String uri, String id, String version, @Nullable String lang) {

        if (id == null || id.isBlank() || version == null) {
            throw new IllegalArgumentException("'id' cannot be null or blank and 'version' cannot be null.");
        }

        uri = uri + id + "/" + version + "?lang=" + lang;

        if (cache != null && cache.containsKey(uri)) {
            return cache.get(uri); //gets the value for that key
        }

        EvidenceType evidenceList = new EvidenceType();
        return jsonMarshaller(uri, evidenceList);
    }
}

