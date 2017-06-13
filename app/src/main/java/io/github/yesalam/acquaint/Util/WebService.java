package io.github.yesalam.acquaint.Util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;

public class WebService {

    static final String COOKIES_HEADER = "Set-Cookie";
    static final String COOKIE = "Cookie";

    static CookieManager msCookieManager = new CookieManager();

    private static int responseCode;



    /*public static String testPost(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("UserName", "Gwalioroffice1")
                .addFormDataPart("Password", "Mohnish123")
                .addFormDataPart("RememberMe", "true")
                .addFormDataPart("RememberMe", "false")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

            *//*RequestBody body = RequestBody.create(json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();*//*
            Response response = client.newCall(request).execute();
            return response.body().string();
    }*/



    public static String sendPost(String requestURL, String urlParameters) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            conn.setRequestProperty("Connection","keep-alive");
            conn.setRequestProperty("Content-Length","76");
            conn.setRequestProperty("Cache-Control","max-age=0");
            conn.setRequestProperty("Origin","http://myacquaint.com");
            conn.setRequestProperty("Upgrade-Insecure-Requests","1");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.setRequestProperty("Referer","http://myacquaint.com/");
            conn.setRequestProperty("Accept-Encoding","gzip, deflate");
            conn.setRequestProperty("Accept-Language","en-US,en;q=0.8");
            conn.setRequestProperty("Cookie",".ASPXAUTH=74156FC6B6540287165CFD9C12BFE23B6EB230B893F1D14E1819BE2C1A58381B6DDBEDF97A9D4F1E67392BB06E79D2BB5DFBB961C2FACA44A05C56B8A6BDFF6EB7AADA2AFD8A8C4E98C76EDB49B8350C0F6C8167C769625F39D067661E102054267E0D114AD7281C50C6E0776E4E2133; unm=Gwalioroffice1; rem=True");





            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                conn.setRequestProperty(COOKIE ,
                        TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            }

            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            if (urlParameters != null) {
                writer.write(urlParameters);
            }
            writer.flush();
            writer.close();
            os.close();

            Map<String, List<String>> headerFields = conn.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }

            setResponseCode(conn.getResponseCode());

            if (getResponseCode() == HttpsURLConnection.HTTP_OK) {

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = ""+getResponseCode();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        return response;
    }


    // HTTP GET request
    public static String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla");
    /*
    * https://stackoverflow.com/questions/16150089/how-to-handle-cookies-in-httpurlconnection-using-cookiemanager
    * Get Cookies form cookieManager and load them to connection:
     */
        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
            con.setRequestProperty(COOKIE ,
                    TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
        }

    /*
    * https://stackoverflow.com/questions/16150089/how-to-handle-cookies-in-httpurlconnection-using-cookiemanager
    * Get Cookies form response header and load them to cookieManager:
     */
        Map<String, List<String>> headerFields = con.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }


        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static void setResponseCode(int responseCode) {
        WebService.responseCode = responseCode;
        //Log.i("Milad", "responseCode" + responseCode);
    }


    public static int getResponseCode() {
        return responseCode;
    }
}