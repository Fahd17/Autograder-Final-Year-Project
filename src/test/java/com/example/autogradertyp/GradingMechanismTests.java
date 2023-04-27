package com.example.autogradertyp;


import com.example.autogradertyp.backend.RestrictedEnvironmentConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
public class GradingMechanismTests {

    private Boolean testGrading(String fileName, String input, String output) throws IOException {

        File file = new File(".\\submissions_directory\\" + fileName + ".java");
        //turn file to bytes[]
        FileInputStream fileInput = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        fileInput.read(fileBytes);
        fileInput.close();

        RestrictedEnvironmentConnection connection =  new RestrictedEnvironmentConnection();
        connection.sendFileToRestrictedEnvironment(file.getName() + ".java", fileBytes);
        return connection.gradeATestCase(file.getName(), input, output);

    }

    @Test
    void test1 () throws IOException {

        Assertions.assertTrue(testGrading("FactorialCorrect", "4", "24"));
    }

    @Test
    void test2 () throws IOException {

        Assertions.assertFalse(testGrading("FactorialCorrect","5", "24"));
    }

    @Test
    void test3 () throws IOException {

        Assertions.assertTrue(testGrading("FactorialCorrect","5", "120"));
    }

    @Test
    void test4 () throws IOException {

        Assertions.assertFalse(testGrading("FactorialWrong", "4", "24"));
    }

    @Test
    void test5 () throws IOException {

        Assertions.assertFalse(testGrading("FactorialWrong","6", "720"));
    }

    @Test
    void test6 () throws IOException {

        Assertions.assertTrue(testGrading("FactorialWrong","5", "121"));
    }


}

