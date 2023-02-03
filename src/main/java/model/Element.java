package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_DEFAULT) //prevents fields with no given values from serializing
@JsonPropertyOrder({"value", "languageID", "currencyID" ,"description","Name","Attachment","ExternalReference","URI"})
public class Element { //contains values of an element of a criterion 

	private String value;
	private String languageID;

	//the properties bellow are needed for any specific criterion
	private Element description;
	private Element Name;
	private Element Attachment;
	private Element ExternalReference;
	private Element URI;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLanguageID() {
		return languageID;
	}
	public void setLanguageID(String languageID) {
		this.languageID = languageID;
	}

	public Element getDescription() {
		return description;
	}
	public void setDescription(Element description) {
		this.description = description;
	}

	@JsonProperty(value="Name")
	public Element getName() {
		return Name;
	}
	public void setName(Element name) {
		Name = name;
	}

	@JsonProperty(value="Attachment")
	public Element getAttachment() {
		return Attachment;
	}
	public void setAttachment(Element attachment) {
		Attachment = attachment;
	}

	@JsonProperty(value="ExternalReference")
	public Element getExternalReference() {
		return ExternalReference;
	}
	public void setExternalReference(Element externalReference) {
		ExternalReference = externalReference;
	}

	@JsonProperty(value="URI")
	public Element getURI() {
		return URI;
	}
	public void setURI(Element uRI) {
		URI = uRI;
	}
}
