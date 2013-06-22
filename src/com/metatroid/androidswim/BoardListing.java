package com.metatroid.androidswim;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BoardListing extends ListActivity {
	public static BoardListing ACTIVE_INSTANCE;
    public static final String pref = "preference";
    public static final String floated = "boardfloatpref";
	private final ASsettings mSettings = new ASsettings();
	private static final int LOGIN_DIALOG = 0;
	private static final int DIALOG_LOGGING_IN = 1;
	final ArrayList<HashMap<String,String>> FOLDERS = new ArrayList<HashMap<String,String>>();
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
	Intent starterintent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	ACTIVE_INSTANCE = this;
    	super.onCreate(savedInstanceState);
        starterintent = getIntent();
        SharedPreferences settings = getSharedPreferences(pref, 0);
       
        
        final String[] links = getResources().getStringArray(R.array.links_array);
        String[] boards = getResources().getStringArray(R.array.boards_array);
		for(int i = 0; i < boards.length; i++){
			HashMap<String,String> temp = new HashMap<String,String>();
			temp.put("board", boards[i]);
			temp.put("link", links[i]);
			
			FOLDERS.add(temp);
		}
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		if(getStylePref().equals("default")){
			adapter = new SpecialAdapter(this, FOLDERS, R.layout.board_row, new String[]{"board"}, new int[]{R.id.boardtitle});
		} else if(getStylePref().equals("light")) {
			adapter = new SpecialAdapter(this, FOLDERS, R.layout.board_row_light, new String[]{"board"}, new int[]{R.id.boardtitle});
		} else if(getStylePref().equals("dark")) {
			adapter = new SpecialAdapter(this, FOLDERS, R.layout.board_row_dark, new String[]{"board"}, new int[]{R.id.boardtitle});
		}
		setListAdapter(adapter);
		setContentView(R.layout.board_list);
		TextView title = (TextView) findViewById(R.id.title);
		Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
		title.setTypeface(font);
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id) {
        		String content = FOLDERS.get(position).get("link");
        		Intent showContent = new Intent(getApplicationContext(), ThreadListing.class);
        		showContent.setData(Uri.parse(content));
        		startActivity(showContent);
        		
        		//Toast.makeText(getApplicationContext(), FOLDERS.get(position).get("link"), Toast.LENGTH_SHORT).show();
        	}
        });
		registerForContextMenu(getListView());
		adaptr = new SimpleAdapter(this, FLOATERS, R.layout.board_sel_row, new String[]{"board"}, new int[]{R.id.boardseltitle});
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
    	
    	
    }
    @Override 
    public void onDestroy() { 
        super.onDestroy(); 
           ACTIVE_INSTANCE = null; 
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
				   row=inflater.inflate(R.layout.board_row, parent, false);
				} else if(getStylePref().equals("light")) {
					row=inflater.inflate(R.layout.board_row_light, parent, false);
				} else if(getStylePref().equals("dark")) {
					row=inflater.inflate(R.layout.board_row_dark, parent, false);
				}
			   
			  }
			  String folder = FOLDERS.get(position).get("board");
			   
			  TextView text1=(TextView)row.findViewById(R.id.boardtitle);
			  text1.setText(folder);
			
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
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.init_menu, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.login1:
    		showDialog(LOGIN_DIALOG);
    		return true;
    	case R.id.newpm:
    		compose();
    		return true;
    	case R.id.inbox:
    		inbox();
    		return true;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(requestCode == 0){
    		finish();
    		startActivity(starterintent);
    		
    	}
    }
    private void settings(){
    	String calling = this.getLocalClassName();
    	Intent settingsActivity = new Intent(getApplicationContext(), Preferences.class);
    	settingsActivity.setData(Uri.parse(calling));
    	startActivityForResult(settingsActivity, 0);
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
    		pdialog.setCancelable(true);
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
    		ACTIVE_INSTANCE.showDialog(DIALOG_LOGGING_IN);
    		
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean success){
    		
    		ACTIVE_INSTANCE.dismissDialog(DIALOG_LOGGING_IN);
    		
    		Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
    		if(getpmPref()){
    			new CheckMessagesTask(getApplicationContext(), mClient).execute();
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
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.board_menu, menu);
	  MenuItem fldr = menu.findItem(R.id.gotofolder);
	  fldr.setTitle("Open "+FOLDERS.get(info.position).get("board"));
	  MenuItem fltr = menu.findItem(R.id.floatfolder);
	  fltr.setTitle("Float "+FOLDERS.get(info.position).get("board"));
	}
    @Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
	  		case R.id.gotofolder:
	  			//Toast.makeText(getApplicationContext(), REPLIES.get(info.position).get("profile"), Toast.LENGTH_SHORT).show();
	  			String content = FOLDERS.get(info.position).get("link");
	  			Intent showContent = new Intent(getApplicationContext(), ThreadListing.class);
        		showContent.setData(Uri.parse(content));
        		startActivity(showContent);
	  			return true;
	  		case R.id.floatfolder:
	  			SharedPreferences settings = getSharedPreferences(floated, 0);
	  			SharedPreferences.Editor editor = settings.edit();
	  			if(FLOATERS.size() <= 10){
	  				if(settings.getString(FOLDERS.get(info.position).get("board"), "non") != FOLDERS.get(info.position).get("link")){
		  				editor.putString(FOLDERS.get(info.position).get("board"), FOLDERS.get(info.position).get("link"));
		  				editor.commit();
		  				index();
		  			}
	  			} else {
	  				Toast.makeText(getApplicationContext(), "Maximum floated boards reached", Toast.LENGTH_LONG).show();
	  			}
	  			return true;
	  		default:
	  			return super.onContextItemSelected(item);
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