package net.justdoit.dexter.allocator;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.justdoit.dexter.model.Category;
import net.justdoit.dexter.model.DexterRequest;
import net.justdoit.dexter.model.Transaction;
import net.justdoit.dexter.util.TransactionTranslator;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.*;


public class AllocatorModelTest extends TestCase{

	DexterRequest req;
	
	@Before
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
		
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Transaction.class, new TransactionTranslator());
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        try {
        	t2 = (Transaction) gson.fromJson(t2Json, Class.forName("net.justdoit.dexter.model.Transaction"));
        } catch (Exception e) {
        	System.out.println("GSON parsing failed");
        }
        
        
        transactions.add(t1);
        //transactions.add(t2);
        
        req.setTransactions(transactions);
        
        System.out.println(req);
    }
	
	@Test
	public void test() {
		AllocatorModel allocator = AllocatorModel.getInstance();
		
		List<Category> allocation = allocator.allocateBudget(req);
		
		assertNotNull(allocation);
	}

}
