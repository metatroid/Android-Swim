package com.metatroid.androidswim;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemClock;

public class MessageService extends Service {
	NotificationManager mNM;
	private ASsettings mSettings = new ASsettings();
	private DefaultHttpClient mClient = AndroidSwimSettings.getClient();
	
	@Override
	public void onCreate(){
		mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		new CheckMessageServiceTask(this, mClient).execute();
	}
	
	private class CheckMessageServiceTask extends CheckMessagesTask {
		public CheckMessageServiceTask(Context context, DefaultHttpClient client){
			super(context, client);
		}
		@Override
		public void onPostExecute(Integer count){
			super.onPostExecute(count);
			MessageService.this.stopSelf();
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	private final IBinder mBinder = new Binder() {
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
			return super.onTransact(code, data, reply, flags);
		}
	};
	
	public static void resetAlarm(Context context, long interval){
		PendingIntent alarmSender = PendingIntent.getService(context, 0, new Intent(context, MessageService.class), 0);
		AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
		am.cancel(alarmSender);
		if (interval != 0)
			am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), interval, alarmSender);
	}
}