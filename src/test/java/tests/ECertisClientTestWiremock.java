package tests;

import client.ECertisClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import model.Criterion;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.matching.MatchResult;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.requestMadeFor;
import static com.github.tomakehurst.wiremock.client.WireMock.requestMatching;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //It allows usage of BeforeAll and AfterAll
@ExtendWith(WireMockExtension.class)
public class ECertisClientTestWiremock {

    // public WireMockRule wm = new WireMockRule(wireMockConfig().dynamicPort());
    static WireMockServer wm;
    ECertisClient eCertisClient = new ECertisClient();

    @BeforeAll
    void setUp() {
        wm = new WireMockServer(8089);
        wm.start();
        configureFor("localhost", 8089);
    }

    @AfterAll
    static void tearDown() {
        wm.stop();
    }

    @Test
    public void WhenOpeningConnection_ConnectionIsSuccessful_AndResponseCodeIsOK() throws IOException {

        stubFor(get(urlEqualTo("/connectionSuccessful"))
                .willReturn(aResponse().withBody("successful response")));

        URL url = new URL("http://localhost:" + wm.port() + "/connectionSuccessful");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Content-Type", "text/xml");

        assertThat(connection.getResponseCode(), is(200));
        verify(getRequestedFor(urlEqualTo("/connectionSuccessful"))
                .withHeader("Content-Type", equalTo("text/xml")));
    }


    @RepeatedTest(2) // repeats the test x times with different values based on repetitionInfo
    public void getCriteria_WhenScenarioIdAndDomainIdAreCorrect_ReserialisedStubResponseIsEqualToMethodOutput_AndWithoutIndentationStubResponseIsEqualToMethodOutput(RepetitionInfo repetitionInfo) throws IOException {

        String scenarioId = repetitionInfo.getCurrentRepetition() == 1 ? "1100" : "1602";
        String domainId = repetitionInfo.getCurrentRepetition() == 1 ? "1100" : "";

        //saved (raw) response from the eCertis API from postman
        String responseBodyInput = repetitionInfo.getCurrentRepetition() == 1 ?
                "src/test/resources/StubResponseBodyAcceptance/getCriteriaScenarioId.json" : "src/test/resources/StubResponseBodyProduction/getCriteriaScenarioId.json";

        String input = new String(Files.readAllBytes(Paths.get(responseBodyInput)));
        String uri ="http://localhost:" + wm.port() + "/getCriteria";

        stubFor(get(urlEqualTo("/getCriteria?scenarioId="+scenarioId+"&domainId="+domainId))
                .willReturn(aResponse().withBody(input)));

        //saved (pretty printed) response from the eCertis API from postman
        String getCriteriaOutput = eCertisClient.getCriteria(uri,scenarioId,domainId);

        ObjectMapper mapper = new ObjectMapper();
        Criterion APIList = new Criterion();

        //Deserializes and then re-serializes the eCertis API Response with default pretty printing
        APIList = mapper.readValue(input, APIList.getClass());
        String APIReserializedInput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(APIList);

        assertThat(APIReserializedInput, is(getCriteriaOutput)); //checks that the re-serialized input from the file is the same as the output of the getCriteria method
        assertThat(input.replaceAll("\\s", ""), is(getCriteriaOutput.replaceAll("\\s", ""))); //checks that the eCertis API response is the same as the getCriteria output when they have no indentation
        //Both of the above should be true since getCriterion only changes the indentation of the response of the API with deserialization and re-serialization.
    }

