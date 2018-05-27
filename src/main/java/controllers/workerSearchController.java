package controllers;


import ModelFX.EmployeeFX;
import ModelFX.EmployeeModel;
import dbUtils.dbConn;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.omg.CORBA.portable.ApplicationException;
import utils.dialogsUtils;
import utils.fxmlUtils;

import static javafx.scene.input.KeyCode.T;


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
    private ComboBox dayCombo;
    @FXML
    private ComboBox monthCombo;
    @FXML
    private ComboBox yearCombo;
    @FXML
    private TextField addressField;
    @FXML
    private TextField salaryFromField;
    @FXML
    private TextField salaryToField;
    @FXML
    TableView<EmployeeFX> workersTableView;
    @FXML
    TableColumn<EmployeeFX, String> nameColumn;
    @FXML
    TableColumn<EmployeeFX, String> surnameColumn;
    @FXML
    TableColumn<EmployeeFX, String> branchColumn;
    @FXML
    TableColumn<EmployeeFX, String> birthColumn;
    @FXML
    TableColumn<EmployeeFX, String> addressColumn;
    @FXML
    TableColumn<EmployeeFX, Number> salaryColumn;

    private mainBorderController mbc;

    private EmployeeModel employeeModel;

    public String query;

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
        this.salaryColumn.setCellValueFactory(cellData-> cellData.getValue().salaryProperty());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.branchColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.birthColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

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
                if(nameField.getText().matches("[%]")){
                    if(wchichIfWorks!=0){
                        query += "and name like '%'";
                    }else {
                        query += "name like '%'";
                    }
                }else {
                    query += "name='" + nameField.getText() + "'";
                }
                wchichIfWorks = 1;
            } if (!surnameField.getText().isEmpty()) {
                if(surnameField.getText().matches("[%]")){
                    if(wchichIfWorks!=0){
                        query += " or surname like '%'";
                    } else {
                        query += "surname like '%'";
                    }
                } else {
                    query += "surname='" + surnameField.getText() + "'";
                }
                wchichIfWorks = 2;
            } else if (!branchField.getText().isEmpty()) {
                query += "branch='" + branchField.getText() + "'";
                wchichIfWorks = 3;
            } else if (dayCombo.getSelectionModel().getSelectedItem() != null &&
                    monthCombo.getSelectionModel().getSelectedItem() != null &&
                    yearCombo.getSelectionModel().getSelectedItem() != null) {
                query += "birth_date='"
                        + dayCombo.getSelectionModel().getSelectedItem() + "-"
                        + monthCombo.getSelectionModel().getSelectedItem() + "-"
                        + yearCombo.getSelectionModel().getSelectedItem() + "'";
                wchichIfWorks = 4;
            } else if (!addressField.getText().isEmpty()) {
                query += "address='" + addressField.getText() + "'";
                wchichIfWorks = 5;
            } else if (!salaryFromField.getText().isEmpty() && !salaryToField.getText().isEmpty()){
                query += "salary between " + salaryFromField.getText() + " and " + salaryToField.getText();
                wchichIfWorks = 6;
            }

            if (!nameField.getText().isEmpty() && wchichIfWorks != 1)
                query += " and name='" + nameField.getText() + "'";
            if (!surnameField.getText().isEmpty() && wchichIfWorks != 2)
                query += " and surname='" + surnameField.getText() + "'";
            if (!branchField.getText().isEmpty() && wchichIfWorks != 3)
                query += " and branch='" + branchField.getText() + "'";
            if (dayCombo.getSelectionModel().getSelectedItem() != null &&
                    monthCombo.getSelectionModel().getSelectedItem() != null &&
                    yearCombo.getSelectionModel().getSelectedItem() != null && wchichIfWorks != 4)
                query += " and birth_date='"
                        + dayCombo.getSelectionModel().getSelectedItem() + "-"
                        + monthCombo.getSelectionModel().getSelectedItem() + "-"
                        + yearCombo.getSelectionModel().getSelectedItem() + "'";
            if (!addressField.getText().isEmpty() && wchichIfWorks != 5)
                query += "and address='" + addressField.getText() + "'";
            if(!salaryFromField.getText().isEmpty() && !salaryToField.getText().isEmpty() && wchichIfWorks != 6)
                query += "and salary between " + salaryFromField.getText() + " and " + salaryToField.getText();

            executeQueryOnTable(query);
        }
    }

    //sorting up/down order-by on name column
    @FXML
    public void sortAsc(){
        if(!dbConn.isConnected) dbConn.connectToDatabase();
        this.workersTableView.getItems().clear();
        try {
            employeeModel.searchEmployee(query+" order by name asc");
        } catch (ApplicationException e) {
            dialogsUtils.errorDialog(e.getMessage());
        } finally {
            if(dbConn.isConnected) dbConn.disconnectDatabase();
        }
    }

    @FXML
    public void sortDesc(){
        if(!dbConn.isConnected) dbConn.connectToDatabase();
        this.workersTableView.getItems().clear();
        try {
            employeeModel.searchEmployee(query+" order by name desc");
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

    public void onEditCommitSalary(TableColumn.CellEditEvent<EmployeeFX, Number> employeeFXNumberCellEditEvent) {
        //this.employeeModel.getEmployeeFXObjectPropertyEdit().setSalary(employeeFXNumberCellEditEvent.getNewValue());
        //this.employeeModel.updateDatabaseFXObj();
    }
}
