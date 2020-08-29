package ir.madeinlobby.memoreminder.utilities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class HttpUtility {
    private static HttpURLConnection httpConn;
    private static URL url;
    private static StringBuilder response;

    public static String sendPostRequest(String requestURL, Map<String, String> params) {
        try {
            url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            StringBuffer requestParams = new StringBuffer();

            if (params.size() > 0) {

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
                OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
                writer.write(requestParams.toString());
                writer.flush();
            }

            InputStream inputStream = httpConn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));

            String line = "";
            response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            reader.close();
            httpConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String sendPostRequest (String requestUrl, Map<String, String> params, ArrayList<String> files) {
        String charset = "UTF-8";
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
        try {
            URLConnection connection = null;
            connection = new URL(requestUrl).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            OutputStream output = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);

            for (String key : params.keySet()) {
                writer.append("--").append(boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=").append(charset).append(CRLF);
                writer.append(CRLF).append(params.get(key)).append(CRLF).flush();
            }

            for (String file : files) {
                File thisFile = new File(file);
                writer.append("--").append(boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"file[]\"; filename=\"").
                        append(thisFile.getName()).append("\"").append(CRLF);
                writer.append("Content-Type: ").append(URLConnection
                        .guessContentTypeFromName(thisFile.getName())).append(CRLF);
                writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                writer.append(CRLF).flush();
                Files.copy(thisFile.toPath(), output);
                output.flush(); // Important before continuing with writer!
                writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
            }


            // End of multipart/form-data.
            writer.append("--").append(boundary).append("--").append(CRLF).flush();

            InputStream inputStream = null;
            inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            StringBuilder response = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            reader.close();
        }catch (Exception e) {
        }
        return response.toString();
    }
}

