package com.example.autogradertyp.backend;

public class TestCase {

    private String testCaseInput;
    private String expectedOutput;

    public TestCase(String testCaseInput, String expectedOutput) {

        this.testCaseInput = testCaseInput;
        this.expectedOutput = expectedOutput;
    }

    public String getTestCaseInput() {
        return testCaseInput;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }
}
