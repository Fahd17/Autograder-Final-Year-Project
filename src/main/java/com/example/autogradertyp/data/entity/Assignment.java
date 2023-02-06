package com.example.autogradertyp.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class represent the assignment table in the database.
 *
 * @author Fahd Alsahali
 * @date 10/12/2022
 * @version 3.0
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
    private String courseID;

    private LocalDateTime deadline;

    @OneToMany(mappedBy="assignment")
    private List<TestCase> testCases;

    @OneToMany(mappedBy="assignment")
    private List<Submission> Submissions;
    @ManyToOne
    @JoinColumn(name="author_id", nullable=false)
    private User author;


    /**
     * Creates a new assignment
     *
     * @param assignmentName The name of the assignment
     * @param courseID The course id of which the assignment belong to
     * @param author The user who created the assigment
     * @param deadline The deadline of the assigment
     */
    public Assignment (String assignmentName, String courseID, User author, LocalDateTime deadline){

        this.assignmentName = assignmentName;
        this.courseID = courseID;
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
    public String getCourseID() {
        return courseID;
    }

    /**
     * A method to set the course id
     *
     * @param courseID The id of the course
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
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
}
