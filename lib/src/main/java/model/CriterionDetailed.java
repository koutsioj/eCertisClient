package model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder({"Id", "TypeCode", "Name" ,"Description","DomainID","VersionID","LegislationReference","RequirementGroup","SubCriterion","ParentCriterion"})
public class CriterionDetailed {

	private Map<String, String> Id;
	private Map<String, String> TypeCode;
	private Element Name;
	private Element Description;
	private Map<String, String> DomainID;
	private Map<String, String> VersionID;
	private CriterionLegislationRef[] LegislationReference;
	private CriterionRequirementGroup[] RequirementGroup;
	private CriterionDetailed[] SubCriterion;
	private CriterionDetailed ParentCriterion;
	
	@JsonProperty(value="Id")
	public Map<String, String> getId() {
		return Id;
	}
	public void setId(Map<String, String> id) {
		Id = id;
	}
	
	@JsonProperty(value="Name")
	public Element getName() {
		return Name;
	}
	public void setName(Element name) {
		Name = name;
	}
	
	@JsonProperty(value="Description")
	public Element getDescription() {
		return Description;
	}
	public void setDescription(Element description) {
		Description = description;
	}
	
	@JsonProperty(value="VersionID")
	public Map<String, String> getVersionID() {
		return VersionID;
	}
	public void setVersionID(Map<String, String> versionID) {
		VersionID = versionID;
	}
	
	@JsonProperty(value="TypeCode")
	public Map<String, String> getTypeCode() {
		return TypeCode;
	}
	public void setTypeCode(Map<String, String> typeCode) {
		TypeCode = typeCode;
	}
	
	@JsonProperty(value="DomainID")
	public Map<String, String> getDomainID() {
		return DomainID;
	}
	public void setDomainID(Map<String, String> domainID) {
		DomainID = domainID;
	}
	
	@JsonProperty(value="LegislationReference")
	public CriterionLegislationRef[] getLegislationReference() {
		return LegislationReference;
	}
	public void setLegislationReference(
			CriterionLegislationRef[] legislationReference) {
		LegislationReference = legislationReference;
	}
	
	@JsonProperty(value="RequirementGroup")
	public CriterionRequirementGroup[] getRequirementGroup() {
		return RequirementGroup;
	}
	public void setRequirementGroup(CriterionRequirementGroup[] requirementGroup) {
		RequirementGroup = requirementGroup;
	}
	
	@JsonProperty(value="SubCriterion")
	public CriterionDetailed[] getSubCriterion() {
		return SubCriterion;
	}
	public void setSubCriterion(CriterionDetailed[] subCriterion) {
		SubCriterion = subCriterion;
	}
	
	@JsonProperty(value="ParentCriterion")
	public CriterionDetailed getParentCriterion() {
		return ParentCriterion;
	}
	public void setParentCriterion(CriterionDetailed parentCriterion) {
		ParentCriterion = parentCriterion;
	}
	
	
}
