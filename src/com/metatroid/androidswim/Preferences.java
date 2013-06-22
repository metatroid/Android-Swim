package com.metatroid.androidswim;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;
 
public class Preferences extends PreferenceActivity {
	ArrayList<HashMap<String,String>> FLOATERS = new ArrayList<HashMap<String,String>>();
	public static final String floated = "boardfloatpref";
	String previous;
	Intent starterintent;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                starterintent = getIntent();
                previous = starterintent.getData().toString();
                addPreferencesFromResource(R.layout.preferences);
                final DefaultHttpClient mClient = AndroidSwimSettings.getClient();
                
                /*
                Preference fpref = (Preference) findPreference("floatprefs");
                fpref
                                .setOnPreferenceClickListener(new OnPreferenceClickListener() {

									@Override
									public boolean onPreferenceClick(
											Preference arg0) {
										// TODO Auto-generated method stub
										
										Intent floatManage = new Intent(getApplicationContext(), FloatManager.class);
								    	startActivity(floatManage);
										
										
										return false;
									}
 
                                        
                                        
 
                                });
                */
                SharedPreferences fsettings = getSharedPreferences(floated, 0);
            	Map<String, ?> floats = fsettings.getAll();
        	    if(!floats.isEmpty()){
            		for(String s : floats.keySet()){
        	    		HashMap<String,String> temp = new HashMap<String,String>();
        	    		temp.put("board", s);
        	    		temp.put("link", floats.get(s).toString());
        	    		FLOATERS.add(temp);
        	    	}
        	    }
        	    
        	   
        	   int z = FLOATERS.size();
        	    if(FLOATERS.size() < 10){
        	    	for(int x = 0; x < (10 - z); x++){
        	    		HashMap<String,String> temp = new HashMap<String,String>();
        	    		temp.put("board", "none");
        	    		temp.put("link", "none");
        	    		FLOATERS.add(temp);
        	    	}
        	    }
                Preference f1pref = (Preference) findPreference("floatone");
                f1pref.setSummary(FLOATERS.get(0).get("board"));
                ((ListPreference) f1pref).setValue("");
                Preference f2pref = (Preference) findPreference("floattwo");
                f2pref.setSummary(FLOATERS.get(1).get("board"));
                ((ListPreference) f2pref).setValue("");
                Preference f3pref = (Preference) findPreference("floatthree");
                f3pref.setSummary(FLOATERS.get(2).get("board"));
                ((ListPreference) f3pref).setValue("");
                Preference f4pref = (Preference) findPreference("floatfour");
                f4pref.setSummary(FLOATERS.get(3).get("board"));
                ((ListPreference) f4pref).setValue("");
                Preference f5pref = (Preference) findPreference("floatfive");
                f5pref.setSummary(FLOATERS.get(4).get("board"));
                ((ListPreference) f5pref).setValue("");
                Preference f6pref = (Preference) findPreference("floatsix");
                f6pref.setSummary(FLOATERS.get(5).get("board"));
                ((ListPreference) f6pref).setValue("");
                Preference f7pref = (Preference) findPreference("floatseven");
                f7pref.setSummary(FLOATERS.get(6).get("board"));
                ((ListPreference) f7pref).setValue("");
                Preference f8pref = (Preference) findPreference("floateight");
                f8pref.setSummary(FLOATERS.get(7).get("board"));
                ((ListPreference) f8pref).setValue("");
                Preference f9pref = (Preference) findPreference("floatnine");
                f9pref.setSummary(FLOATERS.get(8).get("board"));
                ((ListPreference) f9pref).setValue("");
                Preference f10pref = (Preference) findPreference("floatten");
                f10pref.setSummary(FLOATERS.get(9).get("board"));
                ((ListPreference) f10pref).setValue("");
                
