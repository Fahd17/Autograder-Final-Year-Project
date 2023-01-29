package com.example.autogradertyp.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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


    public Assignment (String assignmentName, String courseID, User author, LocalDateTime deadline){

        this.assignmentName = assignmentName;
        this.courseID = courseID;
        this.author = author;
        this.deadline = deadline;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getAssignmentName() {
        return assignmentName;
    }


    public String getCourseID() {
        return courseID;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
