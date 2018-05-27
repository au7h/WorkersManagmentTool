package utils;

import ModelFX.EmployeeFX;
import dbModels.Employee;
import javafx.beans.property.ObjectProperty;

/**
 * Created by kamil on 27.05.2018.
 */
public abstract class setModelColumns {
    public static void setModel(Employee employee, ObjectProperty<EmployeeFX> employeeFXObjectPropertyEdit){
        employee.setFirstName(employeeFXObjectPropertyEdit.getValue().getFirstName());
        employee.setLastName(employeeFXObjectPropertyEdit.getValue().getLastName());
        employee.setBirthDate(employeeFXObjectPropertyEdit.getValue().getBirthDate());
        employee.setAddress(employeeFXObjectPropertyEdit.getValue().getAddress());
        employee.setSalary(employeeFXObjectPropertyEdit.getValue().getSalary());
        employee.setBranch(employeeFXObjectPropertyEdit.getValue().getBranch());
    }
}
