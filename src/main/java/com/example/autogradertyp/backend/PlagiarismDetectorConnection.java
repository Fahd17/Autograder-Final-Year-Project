package com.example.autogradertyp.backend;


import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * The class is used to communicate with the plagiarism detection api
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 06/04/2023
 */
public class PlagiarismDetectorConnection {

    private static String apiEndpoint = "https://codequiry.com/api/v1/";
    private static String apiKey = "f566d804d37ef4b8eb90c572933f1266099746525490f073ed1e115e0d679fec";

    private static OkHttpClient getOkHttpClientInstance() {

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }

    /**
     * A method to create a new plagiarism check
     *
     * @param name The name of the new check
     * @return The response of the API
     * @throws Exception
     */
    public static JSONObject crateCheck(String name) throws Exception {

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
                .header("Accept", "*/*")
                .header("apikey", apiKey)
                .build();

        Response response = getOkHttpClientInstance().newCall(request).execute();

        String jsonString  = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(jsonString);
        return jsonResponse;
    }

    /**
     * A method that uploads a submission file to the plagiarism detection api
     *
     * @param filename The name of the file uploaded
     * @param input The bytes of the file uploaded
     * @param checkId The Id of the check the submission is uploaded to
     * @return The response of the API
     * @throws Exception
     */
    public static JSONObject uploadFile(String filename, byte[] input, String checkId) throws Exception {

        String FILEPATH = ".\\submissions_directory\\" + filename + ".zip";
        File file = new File(FILEPATH);

        try {
            //Converting bytes of file to bytes of zip file
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);
            ZipEntry entry = new ZipEntry(filename + ".java");
            entry.setSize(input.length);
            zos.putNextEntry(entry);
            zos.write(input);
            zos.closeEntry();
            zos.close();

            FileOutputStream fos = new FileOutputStream(FILEPATH);
            fos.write(baos.toByteArray());
            fos.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

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

    /**
     * A method to start the plagiarism check
     *
     * @param checkId The Id of the plagiarism check
     * @return The response of the API
     * @throws Exception
     */
    public static JSONObject startCheck(String checkId) throws Exception {

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

    /**
     * A method to get the plagiarism result
     *
     * @param checkId The Id of the plagiarism check
     * @return The response of the API
     * @throws Exception
     */
    public static JSONObject getResultsOverview(String checkId) throws Exception {

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

}
