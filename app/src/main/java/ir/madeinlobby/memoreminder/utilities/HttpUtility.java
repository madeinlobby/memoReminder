package ir.madeinlobby.memoreminder.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtility {
    private static HttpURLConnection httpConn;
    private static URL url;

    public static void openConnection(String requestURL) throws IOException {
        url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
    }

    public static void sendPostRequest(Map<String, String> params) throws IOException {
        httpConn.setUseCaches(false);

        httpConn.setDoInput(true); // true indicates the server returns response

        StringBuffer requestParams = new StringBuffer();

        if (params != null && params.size() > 0) {

            httpConn.setDoOutput(true); // true indicates POST request

            // creates the params string, encode them using URLEncoder
            Iterator<String> paramIterator = params.keySet().iterator();
            while (paramIterator.hasNext()) {
                String key = paramIterator.next();
                String value = params.get(key);
                requestParams.append(URLEncoder.encode(key, "UTF-8"));
                requestParams.append("=").append(
                        URLEncoder.encode(value, "UTF-8"));
                requestParams.append("&");
            }

            // sends POST data
            OutputStreamWriter writer = new OutputStreamWriter(
                    httpConn.getOutputStream());
            writer.write(requestParams.toString());
            writer.flush();
        }
    }

    public static String readResponse() throws IOException {
        InputStream inputStream = null;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder response = new StringBuilder();

        String line = "";
        while ((line = reader.readLine()) != null) {
            response.append(line).append("\n");
        }
        reader.close();

        return response.toString();
    }
}
