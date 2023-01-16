package model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"Title","Description","JurisdictionLevelCode","Article","URI"})
public class CriterionLegislationRef {

	private Element Title;
	private Element Description;
	private Map<String, String> JurisdictionLevelCode;
	private Element Article;
	private Map<String, String> URI;
	
	@JsonProperty(value="Title")
	public Element getTitle() {
		return Title;
	}
	public void setTitle(Element title) {
		Title = title;
	}
	
	@JsonProperty(value="Description")
	public Element getDescription() {
		return Description;
	}
	public void setDescription(Element description) {
		Description = description;
	}
	
	@JsonProperty(value="JurisdictionLevelCode")
	public Map<String, String> getJurisdictionLevelCode() {
		return JurisdictionLevelCode;
	}
	public void setJurisdictionLevelCode(
			Map<String, String> jurisdictionLevelCode) {
		JurisdictionLevelCode = jurisdictionLevelCode;
	}
	
	@JsonProperty(value="Article")
	public Element getArticle() {
		return Article;
	}
	public void setArticle(Element article) {
		Article = article;
	}
	
	@JsonProperty(value="URI")
	public Map<String, String> getURI() {
		return URI;
	}
	public void setURI(Map<String, String> uRI) {
		URI = uRI;
	}


}