    @RepeatedTest(2)
    public void getCriteria_WhenNationalEntityAndOtherParametersAreCorrect_ReserialisedStubResponseIsEqualToMethodOutput_AndWithoutIndentationStubResponseIsEqualToMethodOutput(RepetitionInfo repetitionInfo) throws IOException {

        String nationalEntity = repetitionInfo.getCurrentRepetition() == 1 ? "gr" : "be";
        String type = repetitionInfo.getCurrentRepetition() == 1 ? "" : "3555";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate updateDate = repetitionInfo.getCurrentRepetition() == 1 ? null : LocalDate.parse("15-12-2019",formatter); //null or 2019-12-15 (not 15-12-2019)
        String formattedDate = updateDate == null ? "" : updateDate.format(formatter); // empty or 15-12-2019. Needed for stubUrl

        String lang = repetitionInfo.getCurrentRepetition() == 1 ? "" : "en";

                //saved (raw) response from the eCertis API from postman
        String responseBodyInput = repetitionInfo.getCurrentRepetition() == 1 ?
                "src/test/resources/StubResponseBodyAcceptance/getCriteria.json" : "src/test/resources/StubResponseBodyProduction/getCriteria.json";

        String input = new String(Files.readAllBytes(Paths.get(responseBodyInput)));
        String uri ="http://localhost:" + wm.port() + "/getCriteria";
        String stubUrl = "/getCriteria?nationalEntity="+nationalEntity+"&type="+type+"&updateDate="+formattedDate+"&lang="+lang;
        stubFor(get(urlEqualTo(stubUrl))
                .willReturn(aResponse().withBody(input)));

        //saved (pretty printed) response from the eCertis API from postman
        String getCriteriaOutput = eCertisClient.getCriteria(uri,nationalEntity,type,updateDate,lang);

        ObjectMapper mapper = new ObjectMapper();
        Criterion APIList = new Criterion();

        //Deserializes and then re-serializes the eCertis API Response with default pretty printing
        APIList = mapper.readValue(input, APIList.getClass());
        String APIReserializedInput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(APIList);

        assertThat(APIReserializedInput, is(getCriteriaOutput)); //checks that the re-serialized input from the file is the same as the output of the getCriteria method
        assertThat(input.replaceAll("\\s", ""), is(getCriteriaOutput.replaceAll("\\s", ""))); //checks that the eCertis API response is the same as the getCriteria output when they have no indentation
        //Both of the above should be true since getCriterion only changes the indentation of the response of the API with deserialization and re-serialization.
    }


    @RepeatedTest(2)
    public void getCriterion_WhenIdAndOtherParametersAreCorrect_ReserialisedStubResponseIsEqualToMethodOutput_AndWithoutIndentationStubResponseIsEqualToMethodOutput(RepetitionInfo repetitionInfo) throws IOException {

        String id = repetitionInfo.getCurrentRepetition() == 1 ? "811b3d07-4ef1-47a1-a16c-d973f0e65b1b" : "c27b7c4e-c837-4529-b867-ed55ce639db5";
        String version = repetitionInfo.getCurrentRepetition() == 1 ? "" : "3";
        String lang = repetitionInfo.getCurrentRepetition() == 1 ? "" : "es";
        String countryFilter = repetitionInfo.getCurrentRepetition() == 1 ? "be" : "be";

        //saved (raw) response from the eCertis API from postman
        String responseBodyInput = repetitionInfo.getCurrentRepetition() == 1 ?
                "src/test/resources/StubResponseBodyAcceptance/getCriterion.json" : "src/test/resources/StubResponseBodyProduction/getCriterion.json";

        String input = new String(Files.readAllBytes(Paths.get(responseBodyInput)));
        String uri ="http://localhost:" + wm.port() + "/getCriterion/";
        String stubUrl = "/getCriterion/"+id+"/"+version+"?lang="+lang+"&countryFilter="+countryFilter;
        stubFor(get(urlEqualTo(stubUrl))
                .willReturn(aResponse().withBody(input)));

        //saved (pretty printed) response from the eCertis API from postman
        String getCriterionOutput = eCertisClient.getCriterion(uri,id,version,lang,countryFilter);

        ObjectMapper mapper = new ObjectMapper();
        Criterion APIList = new Criterion();

        //Deserializes and then re-serializes the eCertis API Response with default pretty printing
        APIList = mapper.readValue(input, APIList.getClass());
        String APIReserializedInput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(APIList);

        assertThat(APIReserializedInput, is(getCriterionOutput)); //checks that the re-serialized input from the file is the same as the output of the library method
        assertThat(input.replaceAll("\\s", ""), is(getCriterionOutput.replaceAll("\\s", ""))); //checks that the eCertis API response is the same as the getCriteria output when they have no indentation
        //Both of the above should be true since getCriterion only changes the indentation of the response of the API with deserialization and re-serialization.
    }
}

