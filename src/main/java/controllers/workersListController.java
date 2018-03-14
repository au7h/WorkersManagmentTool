package controllers;


import ModelFX.EmployeeFX;
import ModelFX.EmployeeModel;
import dbUtils.dbConn;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.omg.CORBA.portable.ApplicationException;

import java.util.Date;


/**
 * Created by kamil on 15.06.2017.
 */
public class workersListController {

    private mainBorderController mbc;
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

    private EmployeeModel employeeModel;

    @FXML
    public void initialize(){
        dbConn.connectToDatabase();
        this.employeeModel = new EmployeeModel();
        try {
            this.employeeModel.init();
        } catch (ApplicationException e) {
            e.printStackTrace();
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
    }

    @FXML
    public void backToMenu() {
        mainWindowController mwc = mbc.getMwc();
        mwc.loadMenuScreen();
    }

    public void setMbc(mainBorderController mbc){
        this.mbc = mbc;
    }
}
