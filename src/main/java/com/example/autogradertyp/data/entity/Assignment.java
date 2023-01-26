package com.example.autogradertyp.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String assignmentID;
    private String courseID;

    @OneToMany(mappedBy="assignment")
    private List<TestCase> testCases;



    public Assignment (String assignmentName, String assignmentID, String courseID){

        this.assignmentName = assignmentName;
        this.assignmentID = assignmentID;
        this.courseID = courseID;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public void setAssignmentID(String assignmentID) {
        this.assignmentID = assignmentID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getAssignmentID() {
        return assignmentID;
    }

    public String getCourseID() {
        return courseID;
    }


    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", assignmentName='" + assignmentName + '\'' +
                ", assignmentID='" + assignmentID + '\'' +
                ", courseID='" + courseID +
                '}';
    }
}
