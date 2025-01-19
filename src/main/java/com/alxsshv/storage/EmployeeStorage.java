package com.alxsshv.storage;

import com.alxsshv.config.PathsConfig;
import com.alxsshv.model.Employee;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeStorage {
    @Autowired
    private PathsConfig paths;

    private final Gson gson = new Gson();

    public Optional<Employee> findById(long id) throws IOException {
         return findALl().stream().filter(e -> e.getId() == id).findAny();
    }

    public Optional<Employee> findBySnils(String snils){
        return Optional.of(new Employee());
    }

    public List<Employee> findALl() throws IOException {
        checkIfFileExsist();
        JsonReader reader = new JsonReader(new FileReader(paths.getEmployeeStoragePath()));
        try {
            return List.of(gson.fromJson(reader, Employee[].class));
        } catch (NullPointerException ex){
            return new ArrayList<>();
        }
    }

    private void checkIfFileExsist() throws IOException {
        File file = new File(paths.getEmployeeStoragePath());
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public void delete(long id) throws IOException {
       List<Employee> employees = findALl().stream().filter(e -> e.getId() != id).toList();
       String employeeData = gson.toJson(employees);
       writeData(employeeData);
    }

    public void save(Employee employee) throws IOException {
        List<Employee> employees = new ArrayList<>(findALl());
        employees.add(employee);
        String employeeData = gson.toJson(employees);
        writeData(employeeData);
    }

    public void update(Employee employee) throws IOException {
        List<Employee> employees = new ArrayList<>(findALl().stream().filter(e -> e.getId() != employee.getId()).toList());
        employees.add(employee);
        String employeeData = gson.toJson(employees);
        writeData(employeeData);
    }

    private void writeData(String employeeData) throws IOException {
        FileWriter writer = new FileWriter(paths.getEmployeeStoragePath(),false);
        writer.write(employeeData);
        writer.flush();
        writer.close();
    }

}
