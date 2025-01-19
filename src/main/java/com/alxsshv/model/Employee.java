package com.alxsshv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String snils;

    public void updateFrom(Employee employeeData){
        this.name = employeeData.getName();
        this.surname = employeeData.getSurname();
        this.patronymic =employeeData.getPatronymic();
        this.snils = employeeData.getSnils();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Employee employee = (Employee) object;
        return id == employee.id && Objects.equals(name, employee.name) && Objects.equals(surname, employee.surname) && Objects.equals(patronymic, employee.patronymic) && Objects.equals(snils, employee.snils);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, snils);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", snils='" + snils + '\'' +
                '}';
    }
}
