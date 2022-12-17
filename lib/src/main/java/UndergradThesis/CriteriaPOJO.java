package UndergradThesis;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

//@JsonRootName(value = "criterion") 
public class CriteriaPOJO { //contains a list of criteria

    //@JsonProperty(value="criterion")    
	private ArrayList<CriterionPOJO> criteria = new ArrayList<CriterionPOJO>(); //string araylist???

	@JsonIgnore
	public ArrayList<CriterionPOJO> getCriteria() {
		return criteria;
	}

	public void setCriterion(CriterionPOJO criterion) {
		this.criteria.add(criterion);
	}
	
	
	@JsonRawValue() //mapper.writerWithDefaultPrettyPrinter().writeValueAsString(criteriaList) in main now uses this method for the output
	@JsonProperty(value="criterion") //changes the name of the method which acts as a getter (adds the ' "criterion" : ' at the start of the printed output)
	public String toString() { //returns a string of the Original JSON that we used to create this object
		
		String output = "[";
		for (int i=0; i<criteria.size();i++)
		{
			output = output+criteria.get(i).toString();
			if (i == criteria.size()-1)
			{
				break;
			}
			output+=",\n";
		}	
		output+= "]";
		return output;	
	}
}
