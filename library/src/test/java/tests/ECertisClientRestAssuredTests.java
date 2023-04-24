package tests;

import client.ECertisClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class ECertisClientRestAssuredTests {

    static ECertisClient eCertisClientTest = new ECertisClient(true);

    @Test
    public void CriteriaCall_WhenNationalEntityIsCorrect_JsonElementValuesOfTheFirstCriterionAreNotNull_AndIdValueIsCorrect() {
        String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria";
        String nationalEntity = "gr";
        String type = "";
        //LocalDate updateDate = LocalDate.now().minusYears(5);
        LocalDate updateDate = null;
        String lang = null;

        String updateDateFormatted;
        if (updateDate != null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            updateDateFormatted = updateDate.format(format);
        } else
        {
            updateDateFormatted = "";
        }
        String fullUrl = uri + "?nationalEntity=" + nationalEntity + "&type=" + type + "&updateDate=" + updateDateFormatted + "&lang=" + lang; //used below for testing from the webpage itself

        String jsonResponse = eCertisClientTest.getCriteria(uri,nationalEntity,type,updateDate,lang);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readValue(jsonResponse, JsonNode.class); //root of json
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode firstCriterion = rootNode.get("criterion").get(0); //the first criterion from the array
        JsonNode idNode = firstCriterion.get("Id"); //the id of the first criterion
        JsonNode nameNode = firstCriterion.get("Name");
        JsonNode descriptionNode = firstCriterion.get("Description");
        JsonNode versionIdNode = firstCriterion.get("VersionID");
        JsonNode criteriaTypeNameNode = firstCriterion.get("CriteriaTypeName");
        JsonNode criteriaTypeIdNode = firstCriterion.get("CriteriaTypeID");

        assertThat(idNode.get("value").asText(), is(notNullValue())); //checks that the id of the first criterion is not null
        assertThat(nameNode.get("value").asText(), is(notNullValue()));
        assertThat(descriptionNode.get("value").asText(), is(notNullValue()));
        assertThat(versionIdNode.get("value").asText(), is(notNullValue()));
        assertThat(criteriaTypeNameNode.get("value").asText(), is(notNullValue()));
        assertThat(criteriaTypeIdNode.get("value").asText(), is(notNullValue()));

        get(fullUrl).then().statusCode(200).assertThat() //'get' request to the eCertis API
                .body("criterion[0].Id.value", equalTo(idNode.get("value").asText())); //checks if the value of the id of the first criterion of the json provided by the eCertisAPI is the same as the one from criteriaCall
    }

    @Test
    public void CriteriaCall_WhenScenarioIdIsCorrect_JsonElementValuesOfTheFirstCriterionAreNotNull_AndIdValueIsCorrect() {
        String uri = "https://ec.europa.eu/growth/tools-databases/ecertisrest/criteria";
        String scenarioId = "1602";
        String domainId = "";
        String fullUrl = uri + "?scenarioId=" + scenarioId + "&domainId=" + domainId;

        String jsonResponse = eCertisClientTest.getCriteria(uri, scenarioId, domainId);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readValue(jsonResponse, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode firstCriterion = rootNode.get("criterion").get(0);
        JsonNode idNode = firstCriterion.get("Id");
        JsonNode nameNode = firstCriterion.get("Name");
        JsonNode descriptionNode = firstCriterion.get("Description");
        JsonNode domainIdNode = firstCriterion.get("DomainID");
        JsonNode versionIdNode = firstCriterion.get("VersionID");
        JsonNode criteriaTypeNameNode = firstCriterion.get("CriteriaTypeName");
        JsonNode previousVersionIdNode = firstCriterion.get("PreviousVersionID");
        JsonNode applicableNode = firstCriterion.get("Applicable");
        JsonNode criteriaTypeIdNode = firstCriterion.get("CriteriaTypeID");
        JsonNode mandatoryNode = firstCriterion.get("Mandatory");

        assertThat(idNode.get("value").asText(), is(notNullValue()));
        assertThat(nameNode.get("value").asText(), is(notNullValue()));
        assertThat(descriptionNode.get("value").asText(), is(notNullValue()));
        assertThat(domainIdNode.get("value").asText(), is(notNullValue()));
        assertThat(versionIdNode.get("value").asText(), is(notNullValue()));
        assertThat(criteriaTypeNameNode.get("value").asText(), is(notNullValue()));
        assertThat(previousVersionIdNode.get("value").asText(), is(notNullValue()));
        assertThat(applicableNode.get("value").asText(), is(notNullValue()));
        assertThat(criteriaTypeIdNode.get("value").asText(), is(notNullValue()));
        assertThat(mandatoryNode.get("value").asText(), is(notNullValue()));


        get(fullUrl).then().statusCode(200).assertThat()
                .body("criterion[0].Id.value", equalTo(idNode.get("value").asText()));
    }

    @Test
    public void CriterionCall_WhenIdAndCountryFilterAreCorrect_JsonElementValuesAreNotNull_AndSpecificTestValuesAreCorrect() {
        String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria/";
        String id = "c27b7c4e-c837-4529-b867-ed55ce639db5";
        String version = "";
        String lang = "";
        String countryFilter = "be";

        String fullUrl = uri + id + "/" + version + "?lang=" + lang + "&countryFilter=" + countryFilter;

        String jsonResponse = eCertisClientTest.getCriterion(uri,id,version,lang,countryFilter);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readValue(jsonResponse, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode idNode = rootNode.get("Id");
        JsonNode typeCodeNode = rootNode.get("TypeCode");
        JsonNode nameNode = rootNode.get("Name");
        JsonNode descriptionNode = rootNode.get("Description");
        JsonNode domainIdNode = rootNode.get("DomainID");
        JsonNode versionIdNode = rootNode.get("VersionID");
        JsonNode legislationReferenceNode = rootNode.get("LegislationReference");
        JsonNode subCriterionNode = rootNode.get("SubCriterion");
        JsonNode parentCriterionNode = rootNode.get("ParentCriterion");

        assertThat(idNode.get("value").asText(), is(notNullValue()));
        assertThat(typeCodeNode.get("value").asText(), is(notNullValue()));
        assertThat(nameNode.get("value").asText(), is(notNullValue()));
        assertThat(descriptionNode.get("value").asText(), is(notNullValue()));
        assertThat(domainIdNode.get("value").asText(), is(notNullValue()));
        assertThat(versionIdNode.get("value").asText(), is(notNullValue()));
        assertThat(legislationReferenceNode.get(0), is(notNullValue()));
        assertThat(subCriterionNode.get(0), is(notNullValue()));
        assertThat(parentCriterionNode.get("Id").get("value"), is(notNullValue()));

        get(fullUrl).then().statusCode(200).assertThat()
                .body("Id.value", equalTo(idNode.get("value").asText()))
                .body("LegislationReference[0].Title.value", equalTo(legislationReferenceNode.get(0).get("Title").get("value").asText())) //checks if the "value" of the first "Title" in the "LegislationReference" array is equal from the value from the webpage
                .body("SubCriterion[0].Id.value", equalTo(subCriterionNode.get(0).get("Id").get("value").asText()))
                .body("ParentCriterion.Id.value", equalTo(parentCriterionNode.get("Id").get("value").asText()));
    }

    @Test
    public void CriterionMdCall_WhenIdIsCorrect_JsonElementValuesAreNotNull_AndSpecificTestValuesAreCorrect() {
        String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteriaMd/";
        String id = "005eb9ed-1347-4ca3-bb29-9bc0db64e1ab";
        String version = "";
        String lang = "";
        String nationalEntity = "";

        String fullUrl = uri + id + "/" + version + "?lang=" + lang + "&nationalEntity=" + nationalEntity;

        String jsonResponse = eCertisClientTest.getCriterionSimplified(uri,id,version,lang,nationalEntity);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readValue(jsonResponse, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode idNode = rootNode.get("Id");
        JsonNode nameNode = rootNode.get("Name");
        JsonNode descriptionNode = rootNode.get("Description");
        JsonNode versionIdNode = rootNode.get("VersionID");
        JsonNode legislationReferenceNode = rootNode.get("LegislationReference");
        JsonNode requirementGroupNode = rootNode.get("RequirementGroup");

        assertThat(idNode.get("value").asText(), is(notNullValue()));
        assertThat(nameNode.get("value").asText(), is(notNullValue()));
        assertThat(descriptionNode.get("value").asText(), is(notNullValue()));
        assertThat(versionIdNode.get("value").asText(), is(notNullValue()));
        assertThat(legislationReferenceNode.get(0), is(notNullValue()));
        assertThat(requirementGroupNode.get(0), is(notNullValue()));

        get(fullUrl).then().statusCode(200).assertThat()
                .body("Id.value", equalTo(idNode.get("value").asText()))
                .body("LegislationReference[0].Title.value", equalTo(legislationReferenceNode.get(0).get("Title").get("value").asText()))
                .body("RequirementGroup[0].Id.value", equalTo(requirementGroupNode.get(0).get("Id").get("value").asText()));
    }

    @Test
    public void criterionESPDCall_WhenIdIsCorrect_JsonElementValuesAreNotNull_AndSpecificTestValuesAreCorrect() {
        String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria/espd/";

        String id = "811b3d07-4ef1-47a1-a16c-d973f0e65b1b";
        String version = "";
        String lang = "";
        String countryFilter = "";

        String fullUrl = uri + id + "/" + version + "?lang=" + lang + "&countryFilter=" + countryFilter;

        String jsonResponse = eCertisClientTest.getESPDCriterion(uri,id,version,lang,countryFilter);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readValue(jsonResponse, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode idNode = rootNode.get("Id");
        JsonNode typeCodeNode = rootNode.get("TypeCode");
        JsonNode nameNode = rootNode.get("Name");
        JsonNode descriptionNode = rootNode.get("Description");
        JsonNode domainIdNode = rootNode.get("DomainID");
        JsonNode versionIdNode = rootNode.get("VersionID");
        JsonNode legislationReferenceNode = rootNode.get("LegislationReference");
        JsonNode requirementGroup = rootNode.get("RequirementGroup");
        JsonNode parentCriterionNode = rootNode.get("ParentCriterion");

        assertThat(idNode.get("value").asText(), is(notNullValue()));
        assertThat(typeCodeNode.get("value").asText(), is(notNullValue()));
        assertThat(nameNode.get("value").asText(), is(notNullValue()));
        assertThat(descriptionNode.get("value").asText(), is(notNullValue()));
        assertThat(domainIdNode.get("value").asText(), is(notNullValue()));
        assertThat(versionIdNode.get("value").asText(), is(notNullValue()));
        assertThat(legislationReferenceNode.get(0), is(notNullValue()));
        assertThat(requirementGroup.get(0), is(notNullValue()));
        assertThat(parentCriterionNode.get("Id").get("value"), is(notNullValue()));

        get(fullUrl).then().statusCode(200).assertThat()
                .body("Id.value", equalTo(idNode.get("value").asText()))
                .body("LegislationReference[0].Title.value", equalTo(legislationReferenceNode.get(0).get("Title").get("value").asText()))
                .body("RequirementGroup[0].Id.value", equalTo(requirementGroup.get(0).get("Id").get("value").asText()))
                .body("ParentCriterion.Id.value", equalTo(parentCriterionNode.get("Id").get("value").asText()));
    }

    @Test
    public void EvidencesCall_WhenNationalEntityIsCorrect_JsonElementValuesOfTheFirstEvidenceAreNotNull_AndTheyAreCorrect() {
        String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/evidences";

        String nationalEntity = "gr";
        LocalDate updateDate = null;
        String lang = null;

        String updateDateFormatted;
        if (updateDate != null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            updateDateFormatted = updateDate.format(format);
        } else
        {
            updateDateFormatted = "";
        }
        String fullUrl = uri + "?nationalEntity=" + nationalEntity + "&updateDate=" + updateDateFormatted + "&lang=" + lang;

        String jsonResponse = eCertisClientTest.getEvidences(uri,nationalEntity,updateDate,lang);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readValue(jsonResponse, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode firstEvidence = rootNode.get(0);
        JsonNode idNode = firstEvidence.get("Id");
        JsonNode nameNode = firstEvidence.get("Name");
        JsonNode descriptionNode = firstEvidence.get("Description");
        JsonNode versionIdNode = firstEvidence.get("VersionID");

        assertThat(idNode.get("value").asText(), is(notNullValue()));
        assertThat(nameNode.get("value").asText(), is(notNullValue()));
        assertThat(descriptionNode.get("value").asText(), is(notNullValue()));
        assertThat(versionIdNode.get("value").asText(), is(notNullValue()));

        get(fullUrl).then().statusCode(200).assertThat()
                .body("[0].Id.value", equalTo(idNode.get("value").asText()));
     }

    @Test
    public void EvidenceCall_WhenIdIsCorrect_JsonElementValuesAreNotNull_AndSpecificTestValuesAreCorrect() {
        String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/evidences/";
        String id = "f4701b97-6fda-40cb-8352-04168090574b";
        String version = "";
        String lang = "";

        String fullUrl = uri + id + "/" + version + "?lang=" + lang;

        String jsonResponse = eCertisClientTest.getEvidence(uri,id,version,lang);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readValue(jsonResponse, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode idNode = rootNode.get("Id");
        JsonNode typeCodeNode = rootNode.get("TypeCode");
        JsonNode nameNode = rootNode.get("Name");
        JsonNode descriptionNode = rootNode.get("Description");
        JsonNode versionIdNode = rootNode.get("VersionID");
        JsonNode feeAmountNode = rootNode.get("FeeAmount");
        JsonNode evidenceIntendedUseNode = rootNode.get("EvidenceIntendedUse");
        JsonNode evidenceIssuerPartyNode = rootNode.get("EvidenceIssuerParty");
        JsonNode addresseeDescriptionNode = rootNode.get("AddresseeDescription");
        JsonNode jurisdictionLevelCodeNode = rootNode.get("JurisdictionLevelCode");
        JsonNode evidenceDocumentReferenceNode = rootNode.get("EvidenceDocumentReference");

        assertThat(idNode.get("value").asText(), is(notNullValue()));
        assertThat(typeCodeNode.get("value").asText(), is(notNullValue()));
        assertThat(nameNode.get("value").asText(), is(notNullValue()));
        assertThat(descriptionNode.get("value").asText(), is(notNullValue()));
        assertThat(versionIdNode.get("value").asText(), is(notNullValue()));
        assertThat(feeAmountNode.get("value").asText(), is(notNullValue()));
        assertThat(evidenceIntendedUseNode.get("description").get("value").asText(), is(notNullValue()));
        assertThat(evidenceIssuerPartyNode.get(0), is(notNullValue()));
        assertThat(addresseeDescriptionNode.get("value").asText(), is(notNullValue()));
        assertThat(jurisdictionLevelCodeNode.get(0), is(notNullValue()));
        assertThat(evidenceDocumentReferenceNode.get(0), is(notNullValue()));

        get(fullUrl).then().statusCode(200).assertThat()
                .body("Id.value", equalTo(idNode.get("value").asText()))
                .body("FeeAmount.value", equalTo(feeAmountNode.get("value").floatValue()))
                .body("EvidenceIntendedUse.description.value", equalTo(evidenceIntendedUseNode.get("description").get("value").asText()))
                .body("EvidenceIssuerParty[0].PartyName[0].Name.languageID", equalTo(evidenceIssuerPartyNode.get(0).get("PartyName").get(0).get("Name").get("languageID").asText()))
                .body("AddresseeDescription.value", equalTo(addresseeDescriptionNode.get("value").asText()))
                .body("JurisdictionLevelCode[0].value", equalTo(jurisdictionLevelCodeNode.get(0).get("value").asText()))
                .body("EvidenceDocumentReference[0].Attachment.ExternalReference.URI.value", equalTo(evidenceDocumentReferenceNode.get(0).get("Attachment").get("ExternalReference").get("URI").get("value").asText()));
    }
}
