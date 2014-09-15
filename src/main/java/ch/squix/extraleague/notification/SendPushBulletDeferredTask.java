package ch.squix.extraleague.notification;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class SendPushBulletDeferredTask implements DeferredTask {
	
    private static final Logger log = Logger.getLogger(SendPushBulletDeferredTask.class.getName());
    private static final Joiner JOINER = Joiner.on(", ");
	private String apiKey;
	private String body;

	public final static SendPushBulletDeferredTask createPushBulletNoteTask(String apiKey, String title, String body) {
        try {
            StringBuilder httpBody = new StringBuilder();
            httpBody.append("type=note&");
            httpBody.append("title="+URLEncoder.encode(title, "UTF-8")+"&");
            httpBody.append("body="+URLEncoder.encode(body, "UTF-8"));
            return new SendPushBulletDeferredTask(apiKey, httpBody.toString());
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Server does not support UTF-8 encoding");
            return null;
        }
	}
	
	public final static SendPushBulletDeferredTask sendPushBulletLink(String apiKey, String title, String url, String body) {
        try {
            StringBuilder httpBody = new StringBuilder();
            httpBody.append("type=link&");
            httpBody.append("title="+URLEncoder.encode("Game starts now.", "UTF-8")+"&");
            httpBody.append("url="+URLEncoder.encode(url, "UTF-8")+"&");
            if (!Strings.isNullOrEmpty(body)) {
                httpBody.append("body="+URLEncoder.encode(body, "UTF-8"));
            }

            return new SendPushBulletDeferredTask(apiKey, httpBody.toString());
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Server does not support UTF-8 encoding");
            return null;
        }
    }


    private SendPushBulletDeferredTask(String apiKey, String body) {
		this.apiKey = apiKey;
		this.body = body;
    }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void run() {
        try {
            URL url = new URL("https://api.pushbullet.com/v2/pushes");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Authorization",
            		"Basic "+ Base64.encodeBase64String((apiKey).getBytes()));

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(body);
            writer.close();
    
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                log.log(Level.INFO, "Sent message");
            } else {
            	log.log(Level.SEVERE, "Could not send message: " + connection.getResponseCode() );
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Server does not support UTF-8 encoding", e);
        } catch (MalformedURLException e) {
        	throw new RuntimeException("Could not send message: ", e);
        } catch (IOException e) {
        	throw new RuntimeException("Could not send message: ", e);
        }
	}

}
