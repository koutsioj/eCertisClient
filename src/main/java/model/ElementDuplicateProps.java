package model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT) //prevents fields with no given values from serializing

public class ElementDuplicateProps {

	private Element[] description; //for inside EvidenceDocumentReference
	private float value; //for EvidenceType FeeAmount
	
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

}
