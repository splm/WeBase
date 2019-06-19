package me.splm.app.core.component.http;

import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class AsyncHttpsClient {
    private static DefaultHttpClient httpClient;
    private static final String VERSION = "1.1";
    /** http请求最大并发连接数 */
    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    /** 超时时间，默认10秒 */
    private static final int DEFAULT_SOCKET_TIMEOUT = 30 * 1000;
    /** 默认的套接字缓冲区大小 */
    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    private static int maxConnections = DEFAULT_MAX_CONNECTIONS;
    private static int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
}
