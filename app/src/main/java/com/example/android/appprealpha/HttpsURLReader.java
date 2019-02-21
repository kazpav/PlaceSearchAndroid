package com.example.android.appprealpha;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Pavlo on 07-May-18.
 */

public class HttpsURLReader {
    public static String makeCall(String myURL) throws IOException {
        String data = "";
        InputStream is = null;

        HttpsURLConnection urlConnection = null;
        try {
            URL url = new URL(myURL);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.connect();

            is = urlConnection.getInputStream();


            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
            urlConnection.disconnect();
        }

        return data;
    }
}

