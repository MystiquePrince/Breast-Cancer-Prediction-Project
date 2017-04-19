package breastcancerprediction.view;

import breastcancerprediction.control.Classifier;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Kim
 */
public class AddPatientController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField fnameField;
    @FXML
    private TextField sNameField;
    @FXML
    private TextField natIDField;
    @FXML
    private DatePicker dobField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField heightField;

    @FXML
    private TextField addressField;
    Classifier handler;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = Classifier.getInstance();
    }

    @FXML
    private void closePersonDetails(ActionEvent event) {
        pane.setVisible(false);
    }

    @FXML
    public void insertToPatientDB() {

        if (fnameField.getText().isEmpty() || sNameField.getText().isEmpty() || natIDField.getText().isEmpty() || dobField.getValue() == null
                || weightField.getText().isEmpty() || heightField.getText().isEmpty() || addressField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return;
        }

        LocalDate localDate = dobField.getValue();
        Date date = Date.valueOf(localDate);

        String query = "INSERT INTO patients (nationalId, firstName,surname,dob,weight,height,address) VALUES("
                + "" + Integer.parseInt(natIDField.getText()) + ","
                + "'" + fnameField.getText() + "',"
                + "'" + sNameField.getText() + "',"
                + "'" + date + "',"
                + "" + Double.parseDouble(weightField.getText()) + ","
                + "" + Double.parseDouble(heightField.getText()) + ","
                + "'" + addressField.getText() + "'"
                + ")";
        if (handler.execAction(query)) {
            try {
                ResultSet rs=handler.execQuery("SELECT * FROM PATIENTS ORDER BY patientid desc limit 1");
                String id="";
                if (rs.next()) {
                    id = rs.getString(1);
                }
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Success\n Your Patient ID is:"+ id);
                alert.showAndWait();
                clearInputs();
            } catch (SQLException ex) {
                Logger.getLogger(AddPatientController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }

    }

    private void clearInputs() {
      fnameField.setText("");
      sNameField.setText("");
      natIDField.setText("");
      dobField.setValue(null);
      weightField.setText("");
      heightField.setText("");
      addressField.setText("");
    }


}
