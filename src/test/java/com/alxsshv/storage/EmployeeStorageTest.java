package com.alxsshv.storage;

import com.alxsshv.config.PathsConfig;
import com.alxsshv.model.Employee;
import com.alxsshv.utils.PathResolver;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class EmployeeStorageTest {
    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();
    private final PathResolver pathResolver = new PathResolver();
    private final PathsConfig paths = new PathsConfig();
    private final EmployeeStorage storage = new EmployeeStorage(paths,pathResolver);
    private final Gson gson = new Gson();
    @TempDir
    private static Path tempDir;

    @BeforeEach
    public void createStorageFile() throws IOException {
        paths.setEmployeeStoragePath(tempDir + SEPARATOR + "employee.json");
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Иван");
        employee.setPatronymic("Иванов");
        employee.setSurname("Иванов");
        employee.setSnils("11111111111");
        Employee employee2 = new Employee();
        employee2.setId(2);
        employee2.setName("Петр");
        employee2.setPatronymic("Петрович");
        employee2.setSurname("Петров");
        employee2.setSnils("22222222222");
        FileWriter writer = new FileWriter(paths.getEmployeeStoragePath(),false);
        writer.write(gson.toJson(List.of(employee, employee2)));
        writer.flush();
        writer.close();
    }

    @Test
    public void testFindAll() throws IOException {
        List<Employee> employees = storage.findALl();
        Assertions.assertEquals(2,employees.size());
    }

    @Test
    public void testFindBySnils() throws IOException {
        String snils = "11111111111";
        Optional<Employee> opt = storage.findBySnils(snils);
        Assertions.assertEquals(snils,opt.get().getSnils());
    }

    @Test
    public void testFindById() throws IOException {
        int id = 1;
        Optional<Employee> opt = storage.findById(id);
        Assertions.assertEquals(id,opt.get().getId());
    }

    @Test
    public void testDelete() throws IOException {
        int id = 2;
        storage.delete(id);
        Assertions.assertEquals(1, storage.findALl().size());
    }

    @Test
    public void testSave() throws IOException {
        Employee employee3 = new Employee();
        employee3.setId(3);
        employee3.setName("Сергей");
        employee3.setPatronymic("Сергеевич");
        employee3.setSurname("Сергеев");
        employee3.setSnils("333333333333");
        storage.save(employee3);
        Assertions.assertEquals(3,storage.findALl().size());
    }

    @Test
    public void testUpdate() throws IOException {
        String expectedSnils = "44444444444";
        int id = 1;
        Optional<Employee> employeeOpt = storage.findById(id);
        Employee employee = employeeOpt.get();
        employee.setSnils(expectedSnils);
        storage.update(employee);
        Assertions.assertEquals(expectedSnils,storage.findById(id).get().getSnils());
    }

    @Test
    public void testDeleteAll() throws IOException {
        storage.deleteAll();
        Assertions.assertTrue(storage.findALl().isEmpty());
    }


}
