package me.splm.app.inject.processor.core;

public class Config {
    //TODO 可配置项，未来要写成builder模式
    public static final String GEN_fOLDER="me.splm.app.auto";
    public static final String APP_PACKAGE="me.splm.app.baselibdemo";

    //UnWriteble fields
    public static final String SUFFIX_POINT=".";
    private static final String SUFFIX_SIGN="_";
    public static final String SUFFIX_RECORD=SUFFIX_SIGN+"Legend";
    public static final String SUFFIX_PORTER=SUFFIX_SIGN+"Porter";
    public static final String SUFFIX__PLUMBER=SUFFIX_SIGN+"Plumber";
    public static final String SUFFIX_BEADLE=SUFFIX_SIGN+"Beadle";

    public static final int YES=1;
    public static final int NO=2;
}
