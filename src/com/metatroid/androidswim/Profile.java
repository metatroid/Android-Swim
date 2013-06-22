package com.metatroid.androidswim;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.task.AsyncTaskEx;
import com.metatroid.androidswim.ThreadListing.SpecialAdapter;

public class Profile extends Activity {
	public static Profile ACTIVE_INSTANCE;
	private static final int LOGIN_DIALOG = 0;
	private static final int INDEX = 1;
	private static final int DIALOG_LOGGING_IN = 2;
	public static final String floated = "boardfloatpref";
	private final ASsettings mSettings = new ASsettings();
	static DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	final ArrayList<HashMap<String,String>> PROFILE = new ArrayList<HashMap<String,String>>();
	
	//String title ="";
	Intent starterintent;
	String thread;
	String page;
	//ProgressBar progressBar;
	SpecialAdapter adapter;
	SimpleAdapter adaptr;
	ArrayList<HashMap<String,String>> FLOATERS = new ArrayList<HashMap<String,String>>();
	ActionItem first;
	ActionItem second;
	ActionItem third;
	ActionItem fourth;
	ActionItem fifth;
	ActionItem sixth;
	ActionItem seventh;
	ActionItem eigth;
	ActionItem ninth;
	ActionItem tenth;
	ActionItem eleventh;
	ActionItem twelfth;
	ActionItem sTracker;
	ActionItem subV;
	ActionItem frnd;
	ActionItem home;
	String postCount;
	String kudos;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		ACTIVE_INSTANCE = this;
		starterintent = getIntent();
		
		page = starterintent.getData().toString();
		
