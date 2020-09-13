package ir.madeinlobby.memoreminder.utilities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

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
import java.net.URI;
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
    private static String cookie;

    public static void setCookie(String cookie) {
        HttpUtility.cookie = cookie;
    }

    public static String sendPostRequest(String requestURL, Map<String, String> params) {
        try {
            url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Cookie", cookie);
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
        System.out.println("RESP:     " + response);
        return response.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String sendPostRequest (ContentResolver resolver, String requestUrl, Map<String, String> params, ArrayList<Uri> files) {
        String charset = "UTF-8";
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
        try {
            URLConnection connection = null;
            connection = new URL(requestUrl).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setRequestProperty("Cookie", cookie);
            OutputStream output = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);

            for (String key : params.keySet()) {
                writer.append("--").append(boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=").append(charset).append(CRLF);
                writer.append(CRLF).append(params.get(key)).append(CRLF).flush();
            }

            for (Uri file : files) {
                InputStream fileInputStream = resolver.openInputStream(file);
                writer.append("--").append(boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"file[]\"; filename=\"").
                        append(getNameFromURI(resolver, file)).append("\"").append(CRLF);
                writer.append("Content-Type: ").append(URLConnection
                        .guessContentTypeFromName(getNameFromURI(resolver, file))).append(CRLF);
                writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                writer.append(CRLF).flush();
                sendFile(fileInputStream, output);
                output.flush(); // Important before continuing with writer!
                writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
                fileInputStream.close();
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
            e.printStackTrace();
        }
        System.out.println("RESP:     " + response);
        return response.toString();
    }

    public static String getRealPathFromURI(ContentResolver resolver, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = resolver.query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getNameFromURI (ContentResolver resolver, Uri contentUri) {
        Cursor returnCursor = resolver.query(contentUri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        return returnCursor.getString(nameIndex);
    }

    public static void sendFile (InputStream inputStream, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1)
                break;
            output.write(buffer, 0, bytesRead);
        }
    }

    public static void loadBitmapIntoImage (final Context context, final ImageView view, final String addr) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(addr);
                    URLConnection conn = url.openConnection();
                    conn.addRequestProperty("Cookie", cookie);
                    final Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(bmp);
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void getCookies (Context context) {
        WebView webView = new WebView(context);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(BaseController.server + "/getCode.php");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String cookie = CookieManager.getInstance().getCookie(BaseController.server + "/getcode.php");
                setCookie(cookie);
            }
        });
    }
}

