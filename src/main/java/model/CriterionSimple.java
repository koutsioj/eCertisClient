package model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder({"Id", "CriteriaTypeID", "CriteriaTypeName" ,"Applicable","Mandatory","Name","Description","DomainID","VersionID","PreviousVersionID"})
public class CriterionSimple { //note to self: maybe delete this and add the missing fields to CriterionDetailed

	private Map<String, String> Id;
	private Map<String, String> CriteriaTypeID;
	private Map<String, String> CriteriaTypeName;
	private Map<String, String> Applicable;
	private Map<String, String> Mandatory;
	private Element Name;
	private Element Description;
	private Map<String, String> DomainID;
	private Map<String, String> VersionID;
	private Map<String, String> PreviousVersionID;

	
	@JsonProperty(value="Id")
	public Map<String, String> getId() {
		return Id;
	}
	public void setId(Map<String, String> id) {
		Id = id;
	}
	
	@JsonProperty(value="CriteriaTypeID")
	public Map<String, String> getCriteriaTypeID() {
		return CriteriaTypeID;
	}
	public void setCriteriaTypeID(Map<String, String> criteriaTypeID) {
		CriteriaTypeID = criteriaTypeID;
	}
	
	@JsonProperty(value="CriteriaTypeName")
	public Map<String, String> getCriteriaTypeName() {
		return CriteriaTypeName;
	}
	public void setCriteriaTypeName(Map<String, String> criteriaTypeName) {
		CriteriaTypeName = criteriaTypeName;
	}
	
	@JsonProperty(value="Applicable")
	public Map<String, String> getApplicable() {
		return Applicable;
	}
	public void setApplicable(Map<String, String> applicable) {
		Applicable = applicable;
	}
	
	@JsonProperty(value="Mandatory")
	public Map<String, String> getMandatory() {
		return Mandatory;
	}
	public void setMandatory(Map<String, String> mandatory) {
		Mandatory = mandatory;
	}
	
	@JsonProperty(value="Name")
	public Element getName() {
		return Name;
	}
	public void setName(Element name) {
		Name = name;
	}
	public Element getDescription() {
		return Description;
	}
	
	@JsonProperty(value="Description")
	public void setDescription(Element description) {
		Description = description;
	}
	
	@JsonProperty(value="DomainID")
	public Map<String, String> getDomainID() {
		return DomainID;
	}
	public void setDomainID(Map<String, String> domainID) {
		DomainID = domainID;
	}
	
	@JsonProperty(value="VersionID")
	public Map<String, String> getVersionID() {
		return VersionID;
	}
	public void setVersionID(Map<String, String> versionID) {
		VersionID = versionID;
	}
	
	@JsonProperty(value="PreviousVersionID")
	public Map<String, String> getPreviousVersionID() {
		return PreviousVersionID;
	}
	public void setPreviousVersionID(Map<String, String> previousVersionID) {
		PreviousVersionID = previousVersionID;
	}
	
//	@Override
//	public String toString() {
//		return "CriterionNew [Id=" + Id + ", CriteriaTypeID=" + CriteriaTypeID
//				+ ", CriteriaTypeName=" + CriteriaTypeName + ", Name=" + Name
//				+ ", Description=" + Description + ", VersionID=" + VersionID
//				+ "]";
//	}
}
