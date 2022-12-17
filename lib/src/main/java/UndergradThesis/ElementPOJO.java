package UndergradThesis;

public class ElementPOJO { //contains the "value" and "languageID" values from an element a criterion

	public String value;
	public String languageID;
	
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
	
	
	@Override
	public String toString() {
		return "{\"Id\":{\"value\":\""+value+"\"},{\"Id\":{\"languageID\":\""+languageID+"\"}";
	}
}
