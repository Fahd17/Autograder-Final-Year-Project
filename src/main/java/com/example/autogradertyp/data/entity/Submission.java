package com.example.autogradertyp.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name="assignment_id", nullable=false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Submission(int marks, int totalMarks, LocalDateTime submissionDataTime, byte[] data,
                      int attemptNumber, Assignment assignment, User user){

        this.marks = marks;
        this.totalMarks = totalMarks;
        this.submissionDataTime = submissionDataTime;
        this.data = data;
        this.assignment = assignment;
        this.user = user;
        this.attemptNumber = attemptNumber;

    }
    
    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public LocalDateTime getSubmissionDataTime() {
        return submissionDataTime;
    }

    public void setSubmissionDataTime(LocalDateTime submissionDataTime) {
        this.submissionDataTime = submissionDataTime;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
