package com.example.autogradertyp.data.service;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.repository.AssigmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A service class that provide CRUD of the assigment table in the database
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 10/12/2022
 */

@Service
public class AssignmentService {

    @Autowired
    private final AssigmentRepository repository;

    public AssignmentService(AssigmentRepository repository) {
        this.repository = repository;
    }

    /**
     * A method that gets all assignment in the database
     *
     * @return All the assignment in the database
     */
    public List<Assignment> getAllAssignments() {
        return repository.findAll();
    }

    /**
     * A method that gets an assignment with a specified id
     *
     * @param assignmentId The id of the wanted assignment
     * @return The wanted assignment
     */
    public Assignment getAssigmentById(Long assignmentId) {

        List<Assignment> assignments = getAllAssignments();
        Assignment targetAssignment = null;

        // going over all the assignments and checking if the ids match
        for (int i = 0; i < assignments.size(); i++) {

            if (assignments.get(i).getId().equals(assignmentId)) {

                targetAssignment = assignments.get(i);
            }
        }
        return targetAssignment;
    }

    /**
     * Adds an assignment to the assignment table
     *
     * @param assignment The assignment to be added
     */
    public void add(Assignment assignment) {

        repository.save(assignment);
    }

    /**
     * A method to update an assignment
     *
     * @param assignment The assignment to be updated
     */
    public void updateAssignment(Assignment assignment) {

        repository.save(assignment);
    }

    /**
     * A method that deletes an assignment
     *
     * @param assignment The assignment to be deleted
     */
    public void deleteAssigment(Assignment assignment) {
        repository.delete(assignment);
    }


}
