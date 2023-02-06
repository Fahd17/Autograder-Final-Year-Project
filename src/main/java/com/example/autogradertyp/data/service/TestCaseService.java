package com.example.autogradertyp.data.service;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A service class that provide CRUD of the testcase table in the database
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 10/12/2022
 */
@Service
public class TestCaseService {

    @Autowired
    private final TestCaseRepository repository;

    public TestCaseService(TestCaseRepository repository) {
        this.repository = repository;
    }

    /**
     * A method that gets all the test cases in the test case table
     *
     * @return All the test cases
     */
    public List<TestCase> getAllTestCases() {
        return repository.findAll();
    }

    /**
     * A method that adds a test case to the database
     *
     * @param testCase The test case to be added
     */
    public void add(TestCase testCase) {

        repository.save(testCase);
    }

    /**
     * A method that updates a testcase
     *
     * @param testCase The test case to be updated
     */
    public void update(TestCase testCase) {
        repository.save(testCase);
    }

    /**
     * A method that deletes a test case
     *
     * @param testCase The test case to be deleted
     */
    public void delete(TestCase testCase) {
        repository.delete(testCase);
    }

    /**
     * A method that gets the test case of a specific assigment
     *
     * @param assignment The target assigment
     * @return The test cases of the specified assignment
     */
    public ArrayList<TestCase> getTestCasesForAssignment(Assignment assignment) {

        ArrayList<TestCase> testCases = new ArrayList<>();
        List<TestCase> allTestCases;
        allTestCases = getAllTestCases();

        // going over all the test case and checking related to the targeted assignment
        for (int i = 0; i < allTestCases.size(); i++) {

            if (allTestCases.get(i).getAssignment().getId().equals(assignment.getId())) {
                testCases.add(allTestCases.get(i));
            }
        }

        return testCases;

    }
}
