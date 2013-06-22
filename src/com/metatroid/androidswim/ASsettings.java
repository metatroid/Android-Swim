package com.metatroid.androidswim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.cookie.Cookie;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ASsettings {
	private static String username;
	private static String password;
	private static String userid;
	
	Cookie ascookie;
	
	public static String getUsername(){
		String name ="";
		try {
			BufferedReader in = new BufferedReader(new FileReader("/sdcard/AndroidSwimUsername"));
			String line = null;
			while (( line = in.readLine()) != null){
				name = line;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			name = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			name = "";
		}
		
		
		return name;
	}
	
	public static void setUsername(String username){
		ASsettings.username = username;
		try {
			FileWriter fw = new FileWriter("/sdcard/AndroidSwimUsername");
			fw.write(username);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getPassword(){
		String password ="";
		try {
			BufferedReader in = new BufferedReader(new FileReader("/sdcard/AndroidSwimPassword"));
			String line = null;
			while (( line = in.readLine()) != null){
				password = line;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			password = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			password = "";
		}
		
		
		return password;
	}
	
	public static void setPassword(String password){
		ASsettings.password = password;
		try {
			FileWriter fw = new FileWriter("/sdcard/AndroidSwimPassword");
			fw.write(password);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setUserId(String userid){
		ASsettings.userid = userid;
		try {
			FileWriter fw = new FileWriter("/sdcard/AndroidSwimUserid");
			fw.write(userid);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String getUserId(){
		String userid ="";
		try {
			BufferedReader in = new BufferedReader(new FileReader("/sdcard/AndroidSwimUserid"));
			String line = null;
			while (( line = in.readLine()) != null){
				userid = line;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			userid = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			userid = "";
		}
		
		
		return userid;
	}
	
	
	
	boolean isLoggedIn() {
		return username != null;
	}

	void setASCookie(Cookie ascookie){
		this.ascookie = ascookie;
	}
	public static Document XMLfromString(String xml){
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
		        is.setCharacterStream(new StringReader(xml));
		        doc = db.parse(is);
			} catch (ParserConfigurationException e) {
				System.out.println("XML parse error: " + e.getMessage());
				return null;
			} catch (SAXException e) {
				System.out.println("Wrong XML file structure: " + e.getMessage());
	            return null;
			} catch (IOException e) {
				System.out.println("I/O exeption: " + e.getMessage());
				return null;
			}
	        return doc;
		}
	
}