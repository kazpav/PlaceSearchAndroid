package com.example.android.appprealpha;
import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;


//import org.apache.http.HttpResponse;
//
//import org.apache.http.client.HttpClient;
//
//import org.apache.http.client.methods.HttpGet;
//
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import org.apache.http.util.ByteArrayBuffer;

import org.json.JSONArray;

import org.json.JSONObject;


import android.app.Activity;
import android.app.ListActivity;

import android.os.AsyncTask;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import javax.net.ssl.HttpsURLConnection;
public class GetPlacesActivity extends AppCompatActivity {

    List<GooglePlace> venuesList = new ArrayList<>();


    // the google key


    // ============== YOU SHOULD MAKE NEW KEYS ====================//

    final String GOOGLE_KEY = "AIzaSyBb9nvlXDU_FPDoTQP5HpU9Fc3y7teO14Y";


    // we will need to take the latitude and the logntitude from a certain point

    // this is the center of New York

    //    final String latitude = "40.7463956";
//    final String latitude = "50.3796761";

    //    final String longtitude = "-73.9852992";
//    final String longtitude = "30.4831826";
//    private String longtitude;
//    private String latitude;

    ArrayAdapter myAdapter;
    private static final String TAG = "GetPlacesActivity";

    private String longtitude;
    String latitude;
    private static boolean typeCheck = false; //check for static!!
    private static String keyType;


    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_places);

        mProgressBar = (ProgressBar)findViewById(R.id.pb_progressBar);
        new googleplaces().execute();

        LocationServiceSingleton singleton3 = LocationServiceSingleton.getInstance("THIRD");
        Log.i(TAG, "onCreate: ==================================================================");
        Log.i(TAG, "onCreate: ================================================"+singleton3);

        MainActivity mainActivity = new MainActivity();

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            longtitude = arguments.getString("longitude");
            latitude = arguments.getString("latitude");
            keyType = arguments.getString("type");
            Log.i(TAG, "onCreate: ------------------------------"+longtitude+"-------------"+latitude);
        }
    }
    private class googleplaces extends AsyncTask<Void, Void, Void> {


        String temp;
        private Activity mActivity;



        @Override
        protected Void doInBackground(Void... params) {

            // make Call to the url
            HttpsURLReader urlReader = new HttpsURLReader();

            try {
                temp = urlReader.makeCall("https://maps.googleapis.com/maps/api/place/search/json?location=" + latitude + "," + longtitude + "&radius=500&sensor=true&key=" + GOOGLE_KEY);
//                temp = makeCall("https://maps.googleapis.com/maps/api/place/search/json?location=" + latitude + "," + longtitude + "&radius=500&sensor=true&key=" + GOOGLE_KEY);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //print the call in the console

            System.out.println("https://maps.googleapis.com/maps/api/place/search/json?location=" + latitude + "," + longtitude + "&radius=500&sensor=true&key=" + GOOGLE_KEY);

            return null;

        }




        @Override

        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);


            // we can start a progress bar here

        }


        @Override

        protected void onPostExecute(Void result) {

            if (temp == null) {

                // we have an error to the call

                // we can also stop the progress bar

            } else {

                // all things went right


                // parse Google places search result

                venuesList = (ArrayList<GooglePlace>) parseGoogleParse(temp);


                List listTitle = new ArrayList();


                for (int i = 0; i < venuesList.size(); i++) {

                    // make a list of the venus that are loaded in the list.

                    // show the name, the category and the city

                    listTitle.add(i, venuesList.get(i).getName() + "\nOpen Now: " + venuesList.get(i).getOpen() + "\n(" + venuesList.get(i).getCategory() + ")");

                }


                // set the results to the list

                // and show them in the xml
                //just commented. setting arrayadapter. todelete after recycler
//                myAdapter = new ArrayAdapter(GetPlacesActivity.this, R.layout.row_layout, R.id.listText, listTitle);

//                setListAdapter(myAdapter);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
                GooglePlaceAdapter adapter = new GooglePlaceAdapter(GetPlacesActivity.this, venuesList);
                recyclerView.setAdapter(adapter);
            }

            mProgressBar.setVisibility(View.INVISIBLE);
        }

    }


    public static String makeCall(String myURL) throws IOException {
        String data ="";
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
            while ((line = br.readLine())!=null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            is.close();
            urlConnection.disconnect();
        }

        return data;




        //        // string buffers the url
//        StringBuffer buffer_string = new StringBuffer(url);
//        String replyString = "";
//        // instanciate an HttpClient
//        HttpClient httpclient = new DefaultHttpClient();
//        // instanciate an HttpGet
//        HttpGet httpget = new HttpGet(buffer_string.toString());
//        try {
//            // get the responce of the httpclient execution of the url
//            HttpResponse response = httpclient.execute(httpget);
//            InputStream is = response.getEntity().getContent();
//            // buffer input stream the result
//            BufferedInputStream bis = new BufferedInputStream(is);
//            ByteArrayBuffer baf = new ByteArrayBuffer(20);
//            int current = 0;
//            while ((current = bis.read()) != -1) {
//                baf.append((byte) current);
//            }
//            // the result as a string is ready for parsing
//            replyString = new String(baf.toByteArray());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(replyString);
//        // trim the whitespaces
//        return replyString.trim();
    }

    private static ArrayList parseGoogleParse(final String response) {


        ArrayList<GooglePlace> temp = new ArrayList();

        try {


            // make an jsonObject in order to parse the response

            JSONObject jsonObject = new JSONObject(response);


            // make an jsonObject in order to parse the response

            if (jsonObject.has("results")) {


                JSONArray jsonArray = jsonObject.getJSONArray("results");


                for (int i = 0; i < jsonArray.length(); i++) {

//                    if(jsonArray.getJSONObject(i).getJSONObject("types").has("store")){
//                        Log.i(TAG, "parseGoogleParse: -----------------RESTAURANT----------------------");
//                    }else{
//                        Log.i(TAG, "parseGoogleParse: ------------------------NOT WORKED-------------------");
//                    }
                    if (jsonArray.getJSONObject(i).has("types")) {

                        JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");


                        for (int j = 0; j < typesArray.length(); j++) {

//                            poi.setCategory(typesArray.getString(j) + ", " + poi.getCategory());
                            if(typesArray.getString(j).equals(keyType)||keyType.equals("all")){
                                typeCheck = true;
                                break;
                            }
                        }

                    }

                    if(typeCheck == true) {
                        GooglePlace poi = new GooglePlace();

                        if (jsonArray.getJSONObject(i).has("name")) {

                            poi.setName(jsonArray.getJSONObject(i).optString("name"));

                            poi.setRating(jsonArray.getJSONObject(i).optString("rating", " "));
                            poi.setImageURL(jsonArray.getJSONObject(i).optString("icon"));
                            poi.setAddress(jsonArray.getJSONObject(i).optString("vicinity"));
                            Log.i(TAG, "parseGoogleParse: "+poi.getAddress());


                            if (jsonArray.getJSONObject(i).has("opening_hours")) {

                                if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").has("open_now")) {

                                    if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").getString("open_now").equals("true")) {

                                        poi.setOpen("YES");

                                    } else {

                                        poi.setOpen("NO");

                                    }

                                }

                            } else {

                                poi.setOpen("Not Known");

                            }

                            if (jsonArray.getJSONObject(i).has("types")) {

                                JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");


                                for (int j = 0; j < typesArray.length(); j++) {

                                    poi.setCategory(typesArray.getString(j) + ", " + poi.getCategory());

                                }

                            }

                        }

                        temp.add(poi);
                        typeCheck = false;
                    } //skobka
                }

            }

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList();

        }

        return temp;


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ============================================================");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: =============================================================");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ============================================================");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ===========================================================");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ============================================================");
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ==============================================================");
        super.onDestroy();
    }
}