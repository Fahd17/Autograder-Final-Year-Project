package com.example.autogradertyp.data.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public TestCase(String testCaseInput, String expectedOutput) {

        this.testCaseInput = testCaseInput;
        this.expectedOutput = expectedOutput;
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
