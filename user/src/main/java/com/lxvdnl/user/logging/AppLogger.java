package com.lxvdnl.user.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class AppLogger {

    private final Logger logger;

    private AppLogger(Class<?> _class) {
        this.logger = LoggerFactory.getLogger(_class);
    }

    public static AppLogger getLogger(Class<?> _class) {
        return new AppLogger(_class);
    }

    private String withRequestId(String message) {
        String requestId = MDC.get("requestId");
        if (requestId != null) return "[requestId=" + requestId + "] " + message;
        return message;
    }

    public void info(String message) {
        logger.info(withRequestId(message));
    }

    public void info(String format, Object... args) {
        logger.info(withRequestId(format), args);
    }

    public void debug(String message) {
        logger.debug(withRequestId(message));
    }

    public void debug(String format, Object... args) {
        logger.debug(withRequestId(format), args);
    }

    public void warn(String message) {
        logger.warn(withRequestId(message));
    }

    public void warn(String format, Object... args) {
        logger.warn(withRequestId(format), args);
    }

    public void error(String message) {
        logger.error(withRequestId(message));
    }

    public void error(String format, Object... args) {
        logger.error(withRequestId(format), args);
    }

    public void error(String message, Throwable t) {
        logger.error(withRequestId(message), t);
    }

}
