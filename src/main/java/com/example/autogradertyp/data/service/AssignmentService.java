package com.example.autogradertyp.data.service;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Course;
import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.repository.AssigmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * A method that gets all assignments in the database
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
     * A method that gets the assignments of a specific course
     *
     * @param course The target course
     * @return The assignments of the specified course
     */
    public ArrayList<Assignment> getAssignmentsForCourse(Course course) {

        ArrayList<Assignment> assignments = new ArrayList<>();
        List<Assignment> allAssignment = getAllAssignments();

        // going over all the assignments and checking related to the targeted course
        for (int i = 0; i < allAssignment.size(); i++) {

            if (allAssignment.get(i).getCourse().getId().equals(course.getId())) {
                assignments.add(allAssignment.get(i));
            }
        }

        return assignments;

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
