package com.example.autogradertyp.backend;

import java.io.*;

/**
 * This class runs a java program and grade it according to a test case.
 *
 * @author Fahd Alsahali
 * @date 06/11/2022
 * @version 2.0
 * @since 14/11/2022
 */

public class JavaGrader {

    private Process pro = null;
    private String programOutput;
    private String fileName;
    private String testCaseInput;
    private String testCaseOutput;
    private Boolean result;
    private String absolutePath = System.getProperty("user.dir");

    /**
     * A method to grade a java program
     *
     * @param mainFileName   The name of the java file that have the main method
     * @param testCaseInput  The test case input.
     * @param testCaseOutput The test case expected output.
     * @return A boolean to indicate if the programs passed the test case.
     * @throws IOException
     */
    public boolean gradeProgram(String mainFileName, String testCaseInput, String testCaseOutput) {
        this.fileName = mainFileName;
        this.testCaseInput = testCaseInput;
        this.testCaseOutput = testCaseOutput;

        try {

            runProcess("javac submissions_directory\\" + mainFileName + ".java");
            pro.waitFor();
            runProcess("java -classpath " + absolutePath + "\\submissions_directory " + mainFileName);

            inputToProcess();

            readFromProcess(pro.getInputStream());
            readFromProcess(pro.getErrorStream());


        } catch (Exception e) {
            e.printStackTrace();
        }

        result = programOutput.equalsIgnoreCase(testCaseOutput);
        return result;
    }

    /**
     * A method to execute commands in cmd
     *
     * @param command The command that will be executed
     * @throws Exception
     */
    private void runProcess(String command) throws Exception {

        pro = Runtime.getRuntime().exec(command);
    }

    /**
     * A method that inputs a value to a process
     *
     * @throws IOException
     */
    private void inputToProcess() throws IOException {

        //inputting test case input
        pro.getOutputStream();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(pro.getOutputStream()));
        out.write(testCaseInput);
        out.close();
    }

    /**
     * A method that reads process output.
     *
     * @param ins Input stream of the process.
     * @throws Exception
     */
    private void readFromProcess(InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));

        //reading the program output
        while ((line = in.readLine()) != null) {

            System.out.println(line);
            programOutput = line;
        }
    }
}
