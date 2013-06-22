package com.metatroid.androidswim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Browser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class Common {
	
	private static final String TAG = "Common";
	
	private static final DefaultHttpClient mGzipHttpClient = createGzipHttpClient();
	// 1:subreddit 2:threadId 3:commentId
	
	private static final ObjectMapper mObjectMapper = new ObjectMapper();

	
	
	/**
     * Set the Drawable for the list selector etc. based on the current theme.
     */
	
	
    
        
    static void doLogout(ASsettings settings, DefaultHttpClient client, Context context) {
    	//clearCookies(settings, client, context);
    	//CacheInfo.invalidateAllCaches(context);
    	//settings.setUsername(null);
    }
    
    
    /**
     * Get a new modhash by scraping and return it
     * 
     * @param client
     * @return
     */
    
    
    
    
    static ObjectMapper getObjectMapper() {
    	return mObjectMapper;
    }
    
	/**
	 * http://hc.apache.org/httpcomponents-client/examples.html
	 * @return a Gzip-enabled DefaultHttpClient
	 */
	static DefaultHttpClient getGzipHttpClient() {
		return mGzipHttpClient;
	}
	
	private static DefaultHttpClient createGzipHttpClient() {
		BasicHttpParams params = new BasicHttpParams();
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
		DefaultHttpClient httpclient = new DefaultHttpClient(cm, params);
        httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(
                    final HttpRequest request, 
                    final HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
            }
        });
        httpclient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(
                    final HttpResponse response, 
                    final HttpContext context) throws HttpException, IOException {
                HttpEntity entity = response.getEntity();
                Header ceheader = entity.getContentEncoding();
                if (ceheader != null) {
                    HeaderElement[] codecs = ceheader.getElements();
                    for (int i = 0; i < codecs.length; i++) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(
                                    new GzipDecompressingEntity(response.getEntity())); 
                            return;
                        }
                    }
                }
            }
        });
        return httpclient;
	}
    static class GzipDecompressingEntity extends HttpEntityWrapper {
        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }
        @Override
        public InputStream getContent()
            throws IOException, IllegalStateException {
            // the wrapped entity's getContent() decides about repeatability
            InputStream wrappedin = wrappedEntity.getContent();
            return new GZIPInputStream(wrappedin);
        }
        @Override
        public long getContentLength() {
            // length of ungzipped content is not known
            return -1;
        }
    }
	
   
}
