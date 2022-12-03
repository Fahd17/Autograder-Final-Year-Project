package com.example.autogradertyp.data.repository;

import com.example.autogradertyp.data.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AssigmentRepository extends JpaRepository<Assignment, Long> {

}
