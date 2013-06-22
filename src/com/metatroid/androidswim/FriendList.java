package com.metatroid.androidswim;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.commonsware.cwac.task.AsyncTaskEx;

public class FriendList extends ListActivity {
	private final DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	final ArrayList<HashMap<String,String>> FRIENDS = new ArrayList<HashMap<String,String>>();
	SpecialAdapter adapter;
	ArrayList<HashMap<String,String>> FLOATERS = new ArrayList<HashMap<String,String>>();
	public static final String floated = "boardfloatpref";
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
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.loading);
    	if(getStylePref().equals("default")){
			adapter = new SpecialAdapter(this, FRIENDS, R.layout.friend_row, new String[]{"name"}, new int[]{R.id.frText});
		} else if(getStylePref().equals("light")){
			adapter = new SpecialAdapter(this, FRIENDS, R.layout.thread_row_light, new String[]{"name","name"}, new int[]{R.id.texta,R.id.textb});
		} else if(getStylePref().equals("dark")){
			adapter = new SpecialAdapter(this, FRIENDS, R.layout.thread_row_dark, new String[]{"name","name"}, new int[]{R.id.texta,R.id.textb});
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
    	new getFriends(null).execute();
	}
	
	public class getFriends extends AsyncTask<String, Integer, String>{
		public getFriends(String string){
			super();
		}
		@Override
		protected String doInBackground(String...params){
			HttpGet httpget = new HttpGet("http://boards.adultswim.com/restapi/vc/users/self/addressbook/contacts/friends?page_size=100");
			httpget.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			String line = null;
			try {
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
			Document doc = ASsettings.XMLfromString(line);
			String status;
			if(!line.contains("success")){
				HashMap<String,String> temp = new HashMap<String,String>();
				temp.put("id", null);
				temp.put("name", "You have no friends");
				FRIENDS.add(temp);
				status="error";
			} else {
				NodeList nodes = doc.getElementsByTagName("user");
				if(nodes != null){
					for (int i = 0; i < nodes.getLength(); i++) {
						HashMap<String,String> temp = new HashMap<String,String>();
						Node node = nodes.item(i);
						Element el = (Element) node;
						
						NodeList id = el.getElementsByTagName("id");
						Element mId = (Element) id.item(0);
						NodeList mIds = mId.getChildNodes();
						String mIdNum = ((Node) mIds.item(0)).getNodeValue();
						temp.put("id", mIdNum);
						
						NodeList name = el.getElementsByTagName("login");
						Element mName = (Element) name.item(0);
						NodeList mNames = mName.getChildNodes();
						String username ="";
						for(int s=0;s<mNames.getLength();s++){
							username += ((Node) mNames.item(s)).getNodeValue(); 
						}
						System.out.println("----------------------------------- "+username);
						temp.put("name", username);
						FRIENDS.add(temp);
					}
				}
				status="success";
			}
			return status;
		}
		@Override
		protected void onPostExecute(String result){
			setContentView(R.layout.item_list);
			TextView title = (TextView) findViewById(R.id.title);
			Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
			title.setTypeface(font);
			setListAdapter(adapter);
			ListView lv = getListView();
			registerForContextMenu(lv);
			
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
			ViewHolder holder;
			View row = convertView;
			if(row==null){
				   LayoutInflater inflater=getLayoutInflater();
				   if(getStylePref().equals("default")){
					   row=inflater.inflate(R.layout.friend_row, parent, false);
					   holder = new ViewHolder();
					   holder.text2 = (TextView)row.findViewById(R.id.frText);
					   row.setTag(holder);
				   } else if(getStylePref().equals("light")){
					   row=inflater.inflate(R.layout.thread_row_light, parent, false);
					   holder = new ViewHolder();
					   holder.text2 = (TextView)row.findViewById(R.id.textb);
					   row.setTag(holder);
				   } else if(getStylePref().equals("dark")){
					   row=inflater.inflate(R.layout.thread_row_dark, parent, false);
					   holder = new ViewHolder();
					   holder.text2 = (TextView)row.findViewById(R.id.textb);
					   row.setTag(holder);
				   } else {
					   row=inflater.inflate(R.layout.thread_row, parent, false);
					   holder = new ViewHolder();
					   holder.text2 = (TextView)row.findViewById(R.id.textb);
					   row.setTag(holder);
				   }
				  } else {
					  holder = (ViewHolder) row.getTag();
			}
			String username = FRIENDS.get(position).get("name");
			holder.text2.setText(username);
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
				    	Intent searchActivity = new Intent(getApplicationContext(), SearchMessages.class);
			    		startActivity(searchActivity);
				    }
			  });
			return row;
		}
	}
	static class ViewHolder {
		TextView text1;
		TextView text2;
		ImageView icon;
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
	private void inbox() {
    	String pmpage = "http://boards.adultswim.com/restapi/vc/users/id/";
		Intent inbox = new Intent(getApplicationContext(), InboxActivity.class);
		inbox.setData(Uri.parse(pmpage));
		startActivity(inbox);
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
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.thread_menu, menu);
	  MenuItem trker = menu.findItem(R.id.tracker);
	  trker.setTitle("Go to "+FRIENDS.get(info.position).get("name")+"'s Tracker");
	  MenuItem msgr = menu.findItem(R.id.sendpm);
	  msgr.setTitle("Send PM to "+FRIENDS.get(info.position).get("name"));
	  MenuItem opnr = menu.findItem(R.id.openthread);
	  opnr.setTitle("Remove \""+FRIENDS.get(info.position).get("name")+"\"");
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
	  		case R.id.tracker:
	  			//Toast.makeText(getApplicationContext(), REPLIES.get(info.position).get("profile"), Toast.LENGTH_SHORT).show();
	  		  String trackerurl = "/t5/forums/recentpostspage/user-id/"+FRIENDS.get(info.position).get("id")+"/post-type/message/page/1";
	  		  Intent tracker = new Intent(getApplicationContext(), Tracker.class);
	  		  tracker.setData(Uri.parse(trackerurl));
	  		  startActivity(tracker);
	  			return true;
	  		case R.id.sendpm:
	  			String msgAuthId = "";
	  			String author = FRIENDS.get(info.position).get("name"); 
	  			String subject = "";
	  			String msg = "";
	  			Bundle b = new Bundle();
	  			b.putStringArray("data", new String[]{msgAuthId, author, subject, msg});
	  			Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
	  			pmContent.putExtras(b);
	  			startActivity(pmContent);
	  			return true;
	  		case R.id.openthread:
	  			String friend = FRIENDS.get(info.position).get("name");
        		new removeFriend(friend).execute();
	  			return true;
	  		default:
	  			return super.onContextItemSelected(item);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.init_menu, menu);
		MenuItem lgn = menu.findItem(R.id.login1);
		lgn.setVisible(false);
		MenuItem pm = menu.findItem(R.id.newpm);
		pm.setVisible(false);
		MenuItem in = menu.findItem(R.id.inbox);
		in.setVisible(false);
		return true;
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.prfs:
    		settings();
    		return true;
    	case R.id.searchMsgs:
    		Intent searchActivity = new Intent(getApplicationContext(), SearchMessages.class);
    		startActivity(searchActivity);
    		return true;
    	case R.id.homescreen:
    		Intent index = new Intent(getApplicationContext(), AndroidSwim.class);	
    		startActivity(index);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
	private class removeFriend extends AsyncTaskEx<Void, Void, Void> {
		String username;
		public removeFriend(String string) {
			// TODO Auto-generated constructor stub
			super();
			username = string;
		}

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String uName = username;
			HttpPost httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/users/self/addressbook/contacts/friends/remove?contacts.user=login/"+uName);
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