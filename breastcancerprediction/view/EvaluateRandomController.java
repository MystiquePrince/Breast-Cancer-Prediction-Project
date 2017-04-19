/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breastcancerprediction.view;

import breastcancerprediction.control.Classifier;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Kim
 */
public class EvaluateRandomController implements Initializable {

    @FXML
    private JFXTextField fnameField;
    @FXML
    private JFXTextField lnameField;
    @FXML
    private JFXTextField weightField;
    @FXML
    private JFXTextField heightField;
    @FXML
    private ChoiceBox<String> historyChoice;
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
    private Button cancelButton;
    @FXML
    private JFXDatePicker dobPicker;
    
    Classifier cs=new Classifier();
    String fname, lname, familyHistory, alcohol, radiation, breastLump, invertedNipples, colorChange, nipplePain, rash, nippleDischarge, status;
       double age=0;
       double bmi=0;
    @FXML
    private Label healthLabel;
    @FXML
    private Label cancerLabel;
    @FXML
    private Label recLabel;
    ObservableList<String> values=FXCollections.observableArrayList("Yes", "No", "Not sure");
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       historyChoice.setValue("No");
        historyChoice.getItems().addAll(values);
        alcoholChoice.setValue("No");
        alcoholChoice.getItems().addAll("Yes", "No");
        radiationChoice.setValue("No");
        radiationChoice.getItems().addAll(values);
        lumpsChoice.setValue("No");
        lumpsChoice.getItems().addAll("Yes", "No");
        dischargeChoice.setValue("No");
        dischargeChoice.getItems().addAll("Yes", "No");
        invertedChoice.setValue("No");
        invertedChoice.getItems().addAll("Yes", "No");
        colorChoice.setValue("No");
        colorChoice.getItems().addAll("Yes", "No");
        painChoice.setValue("No");
        painChoice.getItems().addAll("Yes", "No");
        rashChoice.setValue("No");
        rashChoice.getItems().addAll("Yes", "No");
    }    

    @FXML
    private void evaluatePatient(ActionEvent event) {
        fname=fnameField.getText();
        lname=lnameField.getText();
        bmi=(Double.parseDouble(weightField.getText()))*(Math.pow(Double.parseDouble(heightField.getText()),2));
        familyHistory=historyChoice.getValue();
        alcohol=alcoholChoice.getValue();
        radiation=radiationChoice.getValue();
        breastLump=lumpsChoice.getValue();
        invertedNipples=invertedChoice.getValue();
        colorChange=colorChoice.getValue();
        nippleDischarge=dischargeChoice.getValue();
        nipplePain=painChoice.getValue();
        rash=rashChoice.getValue();
        age=Period.between(dobPicker.getValue(), LocalDate.now()).getYears();
        
    cs.classifyPatient(age, bmi, familyHistory, alcohol, radiation, breastLump, invertedNipples, colorChange, nipplePain, rash, nippleDischarge);      
    double probYes=cs.getPYes();
    double probNo=cs.getPNo();
        if (probYes>probNo) {
            healthLabel.setText("You have breast cancer");
            cancerLabel.setText("Risk of developing breast cancer: \n"+probYes +"%");
            recLabel.setText("No Breast Cancer Risk: \n" +probNo+"%");
        }else{
            healthLabel.setText("No breast cancer");
            cancerLabel.setText("Risk of developing breast cancer: "+probYes +"%");
            recLabel.setText("No Breast Cancer Risk: " +probNo+"%");
        }
    
    }
    @FXML
    private void cancel(ActionEvent event) {
    }
    
}
