package controllers;


import ModelFX.EmployeeFX;
import ModelFX.EmployeeModel;
import dbModels.Employee;
import dbUtils.dbConn;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DoubleStringConverter;
import org.omg.CORBA.portable.ApplicationException;
import utils.dialogsUtils;

import java.util.List;


/**
 * Created by kamil on 15.06.2017.
 */
public class workerSearchController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField branchField;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox dayCombo;
    @FXML
    private ComboBox monthCombo;
    @FXML
    private ComboBox yearCombo;
    @FXML
    private TextField salaryFromField;
    @FXML
    private TextField salaryToField;
    @FXML
    private TableView<EmployeeFX> workersTableView;
    @FXML
    private TableColumn<EmployeeFX, String> nameColumn;
    @FXML
    private TableColumn<EmployeeFX, String> surnameColumn;
    @FXML
    private TableColumn<EmployeeFX, String> branchColumn;
    @FXML
    private TableColumn<EmployeeFX, String> birthColumn;
    @FXML
    private TableColumn<EmployeeFX, String> addressColumn;
    @FXML
    private TableColumn<EmployeeFX, Double> salaryColumn;

    private mainBorderController mbc;
    private EmployeeModel employeeModel;
    private String query;

    @FXML
    public void initialize(){
        //to avoid bad query format
        query = "From Employees";

        dbConn.connectToDatabase();
        for(int i=1;i<32;i++)
            dayCombo.getItems().add(i);
        for(int i=1;i<13;i++)
            monthCombo.getItems().add(i);
        for(int i=2000;i>1950;i--)
            yearCombo.getItems().add(i);
        this.employeeModel = new EmployeeModel();
        try {
            this.employeeModel.init();
        }catch(ApplicationException e){
            dialogsUtils.errorDialog(e.getMessage());
        } finally {
            dbConn.disconnectDatabase();
        }
        this.workersTableView.setItems(this.employeeModel.getEmployeeFXObservableList());
        this.nameColumn.setCellValueFactory(cellData-> cellData.getValue().firstNameProperty());
        this.surnameColumn.setCellValueFactory(cellData-> cellData.getValue().lastNameProperty());
        this.branchColumn.setCellValueFactory(cellData-> cellData.getValue().branchProperty());
        this.birthColumn.setCellValueFactory(cellData-> cellData.getValue().birthDateProperty());
        this.addressColumn.setCellValueFactory(cellData-> cellData.getValue().addressProperty());
        this.salaryColumn.setCellValueFactory(cellData-> cellData.getValue().salaryProperty().asObject());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.branchColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.birthColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        this.workersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.employeeModel.setEmployeeFXObjectPropertyEdit(newValue);
        });
    }

    @FXML
    public void backToMenu() {
        if(dbConn.isConnected) dbConn.disconnectDatabase();
        mainWindowController mwc = mbc.getMwc();
        mwc.loadMenuScreen();
    }

    @FXML
    public void search(){
        if(!dbConn.isConnected) dbConn.connectToDatabase();
        short wchichIfWorks = 0;
        query ="FROM Employees where ";
        if(nameField.getText().isEmpty()&&surnameField.getText().isEmpty()&&branchField.getText().isEmpty()
                &&dayCombo.getSelectionModel().getSelectedItem()==null&&monthCombo.getSelectionModel().getSelectedItem()==null
                &&yearCombo.getSelectionModel().getSelectedItem()==null&&addressField.getText().isEmpty()
                &&salaryFromField.getText().isEmpty()&&salaryToField.getText().isEmpty()) {
            //dialogsUtils.createErrorWindow("Wpisz cokolwiek!");
            query = "FROM Employees";
            executeQueryOnTable(query);
        }
        else {
            if (!nameField.getText().isEmpty()) {
                if(wchichIfWorks!=0){
                    query += "and name like '%"+nameField.getText()+"%'";
                }else {
                    query += "name like '%"+nameField.getText()+"%'";
                }
                wchichIfWorks = 1;
            }
            if (!surnameField.getText().isEmpty()) {
                if(wchichIfWorks!=0){
                    query += "and surname like '%"+surnameField.getText()+"%'";
                } else {
                    query += "surname like '%"+surnameField.getText()+"%'";
                }
                wchichIfWorks = 2;
            }
            if (!branchField.getText().isEmpty()) {
                if(wchichIfWorks !=0) {
                    query += " and branch like '%"+branchField.getText() + "%'";
                } else {
                    query += "branch like '%" + branchField.getText() + "%'";
                }
                wchichIfWorks = 3;
            }
            if (dayCombo.getSelectionModel().getSelectedItem() != null &&
                    monthCombo.getSelectionModel().getSelectedItem() != null &&
                    yearCombo.getSelectionModel().getSelectedItem() != null) {
                if(wchichIfWorks!=0) {
                    query += " and birth_date='"
                            + dayCombo.getSelectionModel().getSelectedItem() + "-"
                            + monthCombo.getSelectionModel().getSelectedItem() + "-"
                            + yearCombo.getSelectionModel().getSelectedItem() + "'";
                } else {
                    query += "birth_date='"
                            + dayCombo.getSelectionModel().getSelectedItem() + "-"
                            + monthCombo.getSelectionModel().getSelectedItem() + "-"
                            + yearCombo.getSelectionModel().getSelectedItem() + "'";
                }
                wchichIfWorks = 4;
            }
            if (!addressField.getText().isEmpty()) {
                if(wchichIfWorks!=0) {
                    query += " and address like '%" + addressField.getText() + "%'";
                }else {
                    query += "address like '%" + addressField.getText() + "%'";
                }
                wchichIfWorks = 5;
            }
            if (!salaryFromField.getText().isEmpty() && !salaryToField.getText().isEmpty()){
                if(wchichIfWorks!=0) {
                    query += " and salary between " + salaryFromField.getText() + " and " + salaryToField.getText();
                }else {
                    query += "salary between " + salaryFromField.getText() + " and " + salaryToField.getText();
                }
                wchichIfWorks = 6;
            }

            dialogsUtils.errorDialog(query);
            executeQueryOnTable(query);
        }
    }

    //sorting up/down order-by on name column
    @FXML
    public void sortAsc(){
        sortAscOrDesc("order by name asc");
    }

    @FXML
    public void sortDesc(){
        sortAscOrDesc("order by name desc");
    }

    private void sortAscOrDesc(String q){
        if(!dbConn.isConnected) dbConn.connectToDatabase();
        this.workersTableView.getItems().clear();
        try {
            employeeModel.searchEmployee(query+" "+q);
        } catch (ApplicationException e) {
            dialogsUtils.errorDialog(e.getMessage());
        } finally {
            if(dbConn.isConnected) dbConn.disconnectDatabase();
        }
    }

    public void setMbc(mainBorderController mbc){
        this.mbc = mbc;
    }

    private void executeQueryOnTable(String query){
        this.workersTableView.getItems().clear();
        try {
            employeeModel.searchEmployee(query);
        } catch (ApplicationException e) {
            dialogsUtils.errorDialog(e.getMessage());
        } finally {
            if(dbConn.isConnected) dbConn.disconnectDatabase();
        }
    }

    public void onEditCommitName(TableColumn.CellEditEvent<EmployeeFX, String> employeeFXStringCellEditEvent) {
        this.employeeModel.getEmployeeFXObjectPropertyEdit().setFirstName(employeeFXStringCellEditEvent.getNewValue());
        this.employeeModel.updateDatabaseFXObj();
    }

    public void onEditCommitSurname(TableColumn.CellEditEvent<EmployeeFX, String> employeeFXStringCellEditEvent) {
        this.employeeModel.getEmployeeFXObjectPropertyEdit().setLastName(employeeFXStringCellEditEvent.getNewValue());
        this.employeeModel.updateDatabaseFXObj();
    }

    public void onEditCommitPosition(TableColumn.CellEditEvent<EmployeeFX, String> employeeFXStringCellEditEvent) {
        this.employeeModel.getEmployeeFXObjectPropertyEdit().setBranch(employeeFXStringCellEditEvent.getNewValue());
        this.employeeModel.updateDatabaseFXObj();
    }

    public void onEditCommitBirthDate(TableColumn.CellEditEvent<EmployeeFX, String> employeeFXStringCellEditEvent) {
        this.employeeModel.getEmployeeFXObjectPropertyEdit().setBirthDate(employeeFXStringCellEditEvent.getNewValue());
        this.employeeModel.updateDatabaseFXObj();
    }

    public void onEditCommitAddress(TableColumn.CellEditEvent<EmployeeFX, String> employeeFXStringCellEditEvent) {
        this.employeeModel.getEmployeeFXObjectPropertyEdit().setAddress(employeeFXStringCellEditEvent.getNewValue());
        this.employeeModel.updateDatabaseFXObj();
    }

    public void onEditCommitSalary(TableColumn.CellEditEvent<EmployeeFX, Double> employeeFXDoubleCellEditEvent) {
            this.employeeModel.getEmployeeFXObjectPropertyEdit().setSalary(employeeFXDoubleCellEditEvent.getNewValue());
            this.employeeModel.updateDatabaseFXObj();
    }

    public void textFieldSearchInit(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.ENTER)
            search();
    }

    //its not useful for us
    private void generatePromptForComboBox(String newValue, String name, String whatWeWant, ComboBox<String> field){
        if(!newValue.isEmpty()) {
            if (!dbConn.isConnected) dbConn.connectToDatabase();
            field.getItems().clear();
            String queryString = "FROM Employees where "+ name +" like '%" + newValue + "%'";
            List<Employee> listOf = employeeModel.searchEmployeeAndReturnResult(queryString);

            switch(whatWeWant) {
                case "firstName":
                    for (Employee emp : listOf)
                        if(!field.getItems().contains(emp.getFirstName())) field.getItems().add(emp.getFirstName());
                    break;
                case "lastName":
                    for (Employee emp : listOf)
                        if(!field.getItems().contains(emp.getLastName())) field.getItems().add(emp.getLastName());
                    break;
                case "branch":
                    for (Employee emp : listOf)
                        if(!field.getItems().contains(emp.getBranch())) field.getItems().add(emp.getBranch());
                    break;
                case "address":
                    for (Employee emp : listOf)
                        if(!field.getItems().contains(emp.getAddress())) field.getItems().add(emp.getAddress());
                    break;
            }
            field.show();
        } else {
            field = new ComboBox<>();
            field.getItems().clear();
            field.hide();
        }
    }
}
