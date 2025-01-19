package com.alxsshv.service;

import com.alxsshv.model.Employee;
import com.alxsshv.storage.EmployeeStorage;
import com.alxsshv.utils.ServiceMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


    @Service
    @RequiredArgsConstructor
    @Validated
    public class EmployeeService {
        private final EmployeeStorage employeeStorage;


        public ResponseEntity<?> save(Employee employee) throws IOException {
            employee.setId(employee.hashCode());
            Optional<Employee> employeeOpt = employeeStorage.findBySnils(employee.getSnils());
            if (employeeOpt.isEmpty()) {
                    String errorMessage = "Поверитель с СНИЛС " + employee.getSnils() + " уже существует";
                    System.out.println(errorMessage);
                    return ResponseEntity.status(422).body(errorMessage);
                }
                employeeStorage.save(employee);
                String okMessage = "Поверитель " + employee.getName() + " " + employee.getSurname() + " успешно добавлен";
                System.out.println(okMessage);
                return ResponseEntity.ok(new ServiceMessage(okMessage));
        }


        public ResponseEntity<?> update(Employee employee) throws IOException {
            Optional<Employee> userOpt = employeeStorage.findById(employee.getId());
            if (userOpt.isEmpty()){
                String errorMessage = "Поверитель " + employee.getSurname()+ " " + employee.getName() +  " не найден";
                return ResponseEntity.status(404).body(errorMessage);
            }
            Employee employeeFromDb = userOpt.get();
            employeeFromDb.updateFrom(employee);
            employeeStorage.update(employeeFromDb);
            String okMessage ="Cведения о поверителе " + employee.getName() + " "
                    + employee.getSurname() + " обновлены";
            System.out.println(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        }


        public ResponseEntity<?>delete(int id) throws IOException {
            employeeStorage.delete(id);
            String okMessage ="Запись о поверителе успешно удалена";
            System.out.println(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        }


        public ResponseEntity<?> findById(int id) throws IOException {
            Optional<Employee> employeeOpt = employeeStorage.findById(id);
            if (employeeOpt.isPresent()) {
                Employee employee = employeeOpt.get();
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.notFound().build();
            }
        }


        public List<Employee> findAll() throws IOException {
            return employeeStorage.findALl();
        }

}
