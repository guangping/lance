package com.rop.http;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class HttpClient {
    private static boolean isJDK14orEarlier = false;
    private Map<String, String> params;
    private Configuration conf;

    public HttpClient(Configuration conf, Map<String, String> params) {
        if ((conf == null) || (params == null)) {
            throw new RuntimeException("conf and params is must not null");
        }
        this.conf = conf;
        this.params = params;
    }

    public HttpResponse post() throws Exception {
        int retry = this.conf.getHttpConnectRetryCount() + 1;
        HttpResponse resp = null;
        for (int retriedCount = 1; retriedCount <= retry; retriedCount++) {
            int responseCode = -1;
            try {
                HttpURLConnection con = null;
                OutputStream out = null;
                try {
                    con = getConnection(this.conf.getConnectUrl(), this.conf.getHttpConnectionTimeout(), this.conf.getHttpReadTimeout());
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    setHeaders(con, this.conf.getRequestHeader());
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    String postParam = encodeParameters(this.params);
                    byte[] bytes = postParam.getBytes("UTF-8");
                    con.setRequestProperty("Content-Length", Integer.toString(bytes.length));

                    out = con.getOutputStream();
                    out.write(bytes);
                    out.flush();
                    responseCode = con.getResponseCode();
                    if (200 == responseCode) {
                        StringBuilder respHeader;
                        resp = new HttpResponse(con);
                        return resp;
                    }
                    if (400 == responseCode) {
                        String errmsg = con.getHeaderField("errmsg");
                        if ((errmsg != null) && ((errmsg.indexOf("max connect") > -1) || (errmsg.indexOf("cache") > -1))) {
                            if (retriedCount == this.conf.getHttpConnectRetryCount()) {
                                throw new Exception("Server response err msg:" + errmsg);
                            }
                            try {
                                Thread.sleep(retriedCount * this.conf.getHttpConnectRetryInterval() * 1000);
                            } catch (InterruptedException e) {
                            }

                            try {
                                if (out != null)
                                    out.close();
                            } catch (Exception ignore) {
                            }
                        } else {
                            throw new Exception("Server response err msg:" + errmsg);
                        }
                    } else if (403 == responseCode) {
                        String errmsg = con.getHeaderField("errmsg");
                        if (retriedCount == this.conf.getHttpConnectRetryCount()) {
                            throw new Exception("Server response err msg:" + errmsg);
                        }
                        try {
                            Thread.sleep(retriedCount * this.conf.getHttpConnectRetryInterval() * 1000);
                        } catch (InterruptedException e) {
                        }

                        try {
                            if (out != null)
                                out.close();
                        } catch (Exception ignore) {
                        }
                    } else {
                        String errmsg = con.getHeaderField("errmsg");

                        if ((errmsg != null) && ((errmsg.indexOf("max connect") > -1) || (errmsg.indexOf("cache") > -1))) {
                            if (retriedCount == this.conf.getHttpConnectRetryCount()) {
                                throw new Exception("Server response err msg:" + errmsg);
                            }
                            try {
                                Thread.sleep(retriedCount * this.conf.getHttpConnectRetryInterval() * 1000);
                            } catch (InterruptedException e) {
                            }

                            try {
                                if (out != null)
                                    out.close();
                            } catch (Exception ignore) {
                            }
                        } else {
                            throw new Exception("Server response err msg:" + errmsg);
                        }
                    }
                } finally {
                    try {
                        if (out != null)
                            out.close();
                    } catch (Exception ignore) {
                    }
                }
            } catch (IOException ioe) {
                if (retriedCount == this.conf.getHttpConnectRetryCount()) {
                    throw new Exception(ioe.getMessage(), ioe);
                }
                try {
                    Thread.sleep(retriedCount * this.conf.getHttpConnectRetryInterval() * 1000);
                } catch (InterruptedException ignore) {
                }
            }
        }
        return resp;
    }

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException neverHappen) {
        }
        throw new AssertionError("will never happen");
    }

    private String encodeParameters(Map<String, String> params) {
        if ((null == params) || ((params != null) && (params.size() == 0))) {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        Iterator it = params.entrySet().iterator();
        int j = 0;
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            if (j != 0) {
                buf.append("&");
            }
            buf.append(encode((String) e.getKey())).append("=").append(encode((String) e.getValue()));

            j++;
        }
        return buf.toString();
    }

    private void setHeaders(HttpURLConnection connection, Map<String, String> reqHeader) {
        if ((reqHeader != null) && (reqHeader.size() > 0)) {
            Iterator it = reqHeader.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                connection.addRequestProperty((String) e.getKey(), (String) e.getValue());
            }
        }
    }

    private HttpURLConnection getConnection(String url, int connTimeout, int readTimeout) throws IOException {
        HttpURLConnection con = null;
        con = (HttpURLConnection) new URL(url).openConnection();
        if (connTimeout > 0) {
            if (!isJDK14orEarlier)
                con.setConnectTimeout(connTimeout * 1000);
            else {
                System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(connTimeout * 1000));
            }
        }
        if (readTimeout > 0) {
            if (!isJDK14orEarlier)
                con.setReadTimeout(readTimeout * 1000);
            else {
                System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(readTimeout * 1000));
            }
        }
        con.setInstanceFollowRedirects(false);
        return con;
    }

    static {
        try {
            String versionStr = System.getProperty("java.specification.version");
            if (versionStr != null)
                isJDK14orEarlier = 1.5D > Double.parseDouble(versionStr);
        } catch (Exception ignore) {
            isJDK14orEarlier = false;
        }
    }
}