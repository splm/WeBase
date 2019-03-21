package me.splm.app.inject.processor.log;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

public class LoggerContext {
    private static LoggerContext instance;
    private ProcessingEnvironment processingEnvironment;
    private Messager messager;
    private final Object mLockObject=new Object();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.S", Locale.ENGLISH);
    public boolean mProcessed;

    private LoggerContext() {
    }

    public static LoggerContext getInstance() {
        if (instance == null) {
            synchronized (LoggerContext.class) {
                if (instance == null) {
                    instance = new LoggerContext();
                }
            }
        }
        return instance;
    }

    public void setProcessingEnvironment(ProcessingEnvironment processingEnvironment) {
        synchronized (mLockObject){
            this.processingEnvironment = processingEnvironment;
            this.messager = processingEnvironment.getMessager();
        }
    }

    public boolean isInit() {
        synchronized (mLockObject){
            return processingEnvironment!=null;//If this mProcessorEnv is not null,it will return true.
        }
    }

    public boolean isProcessed(){
        return mProcessed;
    }

    /**
     * Check weather the Logger engine neccessary argument has been already setted.
     */
    private void checkInit(){
        if(!isInit()){
            throw new IllegalStateException("[javax.annotation.processing.ProcessingEnvironment] should be not null,Please check weather the init job has completed!");
        }
    }

    public void consoleLog(Diagnostic.Kind kind, String message, Throwable tr) {
        checkInit();
        messager.printMessage(kind, buildMsg(message, tr));
    }

    public void consoleLog(Diagnostic.Kind kind, String message) {
        consoleLog(kind, message, null);
    }

    private String buildMsg(String message, Throwable tr) {
        //[ Date ]+[ Thread:ThreadNum.]+[ LogLevel: -- n -- ]+ [ processLineNum: -- n -- ].+{ CommonMessage : txt }+exceptionMessage
        StringBuilder builder = new StringBuilder();
        String date = getCurrentDate();
        long threadNum = getThreadNum();
        String[] resource = getCallerRaw();
        builder.append(" [ " + date + " ] ");
        builder.append(" [ Thread: -- " + threadNum + " -- ] ");
        //resource[0]=name of File,resource[1]=name of class,resource[2]=name of method,resource[3]=call number line
        builder.append(" { [ resource: -- "+resource[1] +".("+resource[2]+") ] [ ProcessLineNum: -- " + resource[3] + " -- ] }");
        if(message!=null||!"".equals(message)){
            builder.append(" { " + message + " } ");
        }
        if (tr != null) {
            builder.append(" [ Exception:" + getExceptionDetail(tr) + " ] ");
        }
        try{
            return new String(builder.toString().getBytes("GBK"), "utf-8");
        }catch (UnsupportedEncodingException e){

        }
        return builder.toString();
    }

    private String getCurrentDate() {
        return DATE_FORMAT.format(new Date());
    }

    /**
     * Get current thread id.
     * @return
     */
    private long getThreadNum() {
        return Thread.currentThread().getId();
    }

    /**
     * Get the line number where the logger has been called.
     * @return If no calling,return a String[]
     */
    private String[] getCallerRaw() {
        String[] resource=new String[4];
        boolean previousWasLogger = false;
        String loggerClassName = Logger.class.getCanonicalName();

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (stackTraceElement.getClassName().equals(loggerClassName)) {
                previousWasLogger = true;
            } else if (previousWasLogger) {
                resource[0]=stackTraceElement.getFileName();
                resource[1]=stackTraceElement.getClassName();
                resource[2]=stackTraceElement.getMethodName();
                resource[3]=stackTraceElement.getLineNumber()+"";
                return resource;
            }
        }
        return null;
    }

    /**
     * If the application goes error,You can check error messages via this method.<br />
     * What's the different between {@link #getExceptionDetail(Throwable)} and {@link #getExceptionInfoFromPanel(Throwable)},During my development,{@link #getExceptionInfoFromPanel} <br />
     * didn't load the information of the errors instead of printing a chunk of blanks.
     * @param e
     * @return
     */
    private String getExceptionDetail(Throwable e) {
        StringBuffer stringBuffer = new StringBuffer(e.toString() + "\n");
        StackTraceElement[] messages = e.getStackTrace();
        int length = messages.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append("\t"+messages[i].toString()+"\n");
        }
        return stringBuffer.toString();
    }

    /**
     * The function is same as {@link #getExceptionDetail(Throwable)}
     * @param tr
     * @return
     */
    private String getExceptionInfoFromPanel(Throwable tr){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        tr.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception e1) {

        }
        return ret;
    }

}
