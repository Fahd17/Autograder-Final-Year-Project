package com.example.autogradertyp.backend;

import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlagiarismDetectorConnection {

    private static String apiEndpoint = "https://codequiry.com/api/v1/";

    private static String apiKey = "5796c39f4698e097692974916cc02c2691e7118030ddf35f78e5d7c5426a7bdf";

    static File file = new File(".\\submissions_directory\\hey.zip");

    public static OkHttpClient getOkHttpClientInstance () {

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }

    public static JSONObject crateCheck (String name) throws Exception {

        HttpUrl url = HttpUrl.parse(apiEndpoint).newBuilder()
                .addPathSegments("check")
                .addPathSegments("create")
                .addQueryParameter("name", name)
                .addQueryParameter("language", "13")
                .build();


        RequestBody formBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .header("Accept","*/*")
                .header("apikey", apiKey)
                .build();



        Response response = getOkHttpClientInstance().newCall(request).execute();

        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.body().string());

        return jsonResponse;
    }

    public static JSONObject uploadFile (File file, String checkId) throws Exception {

        //Building the url
        HttpUrl url = HttpUrl.parse(apiEndpoint).newBuilder()
                .addPathSegments("check")
                .addPathSegments("upload")
                .build();

        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("application/zip"), file))
                .addFormDataPart("check_id", checkId)
                .build();

        Request request = new Request.Builder()
                .url(url)  // pass the url endpoint of api
                .post(formBody)
                .header("apikey", apiKey)
                .build();

        Response response = getOkHttpClientInstance().newCall(request).execute();

        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.body().string());

        return jsonResponse;

    }

    public static JSONObject startCheck (String checkId) throws Exception {

        //Building the url
        HttpUrl url = HttpUrl.parse(apiEndpoint).newBuilder()
                .addPathSegments("check")
                .addPathSegments("start")
                .addQueryParameter("check_id", checkId)
                .addQueryParameter("dbcheck", "1")
                .addQueryParameter("webcheck ", "1")
                .build();


        RequestBody formBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .header("apikey", apiKey)
                .build();

        Response response = getOkHttpClientInstance().newCall(request).execute();

        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.body().string());

        return jsonResponse;
    }

    public static JSONObject getResultsOverview (String checkId) throws Exception {

        //Building the url
        HttpUrl url = HttpUrl.parse(apiEndpoint).newBuilder()
                .addPathSegments("check")
                .addPathSegments("overview")
                .addQueryParameter("check_id", checkId)
                .build();


        RequestBody formBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .header("apikey", apiKey)
                .build();

        Response response = getOkHttpClientInstance().newCall(request).execute();

        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.body().string());

        return jsonResponse;
    }


    //TODO remove this main method
    public static void main(String[] argv) throws Exception {

        System.out.println(crateCheck("new").toJSONString());
        //System.out.println(uploadFile(file, "75693"));
        //System.out.println(startCheck( "75693"));
        //System.out.println(getResultsOverview( "75693"));
    }
}
