package client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.EvidenceType;
import model.Criteria;
import model.Criterion;


public class eCertisAPICalls {

	public static void main(String[] args) {

		LocalDate updateDate = LocalDate.of(2020, 11, 15);
		criteriaCallAcceptance("gr", "", null, null);
		//criteriaCallAcceptance("1100", "");
		//criteriaCallProduction("gr", "", null, null);
		//criteriaCallProduction("1602","");
		
		//criterionCallAcceptance("c27b7c4e-c837-4529-b867-ed55ce639db5","","","be");
		//criterionCallProduction("c27b7c4e-c837-4529-b867-ed55ce639db5","","","be");
		
		//criterionMdCallAcceptance("005eb9ed-1347-4ca3-bb29-9bc0db64e1ab","","","");
		//criterionMdCallProduction("005eb9ed-1347-4ca3-bb29-9bc0db64e1ab","","","");

		//criterionESPDCallProduction("811b3d07-4ef1-47a1-a16c-d973f0e65b1b","",null,null);
		//criterionESPDCallAcceptance("811b3d07-4ef1-47a1-a16c-d973f0e65b1b","",null,null);
		
		//evidencesCallAcceptance("", updateDate, null);
		//evidencesCallProduction("", updateDate, null);
		
		//evidenceCallAcceptance("f4701b97-6fda-40cb-8352-04168090574b","",null);
		//evidenceCallProduction("290823c9-e98d-477a-8289-0f11967fd558","",null);
	}
	
	private static void generalCall(String uri,Object deserializationValue) { 
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.GET()
				.timeout(Duration.ofSeconds(60))
				.build();
		
		//"output" variable value is a json string of all the information we got from the request (criteria, values etc)
		String output = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())  
				.thenApply(HttpResponse::body)     
				//.thenAccept(System.out::println)
				.join(); 
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			//mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
			
			deserializationValue = mapper.readValue(output, deserializationValue.getClass());
			
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(deserializationValue);
			
