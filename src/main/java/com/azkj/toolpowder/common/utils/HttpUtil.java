package com.azkj.toolpowder.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {

	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final int CONNECT_TIME_OUT = 5000; //链接超时时间3秒

	private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT).build();

	private static SSLContext wx_ssl_context = null; //微信支付ssl证书

	static{
		Resource resource = new ClassPathResource("apiclient_cert.p12");
		try {
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			char[] keyPassword = ConfigUtil.getProperty("wx.mchid").toCharArray(); //证书密码
			keystore.load(resource.getInputStream(), keyPassword);
			wx_ssl_context = SSLContexts.custom().loadKeyMaterial(keystore, keyPassword).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static CloseableHttpResponse Post(String url, String outputEntity) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(new StringEntity(outputEntity, "UTF-8"));
			return HttpClients.custom().build().execute(httpPost);

	}


	public static String posts(String url, String s) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.setSSLSocketFactory(getSSLConnectionSocket())
					.build();
			httpPost.setEntity(new StringEntity(s, DEFAULT_CHARSET));
			response = httpClient.execute(httpPost);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}

	//获取ssl connection链接
	private static SSLConnectionSocketFactory getSSLConnectionSocket() {
		return new SSLConnectionSocketFactory(wx_ssl_context, new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
	}

	private static final String ENCODING = "UTF-8";


	public static String post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
}
