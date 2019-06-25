package com.jc.android.baselib.manager;

public class HttpRequestConfig implements IConfigMarker {

    private int port;
    private int ports;
    private boolean isFixWhenException;
    private String hostUrl;

    public HttpRequestConfig(Builder builder) {
        this.port = builder.port;
        this.ports = builder.ports;
        this.isFixWhenException = builder.isFixWhenException;
        this.hostUrl=builder.hostUrl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }

    public void setFixWhenException(boolean fixWhenException) {
        isFixWhenException = fixWhenException;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public int getPorts() {
        return ports;
    }

    public boolean isFixWhenException() {
        return isFixWhenException;
    }

    public String getHostUrl(){
        return hostUrl;
    }

    public static class Builder {
        int port;
        int ports;
        boolean isFixWhenException;
        String hostUrl;

        public Builder httpPort(int port) {
            this.port = port;
            return this;
        }

        public Builder httpsPort(int port) {
            this.ports = port;
            return this;
        }

        public Builder hostUrl(String url){
            this.hostUrl=url;
            return this;
        }

        public Builder isFixWhenException(boolean isFixWhenException) {
            this.isFixWhenException = isFixWhenException;
            return this;
        }

        public HttpRequestConfig build() {
            return new HttpRequestConfig(this);
        }
    }
}
