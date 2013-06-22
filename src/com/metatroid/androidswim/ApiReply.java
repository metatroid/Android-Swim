package com.metatroid.androidswim;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ApiReply extends Activity{
	WebView webview;
	Intent starterintent;
	private static final int LOGIN_DIALOG = 0;
	private static final int INDEX = 1;
	private static final int DIALOG_LOGGING_IN = 2;
	private final ASsettings mSettings = new ASsettings();
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
	EditText msgContent;
	String msgId;
	String title;
	String original;
	String author;
	DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		starterintent = getIntent();
		
		
		Bundle b = this.getIntent().getExtras();
		String[] array = b.getStringArray("data");
		
		msgId = array[0];
		title = array[1];
		original = array[2];
		author = array[3];
		System.out.println("---------------------message id "+msgId);
		
		//mClient.getCookieStore().getCookies();

		
		setContentView(R.layout.api_reply);
		TextView ttitle = (TextView) findViewById(R.id.title);
		Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
		ttitle.setTypeface(font);
		ImageButton quote = (ImageButton) findViewById(R.id.quote);
		quote.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText txt = (EditText) findViewById(R.id.replyarea);
				String txtContent = txt.getText().toString();
				txt.setText("<blockquote><hr/>"+author+" wrote:<br/>"+original+"<hr/></blockquote>"+txtContent);
			}
			
		});
		TextView originalMsg = (TextView) findViewById(R.id.originalmsg);
		originalMsg.setText(Html.fromHtml(original, new ImageGetter(){
			  
			  public Drawable getDrawable(String source) {
			        int id;
			        if(getSmileyPref().equals("robot")){
				        if (source.contains("mad")) {
				            id = R.drawable.mad;
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
		msgContent = (EditText) findViewById(R.id.replyarea);
		//msgContent.setImeOptions(EditorInfo.IME_ACTION_DONE);
		//msgContent.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
		final Button postButton = (Button) findViewById(R.id.sendreply);
		postButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new sendReply(msgContent.getText().toString().trim(), msgId, title).execute();
		    }
		});
		
		
		
		//String replyform = replyhtml.replaceAll("/t5/forums/forumtopicpage", "http://boards.adultswim.com/t5/forums/forumtopicpage");
		
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
			    	Toast.makeText(getApplicationContext(), "Reply screen loaded", Toast.LENGTH_LONG).show();
			    }
			});
	}
	private class sendReply extends AsyncTask<String, Integer, String> {
		String msg;
		String id;
		String title;
		public sendReply(String string, String string2, String string3){
			super();
			msg = string;
			id = string2;
			title = string3.substring(0, Math.min(string3.length(), 45));
		}
		@Override
		protected void onPreExecute(){
			Toast.makeText(getApplicationContext(), "Posting...", Toast.LENGTH_LONG).show();
		}
		@Override 
		protected void onPostExecute(String result){
			//Toast.makeText(getApplicationContext(), "success?", Toast.LENGTH_SHORT).show();
			if(result != null){
				System.out.println(result);
				if(result.contains("status=\"success\"")){
					finish();
				} else {
					String error = result.replaceAll("(?s)(?:.*)<message>", "").replaceAll("(?s)</message.*", "");
					Toast.makeText(getApplicationContext(), "Error: "+error, Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "Error: No response from server.  Bad connection", Toast.LENGTH_LONG).show();
			}
			
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String line = null;
			try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("message.body", msg.toString().replaceAll("\\n", "<br>")));
			nvps.add(new BasicNameValuePair("message.subject", title.toString()));
			HttpPost httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/messages/id/"+id+"/reply");
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	        httppost.setHeader("User-Agent", "(Android Swim v. 0.2)/metatroid");
	        HttpResponse response = mClient.execute(httppost);
	        HttpEntity httpEntity = response.getEntity();
			line = EntityUtils.toString(httpEntity);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return line;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.posts_menu, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.index:
    		index();
    		return true;
    	case R.id.login:
    		showDialog(LOGIN_DIALOG);
    		return true;
    	case R.id.homescreen:
    		Intent index = new Intent(getApplicationContext(), AndroidSwim.class);	
    		startActivity(index);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    	
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
    		showDialog(DIALOG_LOGGING_IN);
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean success){
    		
    		dismissDialog(DIALOG_LOGGING_IN);
    		
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
    private String getSmileyPref(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String emoticon = prefs.getString("smileyPref", "robot");
		return emoticon;
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