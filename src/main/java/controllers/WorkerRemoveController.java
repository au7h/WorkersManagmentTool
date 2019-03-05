package controllers;


import ModelFX.EmployeeFX;
import ModelFX.EmployeeModel;
import dbUtils.DbConn;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import utils.DialogsUtils;

/**
 * Created by kamil on 15.06.2017.
 */
public class WorkerRemoveController {

    private MainBorderController mbc;

    @FXML
    TableView<EmployeeFX> workersTableView;
    @FXML
    TableColumn<EmployeeFX, String> workerIdColumn;
    @FXML
    TableColumn<EmployeeFX, String> workerNameColumn;
    @FXML
    TableColumn<EmployeeFX, String> workerSurnameColumn;

    private EmployeeModel employeeModel;

    @FXML
    public void initialize(){
        DbConn.connectToDatabase();
        employeeModel = new EmployeeModel();
        try{
            employeeModel.init();
        } catch (Exception ex){
            DialogsUtils.errorDialog(ex.getMessage());
        } finally {
            DbConn.disconnectDatabase();
        }

        workersTableView.setItems(employeeModel.getEmployeeFXObservableList());
        workerIdColumn.setCellValueFactory(cell -> cell.getValue().idProperty().asString());
        workerNameColumn.setCellValueFactory(cell -> cell.getValue().firstNameProperty());
        workerSurnameColumn.setCellValueFactory(cell -> cell.getValue().lastNameProperty());
        workerIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        workerNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        workerSurnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        workersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.employeeModel.setEmployeeFXObjectPropertyEdit(newValue);
    });
    }

    @FXML
    public void backToMenu() {
        MainWindowController mwc = mbc.getMwc();
        mwc.loadMenuScreen();
    }

    @FXML
    public void deleteWorker(){
        EmployeeFX efx = this.employeeModel.getEmployeeFXObjectProperty();
        employeeModel.deleteEmployee(efx.getId());
    }

    public void setMbc(MainBorderController mbc){
        this.mbc = mbc;
    }
}
