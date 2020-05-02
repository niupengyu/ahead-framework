package com.github.niupengyu.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UrlUtil {

	public static String ur = "http://localhost:8080/flyman/";

	public String url;

	private List<BasicNameValuePair> params;

	public UrlUtil(String url) {
		this.url = url;
		params = new ArrayList<BasicNameValuePair>();
	}

	public void put(final String name, final String value) {
		BasicNameValuePair e = new BasicNameValuePair(name, value);
		params.add(e);
	}

	public void putmap(final String name, final String value) {
		BasicNameValuePair e = new BasicNameValuePair(name, value);
		params.add(e);
	}


	public String toString() {
		try {
			String str = genHttpUrl(url, params);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	@Deprecated
	public static String genHttpUrl(String uri, Map<String, String> params)
			throws Exception {
		String httpurl = uri + paramString(params);
		return httpurl;
	}

	@Deprecated
	private static String paramString(Map<String, String> params)
			throws UnsupportedEncodingException {
		String paramStr = "";
		if (params == null) {
			params = new HashMap<String, String>();
		}
		/** 迭代请求参数集合 **/
		for (Entry<String, String> entry : params.entrySet()) {
			Object key = entry.getKey();
			String val = entry.getValue();
			paramStr += paramStr = "&" + key + "="
					+ URLEncoder.encode(val, "utf-8");
		}
		if (!paramStr.equals("")) {
			paramStr = paramStr.replaceFirst("&", "?");
		}
		return paramStr;
	}

	public static String genHttpUrl(String url, List<BasicNameValuePair> params)
			throws Exception {
		String paramStr = "";
		if (params == null) {
			params = new ArrayList<BasicNameValuePair>();
		}
		/** 迭代请求参数集合 **/
		for (BasicNameValuePair obj : params) {
			paramStr += paramStr = "&" + obj.getName() + "="
					+ URLEncoder.encode(obj.getValue(), "utf-8");
		}
		if (!paramStr.equals("")) {
			paramStr = paramStr.replaceFirst("&", "?");
			url += paramStr;
		}
		return url;
	}

	private class BasicNameValuePair {

		public BasicNameValuePair(String name, String value) {
			this.name = name;
			this.value = value;
		}

		private String name;

		private String value;

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

	}

	public void clear() {
		params.clear();
		params = new ArrayList<BasicNameValuePair>();
	}

	public void clearAll() {
		params.clear();
		params = new ArrayList<BasicNameValuePair>();
		url = null;
	}

	public String get() throws Exception {
		return genHttpUrl(url, params);
	}

	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @param urlAll
	 *            :请求接口
	 * @param charset
	 *            :字符编码
	 * @return 返回json结果
	 */
	public static String get(String urlAll, String charset,
			Map<String, String> map) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		// String userAgent =
		// "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";//模拟浏览器
		try {
			URL url = new URL(urlAll);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			if (!StringUtil.mapIsNull(map)) {
				for (Entry<String, String> entry : map.entrySet()) {
					connection.setRequestProperty(entry.getKey(),
							entry.getValue());
				}
			}
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, charset));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param httpUrl
	 *            :请求接口
	 * @param charset
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String charset) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// connection.setRequestProperty("apikey", key);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, charset));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String xmlPost(String urlStr, String xmlInfo) {
		URLConnection con = null;
		OutputStreamWriter out = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlStr);
			con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");
			con.setRequestProperty("Charsert", "UTF-8");
			out = new OutputStreamWriter(con.getOutputStream());
//			LogUtil.logger.info("urlStr=" + urlStr);
//			LogUtil.logger.info("xmlInfo=" + xmlInfo);
//			LogUtil.logger.info(xmlInfo);
//			LogUtil.logger.info(new String(xmlInfo.getBytes(), "UTF-8"));
			out.write(new String(xmlInfo.getBytes(), "UTF-8"));
			out.flush();
			out.close();
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			FileUtil.close(out);
			FileUtil.close(br);
			out=null;
			br=null;
			con=UrlUtil.closeURLConnection(con);
		}
		return sb.toString();
	}

	public static URLConnection closeURLConnection(URLConnection urlConnection) {
		try {
			if (urlConnection != null) {
				HttpURLConnection hul = (HttpURLConnection) urlConnection;
				hul.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			urlConnection=null;
		}
		return urlConnection;
	}

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("<videoSend>");
		sb.append("    <header>");
		sb.append("        <sid>1</sid>");
		sb.append("        <type>service</type>");
		sb.append("    </header>");
		sb.append("    <service name=\"videoSend\">");
		sb.append("        <fromNum>0000021000011001</fromNum>");
		sb.append("           <toNum>33647405</toNum>");
		sb.append("        <videoPath>mnt/localhost/resources/80009.mov</videoPath>");
		sb.append("        <chargeNumber>我不急史蒂夫</chargeNumber>");
		sb.append("    </service>");
		sb.append("</videoSend>");
		UrlUtil.xmlPost(
				"http://localhost:8080/point/mobile/order/wxsendmessage-s",
				sb.toString());
	}
}
