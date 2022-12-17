package UndergradThesis;

public class CriterionPOJO { //Contains the elements of a criterion

	
	//@JsonPropertyOrder({ "Id", "CriteriaTypeID","Id", "CriteriaTypeName" ,"Name", "languageID" , "Description" , "VersionID"})

	private String Id;
	private String CriteriaTypeID;
	private String CriteriaTypeName;
	private String Name;
	
	//@JsonInclude(JsonInclude.Include.NON_NULL)
	private String languageID; 	
	private String Description;
	private String VersionID;

	public CriterionPOJO(){}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getCriteriaTypeID() {
		return CriteriaTypeID;
	}

	public void setCriteriaTypeID(String criteriaTypeID) {
		CriteriaTypeID = criteriaTypeID;
	}

	public String getCriteriaTypeName() {
		return CriteriaTypeName;
	}

	public void setCriteriaTypeName(String criteriaTypeName) {
		CriteriaTypeName = criteriaTypeName;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}

	public String getLanguageID() {
		return languageID;
	}

	public void setLanguageID(String languageID) {
		this.languageID = languageID;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
	public String getVersionID() {
		return VersionID;
	}

	public void setVersionID(String versionID) {
		VersionID = versionID;
	}

	
	@Override
	public String toString(){ //unused
		return "{\"Id\":{\"value\":\""+Id+"\"},"
				+ "\"CriteriaTypeID\":{\"value\":\""+CriteriaTypeID+"\"},"
				+ "\"CriteriaTypeName\":{\"value\":\""+CriteriaTypeName+"\"},"
				+ "\"Name\":{\"value\":\""+Name+"\",\"languageID\":\""+languageID+"\"},"
				+ "\"Description\":{\"value\":\""+Description+"\",\"languageID\":\""+languageID+"\"},"
				+ "\"VersionID\":{\"value\":\""+VersionID+"\"}}";
	}
}