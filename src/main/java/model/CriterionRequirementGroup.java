package model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"Id","TypeOfEvidence"})
public class CriterionRequirementGroup {

	private Map<String, String> Id;
	private EvidenceType[] TypeOfEvidence;
	
	@JsonProperty(value="Id")
	public Map<String, String> getId() {
		return Id;
	}
	public void setId(Map<String, String> id) {
		Id = id;
	}
	
	@JsonProperty(value="TypeOfEvidence")
	public EvidenceType[] getTypeOfEvidence() {
		return TypeOfEvidence;
	}
	public void setTypeOfEvidence(EvidenceType[] typeOfEvidence) {
		TypeOfEvidence = typeOfEvidence;
	}

	

}
