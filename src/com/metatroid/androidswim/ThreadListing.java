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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
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

import com.commonsware.cwac.task.AsyncTaskEx;

public class ThreadListing extends ListActivity {
	public static ThreadListing ACTIVE_INSTANCE;
	private static final int LOGIN_DIALOG = 0;
	private static final int INDEX = 1;
	private static final int DIALOG_LOGGING_IN = 2;
	public static final String floated = "boardfloatpref";
	private final ASsettings mSettings = new ASsettings();
	static DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	final ArrayList<HashMap<String,String>> THREADS = new ArrayList<HashMap<String,String>>();
	final ArrayList<ArrayList<HashMap<String,String>>> PAGES = new ArrayList<ArrayList<HashMap<String,String>>>();
	ArrayList<HashMap<String, String>> pgs;
	final List<String> POSTS = new ArrayList<String>();
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
	String boardId;
	getlist getlistTask = null;
	HttpGet httpget = null;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		ACTIVE_INSTANCE = this;
		starterintent = getIntent();
		
		setContentView(R.layout.loading);
		//progressBar = (ProgressBar)findViewById(R.id.progressbar);
		//progressBar.setProgress(0);
		
		String curPage = starterintent.getData().toString();
		if(curPage.contains("delimiter")){
			boardId = curPage.replaceAll("delimiter ", "");
			new getBoard(boardId).execute();
			page = null;
		} else {
			boardId = curPage.replaceAll("^.*(?:bd\\-p\\/)", "").replaceAll("/page.*", "");
			page = curPage;
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
    	if(page != null){
    		getlistTask = new getlist();
    		getlistTask.execute();
    	}
	}
	@Override 
    public void onDestroy() { 
        super.onDestroy(); 
           ACTIVE_INSTANCE = null; 
    }
		
	private class getlist extends AsyncTask<Void, Integer, Void> implements OnDismissListener{
		//int progress;
		

		@Override
		protected Void doInBackground(Void... params) {
			
			
			//String[] pageSplit = curPage.split("/");
			//String pageNum = pageSplit[pageSplit.length-1];
			
			getThreads(page);
			//while(progress<100){
			//    progress++;
			//    publishProgress(progress);
			//    SystemClock.sleep(10); 
			//   }
			return null;
		}
		@Override
	    protected void onCancelled() {
	        //client.getConnectionManager().shutdown();
	        httpget.abort();
	    }
		protected void onPostExecute(Void result){
				
			setListAdapter(adapter);
			
				
				if(THREADS.isEmpty()){
					HashMap<String,String> temp = new HashMap<String,String>();
					temp.put("author", "error");
					temp.put("title", "Connection timed out");
					THREADS.add(temp);
				}
				if(!THREADS.isEmpty() && THREADS.get(0).get("lastPage") != null){
					int tSize = THREADS.size();
					for(int t=0;t<tSize;t++){
						String lp = THREADS.get(t).get("lastPage");
						String base = THREADS.get(t).get("base").replaceAll("^.*(?:td\\-p\\/)", "").replaceAll("/jump-to/first-unread-message", "?page_size=25&page=PAGE_NUMBER_HERE");
						int p = Integer.parseInt(lp);
						
						ArrayList<HashMap<String, String>> lnks = new ArrayList<HashMap<String, String>>();
						
						for(int u=0;u<p;u++){
							  HashMap<String,String> temp = new HashMap<String,String>();
							  temp.put("page", Integer.toString(u+1));
							  temp.put("base", base);
							  temp.put("title", THREADS.get(t).get("title"));
							  lnks.add(temp);
							  PAGES.add(t, lnks);
								  
						}
						
					}
				} else {
					HashMap<String,String> temp = new HashMap<String,String>();
					temp.put("author", "error");
					temp.put("title", "Please try again");
					THREADS.add(temp);
				}
				
				  
				
				setContentView(R.layout.item_list);
				TextView title = (TextView) findViewById(R.id.title);
				Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
				title.setTypeface(font);
				if(!POSTS.isEmpty()){
					ListView lv2 = getListView();
					lv2.setOnItemClickListener(new OnItemClickListener(){
			        	public void onItemClick(AdapterView<?> parent, View view,
			        			int position, long id) {
			        		String urlcontent = POSTS.get(position).replaceAll("^.*(?:td\\-p\\/)", "").replaceAll("/jump-to/first-unread-message", "?page_size=25&page=1");
			        		Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
			        		showurlContent.setData(Uri.parse(urlcontent));
			        		startActivity(showurlContent);
			        	}
			        });
					//setTitle(title);
				}
			if(!THREADS.isEmpty() && THREADS.get(0).get("lastPage") != null){
				registerForContextMenu(getListView());
			}
		}
		@Override
		protected void onPreExecute(){
			//progress = 0;
		}
		//protected void onProgressUpdate(Integer... values){
		//	progressBar.setProgress(values[0]);
		//}

