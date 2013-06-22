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
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.task.AsyncTaskEx;


public class SearchMessages extends ListActivity {
	EditText query;
	DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	ArrayList<HashMap<String,String>> MESSAGES = new ArrayList<HashMap<String,String>>();
	SpecialAdapter adapter;
	ArrayList<HashMap<String,String>> FLOATERS = new ArrayList<HashMap<String,String>>();
	public static final String floated = "boardfloatpref";
	public static SearchMessages ACTIVE_INSTANCE;
	private final ASsettings mSettings = new ASsettings();
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
	String searchType;
	private static final int DIALOG_SEARCHING = 1;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		TextView title = (TextView) findViewById(R.id.title);
		Typeface font = Typeface.createFromAsset(getAssets(), "Hardpixel.otf");
		title.setTypeface(font);
		View bar = (View)findViewById(R.id.bar);
		bar.setVisibility(View.INVISIBLE);
		ACTIVE_INSTANCE = this;
		query = (EditText) findViewById(R.id.searchTerm);
		query.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
		        	new doSearch(query.getText().toString().trim()).execute();
		        	return true;
		        }
		        return false;
		    }
		});
		final Button searchButton = (Button) findViewById(R.id.searchBtn);
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new doSearch(query.getText().toString().trim()).execute();
		    }
		});
		searchType = "messages";
		final RadioButton radioM = (RadioButton) findViewById(R.id.radio_msg);
		final RadioButton radioU = (RadioButton) findViewById(R.id.radio_usr);
		radioM.setChecked(true);
		radioM.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				searchType = "messages";
		    }
		});
		radioU.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				searchType = "users";
		    }
		});
		if(getStylePref().equals("default")){
			adapter = new SpecialAdapter(this, MESSAGES, R.layout.thread_row, new String[]{"author","subject"}, new int[]{R.id.texta,R.id.textb});
		} else if(getStylePref().equals("light")){
			adapter = new SpecialAdapter(this, MESSAGES, R.layout.thread_row_light, new String[]{"author","subject"}, new int[]{R.id.texta,R.id.textb});
		} else if(getStylePref().equals("dark")){
			adapter = new SpecialAdapter(this, MESSAGES, R.layout.thread_row_dark, new String[]{"author","subject"}, new int[]{R.id.texta,R.id.textb});
		}
		
		ListView lv3 = getListView();
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
	public class doSearch extends AsyncTask<String, Integer, String>{
		String q;
		public doSearch(String string){
			super();
			q = string;
		}
		@Override
		protected void onPreExecute(){
			ACTIVE_INSTANCE.showDialog(DIALOG_SEARCHING);
			MESSAGES.clear();
			adapter.notifyDataSetChanged();
		}
		@Override
		protected String doInBackground(String... params) {
			
			String line = null;
			try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("q", q.toString()));
			nvps.add(new BasicNameValuePair("collapse_discussion", "true"));
			nvps.add(new BasicNameValuePair("page_size", "100"));
			HttpPost httppost;
			if(searchType.equals("users")){
				httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/search/users");
			} else {
				httppost = new HttpPost("http://boards.adultswim.com/restapi/vc/search/messages");
			}
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
		@Override
		protected void onPostExecute(String result){
			if(result != null){
				if(!result.contains("status=\"success\"")){
					String error = result.replaceAll("(?s)(?:.*)<message>", "").replaceAll("(?s)</message.*", "");
					Toast.makeText(getApplicationContext(), "Error: "+error, Toast.LENGTH_LONG).show();
				} else {
					Document doc = ASsettings.XMLfromString(result);
					if(searchType.equals("users")){
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
								temp.put("subject", username);
								MESSAGES.add(temp);
							}
							ACTIVE_INSTANCE.dismissDialog(DIALOG_SEARCHING);
							ImageView icn = (ImageView)findViewById(R.id.icn);
							icn.setVisibility(View.GONE);
							View bar = (View)findViewById(R.id.bar);
							bar.setVisibility(View.VISIBLE);
							InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(query.getWindowToken(), 0);
							//setContentView(R.layout.item_list);
							setListAdapter(adapter);
							adapter.notifyDataSetChanged();
							ListView lv = getListView();
							if(searchType.equals("users")){
								lv.setOnItemClickListener(new OnItemClickListener(){
						        	public void onItemClick(AdapterView<?> parent, View view,
						        			int position, long id) {
						        	String trackerurl = "/t5/forums/recentpostspage/user-id/"+MESSAGES.get(position).get("id")+"/post-type/message/page/1";
						      		Intent tracker = new Intent(getApplicationContext(), Tracker.class);
						      		tracker.setData(Uri.parse(trackerurl));
						      		startActivity(tracker);
						        		
						        		//Toast.makeText(getApplicationContext(), FOLDERS.get(position).get("link"), Toast.LENGTH_SHORT).show();
						        	}
						        });
							} else {
								lv.setOnItemClickListener(new OnItemClickListener(){
						        	public void onItemClick(AdapterView<?> parent, View view,
						        			int position, long id) {
						        		String content = MESSAGES.get(position).get("threadId")+"?page_size=25&page=1";
						        		Intent showContent = new Intent(getApplicationContext(), ThreadViewer.class);
						        		showContent.setData(Uri.parse(content));
						        		startActivity(showContent);
						        		
						        		//Toast.makeText(getApplicationContext(), FOLDERS.get(position).get("link"), Toast.LENGTH_SHORT).show();
						        	}
						        });
							}
							registerForContextMenu(lv);
						}
					} else {
						NodeList msgs = doc.getElementsByTagName("message");
						
						if(msgs == null){
							ACTIVE_INSTANCE.dismissDialog(DIALOG_SEARCHING);
							Toast.makeText(getApplicationContext(), "No Results", Toast.LENGTH_SHORT).show();
						} else {
							for (int i = 0; i < msgs.getLength(); i++) {
								HashMap<String,String> temp = new HashMap<String,String>();
								Node msg = msgs.item(i);
								Element message = (Element) msg;
								
								NodeList ids = message.getElementsByTagName("id");
								Element msgId = (Element) ids.item(0);
								NodeList msgIds = msgId.getChildNodes();
								String idnum = ((Node) msgIds.item(0)).getNodeValue();
								temp.put("id", idnum);
								
								NodeList sub01 = message.getElementsByTagName("subject");
								Element sub11 = (Element) sub01.item(0);
								NodeList sub21 = sub11.getChildNodes();
								String subject ="";
								for(int s=0;s<sub21.getLength();s++){
									subject += ((Node) sub21.item(s)).getNodeValue(); 
								}
								temp.put("subject", subject);
								
								NodeList nodes13 = message.getElementsByTagName("author");
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
									System.out.println("---------------------msg author = "+author);
									temp.put("author", author);
								}
								
								
								NodeList thrd = message.getElementsByTagName("thread");
								Element thrdstng = (Element) thrd.item(0);
								//NodeList thrdstrngs = thrdstng.getChildNodes();
								String thread = thrdstng.getAttribute("href").replaceAll("(?:.*)id/", "");
								temp.put("threadId", thread);
								
								MESSAGES.add(temp);
							}
						
							ACTIVE_INSTANCE.dismissDialog(DIALOG_SEARCHING);
							ImageView icn = (ImageView)findViewById(R.id.icn);
							icn.setVisibility(View.GONE);
							View bar = (View)findViewById(R.id.bar);
							bar.setVisibility(View.VISIBLE);
							InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(query.getWindowToken(), 0);
							//setContentView(R.layout.item_list);
							setListAdapter(adapter);
							adapter.notifyDataSetChanged();
							ListView lv = getListView();
							if(searchType.equals("users")){
								lv.setOnItemClickListener(new OnItemClickListener(){
						        	public void onItemClick(AdapterView<?> parent, View view,
						        			int position, long id) {
						        		String trackerurl = MESSAGES.get(position).get("id").replaceAll("/user/viewprofilepage", "/forums/recentpostspage")+"/post-type/message/page/1";
							  			Intent tracker = new Intent(getApplicationContext(), Tracker.class);
							  			tracker.setData(Uri.parse(trackerurl));
							  			startActivity(tracker);
						        		
						        		//Toast.makeText(getApplicationContext(), FOLDERS.get(position).get("link"), Toast.LENGTH_SHORT).show();
						        	}
						        });
							} else {
								lv.setOnItemClickListener(new OnItemClickListener(){
						        	public void onItemClick(AdapterView<?> parent, View view,
						        			int position, long id) {
						        		String content = MESSAGES.get(position).get("threadId")+"?page_size=25&page=1";
						        		Intent showContent = new Intent(getApplicationContext(), ThreadViewer.class);
						        		showContent.setData(Uri.parse(content));
						        		startActivity(showContent);
						        		
						        		//Toast.makeText(getApplicationContext(), FOLDERS.get(position).get("link"), Toast.LENGTH_SHORT).show();
						        	}
						        });
							}
							registerForContextMenu(lv);
						}
					}
				}
			} else {
				Toast.makeText(getApplicationContext(), "Error: server did not respond", Toast.LENGTH_LONG).show();
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
		public View getView(final int position, View convertView, ViewGroup parent){
			ViewHolder holder;
			View row = convertView;
			if(row==null){
				   LayoutInflater inflater=getLayoutInflater();
				   if(getStylePref().equals("default")){
					   row=inflater.inflate(R.layout.thread_row, parent, false);
					   holder = new ViewHolder();
					   holder.text1 = (TextView)row.findViewById(R.id.texta);
					   holder.text2 = (TextView)row.findViewById(R.id.textb);
					   
					   row.setTag(holder);
				   } else if(getStylePref().equals("light")){
					   row=inflater.inflate(R.layout.thread_row_light, parent, false);
					   holder = new ViewHolder();
					   holder.text1 = (TextView)row.findViewById(R.id.texta);
					   holder.text2 = (TextView)row.findViewById(R.id.textb);
					   
					   row.setTag(holder);
				   } else if(getStylePref().equals("dark")){
					   row=inflater.inflate(R.layout.thread_row_dark, parent, false);
					   holder = new ViewHolder();
					   holder.text1 = (TextView)row.findViewById(R.id.texta);
					   holder.text2 = (TextView)row.findViewById(R.id.textb);
					   
					   row.setTag(holder);
				   } else {
					   row=inflater.inflate(R.layout.thread_row, parent, false);
					   holder = new ViewHolder();
					   holder.text1 = (TextView)row.findViewById(R.id.texta);
					   holder.text2 = (TextView)row.findViewById(R.id.textb);
					   
					   row.setTag(holder);
				   }
				  } else {
					  holder = (ViewHolder) row.getTag();
				  }
			
			String author = MESSAGES.get(position).get("author");
			String subject = MESSAGES.get(position).get("subject");
			holder.text1.setText(author);
			holder.text2.setText(subject);
			
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
    static class ViewHolder {
		TextView text1;
		TextView text2;
		ImageView icon;
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
		  if(searchType.equals("users")){
			  MenuItem trker = menu.findItem(R.id.tracker);
			  trker.setTitle("Go to "+MESSAGES.get(info.position).get("subject")+"'s Tracker");
			  MenuItem msgr = menu.findItem(R.id.sendpm);
			  msgr.setTitle("Send PM to "+MESSAGES.get(info.position).get("subject"));
			  MenuItem opnr = menu.findItem(R.id.openthread);
			  opnr.setTitle("Add as friend: "+MESSAGES.get(info.position).get("subject"));
			  MenuItem kdo = menu.findItem(R.id.kudo);
			  kdo.setVisible(false);
			  MenuItem lnk = menu.findItem(R.id.externallinks);
			  lnk.setVisible(false);
			  MenuItem edt = menu.findItem(R.id.edit);
			  edt.setVisible(false); 
			  MenuItem frnd = menu.findItem(R.id.friendAdd);
			  frnd.setVisible(false);
			  
		  } else {
			  MenuItem trker = menu.findItem(R.id.tracker);
			  trker.setTitle("Go to "+MESSAGES.get(info.position).get("author")+"'s Tracker");
			  MenuItem msgr = menu.findItem(R.id.sendpm);
			  msgr.setTitle("Send PM to "+MESSAGES.get(info.position).get("author"));
			  MenuItem opnr = menu.findItem(R.id.openthread);
			  opnr.setTitle("Open \""+MESSAGES.get(info.position).get("subject")+"\"");
			  MenuItem kdo = menu.findItem(R.id.kudo);
			  kdo.setVisible(false);
			  MenuItem lnk = menu.findItem(R.id.externallinks);
			  lnk.setVisible(false);
			  MenuItem edt = menu.findItem(R.id.edit);
			  edt.setVisible(false); 
			  MenuItem frnd = menu.findItem(R.id.friendAdd);
			  frnd.setVisible(false);
		  }
		}
		@Override
		public boolean onContextItemSelected(MenuItem item) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			switch (item.getItemId()) {
		  		case R.id.tracker:
		  		if(searchType.equals("users")){
		  			String trackerurl = "/t5/forums/recentpostspage/user-id/"+MESSAGES.get(info.position).get("id")+"/post-type/message/page/1";
			  		  Intent tracker = new Intent(getApplicationContext(), Tracker.class);
			  		  tracker.setData(Uri.parse(trackerurl));
			  		  startActivity(tracker);
		  		} else {
		  		  String trackerurl = "/t5/forums/recentpostspage/user-id/"+MESSAGES.get(info.position).get("msgAuthId")+"/post-type/message/page/1";
		  		  Intent tracker = new Intent(getApplicationContext(), Tracker.class);
		  		  tracker.setData(Uri.parse(trackerurl));
		  		  startActivity(tracker);
		  		}
		  			return true;
		  		case R.id.sendpm:
		  			if(searchType.equals("users")){
		  				String msgAuthId = "";
			  			String author = MESSAGES.get(info.position).get("subject"); 
			  			String subject = "";
			  			String msg = "";
			  			Bundle b = new Bundle();
			  			b.putStringArray("data", new String[]{msgAuthId, author, subject, msg});
			  			Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
			  			pmContent.putExtras(b);
			  			startActivity(pmContent);
		  			} else {
			  			String msgAuthId = "";
			  			String author = MESSAGES.get(info.position).get("author"); 
			  			String subject = "";
			  			String msg = "";
			  			Bundle b = new Bundle();
			  			b.putStringArray("data", new String[]{msgAuthId, author, subject, msg});
			  			Intent pmContent = new Intent(getApplicationContext(), MessageReply.class);
			  			pmContent.putExtras(b);
			  			startActivity(pmContent);
		  			}
		  			return true;
		  		case R.id.openthread:
		  			if(searchType.equals("users")){
		  				String friend = MESSAGES.get(info.position).get("subject");
			  		  	new addFriend(friend).execute();
		  			} else {
			  			String urlcontent = MESSAGES.get(info.position).get("threadId")+"?page_size=25&page=1";
		        		Intent showurlContent = new Intent(getApplicationContext(), ThreadViewer.class);
		        		showurlContent.setData(Uri.parse(urlcontent));
		        		startActivity(showurlContent);
		  			}
		  			return true;
		  		default:
		  			return super.onContextItemSelected(item);
			}
		}
		@Override
	    protected Dialog onCreateDialog(int id){
	    	Dialog dialog;
	    	ProgressDialog pdialog;
	    	switch (id) {
	    	case DIALOG_SEARCHING:
	    		pdialog = new ProgressDialog(this);
	    		pdialog.setMessage("Searching...");
	    		pdialog.setIndeterminate(true);
	    		pdialog.setCancelable(true);
	    		dialog = pdialog;
	    		break;
	    	default:
	    		throw new IllegalArgumentException("Unexpected dialog id "+id);
	    	}
	    	return dialog;
	    	
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