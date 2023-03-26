package com.example.autogradertyp.data.service;


import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A service class that provide CRUD of the submission table in the database
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 01/02/2023
 */
@Service
public class SubmissionService {

    @Autowired
    private final SubmissionRepository repository;

    public SubmissionService(SubmissionRepository repository) {
        this.repository = repository;
    }

    /**
     * A method that gets all submissions for the database
     *
     * @return All the submissions
     */
    public List<Submission> getAllSubmissions() {
        return repository.findAll();
    }

    /**
     * A method that adds a submission to the database
     *
     * @param submission The submission to be added
     */
    public void add(Submission submission) {
        repository.save(submission);
    }

    /**
     * A method that gets all the submission of a user to an assignment
     *
     * @param assignment The targeted assignment
     * @param user       The targeted user
     * @return The test cases of the user to the assignment
     */
    public ArrayList<Submission> getStudentSubmissionsForAssignment(Assignment assignment, User user) {

        ArrayList<Submission> submissions = new ArrayList<>();
        List<Submission> allSubmissions = getAllSubmissions();

        //going over all submissions
        for (int i = 0; i < allSubmissions.size(); i++) {

            if (allSubmissions.get(i).getAssignment().getId().equals(assignment.getId()) &&
                    allSubmissions.get(i).getUser().getId().equals(user.getId())) {

                submissions.add(allSubmissions.get(i));
            }
        }

        return submissions;

    }

    /**
     * A method that gets all the submission of an assignment
     *
     * @param assignment The targeted assignment
     * @return The test cases of the user to the assignment
     */
    public ArrayList<Submission> getSubmissionsForAssignment(Assignment assignment) {

        ArrayList<Submission> submissions = new ArrayList<>();
        List<Submission> allSubmissions = getAllSubmissions();

        //going over all submissions
        for (int i = 0; i < allSubmissions.size(); i++) {

            if (allSubmissions.get(i).getAssignment().getId().equals(assignment.getId())) {

                submissions.add(allSubmissions.get(i));
            }
        }

        return submissions;
    }

    /**
     * A method that gets the most recent submission with the highest mark achieved
     *
     * @param submissions The list of the submissions
     * @return The most recent submission with the highest mark achieved
     */
    public Submission getUserCurrentSubmission(ArrayList<Submission> submissions) {

        Submission keptSubmission = submissions.get(0);
        for (int i = 0; i < submissions.size(); i++) {

            if (submissions.get(i).getMarks() > keptSubmission.getMarks()) {
                keptSubmission = submissions.get(i);
            }

        }
        return keptSubmission;
    }

    /**
     * A method that gets final submissions which will be recorded as the final results of an assigment
     *
     * @param assignment The targeted assignment
     * @return The final submissions
     */
    public ArrayList<Submission> getFinalSubmissions(Assignment assignment) {

        ArrayList<User> users = new ArrayList<>();
        List<Submission> allSubmissions = getSubmissionsForAssignment(assignment);
        ArrayList<Submission> finalSubmissions = new ArrayList<>();

        //going over all submissions to figure out which user that attempted the assignment
        for (int i = 0; i < allSubmissions.size(); i++) {

            User user = allSubmissions.get(i).getUser();

            if (!users.contains(user)) {
                users.add(user);

            }
        }

        //going over the users and getting the kept submission of each user
        for (int i = 0; i < users.size(); i++) {

            finalSubmissions.add(getUserCurrentSubmission(
                    getStudentSubmissionsForAssignment(assignment, users.get(i))));
        }
        return finalSubmissions;

    }

    /**
     * A method that returns the number of attempts a user has on an assignment plus one
     *
     * @param assignment The targeted assignment
     * @param user       The user
     * @return The attempts number plus one
     */
    public int nextAttemptNumber(Assignment assignment, User user) {

        return getStudentSubmissionsForAssignment(assignment, user).size() + 1;
    }

    /**
     * A method to update a submission
     *
     * @param submission The submission to be updated
     */
    public void updateSubmission(Submission submission) {

        repository.save(submission);
    }
}
