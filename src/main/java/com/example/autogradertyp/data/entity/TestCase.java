package com.example.autogradertyp.data.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * This class represent the test case table in the database.
 *
 * @author Fahd Alsahali
 * @date 10/12/2022
 * @version 1.0
 */

@Data
@NoArgsConstructor
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


    /**
     * Creates a new test case of an assigment
     *
     * @param testCaseInput The test case input.
     * @param expectedOutput The test case expected output
     * @param marks The marks assigned to the test case
     * @param assignment The assignment the test case is related to
     */
    public TestCase(String testCaseInput, String expectedOutput, int marks, Assignment assignment) {

        this.testCaseInput = testCaseInput;
        this.expectedOutput = expectedOutput;
        this.assignment = assignment;
        this.marks = marks;
    }

    /**
     * A method to get the marks assigned to the test case
     *
     * @return The marks
     */
    public int getMarks() {
        return marks;
    }

    /**
     * A method to set the marks assigned to the test case
     *
     * @param marks The marks
     */
    public void setMarks(int marks) {
        this.marks = marks;
    }

    /**
     * A method to get the input of a test case
     *
     * @return the input
     */
    public String getTestCaseInput() {
        return testCaseInput;
    }

    /**
     * A method that sets the test case input
     *
     * @param testCaseInput The input
     */
    public void setTestCaseInput(String testCaseInput) {
        this.testCaseInput = testCaseInput;
    }

    /**
     * A method to get the test case expected output
     *
     * @return The expected output
     */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    /**
     * A method to set the test case expected output
     *
     * @param expectedOutput The expected output
     */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

}
