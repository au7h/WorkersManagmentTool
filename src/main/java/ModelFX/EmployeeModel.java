package ModelFX;

import dbModels.Employee;
import dbUtils.dbConn;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.omg.CORBA.portable.ApplicationException;

import java.util.List;

/**
 * Created by kamil on 02.07.2017.
 */
public class EmployeeModel {
    private ObjectProperty<EmployeeFX> employeeFXObjectProperty = new SimpleObjectProperty<>(new EmployeeFX());
    private ObservableList<EmployeeFX> employeeFXObservableList = FXCollections.observableArrayList();

    private void InitEmployeeFx(List<Employee> employees) {
        employees.forEach(employee -> {
            EmployeeFX employeeFX = new EmployeeFX();
            employeeFX.setId(employee.getId());
            employeeFX.setFirstName(employee.getFirstName());
            employeeFX.setLastName(employee.getLastName());
            employeeFX.setAddress(employee.getAddress());
            employeeFX.setBirthDate(employee.getBirthDate());
            employeeFX.setBranch(employee.getBranch());
            employee.setSalary(employee.getSalary());
            this.employeeFXObservableList.addAll(employeeFX);
        });
    }

    public void init() throws ApplicationException{
        List<Employee> employeeList = dbConn.entityManager.createQuery("FROM Employees").getResultList();
        InitEmployeeFx(employeeList);
    }

    public void searchEmployee(String query) throws ApplicationException{
        List<Employee> employeeList = dbConn.entityManager.createQuery(query).getResultList();
        InitEmployeeFx(employeeList);
    }


    public void saveEmployeeInDb(String firstName, String lastName, String birthDate,
                                 String address, String branch, Double salary){
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setBirthDate(birthDate);
        employee.setAddress(address);
        employee.setSalary(salary);
        employee.setBranch(branch);

        if(dbConn.entityManagerFactory.isOpen() && dbConn.isConnected) {
            dbConn.commitDatabase(employee);
            //dbConn.disconnectDatabase();
        } else {
            dbConn.connectToDatabase();
            dbConn.commitDatabase(employee);
            //dbConn.disconnectDatabase();
        }
    }

    public EmployeeFX getEmployeeFXObjectProperty() {
        return employeeFXObjectProperty.get();
    }

    public ObjectProperty<EmployeeFX> employeeFXObjectPropertyProperty() {
        return employeeFXObjectProperty;
    }

    public void setEmployeeFXObjectProperty(EmployeeFX employeeFXObjectProperty) {
        this.employeeFXObjectProperty.set(employeeFXObjectProperty);
    }

    public ObservableList<EmployeeFX> getEmployeeFXObservableList() {
        return employeeFXObservableList;
    }

    public void setEmployeeFXObservableList(ObservableList<EmployeeFX> employeeFXObservableList) {
        this.employeeFXObservableList = employeeFXObservableList;
    }
}
