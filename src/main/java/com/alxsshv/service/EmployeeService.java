package com.alxsshv.service;

import com.alxsshv.exceptions.EmployeeNotFoundException;
import com.alxsshv.model.Employee;
import com.alxsshv.storage.EmployeeStorage;
import com.alxsshv.utils.ServiceMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


    @Service
    @RequiredArgsConstructor
    @Validated
    @Slf4j
    public class EmployeeService {
        private final EmployeeStorage employeeStorage;


        public ResponseEntity<?> save(@Valid Employee employee) throws IOException {
            employee.setId(employee.hashCode());
            Optional<Employee> employeeOpt = employeeStorage.findBySnils(employee.getSnils());
            if (employeeOpt.isPresent()) {
                    String errorMessage = "Поверитель с СНИЛС " + employee.getSnils() + " уже существует";
                    log.error(errorMessage);
                    return ResponseEntity.status(422).body(new ServiceMessage(errorMessage));
                }
                employeeStorage.save(employee);
                String okMessage = "Поверитель " + employee.getName() + " " + employee.getSurname() + " успешно добавлен";
                log.info(okMessage);
                return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
        }


        public ResponseEntity<?> update(@Valid Employee employee) throws IOException {
            Optional<Employee> employeeOpt = employeeStorage.findById(employee.getId());
            if (employeeOpt.isEmpty() || employee.getId() == 0){
                String errorMessage = " Информация о поверителе " + employee.getSurname()+ " " + employee.getName() +  " не найдена";
                log.error(errorMessage);
                throw new EmployeeNotFoundException(errorMessage);
            }
            Employee employeeFromDb = employeeOpt.get();
            employeeFromDb.updateFrom(employee);
            employeeStorage.update(employeeFromDb);
            String okMessage ="Cведения о поверителе " + employee.getName() + " "
                    + employee.getSurname() + " обновлены";
            log.info(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        }


        public ResponseEntity<?>delete(int id) throws IOException {
            employeeStorage.delete(id);
            String okMessage ="Запись о поверителе успешно удалена";
            log.info(okMessage);
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
