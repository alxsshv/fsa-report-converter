package com.alxsshv.utils;

import com.alxsshv.exceptions.EmployeeNotFoundException;
import com.alxsshv.model.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeFinder {
    public static Employee find(List<Employee> employees, String fio){
        if (fio.split(" ").length < 3) {
            throw new IllegalArgumentException("ФИО поверителя имеет неверный формат");
        }
        Optional<Employee> employeeOpt = employees.stream()
                .filter(e->(e.getSurname()+" "+e.getName()+" "+e.getPatronymic()).equals(fio))
                .findAny();
        if(employeeOpt.isPresent()){
            return employeeOpt.get();
        } else throw new EmployeeNotFoundException("Ничего не знаю о поверителе " + fio + ". " +
                "Возможно данные поверителя указаны неверно. Если всё указано верно добавьте сведения о поверителе" +
                " в раздел \"Список поверителей\" настоящего ПО");
    }
}
