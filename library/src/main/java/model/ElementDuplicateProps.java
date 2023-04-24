package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_DEFAULT) //prevents fields with no given values from serializing
@JsonPropertyOrder({"description", "value" , "currencyID"})
public class ElementDuplicateProps {

	private Element[] description; //for inside EvidenceDocumentReference
	private float value; //for EvidenceType FeeAmount
	private String currencyID; //for EvidenceType FeeAmount

	public Element[] getDescription() {
		return description;
	}
	public void setDescription(Element[] description) {
		this.description = description;
	}
	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}

	public String getCurrencyID() {
		return currencyID;
	}
}
