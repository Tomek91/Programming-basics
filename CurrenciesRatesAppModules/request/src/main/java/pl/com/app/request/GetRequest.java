package pl.com.app.request;


import pl.com.app.exceptions.MyException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequest {

    public static String sendGetRequest(final String nbpUrl) {
        StringBuilder jsonInString = new StringBuilder();
        try {
            if (nbpUrl == null) {
                throw new NullPointerException("URL IS NULL");
            }

            URL url = new URL(nbpUrl);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String line;
            while ((line = br.readLine()) != null) {
                jsonInString.append(line);
            }

            if (conn.getResponseCode() != 200) {
                throw new MyException("Error with response status " + conn.getResponseCode());
            }
            conn.disconnect();

        } catch (Exception e) {
           throw new MyException("NBP REQUEST EXCEPTION");
        }
        return jsonInString.toString();
    }
}
