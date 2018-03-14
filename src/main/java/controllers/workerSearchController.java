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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.omg.CORBA.portable.ApplicationException;
import utils.fxmlUtils;


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

    @FXML
    public void initialize(){
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

    @FXML
    public void search(){
        if(!dbConn.isConnected) dbConn.connectToDatabase();
        short wchichIfWorks = 0;
        String query ="FROM Employees where ";
        if(nameField.getText().isEmpty()&&surnameField.getText().isEmpty()&&branchField.getText().isEmpty()
                &&dayCombo.getSelectionModel().getSelectedItem()==null&&monthCombo.getSelectionModel().getSelectedItem()==null
                &&yearCombo.getSelectionModel().getSelectedItem()==null&&addressField.getText().isEmpty()) createErrorWindow("Wpisz cokolwiek!");
        else {
            if (!nameField.getText().isEmpty()) {
                query += "name='" + nameField.getText() + "'";
                wchichIfWorks = 1;
            } else if (!surnameField.getText().isEmpty()) {
                query += "surname='" + surnameField.getText() + "'";
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
                query += "address='" + addressField.getText() + "'";

            this.workersTableView.getItems().clear();
            try {
                employeeModel.searchEmployee(query);
            } catch (ApplicationException e) {
                e.printStackTrace();
            } finally {
                if(dbConn.isConnected) dbConn.disconnectDatabase();
            }
        }
    }

    public void setMbc(mainBorderController mbc){
        this.mbc = mbc;
    }

    public void createErrorWindow(String msg){
        Stage stage;
        Pane pane;
        Scene scene;
        try {
            stage = new Stage();
            pane = fxmlUtils.fxmlLoader("/fxml/EmptyEditMessage.fxml");
            EmptyEditMessageController et = fxmlUtils.loaderForController.getController();
            et.setEditText(msg);
            et.setStage(stage);
            scene = new Scene(pane);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
