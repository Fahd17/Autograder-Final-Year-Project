package com.example.autogradertyp.data.service;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.repository.AssigmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private final AssigmentRepository repository;

    public AssignmentService(AssigmentRepository repository) {
        this.repository = repository;
    }

    public List<Assignment> getAllAssignments() {
        return repository.findAll();
    }

    public Assignment getAssigmentById(Long assignmentId) {

        List<Assignment> assignments = getAllAssignments();
        Assignment targetAssignment = null;

        for (int i = 0; i < assignments.size(); i++) {

            if (assignments.get(i).getId().equals(assignmentId)) {

                targetAssignment = assignments.get(i);
            }
        }
        return targetAssignment;
    }

    public Assignment add(Assignment assignment) {
        return repository.save(assignment);
    }

    public Assignment updateAssignment(Assignment assignment) {
        return repository.save(assignment);
    }

    public void deleteAssigment(Assignment assignment) {
        repository.delete(assignment);
    }


}