			//mapper.writeValue(new File("criteria.json"), deserializationValue); //creates new file
			System.out.println(json);


		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}
	
	public static void criteriaCallAcceptance(String nationalEntity,String type,LocalDate updateDate,String lang) { 
		
		if (nationalEntity == null || type == null )
		{
			throw new NullPointerException("'nationalEntity' and 'type' cannot be null.");
		}
		
		String updateDateFormated = null;
		if (updateDate != null)
		{
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
			updateDateFormated = updateDate.format(format);
		}
		else //for convenience a null 'LocalDate' parameter becomes an empty String when formatting
		{
			updateDateFormated = "";
		}
		
		String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria?nationalEntity="+nationalEntity+"&type="+type+"&updateDate="+updateDateFormated+"&lang="+lang;
		
		//ArrayList<Criterion> criteriaList = new ArrayList<>();
		Criteria criteriaList = new Criteria();
		generalCall(uri,criteriaList);
	}

	public static void criteriaCallAcceptance(String scenarioId,String domainId) { //works for both production (real) and acceptance (testing) environment.
		
		if (scenarioId == null || scenarioId.isBlank() || domainId == null) //empty strings are fine
		{
			throw new IllegalArgumentException("'scenarioId' can not be null or blank and 'domainId' can not be null");
		}
		String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria?scenarioId="+scenarioId+"&domainId="+domainId;
		
		//Criterion criteriaList = new Criterion();
		Criteria criteriaList = new Criteria();
		generalCall(uri,criteriaList);
	}
	
	public static void criteriaCallProduction(String nationalEntity,String type,LocalDate updateDate,String lang) { 
		
		if (nationalEntity == null || type == null ) 
		{
			throw new IllegalArgumentException("'nationalEntity' and 'type' cannot be null.");
		}
		String updateDateFormated = null;
		if (updateDate != null) 
		{
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
			updateDateFormated = updateDate.format(format);
		}
		else //for convenience a null 'LocalDate' parameter becomes an empty String when formatting
		{
			updateDateFormated = "";
		}
		
		String uri = "https://ec.europa.eu/growth/tools-databases/ecertisrest/criteria?nationalEntity="+nationalEntity+"&type="+type+"&updateDate="+updateDateFormated+"&lang="+lang;

		//ArrayList<Criterion> criteriaList = new ArrayList<>();
		//Criterion criteriaList = new Criterion();
		Criteria criteriaList = new Criteria();
		generalCall(uri,criteriaList);
	}
	
	public static void criteriaCallProduction(String scenarioId,String domainId) {
		
		if (scenarioId == null || scenarioId.isBlank() || domainId == null) 
		{
			throw new IllegalArgumentException("'scenarioId' can not be null or blank and 'domainId' can not be null");
		}
		String uri = "https://ec.europa.eu/growth/tools-databases/ecertisrest/criteria?scenarioId="+scenarioId+"&domainId="+domainId;
	
		//ArrayList<Criterion> criteriaList = new ArrayList<>();
		//Criterion criteriaList = new Criterion();
		Criteria criteriaList = new Criteria();
		generalCall(uri,criteriaList);
	}

	public static void criterionCallAcceptance(String id,String version,String lang,String countryFilter) { 
		
		if (id == null || id.isBlank() || countryFilter == null || countryFilter.isBlank() || version == null) 
		{
			throw new IllegalArgumentException("'id' and 'countryFilter' can not be null or blank. 'version' can not be null");
		}
		
		String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria/"+id+"/"+version+"?lang="+lang+"&countryFilter="+countryFilter;

		Criterion criterion = new Criterion();
		generalCall(uri,criterion);
	}
	
	public static void criterionCallProduction(String id,String version,String lang,String countryFilter) {
		
		if (id == null || id.isBlank() || countryFilter == null || countryFilter.isBlank() || version == null) 
		{
			throw new IllegalArgumentException("'id' and 'countryFilter' can not be null or blank. 'version' can not be null");
		}
		
		String uri = "https://ec.europa.eu/growth/tools-databases/ecertisrest/criteria/"+id+"/"+version+"?lang="+lang+"&countryFilter="+countryFilter;

		Criterion criterion = new Criterion();
		generalCall(uri,criterion);	
	}
	
	public static void criterionMdCallAcceptance(String id,String version,String lang,String nationalEntity) { 
		
		if (id == null || id.isBlank() || version == null || nationalEntity == null) 
		{
			throw new IllegalArgumentException("'id' can not be null or blank. 'version' and 'nationalEntity' can not be null");
		}
		
		String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteriaMd/"+id+"/"+version+"?lang="+lang+"&nationalEntity="+nationalEntity;

		Criterion criterion = new Criterion();
		generalCall(uri,criterion);	
	}
	
	public static void criterionMdCallProduction(String id,String version,String lang,String nationalEntity) { 
		
		if (id == null || id.isBlank() || version == null || nationalEntity == null) 
		{
			throw new IllegalArgumentException("'id' can not be null or blank. 'version' and 'nationalEntity' can not be null");
		}
		
		String uri = "https://ec.europa.eu/growth/tools-databases/ecertisrest/criteriaMd/"+id+"/"+version+"?lang="+lang+"&nationalEntity="+nationalEntity;

		Criterion criterion = new Criterion();
		generalCall(uri,criterion);	
	}
	
	public static void criterionESPDCallAcceptance(String id,String version,String lang,String countryFilter) {
		
		if (id == null || id.isBlank() || version == null) 
		{
			throw new IllegalArgumentException("'id' can not be null or blank. 'version' can not be null");
		}
		
		String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria/espd/"+id+"/"+version+"?lang="+lang+"&countryFilter="+countryFilter;

		Criterion criterion = new Criterion();
		generalCall(uri,criterion);	
	}
	
	public static void criterionESPDCallProduction(String id,String version,String lang,String countryFilter) { 
		
		if (id == null || id.isBlank() || version == null) 
		{
			throw new IllegalArgumentException("'id' can not be null or blank. 'version' can not be null");
		}
		
		String uri = "https://ec.europa.eu/growth/tools-databases/ecertisrest/criteria/espd/"+id+"/"+version+"?lang="+lang+"&countryFilter="+countryFilter;

		Criterion criterion = new Criterion();
		generalCall(uri,criterion);	
	}
	
	public static void evidencesCallAcceptance(String nationalEntity,LocalDate updateDate,String lang) { 
	
		String updateDateFormated = null;
		if (updateDate != null)
		{
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
			updateDateFormated = updateDate.format(format);
		}
		else 
		{
			updateDateFormated = "";
		}
		
		if (nationalEntity == null || (nationalEntity.isBlank() && updateDateFormated.isBlank())) //empty strings are fine
		{
			throw new IllegalArgumentException("'nationalEntity' cannot be null. Also either 'nationalEntity' or 'updateDate' need a non blank value.");
		}
		
		String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/evidences?nationalEntity="+nationalEntity+"&updateDate="+updateDateFormated+"&lang="+lang;
		ArrayList<EvidenceType> evidenceList = new ArrayList<>();
		generalCall(uri,evidenceList);	
	}
	
	public static void evidencesCallProduction(String nationalEntity,LocalDate updateDate,String lang) { //works for both production (real) and acceptance (testing) environment

		
		String updateDateFormated = null;
		if (updateDate != null)
		{
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
			updateDateFormated = updateDate.format(format);
		}
		else
		{
			updateDateFormated = "";
		}
		
		if (nationalEntity == null || (nationalEntity.isBlank() && updateDateFormated.isBlank()))
		{
			throw new IllegalArgumentException("'nationalEntity' cannot be null. Also either 'nationalEntity' or 'updateDate' need a non blank value.");
		}
		
		String uri = "https://ec.europa.eu/growth/tools-databases/ecertisrest/evidences?nationalEntity="+nationalEntity+"&updateDate="+updateDateFormated+"&lang="+lang;
		ArrayList<EvidenceType> evidenceList = new ArrayList<>();
		generalCall(uri,evidenceList);	
	}
		
	public static void evidenceCallAcceptance(String id,String version,String lang) {
	
		if (id == null || id.isBlank() || version == null) 
		{
			throw new IllegalArgumentException("'id' cannot be null or blank and 'version' cannot be null.");
		}
		
		String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/evidences/"+id+"/"+version+"?lang="+lang;
		EvidenceType evidenceList = new EvidenceType();
		generalCall(uri,evidenceList);
	}
	
	public static void evidenceCallProduction(String id,String version,String lang) {
		
		if (id == null || id.isBlank() || version == null) 
		{
			throw new IllegalArgumentException("'id' cannot be null or blank and 'version' cannot be null.");
		}
		
		String uri = "https://ec.europa.eu/growth/tools-databases/ecertisrest/evidences/"+id+"/"+version+"?lang="+lang;
		EvidenceType evidenceList = new EvidenceType();
		generalCall(uri,evidenceList);
	}
}

