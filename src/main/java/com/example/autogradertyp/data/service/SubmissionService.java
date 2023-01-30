package com.example.autogradertyp.data.service;


import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private final SubmissionRepository repository;

    public SubmissionService(SubmissionRepository repository) {
        this.repository = repository;
    }

    public List<Submission> getAllSubmissions() {
        return repository.findAll();
    }

    public Submission add(Submission submission) {
        return repository.save(submission);
    }

    public ArrayList<Submission> getStudentSubmissionsForAssignment(Assignment assignment, User user){

        ArrayList<Submission> submissions = new ArrayList<>();
        List<Submission> allSubmissions = getAllSubmissions();

        for (int i = 0; i < allSubmissions.size(); i++) {

            if(allSubmissions.get(i).getAssignment().getId().equals(assignment.getId()) &&
                    allSubmissions.get(i).getUser().getId().equals(user.getId())){

                submissions.add(allSubmissions.get(i));
            }
        }

        return submissions;

    }

    public int nextAttemptNumber (Assignment assignment, User user){

        return getStudentSubmissionsForAssignment(assignment, user).size()+1;
    }
}
