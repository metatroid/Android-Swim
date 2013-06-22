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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.task.AsyncTaskEx;

public class BoardSubscriptionViewer extends ListActivity{
	private final ASsettings mSettings = new ASsettings();
	static DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	final ArrayList<HashMap<String,String>> THREADS = new ArrayList<HashMap<String,String>>();
	public static final String floated = "boardfloatpref";
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
	String page;
	Intent starterintent;
	String ticket;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		starterintent = getIntent();
		setContentView(R.layout.loading);
		//String curPage = starterintent.getData().toString();
		//page = curPage;
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
				Toast.makeText(getApplicationContext(), "Subscriptions Loaded", Toast.LENGTH_SHORT).show();
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
    	new getSubscriptions().execute();
		
	}
	
	private class getSubscriptions extends AsyncTask<Void, Integer, Void> {

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			getSubs(page);
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
				setContentView(R.layout.sub_list);
				TextView title = (TextView) findViewById(R.id.title);
				Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
				title.setTypeface(font);
				if(!THREADS.isEmpty()){
					ListView lv2 = getListView();
					lv2.setOnItemClickListener(new OnItemClickListener(){
			        	public void onItemClick(AdapterView<?> parent, View view,
			        			int position, long id) {
			        		if(!THREADS.get(position).get("type").equals("board")){
				        		String urlcontent = "MessageID"+THREADS.get(position).get("link");
				        		Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
				        		showurlContent.setData(Uri.parse(urlcontent));
				        		startActivity(showurlContent);
			        		} else {
			        			String boardId = THREADS.get(position).get("link");
			        			Intent i = new Intent(getApplicationContext(), ThreadListing.class);
			        			i.setData(Uri.parse("delimiter "+boardId));
			        			startActivity(i);
			        		}
			        	}
			        });
					//setTitle(title);
				}
			registerForContextMenu(getListView());
			
			Button t = (Button)findViewById(R.id.subscriptions);
			Button b = (Button)findViewById(R.id.bookmarks);
			t.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					Intent ts = new Intent(getApplicationContext(), SubscriptionViewer.class);
					startActivity(ts);
				}
				
			});
			b.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "showing bookmarks", Toast.LENGTH_SHORT).show();
					
				}
				
			});
		}
		
	}
	
	public void getSubs(String curPage){
		String content = curPage;
		
		if(getStylePref().equals("default")){
			adapter = new SpecialAdapter(this, THREADS, R.layout.subscription_row, new String[]{"title","count"}, new int[]{R.id.subs1,R.id.subs2});
		} else if(getStylePref().equals("light")){
			adapter = new SpecialAdapter(this, THREADS, R.layout.subscription_row_light, new String[]{"title","count"}, new int[]{R.id.subs1,R.id.subs2});
		} else if(getStylePref().equals("dark")){
			adapter = new SpecialAdapter(this, THREADS, R.layout.subscription_row_dark, new String[]{"title","count"}, new int[]{R.id.subs1,R.id.subs2});
		}
		
		ListView lv2 = getListView();
        lv2.setTextFilterEnabled(true);
        HttpGet httpget = new HttpGet("http://boards.adultswim.com/restapi/vc/subscriptions/users/self/bookmark/all/?page_size=100&page=1");
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
			
			
			NodeList nodes = doc.getElementsByTagName("subscription");
			if(nodes != null){
				for(int i = 0; i < nodes.getLength(); i++){
					HashMap<String,String> temp = new HashMap<String,String>();
					Node item = nodes.item(i);
					Element itm = (Element) item;
					
					NodeList types = itm.getElementsByTagName("target_type");
					Element type2 = (Element) types.item(0);
					NodeList types2 = type2.getChildNodes();
					String type = ((Node) types2.item(0)).getNodeValue();
					
					if(type.equals("board")){
						
						NodeList nodes0 = itm.getElementsByTagName("target");
						Node msgItem = nodes0.item(0);
						Element messages = (Element) msgItem;
						NodeList msgNodes = messages.getChildNodes();
						for(int m = 0; m < msgNodes.getLength(); m++){
							Node msgNode = (Node)msgNodes.item(m);
							if(msgNode.getNodeName().equals("id")){
								Element subjnode = (Element) msgNode;
								NodeList subs2 = subjnode.getChildNodes();
								String subject ="";
								for(int s=0;s<subs2.getLength();s++){
									subject += ((Node) subs2.item(s)).getNodeValue(); 
								}
								temp.put("link", subject);
							}
							if(msgNode.getNodeName().equals("title")){
								Element subjnode = (Element) msgNode;
								NodeList subs2 = subjnode.getChildNodes();
								String subject ="";
								for(int s=0;s<subs2.getLength();s++){
									subject += ((Node) subs2.item(s)).getNodeValue(); 
								}
								temp.put("title", subject);
							}
						}
						temp.put("type", "board");
						THREADS.add(temp);
						
					} else if (type.equals("message")){
						String totalCount = "";
						String author = "";
						String threadId = "";
						NodeList nodes0 = itm.getElementsByTagName("target");
						Node msgItem = nodes0.item(0);
						Element messages = (Element) msgItem;
						NodeList msgNodes = messages.getChildNodes();
						for(int m = 0; m < msgNodes.getLength(); m++){
							Node msgNode = (Node)msgNodes.item(m);
							if(msgNode.getNodeName().equals("subject")){
								Element subjnode = (Element) msgNode;
								NodeList subs2 = subjnode.getChildNodes();
								String subject ="";
								for(int s=0;s<subs2.getLength();s++){
									subject += ((Node) subs2.item(s)).getNodeValue(); 
								}
								temp.put("title", subject);
							}
							if(msgNode.getNodeName().equals("author")){
								//readCount = msgNode.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
								Element el = (Element) msgNode;
								NodeList nl = el.getElementsByTagName("login");
								Element el2 = (Element) nl.item(0);
								NodeList nl2 = el2.getChildNodes();
								author = ((Node) nl2.item(0)).getNodeValue();
							}
							if(msgNode.getNodeName().equals("thread")){
								//readCount = msgNode.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
								Element threadnode = (Element) msgNode;

								
								String board = threadnode.getAttribute("href").replaceAll("/threads/id/", "");
								temp.put("link", board);
								
							}
						}
					
						
						temp.put("author", author);
						temp.put("type", "message");
						THREADS.add(temp);
						
					} else if (type.equals("thread")){
						String totalCount = "";
						String readCount = "";
						String threadId = "";
						NodeList nodes0 = itm.getElementsByTagName("messages");
						Node msgItem = nodes0.item(0);
						Element messages = (Element) msgItem;
						NodeList msgNodes = messages.getChildNodes();
						for(int m = 0; m < msgNodes.getLength(); m++){
							Node msgNode = (Node)msgNodes.item(m);
							if(msgNode.getNodeName().equals("count")){
								totalCount = msgNode.getChildNodes().item(0).getNodeValue();
							}
							if(msgNode.getNodeName().equals("read")){
								//readCount = msgNode.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
								Element el = (Element) msgNode;
								NodeList nl = el.getElementsByTagName("count");
								Element el2 = (Element) nl.item(0);
								NodeList nl2 = el2.getChildNodes();
								readCount = ((Node) nl2.item(0)).getNodeValue();
							}
							if(msgNode.getNodeName().equals("topic")){
								//readCount = msgNode.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
								Element el = (Element) msgNode;
								NodeList nl = el.getElementsByTagName("id");
								Element el2 = (Element) nl.item(0);
								NodeList nl2 = el2.getChildNodes();
								threadId = ((Node) nl2.item(0)).getNodeValue();
							}
						}
						int t = Integer.parseInt(totalCount);
						int r = Integer.parseInt(readCount);
						int n = t-r;
						String unreadCount = Integer.toString(n);
						temp.put("count", unreadCount);
						temp.put("link", threadId);
						
						
						NodeList ids = itm.getElementsByTagName("id");
						Element id2 = (Element) ids.item(0);
						NodeList ids2 = id2.getChildNodes();
						String id = ((Node) ids2.item(0)).getNodeValue();
						temp.put("id", id);
						
						
						NodeList subs = itm.getElementsByTagName("subject");
						Element sub2 = (Element) subs.item(0);
						NodeList subs2 = sub2.getChildNodes();
						String subject ="";
						for(int s=0;s<subs2.getLength();s++){
							subject += ((Node) subs2.item(s)).getNodeValue(); 
						}
						temp.put("title", subject);
						
						
						NodeList auths = itm.getElementsByTagName("login");
						Element auth2 = (Element) auths.item(0);
						NodeList auths2 = auth2.getChildNodes();
						String auth = ((Node) auths2.item(0)).getNodeValue();
						temp.put("author", auth);
						
						temp.put("type", "thread");
						THREADS.add(temp);
					}
					
				}
				
			}
			
			
			//Document doc = Jsoup.parse(result);
			
			//ticket = doc.select("input[name=ticket]").val();
			//Elements threads = doc.getElementsByClass("lia-list-row");
			//for (Element src : threads){
//				String title = src.select("a.subscription-thread").first().text();
				//String link = src.select(".related-info").select(".latest-message-info").select("a.lia-link-navigation").first().attr("href");
				//String count = src.select("td.newMessages").select("a.lia-link-navigation").first().text();
				//HashMap<String,String> temp = new HashMap<String,String>();
				//temp.put("title", title);
				//temp.put("link", link);
				//temp.put("count", count);
				//THREADS.add(temp);
			//}
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
					   row=inflater.inflate(R.layout.subscription_row, parent, false);
					} else if(getStylePref().equals("light")) {
						row=inflater.inflate(R.layout.subscription_row_light, parent, false);
					} else if(getStylePref().equals("dark")) {
						row=inflater.inflate(R.layout.subscription_row_dark, parent, false);
					}
				  }
			if(!THREADS.isEmpty() && THREADS.get(position).get("title") != null){
				String title = THREADS.get(position).get("title");
				String count = THREADS.get(position).get("count");
				String auth = THREADS.get(position).get("author");
				TextView text1=(TextView)row.findViewById(R.id.subs1);
				text1.setText(title);
				TextView text3=(TextView)row.findViewById(R.id.subs3);
				text3.setText(auth);
				TextView text2=(TextView)row.findViewById(R.id.subs2);
				text2.setText("New: "+count);  
			} else {
				  TextView text1=(TextView)row.findViewById(R.id.subs1);
				  text1.setText("Error - Connection timed out");
				  
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
				    	Toast.makeText(getApplicationContext(), "Nothing to reply to here", Toast.LENGTH_SHORT).show();
				    }
				});
			return row;
			
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
    	String curPage = starterintent.getData().toString();
		String[] pageSplit = curPage.split("/");
		String pageNum = pageSplit[pageSplit.length-1];
		int pageInt = Integer.parseInt(pageNum.trim());
		int nextInt = pageInt+1;
		String nextPageNum = Integer.toString(nextInt);
		String nextPage = curPage.replaceAll("/[^/]*$", "/"+nextPageNum);
		System.out.println("NEXT PAGE!!! "+ nextPage);
		page = nextPage;
		Intent showContent = new Intent(getApplicationContext(), SubscriptionViewer.class);
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
    		Intent showContent = new Intent(getApplicationContext(), SubscriptionViewer.class);
    		showContent.setData(Uri.parse(page));
    		startActivity(showContent);
		} else {
			Toast.makeText(getApplicationContext(), "No previous pages", Toast.LENGTH_SHORT).show();
		}
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		MenuItem lgn = menu.findItem(R.id.login);
		lgn.setVisible(false);
		MenuItem trd = menu.findItem(R.id.thread);
		trd.setVisible(false);
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
		  kdo.setTitle("Remove \""+THREADS.get(info.position).get("title")+"\"");
		  MenuItem frnd = menu.findItem(R.id.friendAdd);
		  frnd.setVisible(false);
		  MenuItem edt = menu.findItem(R.id.edit);
		  edt.setVisible(false);
		  MenuItem lnk = menu.findItem(R.id.externallinks);
		  lnk.setVisible(false);
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
		  			String msgAuthId = THREADS.get(info.position).get("userid"); 
		  			String author = "";
		  			String subject = "";
		  			String msg = "";
		  			Bundle b = new Bundle();
		  			b.putStringArray("data", new String[]{msgAuthId, author, subject, msg});
		  			Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
		  			pmContent.putExtras(b);
		  			startActivity(pmContent);
		  			return true;
		  		case R.id.openthread:
		  			String urlcontent = "MessageID"+THREADS.get(info.position).get("link");
	        		Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
	        		showurlContent.setData(Uri.parse(urlcontent));
	        		startActivity(showurlContent);
		  			return true;
		  		case R.id.kudo:
		  			new removeSub(THREADS.get(info.position).get("link"), THREADS.get(info.position).get("type")).execute();
		  			return true;
		  		default:
		  			return super.onContextItemSelected(item);
			}
		}
		private class removeSub extends AsyncTaskEx<Void, Void, Void> {
			String msgid;
			String subtype;
			public removeSub(String string, String string2) {
				// TODO Auto-generated constructor stub
				super();
				msgid = string;
				subtype = string2;
			}

			
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				String mid = msgid;
				HttpPost httppost;
				if(subtype.equals("thread")){
					System.out.println("-------------- http://boards.adultswim.com/restapi/vc/threads/id/"+mid+"/subscriptions/users/self/type/bookmark/remove");
					httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/threads/id/"+mid+"/subscriptions/users/self/type/bookmark/remove");
				} else if(subtype.equals("message")){
					System.out.println("-------------- http://boards.adultswim.com/restapi/vc/messages/id/"+mid+"/subscriptions/users/self/type/bookmark/remove");
					httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/messages/id/"+mid+"/subscriptions/users/self/type/bookmark/remove");
				} else if(subtype.equals("board")){
					System.out.println("-------------- http://boards.adultswim.com/restapi/vc/boards/id/"+mid+"/subscriptions/users/self/type/bookmark/remove");
					httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/boards/id/"+mid+"/subscriptions/users/self/type/bookmark/remove");
				} else {
					httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/threads/id/"+mid+"/subscriptions/users/self/type/bookmark/remove");
				}
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
		private boolean getlistPref(){
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			boolean blpref = prefs.getBoolean("blpref", false);
			return blpref;
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