package model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder({"Id", "TypeCode", "Name" ,"Description","DomainID","VersionID","LegislationReference","RequirementGroup","SubCriterion","ParentCriterion"})
public class Criterion {

	private Map<String, String> Id;
	private Map<String, String> TypeCode;
	private Map<String, String> CriteriaTypeID;
	private Map<String, String> CriteriaTypeName;
	private Map<String, String> Applicable;
	private Map<String, String> Mandatory;
	private Element Name;
	private Element Description;
	private Map<String, String> DomainID;
	private Map<String, String> VersionID;
	private Map<String, String> PreviousVersionID;
	private CriterionLegislationRef[] LegislationReference;
	private CriterionRequirementGroup[] RequirementGroup;
	private Criterion[] SubCriterion;
	private Criterion ParentCriterion;	

	
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
	
	@JsonProperty(value="PreviousVersionID")
	public Map<String, String> getPreviousVersionID() {
		return PreviousVersionID;
	}
	public void setPreviousVersionID(Map<String, String> previousVersionID) {
		PreviousVersionID = previousVersionID;
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
	public Criterion[] getSubCriterion() {
		return SubCriterion;
	}
	public void setSubCriterion(Criterion[] subCriterion) {
		SubCriterion = subCriterion;
	}
	
	@JsonProperty(value="ParentCriterion")
	public Criterion getParentCriterion() {
		return ParentCriterion;
	}
	public void setParentCriterion(Criterion parentCriterion) {
		ParentCriterion = parentCriterion;
	}
	
}
