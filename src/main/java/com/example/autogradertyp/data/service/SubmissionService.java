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

    public Submission getUserCurrentSubmission (ArrayList<Submission> submissions) {

        Submission keptSubmission = submissions.get(0);
        for (int i = 0; i < submissions.size(); i++) {

            if (submissions.get(i).getMarks() > keptSubmission.getMarks()) {
                keptSubmission = submissions.get(i);
            }

        }
        return keptSubmission;
    }

    public ArrayList<Submission> getFinalSubmissions(Assignment assignment){

        ArrayList<User> users = new ArrayList<>();
        List<Submission> allSubmissions = getAllSubmissions();
        ArrayList<Submission> finalSubmissions = new ArrayList<>();

        for (int i = 0; i < allSubmissions.size(); i++){

            User user = allSubmissions.get(i).getUser();

            if (!users.contains(user)){
                users.add(user);

            }
        }
        System.out.println(users.size());

        for (int i = 0; i < users.size(); i++){

            finalSubmissions.add(getUserCurrentSubmission(
                    getStudentSubmissionsForAssignment(assignment, users.get(i))));
        }
        return finalSubmissions;

    }

    public int nextAttemptNumber (Assignment assignment, User user){

        return getStudentSubmissionsForAssignment(assignment, user).size()+1;
    }
}
