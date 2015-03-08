package net.justdoit.dexter.model;

import java.util.List;
import java.util.Map;

public class DexterRequest {

	public double budget;
	
	List<Category> ratingsAndPreferredAllocations;
	
	public List<Transaction> transactions;
	
	public String getUserID() {
		return "dummy";
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public void setTransactions(List<Transaction> transactions2) {
		transactions = transactions2;
		
	}

	public void setCategories(List<Category> ratingsAndPreferredAllocations) {
		this.ratingsAndPreferredAllocations = ratingsAndPreferredAllocations;	
	}
}
