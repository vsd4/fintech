package net.justdoit.dexter.util;

import net.justdoit.dexter.model.Transaction;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.*;

public class TransactionTranslator implements JsonDeserializer<Transaction>, JsonSerializer<Transaction> {

	/**
	 * sample input from level
	 * 
	 * "{\"transaction-id\":\"1425679320000\",\"account-id\":\"nonce:42069000-96459775\","
		+ "\"raw-merchant\":\"SERVICE FEE\",\"merchant\":\"Service Fee\",\"is-pending\":false,
		\"transaction-time\":\"2015-03-05T18:58:00.000Z\",\"amount\":-48000,\"categorization\":\"Unknown\"}";
	 */
	public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jobj = json.getAsJsonObject();
        Transaction tr = new Transaction();
        
        tr.setTransactionId(jobj.get("transaction-id").getAsString());
        tr.setAmount(jobj.get("amount").getAsDouble());
        tr.setTransactionTime(jobj.get("transaction-time").getAsString());
        tr.setCategory(jobj.get("categorization").getAsString());
        
        return tr;
    }

   public JsonElement serialize(Transaction src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jobj = new JsonObject();
        
        return jobj;
   }
}
