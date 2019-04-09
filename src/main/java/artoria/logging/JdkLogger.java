package artoria.logging;

import artoria.io.StringBuilderWriter;
import artoria.template.LoggerRenderer;
import artoria.template.Renderer;

import java.util.logging.Level;

/**
 * Jdk logger wrapper.
 * @author Kahle
 */
public class JdkLogger implements Logger {
    private static Renderer loggerRenderer = new LoggerRenderer();

    public static Renderer getLoggerRenderer() {

        return loggerRenderer;
    }

    public static void setLoggerRenderer(Renderer loggerRenderer) {

        JdkLogger.loggerRenderer = loggerRenderer;
    }

    private final java.util.logging.Logger logger;

    private void logp(Level level, String format, Object[] arguments, Throwable throwable) {
        if (!logger.isLoggable(level)) { return; }
        StackTraceElement element = new Throwable().getStackTrace()[3];
        String clazzName = element.getClassName();
        String methodName = element.getMethodName();
        StringBuilderWriter writer = new StringBuilderWriter();
        loggerRenderer.render(arguments, writer, null, format, null);
        String message = writer.toString();
        logger.logp(level, clazzName, methodName, message, throwable);
    }

    public JdkLogger(java.util.logging.Logger logger) {

        this.logger = logger;
    }

    @Override
    public void trace(String format, Object... arguments) {

        this.logp(Level.FINER, format, arguments, null);
    }

    @Override
    public void trace(String message, Throwable throwable) {

        this.logp(Level.FINER, message, null, throwable);
    }

    @Override
    public void debug(String format, Object... arguments) {

        this.logp(Level.FINE, format, arguments, null);
    }

    @Override
    public void debug(String message, Throwable throwable) {

        this.logp(Level.FINE, message, null, throwable);
    }

    @Override
    public void info(String format, Object... arguments) {

        this.logp(Level.INFO, format, arguments, null);
    }

    @Override
    public void info(String message, Throwable throwable) {

        this.logp(Level.INFO, message, null, throwable);
    }

    @Override
    public void warn(String format, Object... arguments) {

        this.logp(Level.WARNING, format, arguments, null);
    }

    @Override
    public void warn(String message, Throwable throwable) {

        this.logp(Level.WARNING, message, null, throwable);
    }

    @Override
    public void error(String format, Object... arguments) {

        this.logp(Level.SEVERE, format, arguments, null);
    }

    @Override
    public void error(String message, Throwable throwable) {

        this.logp(Level.SEVERE, message, null, throwable);
    }

    @Override
    public boolean isTraceEnabled() {

        return logger.isLoggable(Level.FINER);
    }

    @Override
    public boolean isDebugEnabled() {

        return logger.isLoggable(Level.FINE);
    }

    @Override
    public boolean isInfoEnabled() {

        return logger.isLoggable(Level.INFO);
    }

    @Override
    public boolean isWarnEnabled() {

        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public boolean isErrorEnabled() {

        return logger.isLoggable(Level.SEVERE);
    }

}