		@Override
		public void onDismiss(DialogInterface arg0) {
			this.cancel(true);
			
		}

		

		
		
	}
	
	public void getThreads(String curPage2){
		
		
		
		String content = curPage2;
		
		if(getStylePref().equals("default")){
			adapter = new SpecialAdapter(this, THREADS, R.layout.item_row, new String[]{"author","title"}, new int[]{R.id.text1,R.id.text2});
		} else if(getStylePref().equals("light")){
			adapter = new SpecialAdapter(this, THREADS, R.layout.item_row_light, new String[]{"author","title"}, new int[]{R.id.text1,R.id.text2});
		} else if(getStylePref().equals("dark")){
			adapter = new SpecialAdapter(this, THREADS, R.layout.item_row_dark, new String[]{"author","title"}, new int[]{R.id.text1,R.id.text2});
		}
		
		ListView lv2 = getListView();
        lv2.setTextFilterEnabled(true);
                
        
        
		//mClient.getCookieStore().getCookies();
		httpget = new HttpGet(content);
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
			
			Document doc = Jsoup.parse(result);
			//title = doc.getElementsByTag("title").first().text();
			Elements threads = doc.getElementsByClass("lia-list-row");
			thread = doc.getElementsByClass("primary-action").select("a").attr("href");
			for (Element src : threads){
				String title = src.select("a.page-link").first().text();
				String author = src.select("a.lia-user-name-link").first().text();
				String color = src.select("a.lia-user-name-link").first().attr("style").replaceAll("color:", "");
				String status = src.select("td.messageStatusIconColumn").select("img").attr("title");
				String profile = src.select("a.lia-user-name-link").first().attr("href");
				String[] useridsplit = src.select("a.lia-user-name-link").first().attr("href").split("/");
				String userid = useridsplit[useridsplit.length-1];
				String lastPage;
				if(src.select("ul").size() > 0){
					int pageSel = src.select("ul").first().select("li").size();
					lastPage = src.select("ul").first().select("li").eq(pageSel - 2).select("a").attr("href").replaceAll(".*?(page/)", "");
				} else {
					lastPage = "1";
				}
				HashMap<String,String> temp = new HashMap<String,String>();
				String href = src.select("a.page-link").attr("href");
				temp.put("author", author);
				temp.put("title", title);
				temp.put("status", status);
				temp.put("color", color);
				temp.put("profile", profile);
				temp.put("userid", userid);
				temp.put("lastPage", lastPage);
				temp.put("base", href);
				//System.out.println(color);
				THREADS.add(temp);
				
				POSTS.add(href);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Cookie> cookies = mClient.getCookieStore().getCookies();
		if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
            	System.out.println("COOKIE- " + cookies.get(i).getName().toString());
                if(cookies.get(i).getName().toString().equals("LithiumUserInfo")){
                	System.out.println("COOKIE MATCH!!!!- " + cookies.get(i).getValue().toString());
                	String userid = cookies.get(i).getValue().toString();
                	ASsettings.setUserId(userid);
                }
            }
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
				   row=inflater.inflate(R.layout.item_row, parent, false);
				} else if(getStylePref().equals("light")) {
					row=inflater.inflate(R.layout.item_row_light, parent, false);
				} else if(getStylePref().equals("dark")) {
					row=inflater.inflate(R.layout.item_row_dark, parent, false);
				}
			  }
			  if(!THREADS.isEmpty() && THREADS.get(position).get("author") != null && THREADS.get(position).get("color") != null && THREADS.get(position).get("title") != null && THREADS.get(position).get("status") != null){
				  
				  
				  
				  String auth = THREADS.get(position).get("author");
				  String hex = THREADS.get(position).get("color"); 
				  TextView text1=(TextView)row.findViewById(R.id.text1);
				  text1.setText(Html.fromHtml("<font color="+hex+">"+auth+"</font>"));
				  TextView text2=(TextView)row.findViewById(R.id.text2);
				  text2.setText(THREADS.get(position).get("title"));
				  
				  ImageView icon=(ImageView)row.findViewById(R.id.icon);
				  if(getIconPref().equals("standard")){
					  if(getStylePref().equals("light")){
						  if(THREADS.get(position).get("status").equals("Topic with new content")){
							  icon.setImageResource(R.drawable.icon_thread_new);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic")){
							  icon.setImageResource(R.drawable.icon_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic - you replied.")){
							  icon.setImageResource(R.drawable.icon_thread_reply);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.icon_thread_new_reply);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content")){
							  icon.setImageResource(R.drawable.icon_thread_new_hot);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic")){
							  icon.setImageResource(R.drawable.icon_thread_hot);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.icon_thread_new_hot_reply);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic - you replied.")){
							  icon.setImageResource(R.drawable.icon_thread_hot_reply);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic")){
							  icon.setImageResource(R.drawable.icon_thread_readonly);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_reply);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_new);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_new_hot);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_hot);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_hot_reply);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else {
							  icon.setImageResource(R.drawable.icon_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
					  } else {
						  if(THREADS.get(position).get("status").equals("Topic with new content")){
							  icon.setImageResource(R.drawable.icon_thread_new);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic")){
							  icon.setImageResource(R.drawable.icon_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic - you replied.")){
							  icon.setImageResource(R.drawable.icon_thread_reply);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.icon_thread_new_reply);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content")){
							  icon.setImageResource(R.drawable.icon_thread_new_hot);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic")){
							  icon.setImageResource(R.drawable.icon_thread_hot);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.icon_thread_new_hot_reply);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic - you replied.")){
							  icon.setImageResource(R.drawable.icon_thread_hot_reply);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic")){
							  icon.setImageResource(R.drawable.icon_thread_readonly);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_reply);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_new);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_new_hot);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_hot);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.icon_thread_readonly_hot_reply);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else {
							  icon.setImageResource(R.drawable.icon_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
					  }
				  } else if(getIconPref().equals("science")){
					  if(getStylePref().equals("light")){
						  if(THREADS.get(position).get("status").equals("Topic with new content")){
							  icon.setImageResource(R.drawable.default_icon);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic")){
							  icon.setImageResource(R.drawable.read_icon);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic - you replied.")){
							  icon.setImageResource(R.drawable.replied_icon);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.newreply);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content")){
							  icon.setImageResource(R.drawable.default_icon);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic")){
							  icon.setImageResource(R.drawable.read_icon);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.newreply);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic - you replied.")){
							  icon.setImageResource(R.drawable.replied_icon);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else {
							  icon.setImageResource(R.drawable.default_icon);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
					  } else {
						  if(THREADS.get(position).get("status").equals("Topic with new content")){
							  icon.setImageResource(R.drawable.default_icon);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic")){
							  icon.setImageResource(R.drawable.read_icon);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic - you replied.")){
							  icon.setImageResource(R.drawable.replied_icon);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.newreply);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content")){
							  icon.setImageResource(R.drawable.default_icon);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic")){
							  icon.setImageResource(R.drawable.read_icon);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.newreply);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic - you replied.")){
							  icon.setImageResource(R.drawable.replied_icon);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.locked);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else {
							  icon.setImageResource(R.drawable.default_icon);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
					  }
				  } else if(getIconPref().equals("pixel")){
					  if(getStylePref().equals("light")){
						  if(THREADS.get(position).get("status").equals("Topic with new content")){
							  icon.setImageResource(R.drawable.unread_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic")){
							  icon.setImageResource(R.drawable.read_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic - you replied.")){
							  icon.setImageResource(R.drawable.read_replied_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.unread_replied_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content")){
							  icon.setImageResource(R.drawable.unread_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic")){
							  icon.setImageResource(R.drawable.read_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.unread_replied_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic - you replied.")){
							  icon.setImageResource(R.drawable.read_replied_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else {
							  icon.setImageResource(R.drawable.unread_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
					  } else {
						  if(THREADS.get(position).get("status").equals("Topic with new content")){
							  icon.setImageResource(R.drawable.unread_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic")){
							  icon.setImageResource(R.drawable.read_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic - you replied.")){
							  icon.setImageResource(R.drawable.read_replied_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.unread_replied_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content")){
							  icon.setImageResource(R.drawable.unread_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic")){
							  icon.setImageResource(R.drawable.read_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.unread_replied_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic - you replied.")){
							  icon.setImageResource(R.drawable.read_replied_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.locked_thread);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else {
							  icon.setImageResource(R.drawable.unread_thread);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
					  }
				  } else if(getIconPref().equals("none")){
					  if(getStylePref().equals("light")){
						  if(THREADS.get(position).get("status").equals("Topic with new content")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic - you replied.")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic - you replied.")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else {
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(0,0,0));
						  }
					  } else {
						  if(THREADS.get(position).get("status").equals("Topic with new content")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic - you replied.")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic with new content - you replied.")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Topic - you replied.")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic with new content")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(221,221,221));
						  }
						  else if(THREADS.get(position).get("status").equals("Hot Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else if(THREADS.get(position).get("status").equals("Read-Only Topic - you replied")){
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT);
							  text2.setTextColor(Color.rgb(34,34,34));
						  }
						  else {
							  icon.setImageResource(R.drawable.trans);
							  text2.setTypeface(Typeface.DEFAULT_BOLD);
							  text2.setTextColor(Color.rgb(255,255,255));
						  }
					  }
				  }
			  }
			  else {
				  TextView text1=(TextView)row.findViewById(R.id.text1);
				  text1.setText("Error");
				  TextView text2=(TextView)row.findViewById(R.id.text2);
				  text2.setText("Connection timed out");
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
				return row;
			  
		}
		
	}
	
	@Override
	public void onBackPressed() {
		//Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();
		if(getlistTask != null){
			getlistTask.cancel(true);
		}
		
		finish();
	//return;
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
    	case R.id.homescreen:
    		Intent index = new Intent(getApplicationContext(), AndroidSwim.class);	
    		startActivity(index);
    		return true;
    	case R.id.prfs:
    		settings();
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
		if(boardId != null){
			Intent showContent = new Intent(getApplicationContext(), ThreadPost.class);
			showContent.setData(Uri.parse(boardId));
			startActivity(showContent);
		} else {
			Toast.makeText(getApplicationContext(), "Cannot make thread at this time", Toast.LENGTH_SHORT).show();
		}
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
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.thread_menus, menu);
	  MenuItem trker = menu.findItem(R.id.tracker);
	  trker.setTitle("Go to "+THREADS.get(info.position).get("author")+"'s Tracker");
	  MenuItem msgr = menu.findItem(R.id.sendpm);
	  msgr.setTitle("Send PM to "+THREADS.get(info.position).get("author"));
	  MenuItem opnr = menu.findItem(R.id.openthread);
	  opnr.setTitle("Open \""+THREADS.get(info.position).get("title")+"\"");
	  MenuItem kdo = menu.findItem(R.id.kudo);
	  kdo.setTitle("Open "+THREADS.get(info.position).get("author")+"'s Profile");
	  MenuItem lnk = menu.findItem(R.id.externallinks);
	  lnk.setTitle("Bookmark "+THREADS.get(info.position).get("title")+"");
	  MenuItem edt = menu.findItem(R.id.edit);
	  edt.setTitle("Subscribe to "+THREADS.get(info.position).get("title")+""); 
	  MenuItem frnd = menu.findItem(R.id.friendAdd);
	  frnd.setTitle("Add to friends: "+THREADS.get(info.position).get("author"));
	  if(!PAGES.get(info.position).get(0).containsKey("page")){
		  MenuItem lnks = menu.findItem(R.id.goPage);
		  lnks.setVisible(false);
	  }
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
	  		case R.id.tracker:
	  			//Toast.makeText(getApplicationContext(), REPLIES.get(info.position).get("profile"), Toast.LENGTH_SHORT).show();
	  			String trackerurl = THREADS.get(info.position).get("profile").replaceAll("/user/viewprofilepage", "/forums/recentpostspage")+"/post-type/message/page/1";
	  			Intent tracker = new Intent(getApplicationContext(), Tracker.class);
	  			tracker.setData(Uri.parse(trackerurl));
	  			startActivity(tracker);
	  			return true;
	  		case R.id.sendpm:
	  			String msgAuthId = "";
	  			String author = THREADS.get(info.position).get("author"); 
	  			String subject = "";
	  			String msg = "";
	  			Bundle b = new Bundle();
	  			b.putStringArray("data", new String[]{msgAuthId, author, subject, msg});
	  			Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
	  			pmContent.putExtras(b);
	  			startActivity(pmContent);
	  			return true;
	  		case R.id.openthread:
	  			String urlcontent = POSTS.get(info.position).replaceAll("^.*(?:td\\-p\\/)", "").replaceAll("/jump-to/first-unread-message", "?page_size=25&page=1");
        		Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
        		showurlContent.setData(Uri.parse(urlcontent));
        		startActivity(showurlContent);
	  			return true;
	  		case R.id.goPage:
	  			final Dialog dlg = new Dialog(ThreadListing.this);
		    	dlg.setContentView(R.layout.link_selector);
		    	ListView sel = (ListView) dlg.findViewById(R.id.linksel);
		    	
		    	adaptr = new SimpleAdapter(this, PAGES.get(info.position), R.layout.link_sel_row, new String[]{"page"}, new int[]{R.id.link_title});
		    	pgs = PAGES.get(info.position);
		    	sel.setAdapter(adaptr);
		    	dlg.setTitle(PAGES.get(info.position).get(0).get("title"));
		    	
		    	dlg.show();
		    	sel.setOnItemClickListener(new OnItemClickListener(){
		    		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		    			//Toast.makeText(getApplicationContext(), lnks.get(position).get("extlinkhref"), Toast.LENGTH_SHORT).show();
		    			Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
				    	String pg = pgs.get(position).get("base").replaceAll("PAGE_NUMBER_HERE", pgs.get(position).get("page"));
		    			showurlContent.setData(Uri.parse(pg));
				    	startActivity(showurlContent);
		    			dlg.dismiss();
		    		}
		    	});
	  			return true;
	  		case R.id.friendAdd:
	  			String friend = THREADS.get(info.position).get("author");
	  		  	new addFriend(friend).execute();
	  		  	return true;
	  		case R.id.kudo:
	  			String profile = "http://boards.adultswim.com/restapi/vc/users/login/"+THREADS.get(info.position).get("author");
				Intent index = new Intent(getApplicationContext(), Profile.class);	
				index.setData(Uri.parse(profile));
				startActivity(index);
				return true;
	  		case R.id.externallinks:
	  			new doSub("bookmark", POSTS.get(info.position).replaceAll("^.*(?:td\\-p\\/)", "").replaceAll("/jump-to/first-unread-message", "")).execute();
	  			return true;
	  		case R.id.edit:
	  			new doSub("email", POSTS.get(info.position).replaceAll("^.*(?:td\\-p\\/)", "").replaceAll("/jump-to/first-unread-message", "")).execute();
	  			return true;
	  		default:
	  			return super.onContextItemSelected(item);
		}
	}
	private class doSub extends AsyncTask<String, Integer, String>{
		String id;
		String type;
		public doSub(String s1, String s2){
			super();
			id = s2;
			type = s1;
		}
		@Override
		protected String doInBackground(String... params) {
			
			HttpPost httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/threads/id/"+id+"/subscriptions/users/self/add?subscription.type="+type);
			System.out.println("---------------------------------- http://boards.adultswim.com/restapi/vc/threads/id/"+id+"/subscriptions/users/self/add?subscription.type="+type);
			HttpResponse response1;
			httppost.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			String line = null;
			try {
				response1 = mClient.execute(httppost);
				HttpEntity httpEntity = response1.getEntity();
				line = EntityUtils.toString(httpEntity);
			} catch (UnsupportedEncodingException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (MalformedURLException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (IOException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			}
			//System.out.println(line);
			
			return null;
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
    private class getBoard extends AsyncTask<String, Integer, String>{
		String theboard;
		public getBoard(String string) {
			// TODO Auto-generated constructor stub
			super();
			theboard = string;
		}
		@Override
		protected String doInBackground(String... params) {
			System.out.println("----------------------------- board id = "+theboard);
			String link ="";
			HttpGet httpget = new HttpGet("http://boards.adultswim.com/restapi/vc/boards/id/"+theboard+"/web_page_url");
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
				
				link = result.replaceAll("(?s)^.*(?:string\">)", "").replaceAll("(?s)</.*", "");	
				
				System.out.println("----------------------------- board link = "+link);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			return link;
		}
		@Override
		protected void onPostExecute(String result){
			String url = result+"/page/1";
			System.out.println("----------------------------- using = "+url);
			Intent showurlContent = new Intent(getApplicationContext(), ThreadListing.class);
    		showurlContent.setData(Uri.parse(url));
    		startActivity(showurlContent);
		}
		
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