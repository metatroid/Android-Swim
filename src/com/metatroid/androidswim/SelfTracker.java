package com.metatroid.androidswim;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SelfTracker extends ListActivity{
	public static SelfTracker ACTIVE_INSTANCE;
	private static final int LOGIN_DIALOG = 0;
	private static final int INDEX = 1;
	private static final int DIALOG_LOGGING_IN = 2;
	private final ASsettings mSettings = new ASsettings();
	static DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	final ArrayList<HashMap<String,String>> THREADS = new ArrayList<HashMap<String,String>>();
	Intent starterintent;
	SpecialAdapter adapter;
	String page;
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
	ActionItem home;
	String Userid = mSettings.getUserId();
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		ACTIVE_INSTANCE = this;
		starterintent = getIntent();
		setContentView(R.layout.loading);
		if(Userid.length() > 1){
			if(starterintent.getData() == null){
				page = "/t5/forums/recentpostspage/user-id/"+Userid+"/post-type/message/page/1";
			} else {
				page = "/t5/forums/recentpostspage/user-id/"+Userid+"/post-type/message/page/"+starterintent.getData().toString();
			}
			
		}
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
		if(Userid.length() < 1){
			System.out.println("!!!!!!!!!!!!!!!!! no userid");
			new getTrackerUrl().execute();
		} else {
			System.out.println("!!!!!!!!!!!!!!!!! userid is " + Userid);
			new getSelfTracker().execute();
		}
	
	}
	@Override 
    public void onDestroy() { 
        super.onDestroy(); 
           ACTIVE_INSTANCE = null; 
    }
	
	private class getTrackerUrl extends AsyncTask<String, Integer, String>  {

		
		
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
			
			page = "/t5/forums/recentpostspage/user-id/"+result+"/post-type/message/page/1";
			new getSelfTracker().execute();
		}
		
	}
	
	private class getSelfTracker extends AsyncTask<Void, Integer, Void> implements OnDismissListener{

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			getThreads(page);
			return null;
		}
		
		protected void onPostExecute(Void result){
			setListAdapter(adapter);
			
			if(THREADS.isEmpty()){
				HashMap<String,String> temp = new HashMap<String,String>();
				temp.put("author", "error");
				temp.put("title", "Connection timed out");
				THREADS.add(temp);
			}
			setContentView(R.layout.item_list);
			TextView title = (TextView) findViewById(R.id.title);
			Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
			title.setTypeface(font);
			
			if(!THREADS.isEmpty()){
				ListView lv = getListView();
				lv.setOnItemClickListener(new OnItemClickListener(){
					public void onItemClick(AdapterView<?> parent, View view,
		        			int position, long id) {
		        		String urlcontent = "MessageID"+THREADS.get(position).get("link").replaceAll("^.*(?:m\\-p\\/)", "").replaceAll("#[^#]+$", "");
		        		Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
		        		showurlContent.setData(Uri.parse(urlcontent));
		        		startActivity(showurlContent);
		        	}
				});
			}
			ListView lv = getListView();
			registerForContextMenu(lv);
		}

		@Override
		public void onDismiss(DialogInterface arg0) {
			// TODO Auto-generated method stub
			this.cancel(true);
		}
		
	}
	
	
	
	
	
	
	public void getThreads(String url){
		String content = url;
		
		if(getStylePref().equals("default")){
			adapter = new SpecialAdapter(this, THREADS, R.layout.tracker_row, new String[]{"title","replies","new"}, new int[]{R.id.tracker1,R.id.tracker2,R.id.tracker3});
		} else if(getStylePref().equals("light")){
			adapter = new SpecialAdapter(this, THREADS, R.layout.tracker_row_light, new String[]{"title","replies","new"}, new int[]{R.id.tracker1,R.id.tracker2,R.id.tracker3});
		} else if(getStylePref().equals("dark")){
			adapter = new SpecialAdapter(this, THREADS, R.layout.tracker_row_dark, new String[]{"title","replies","new"}, new int[]{R.id.tracker1,R.id.tracker2,R.id.tracker3});
		}
		
		
		HttpGet httpget = new HttpGet("http://boards.adultswim.com"+content);
		HttpResponse response;
		httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
		System.out.println("Trying this URL" + content);
		try {
			response = mClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String result = "";
			try{
			InputStream in = entity.getContent();
			byte[] bytes = new byte[1000];

			StringBuilder x = new StringBuilder();

			int numRead = 0;
			while ((numRead = in.read(bytes)) >= 0) {
			    x.append(new String(bytes, 0, numRead));
			}
			result = x.toString();
		} catch(Exception ex){
			result = "error";
		}
			
			Document doc = Jsoup.parse(result);
			//title = doc.getElementsByTag("title").first().text();
			Elements threads = doc.getElementsByClass("lia-list-row");
			
			for (Element src : threads){
				String title = src.select("a.page-link").text().replaceAll("\\(.+", "");
				String link = src.select("a.page-link").attr("href");
				String replies = src.select(".message-subject-body").text();
				String newReplies = src.select(".newMessagesCountColumn").select("a").text();
				String responses = src.select(".repliesCountColumn").text();
				
				HashMap<String,String> temp = new HashMap<String,String>();
				temp.put("title", title);
				temp.put("link", link);
				temp.put("replies", replies);
				temp.put("new", newReplies);
				temp.put("responses", responses);
				THREADS.add(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		public View getView(int position, View convertView, ViewGroup parent){
			View row = convertView;
			
			if(row==null){
				LayoutInflater inflater=getLayoutInflater();
				if(getStylePref().equals("default")){
					row=inflater.inflate(R.layout.tracker_row, parent, false);
				} else if(getStylePref().equals("light")){
					row=inflater.inflate(R.layout.tracker_row_light, parent, false);
				} else if(getStylePref().equals("dark")){
					row=inflater.inflate(R.layout.tracker_row_dark, parent, false);
				}
			}
			if(!THREADS.isEmpty() && THREADS.get(position).get("title") != null && THREADS.get(position).get("link") != null && THREADS.get(position).get("new") != null && THREADS.get(position).get("replies") != null){
				String title = THREADS.get(position).get("title").replaceAll("span", "font").replaceAll("class", "color=\"#111\" class");
				String newReplies = THREADS.get(position).get("new");
				String replies = THREADS.get(position).get("replies");
				String responses = THREADS.get(position).get("responses");
				
				TextView text1=(TextView)row.findViewById(R.id.tracker1);
				text1.setText(title);
				TextView text2=(TextView)row.findViewById(R.id.tracker2);
				text2.setText(replies);
				TextView text3=(TextView)row.findViewById(R.id.tracker3);
				text3.setText("Replies:  "+responses);
				TextView text4=(TextView)row.findViewById(R.id.tracker4);
				text4.setText("New:  "+newReplies);
			} else {
				TextView text1=(TextView)row.findViewById(R.id.tracker1);
				text1.setText("Connection timed out or not logged in");
				TextView text2=(TextView)row.findViewById(R.id.tracker2);
				text2.setText("");
				TextView text3=(TextView)row.findViewById(R.id.tracker3);
				text3.setText("");
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
				    	Toast.makeText(getApplicationContext(), "Nothing to reply to", Toast.LENGTH_LONG).show();
				    }
				});
			
			return row;
		}
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.thread_menu, menu);
	  MenuItem trker = menu.findItem(R.id.tracker);
	  trker.setVisible(false);
	  MenuItem msgr = menu.findItem(R.id.sendpm);
	  msgr.setVisible(false);
	  MenuItem opnr = menu.findItem(R.id.openthread);
	  opnr.setTitle("Open \""+THREADS.get(info.position).get("title")+"\"");
	  MenuItem kdo = menu.findItem(R.id.kudo);
	  kdo.setVisible(false);
	  MenuItem lnk = menu.findItem(R.id.externallinks);
	  lnk.setVisible(false);
	  MenuItem edt = menu.findItem(R.id.edit);
	  edt.setVisible(false); 
	  MenuItem frnd = menu.findItem(R.id.friendAdd);
	  frnd.setVisible(false);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
	  		case R.id.openthread:
	  			String urlcontent = "MessageID"+THREADS.get(info.position).get("link").replaceAll("^.*(?:m\\-p\\/)", "").replaceAll("#[^#]+$", "");
        		Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
        		showurlContent.setData(Uri.parse(urlcontent));
        		startActivity(showurlContent);
	  			return true;
	  		default:
	  			return super.onContextItemSelected(item);
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
    		ACTIVE_INSTANCE.showDialog(DIALOG_LOGGING_IN);
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean success){
    		
    		ACTIVE_INSTANCE.dismissDialog(DIALOG_LOGGING_IN);
    		
    		Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
    		if(getpmPref()){
    			new CheckMessagesTask(getApplicationContext(), mClient).execute();
    		} else {
    			System.out.println("------------------------ PM SERVICE OFF");
    		}
    	}
    }
    private boolean getpmPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean pmstate = prefs.getBoolean("notificationpref", true);
		return pmstate;
	}
    private String getStylePref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String style = prefs.getString("themePref", "default");
		return style;
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
		Toast.makeText(getApplicationContext(), "Cannot make thread here", Toast.LENGTH_SHORT).show();
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
    private void next(){
    	if(starterintent.getData() != null){
    		String curPage = starterintent.getData().toString();
    		
    		int pageInt = Integer.parseInt(curPage);
    		int nextInt = pageInt+1;
    		String nextPageNum = Integer.toString(nextInt);
    		
    		Intent showContent = new Intent(getApplicationContext(), SelfTracker.class);
    		showContent.setData(Uri.parse(nextPageNum));
    		startActivity(showContent);
    	} else {
    		String nextPageNum = Integer.toString(2);
    		
    		Intent showContent = new Intent(getApplicationContext(), SelfTracker.class);
    		showContent.setData(Uri.parse(nextPageNum));
    		startActivity(showContent);
    	}
    }
    private void prev(){
    	if(starterintent.getData() != null){
    		String curPage = starterintent.getData().toString();
    		
    		int pageInt = Integer.parseInt(curPage);
    		int prevInt = pageInt-1;
    		String nextPageNum = Integer.toString(prevInt);
    		if(prevInt != 0){
    			Intent showContent = new Intent(getApplicationContext(), SelfTracker.class);
    			showContent.setData(Uri.parse(nextPageNum));
    			startActivity(showContent);
    		} else {
    			Toast.makeText(getApplicationContext(), "No previous pages", Toast.LENGTH_SHORT).show();
    		}
    	} else {
			Toast.makeText(getApplicationContext(), "No previous pages", Toast.LENGTH_SHORT).show();
		}
		
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