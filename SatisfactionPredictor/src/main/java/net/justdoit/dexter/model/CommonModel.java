package net.justdoit.dexter.model;

import org.apache.log4j.Logger;

public class CommonModel {
    private static Logger logger;
    private static boolean debugEnabled;

    public static final int HTTP_NOT_FOUND = 404;
    
    static {
        logger = Logger.getLogger(CommonModel.class);
    }
    
    public CommonModel(){
    	
    }
    
}
