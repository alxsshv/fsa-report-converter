package com.alxsshv.utils.fsa.factory;

import com.alxsshv.model.Employee;
import com.alxsshv.utils.fsa.entity.ApprovedEmployee;
import com.alxsshv.utils.fsa.entity.EmployeeName;

public class ApprovedEmployeeFactory {
    public static ApprovedEmployee createApprovedEmployee(Employee employee){
        ApprovedEmployee approvedEmployee = new ApprovedEmployee();
        EmployeeName employeeName = new EmployeeName();
        employeeName.setName(employee.getName());
        employeeName.setSurname(employee.getSurname());
        employeeName.setPatronymic(employee.getPatronymic());
        approvedEmployee.setEmployeeName(employeeName);
        approvedEmployee.setSnils(employee.getSnils());
        return approvedEmployee;
    }
    
}
