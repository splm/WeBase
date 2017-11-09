package me.splm.app.inject.processor.component.elder;


public class NamePair {
    private String packageName;
    private String simpleName;

    public NamePair() {
    }

    public NamePair(String packageName, String simpleName) {
        this.packageName = packageName;
        this.simpleName = simpleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
}
