package com.example.autogradertyp.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represent the submission table in the database.
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 01/02/2023
 */

@Data
@NoArgsConstructor
@Entity
@Table
public class Submission {

    @Id
    @GeneratedValue
    private Long id;

    private int marks;

    private int totalMarks;

    private LocalDateTime submissionDataTime;

    @Lob
    private byte[] data;

    private int attemptNumber;

    private String checkUploadId;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Creates a new submission for an assigment
     *
     * @param marks              The marks the submission achieved
     * @param totalMarks         The total marks available
     * @param submissionDataTime The time and date of submission
     * @param data               The file submitted
     * @param attemptNumber      The index of the attempts the user had on an assigment
     * @param assignment         The assigment the submission was submitted to
     * @param user               The user that submitted
     */
    public Submission(int marks, int totalMarks, LocalDateTime submissionDataTime, byte[] data, int attemptNumber, Assignment assignment, User user) {

        this.marks = marks;
        this.totalMarks = totalMarks;
        this.submissionDataTime = submissionDataTime;
        this.data = data;
        this.assignment = assignment;
        this.user = user;
        this.attemptNumber = attemptNumber;

    }

    /**
     * A method that gets the marks achieve in the assignment
     *
     * @return The mark
     */
    public int getMarks() {
        return marks;
    }

    /**
     * A method that sets the marks achieved in the assigment
     *
     * @param marks
     */
    public void setMarks(int marks) {
        this.marks = marks;
    }

    /**
     * A method to get the total mark
     *
     * @return The total mark
     */
    public int getTotalMarks() {
        return totalMarks;
    }

    /**
     * A method to set the total mark
     *
     * @param totalMarks The total mark
     */
    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    /**
     * A method that gets the submission time and date
     *
     * @return The submission time and date
     */
    public LocalDateTime getSubmissionDataTime() {

        return submissionDataTime;
    }

    /**
     * A method that sets the submission time and date
     *
     * @param submissionDataTime The submission time and date
     */
    public void setSubmissionDataTime(LocalDateTime submissionDataTime) {
        this.submissionDataTime = submissionDataTime;
    }

    /**
     * A method that gets the submission time and date formatted
     *
     * @return The submission time and date formatted
     */
    public String getSubmissionDataTimeFormatted() {

        DateTimeFormatter formatSubmissionTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return getSubmissionDataTime().format(formatSubmissionTime);
    }

    /**
     * A method that gets the assigment the submission belong to
     *
     * @return The assignment
     */
    public Assignment getAssignment() {
        return assignment;
    }

    /**
     * A method to set the assigment the submission belong to
     *
     * @param assignment The assigment
     */
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    /**
     * A method that gets the user which submitted the submission
     *
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * A method that sets the user which submitted the submission
     *
     * @param user The submission
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * A method that gets the file that was submitted as a part of the submission
     *
     * @return The file as bytes
     */
    public byte[] getData() {
        return data;
    }

    /**
     * A method that sets the file that was submitted as a part of the submission
     *
     * @param data The file as bytes
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * A method that returns the submission details in a format that can be written in a CSV
     *
     * @return Details of the submission
     */
    public String[] ToStringCSV() {

        String[] record = {user.getStudentNumber(), getMarks() + "", totalMarks + "", user.getEmail()};

        return record;
    }

    /**
     * Gets the check upload id
     *
     * @return The check upload id
     */
    public String getCheckUploadId() {
        return checkUploadId;
    }

    /**
     * Sets the check upload id
     *
     * @param checkUploadId the check upload id
     */
    public void setCheckUploadId(String checkUploadId) {
        this.checkUploadId = checkUploadId;
    }
}
