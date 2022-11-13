package com.example.autogradertyp.backend;

public class Assignment {

    private String assignmentName;
    private String assignmentID;
    private String courseID;
    private TestCase testCase;

    public Assignment (String assignmentName, String assignmentID, String courseID,TestCase testCase){

        this.assignmentName = assignmentName;
        this.assignmentID = assignmentID;
        this.courseID = courseID;
        this.testCase = testCase;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getAssignmentID() {
        return assignmentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public TestCase getTestCase() {
        return testCase;
    }
}
