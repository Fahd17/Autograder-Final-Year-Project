package com.example.autogradertyp.data.service;


import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Course;
import com.example.autogradertyp.data.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A service class that provide CRUD of the course table in the database
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 10/12/2022
 */

@Service
public class CourseService {

    @Autowired
    private final CourseRepository repository;


    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a course to the course table
     *
     * @param course The course to be added
     */
    public void add(Course course) {

        repository.save(course);
    }

    /**
     * A method that gets all courses in the database
     *
     * @return All the courses in the database
     */
    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    /**
     * A method that gets a course with a specified id
     *
     * @param courseId The id of the wanted assignment
     * @return The wanted assignment
     */
    public Course getCourseById(Long courseId) {

        List<Course> allCourses = getAllCourses();
        Course targetCourse = null;

        // going over all the courses and checking if the ids match
        for (int i = 0; i < allCourses.size(); i++) {

            if (allCourses.get(i).getId().equals(courseId)) {

                targetCourse = allCourses.get(i);
            }
        }
        return targetCourse;
    }
}
