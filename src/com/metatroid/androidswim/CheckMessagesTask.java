package com.metatroid.androidswim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

public class CheckMessagesTask extends AsyncTask<Void, Void, Integer> {
	String pmcount = "0";
	Integer count;
	protected Context mContext;
	protected DefaultHttpClient mClient;
	
	public CheckMessagesTask(Context context, DefaultHttpClient client){
		mContext = context;
		mClient = client;
	}
	
	@Override
	protected Integer doInBackground(Void... arg0) {
		HttpEntity entity = null;
		String result = "";
		try {
			HttpGet request = new HttpGet("http://boards.adultswim.com/t5/notes/privatenotespage");
			request.setHeader("User-Agent", "(Android Swim v. 0.01)/metatroid");
			HttpResponse response = mClient.execute(request);
			entity = response.getEntity();
			try{
				InputStream in = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder str = new StringBuilder(4096);
				String line = null;
				while((line = reader.readLine()) != null){
					//System.out.println(line);
					str.append(line);
				}
				in.close();
				result = str.toString();
			} catch(Exception ex){
				result = "error";
			}
		
		Document doc = Jsoup.parse(result);
		Element pm = doc.getElementById("privateNotes");
		
		if(pm != null){
			pmcount = pm.text().replaceAll("\\D", "");
		}
			
			count = Integer.parseInt(pmcount);
			
			return count;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void onPostExecute(Integer count){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		boolean pmstate = prefs.getBoolean("notificationpref", true);
		if(pmstate){
			MessageService.resetAlarm(mContext, 500000);
		} else {
			MessageService.resetAlarm(mContext, 0);
		}
		if (count == null)
			return;
		if (count > 0){
			newMailNotification(mContext, count);
		} else {
			cancelMailNotification(mContext);
		}
	}
	
	static void newMailNotification(Context context, int count){
		String pmpage = "http://boards.adultswim.com/restapi/vc/users/id/";
		Intent inbox = new Intent(context, InboxActivity.class);
		inbox.setData(Uri.parse(pmpage));
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, inbox, 0);
		Notification notification = new Notification(R.drawable.mail, "Private Message", System.currentTimeMillis());
		notification.setLatestEventInfo(context, "Android Swim", count + (count == 1 ? " unread message" : " unread messages"), contentIntent);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_AUTO_CANCEL;
		notification.contentIntent = contentIntent;
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}
	
	static void cancelMailNotification(Context context){
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
	}
	
}