/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breastcancerprediction.view;

import breastcancerprediction.control.Classifier;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Kim
 */
public class HealthDetailsController implements Initializable {

    @FXML
    private ChoiceBox<String> historyChoice;
    ObservableList<String> values = FXCollections.observableArrayList("Yes", "No", "Not sure");
    @FXML
    private ChoiceBox<String> alcoholChoice;
    @FXML
    private ChoiceBox<String> radiationChoice;
    @FXML
    private ChoiceBox<String> lumpsChoice;
    @FXML
    private ChoiceBox<String> dischargeChoice;
    @FXML
    private ChoiceBox<String> invertedChoice;
    @FXML
    private ChoiceBox<String> colorChoice;
    @FXML
    private ChoiceBox<String> painChoice;
    @FXML
    private ChoiceBox<String> rashChoice;
    @FXML
    private TextField pID;

    Classifier handler;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = Classifier.getInstance();
        setValues();
    }

    @FXML
    public void insertHealthDeatails() {

        String query = "INSERT INTO patientData (pID,familyHistory,alcohol,radiation,breastLump,invertedNipples,colorChange,nipplePain,rash,discharge,status) VALUES("
                + "'" + pID.getText() + "',"
                + "'" + historyChoice.getValue() + "',"
                + "'" + alcoholChoice.getValue() + "',"
                + "'" + radiationChoice.getValue() + "',"
                + "'" + lumpsChoice.getValue() + "',"
                + "'" + invertedChoice.getValue() + "',"
                + "'" + colorChoice.getValue() + "',"
                + "'" + painChoice.getValue() + "',"
                + "'" + rashChoice.getValue() + "',"
                + "'" + dischargeChoice.getValue() + "',"
                + "'" + "unknown"+ "'"
                + ")";
        if (handler.execAction(query)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
            reset();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }

    public void setValues() {
        historyChoice.getItems().addAll(values);
        alcoholChoice.getItems().addAll("Yes", "No");
        radiationChoice.getItems().addAll(values);
        lumpsChoice.getItems().addAll("Yes", "No");
        dischargeChoice.getItems().addAll("Yes", "No");
        invertedChoice.getItems().addAll("Yes", "No");
        colorChoice.getItems().addAll("Yes", "No");
        painChoice.getItems().addAll("Yes", "No");
        rashChoice.getItems().addAll("Yes", "No");
        
        reset();
    }
    
    public void reset(){
        historyChoice.setValue("No");
        alcoholChoice.setValue("No");
        radiationChoice.setValue("No");
        lumpsChoice.setValue("No");
        dischargeChoice.setValue("No");
        invertedChoice.setValue("No");
        colorChoice.setValue("No");
        painChoice.setValue("No");
        rashChoice.setValue("No");
    }
}
