package com.metatroid.androidswim;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public final class UnreadMessages extends ListActivity  {
	private static final int LOGIN_DIALOG = 0;
	private static final int DIALOG_LOGGING_IN = 1;
	private final ASsettings mSettings = new ASsettings();
	private final DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	final ArrayList<HashMap<String,String>> MESSAGES = new ArrayList<HashMap<String,String>>();
	SpecialAdapter adapter;
	Intent starterintent;
	String pageN;
	public static final String floated = "boardfloatpref";
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
	String uid;
	String content;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		starterintent = getIntent();
		if(starterintent.getData().toString().contains("pageNumber")){
			pageN = starterintent.getData().toString().replaceAll("(?:.*)pageNumber=", "");
			content = starterintent.getData().toString().replaceAll("pageNumber=.*", "");
		} else {
			pageN = "1";
			content = starterintent.getData().toString();
		}
		
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
    	if (ASsettings.getUserId().length() < 1) {
            new getUserId().execute();
        } else {
        	uid = ASsettings.getUserId();
        	new getMessages().execute();
        }
		
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
			uid = result;
			new getMessages().execute();
		}
		
	}
	
	private class getMessages extends AsyncTask<Void, Integer, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			messageList();
			return null;
		}
		protected void onPostExecute(Void result){
			
			setListAdapter(adapter);
			setContentView(R.layout.item_list);
			
			
		}
	
	}
	
	public void messageList(){
		if(getStylePref().equals("default")){
			adapter = new SpecialAdapter(this, MESSAGES, R.layout.item_row, new String[]{"author","title"}, new int[]{R.id.text1,R.id.text2});
		} else if(getStylePref().equals("light")){
			adapter = new SpecialAdapter(this, MESSAGES, R.layout.item_row_light, new String[]{"author","title"}, new int[]{R.id.text1,R.id.text2});
		} else if(getStylePref().equals("dark")){
			adapter = new SpecialAdapter(this, MESSAGES, R.layout.item_row_dark, new String[]{"author","title"}, new int[]{R.id.text1,R.id.text2});
		}
		
		
		String line = null;
		try {
			System.out.println("------------------------------------------------------------" + content+uid+"/mailbox/notes/inbox?page="+pageN);
			HttpGet httpget = new HttpGet(content+uid+"/mailbox/notes/inbox/unread?page="+pageN);
			HttpResponse httpResponse = mClient.execute(httpget);
			HttpEntity httpEntity = httpResponse.getEntity();
			line = EntityUtils.toString(httpEntity);
		} catch (UnsupportedEncodingException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (MalformedURLException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (IOException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		}
		System.out.println(line);
		Document doc = ASsettings.XMLfromString(line);
			
		NodeList nodes = doc.getElementsByTagName("note");
		for (int i = 0; i < nodes.getLength(); i++) {
			HashMap<String,String> temp = new HashMap<String,String>();
			Node item = nodes.item(i);
			Element itm = (Element) item;
			
			NodeList ids = itm.getElementsByTagName("id");
			Element threadid = (Element) ids.item(0);
			NodeList idnums = threadid.getChildNodes();
			String idnum = ((Node) idnums.item(0)).getNodeValue();
			System.out.println("---------------------thread id = "+idnum);
			temp.put("messageId", idnum);
			
			NodeList subjects = itm.getElementsByTagName("subject");
			Element subject = (Element) subjects.item(0);
			NodeList subj = subject.getChildNodes();
			String title = "";
			for (int q = 0; q < subj.getLength(); q++){
				title += ((Node) subj.item(q)).getNodeValue();
			}
			System.out.println("---------------------thread title = "+title);
			temp.put("title", title);
			
			NodeList nodes2 = itm.getElementsByTagName("from");
			for (int n = 0; n < nodes2.getLength(); n++) {
				Node item2 = nodes2.item(n);
				Element itm2 = (Element) item2;
				
				NodeList auths = itm2.getElementsByTagName("login");
				Element authname = (Element) auths.item(0);
				NodeList auths2 = authname.getChildNodes();
				String author = ((Node) auths2.item(0)).getNodeValue();
				System.out.println("---------------------thread author = "+author);
				temp.put("author", author);
			}
			
			String msgContent = "";
			NodeList body = itm.getElementsByTagName("body");
			Element bodystng = (Element) body.item(0);
			NodeList bodystrngs = bodystng.getChildNodes();
			for(int p=0;p<bodystrngs.getLength();p++){
				msgContent += ((Node) bodystrngs.item(p)).getNodeValue();
			}
			temp.put("message", msgContent);
			
			MESSAGES.add(temp);
		}
		
		
		
		
	}
	
	public class SpecialAdapter extends SimpleAdapter {

		public SpecialAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent){
			
			 View row = convertView;
			 
			  if(row==null){
			   LayoutInflater inflater=getLayoutInflater();
			   if(getStylePref().equals("default")){
				   row=inflater.inflate(R.layout.item_row, parent, false);
				} else if(getStylePref().equals("light")) {
					row=inflater.inflate(R.layout.item_row_light, parent, false);
				} else if(getStylePref().equals("dark")) {
					row=inflater.inflate(R.layout.item_row_dark, parent, false);
				}
			  }
			  
			  String auth = MESSAGES.get(position).get("author");
			  String title = MESSAGES.get(position).get("title");
			  if(auth != null && title != null){
				  TextView text1=(TextView)row.findViewById(R.id.text1);
				  	text1.setText(auth);
				  TextView text2=(TextView)row.findViewById(R.id.text2);
				  	text2.setText(title);
				  	text2.setOnClickListener(new OnClickListener(){
			        	public void onClick(View v) {
			        		String replyurl = MESSAGES.get(position).get("messageId");
			        		
			      			Intent replyContent = new Intent(getApplicationContext(), MessageViewer.class);
			      			replyContent.setData(Uri.parse(replyurl));
			      			startActivity(replyContent);
			        	}
			        });
			  } else {
				  TextView text1=(TextView)row.findViewById(R.id.text1);
				  	text1.setText("Error");
				  TextView text2=(TextView)row.findViewById(R.id.text2);
				  	text2.setText("Connection timed out or no messages to display");
			  }
			  	
			  ImageView im1 = (ImageView)findViewById(R.id.lst);
			  im1.setOnClickListener(new OnClickListener() {
				    public void onClick(View v) {
				    	QuickAction qa = new QuickAction(v);
						
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
				    	compose();
				    }
				});
			  
			return row;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.inbox_menu, menu);
		
		return true;
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.index:
    		index();
    		return true;
    	case R.id.refresh:
    		startActivity(starterintent);
    		finish();
    		break;
    	case R.id.newpm:
    		compose();
    		return true;
    	case R.id.prfs:
    		settings();
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
    		showDialog(DIALOG_LOGGING_IN);
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean success){
    		dismissDialog(DIALOG_LOGGING_IN);
    		Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
    		
    	}
    }
    
	private void index() {
		Intent index = new Intent(getApplicationContext(), AndroidSwim.class);	
		startActivity(index);
	}
	private void compose() {
		String msgAuthId = ""; 
		String author = "";
		String subject = "";
		Bundle b = new Bundle();
		b.putStringArray("data", new String[]{msgAuthId, author, subject});
		Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
		pmContent.putExtras(b);
		startActivity(pmContent);
	}
	private void selftracker(){
    	Intent trkr = new Intent(getApplicationContext(), SelfTracker.class);	
		startActivity(trkr);
    }
	private void inbox() {
    	String pmpage = "http://boards.adultswim.com/restapi/vc/users/id/";
		Intent inbox = new Intent(getApplicationContext(), InboxActivity.class);
		inbox.setData(Uri.parse(pmpage));
		startActivity(inbox);
	}
	private String getStylePref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String style = prefs.getString("themePref", "default");
		return style;
	}
	private void settings(){
    	String calling = this.getLocalClassName();
    	Intent settingsActivity = new Intent(getApplicationContext(), Preferences.class);
    	settingsActivity.setData(Uri.parse(calling));
    	startActivity(settingsActivity);
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
	
}