package com.example.autogradertyp.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class represent the assignment table in the database.
 *
 * @author Fahd Alsahali
 * @version 3.0
 * @date 10/12/2022
 * @since 01/02/2023
 */

@Data
@NoArgsConstructor
@Entity
@Table
public class Assignment {

    @Id
    @GeneratedValue
    private Long id;

    private String assignmentName;

    private LocalDateTime deadline;

    @Column(nullable = true)
    private byte[] assignmentResultsCSV;

    @OneToMany(mappedBy = "assignment")
    private List<TestCase> testCases;

    @OneToMany(mappedBy = "assignment")
    private List<Submission> Submissions;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


    /**
     * Creates a new assignment
     *
     * @param assignmentName The name of the assignment
     * @param course         The course of which the assignment belong to
     * @param author         The user who created the assigment
     * @param deadline       The deadline of the assigment
     */
    public Assignment(String assignmentName, Course course, User author, LocalDateTime deadline) {

        this.assignmentName = assignmentName;
        this.course = course;
        this.author = author;
        this.deadline = deadline;
    }


    /**
     * A method to get the name of the assignment
     *
     * @return The assignment name
     */
    public String getAssignmentName() {
        return assignmentName;
    }

    /**
     * A method to set the name of the assignment
     *
     * @param assignmentName The name of the assignment
     */
    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    /**
     * A method that gets the course id
     *
     * @return The course id
     */
    public Course getCourse() {
        return course;
    }

    /**
     * A method to set the course id
     *
     * @param course The id of the course
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * A method to get the author
     *
     * @return The author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * A method to se the author
     *
     * @param author The author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * A method to get the deadline
     *
     * @return The deadline
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * A method to set the deadline
     *
     * @param deadline The deadline
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * A method to get the CSV file of the assignment results
     *
     * @return The bytes of the CSV file
     */
    public byte[] getAssignmentResultsCSV() {
        return assignmentResultsCSV;
    }

    /**
     * A method to set the CSV of the assignment results
     *
     * @param assignmentResultsCSV The bytes of the CSV file
     */
    public void setAssignmentResultsCSV(byte[] assignmentResultsCSV) {
        this.assignmentResultsCSV = assignmentResultsCSV;
    }
}
