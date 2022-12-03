package com.example.autogradertyp.data.service;

import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    private final TestCaseRepository repository;

    public TestCaseService(TestCaseRepository repository) {
        this.repository = repository;
    }

    public List<TestCase> findAll(){
        return repository.findAll();
    }

    public TestCase add(TestCase testCase){
        return repository.save(testCase);
    }

    public TestCase update(TestCase testCase){
        return repository.save(testCase);
    }

    public void delete(TestCase testCase){
        repository.delete(testCase);
    }
}
