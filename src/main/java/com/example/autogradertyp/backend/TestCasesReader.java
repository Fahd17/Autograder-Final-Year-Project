package com.example.autogradertyp.backend;

import com.example.autogradertyp.views.CreateAssignmentView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * The class is used to read testcases form a file and construct them in a view
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 28/02/2023
 */

public class TestCasesReader {
    static JSONParser parser = new JSONParser();

    /**
     * A method that reads a file and parse it contain as Json
     *
     * @param filename The file name
     * @param createAssignmentView the view the test cases will be added to
     * @throws Exception
     */
    public static void readTestCases(String filename, CreateAssignmentView createAssignmentView) throws Exception {


        JSONArray testcases = (JSONArray) parser.parse(new FileReader(".\\submissions_directory\\" + filename));

        for(int i = 0; i < testcases.size(); i++){

            JSONObject jsonObject = (JSONObject) testcases.get(i);

            String input = (String) jsonObject.get("input");
            String output = (String) jsonObject.get("output");
            String mark = (String) jsonObject.get("mark");
            System.out.println(mark);

            createAssignmentView.addTestCase(input, output, mark);
        }
    }
}