		setContentView(R.layout.loading);
		home = new ActionItem();
		home.setTitle("Home");
		home.setIcon(getResources().getDrawable(R.drawable.folder));
		home.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				goHome();
			}
		});
		first = new ActionItem();
		first.setTitle("Index");
		first.setIcon(getResources().getDrawable(R.drawable.folder));
		first.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				index();
			}
		});
		second = new ActionItem();
		second.setTitle("Inbox");
		second.setIcon(getResources().getDrawable(R.drawable.folder));
		second.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				inbox();
			}
		});
		sTracker = new ActionItem();
		sTracker.setTitle("Tracker");
		sTracker.setIcon(getResources().getDrawable(R.drawable.folder));
		sTracker.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				selftracker();
			}
		});
		subV = new ActionItem();
		subV.setTitle("Subscriptions");
		subV.setIcon(getResources().getDrawable(R.drawable.folder));
		subV.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				subscriptionViewer();
			}
		});
		frnd = new ActionItem();
		frnd.setTitle("Friends List");
		frnd.setIcon(getResources().getDrawable(R.drawable.folder));
		frnd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent friendActivity = new Intent(getApplicationContext(), FriendList.class);
	    		startActivity(friendActivity);
			}
		});
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
    	if(FLOATERS.size() >= 1){
			third = new ActionItem();
			third.setTitle(FLOATERS.get(0).get("board"));
			third.setIcon(getResources().getDrawable(R.drawable.folder));
			third.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(0).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	if(FLOATERS.size() >= 2){
			fourth = new ActionItem();
			fourth.setTitle(FLOATERS.get(1).get("board"));
			fourth.setIcon(getResources().getDrawable(R.drawable.folder));
			fourth.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(1).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	if(FLOATERS.size() >= 3){
			fifth = new ActionItem();
			fifth.setTitle(FLOATERS.get(2).get("board"));
			fifth.setIcon(getResources().getDrawable(R.drawable.folder));
			fifth.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(2).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	if(FLOATERS.size() >= 4){
			sixth = new ActionItem();
			sixth.setTitle(FLOATERS.get(3).get("board"));
			sixth.setIcon(getResources().getDrawable(R.drawable.folder));
			sixth.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(3).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	if(FLOATERS.size() >= 5){
			seventh = new ActionItem();
			seventh.setTitle(FLOATERS.get(4).get("board"));
			seventh.setIcon(getResources().getDrawable(R.drawable.folder));
			seventh.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(4).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	if(FLOATERS.size() >= 6){
			eigth = new ActionItem();
			eigth.setTitle(FLOATERS.get(5).get("board"));
			eigth.setIcon(getResources().getDrawable(R.drawable.folder));
			eigth.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(5).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	if(FLOATERS.size() >= 7){
			ninth = new ActionItem();
			ninth.setTitle(FLOATERS.get(6).get("board"));
			ninth.setIcon(getResources().getDrawable(R.drawable.folder));
			ninth.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(6).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	if(FLOATERS.size() >= 8){
			tenth = new ActionItem();
			tenth.setTitle(FLOATERS.get(7).get("board"));
			tenth.setIcon(getResources().getDrawable(R.drawable.folder));
			tenth.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(7).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	if(FLOATERS.size() >= 9){
			eleventh = new ActionItem();
			eleventh.setTitle(FLOATERS.get(8).get("board"));
			eleventh.setIcon(getResources().getDrawable(R.drawable.folder));
			eleventh.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(8).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	if(FLOATERS.size() >= 10){
			twelfth = new ActionItem();
			twelfth.setTitle(FLOATERS.get(9).get("board"));
			twelfth.setIcon(getResources().getDrawable(R.drawable.folder));
			twelfth.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					String urlcontent = FLOATERS.get(9).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
				}
			});
    	}
    	
    	new getProfile().execute();
	}
	@Override 
    public void onDestroy() { 
        super.onDestroy(); 
           ACTIVE_INSTANCE = null; 
    }
	
	private class getProfile extends AsyncTask<Void, Integer, Void> implements OnDismissListener{

		@Override
		public void onDismiss(DialogInterface dialog) {
			// TODO Auto-generated method stub
			this.cancel(true);
		}

		@Override
		protected Void doInBackground(Void... params) {
		
			getPage(page);
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result){
			setContentView(R.layout.profile);
			TextView title = (TextView) findViewById(R.id.title);
			Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
			title.setTypeface(font);
			
			if(PROFILE.size() < 1){
				TextView name = (TextView) findViewById(R.id.username);
				name.setText("error");
			} else {
				TextView name = (TextView) findViewById(R.id.username);
				name.setText(PROFILE.get(0).get("name"));
				
				TextView mail = (TextView) findViewById(R.id.useremail);
				mail.setText(PROFILE.get(0).get("email"));
				
				TextView registration = (TextView) findViewById(R.id.userreg);
				registration.setText(PROFILE.get(0).get("reg").replaceAll("T.*", ""));
				
				TextView lastvisit = (TextView) findViewById(R.id.userlast);
				lastvisit.setText(PROFILE.get(0).get("last").replaceAll("T.*", ""));
				
				TextView postcounts = (TextView) findViewById(R.id.userposts);
				postcounts.setText(postCount);
				
				TextView kudocounts = (TextView) findViewById(R.id.userkudos);
				kudocounts.setText(kudos);
				
				try {
				ImageView icon = (ImageView)findViewById(R.id.user_icon);
				Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("http://boards.adultswim.com"+PROFILE.get(0).get("avatar")).getContent());
				icon.setImageBitmap(bitmap);
				} catch (MalformedURLException e) {
					  e.printStackTrace();
				} catch (IOException e) {
				  e.printStackTrace();
				}
			}
			ImageView im1 = (ImageView)findViewById(R.id.lst);
			  im1.setOnClickListener(new OnClickListener() {
				    public void onClick(View v) {
				    	QuickAction qa = new QuickAction(v);
				    	if(getHomePref()){
				    		qa.addActionItem(home);
				    	}
				    	if(getindxPref()){
				    		qa.addActionItem(first);
				    	}
						if(getinbxPref()){
							qa.addActionItem(second);
						}
						if(gettrkrPref()){
							qa.addActionItem(sTracker);
						}
						if(getsubPref()){
							qa.addActionItem(subV);
						}
						if(getfrndPref()){
							qa.addActionItem(frnd);
						}
						if(third != null){
							qa.addActionItem(third);
						}
						if(fourth != null){
							qa.addActionItem(fourth);
						}
						if(fifth != null){
							qa.addActionItem(fifth);
						}
						if(sixth != null){
							qa.addActionItem(sixth);
						}
						if(seventh != null){
							qa.addActionItem(seventh);
						}
						if(eigth != null){
							qa.addActionItem(eigth);
						}
						if(ninth != null){
							qa.addActionItem(ninth);
						}
						if(tenth != null){
							qa.addActionItem(tenth);
						}
						if(eleventh != null){
							qa.addActionItem(eleventh);
						}
						if(twelfth != null){
							qa.addActionItem(twelfth);
						}
						
						qa.show();
				    }
				});
			  
			  ImageView im2 = (ImageView)findViewById(R.id.rep);
			  im2.setOnClickListener(new OnClickListener() {
				    public void onClick(View v) {
				    	newThread();
				    }
				});
			  
			  Button pm = (Button)findViewById(R.id.sendpm);
			  Button vt = (Button)findViewById(R.id.viewtracker);
			  if(PROFILE.size() < 1){
				  
			  } else {
				  pm.setOnClickListener(new OnClickListener(){
	
					@Override
					public void onClick(View v) {
						String msgAuthId = PROFILE.get(0).get("id"); 
			    		String author = PROFILE.get(0).get("name");
			    		String subject = "";
			    		String msg = "";
			    		Bundle b = new Bundle();
			    		b.putStringArray("data", new String[]{msgAuthId, author, subject, msg});
			  			Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
			  			pmContent.putExtras(b);
			  			startActivity(pmContent);
						
					}
					  
				  });
			  }
			  if(PROFILE.size() < 1){
				  
			  } else {
				  vt.setOnClickListener(new OnClickListener(){
	
					@Override
					public void onClick(View v) {
						String trackerurl = "/t5/forums/recentpostspage/user-id/"+PROFILE.get(0).get("id")+"/post-type/message/page/1";
						Intent tracker = new Intent(getApplicationContext(), Tracker.class);
						tracker.setData(Uri.parse(trackerurl));
						startActivity(tracker);
						
					}
					  
				  });
			  }
		}
		
	}
	
	public void getPage(String curPage2){
		String content = curPage2;
		
		HttpGet httpget = new HttpGet(content);
		HttpResponse response1;
		httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
		
		try {
			response1 = mClient.execute(httpget);
			HttpEntity entity1 = response1.getEntity();
			String result = "";
			try{
				InputStream in = entity1.getContent();
				result = IOUtils.toString(in);
			} catch(Exception ex){
				result = "error";
			}
			
				
				Document doc = ASsettings.XMLfromString(result);
				HashMap<String,String> temp1 = new HashMap<String,String>();
				NodeList nodes = doc.getElementsByTagName("user");
				if(result.contains("status=\"success\"")){
				if(nodes != null){
				
					Element userstring = (Element) nodes.item(0);
					
					String isuser = userstring.getAttribute("null");
					
					
					if(!isuser.equals("true")){	
					
					
					Node userItem = nodes.item(0);
					Element node = (Element) userItem;
					
					NodeList logins = node.getElementsByTagName("login");
					Element login2 = (Element) logins.item(0);
					NodeList logins2 = login2.getChildNodes();
					String name = ((Node) logins2.item(0)).getNodeValue();
					temp1.put("name", name);
					
					PROFILE.add(temp1);
					
					NodeList emails = node.getElementsByTagName("email");
					Element email2 = (Element) emails.item(0);
					if(email2 != null){
						NodeList emails2 = email2.getChildNodes();
						String email = ((Node) emails2.item(0)).getNodeValue();
						temp1.put("email", email);
						PROFILE.add(temp1);
					} else {
						temp1.put("email", "null");
						PROFILE.add(temp1);
					}
					NodeList ids = node.getElementsByTagName("id");
					Element id2 = (Element) ids.item(0);
					NodeList ids2 = id2.getChildNodes();
					String id = ((Node) ids2.item(0)).getNodeValue();
					temp1.put("id", id);
					
					PROFILE.add(temp1);
					
					NodeList regs = node.getElementsByTagName("registration_time");
					Element reg2 = (Element) regs.item(0);
					NodeList regs2 = reg2.getChildNodes();
					String reg = ((Node) regs2.item(0)).getNodeValue();
					temp1.put("reg", reg);
					
					PROFILE.add(temp1);
					
					NodeList lasts = node.getElementsByTagName("last_visit_time");
					Element last2 = (Element) lasts.item(0);
					NodeList lasts2 = last2.getChildNodes();
					String last = ((Node) lasts2.item(0)).getNodeValue();
					temp1.put("last", last);
					
					PROFILE.add(temp1);
					
					NodeList profilenodes = doc.getElementsByTagName("profile");
					for (int i = 0; i < profilenodes.getLength(); i++) {
						
						Node item = profilenodes.item(i);
						Element itm = (Element) item;
						
						
						if(itm.getAttribute("name").equals("url_icon")){
							NodeList avatar1 = itm.getChildNodes();
							String avatar = ((Node) avatar1.item(0)).getNodeValue();
							temp1.put("avatar", avatar);
							PROFILE.add(temp1);
						}
						
					}
					
				} else {
					temp1.put("name", "error");
					temp1.put("avatar", "error");
					temp1.put("last", "error");
					temp1.put("reg", "error");
					temp1.put("id", "error");
					temp1.put("email", "error");
					PROFILE.add(temp1);
				}
					
				}
			} else {
				System.out.println("ERROR");
				
				temp1.put("name", "error");
				temp1.put("avatar", "error");
				temp1.put("last", "error");
				temp1.put("reg", "error");
				temp1.put("id", "error");
				temp1.put("email", "error");
				PROFILE.add(temp1);
			}
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String postUrl = content+"/posts/count";
		
		HttpGet httpget2 = new HttpGet(postUrl);
		HttpResponse response2;
		httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
		
		try {
			response2 = mClient.execute(httpget2);
			HttpEntity entity2 = response2.getEntity();
			String result2 = "";
			try{
				InputStream in = entity2.getContent();
				result2 = IOUtils.toString(in);
			} catch(Exception ex){
				result2 = "error";
			}
			
			if(result2.contains("status=\"success\"")){
				Document doc = ASsettings.XMLfromString(result2);
				HashMap<String,String> temp1 = new HashMap<String,String>();
				NodeList nodes = doc.getElementsByTagName("response");
				if(nodes != null){
					
					
					Node userItem = nodes.item(0);
					Element node = (Element) userItem;
					
					NodeList logins = node.getElementsByTagName("value");
					Element login2 = (Element) logins.item(0);
					NodeList logins2 = login2.getChildNodes();
					String name = ((Node) logins2.item(0)).getNodeValue();
					postCount = name;
					temp1.put("postCount", postCount);
					PROFILE.add(temp1);
				}
			} else {
				HashMap<String,String> temp1 = new HashMap<String,String>();
				temp1.put("postCount", "error");
				PROFILE.add(temp1);
			}
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String kudoUrl = content+"/kudos/received/count";
		
		HttpGet httpget3 = new HttpGet(kudoUrl);
		HttpResponse response3;
		httpget3.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
		
		try {
			response3 = mClient.execute(httpget3);
			HttpEntity entity3 = response3.getEntity();
			String result3 = "";
			try{
				InputStream in = entity3.getContent();
				result3 = IOUtils.toString(in);
			} catch(Exception ex){
				result3 = "error";
			}
			if(result3.contains("status=\"success\"")){
				Document doc = ASsettings.XMLfromString(result3);
				HashMap<String,String> temp1 = new HashMap<String,String>();
				NodeList nodes = doc.getElementsByTagName("response");
				if(nodes != null){
					
					
					Node userItem = nodes.item(0);
					Element node = (Element) userItem;
					
					NodeList logins = node.getElementsByTagName("value");
					Element login2 = (Element) logins.item(0);
					NodeList logins2 = login2.getChildNodes();
					String name = ((Node) logins2.item(0)).getNodeValue();
					kudos = name;
					temp1.put("kudoCount", kudos);
					PROFILE.add(temp1);
				}
			} else {
				HashMap<String,String> temp1 = new HashMap<String,String>();
				temp1.put("kudoCount", "error");
				PROFILE.add(temp1);
			}
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.login:
    		showDialog(LOGIN_DIALOG);
    		return true;
    	case R.id.index:
    		index();
    		return true;
    	case R.id.refresh:
    		startActivity(starterintent);
    		finish();
    		break;
    	case R.id.thread:
    		newThread();
    		return true;
    	case R.id.newpm:
    		compose();
    		return true;
    	case R.id.inbox:
    		inbox();
    		return true;
    	case R.id.prv:
    		prev();
    		return true;
    	case R.id.nxt:
    		next();
    		return true;
    	case R.id.prfs:
    		settings();
    		return true;
    	case R.id.homescreen:
    		Intent index = new Intent(getApplicationContext(), AndroidSwim.class);	
    		startActivity(index);
    		return true;
    	case R.id.searchMsgs:
    		Intent searchActivity = new Intent(getApplicationContext(), SearchMessages.class);
    		startActivity(searchActivity);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    	return true;
    }
    @Override
    protected Dialog onCreateDialog(int id){
    	Dialog dialog;
    	ProgressDialog pdialog;
    	switch (id) {
    	case LOGIN_DIALOG:
    		dialog = new LoginDialog(this, mSettings, false){
    			public void onLoginChosen(String user, String password){
    				dismissDialog(LOGIN_DIALOG);
    				new MyLoginTask(user, password).execute();
    				Toast.makeText(getApplicationContext(), "please wait...", Toast.LENGTH_LONG).show();
    			}
    		};
    		break;
    	case DIALOG_LOGGING_IN:
    		pdialog = new ProgressDialog(this);
    		pdialog.setMessage("Logging in...");
    		pdialog.setIndeterminate(true);
    		pdialog.setCancelable(false);
    		dialog = pdialog;
    		break;
    	default:
    		throw new IllegalArgumentException("Unexpected dialog id "+id);
    	}
    	return dialog;
    	
    }
    private class MyLoginTask extends LoginTask {
    	public MyLoginTask(String username, String password) {
    		super(username, password, mSettings, mClient, getApplicationContext());
    	}
    	
    	@Override
    	protected void onPreExecute(){
    		mClient.getCookieStore().clear();
    		if(ACTIVE_INSTANCE != null){
    			ACTIVE_INSTANCE.showDialog(DIALOG_LOGGING_IN);
    		}
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean success){
    		if(ACTIVE_INSTANCE != null){
    			ACTIVE_INSTANCE.dismissDialog(DIALOG_LOGGING_IN);
    		}
    		Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
    		if(getpmPref()){
    			new CheckMessagesTask(getApplicationContext(), mClient).execute();
    		} else {
    			System.out.println("------------------------ PM SERVICE OFF");
    		}
    	}
    }
    private class addFriend extends AsyncTaskEx<Void, Void, Void> {
		String username;
		public addFriend(String string) {
			// TODO Auto-generated constructor stub
			super();
			username = string;
		}

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String uName = username;
			HttpPost httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/users/self/addressbook/contacts/friends/add?contacts.user=login/"+uName);
			HttpResponse response1;
			httppost.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			try {
				response1 = mClient.execute(httppost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
    private void settings(){
    	String calling = this.getLocalClassName();
    	Intent settingsActivity = new Intent(getApplicationContext(), Preferences.class);
    	settingsActivity.setData(Uri.parse(calling));
    	startActivity(settingsActivity);
    }
    private String getStylePref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String style = prefs.getString("themePref", "default");
		return style;
	}
    private String getIconPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String icons = prefs.getString("iconPref", "standard");
		return icons;
	}
    private boolean getpmPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean pmstate = prefs.getBoolean("notificationpref", true);
		return pmstate;
	}
   
	private void index() {
		if(getlistPref()){
			Intent index = new Intent(getApplicationContext(), AlternateBoardList.class);	
			startActivity(index);
		} else {
			Intent index = new Intent(getApplicationContext(), BoardListing.class);	
			startActivity(index);
		}
	}
	private void newThread(){
		
			Toast.makeText(getApplicationContext(), "Cannot make thread at this time", Toast.LENGTH_SHORT).show();
		
	}
	private void compose() {
		String msgAuthId = ""; 
		String author = "";
		String subject = "";
		String msg = "";
		Bundle b = new Bundle();
		b.putStringArray("data", new String[]{msgAuthId, author, subject, msg});
		Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
		pmContent.putExtras(b);
		startActivity(pmContent);
	}
    private void inbox() {
    	String pmpage = "http://boards.adultswim.com/restapi/vc/users/id/";
		Intent inbox = new Intent(getApplicationContext(), InboxActivity.class);
		inbox.setData(Uri.parse(pmpage));
		startActivity(inbox);
	}
    private void selftracker(){
    	Intent trkr = new Intent(getApplicationContext(), SelfTracker.class);	
		startActivity(trkr);
    }
    private void next(){
    	String curPage = starterintent.getData().toString();
		String[] pageSplit = curPage.split("/");
		String pageNum = pageSplit[pageSplit.length-1];
		int pageInt = Integer.parseInt(pageNum.trim());
		int nextInt = pageInt+1;
		String nextPageNum = Integer.toString(nextInt);
		String nextPage = curPage.replaceAll("/[^/]*$", "/"+nextPageNum);
		//System.out.println("NEXT PAGE!!! "+ nextPage);
		page = nextPage;
		Intent showContent = new Intent(getApplicationContext(), ThreadListing.class);
		showContent.setData(Uri.parse(page));
		startActivity(showContent);
    }
    private void prev(){
    	String curPage = starterintent.getData().toString();
		String[] pageSplit = curPage.split("/");
		String pageNum = pageSplit[pageSplit.length-1];
		int pageInt = Integer.parseInt(pageNum.trim());
		int prevInt = pageInt-1;
		String prevPageNum = Integer.toString(prevInt);
		String prevPage = curPage.replaceAll("/[^/]*$", "/"+prevPageNum);
		//System.out.println("NEXT PAGE!!! "+ nextPage);
		page = prevPage;
		if(prevInt != 0){
    		Intent showContent = new Intent(getApplicationContext(), ThreadListing.class);
    		showContent.setData(Uri.parse(page));
    		startActivity(showContent);
		} else {
			Toast.makeText(getApplicationContext(), "No previous pages", Toast.LENGTH_SHORT).show();
		}
    }
    
		
	private void subscriptionViewer(){
    	String subUrl = "/t5/user/myprofilepage/tab/user-subscriptions%3Aemail-subscriptions/page/1";
    	Intent subs = new Intent(getApplicationContext(), SubscriptionViewer.class);
    	subs.setData(Uri.parse(subUrl));
    	startActivity(subs);
    }
	private boolean getsubPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean subs = prefs.getBoolean("subspref", false);
		return subs;
	}
	private boolean getfrndPref(){
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean fr = prefs.getBoolean("frndspref", true);
		return fr;
    }
    private boolean getinbxPref(){
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean inb = prefs.getBoolean("inbxpref", true);
		return inb;
    }
    private boolean getindxPref(){
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean ind = prefs.getBoolean("indxpref", true);
		return ind;
    }
    private boolean gettrkrPref(){
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean trk = prefs.getBoolean("trkrpref", true);
		return trk;
    }
    private boolean getlistPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean blpref = prefs.getBoolean("blpref", false);
		return blpref;
	}
    private boolean getHomePref(){
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean hm = prefs.getBoolean("homepref", true);
		return hm;
    }
    private void goHome() {
    	Intent index = new Intent(getApplicationContext(), AndroidSwim.class);	
		startActivity(index);
	}
}