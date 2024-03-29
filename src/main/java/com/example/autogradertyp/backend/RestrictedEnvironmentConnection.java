package com.example.autogradertyp.backend;

import okhttp3.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * The class is used to communicate with the restricted environment
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 15/02/2023
 */

public class RestrictedEnvironmentConnection {

    private final String apiEndpoint = "http://localhost:8081/api";

    /**
     * A method that send the student submission to the restricted environment
     *
     * @param fileName The submission name
     * @param fileContent The submission file content
     * @throws IOException
     */
    public void sendFileToRestrictedEnvironment (String fileName, byte[] fileContent) throws IOException {

        String contentType = "text/plain";

        MultipartFile file = new MockMultipartFile(fileName,
                fileName, contentType, fileContent);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .build();


        RequestBody fileBody = RequestBody.create(okhttp3.MediaType.parse(file.getContentType()), file.getBytes());

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getOriginalFilename(),fileBody)
                .build();

        Request request = new Request.Builder()
                .url(apiEndpoint)
                .post(multipartBody)
                .build();

        Response respone = okHttpClient.newCall(request).execute();
    }

    /**
     *  A method that communicates with the restricted environment to grade a test case
     *
     * @param mainFileName The name of the submission file
     * @param testCaseInput The input of the test case
     * @param testCaseOutput The expected output of the test case
     * @return Whether the submission passed the test case
     * @throws IOException
     */
    public boolean gradeATestCase (String mainFileName, String testCaseInput, String testCaseOutput) throws IOException {

        
        HttpUrl url = HttpUrl.parse(apiEndpoint).newBuilder()
                .addPathSegments("grade")
                .addQueryParameter("name", mainFileName)
                .addQueryParameter("testCaseInput", testCaseInput)
                .addQueryParameter("testCaseOutput", testCaseOutput)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response resp = okHttpClient.newCall(request).execute();

        String response = resp.body().string();

        return Boolean.parseBoolean(response);

    }
}
