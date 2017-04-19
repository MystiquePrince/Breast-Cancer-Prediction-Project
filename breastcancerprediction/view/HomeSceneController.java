/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breastcancerprediction.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kim
 */
public class HomeSceneController implements Initializable {

     Stage stage=null;
     Parent root=null;
     
    @FXML
    private Button addPatientButton;
    
    @FXML
    private BorderPane mainRootPane;
    @FXML
    private ImageView homePane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void loadNewPatientWindow(ActionEvent event) {
         try {
             stage=(Stage)addPatientButton.getScene().getWindow();
             root=FXMLLoader.load(getClass().getResource("/breastcancerprediction/view/AddPatient.fxml"));
            mainRootPane.setCenter(root);
             
         } catch (IOException ex) {
             Logger.getLogger(HomeSceneController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void loadEvaluationWindow(ActionEvent event) {
        try {
             stage=(Stage)addPatientButton.getScene().getWindow();
             root=FXMLLoader.load(getClass().getResource("/breastcancerprediction/view/EvaluatePatient.fxml"));
            mainRootPane.setCenter(root);
             
         } catch (IOException ex) {
             Logger.getLogger(HomeSceneController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void loadHealthEntry(ActionEvent event) {
        try {
             stage=(Stage)addPatientButton.getScene().getWindow();
             root=FXMLLoader.load(getClass().getResource("/breastcancerprediction/view/HealthDetails.fxml"));
             mainRootPane.setCenter(root);
             
         } catch (IOException ex) {
             Logger.getLogger(HomeSceneController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    private void loadHomePage(ActionEvent event) {
        try {
             stage=(Stage)addPatientButton.getScene().getWindow();
            mainRootPane.setCenter(homePane);
             
         } catch (Exception ex) {
             Logger.getLogger(HomeSceneController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void loadRandomEvaluation(ActionEvent event) {
         try {
             stage=(Stage)addPatientButton.getScene().getWindow();
             root=FXMLLoader.load(getClass().getResource("/breastcancerprediction/view/EvaluateRandom.fxml"));
            mainRootPane.setCenter(root);
             
         } catch (IOException ex) {
             Logger.getLogger(HomeSceneController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
}
