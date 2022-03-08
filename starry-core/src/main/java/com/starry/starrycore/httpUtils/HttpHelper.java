package com.starry.starrycore.httpUtils;

public class HttpHelper {
    /**
     * 检测是否https
     *
     * @param url URL
     * @return 是否https
     */
    public static boolean isHttps(String url) {
        return url.toLowerCase().startsWith("https:");
    }

    /**
     * 检测是否http
     *
     * @param url URL
     * @return 是否http
     * @since 5.3.8
     */
    public static boolean isHttp(String url) {
        return url.toLowerCase().startsWith("http:");
    }
}
