package net.justdoit.dexter.tests;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.justdoit.dexter.allocator.*;
import net.justdoit.dexter.model.Category;
import net.justdoit.dexter.model.DexterRequest;
import net.justdoit.dexter.model.Transaction;
import net.justdoit.dexter.util.TransactionTranslator;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;

public class TestAllocatorModelWithTransactionsNoJunit{

	DexterRequest req;
	
	public void setup(){
		this.req = new DexterRequest();
		
		req.setBudget(2500.0);
		
		Map<String,Double> allocationRatios = new HashMap<String,Double>();
		Map<String,Double> allocationAmounts = new HashMap<String,Double>();
		
		allocationAmounts.put(Category.RENT, (double) 1000);
		
		List<Transaction> transactions = new LinkedList<Transaction>();
		
		Transaction t1 = new Transaction();
		
		t1.accountId = "nonce:42069000-96459775";
		t1.transactionId = "1424044740000";
		t1.merchant = "Fuel City";
		
		t1.setTransactionTime("2015-02-15T17:19:00.000Z");
		t1.amount = Double.parseDouble("-739979");
		t1.categorization = "Gas & Fuel";

		Transaction t2 = new Transaction();
		
		String t2Json = "{\"transaction-id\":\"1425679320000\",\"account-id\":\"nonce:42069000-96459775\","
				+ "\"raw-merchant\":\"SERVICE FEE\",\"merchant\":\"Service Fee\",\"is-pending\":false,\"transaction-time\":\"2015-03-05T18:58:00.000Z\",\"amount\":-48000,\"categorization\":\"Unknown\"}";
		
		//Gson gson = new Gson();
        
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Transaction.class, new TransactionTranslator());
        builder.setPrettyPrinting();
        Gson gson = builder.create();
		
        try {
        	t2 = (Transaction) gson.fromJson(t2Json, Class.forName("net.justdoit.dexter.model.Transaction"));
        	System.out.println("Test GSON : \t" + t2);
        } catch (Exception e) {
        	System.out.println("GSON parsing failed");
        }
        
        Transaction t3 = new Transaction();
        try {
        	String t3Json = "{\"transaction-id\":\"1425452880000\",\"account-id\":\"nonce:42069000-96459775\","
				+ "\"raw-merchant\":\"CHECK\",\"merchant\":\"Check\",\"is-pending\":false,\"transaction-time\":\"2015-03-03T00:56:00.000Z\",\"amount\":-13642900,\"categorization\":\"Unknown\"}";
				t3 = (Transaction) gson.fromJson(t3Json, Class.forName("net.justdoit.dexter.model.Transaction"));
				System.out.println("Test GSON : \t" + t3);
        } catch (Exception e) {
        	System.out.println("GSON parsing failed");
        }
        
        Transaction t4 = new Transaction();
        try {
        	String t4Json = "{\"transaction-id\":\"1425442800000\",\"account-id\":\"nonce:42069000-96459775\","
				+ "\"raw-merchant\":\"CC PAYMENT\",\"merchant\":\"CC Payment\",\"is-pending\":false,\"transaction-time\":\"2015-03-03T07:22:00.000Z\",\"amount\":-5194500,\"categorization\":\"Unknown\"}";
        	t4 = (Transaction) gson.fromJson(t4Json, Class.forName("net.justdoit.dexter.model.Transaction"));
        	System.out.println("Test GSON : \t" + t4);
        } catch (Exception e) {
        	System.out.println("GSON parsing failed");
        }
        
        transactions.add(t1);
        transactions.add(t2);
        transactions.add(t3);
        transactions.add(t4);
        
        req.setTransactions(transactions);
        
        System.out.println(req);
    }
	
	void test(){
		Allocator allocator = PreferenceAgnosticAllocator.getInstance();
		
		List<Category> allocation = allocator.allocateBudget(req);
		
		System.out.println("transactions " + req.transactions);
		System.out.println(allocation);
		
		// test json encoding
		Gson gson = new Gson();
    	
    	Type resType = new TypeToken< List<Category>>() {}.getType();
    	String resStr = gson.toJson(allocation, resType);
    	
    	System.out.println("{\"allocations:\"" + resStr + "}");
	}
	
	public static void main(String args[]){
		TestAllocatorModelWithTransactionsNoJunit x = new TestAllocatorModelWithTransactionsNoJunit();
		
		x.setup();
		x.test();
		
	}

}
