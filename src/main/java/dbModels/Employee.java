package dbModels;

import javax.persistence.*;

/**
 * Created by kamil on 25.06.2017.
 */
@Entity(name = "Employees")
public class Employee implements BaseModel{
    @Id
    @GeneratedValue
    private int id;
    @Column(name="branch")
    private String branch;
    @Column(name = "name")
    private String firstName;
    @Column(name = "surname")
    private String lastName;
    @Column(name = "birth_date")
    private String birthDate;
    @Column(name = "address")
    private String address;
    @Column(name = "salary")
    private Double salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
