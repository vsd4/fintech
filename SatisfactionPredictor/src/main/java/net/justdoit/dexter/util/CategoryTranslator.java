package net.justdoit.dexter.util;

import net.justdoit.dexter.model.Category;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.*;

public class CategoryTranslator implements JsonDeserializer<Category>, JsonSerializer<Category> {

	/**
	 * sample input from level
	 * 
	 * {"category":"Unknown","allocation":2405.0,"expenseRatio":0.96 , "rating" : 4}
	 **/
	public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jobj = json.getAsJsonObject();
        Category c = new Category();
        
        c.setCategory(jobj.get("category").getAsString());
        c.setAllocation(jobj.get("allocation").getAsDouble());
        
        c.setMinAllocation(jobj.get("minAllocation").getAsDouble());
        
        c.setExpenseRatio(jobj.get("expenseRatio").getAsDouble());
        c.setRating(jobj.get("rating").getAsShort());
        
        return c;
    }

   public JsonElement serialize(Category src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jobj = new JsonObject();
        
        return jobj;
   }
}
