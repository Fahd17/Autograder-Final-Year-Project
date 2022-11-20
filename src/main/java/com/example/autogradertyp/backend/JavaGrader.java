package com.example.autogradertyp.backend;

import java.io.*;

public class JavaGrader {

    String programsdirectory = "Users\\fahds\\IdeaProjects\\autograder-TYP\\submissions_directory";
    Process pro = null;
    String programOutput;
    String fileName;
    String testCaseInput;
    String testCaseOutput;
    Boolean result;
    String absolutePath = System.getProperty("user.dir");

    /**
     * A method to grade a java program
     *
     * @param mainFileName   The name of the java file that have the main method
     * @param testCaseInput  The test case input.
     * @param testCaseOutput The test case expected output.
     * @return A boolean to indicate if the programs passed the test case.
     * @throws IOException
     */
    public boolean gradeProgram(String mainFileName, String testCaseInput, String testCaseOutput) throws IOException {
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
