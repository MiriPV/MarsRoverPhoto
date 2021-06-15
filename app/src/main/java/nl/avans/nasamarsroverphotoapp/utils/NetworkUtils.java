package nl.avans.nasamarsroverphotoapp.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkUtils {
    public static final String TAG = NetworkUtils.class.getSimpleName();
    public static final String API_KEY = "xIhUEnyavcxtow8fPi7Qd2AaC0sF57OlbmVS8QyJ";

    public static String buildUrl(String rover) {
        return "https://api.nasa.gov/mars-photos/api/v1/rovers/" + rover + "/photos?sol=1000&api_key=" + API_KEY;
    }

    public static String buildManifestUrl(String rover) {
        return "https://api.nasa.gov/mars-photos/api/v1/manifests/" + rover + "/?api_key=" + API_KEY;
    }

    public static String getResponseFromHttpUrl(String urlString) {

        InputStream inputStream = null;
        int responseCode = -1;
        String response = "";

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                Log.e(TAG, "Could not make a URL connection.");
                return null;
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
                Log.i(TAG, "Response = " + response);
            } else {
                Log.e(TAG, "Error with response: code = " + responseCode);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            return null;
        }
        return response;
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground IOException: " + e.getMessage());
                }
            }
        }
        return sb.toString();
    }
}

