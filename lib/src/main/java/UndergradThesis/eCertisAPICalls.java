package UndergradThesis;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class eCertisAPICalls {

	public static void main(String[] args) throws URISyntaxException {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria?nationalEntity=gr"))
				.GET()
				.timeout(Duration.ofSeconds(60))
				.build();

		//"output" variable value is a string of all the information we got from the request (criteria, values etc)
		String output = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())  
				.thenApply(HttpResponse::body)     
				//.thenAccept(System.out::println)
				.join(); 

		ArrayList<Integer> charToSplit = new ArrayList<Integer>(); //finds the chars where we create a split in the output
		ArrayList<String> splitOutput = new ArrayList<String>(); //gets the values of the lines we split based on "charToSplit"

		int i=0;	
		int searchStartingPoint=0; 
		int index;
		while ((index = output.indexOf("},{",searchStartingPoint)) != -1) //we want to create splits on the comma wherever there are the characters "},{" in the string because after those characters comes the next criterion, so first we find where these characters are.
		{
			charToSplit.add(index+1);
			searchStartingPoint=charToSplit.get(i);
			i++;
		}

		for (i=0; i< charToSplit.size(); i++) //inserts the substrings (which act as splits) into the splitOutput arraylist
		{
			if (i==0) //first value
			{
				splitOutput.add(output.substring(output.indexOf("{\"Id\""),charToSplit.get(i))); //first criterion, we want to cut the ' {"criterion":[ '
				splitOutput.add(output.substring(charToSplit.get(i)+1 , charToSplit.get(i+1))); //second criterion
			}
			else if(i == charToSplit.size()-1) //last value
			{
				splitOutput.add(output.substring(charToSplit.get(i)+1)); //last criterion
			}
			else //middle values
			{
				splitOutput.add(output.substring(charToSplit.get(i)+1 , charToSplit.get(i+1))); //from 3rd criterion to last-1
			}
		}

		CriteriaPOJO criteriaList = new CriteriaPOJO();		        	

		ObjectMapper mapper = new ObjectMapper();
		try {
			//mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);

			TypeReference<HashMap<String, ElementPOJO>> typeRef  = new TypeReference<HashMap<String, ElementPOJO>>() {}; //we create this typereference so that we can use it in the readvalue(...) bellow to get the values we want from the splitOutput

			for (i=0;i<splitOutput.size();i++) 
			{
				CriterionPOJO criterion = new CriterionPOJO(); //each time we create a new criterion
				
				Map<String, ElementPOJO> map = mapper.readValue(splitOutput.get(i), typeRef);	
				
				map.forEach((key, value) -> {
					String keyValue = value.getValue(); //value of "value" variable of key (we also use value of "languageID" of key bellow)
					if (key.equals("Id")) 
					{
						criterion.setId(keyValue);
					}
					else if(key.equals("CriteriaTypeID")) 
					{
						criterion.setCriteriaTypeID(keyValue);
					}
					else if(key.equals("CriteriaTypeName")) 
					{
						criterion.setCriteriaTypeName(keyValue);
					}
					else if(key.equals("Name")) 
					{
						criterion.setName(keyValue); //name and description also have "language" so we include its value here
						criterion.setLanguageID(value.getLanguageID()); //name and description also have "language" so we include its value here
					}
					else if(key.equals("Description")) 
					{
						criterion.setDescription(keyValue);
						criterion.setLanguageID(value.getLanguageID()); //name and description also have "language" so we include its value here
					}
					else if(key.equals("VersionID")) 
					{
						criterion.setVersionID(keyValue);
					}
				});
				
				criteriaList.setCriterion(criterion); //on the last iteration the "criteriaList",CriterionPOJO object is complete.
			}

			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(criteriaList); //returns the "toString" method of the class (because of the @JsonRawValue)
			System.out.println(json);

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

