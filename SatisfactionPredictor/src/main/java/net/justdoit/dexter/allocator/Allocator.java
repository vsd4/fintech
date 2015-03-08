package net.justdoit.dexter.allocator;


import java.util.Comparator;
import java.util.List;

import net.justdoit.dexter.model.Category;
import net.justdoit.dexter.model.CommonModel;
import net.justdoit.dexter.model.DexterRequest;

public abstract class Allocator extends CommonModel {
	
	static Comparator<Category> CategoryComparator 
		    = new Comparator<Category>() {
		
		public int compare(Category cat1, Category cat2) {
		
			double amount1 = cat1.amount;
			double amount2 = cat2.amount;
			
			return (new Double(amount2)).compareTo(amount1);
		}
		
	};
	
   protected static boolean debugEnabled;
   
    public static final String TIMESTAMP = "server_timestamp";
    
    static final int TRANS_DAYS_USED_IN_MODEL = 90;
    
    public abstract List<Category> allocateBudget(DexterRequest req);
    
}
