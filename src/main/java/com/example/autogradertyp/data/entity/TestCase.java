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
public class TestCase {


    @Id
    @GeneratedValue
    private Long id;
    private String testCaseInput;
    private String expectedOutput;

    private int marks;

    @ManyToOne
    @JoinColumn(name="assignment_id", nullable=false)
    private Assignment assignment;


    public TestCase(String testCaseInput, String expectedOutput, int marks, Assignment assignment) {

        this.testCaseInput = testCaseInput;
        this.expectedOutput = expectedOutput;
        this.assignment = assignment;
        this.marks = marks;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public void setTestCaseInput(String testCaseInput) {
        this.testCaseInput = testCaseInput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getTestCaseInput() {
        return testCaseInput;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }
}
