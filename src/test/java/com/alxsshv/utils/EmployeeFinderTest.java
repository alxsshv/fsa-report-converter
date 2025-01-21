package com.alxsshv.utils;

import com.alxsshv.exceptions.EmployeeNotFoundException;
import com.alxsshv.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class EmployeeFinderTest {

    @Test
    public void testFindIfMatchFound(){
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Иван");
        employee.setPatronymic("Иванович");
        employee.setSurname("Иванов");
        employee.setSnils("2222222222");
        String searchString = "Иванов Иван Иванович";
        Employee actual = EmployeeFinder.find(List.of(employee), searchString);
        assertEquals(employee, actual);
    }

    @Test
    public void testFindIfSeveralMatchFound(){
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setName("Иван");
        employee1.setPatronymic("Иванович");
        employee1.setSurname("Иванов");
        employee1.setSnils("2222222222");
        Employee employee2 = new Employee();
        employee2.setId(2);
        employee2.setName("Иван");
        employee2.setPatronymic("Иванович");
        employee2.setSurname("Петров");
        employee2.setSnils("33333333333");
        String searchString = "Иванов Иван Иванович";
        Employee actual = EmployeeFinder.find(List.of(employee1, employee2), searchString);
        assertEquals(employee1, actual);
    }

    @Test
    public void testFindIfMatchNotFoundByName(){
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Ивна");
        employee.setPatronymic("Иванович");
        employee.setSurname("Иванов");
        employee.setSnils("2222222222");
        String searchString = "Иванов Иван Иванович";
        assertThrows(EmployeeNotFoundException.class, ()-> EmployeeFinder.find(List.of(employee), searchString));
    }

    @Test
    public void testFindIfMatchNotFoundBySurname(){
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Иван");
        employee.setPatronymic("Ивановчи");
        employee.setSurname("Иванов");
        employee.setSnils("2222222222");
        String searchString = "Иванов Иван Иванович";
        assertThrows(EmployeeNotFoundException.class, ()-> EmployeeFinder.find(List.of(employee), searchString));
    }

    @Test
    public void testFindIfMatchNotFoundByPatronymic(){
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Иван");
        employee.setPatronymic("Иванович");
        employee.setSurname("Иванв");
        employee.setSnils("2222222222");
        String searchString = "Иванов Иван Иванович";
        assertThrows(EmployeeNotFoundException.class, ()-> EmployeeFinder.find(List.of(employee), searchString));
    }
}
