package io.proxylabs.spawning_pool.http;

import com.google.gson.Gson;
import io.proxylabs.spawning_pool.models.*;
import jdk.nashorn.internal.objects.NativeJSON;
import okhttp3.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by dbland on 4/20/17.
 */
public class HttpService {

    private static final String PROPERTIES_FCM_SERVER_KEY = "fcm_server_key";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String FCM_POST_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SPAWN_NOTIFICATION_TITLE = "You caught a monster!";
    private static final String SPAWN_NOTIFICATION_BODY = " has been added to your collection.";

    private static HttpService httpService;
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private Properties properties;

    private HttpService(Properties properties){
        this.properties = properties;
    }

    public static HttpService getInstance(Properties properties){
        if (httpService == null){
            httpService = new HttpService(properties);
        }
        return httpService;
    }

    public void post(String url, String json){
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .header("Authorization", "key=" + properties.getProperty(PROPERTIES_FCM_SERVER_KEY))
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //TODO what do we wanna do with the response?
        } catch (IOException e){
            //TODO Log this
        }
    }

    public void postNotification (FcmToken token, Unit unit){
        Notification notification = new Notification(SPAWN_NOTIFICATION_TITLE, "A " + unit.getName() + SPAWN_NOTIFICATION_BODY);
        Message message = new Message(token.getBody(), notification);
        System.out.println("Post Notification: " + gson.toJson(message));
        post(FCM_POST_URL, gson.toJson(message));
    }


}
