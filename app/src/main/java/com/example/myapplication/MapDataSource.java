package com.example.myapplication;

import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

public class MapDataSource {
    String url;
    String filter;


    public MapDataSource(String url){
        this.url = url;
    }

    public MapDataSource(String url, String filter){
        this.url = url;

        this.filter = filter;

    }

    public JSONObject getDataSource() {
        return getJsonObjectFromApi(url);
    }

    public JSONArray getJsonArrayFromApi(String url) {
        try {
            return new JSONArray(getJsonFromApi(url));
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject getJsonObjectFromApi(String url) {
        try {
            return new JSONObject(getJsonFromApi(url));
        } catch (JSONException e) {
            return null;
        }
    }
    public String getJsonFromApi(String url) {
        int responseCode = 0;
        try {
            HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(url).openConnection();
            urlConnection.setSSLSocketFactory(new TLSSocketFactory());
            responseCode = urlConnection.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = br.read()) != -1)
                sb.append((char) c);
            br.close();
            return sb.toString();
        } catch (IOException e) {
            if (responseCode == 429)
                return "{\"success\":false,\"cause\":\"Request limit\"}";
            return "{\"success\":false,\"cause\":\"Error " + responseCode + "\"}";
        } catch (NoSuchAlgorithmException ignore) {

        } catch (KeyManagementException ignore) {

        }
        return "";
    }

}
