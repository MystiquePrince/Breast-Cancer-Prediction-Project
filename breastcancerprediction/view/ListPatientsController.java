/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breastcancerprediction.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author Kim
 */
public class ListPatientsController implements Initializable {

    @FXML
    private TableColumn<?, ?> pIDCol;
    @FXML
    private TableColumn<?, ?> natIDCol;
    @FXML
    private TableColumn<?, ?> fnameCol;
    @FXML
    private TableColumn<?, ?> snameCol;
    @FXML
    private TableColumn<?, ?> ageCol;
    @FXML
    private TableColumn<?, ?> weightCol;
    @FXML
    private TableColumn<?, ?> heightCol;
    @FXML
    private TableColumn<?, ?> addressCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
