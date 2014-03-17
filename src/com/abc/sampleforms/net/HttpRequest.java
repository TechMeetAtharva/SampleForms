package com.abc.sampleforms.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpRequest {

	public static final String RESULT_SUBMIT_SUCCESS = "1";
	public static final String RESULT_NETWORK_FAIL = "2";

	public static final String POST_METHOD = "POST";
	public static final String GET_METHOD = "GET";

	InputStream is = null;
	JSONObject jObj = null;
	String json = "";
	Context context;

	// constructor
	public HttpRequest() {

	}

	// function get json from url
	// by making HTTP POST or GET mehtod
	public InputStream makeHttpRequest(String url, String method,
			List<NameValuePair> params, Context context) {

		// Making HTTP request
		try {
			this.context = context;
			// check for request method
			if (method.equals(POST_METHOD)) {
				// request method is POST
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				if (isOnline()) {
					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
				} else
					is = new ByteArrayInputStream(
							RESULT_NETWORK_FAIL.getBytes());

			} else if (method.equals(GET_METHOD)) {
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				if (isOnline()) {
					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
				} else {
					is = new ByteArrayInputStream(
							RESULT_NETWORK_FAIL.getBytes());
				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// try {
		// BufferedReader reader = new BufferedReader(new InputStreamReader(
		// is, "iso-8859-1"), 8);
		// StringBuilder sb = new StringBuilder();
		// String line = null;
		// while ((line = reader.readLine()) != null) {
		// sb.append(line + "\n");
		// }
		// is.close();
		// json = sb.toString();
		// } catch (Exception e) {
		// Log.e("Buffer Error", "Error converting result " + e.toString());
		// }
		//
		// // try parse the string to a JSON object
		// try {
		// jObj = new JSONObject(json);
		// } catch (JSONException e) {
		// Log.e("JSON Parser", "Error parsing data " + e.toString());
		// }
		//
		// // return JSON String
		// return jObj;
		return is;

	}

	private Boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected())
			return true;

		return false;
	}
}
