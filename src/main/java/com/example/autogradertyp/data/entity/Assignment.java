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
    private String courseID;

    @OneToMany(mappedBy="assignment")
    private List<TestCase> testCases;

    @OneToMany(mappedBy="assignment")
    private List<Submission> Submissions;


    public Assignment (String assignmentName, String courseID){

        this.assignmentName = assignmentName;
        this.courseID = courseID;
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

}
