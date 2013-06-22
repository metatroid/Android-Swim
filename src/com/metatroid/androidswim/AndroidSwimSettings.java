package com.metatroid.androidswim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class AndroidSwimSettings extends Application {
	
	private static final DefaultHttpClient client = createClient();
	private static String username;
	private static String password;
	private final ASsettings mSettings = new ASsettings();
	protected static Context mContext;
	Cookie cookie;
	CookieStore store = new BasicCookieStore();
	List<Cookie> cookies = client.getCookieStore().getCookies();
	
	@Override
	public void onCreate(){
		super.onCreate();
		username = "";
		password = "";
		cookies = null;
		client.getCookieStore().clear();
		try {
        	File f = new File("/sdcard/AndroidSwimUsername");
        	if(!f.exists()){
				FileWriter fw = new FileWriter("/sdcard/AndroidSwimUsername");
				fw.write("");
				fw.flush();
				fw.close();
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			File f = new File("/sdcard/AndroidSwimPassword");
        	if(!f.exists()){
				FileWriter fw = new FileWriter("/sdcard/AndroidSwimPassword");
				fw.write("");
				fw.flush();
				fw.close();
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			File f = new File("/sdcard/AndroidSwimUserid");
        	if(!f.exists()){
				FileWriter fw = new FileWriter("/sdcard/AndroidSwimUserid");
				fw.write("");
				fw.flush();
				fw.close();
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String user = ASsettings.getUsername();
		String pass = ASsettings.getPassword();
		if(user.length() > 1){
			if(loginPref()){
				new MyLoginTask(user, pass).execute();
				if(loginNoticePref()){
					Toast.makeText(getApplicationContext(), "Attempting automatic log-in of "+user+" please wait...", Toast.LENGTH_LONG).show();
				}
			}
			
			
		}
		
		
	}
	
	public boolean getApiPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean api = prefs.getBoolean("apipref", false);
		return api;
	}
	
	private class MyLoginTask extends LoginTask {
    	public MyLoginTask(String username, String password) {
    		super(username, password, mSettings, mClient, getApplicationContext());
    	}
    	    	
    	@Override
    	protected void onPostExecute(Boolean success){
    		if(getpmPref()){
    			new CheckMessagesTask(getApplicationContext(), mClient).execute();
    		} else {
    			System.out.println("------------------------ PM SERVICE OFF");
    		}
    		String user = ASsettings.getUsername();
    		if(loginNoticePref()){
    			Toast.makeText(getApplicationContext(), user+" logged in", Toast.LENGTH_SHORT).show();
    		}
    	}
	}
	
	public static String getUsername(){
		return username;
	}
	
	public static void setUsername(String username){
		AndroidSwimSettings.username = username;
	}
	
	public static String getPassword(){
		return password;
	}
	
	public static void setPassword(String password){
		AndroidSwimSettings.password = password;
	}
	
	static DefaultHttpClient getClient(){
		return client;
	}
	
	private static DefaultHttpClient createClient(){
		BasicHttpParams params = new BasicHttpParams();
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
		schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
		DefaultHttpClient httpclient = new DefaultHttpClient(cm, params);
		httpclient.getCookieStore().getCookies();
		return httpclient;
	}
	
	
	private boolean getpmPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean pmstate = prefs.getBoolean("notificationpref", true);
		return pmstate;
	}
	private boolean loginPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean autologin = prefs.getBoolean("autologinpref", true);
		return autologin;
	}
	private boolean loginNoticePref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean autologinnotice = prefs.getBoolean("autologinnoticepref", true);
		return autologinnotice;
	}
	
}