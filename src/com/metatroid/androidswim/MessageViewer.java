package com.metatroid.androidswim;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
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

public class MessageViewer extends ListActivity{
	private static final int LOGIN_DIALOG = 0;
	private static final int DIALOG_LOGGING_IN = 1;
	private final ASsettings mSettings = new ASsettings();
	private final DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	final ArrayList<HashMap<String,String>> MESSAGE = new ArrayList<HashMap<String,String>>();
	final ArrayList<ArrayList<HashMap<String,String>>> LINKS = new ArrayList<ArrayList<HashMap<String,String>>>();
	SpecialAdapter adapter;
	Intent starterintent;
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
	String msgId;
	String uid;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		starterintent = getIntent();
		msgId = starterintent.getDataString().toString();
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
    	if (ASsettings.getUserId().length() < 1) {
    		new getUserId().execute();
        } else {
        	uid = ASsettings.getUserId();
        	new getMessage().execute();
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
		}
		
	}
	private class getMessage extends AsyncTask<Void, Integer, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			messageView();
			return null;
		}
		protected void onPostExecute(Void result){
			
			if(!MESSAGE.isEmpty()){
				setListAdapter(adapter);
				setContentView(R.layout.item_list);
				TextView title = (TextView) findViewById(R.id.title);
				Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
				title.setTypeface(font);
				if(MESSAGE.get(0).get("id") != null){
					new markRead(MESSAGE.get(0).get("id")).execute();
				}
				ListView lv3 = getListView();
				registerForContextMenu(lv3);
			}
			
		}
	}
	
	public void messageView(){
		if(getStylePref().equals("default")){
			adapter = new SpecialAdapter(this, MESSAGE, R.layout.thread_row, new String[]{"author","post"}, new int[]{R.id.texta,R.id.textb});
		} else if(getStylePref().equals("light")){
			adapter = new SpecialAdapter(this, MESSAGE, R.layout.thread_row_light, new String[]{"author","post"}, new int[]{R.id.texta,R.id.textb});
		} else if(getStylePref().equals("dark")){
			adapter = new SpecialAdapter(this, MESSAGE, R.layout.thread_row_dark, new String[]{"author","post"}, new int[]{R.id.texta,R.id.textb});
		}
		
		
		Intent launchingIntent = getIntent();
		String content = launchingIntent.getData().toString();
		String line = null;
		try {
			//client.getCookieStore().getCookies();
			
			HttpGet httpget = new HttpGet("http://boards.adultswim.com/restapi/vc/users/id/"+uid+"/mailbox/notes/id/"+content);
			HttpResponse response1;
			httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			response1 = mClient.execute(httpget);
			HttpEntity entity1 = response1.getEntity();
			
			line = EntityUtils.toString(entity1);
		} catch (UnsupportedEncodingException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (MalformedURLException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (IOException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		}
		if(!line.contains("status=\"success\"")){
			String user = ASsettings.getUsername();
			String pass = ASsettings.getPassword();
			if(user.length() > 1){
				new ALoginTask(user, pass).execute();
			} else {
				setListAdapter(adapter);
				setContentView(R.layout.item_list);
			}
		} else {
			Document doc = XMLfromString(line);
			NodeList nodes = doc.getElementsByTagName("note");
			if(nodes != null){
				HashMap<String,String> temp1 = new HashMap<String,String>();
				Node item2 = nodes.item(0);
				Element itm2 = (Element) item2;
				if(itm2 != null){
				NodeList ids = itm2.getElementsByTagName("id");
				Element threadid = (Element) ids.item(0);
				NodeList idnums = threadid.getChildNodes();
				String idnum = ((Node) idnums.item(0)).getNodeValue();
				temp1.put("id", idnum);
				
						
				NodeList body = itm2.getElementsByTagName("body");
				Element bodystng = (Element) body.item(0);
				NodeList bodystrngs = bodystng.getChildNodes();
				String msgContent = "";
				for(int p=0;p<bodystrngs.getLength();p++){
					msgContent += ((Node) bodystrngs.item(p)).getNodeValue();
				}
				
				temp1.put("post", msgContent);
				
				NodeList nodes3 = itm2.getElementsByTagName("from");
				for (int a = 0; a < nodes3.getLength(); a++) {
					Node item3 = nodes3.item(a);
					Element itm3 = (Element) item3;
					
					NodeList auths1 = itm3.getElementsByTagName("login");
					Element authname1 = (Element) auths1.item(0);
					NodeList auths12 = authname1.getChildNodes();
					String author1 = ((Node) auths12.item(0)).getNodeValue();
					temp1.put("author", author1);
				}
				
				NodeList subjects = itm2.getElementsByTagName("subject");
				Element subject = (Element) subjects.item(0);
				NodeList subj = subject.getChildNodes();
				String title = "";
				for (int q = 0; q < subj.getLength(); q++){
					title += ((Node) subj.item(q)).getNodeValue();
				}
				temp1.put("title", title);
				
				MESSAGE.add(temp1);
				} else {
					temp1.put("id", "err");
					temp1.put("title", "error");
					temp1.put("author", "something went wrong");
					temp1.put("post", "please try again");
					MESSAGE.add(temp1);
				}
			} else {
				HashMap<String,String> temp1 = new HashMap<String,String>();
				temp1.put("id", "err");
				temp1.put("title", "error");
				temp1.put("author", "something went wrong");
				temp1.put("post", "please try again");
				MESSAGE.add(temp1);
			}
		}
		
	}
	
	public Document XMLfromString(String xml){
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
				   row=inflater.inflate(R.layout.thread_row, parent, false);
			   } else if(getStylePref().equals("light")){
				   row=inflater.inflate(R.layout.thread_row_light, parent, false);
			   } else if(getStylePref().equals("dark")){
				   row=inflater.inflate(R.layout.thread_row_dark, parent, false);
			   }
			  }
			  
			  String auth = MESSAGE.get(position).get("author");
			  String post = MESSAGE.get(position).get("post");
			  String subject = MESSAGE.get(position).get("title");
			  TextView texta=(TextView)row.findViewById(R.id.texta);
			  	texta.setText(auth+" wrote: "+subject);
			  TextView textb=(TextView)row.findViewById(R.id.textb);
			  	textb.setText(Html.fromHtml(post));
			  	
			  	textb.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v) {
		        		String id = MESSAGE.get(position).get("id");
		        		String author = MESSAGE.get(position).get("author");
		        		String subject = MESSAGE.get(position).get("title");
		        		String content = MESSAGE.get(0).get("post");
		        		Bundle b = new Bundle();
		        		b.putStringArray("data", new String[]{id, author, subject, content});
		        		
		      			Intent replyContent = new Intent(getApplicationContext(), MessageReply.class);
		      			replyContent.putExtras(b);
		      			startActivity(replyContent);
		        	}
		        });
			  	URLSpan[] urls = textb.getUrls();
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
			return row;
		}
		
	}
	private class markRead extends AsyncTask<Void, Void, Void> {
		String mid;
		public markRead(String string) {
			// TODO Auto-generated constructor stub
			super();
			mid = string;
		}

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			HttpPost httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/users/id/"+uid+"/mailbox/notes/id/"+mid+"/markread");
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
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.thread_menu, menu);
	  MenuItem trker = menu.findItem(R.id.tracker);
	  trker.setTitle("Go to "+MESSAGE.get(info.position).get("author")+"'s Tracker");
	  MenuItem msgr = menu.findItem(R.id.sendpm);
	  msgr.setTitle("Send PM to "+MESSAGE.get(info.position).get("author"));
	  MenuItem opnr = menu.findItem(R.id.openthread);
	  opnr.setVisible(false);
	  MenuItem edit = menu.findItem(R.id.edit);
	  edit.setVisible(false);
	  MenuItem kudo = menu.findItem(R.id.kudo);
	  kudo.setVisible(false);
	  if(!LINKS.get(info.position).get(0).containsKey("link")){
		  MenuItem lnks = menu.findItem(R.id.externallinks);
		  lnks.setVisible(false);
	  }
	  MenuItem frnd = menu.findItem(R.id.friendAdd);
	  frnd.setTitle("Add to friends: "+MESSAGE.get(info.position).get("author"));
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	  switch (item.getItemId()) {
	  case R.id.tracker:
		  //Toast.makeText(getApplicationContext(), REPLIES.get(info.position).get("profile"), Toast.LENGTH_SHORT).show();
		  String trackerurl = "/t5/forums/recentpostspage/user-id/"+MESSAGE.get(info.position).get("msgAuthId")+"/post-type/message/page/1";
		  Intent tracker = new Intent(getApplicationContext(), Tracker.class);
		  tracker.setData(Uri.parse(trackerurl));
		  startActivity(tracker);
		  return true;
	  case R.id.sendpm:
			String msgAuthId = MESSAGE.get(info.position).get("msgAuthId"); 
    		String author = MESSAGE.get(info.position).get("author");
    		String subject = MESSAGE.get(info.position).get("title");
    		String content = MESSAGE.get(0).get("post");
    		Bundle b = new Bundle();
    		b.putStringArray("data", new String[]{msgAuthId, author, subject, content});
  			Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
  			pmContent.putExtras(b);
  			startActivity(pmContent);
			return true;
	  case R.id.externallinks:
		  final Dialog dlg = new Dialog(MessageViewer.this);
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
	  case R.id.friendAdd:
		  String friend = MESSAGE.get(info.position).get("author");
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.inbox_message_menu, menu);
		return true;
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.index:
    		index();
    		return true;
    	case R.id.inbox:
    		inbox();
    		return true;
    	case R.id.replypm:
    		reply();
    		return true;
    	case R.id.homescreen:
    		Intent index = new Intent(getApplicationContext(), AndroidSwim.class);	
    		startActivity(index);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
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
    private class ALoginTask extends LoginTask {
    	public ALoginTask(String username, String password) {
    		super(username, password, mSettings, mClient, getApplicationContext());
    	}
    	
    	@Override
    	protected void onPreExecute(){
    		mClient.getCookieStore().clear();
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean success){
    		new getMessage().execute();
    	}
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
	private void reply() {
		String id = MESSAGE.get(0).get("id");
		String author = MESSAGE.get(0).get("author");
		String subject = MESSAGE.get(0).get("title");
		String content = MESSAGE.get(0).get("post");
		Bundle b = new Bundle();
		b.putStringArray("data", new String[]{id, author, subject, content});
		Intent replyContent = new Intent(getApplicationContext(), MessageReply.class);
		replyContent.putExtras(b);
		startActivity(replyContent);
	}
	private void inbox(){
		String pmpage = "http://boards.adultswim.com/restapi/vc/users/id/";
		Intent inbox = new Intent(getApplicationContext(), InboxActivity.class);
		inbox.setData(Uri.parse(pmpage));
		startActivity(inbox);
	}
	private void selftracker(){
    	Intent trkr = new Intent(getApplicationContext(), SelfTracker.class);	
		startActivity(trkr);
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