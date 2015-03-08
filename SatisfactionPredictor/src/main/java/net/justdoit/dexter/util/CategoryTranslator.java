package net.justdoit.dexter.util;

import net.justdoit.dexter.model.Category;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;
import com.google.gson.*;

public class CategoryTranslator implements JsonDeserializer<Category>, JsonSerializer<Category> {

	static String ALLOCATION = "allocation";
	static String RATING = "rating";
	static String MIN_ALLOCATION = "minAllocation";
	
	/**
	 * sample input from level
	 * 
	 * {"category":"Unknown","allocation":2405.0,"expenseRatio":0.96 , "rating" : 4}
	 **/
	public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jobj = json.getAsJsonObject();
        Category c = new Category();
        
        c.setCategory(jobj.get("category").getAsString());
        
        if(jobj.get(ALLOCATION) != null){
        	c.setAllocation(jobj.get(ALLOCATION).getAsDouble());
        }
        
        if(jobj.get(RATING) != null){
        	c.setRating(jobj.get(RATING).getAsShort());
        } 
        
        if(jobj.get(MIN_ALLOCATION) != null){
        	c.setMinAllocation(jobj.get(MIN_ALLOCATION).getAsDouble());
        }
        
        //c.setExpenseRatio(jobj.get("expenseRatio").getAsDouble());
        
        return c;
    }

   public JsonElement serialize(Category src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jobj = new JsonObject();
        
        return jobj;
   }
}
