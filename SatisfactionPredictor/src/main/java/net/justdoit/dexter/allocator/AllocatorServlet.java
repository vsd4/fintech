package net.justdoit.dexter.allocator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.justdoit.dexter.model.*;
import net.justdoit.dexter.util.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.google.gson.*;

public class AllocatorServlet extends HttpServlet {
    
    private static final long serialVersionUID = -5934577083280513080L;

	private static Logger logger = Logger.getLogger(AllocatorServlet.class);

    /** The log level. */
    private static String logLevel = "info";

    Allocator allocator;
    
    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        try {
            ServletContext context = getServletContext();

            String configFile = config.getInitParameter("configFile");
            InputStream inp = context.getResourceAsStream(configFile);

            InputStreamReader isr = new InputStreamReader(inp);
            BufferedReader reader = new BufferedReader(isr);
            /*configUtil.parseConfig(reader);

            if (configUtil.getRootObject().containsKey("debug"))
                logLevel = configUtil.getRootObject().getString("debug");
             */
            
            // Loads logger config (may be better to abstract this in another method)
            String logConfigFile = config.getInitParameter("logConfigFile");
            InputStream logInp = context.getResourceAsStream(logConfigFile);
            Properties properties = new Properties();
            properties.load(logInp);
            PropertyConfigurator.configure(properties);

            logger.setLevel(Level.toLevel(logLevel));
            logger.info("Log level = " + logLevel);
            logger.info("configFile=" + configFile);
            
            //allocator = PreferenceAgnosticAllocator.getInstance();
            allocator = LowerBoundAwareAllocator.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	String postData = "{}";
        
        try {
            BufferedReader reader = req.getReader();

            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line + "\n");
                line = reader.readLine();
            }
            reader.close();

            postData = sb.toString();

 //           if (logger.isDebugEnabled()) {
                logger.info("postData = " + postData);
//            }
        } catch (IOException e) {
            logger.error("Request " + req + "\nServer Connectivity Issues, IO Exception " + e);
            res.sendError(500, "Server Connectivity Issues, IO Exception " + e);
        }
        
        
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DexterRequest.class, new RequestTranslator());
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        DexterRequest dexterRequest = null;
        
        try {
        	dexterRequest = (DexterRequest) gson.fromJson(postData, Class.forName("net.justdoit.dexter.model.DexterRequest"));
        	System.out.println("Test GSON : \t" + req);
        } catch (Exception e) {
        	System.out.println("GSON parsing failed");
        	e.printStackTrace();
        }
        
        if(dexterRequest != null){
        	processRequest(dexterRequest, res);
        } else {
        	res.sendError(CommonModel.HTTP_NOT_FOUND,
                  "Reason: Missing Data In Request");
        }
    }
    

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
    	logger.info("get request map " + req.getParameterMap());
    	
    }


    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action.equalsIgnoreCase("clear_cache")){
            
        }
        res.setStatus(200);
    }
    
    private void processRequest(DexterRequest dexterRequest, HttpServletResponse res) throws ServletException, IOException{
 
        Map<String,String> acknowledgement =new HashMap<String,String>();
        
    	acknowledgement.put("allocator_version", "0.1");
        
//    	String targetUserId = dexterRequest.getUserID();
//    	logger.info(" Dexter user id " + targetUserId);
//        if (targetUserId != null) {
        	long serverTimestamp = System.currentTimeMillis()/1000;
            
            // actual allocation
        	List<Category> allocation = allocator.allocateBudget(dexterRequest);
        	
        	// results to json
        	
            // send acknowledgement
        	Gson gson = new Gson();
        	
        	//Type ackType = new TypeToken< Map<String,String>>() {}.getType();
        	//String ackStr = gson.toJson(acknowledgement, ackType);

        	Type resType = new TypeToken< List<Category>>() {}.getType();
        	String resStr = gson.toJson(allocation, resType);
        	
            res.setContentType("application/json; charset=UTF-8");
            res.getWriter().print(resStr);
            res.getWriter().close();
//        } else {
//            res.sendError(CommonModel.HTTP_NOT_FOUND,
//                    "Reason: No Data Found In Request");
//        }
    }
}
