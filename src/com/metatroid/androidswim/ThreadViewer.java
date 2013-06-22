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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
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

public class ThreadViewer extends ListActivity {
	public static ThreadViewer ACTIVE_INSTANCE;
	private static final int LOGIN_DIALOG = 0;
	private static final int DIALOG_LOGGING_IN = 1;
	private static final int PAGE_DIALOG = 2;
	private final ASsettings mSettings = new ASsettings();
	private final DefaultHttpClient client = AndroidSwimSettings.getClient();
	ArrayList<HashMap<String,String>> REPLIES = new ArrayList<HashMap<String,String>>();
	final ArrayList<HashMap<String,String>> PAGES = new ArrayList<HashMap<String,String>>();
	final ArrayList<ArrayList<HashMap<String,String>>> LINKS = new ArrayList<ArrayList<HashMap<String,String>>>();
	String[] msgs;
	String[] pages;
	String[] pagens;
	final List<String> replylinks = new ArrayList<String>();
	int page;
	//String title = "";
	Intent starterintent;
	//ProgressBar progressBar;
	String nextpage = "";
	String prevpage = "";
	String firstpage = "";
	String lastpage = "";
	String replyto = "";
	String base;
	SpecialAdapter adapter;
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
	String urlcontent;
	String replyCount;
	getThreadId getThreadIdTask = null;
	getlist getlistTask = null;
	HttpGet httpget = null;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		ACTIVE_INSTANCE = this;
		starterintent = getIntent();
		setContentView(R.layout.loading);
		Intent launchingIntent = getIntent();
		urlcontent = launchingIntent.getData().toString();
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
    	//System.out.println("!!!!!!!! getting posts");
    	if(urlcontent.contains("MessageID")){
    		getThreadIdTask = new getThreadId();
    		getThreadIdTask.execute();
    	} else {
    		getlistTask = new getlist();
    		getlistTask.execute();
    	}
	}
	@Override 
    public void onDestroy() { 
        super.onDestroy(); 
           ACTIVE_INSTANCE = null; 
    }
		
	
	
	private class getlist extends AsyncTaskEx<Void, Integer, Void>{
		//int progress;
		
		
		@Override
		protected Void doInBackground(Void... params) {
			getPosts();
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
	        if(httpget != null){
	        	httpget.abort();
	        }
	    }
		protected void onPostExecute(Void result){
			//System.out.println("!!!!!!!! setting up view start");
				setListAdapter(adapter);
				
				if(REPLIES.isEmpty()){
					HashMap<String,String> temp = new HashMap<String,String>();
					temp.put("author", "error");
					temp.put("title", "Connection timed out");
					REPLIES.add(temp);
				}
				
					setContentView(R.layout.item_list);
					TextView title = (TextView) findViewById(R.id.title);
					Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
					title.setTypeface(font);
					ListView lv3 = getListView();
					
				if(!REPLIES.isEmpty()){
					
					lv3.setItemsCanFocus(true);
					
					
					//setTitle(title);
				}
			registerForContextMenu(lv3);
			new markRead(urlcontent).execute();
			
			if (replyCount == null){
				replyCount = "1";
			}
			Double rCount = Double.parseDouble(replyCount);
	    	Double pCount = (rCount/25)+0.5;
	    	Integer numberOfPages = (int) Math.round(pCount);
	    		    	
			HashMap<String,String> lastp = new HashMap<String,String>();
			lastp.put("number", Integer.toString(numberOfPages));
			lastp.put("text", "Last Page");
			PAGES.add(lastp);
	    	for(int p=1;p<numberOfPages;p++){
	    		HashMap<String,String> pagetemp = new HashMap<String,String>();
	    		String pagenum = Integer.toString(p);
				pagetemp.put("number", pagenum);
				pagetemp.put("text", "Page "+pagenum);
				PAGES.add(pagetemp);
	    	}
	    	pages = new String[PAGES.size()];
	    	pagens = new String[PAGES.size()];
			for(int x=0;x<PAGES.size();x++){
				pages[x] = PAGES.get(x).get("number");
				pagens[x] = PAGES.get(x).get("text");
			}
	    	
		}
		@Override
		protected void onPreExecute(){
			//progress = 0;
		}
		//protected void onProgressUpdate(Integer... values){
		//	progressBar.setProgress(values[0]);
		//}
		
	}
	
	public void getPosts(){
		if(getStylePref().equals("default")){
			adapter = new SpecialAdapter(this, REPLIES, R.layout.thread_row, new String[]{"author","post"}, new int[]{R.id.texta,R.id.textb});
		} else if(getStylePref().equals("light")){
			adapter = new SpecialAdapter(this, REPLIES, R.layout.thread_row_light, new String[]{"author","post"}, new int[]{R.id.texta,R.id.textb});
		} else if(getStylePref().equals("dark")){
			adapter = new SpecialAdapter(this, REPLIES, R.layout.thread_row_dark, new String[]{"author","post"}, new int[]{R.id.texta,R.id.textb});
		}
		ListView lv3 = getListView();
        lv3.setTextFilterEnabled(true);
        //registerForContextMenu(getListView());
		
		//client.getCookieStore().getCookies();
		httpget = new HttpGet("http://boards.adultswim.com/restapi/vc/threads/id/"+urlcontent);
		HttpResponse response1;
		httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
		String line = null;
		try {
			HttpResponse httpResponse = client.execute(httpget);
			HttpEntity httpEntity = httpResponse.getEntity();
			line = EntityUtils.toString(httpEntity);
		} catch (UnsupportedEncodingException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (MalformedURLException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (IOException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		}
		if(!line.contains("status=\"success\"")){
			String error = line.replaceAll("(?s)(?:.*)<message>", "").replaceAll("(?s)</message.*", "");
			//Toast.makeText(getApplicationContext(), "Error: "+error, Toast.LENGTH_LONG).show();
			HashMap<String,String> temp1 = new HashMap<String,String>();
			temp1.put("post", error);
			temp1.put("subject", "error");
			REPLIES.add(temp1);
		} else {
			
			
			Document doc = ASsettings.XMLfromString(line);
			HashMap<String,String> temp1 = new HashMap<String,String>();
			
			
			NodeList thrd00 = doc.getElementsByTagName("board");
			if(thrd00 != null){
			Element thrdstng00 = (Element) thrd00.item(0);
			//NodeList thrdstrngs = thrdstng.getChildNodes();
			String board = thrdstng00.getAttribute("href").replaceAll("/boards/id/", "");
			temp1.put("boardId", board);
			
			NodeList nodes0 = doc.getElementsByTagName("messages");
			Node msgItem = nodes0.item(0);
			Element messages = (Element) msgItem;
			NodeList msgNodes = messages.getChildNodes();
			for(int m = 0; m < msgNodes.getLength(); m++){
				Node msgNode = (Node)msgNodes.item(m);
				if(msgNode.getNodeName().equals("count")){
					replyCount = msgNode.getChildNodes().item(0).getNodeValue();
				}
			}
			
			
			NodeList nodes2 = messages.getElementsByTagName("topic");
			Node item2 = nodes2.item(0);
			Element itm2 = (Element) item2;
			NodeList ids = itm2.getElementsByTagName("id");
			Element threadid = (Element) ids.item(0);
			NodeList idnums = threadid.getChildNodes();
			String idnum = ((Node) idnums.item(0)).getNodeValue();
			//System.out.println("---------------------thread id = "+idnum);
			temp1.put("id", idnum);
			
			NodeList sub0 = itm2.getElementsByTagName("subject");
			Element sub1 = (Element) sub0.item(0);
			NodeList sub2 = sub1.getChildNodes();
			String subject ="";
			for(int s=0;s<sub2.getLength();s++){
				subject += ((Node) sub2.item(s)).getNodeValue(); 
			}
			temp1.put("subject", subject);
			
			NodeList body = itm2.getElementsByTagName("body");
			Element bodystng = (Element) body.item(0);
			NodeList bodystrngs = bodystng.getChildNodes();
			//((Node) bodystrngs.item(0))
			String openingPost = "";
			for(int p=0;p<bodystrngs.getLength();p++){
				openingPost += ((Node) bodystrngs.item(p)).getNodeValue();
			}
			
			//System.out.println("---------------------op is = "+openingPost);
			String n1 = urlcontent.replaceAll("(?:.*)=", "");
			if(n1.equals("1")){
				temp1.put("post", openingPost);
			} else {
				temp1.put("post", subject);
			}
				
			
				
			NodeList nodes3 = itm2.getElementsByTagName("author");
			Element msgAuth1 = (Element) nodes3.item(0);
			String msgAuthId1 = msgAuth1.getAttribute("href").replaceAll("(?:.*)id/", "");
			temp1.put("msgAuthId", msgAuthId1);
			for (int a = 0; a < nodes2.getLength(); a++) {
				Node item3 = nodes3.item(a);
				Element itm3 = (Element) item3;
					
				NodeList auths1 = itm3.getElementsByTagName("login");
				Element authname1 = (Element) auths1.item(0);
				NodeList auths12 = authname1.getChildNodes();
				String author1 = ((Node) auths12.item(0)).getNodeValue();
				//System.out.println("---------------------thread author = "+author1);
				if(n1.equals("1")){
					temp1.put("author", author1);
				} else {
					temp1.put("author", "Page "+n1);
				}
			}
			temp1.put("ref", "OP");
			REPLIES.add(temp1);
			
			
			NodeList nodes = doc.getElementsByTagName("message");
			
			Double rCount = Double.parseDouble(replyCount);
	    	Double pCount = (rCount/25)+0.5;
	    	Integer pageCount = (int) Math.round(pCount);
	    	Integer msgsCount;
			if(pageCount.toString().equals(n1)){
				msgsCount = nodes.getLength() -1;
			} else {
				msgsCount = nodes.getLength();
			}
			 
			for (int i = 0; i < msgsCount; i++) {
				HashMap<String,String> temp = new HashMap<String,String>();
				Node item = nodes.item(i);
				Element itm = (Element) item;
								
				NodeList ids1 = itm.getElementsByTagName("id");
				Element threadid1 = (Element) ids1.item(0);
				NodeList idnums1 = threadid1.getChildNodes();
				String idnum1 = ((Node) idnums1.item(0)).getNodeValue();
				//System.out.println("---------------------msg id = "+idnum1);
				temp.put("id", idnum1);
				
				NodeList ref00 = itm.getElementsByTagName("parent");
				if(ref00 != null){
				Element ref0 = (Element) ref00.item(0);
				if(ref0 != null){
				String ref = ref0.getAttribute("href").replaceAll("/messages/id/", "");
				temp.put("ref", ref);
				//System.out.println("---------------------reference number = "+ref);
				}}
				
				NodeList sub01 = itm.getElementsByTagName("subject");
				Element sub11 = (Element) sub01.item(0);
				NodeList sub21 = sub11.getChildNodes();
				String subject1 ="";
				for(int s=0;s<sub21.getLength();s++){
					subject1 += ((Node) sub21.item(s)).getNodeValue(); 
				}
				temp.put("subject", subject);
				
				temp.put("post", "loading");
						
				NodeList nodes13 = itm.getElementsByTagName("author");
				Element msgAuth = (Element) nodes13.item(0);
				String msgAuthId = msgAuth.getAttribute("href").replaceAll("(?:.*)id/", "");
				temp.put("msgAuthId", msgAuthId);
				for (int n = 0; n < nodes13.getLength(); n++) {
					Node item12 = nodes13.item(n);
					Element itm12 = (Element) item12;
					
					NodeList auths = itm12.getElementsByTagName("login");
					Element authname = (Element) auths.item(0);
					NodeList auths2 = authname.getChildNodes();
					String author = ((Node) auths2.item(0)).getNodeValue();
					//System.out.println("---------------------msg author = "+author);
					temp.put("author", author);
				}
			
			
				
			REPLIES.add(temp);
				
			}
			msgs = new String[REPLIES.size()];
			for(int q = 0; q < REPLIES.size(); q++){
				msgs[q] = REPLIES.get(q).get("post");
			}
			
			//System.out.println("!!!!!!!! finished parse");
			} else {
				temp1.put("post", "no response from server");
				temp1.put("subject", "error");
				REPLIES.add(temp1);
				//Toast.makeText(getApplicationContext(), "No response from server - try again", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	private class markRead extends AsyncTaskEx<Void, Void, Void> {
		String tid;
		String page;
		public markRead(String string) {
			// TODO Auto-generated constructor stub
			super();
			tid = string.replaceAll("\\?page.*", "");
			page = string.replaceAll("^.*(?:=)", "");
		}

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			HttpGet httpget = new HttpGet("http://boards.adultswim.com/restapi/vc/threads/id/"+tid+"/web_page_url");
			HttpResponse response;
			httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			String result = "";
			String finalurl = "";
			try {
				response = client.execute(httpget);
				HttpEntity entity1 = response.getEntity();
				try{
				InputStream in = entity1.getContent();
				result = IOUtils.toString(in);
				} catch(Exception ex){
					result = "error";
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(result.contains("status=\"success\"")){
				finalurl = result.replaceAll("(?s)(?:.*)string\">", "").replaceAll("(?s)</string.*", "");
				//System.out.println("---------------url found - "+finalurl);
			} else {
				//System.out.println(result);
			}
			
			if(!finalurl.equals("")){
				HttpGet httpget2 = new HttpGet(finalurl+"/page/"+page);
				HttpResponse response2;
				httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
				try {
					response = client.execute(httpget2);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			
			return null;
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
		public int getViewTypeCount(){
			return 1;
		}
		@Override
		public int getItemViewType(int position){
			return 1;
			
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent){
			
			 ViewHolder holder;
			 View row = convertView;
			 
			  if(row==null){
			   LayoutInflater inflater=getLayoutInflater();
			   if(getStylePref().equals("default")){
				   row=inflater.inflate(R.layout.thread_row, parent, false);
				   holder = new ViewHolder();
				   holder.text1 = (TextView)row.findViewById(R.id.texta);
				   holder.text1a = (TextView)row.findViewById(R.id.texta2);
				   holder.text2 = (TextView)row.findViewById(R.id.textb);
				   
				   row.setTag(holder);
			   } else if(getStylePref().equals("light")){
				   row=inflater.inflate(R.layout.thread_row_light, parent, false);
				   holder = new ViewHolder();
				   holder.text1 = (TextView)row.findViewById(R.id.texta);
				   holder.text1a = (TextView)row.findViewById(R.id.texta2);
				   holder.text2 = (TextView)row.findViewById(R.id.textb);
				   
				   row.setTag(holder);
			   } else if(getStylePref().equals("dark")){
				   row=inflater.inflate(R.layout.thread_row_dark, parent, false);
				   holder = new ViewHolder();
				   holder.text1 = (TextView)row.findViewById(R.id.texta);
				   holder.text1a = (TextView)row.findViewById(R.id.texta2);
				   holder.text2 = (TextView)row.findViewById(R.id.textb);
				   
				   row.setTag(holder);
			   } else {
				   row=inflater.inflate(R.layout.thread_row, parent, false);
				   holder = new ViewHolder();
				   holder.text1 = (TextView)row.findViewById(R.id.texta);
				   holder.text1a = (TextView)row.findViewById(R.id.texta2);
				   holder.text2 = (TextView)row.findViewById(R.id.textb);
				   
				   row.setTag(holder);
			   }
			  } else {
				  holder = (ViewHolder) row.getTag();
			  }
			  
			  
			 // System.out.println("!!!!!!!! finalizing view");
			  if(msgs != null){
			  if(msgs.length != 0 && !REPLIES.isEmpty()){
				  if(REPLIES.get(position) != null){
					  if(REPLIES.get(position).get("post") != null){
					  
				  
				  
						  String auth = REPLIES.get(position).get("author");
						  String post = msgs[position];
						  
						  String ref = REPLIES.get(position).get("ref");
						  String ref0 = "";
						  
						 // System.out.println("-------------trying to match "+REPLIES.get(position).get("ref"));
						  if(ref != null){
						  for(int n=0;n<REPLIES.size();n++){
							  //System.out.println("-------------"+REPLIES.get(n).get("id"));
							  if(REPLIES.get(n).get("id").equals(ref)){
								  ref0 = "Reply to: "+REPLIES.get(n).get("author");
							  }
						  }
						  } else {ref0 = "something broke?";}
						  holder.text1.setText(auth);
						  holder.text1a.setText(ref0);
						  holder.text2.setText(Html.fromHtml(post, new ImageGetter(){
							  
							  public Drawable getDrawable(String source) {
							        int id;
							        if(getSmileyPref().equals("robot")){
								        if (source.contains("mad")) {
								            id = R.drawable.mad;
								        }
								        else if (source.contains("frustrated")) {
								            id = R.drawable.frustrated;
								        }
								        else if (source.contains("embarrassed")) {
								            id = R.drawable.embarrassed;
								        }
								        else if (source.contains("lol")) {
								            id = R.drawable.lol;
								        }
								        else if (source.contains("heart")) {
								            id = R.drawable.heart;
								        }
								        else if (source.contains("happy") && !source.contains("very")) {
								            id = R.drawable.happy;
								        }
								        else if (source.contains("sad")) {
								            id = R.drawable.sad;
								        }
								        else if (source.contains("tongue")) {
								            id = R.drawable.tongue;
								        }
								        else if (source.contains("surprised")) {
								            id = R.drawable.surprise;
								        }
								        else if (source.contains("very-happy")) {
								            id = R.drawable.veryhappy;
								        }
								        else if (source.contains("indifferent")) {
								            id = R.drawable.indifferent;
								        }
								        else if (source.contains("wink")) {
								            id = R.drawable.wink;
								        }
								        else {
								            id = R.drawable.unknown_image;
								        }
							        } else if(getSmileyPref().equals("plain")){
								        if (source.contains("mad")) {
								            id = R.drawable.pmad;
								        }
								        else if (source.contains("frustrated")) {
								            id = R.drawable.frustrated;
								        }
								        else if (source.contains("embarrassed")) {
								            id = R.drawable.embarrassed;
								        }
								        else if (source.contains("lol")) {
								            id = R.drawable.lol;
								        }
								        else if (source.contains("heart")) {
								            id = R.drawable.heart;
								        }
								        else if (source.contains("happy") && !source.contains("very")) {
								            id = R.drawable.phappy;
								        }
								        else if (source.contains("sad")) {
								            id = R.drawable.psad;
								        }
								        else if (source.contains("tongue")) {
								            id = R.drawable.ptongue;
								        }
								        else if (source.contains("surprised")) {
								            id = R.drawable.psurprised;
								        }
								        else if (source.contains("very-happy")) {
								            id = R.drawable.pveryhappy;
								        }
								        else if (source.contains("indifferent")) {
								            id = R.drawable.pindifferent;
								        }
								        else if (source.contains("wink")) {
								            id = R.drawable.pwink;
								        }
								        else {
								            id = R.drawable.unknown_image;
								        } 
							        } else {
							        	id = R.drawable.trans;
							        }

							        Drawable d = getResources().getDrawable(id);
							        d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
							        return d;
							    }
							  
						  }, null));
						  
						  if(post.equals("loading")){
							 new getMessage(REPLIES.get(position).get("id"), position, row).execute();
							 
						  }
						  URLSpan[] urls = holder.text2.getUrls();
						  ArrayList<HashMap<String, String>> lnks = new ArrayList<HashMap<String, String>>();
						  if(urls.length != 0){
							  for(int u=0;u<urls.length;u++){
								  HashMap<String,String> temp = new HashMap<String,String>();
								  temp.put("link", urls[u].getURL());
								  lnks.add(temp);
								  LINKS.add(position, lnks);
								  
							  }
						  } else {
							  HashMap<String,String> temp = new HashMap<String,String>();
							  temp.put("none", "none");
							  lnks.add(temp);
							  LINKS.add(position, lnks);
						  }
						  holder.text2.setMovementMethod(LinkMovementMethod.getInstance());
					  } else {
						  holder.text1.setText("Error");
					  }
				  } else {
					  holder.text1.setText("Error");
				  }
			  } } else {
				  
				  holder.text1.setText("Error");
				  
				  holder.text2.setText("Connection timed out");
			  }
			  
			  if(!REPLIES.isEmpty()){
				  holder.text2.setOnClickListener(new OnClickListener(){
			        	public void onClick(View v) {
			        		String replyId = REPLIES.get(position).get("id");
			    			String msgTitle = REPLIES.get(position).get("subject");
			    			String msgContent = msgs[position];
			    			String msgAuthor = REPLIES.get(0).get("author");
			    			Bundle b2 = new Bundle();
			    			b2.putStringArray("data", new String[]{replyId, msgTitle, msgContent, msgAuthor});
			      			Intent replyContent = new Intent(getApplicationContext(), ApiReply.class);
			      			replyContent.putExtras(b2);
			      			startActivity(replyContent);
			        		
			      			
			        	}
			        });
			  }
			 
			  //System.out.println("!!!!!!!! view finished");
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
				    	reply();
				    }
			  });
			  //System.out.println("!!!!!!!! should be visible now");
			return row;
			
		}
		
	}
	private class getThreadId extends AsyncTaskEx<String, Integer, String> {
		
		@Override
		protected void onPostExecute(String result){
			if(result.equals("error")){
				Toast.makeText(getApplicationContext(), "It broke :[ ... contact Galaxian", Toast.LENGTH_LONG).show();
			} else {
				urlcontent = result.replaceAll("(?:.*)id/", "")+"?page_size=25&page=1";
				new getlist().execute();
			}
		}
		@Override
	    protected void onCancelled() {
	        //client.getConnectionManager().shutdown();
	        httpget.abort();
	    }
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String msg = urlcontent.replaceAll("MessageID", "");
			String msgurl = "http://boards.adultswim.com/restapi/vc/messages/id/"+msg;
			httpget = new HttpGet(msgurl);
			httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			String line = null;
			try {
				HttpResponse httpResponse = client.execute(httpget);
				HttpEntity httpEntity = httpResponse.getEntity();
				line = EntityUtils.toString(httpEntity);
			} catch (UnsupportedEncodingException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (MalformedURLException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (IOException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			}
			Document doc = ASsettings.XMLfromString(line);
			NodeList nodes = doc.getElementsByTagName("message");
			
			
			Node item = nodes.item(0);
			Element itm = (Element) item;
			NodeList thrd = itm.getElementsByTagName("thread");
			Element thrdstng = (Element) thrd.item(0);
			//NodeList thrdstrngs = thrdstng.getChildNodes();
			String thread;
			if(thrdstng != null){
				thread = thrdstng.getAttribute("href");
			} else {
				thread = "error";
			}
			
			return thread;
		}
		
	}
	private class getMessage extends AsyncTaskEx<String, Integer, String> {
		String url;
		int pos;
		String msgPost = "";
		View line;
		HashMap<String,String> temp = new HashMap<String,String>();
		
		
		public getMessage(String string, int index, View row) {
			// TODO Auto-generated constructor stub
			super();
			url = "http://boards.adultswim.com/restapi/vc/messages/id/"+string;
			pos = index;
			line = row;
		}
		
		@Override
    	protected void onPostExecute(String result){
			//TextView textb=(TextView)line.findViewById(R.id.textb);
			//textb.setText(Html.fromHtml(result));
			msgs[pos] = result;
			
			//REPLIES.add(pos, temp);
			//REPLIES.remove(pos-1);
			
			adapter.notifyDataSetChanged();
		}
		@Override
		protected String doInBackground(String... string) {
			// TODO Auto-generated method stub
			//System.out.println("---------------------------------getting message for row #"+pos);
			String msgurl = url;
			HttpGet httpget = new HttpGet(msgurl);
			httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			String line = null;
			try {
				HttpResponse httpResponse = client.execute(httpget);
				HttpEntity httpEntity = httpResponse.getEntity();
				line = EntityUtils.toString(httpEntity);
			} catch (UnsupportedEncodingException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (MalformedURLException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (IOException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			}
			if(line.contains("status=\"success\"")){
				
				Document doc = ASsettings.XMLfromString(line);
				NodeList nodes = doc.getElementsByTagName("message");
				
				if(nodes != null){
					Node item = nodes.item(0);
					Element itm = (Element) item;
					
					NodeList body = itm.getElementsByTagName("body");
					Element bodystng = (Element) body.item(0);
					NodeList bodystrngs = bodystng.getChildNodes();
					//((Node) bodystrngs.item(0))
					
					for(int p=0;p<bodystrngs.getLength();p++){
						msgPost += ((Node) bodystrngs.item(p)).getNodeValue();
						
					}
				}
			} else {
				msgPost += "error";
			}
			//String openingPost = ((Node) bodystrngs.item(1)).getNodeValue();
			
			//temp.put("post", msgPost);
			//temp.put("id", REPLIES.get(pos).get("id"));
			//temp.put("author", REPLIES.get(pos).get("author"));
			
			return msgPost;
		}


		
		
		
	}
	
	@Override
	public void onBackPressed() {
		//Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();
		if(getlistTask != null){
			getlistTask.cancel(true);
		}
		if(getThreadIdTask != null){
			getThreadIdTask.cancel(true);
		}
		finish();
	//return;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.threadview_menu, menu);
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
    	case R.id.newpm:
    		compose();
    		return true;
    	case R.id.inbox:
    		inbox();
    		return true;
    	case R.id.homescreen:
    		Intent index = new Intent(getApplicationContext(), AndroidSwim.class);	
    		startActivity(index);
    		return true;
    	case R.id.paging:
    		pages();
    		return true;
    	case R.id.replythread:
    		reply();
    		return true;
    	case R.id.prfs:
    		settings();
    		return true;
    	case R.id.prv:
    		prev();
    		return true;
    	case R.id.nxt:
    		next();
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
    	AlertDialog ad;
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
    	case PAGE_DIALOG:
    		Builder builder = new AlertDialog.Builder(this)
    		.setTitle("Select Page")
    		.setSingleChoiceItems(pagens, -1, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int item) {
    				//Toast.makeText(getApplicationContext(), PAGES.get(item).get("url"), Toast.LENGTH_SHORT).show();
    				String pgurl = urlcontent.replaceAll("\\?page.*", "")+"?page_size=25&page="+PAGES.get(item).get("number");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
	        		showurlContent.setData(Uri.parse(pgurl));
	        		startActivity(showurlContent);
	        		
    			}
    		});
    		ad = builder.create();
    		dialog = ad;
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
    			//System.out.println("------------------------ PM SERVICE OFF");
    		}
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
    private String getSmileyPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String emoticon = prefs.getString("smileyPref", "robot");
		return emoticon;
	}
    private boolean getpmPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean pmstate = prefs.getBoolean("notificationpref", true);
		return pmstate;
	}
    private void reply() {
    	String replyId = REPLIES.get(0).get("id");
		String msgTitle = REPLIES.get(0).get("subject");
		String msgContent = msgs[0];
		String msgAuthor = REPLIES.get(0).get("author");
		Bundle b2 = new Bundle();
		b2.putStringArray("data", new String[]{replyId, msgTitle, msgContent, msgAuthor});
		Intent replyContent = new Intent(getApplicationContext(), ApiReply.class);
		replyContent.putExtras(b2);
		startActivity(replyContent);
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
    	
    	if(replyCount.replaceAll("\\d+","").length() > 0 && Integer.parseInt(replyCount) <= 0){
    		Toast.makeText(getApplicationContext(), "no pages or something broke", Toast.LENGTH_SHORT).show();
    	} else {
	    	Double rCount = Double.parseDouble(replyCount);
	    	Double pCount = (rCount/25)+0.5;
	    	Integer pageCount = (int) Math.round(pCount);
	    	//System.out.println("-----------------------------------------numbers--- replies:" + Integer.toString(rCount.intValue()) + "pages: " + pageCount + "raw count: " + pCount.toString() );
	    	
	    	if(pageCount > 1){
				String currentPageNum = urlcontent.replaceAll("(?:.*)=", "");
				String currentThreadId = urlcontent.replaceAll("\\?page.*", "");
				Integer cPageNum = Integer.parseInt(currentPageNum);
				Integer nPageNum = cPageNum + 1;
				String nextPageNum = Integer.toString(nPageNum);
				String nextPage = currentThreadId+"?page_size=25&page="+nextPageNum;
				if(pageCount > cPageNum){
			    	Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
			    	showurlContent.setData(Uri.parse(nextPage));
			    	startActivity(showurlContent);
				} else {
					Toast.makeText(getApplicationContext(), "No more pages", Toast.LENGTH_SHORT).show();
				}
	    	} else {
				Toast.makeText(getApplicationContext(), "No more pages", Toast.LENGTH_SHORT).show();
			}
    	}
    }
    private void prev(){
    	String currentPageNum = urlcontent.replaceAll("(?:.*)=", "");
		String currentThreadId = urlcontent.replaceAll("\\?page.*", "");
		Integer cPageNum = Integer.parseInt(currentPageNum);
		if(cPageNum > 1){	
			Integer nPageNum = cPageNum - 1;
			String prevPageNum = Integer.toString(nPageNum);
			String prevPage = currentThreadId+"?page_size=25&page="+prevPageNum;

	    	Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
	    	showurlContent.setData(Uri.parse(prevPage));
	    	startActivity(showurlContent);
    	} else {
			Toast.makeText(getApplicationContext(), "No previous pages", Toast.LENGTH_SHORT).show();
		}
    }
    private void pages() {
    	if(PAGES.size() > 0){
			showDialog(PAGE_DIALOG);
		} else {
			Toast.makeText(getApplicationContext(), "No pages", Toast.LENGTH_SHORT).show();
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
	  trker.setTitle("Open "+REPLIES.get(info.position).get("author")+"'s Profile");
	  MenuItem msgr = menu.findItem(R.id.sendpm);
	  msgr.setTitle("Send PM to "+REPLIES.get(info.position).get("author"));
	  MenuItem opnr = menu.findItem(R.id.openthread);
	  opnr.setTitle("Reply to "+REPLIES.get(info.position).get("author"));
	  if(!REPLIES.get(info.position).get("author").toLowerCase().equals(ASsettings.getUsername().toLowerCase())){
		  MenuItem edit = menu.findItem(R.id.edit);
		  edit.setVisible(false);
	  }
	  if(!LINKS.get(info.position).get(0).containsKey("link")){
		  MenuItem lnks = menu.findItem(R.id.externallinks);
		  lnks.setVisible(false);
	  }
	  MenuItem frnd = menu.findItem(R.id.friendAdd);
	  frnd.setTitle("Add to friends: "+REPLIES.get(info.position).get("author"));
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	  switch (item.getItemId()) {
	  case R.id.tracker:
		  //Toast.makeText(getApplicationContext(), REPLIES.get(info.position).get("profile"), Toast.LENGTH_SHORT).show();
		  String profile = "http://boards.adultswim.com/restapi/vc/users/login/"+REPLIES.get(info.position).get("author");
		  Intent index = new Intent(getApplicationContext(), Profile.class);	
		  index.setData(Uri.parse(profile));
		  startActivity(index);
		  return true;
	  case R.id.sendpm:
			String msgAuthId = REPLIES.get(info.position).get("msgAuthId"); 
    		String author = REPLIES.get(info.position).get("author");
    		String subject = "";
    		String msg = "";
    		Bundle b = new Bundle();
    		b.putStringArray("data", new String[]{msgAuthId, author, subject, msg});
  			Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
  			pmContent.putExtras(b);
  			startActivity(pmContent);
			return true;
	  case R.id.openthread:
			String replyId = REPLIES.get(info.position).get("id");
			String msgTitle = REPLIES.get(info.position).get("subject");
			String msgContent = msgs[info.position];
			String msgAuthor = REPLIES.get(info.position).get("author");
			Bundle b2 = new Bundle();
			b2.putStringArray("data", new String[]{replyId, msgTitle, msgContent, msgAuthor});
  			Intent replyContent = new Intent(getApplicationContext(), ApiReply.class);
  			replyContent.putExtras(b2);
  			startActivity(replyContent);
		  return true;
	  case R.id.kudo:
		  new giveKudo(REPLIES.get(info.position).get("id")).execute();
		  return true;
	  case R.id.externallinks:
		  final Dialog dlg = new Dialog(ThreadViewer.this);
	    	dlg.setContentView(R.layout.link_selector);
	    	ListView sel = (ListView) dlg.findViewById(R.id.linksel);
	    	adaptr = new SimpleAdapter(this, LINKS.get(info.position), R.layout.link_sel_row, new String[]{"link"}, new int[]{R.id.link_title});
	    	final ArrayList<HashMap<String, String>> lnks = LINKS.get(info.position);
	    	sel.setAdapter(adaptr);
	    	dlg.setTitle("Select Link");
	    	dlg.show();
	    	sel.setOnItemClickListener(new OnItemClickListener(){
	    		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
	    			//Toast.makeText(getApplicationContext(), lnks.get(position).get("extlinkhref"), Toast.LENGTH_SHORT).show();
	    			Intent i = new Intent(); 

	    			i.setAction(Intent.ACTION_VIEW); 
	    			i.addCategory(Intent.CATEGORY_BROWSABLE); 
	    			i.setData(Uri.parse(lnks.get(position).get("link"))); 
	    			startActivity(i);
	    			dlg.dismiss();
	    		}
	    	});
		  return true;
	  case R.id.edit:
		  String msgId = REPLIES.get(info.position).get("id"); 
		  String msgSubject = REPLIES.get(info.position).get("subject");
		  String msgBody;
		  String msgBody0 = msgs[info.position];
		  String msgBody1 = REPLIES.get(info.position).get("post");
		  msgBody = msgBody0;
		  
		  Bundle e = new Bundle();
		  e.putStringArray("data", new String[]{msgId, msgSubject, msgBody});
		  Intent editContent = new Intent(getApplicationContext(), MessageEdit.class);
		  editContent.putExtras(e);
		  startActivity(editContent);
		  return true;
	  case R.id.friendAdd:
		  String friend = REPLIES.get(info.position).get("author");
  		  new addFriend(friend).execute();
		  return true;
	  default:
	    return super.onContextItemSelected(item);
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
				response1 = client.execute(httppost);
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
	
	private class giveKudo extends AsyncTaskEx<Void, Void, Void> {
		String msgid;
		public giveKudo(String string) {
			// TODO Auto-generated constructor stub
			super();
			msgid = string;
		}

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String mid = msgid;
			HttpPost httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/messages/id/"+mid+"/kudos/give");
			HttpResponse response1;
			httppost.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			try {
				response1 = client.execute(httppost);
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
	static class ViewHolder {
		TextView text1;
		TextView text1a;
		TextView text2;
		ImageView icon;
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