package com.example.autogradertyp.backend;


import com.example.autogradertyp.data.entity.Submission;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * This class will be used to communicate Canvas API
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 23/02/2023
 */
public class CanvasIntegrator {

    private static final String apiEndpoint = "http://localhost:8080/webapi/myresource/canvasapisimulator/api/";

    /**
     * A method that uploads a grade of one student
     *
     * @param submission The of the student to be uploaded
     * @throws IOException
     */
    public static void uploadStudentGrade (Submission submission) throws IOException {
        //Building the url
        HttpUrl url = HttpUrl.parse(apiEndpoint).newBuilder()
                .addPathSegments("courses/assignments/submissions")
                .addQueryParameter("course_id", submission.getAssignment().getCourse().getCode())
                .addQueryParameter("assignment_id", submission.getAssignment().getAssignmentName())
                .addQueryParameter("user_id", submission.getUser().getStudentNumber())
                .addQueryParameter("grade", String.valueOf(submission.getMarks()/ submission.getTotalMarks()*100))
                .build();

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)  // pass the url endpoint of api
                .get()
                .build();

        Response resp = okHttpClient.newCall(request).execute();
        System.out.println(resp);
    }

    /**
     * A method that upload the grades of a group of students
     *
     * @param submissions The submissions of the students to be uploaded
     */
    public static void uploadStudentsGrade (ArrayList<Submission> submissions){

        for (int i = 0; i < submissions.size(); i++){

            try {
                uploadStudentGrade(submissions.get(i));
            } catch (Exception e) {

            }
        }

    }
}
