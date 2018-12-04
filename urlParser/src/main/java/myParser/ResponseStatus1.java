package myParser;

import java.net.HttpURLConnection;
import java.net.URL;


public class ResponseStatus1 {

    public static int getStatus(String stringUrl){
        int code = 0;

        try {

            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            code = connection.getResponseCode();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }


}
