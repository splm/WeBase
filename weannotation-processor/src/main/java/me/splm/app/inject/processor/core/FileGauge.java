package me.splm.app.inject.processor.core;


import java.util.LinkedHashMap;
import java.util.Map;

public class FileGauge {
    private static Map<String, String> annotationMapper = new LinkedHashMap<>();

    static {
        annotationMapper.put("me.splm.app.inject.annotation.WeInjectPlumber", Config.SUFFIX__PLUMBER);
        annotationMapper.put("me.splm.app.inject.annotation.WeInjectPorter", Config.SUFFIX_PORTER);
    }

    public static String find(String clzzName) {
        return annotationMapper.get(clzzName);
    }
}
