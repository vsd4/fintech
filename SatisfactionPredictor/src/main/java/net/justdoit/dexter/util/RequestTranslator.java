package net.justdoit.dexter.util;

import java.util.List;

import net.justdoit.dexter.model.*;
import net.justdoit.dexter.util.*;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.*;

public class RequestTranslator implements JsonDeserializer<DexterRequest>, JsonSerializer<DexterRequest> {

	private static Gson gson = null;

   public RequestTranslator() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Category.class, new CategoryTranslator());
        builder.registerTypeAdapter(Transaction.class, new TransactionTranslator());
        builder.setPrettyPrinting();
        gson = builder.create();
    }
    
	/**
	 * sample input from level
	 * 
	 * {"budget" : 2500, "transactions" : ... , "categories" : ...}
	 **/
	public DexterRequest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jobj = json.getAsJsonObject();
        DexterRequest req = new DexterRequest();
        
        req.setBudget(jobj.get("budget").getAsDouble());
        
        Type transactionListType = new TypeToken<List<Transaction>>()
                {
                }.getType();
                
        req.setTransactions((List<Transaction>) gson.fromJson(jobj.get("transactions"), transactionListType));
        
        Type categoryListType = new TypeToken<List<Category>>()
                {
                }.getType();
                
        req.setCategories((List<Category>) gson.fromJson(jobj.get("categories"), categoryListType));
        
        return req;
    }

   public JsonElement serialize(DexterRequest src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jobj = new JsonObject();
        
        return jobj;
   }
}
