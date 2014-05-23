package com.rop.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HttpResponse {
    private HttpURLConnection con;
    private InputStream is;
    private BufferedReader reader;

    public HttpResponse(HttpURLConnection con) throws IOException {
        if (con == null) {
            throw new IOException("Http url connection is null");
        }
        this.con = con;
        if (null == (this.is = con.getErrorStream())) {
            this.is = con.getInputStream();
        }
        if ((this.is != null) && ("gzip".equals(con.getContentEncoding()))) {
            this.is = new GZIPInputStream(this.is);
        }
        this.reader = new BufferedReader(new InputStreamReader(this.is, "UTF-8"));
    }

    public String getResponseHeader(String name) {
        return this.con.getHeaderField(name);
    }

    public Map<String, List<String>> getResponseHeaderFields() {
        return this.con.getHeaderFields();
    }

    private void close() {
        if (this.reader != null)
            try {
                this.reader.close();
            } catch (IOException e) {
            }
        if (this.is != null)
            try {
                this.is.close();
            } catch (IOException e) {
            }
        if (this.con != null)
            this.con.disconnect();
    }

    public String getMsg() throws IOException {
        StringBuffer buffer=new StringBuffer(3000);
        String line=this.reader.readLine();
        while(line!=null){
            buffer.append(line);
            line=this.reader.readLine();
        }
        this.close();
        return buffer.toString();
    }



}