                //float 1
                f1pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(0).get("board"));
						
						if(!newTitle.equals("none ")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
		    		      	
		    		      	
						return false;
					}
                }
                	
                );
                
                //float 2
                f2pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(1).get("board"));
						
						if(!newTitle.equals("none ")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
						return false;
					}
                }
                	
                );
                
                //float3
                f3pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(2).get("board"));
						
						if(!newTitle.equals("none ")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
						return false;
					}
                }
                	
                );
                
                //float4
                f4pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(3).get("board"));
						
						if(!newTitle.equals("none ")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
						return false;
					}
                }
                	
                );
                
                //float5
                f5pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(4).get("board"));
						
						if(!newTitle.equals("none ")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
						return false;
					}
                }
                	
                );
                
                //float6
                f6pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(5).get("board"));
						
						if(!newTitle.equals("none ")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
						return false;
					}
                }
                	
                );
                
                //float7
                f7pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(6).get("board"));
						
						if(!newTitle.equals("none ")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
						return false;
					}
                }
                	
                );
                
                //float8
                f8pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(7).get("board"));
						
						if(!newTitle.equals("none ")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
						return false;
					}
                }
                	
                );
                
                //float9
                f9pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(8).get("board"));
						
						if(!newTitle.equals("none ")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
						return false;
					}
                }
                	
                );
                
                //float10
                f10pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference pref,
							Object selected) {
						// TODO Auto-generated method stub
						String[] section = selected.toString().split("cut_here");
						String newTitle = section[0].toString().trim();
						String newValue = section[1].toString().trim();
						
						SharedPreferences settings = getSharedPreferences(floated, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.remove(FLOATERS.get(9).get("board"));
						
						if(!newTitle.equals("none")){
							
								editor.putString(newTitle, newValue);
							
						}
						editor.commit();
						FLOATERS.clear();
						SharedPreferences fsettings = getSharedPreferences(floated, 0);
				    	Map<String, ?> floats = fsettings.getAll();
					    if(!floats.isEmpty()){
				    		for(String s : floats.keySet()){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", s);
					    		temp.put("link", floats.get(s).toString());
					    		FLOATERS.add(temp);
					    	}
					    }
					   int s = FLOATERS.size();
					    if(FLOATERS.size() < 10){
					    	for(int x = 0; x < (10 - s); x++){
					    		HashMap<String,String> temp = new HashMap<String,String>();
					    		temp.put("board", "none");
					    		temp.put("link", "none");
					    		FLOATERS.add(temp);
					    	}
					    }
						
		    		    Preference f1pref = (Preference) findPreference("floatone");
		    		    Preference f2pref = (Preference) findPreference("floattwo");
		    		    Preference f3pref = (Preference) findPreference("floatthree");
		    		    Preference f4pref = (Preference) findPreference("floatfour");
		    		    Preference f5pref = (Preference) findPreference("floatfive");
		    		    Preference f6pref = (Preference) findPreference("floatsix");
		    		    Preference f7pref = (Preference) findPreference("floatseven");
		    		    Preference f8pref = (Preference) findPreference("floateight");
		    		    Preference f9pref = (Preference) findPreference("floatnine");
		    		    Preference f10pref = (Preference) findPreference("floatten");
		    		      	f1pref.setSummary(FLOATERS.get(0).get("board"));
		    		      	f2pref.setSummary(FLOATERS.get(1).get("board"));
		    		      	f3pref.setSummary(FLOATERS.get(2).get("board"));
		    		      	f4pref.setSummary(FLOATERS.get(3).get("board"));
		    		      	f5pref.setSummary(FLOATERS.get(4).get("board"));
		    		      	f6pref.setSummary(FLOATERS.get(5).get("board"));
		    		      	f7pref.setSummary(FLOATERS.get(6).get("board"));
		    		      	f8pref.setSummary(FLOATERS.get(7).get("board"));
		    		      	f9pref.setSummary(FLOATERS.get(8).get("board"));
		    		      	f10pref.setSummary(FLOATERS.get(9).get("board"));
						return false;
					}
                }
                	
                );
               
                Preference pmpref = (Preference) findPreference("notificationpref");
                pmpref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						if(newValue.toString().equals("true")){
							new CheckMessagesTask(getApplicationContext(), mClient).execute();
						} else {
							Toast.makeText(getApplicationContext(), "Service will end after completion of currently running instance", Toast.LENGTH_LONG).show();
						}
						
						return true;
					}
                	
                });
				
                
        }
}