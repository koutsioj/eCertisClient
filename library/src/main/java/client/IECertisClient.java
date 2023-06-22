package client;

import java.time.LocalDate;

public interface IECertisClient {
    String getCriteria(String uri, String nationalEntity, String type, LocalDate updateDate, String lang);
    String getCriteria(String uri, String scenarioId, String domainId, String lang);
    String getCriterion(String uri, String id, String version, String lang, String countryFilter);
    String getCriterionSimplified(String uri, String id, String version, String lang, String nationalEntity);
    String getESPDCriterion(String uri, String id, String version, String lang, String countryFilter);
    String getEvidences(String uri, String nationalEntity, LocalDate updateDate, String lang);
    String getEvidence(String uri, String id, String version, String lang);
    }