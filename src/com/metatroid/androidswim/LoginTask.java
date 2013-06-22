package com.metatroid.androidswim;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

public class LoginTask extends AsyncTask<Void, Void, Boolean> {
	
	private String mUsername;
	private String mPassword;
	private ASsettings mSettings;
	static DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	private Context mContext;

	LoginTask(String username, String password, ASsettings settings,
			DefaultHttpClient client, Context context) {
		mUsername = username;
		mPassword = password;
		mSettings = settings;
		mClient = client;
		mContext = context;
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		return doLogin(mUsername, mPassword, mSettings, mClient, mContext);
	}
	
	private Boolean doLogin(String username, String password, ASsettings settings, DefaultHttpClient client, Context context) {
		String status = "";
    	String userError = "Error logging in. Please try again.";
    	HttpEntity entity = null;
    	
		try {
			HttpGet httpget = new HttpGet("http://login.adultswim.com/services/aswim/flow/login");
			client.getCookieStore().getCookies();
			HttpResponse response1 = client.execute(httpget);
			System.out.println("!!!!!!!!!!!!!!SENDING LOGIN DATA!!!!!!!!!!!!!!");
	        HttpEntity entity1 = response1.getEntity();
	        if (entity1 != null){
	        	entity1.consumeContent();
	        }
	                
			
    	} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("loginId", username.toString()));
			nvps.add(new BasicNameValuePair("password", password.toString()));
			nvps.add(new BasicNameValuePair("_eventId_submit", "Submit"));
			
			HttpPost httppost = new HttpPost("http://login.adultswim.com/services/aswim/flow/login?_flowExecutionKey=e1s1");
	        httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	        httppost.setHeader("User-Agent", "(Android Swim v. 0.2)/metatroid");
	        HttpParams params = httppost.getParams();
	        HttpConnectionParams.setConnectionTimeout(params, 45000);
	        HttpConnectionParams.setSoTimeout(params, 45000);
	        
            // Perform the HTTP POST request
        	HttpResponse response = client.execute(httppost);
        	status = response.getStatusLine().toString();
        	if (!status.contains("OK")) {
        		throw new HttpException(status);
        	}
        	
                    	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();

			nvps2.add(new BasicNameValuePair("_eventId_submit", "Submit"));
			
			HttpPost httppost2 = new HttpPost("http://login.adultswim.com/services/aswim/flow/login_flowExecutionKey=e1s2");
	        httppost2.setEntity(new UrlEncodedFormEntity(nvps2, HTTP.UTF_8));
	        httppost2.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
	        HttpParams params2 = httppost2.getParams();
	        HttpConnectionParams.setConnectionTimeout(params2, 45000);
	        HttpConnectionParams.setSoTimeout(params2, 45000);
	        
	        // Perform the HTTP POST request
	    	HttpResponse response2 = client.execute(httppost2);
	    	
	    	
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//get actual board cookie
			HttpGet httpget = new HttpGet("http://boards.adultswim.com");
			HttpResponse response;
			httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			try {
				response = mClient.execute(httpget);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			if (ASsettings.getUserId().length() < 1) {
                new getUserId().execute();
            } else {
                return true;
            }
		
		return true;
	}
	
	private class getUserId extends AsyncTask<String, Integer, String>  {

		
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			HttpGet httpget = new HttpGet("http://boards.adultswim.com/restapi/vc/authentication/sessions/current/user/id");
			HttpResponse response;
			httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			String result = "";
			String userid ="";
			try {
				response = mClient.execute(httpget);
				HttpEntity entity1 = response.getEntity();
				try{
				InputStream in = entity1.getContent();
				result = IOUtils.toString(in);
			} catch(Exception ex){
				result = "error";
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(result.contains("status=\"success\"")){
				userid = result.replaceAll("(?s)(?:.*)int\">", "").replaceAll("</val[^v]+$", "");
				System.out.println("---------------user id found - "+userid);
			} else {
				System.out.println(result);
			}
			
			return userid;
		}
		protected void onPostExecute(String result){
			mSettings.setUserId(result);
		}
		
	}
	
	public boolean getApiPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		boolean api = prefs.getBoolean("apipref", false);
		return api;
	}
	
}