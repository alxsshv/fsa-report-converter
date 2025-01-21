package com.alxsshv.storage;

import com.alxsshv.config.PathsConfig;
import com.alxsshv.model.Employee;
import com.alxsshv.utils.PathResolver;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class EmployeeStorage {
    @Autowired
    private PathsConfig paths;
    @Autowired
    private PathResolver pathResolver;
    private final Gson gson = new Gson();

    public Optional<Employee> findById(long id) throws IOException {
         return findALl().stream().filter(e -> e.getId() == id).findAny();
    }

    public Optional<Employee> findBySnils(String snils) throws IOException {
        return findALl().stream().filter(e -> e.getSnils().equals(snils)).findAny();
    }

    public List<Employee> findALl() throws IOException {
        pathResolver.createFilePathIfNotExist(paths.getEmployeeStoragePath());
        File storageFile = new File(paths.getEmployeeStoragePath());
        if (!storageFile.exists()){writeData("");}
        JsonReader reader = new JsonReader(new FileReader(storageFile));
        try {
            return List.of(gson.fromJson(reader, Employee[].class));
        } catch (NullPointerException ex){
            return new ArrayList<>();
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
        Optional<Employee> employeeFromDbOpt = findById(employee.getId());
        if (employeeFromDbOpt.isEmpty()){
            String errorMessage = "Информация о поверителе с id = " + employee.getId() + "не найдена";
            log.error(errorMessage);
            throw new FileNotFoundException(errorMessage);
        }
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

    public void deleteAll() throws IOException {
        FileWriter writer = new FileWriter(paths.getEmployeeStoragePath(),false);
        writer.write(gson.toJson(new ArrayList<>()));
        writer.flush();
        writer.close();
    }

}
