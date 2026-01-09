package com.example.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AppLogger {
    private static Logger getLogger() {
        // Get the caller class from the stack trace
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        // stack[0] = Thread.getStackTrace, stack[1] = getLogger, stack[2] = log method, stack[3] = caller
        String className = stack[3].getClassName();
        return LoggerFactory.getLogger(className);
    }

    private static String format(Object... data) {
        if (data == null || data.length == 0) return "";
        return Arrays.stream(data)
                .map(obj -> obj instanceof Throwable ? "Exception: " + obj : String.valueOf(obj))
                .collect(Collectors.joining(" | "));
    }

    public static void info(String message, Object... data) {
        getLogger().info(message + (data.length > 0 ? " | " + format(data) : ""));
    }

    public static void debug(String message, Object... data) {
        getLogger().debug(message + (data.length > 0 ? " | " + format(data) : ""));
    }

    public static void warn(String message, Object... data) {
        getLogger().warn(message + (data.length > 0 ? " | " + format(data) : ""));
    }

    public static void error(String message, Object... data) {
        Throwable t = null;
        if (data.length > 0 && data[data.length - 1] instanceof Throwable) {
            t = (Throwable) data[data.length - 1];
            Object[] dataWithoutThrowable = Arrays.copyOf(data, data.length - 1);
            getLogger().error(message + (dataWithoutThrowable.length > 0 ? " | " + format(dataWithoutThrowable) : ""), t);
        } else {
            getLogger().error(message + (data.length > 0 ? " | " + format(data) : ""));
        }
    }
}
