package com.example.autogradertyp.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Assignment {

    @Id
    @GeneratedValue
    private Long id;

    private String assignmentName;
    private String assignmentID;
    private String courseID;

    @OneToOne
    @JoinColumn(name = "test_case_id")
    private TestCase testCase;



    public Assignment (String assignmentName, String assignmentID, String courseID,TestCase testCase){

        this.assignmentName = assignmentName;
        this.assignmentID = assignmentID;
        this.courseID = courseID;
        this.testCase = testCase;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public void setAssignmentID(String assignmentID) {
        this.assignmentID = assignmentID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setTestCase(TestCase testCase) {
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

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", assignmentName='" + assignmentName + '\'' +
                ", assignmentID='" + assignmentID + '\'' +
                ", courseID='" + courseID + '\'' +
                ", testCase=" + testCase +
                '}';
    }
}
