package com.example.autogradertyp.backend;

import java.io.*;

public class JavaGrader {

    String programsdirectory = "Users\\fahds\\IdeaProjects\\autograder-TYP\\submissions_directory";
    String programOutput;
    String fileName;
    String testCaseInput;
    String testCaseOutput;

    public boolean gradeProgram(String mainFileName, String testCaseInput, String testCaseOutput) throws IOException {
        this.fileName = mainFileName;
        this.testCaseInput = testCaseInput;
        this.testCaseOutput = testCaseOutput;

        try {

            runProcess("javac " + mainFileName + ".java");
            runProcess("java " + mainFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return programOutput.equalsIgnoreCase(testCaseOutput);
    }

    private void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);

        //inputting test case input
        pro.getOutputStream();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(pro.getOutputStream()));
        out.write(testCaseInput);
        out.close();

        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());

        pro.waitFor();
    }

    private void printLines(String name, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));

        //reading the program output
        while ((line = in.readLine()) != null) {

            System.out.println(name + " " + line);
            programOutput = line;
        }
    }
}
