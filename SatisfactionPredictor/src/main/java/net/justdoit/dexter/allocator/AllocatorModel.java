package net.justdoit.dexter.allocator;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.justdoit.dexter.model.Category;
import net.justdoit.dexter.model.CommonModel;
import net.justdoit.dexter.model.DexterRequest;
import net.justdoit.dexter.model.Transaction;

import org.apache.log4j.Logger;

//import net.justdoit.dexter.model.User;
//import net.justdoit.dexter.model.UserDeviceType;
//import net.justdoit.narada.util.ConnectionManager;

public class AllocatorModel extends CommonModel {
	
	static Comparator<Category> CategoryComparator 
		    = new Comparator<Category>() {
		
		public int compare(Category cat1, Category cat2) {
		
			double amount1 = cat1.amount;
			double amount2 = cat2.amount;
			
			return (new Double(amount2)).compareTo(amount1);
		}
		
	};
	
    private static Logger logger = Logger.getLogger(AllocatorModel.class);

    private static boolean debugEnabled;
    public static final String TIMESTAMP = "server_timestamp";
    
    private AllocatorModel(){
    	
    }
    
    private static AllocatorModel mInstance = null;
    
    public static AllocatorModel getInstance(){
    	if(mInstance == null){
    		mInstance = new AllocatorModel();
    	}
    	return mInstance;
    }

    static final int TRANS_DAYS_USED_IN_MODEL = 90;
    
    /**
     * heuristic1
     * merge historical expenses by categories over past 3 months
     * take decayed aggregates  
     */
    public List<Category> allocateBudget(DexterRequest req){
    	double budget = req.budget;
    	
    	// go through transactions and rollup by categories
    	List<Transaction> transactions = req.transactions;
    	Map<String,Category> expensesByCategory = new HashMap<String, Category>();
    	
    	Date nowDate = new Date();
    	
    	for(Transaction tr : transactions){
    		String cat = tr.categorization;
    		
    		System.out.println("DEUG " + tr.amount + "\t" + tr.categorization + "\t" + expensesByCategory.size());
    		
    		if(cat == null){
    			cat = Category.UNKNOWN;
    		}
    		
			if(tr.amount < 0){
				// date in last 3 months
				Date trDate = tr.transactionTime;
				long startTime = trDate.getTime();
				long endTime = nowDate.getTime();
				long diffTime = endTime - startTime;
				long diffDays = diffTime / (1000 * 60 * 60 * 24);
				
				System.out.println("DEBUG diff days " + diffDays);
				
				if(diffDays < TRANS_DAYS_USED_IN_MODEL){
					double x = (0-tr.amount);
					Category c;
					if(!expensesByCategory.containsKey(cat)){
						c = new Category(cat);
    					expensesByCategory.put(cat, c);
					} else {
						c = expensesByCategory.get(cat);
					}
					c.addAmount(x);
				}
    		}
    	}
    	
    	// sort by aggregate
    	double sumExpenses = 0;
    	Collection<Category> categories = expensesByCategory.values();
    	
    	for(Category cat : categories){
    		sumExpenses += cat.amount;
    	}
    	
    	List<Category> categoriesList = new LinkedList<Category>(categories);
    	Collections.sort(categoriesList, CategoryComparator);
    	
    	// get expense ratios and allocate
    	for(Category cat : categories){
    		double expenseRatio = cat.amount/sumExpenses;
    		cat.expenseRatio = expenseRatio;
    		cat.allocation = Math.floor(expenseRatio*budget);
    	}
    	
    	return categoriesList;
    }
    
}
