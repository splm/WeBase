package me.splm.app.inject.processor.log;


import java.util.HashMap;
import java.util.Map;

/**
 * Every Logger instance is contructed by it's own FACTORY,Don't try to create it through calling it's constructor.
 */
public class LoggerFactory {

    private static final Map<Class<?>,Logger> LOGGER_CACHE=new HashMap<>();

    private LoggerFactory(){}

    public static Logger getLogger(Class<?> name){
        if(name==null){
            throw new IllegalArgumentException("Name can't be a null object!");
        }
        Logger logger=LOGGER_CACHE.get(name);
        if(logger==null){
            logger=new Logger(LoggerContext.getInstance());
            LOGGER_CACHE.put(name,logger);
        }
        return logger;
    }
}
