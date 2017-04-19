/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breastcancerprediction.view;

import breastcancerprediction.control.Classifier;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Kim
 */
public class EvaluatePatientController implements Initializable {

    @FXML
    private TextField patientIDField;
    @FXML
    private ListView<String> patientDetailsList;
    @FXML
    private JFXTextField healthCondition;
    @FXML
    private JFXTextField cancerSatus;
    
    private static Connection conn=null;
    private static Statement stmt=null;
    private static ResultSet rs=null;
    Classifier handler;
    Classifier cs=new Classifier();
    
       String fname, sname, address, familyHistory, alcohol, radiation, breastLump, invertedNipples, colorChange, nipplePain, rash, nippleDischarge, status;
       double age=0;
       double bmi=0;
       int nationalID=0;
    @FXML
    private JFXTextField recommendationField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler=Classifier.getInstance();
    }   

    @FXML
    private void cancel(ActionEvent event) {
    }

    @FXML
    private void printReport(ActionEvent event) {
    }

    @FXML
    private void handleExit(ActionEvent event) {
    }

    @FXML
    private void loadPatientData(ActionEvent event) {
        ObservableList<String> pData=FXCollections.observableArrayList();
         
          try {
         String query;
         query="SELECT * FROM patientData where pID = '" + patientIDField.getText() + "'";
         rs=handler.execQuery(query);
         while (rs.next()) {
         familyHistory = rs.getString("familyHistory");
         alcohol = rs.getString("alcohol");
         radiation = rs.getString("radiation");
         breastLump= rs.getString("breastLump");
         invertedNipples = rs.getString("invertedNipples");
         colorChange = rs.getString("colorChange");
         nipplePain = rs.getString("nipplePain");
         rash = rs.getString("rash");
         nippleDischarge = rs.getString("discharge");
         status = rs.getString("status");
         }
         query="SELECT * FROM patients where patientID = '" + patientIDField.getText() + "'";
         rs=handler.execQuery(query);
         while (rs.next()) {
         fname=rs.getString("firstName");
         sname=rs.getString("surname");
         address=rs.getString("address");
         nationalID=rs.getInt("nationalID");
         }
         query="Select year(current_timestamp)-year(dob) as age, weight/(height*height) as bmi from patients where patientID = '" + patientIDField.getText() + "'";
         rs=handler.execQuery(query);
         while (rs.next()) {
         age=rs.getDouble("age");
         bmi=rs.getDouble("bmi");
         }
         
         } catch (SQLException ex) {
         Logger.getLogger(EvaluatePatientController.class.getName()).log(Level.SEVERE, null, ex);
         }
         pData.add("PATIENT DETAILS: ");
         pData.add("        National ID: "+nationalID);
         pData.add("        First Name: "+fname);
         pData.add("        Surname : "+sname);
         pData.add("        Age: "+age);
         pData.add("        BMI: "+bmi);
         pData.add("        Address: "+address);
         pData.add("HEALTH DETAILS");
         pData.add("        Family History: "+familyHistory);
         pData.add("        Aclohol: "+alcohol);
         pData.add("        Radiation: "+radiation);
         pData.add("        Breast Lumps: "+breastLump);
         pData.add("        Inverted Nipples: "+invertedNipples);
         pData.add("        Color change around breast: "+colorChange);
         pData.add("        Breast/Nipple pain: "+nipplePain);
         pData.add("        Rash around breast area: "+rash);
         pData.add("        Nipple discharge: "+nippleDischarge);
         pData.add("        Status: "+status);
       
        patientDetailsList.getItems().setAll(pData);
    
    }

    @FXML
    private void evaluate(ActionEvent event) {
   cs.classifyPatient(age, bmi, familyHistory, alcohol, radiation, breastLump, invertedNipples, colorChange, nipplePain, rash, nippleDischarge);      
    double probYes=cs.getPYes();
    double probNo=cs.getPNo();
        if (probYes>probNo) {
            healthCondition.setText("You have breast cancer");
            cancerSatus.setText("Risk of developing breast cancer: \n"+probYes +"%");
            recommendationField.setText("No Breast Cancer Risk: \n" +probNo+"%");
        }else{
            healthCondition.setText("No breast cancer");
            cancerSatus.setText("Risk of developing breast cancer: "+probYes +"%");
            recommendationField.setText("No Breast Cancer Risk: " +probNo+"%");
        }
    }
}
