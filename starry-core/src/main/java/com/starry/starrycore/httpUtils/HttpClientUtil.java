package com.starry.starrycore.httpUtils;

import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Http帮助类（非爬虫用）  本工具类是基于HttpClient
 * HttpURLConnection 封装层次太低，并且支持特性太少，不建议在项目中使用。
 * Java 9 中引入的 HttpClient，封装层次和支持特性都不错。但是因为 Java 版本的原因，用的不是很多。
 * 如果你不需要 HTTP/2特性，Apache HttpComponents HttpClient 是你的最佳选择，比如在服务器之间的 HTTP 调用。
 * 否则，请使用 OkHttp, 如 Android 开发
 */
public class HttpClientUtil {
    private HttpClientUtil httpClientUtil;
    private String url;
    private Map<String, String> headerMap;
    private Map<String, String> paramMap;
    StopWatch stopWatch = null;

    private HttpClientUtil() {
        // 统计耗时
        stopWatch = new StopWatch();
    }

    public static HttpClientUtil build() {
        return new HttpClientUtil();
    }

    public HttpClientUtil url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 添加请求头
     *
     * @param key   参数名
     * @param value 参数值
     * @return
     */
    public HttpClientUtil addHeader(String key, String value) {
        if (headerMap == null) {
            headerMap = new LinkedHashMap<>(16);
        }
        headerMap.put(key, value);
        return this;
    }

    /**
     * 添加请求参数
     *
     * @param key   参数名
     * @param value 参数值
     * @return
     */
    public HttpClientUtil addParam(String key, String value) {
        if (paramMap == null) {
            paramMap = new LinkedHashMap<>(16);
        }
        paramMap.put(key, value);
        return this;
    }

    /**
     * 添加请求参数
     *
     * @return
     */
    public HttpClientUtil post() {
        if (!StringUtils.hasLength(url)) {
            // throw new IllegalArgumentException();
            return null;
        }


        return this;
    }

    /**
     * 添加请求参数
     *
     * @return
     */
    public HttpClientUtil get() {

        return this;
    }

    public HttpClientUtil useGzip() {

         /*   webRequest.Headers.Add("Accept-Encoding", "gzip");
            webRequest.Headers.Add("Content-Encoding", "gzip");*/

        return this;
    }

    public String execute() {
        if (!StringUtils.hasLength(url)) {
            // throw new IllegalArgumentException();
            return null;
        }
        return null;
    }


    /**
     * http请求文件到流中
     *
     * @param url
     * @return
     */
    public static byte[] GetFileByHttp(String url) {
        byte[] result = null;
        /*HttpWebRequest webRequest = null;
        ServicePointManager.DefaultConnectionLimit = 200;
        try {
            webRequest = (HttpWebRequest) WebRequest.Create(url);
            webRequest.Method = "GET";
            webRequest.Timeout = 60000;
            webRequest.ServicePoint.Expect100Continue = false;

            // 响应流
            var webResponse = (HttpWebResponse) webRequest.GetResponse();
            var respStream = webResponse.GetResponseStream();  // 获取响应的字符串流
            result = respStream.StreamToByte();
        } catch (Exception ex) {
            throw new Exception("访问出错\r\n调用地址:" + url, ex);
        }*/

        return result;
    }

    /**
     * 文件上传到别的地方
     *
     * @param url
     * @param fileName  文件名
     * @param filebytes 文件字节
     * @return http响应字符串
     */
    public static String PostFile(String url, String fileName, byte[] filebytes) {
        /*var result = string.Empty;
        Stopwatch stopWatch = Stopwatch.StartNew();
        HttpWebRequest webRequest = null;
        ServicePointManager.DefaultConnectionLimit = 200;
        try {
            webRequest = (HttpWebRequest) WebRequest.Create(url);
            webRequest.Method = "Post";
            CookieContainer cookieContainer = new CookieContainer();
            webRequest.CookieContainer = cookieContainer;
            webRequest.AllowAutoRedirect = true;
            string boundary = DateTime.Now.Ticks.ToString("X"); // 随机分隔线
            webRequest.ContentType = "multipart/form-data;charset=utf-8;boundary=" + boundary;
            webRequest.Timeout = 5 * 60000;
            webRequest.ServicePoint.Expect100Continue = false;
            byte[] startBoundaryBytes = Encoding.UTF8.GetBytes($"--{boundary}\r\n");
            byte[] endBoundaryBytes = Encoding.UTF8.GetBytes($"\r\n--{boundary}--\r\n");

            // 请求头部信息
            StringBuilder boundaryBlock = new StringBuilder();
                *//*
                StringBuilder boundaryBlock = new StringBuilder();
                boundaryBlock.Append($"--{timpstamp}\r\n");
                boundaryBlock.Append($"Content-Disposition: form-data; name=\"meta\";\r\n");
                boundaryBlock.Append($"Content-Type: application/json\r\n");
                boundaryBlock.Append($"\r\n");
                boundaryBlock.Append($"{reqjson.meta.PackJson()}\r\n");
                boundaryBlock.Append($"--{timpstamp}\r\n");
                boundaryBlock.Append($"Content-Disposition: form-data; name=\"file\"; filename=\"{fileName}\";\r\n");
                boundaryBlock.Append($"Content-Type: image/jpg\r\n");
                boundaryBlock.Append($"\r\n");
                *//*
            boundaryBlock.Append($"Content-Disposition: form-data; name=\"media\"; filename=\"{fileName}\";\r\n");
            boundaryBlock.Append($"Content-Type: application/octet-stream\r\n");
            boundaryBlock.Append($"\r\n");

            // 拿到http请求流，写入请求
            var requestStream = webRequest.GetRequestStream();
            requestStream.Write(startBoundaryBytes, 0, startBoundaryBytes.Length);
            var p1 = Encoding.UTF8.GetBytes(boundaryBlock.ToString());
            requestStream.Write(p1, 0, p1.Length);
            requestStream.Write(filebytes, 0, filebytes.Length);
            requestStream.Write(endBoundaryBytes, 0, endBoundaryBytes.Length);
            requestStream.Close();

            // 响应流
            var webResponse = (HttpWebResponse) webRequest.GetResponse();
            result = GetResponseStreamToStr(webResponse); // 读取Response中的内容
        } catch (WebException err) {
            stopWatch.Stop();
            var elapsed = stopWatch.ElapsedMilliseconds;
            switch (err.Status) {
                case WebExceptionStatus.Timeout:
                    result = "请求超时,耗时:" + elapsed + "，WebException:" + err;
                    break;
                default:
                    result = "耗时:" + elapsed + "，WebException:" + err;
                    break;
            }

            if (webRequest != null) {
                webRequest.Abort();
                webRequest = null;
                System.GC.Collect();
            }

            throw new Exception(result, err);
        } catch (Exception ex) {
            stopWatch.Stop();
            throw new Exception("访问出错\r\n调用地址:" + url, ex);
        }

        stopWatch.Stop();*/
        return "";
    }
}