package net.justdoit.dexter.model;

public class Category {
	public static String TRAVEL = "Travel";
	public static String GROCERY = "Groceries";
	public static String PERSONAL_CARE = "Personal Care";
	public static String SHOPPING = "Shopping";
	public static String HOME_IMPROVEMENT = "Home Improvement";
	public static String UNKNOWN = "Unknown";
	public static String RENT = "Rent";
	
	public String category;
	public double amount;
	public double allocation;
	public double minAllocation;
	public double expenseRatio;
	public short rating;
	
	public Category(){
		
	}
	
	public Category(String name){
		this.category = name;
		amount = 0;
	}
	
	public void addAmount(double x){
		amount += x;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Name " + category);
		//sb.append("\tAmount " + amount);
		sb.append("\tAllocation " + allocation);
		sb.append("\tExpenseRatio " + expenseRatio);
		sb.append("\tRating " + rating);
		
		return sb.toString();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAllocation() {
		return allocation;
	}

	public void setAllocation(double allocation) {
		this.allocation = allocation;
	}

	public double getExpenseRatio() {
		return expenseRatio;
	}

	public void setExpenseRatio(double expenseRatio) {
		this.expenseRatio = expenseRatio;
	}

	public short getRating() {
		return rating;
	}

	public void setRating(short rating) {
		this.rating = rating;
	}

	public double getMinAllocation() {
		return minAllocation;
	}

	public void setMinAllocation(double minAllocation) {
		this.minAllocation = minAllocation;
	}
}
