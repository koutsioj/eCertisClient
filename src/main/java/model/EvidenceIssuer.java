package model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("unused")
@JsonPropertyOrder({"WebsiteURI","PartyName"})
public class EvidenceIssuer {

	private Map<String, String> WebsiteURI;
	private Element[] PartyName;

	@JsonProperty(value="WebsiteURI")
	public Map<String, String> getWebsiteURI() {
		return WebsiteURI;
	}
	public void setWebsiteURI(Map<String, String> websiteURI) {
		WebsiteURI = websiteURI;
	}

	@JsonProperty(value="PartyName")
	public Element[] getPartyName() {
		return PartyName;
	}
	public void setPartyName(Element[] partyName) {
		PartyName = partyName;
	}
}
