package model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonRootName(value = "criterion") 
public class Criteria {
	
	private CriterionSimple[] criteria;

	public void setCriteriaCriterionNew(CriterionSimple[] criteria) {
		this.criteria = criteria;
	}
	@JsonProperty(value="criterion")
	public CriterionSimple[] getCriteriaCriterionNew() {
		return criteria;
	}


	@Override
	public String toString() {
		return "CriteriaNew [criteria=" + Arrays.toString(criteria) + "]";
	}
	
	
}
