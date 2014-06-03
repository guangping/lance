package com.ztesoft.inf.communication.client;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.config.Constants.HTTP_CONFIG;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;

import java.io.InputStream;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class HttpClientInvoker extends Invoker {

	private static DefaultHttpClient httpclient = null;
	protected Logger logger = Logger.getLogger(HttpClientInvoker.class);

	@Override
	public Object invoke(InvokeContext context) throws Exception {
		Object result = null;
		context.setEndpoint(endpoint);
		String url = context.getEndpoint();
		context.setRequestTime(DateUtil.currentTime());
		String reqString = generateRequestString(context);
		context.setRequestString(reqString);
		logger.debug("====>" + reqString);
		Map params = (Map) context.getParameters();
		String methodType = StringUtils.getStrValue(params,HTTP_CONFIG.METHOD_TYPE, HTTP_CONFIG.METHOD_POST);
		// 1.客户端基本设置
		DefaultHttpClient httpClient = getHttpClient(context);
		HttpResponse httpResponse = null;
		HttpRequestBase uriRequest = null;

		if (HTTP_CONFIG.METHOD_GET.equals(methodType)) {

			url = context.getEndpoint() + "?" + reqString;
			uriRequest = new HttpGet(url);

		} else if (HTTP_CONFIG.METHOD_POST.equals(methodType)) {

			uriRequest = new HttpPost(url);
			HttpEntity entity = new StringEntity(context.getRequestString(),
					"UTF-8");
			((HttpPost) uriRequest).setEntity(entity);
		} else {
			new IllegalArgumentException("mothed unsupport");
		}

		try {
			httpResponse = httpClient.execute(uriRequest);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK
					&& statusCode != HttpStatus.SC_NO_CONTENT) {
				throw new Exception("HttpStatusCode:" + statusCode);
			} else if (statusCode == HttpStatus.SC_REQUEST_TIMEOUT) {
				throw new Exception("网络连接超时");
			}

			HttpEntity entity2 = httpResponse.getEntity();
			if (entity2 != null) {
				InputStream instream = entity2.getContent();
				String rspStr = StringUtils.convertStreamToString(instream);
				logger.debug("rspStr====>" + rspStr);
				context.setResponeString(rspStr);
				result = dealResult(context);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			uriRequest.releaseConnection();
		}
		return result;
	}

	public static DefaultHttpClient getHttpClient(InvokeContext context)
			throws Exception {

		if (context.getEndpoint().contains("https")
				|| context.getEndpoint().contains("HTTPS")) {
			return getHttpsClient();
		} else {
			return getHttpClientDefault();
		}
	}

	public static DefaultHttpClient getHttpClientDefault() {
		if (null != httpclient) {
			return httpclient;
		}
		int timeoutConnection = 23 * 1000;
		int timeoutSocket = 28 * 1000;
		int BUFFER_SIZE = 8192 * 20;

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpConnectionParams.setSocketBufferSize(httpParameters, BUFFER_SIZE);

		HttpClientParams.setRedirecting(httpParameters, true);
		HttpClientParams.setCookiePolicy(httpParameters,
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpProtocolParams.setUserAgent(httpParameters, "apache.http.client");
		httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager(),
				httpParameters);
		return httpclient;
	}

	public static DefaultHttpClient getHttpsClient() throws Exception {

		// 创建TrustManager

		X509TrustManager xtm = new X509TrustManager() {

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {

				return null;

			}

		};

		// 这个好像是HOST验证

		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {

			public boolean verify(String arg0, SSLSession arg1) {

				return true;

			}

			public void verify(String arg0, SSLSocket arg1) throws IOException {
			}

			public void verify(String arg0, String[] arg1, String[] arg2)
					throws SSLException {
			}

			public void verify(String arg0, X509Certificate arg1)
					throws SSLException {
			}

		};

		// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext

		SSLContext ctx = SSLContext.getInstance("TLS");

		// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用

		ctx.init(null, new TrustManager[] { xtm }, null);

		// 创建SSLSocketFactory

		SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

		socketFactory.setHostnameVerifier(hostnameVerifier);

		// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
		httpclient = new DefaultHttpClient();
		httpclient.getConnectionManager().getSchemeRegistry()
				.register(new Scheme("https", socketFactory, 443));

		return httpclient;

	}

}