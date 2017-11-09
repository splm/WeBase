package me.splm.app.inject.processor.log;



import javax.tools.Diagnostic;

public class Logger {
    private LoggerContext mContenxt;
    public Logger(LoggerContext loggerContext){
        this.mContenxt=loggerContext;
    }

    private void console(Level level,String message){
        console(level,message,null);
    }

    private void console(Level level,String message,Throwable tr){
        Diagnostic.Kind kind=resolveLogTag(level);
        mContenxt.consoleLog(kind,message,tr);
    }

    public void info(String message){
        console(Level.INFO,message);
    }

    public void warn(String message){
        console(Level.WARN,message);
    }

    //debug
    public void debug(String message){
        console(Level.DEBUG, message);
    }

    public void debug(String message,Throwable tr){
        console(Level.DEBUG,message,tr);
    }

    //error
    public void error(String message){
        console(Level.ERROR,message);
    }

    public void error(Throwable tr){
        console(Level.ERROR,null,tr);
    }

    public void error(String message,Throwable tr){
        console(Level.ERROR,message,tr);
    }

    private Diagnostic.Kind resolveLogTag(Level level){
        switch(level){
            case INFO:
                return Diagnostic.Kind.NOTE;
            case DEBUG:
                return Diagnostic.Kind.NOTE;
            case WARN:
                return Diagnostic.Kind.NOTE;
            case ERROR:
                return Diagnostic.Kind.ERROR;
        }
        return Diagnostic.Kind.OTHER;
    }
}
