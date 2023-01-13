package model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_DEFAULT) //prevents fields with no given values from serializing
@JsonPropertyOrder({"Id","TypeCode","Name","Description","VersionID","FeeAmount","EvidenceIntendedUse","EvidenceIssuerParty","AddresseeDescription","JurisdictionLevelCode","EvidenceDocumentReference"})
public class EvidenceType {
	private Map<String, String> Id;
	private Map<String, String> TypeCode;
	private Element Name;
	private Element Description;
	private Map<String, String> VersionID;
	private Element FeeAmount;
	private Element EvidenceIntendedUse;
	private EvidenceIssuer[] EvidenceIssuerParty;
	private Map<String, String> AddresseeDescription;
	private Map<String, String>[] JurisdictionLevelCode;
	private EvidenceDocRef[] EvidenceDocumentReference;
	
	@JsonProperty(value="Id")
	public Map<String, String> getId() {
		return Id;
	}
	public void setId(Map<String, String> id) {
		Id = id;
	}
	
	@JsonProperty(value="TypeCode")
	public Map<String, String> getTypeCode() {
		return TypeCode;
	}
	public void setTypeCode(Map<String, String> typeCode) {
		TypeCode = typeCode;
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
	
	@JsonProperty(value="FeeAmount")
	public Element getFeeAmount() {
		return FeeAmount;
	}
	public void setFeeAmount(Element feeAmount) {
		FeeAmount = feeAmount;
	}
	
	@JsonProperty(value="EvidenceIntendedUse")
	public Element getEvidenceIntendedUse() {
		return EvidenceIntendedUse;
	}
	public void setEvidenceIntendedUse(Element evidenceIntendedUse) {
		EvidenceIntendedUse = evidenceIntendedUse;
	}
	
	@JsonProperty(value="EvidenceIssuerParty")
	public EvidenceIssuer[] getEvidenceIssuerParty() {
		return EvidenceIssuerParty;
	}
	public void setEvidenceIssuerParty(EvidenceIssuer[] evidenceIssuerParty) {
		EvidenceIssuerParty = evidenceIssuerParty;
	}
	
	@JsonProperty(value="AddresseeDescription")
	public Map<String, String> getAddresseeDescription() {
		return AddresseeDescription;
	}
	public void setAddresseeDescription(Map<String, String> addresseeDescription) {
		AddresseeDescription = addresseeDescription;
	}
	
	@JsonProperty(value="JurisdictionLevelCode")
	public Map<String, String>[] getJurisdictionLevelCode() {
		return JurisdictionLevelCode;
	}
	public void setJurisdictionLevelCode(
			Map<String, String>[] jurisdictionLevelCode) {
		JurisdictionLevelCode = jurisdictionLevelCode;
	}
	
	@JsonProperty(value="EvidenceDocumentReference")
	public EvidenceDocRef[] getEvidenceDocumentReference() {
		return EvidenceDocumentReference;
	}
	public void setEvidenceDocumentReference(
			EvidenceDocRef[] evidenceDocumentReference) {
		EvidenceDocumentReference = evidenceDocumentReference;
	}

	



}
