package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("unused")
@JsonPropertyOrder({"Attachment", "ValidityPeriod"})
public class EvidenceDocRef {

	private Element Attachment;
	private ElementDuplicateProps ValidityPeriod;
	
	@JsonProperty(value="Attachment")
	public Element getAttachment() {
		return Attachment;
	}
	public void setAttachment(Element attachment) {
		Attachment = attachment;
	}
	
	@JsonProperty(value="ValidityPeriod")
	public ElementDuplicateProps getValidityPeriod() {
		return ValidityPeriod;
	}
	public void setValidityPeriod(ElementDuplicateProps validityPeriod) {
		ValidityPeriod = validityPeriod;
	}
}
