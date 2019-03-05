package controllers;


import ModelFX.EmployeeModel;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dbUtils.DbConn;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.FxmlUtils;

/**
 * Created by kamil on 15.06.2017.
 */
public class WorkerAddController {

    //Main border controller handle
    private MainBorderController mbc;

    @FXML
    JFXTextField firstNameEdit;
    @FXML
    JFXTextField lastNameEdit;
    @FXML
    JFXComboBox<Integer> dayCombo;
    @FXML
    JFXComboBox<Integer> monthCombo;
    @FXML
    JFXComboBox<Integer> yearCombo;
    @FXML
    JFXTextField addressEdit;
    @FXML
    JFXTextField branchEdit;
    @FXML
    JFXTextField salaryEdit;

    //initialize comboboxes which contain birth date of employee
    @FXML
    public void initialize(){
        for(int i=1;i<32;i++)
        dayCombo.getItems().add(i);
        for(int i=1;i<13;i++)
            monthCombo.getItems().add(i);
        for(int i=2000;i>1950;i--)
            yearCombo.getItems().add(i);
    }

    //back to main menu
    @FXML
    public void backToMenu() {
        MainWindowController mwc = mbc.getMwc();
        mwc.loadMenuScreen();
    }

    //setting Main Border Controller
    public void setMbc(MainBorderController mbc){
        this.mbc = mbc;
    }

    //add employee to database and clear form
    @FXML
    public void addEmployee() {
        if (firstNameEdit.getText().isEmpty())
            createErrorWindow("Pole imie jest puste");
        else if (lastNameEdit.getText().isEmpty())
            createErrorWindow("Pole nazwisko jest puste");
        else if (branchEdit.getText().isEmpty())
            createErrorWindow("Pole stanowisko jest puste");
        else if (addressEdit.getText().isEmpty())
            createErrorWindow("Pole adres jest puste");
        else if (salaryEdit.getText().isEmpty())
            createErrorWindow("Pole wynagrodzenie jest puste");
        else if(dayCombo.getSelectionModel().getSelectedItem() == null)
            createErrorWindow("Dzien urodzenia jest pusty");
        else if(monthCombo.getSelectionModel().getSelectedItem() == null)
            createErrorWindow("Miesiac urodzenia jest pusty");
        else if(yearCombo.getSelectionModel().getSelectedItem() == null)
            createErrorWindow("Rok urodzenia jest pusty");
        else if(!salaryEdit.getText().matches("^\\d*$"))
            createErrorWindow("Pole wynagrodzenie musi byc liczba"); //to write
        else {
            DbConn.connectToDatabase();
            EmployeeModel employeeToSave = new EmployeeModel();
            String birthDateTemp = Integer.toString(dayCombo.getValue()) + "-" + Integer.toString(monthCombo.getValue()) + "-" + Integer.toString(yearCombo.getValue());
            employeeToSave.saveEmployeeInDb(firstNameEdit.getText(), lastNameEdit.getText(),
                    birthDateTemp, addressEdit.getText(), branchEdit.getText(), Double.parseDouble(salaryEdit.getText()));
            clearForm();
            DbConn.disconnectDatabase();
        }
    }
    //clear form
    @FXML
    public void clearForm(){
        firstNameEdit.clear();
        lastNameEdit.clear();
        dayCombo.setValue(null);
        monthCombo.setValue(null);
        yearCombo.setValue(null);
        addressEdit.clear();
        branchEdit.clear();
        salaryEdit.clear();
    }

    public void createErrorWindow(String msg){
        Stage stage;
        Pane pane;
        Scene scene;
        try {
            stage = new Stage();
            pane = FxmlUtils.fxmlLoader("/fxml/EmptyEditMessage.fxml");
            EmptyEditMessageController et = FxmlUtils.loaderForController.getController();
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
