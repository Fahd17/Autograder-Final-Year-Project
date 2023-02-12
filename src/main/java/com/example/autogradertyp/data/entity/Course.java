package com.example.autogradertyp.data.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * This class represent the course table in the database.
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 07/02/2023
 */

@Data
@NoArgsConstructor
@Entity
@Table
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String code;

    @OneToMany(mappedBy = "course")
    private List<Assignment> assignments;

    public Course(String name, String code){

         this.name = name;
         this.code = code;
    }

    /**
     * Gets the id of the course
     *
     * @return The course id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the name of the course
     *
     * @return The name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course
     *
     * @param name The name of the course
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of the course
     *
     * @return The code of the course
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the course
     *
     * @param code The code of the course
     */
    public void setCode(String code) {
        this.code = code;
    }

}
