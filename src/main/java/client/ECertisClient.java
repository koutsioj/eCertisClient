package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ECertisClientInterface;
import model.Criterion;
import model.EvidenceType;
import javax.annotation.Nullable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Contains methods that represent eCertis API calls.
 * They all return a json String that contains information about Criteria and/or Evidences.
 */
@SuppressWarnings("unused")
public class ECertisClient implements ECertisClientInterface {
    
    /**
     * Creates and sends a GET request and deserializes the data from the request to an object.
     * Then serializes the new object and returns it as String.
     * Method used by the other methods of the class.
     *
     * @param uri                  The URI of the request.
     * @param deserializationValue instance of the class used for deserialization
     * @return String
     */
    private static String jsonMarshaller(String uri, Object deserializationValue) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .timeout(Duration.ofSeconds(60))
                .build();

        //"output" variable value is a json string of all the information we got from the request (criteria, values etc)
        String output = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                //.thenAccept(System.out::println)
                .join();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            //mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);

            deserializationValue = mapper.readValue(output, deserializationValue.getClass());

            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(deserializationValue);

            //mapper.writeValue(new File("criteria.json"), deserializationValue); //creates new file

        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println("URI: "+uri+"\n");
        return json;
    }


    /**
     * It returns a list of all EU criteria that are not parent criteria.
     * Parameters can filter the list.
     * acceptance environment does not allow simultaneous empty 'nationalEntity' and 'type' values.
     *
     * @param nationalEntity to retrieve national criteria specifying the ID of the country.
     * @param type           to specify criterion type
     * @param updateDate     to specify the oldest point in time in which a criterion has been updated.
     * @param lang           to specify the language. Default is English.
     * @return String
     */

    @Override
    public String criteriaCall(String uri, String nationalEntity, String type, @Nullable LocalDate updateDate, @Nullable String lang) {

        if (nationalEntity == null || type == null) {
            throw new NullPointerException("'nationalEntity' and 'type' cannot be null.");
        }

        String updateDateFormatted;
        if (updateDate != null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            updateDateFormatted = updateDate.format(format);
        } else //for convenience a null 'LocalDate' parameter becomes an empty String when formatting
        {
            updateDateFormatted = "";
        }
       uri = uri + "?nationalEntity=" + nationalEntity + "&type=" + type + "&updateDate=" + updateDateFormatted + "&lang=" + lang;

        Criterion criteriaList = new Criterion();
        return jsonMarshaller(uri, criteriaList);
    }

    /**
     * It returns a list of all EU criteria that are not parent criteria.
     * Parameters can filter the list.
     * Acceptance environment does not allow simultaneous empty 'scenarioId' and 'domainId' values.
     *
     * @param scenarioId to specify a scenario.
     * @param domainId   to specify a domain.
     * @return String
     */
    @Override
    public String criteriaCall(String uri, String scenarioId, String domainId) {

        if (scenarioId == null || scenarioId.isBlank() || domainId == null) //empty strings are fine
        {
            throw new IllegalArgumentException("'scenarioId' can not be null or blank and 'domainId' can not be null");
        }
        //String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria?scenarioId=" + scenarioId + "&domainId=" + domainId;
        uri = uri + "?scenarioId=" + scenarioId + "&domainId=" + domainId;

        Criterion criteriaList = new Criterion();
        return jsonMarshaller(uri, criteriaList);
    }

    /**
     * Returns a specific version of a criterion, with all sub criterion linked to it and for each one returns all evidences that fulfill the criterion and all laws for all countries.
     *
     * @param id            specify the id of a criterion
     * @param version       to specify version. Defaults to first version.
     * @param lang          to specify the language. Default is English.
     * @param countryFilter to specify national criteria.
     * @return String
     */
    @Override
    public String criterionCall(String uri, String id, String version, @Nullable String lang, String countryFilter) {

        if (id == null || id.isBlank() || countryFilter == null || countryFilter.isBlank() || version == null) {
            throw new IllegalArgumentException("'id' and 'countryFilter' can not be null or blank. 'version' can not be null");
        }

        uri = uri + id + "/" + version + "?lang=" + lang + "&countryFilter=" + countryFilter;
        //String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria/" + id + "/" + version + "?lang=" + lang + "&countryFilter=" + countryFilter;

        Criterion criterion = new Criterion();
        return jsonMarshaller(uri, criterion);
    }

    /**
     * Returns a specific version of a criterion, with all the evidences and all laws for all countries.
     * Executes call in the acceptance (testing) environment.
     *
     * @param id             specify the id of a criterion
     * @param version        to specify version. Defaults to first version.
     * @param lang           to specify the language. Default is English.
     * @param nationalEntity to specify national criteria.
     * @return String
     */
    @Override
    public String criterionMdCall(String uri,String id, String version, @Nullable String lang, String nationalEntity) {

        if (id == null || id.isBlank() || version == null || nationalEntity == null) {
            throw new IllegalArgumentException("'id' can not be null or blank. 'version' and 'nationalEntity' can not be null");
        }

        //String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteriaMd/" + id + "/" + version + "?lang=" + lang + "&nationalEntity=" + nationalEntity;
        uri = uri + id + "/" + version + "?lang=" + lang + "&nationalEntity=" + nationalEntity;
        Criterion criterion = new Criterion();
        return jsonMarshaller(uri, criterion);
    }

    /**
     * Returns a specific version of a criterion, with all sub criterion linked to it and for each one returns all evidences that fulfill the criterion and all laws for all countries.
     * Executes call in the acceptance (testing) environment.
     *
     * @param id            specify the id of a criterion
     * @param version       to specify version. Defaults to first version.
     * @param lang          to specify the language. Default is English.
     * @param countryFilter to specify national criteria.
     * @return String
     */
    @Override
    public String criterionESPDCall(String uri, String id, String version, @Nullable String lang, @Nullable String countryFilter) {

        if (id == null || id.isBlank() || version == null) {
            throw new IllegalArgumentException("'id' can not be null or blank. 'version' can not be null");
        }

        //String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria/espd/" + id + "/" + version + "?lang=" + lang + "&countryFilter=" + countryFilter;
        uri = uri + id + "/" + version + "?lang=" + lang + "&countryFilter=" + countryFilter;
        Criterion criterion = new Criterion();
        return jsonMarshaller(uri, criterion);
    }

    /**
     * Without any parameters, it returns a list of all evidences.
     * Parameters can filter the list.
     * Executes call in the acceptance (testing) environment.
     *
     * @param nationalEntity to retrieve national criteria specifying the ID of the country.
     * @param updateDate     to specify the oldest point in time in which a criterion has been updated.
     * @param lang           to specify the language. Default is English.
     * @return String
     */
    @Override
    public String evidencesCall(String uri, String nationalEntity, @Nullable LocalDate updateDate, @Nullable String lang) {

        String updateDateFormatted;
        if (updateDate != null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            updateDateFormatted = updateDate.format(format);
        } else {
            updateDateFormatted = "";
        }

        if (nationalEntity == null || (nationalEntity.isBlank() && updateDateFormatted.isBlank())) //empty strings are fine
        {
            throw new IllegalArgumentException("'nationalEntity' cannot be null. Also either 'nationalEntity' or 'updateDate' need a non blank value.");
        }

        //String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/evidences?nationalEntity=" + nationalEntity + "&updateDate=" + updateDateFormatted + "&lang=" + lang;
        uri = uri + "?nationalEntity=" + nationalEntity + "&updateDate=" + updateDateFormatted + "&lang=" + lang;

        ArrayList<EvidenceType> evidenceList = new ArrayList<>();
        return jsonMarshaller(uri, evidenceList);
    }

    /**
     * Provides a specific version of an evidence.
     * Executes call in the acceptance (testing) environment.
     *
     * @param id      specify the id of an evidence
     * @param version to specify version. Defaults to first version.
     * @param lang    to specify the language. Default is English.
     * @return String
     */
    @Override
    public String evidenceCall(String uri,String id, String version, @Nullable String lang) {

        if (id == null || id.isBlank() || version == null) {
            throw new IllegalArgumentException("'id' cannot be null or blank and 'version' cannot be null.");
        }

        //String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/evidences/" + id + "/" + version + "?lang=" + lang;
        uri = uri + id + "/" + version + "?lang=" + lang;

        EvidenceType evidenceList = new EvidenceType();
        return jsonMarshaller(uri, evidenceList);
    }
}

