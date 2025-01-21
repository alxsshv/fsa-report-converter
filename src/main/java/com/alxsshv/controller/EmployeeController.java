package com.alxsshv.controller;

import com.alxsshv.model.Employee;
import com.alxsshv.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

    @RestController
    @AllArgsConstructor
    @RequestMapping("/employee")
    public class EmployeeController {
        @Autowired
        private EmployeeService employeeService;

        @GetMapping
        public List<Employee> geEmployeeList() throws IOException {
            return employeeService.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getEmployee(@PathVariable("id") int id) throws IOException {
            return employeeService.findById(id);
        }
        @PostMapping
        public ResponseEntity<?> addEmployee(@RequestBody Employee employee) throws IOException {
            return employeeService.save(employee);
        }

        @PutMapping("{id}")
        public ResponseEntity<?> editEmployee(@RequestBody Employee employee) throws IOException {
            return employeeService.update(employee);
        }

        @DeleteMapping("{id}")
        public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) throws IOException {
            return employeeService.delete(id);
        }
    }

