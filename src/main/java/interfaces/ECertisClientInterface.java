package interfaces;

import javax.annotation.Nullable;
import java.time.LocalDate;

public interface ECertisClientInterface {
    String criteriaCall(String uri, String nationalEntity, String type, LocalDate updateDate, String lang);
    String criteriaCall(String uri, String scenarioId, String domainId);
    String criterionCall(String uri, String id, String version, String lang, String countryFilter);
    String criterionMdCall(String uri,String id, String version, String lang, String nationalEntity);
    String criterionESPDCall(String uri, String id, String version, String lang, String countryFilter);
    String evidencesCall(String uri, String nationalEntity, LocalDate updateDate, String lang);
    String evidenceCall(String uri,String id, String version, String lang);
    }