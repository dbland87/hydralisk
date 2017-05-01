package io.proxylabs.spawning_pool.http;

import com.google.gson.Gson;
import io.proxylabs.spawning_pool.models.Message;
import io.proxylabs.spawning_pool.models.Notification;
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

    public void postNotification (Notification notification){
        Message message = new Message();
        message.setNotification(notification);

        post(FCM_POST_URL, gson.toJson(message));
    }


}
