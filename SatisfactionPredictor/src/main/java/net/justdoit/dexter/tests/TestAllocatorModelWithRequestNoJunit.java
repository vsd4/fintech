package net.justdoit.dexter.tests;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.justdoit.dexter.allocator.AllocatorModel;
import net.justdoit.dexter.model.Category;
import net.justdoit.dexter.model.DexterRequest;
import net.justdoit.dexter.model.Transaction;
import net.justdoit.dexter.util.*;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;

public class TestAllocatorModelWithRequestNoJunit{

	DexterRequest req;
	
	public void setup(){
		this.req = new DexterRequest();
		
		String reqJson = "{ \"budget\" : 2500, \"transactions\" : [ { "
				+ "\"is-pending\" : false,\"transaction-id\" : \"1425679320000\",\"account-id\" : \"nonce:42069000-96459775\","
				+ "\"merchant\" : \"Service Fee\",\"transaction-time\" : \"2015-03-05T18:58:00.000Z\","
				+ "\"amount\" : -48000,\"categorization\" : \"Unknown\",\"raw-merchant\" : \"SERVICE FEE\"},{ "
			    + "\"is-pending\" : false,\"transaction-id\" : \"1425452880000\",\"account-id\" : \"nonce:42069000-96459775\","
				+ "\"merchant\" : \"Check\",\"transaction-time\" : \"2015-03-03T00:56:00.000Z\",\"amount\" : -13642900,"
			    + "\"categorization\" : \"Unknown\",\"raw-merchant\" : \"CHECK\"}] }";

		
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DexterRequest.class, new RequestTranslator());
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        //gson = new Gson();
        
        try {
        	req = (DexterRequest) gson.fromJson(reqJson, Class.forName("net.justdoit.dexter.model.DexterRequest"));
        	System.out.println("Test GSON : \t" + req);
        } catch (Exception e) {
        	System.out.println("GSON parsing failed");
        	e.printStackTrace();
        }
    }
	
	void test(){
		AllocatorModel allocator = AllocatorModel.getInstance();
		
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
		TestAllocatorModelWithRequestNoJunit x = new TestAllocatorModelWithRequestNoJunit();
		
		x.setup();
		x.test();
		
	}

}
