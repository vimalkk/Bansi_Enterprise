package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BANSIENTERPRISE
 */
public class Sales_ListController implements Initializable {
    @FXML
    private void SalesForm(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Sales_Form.fxml"));
    Scene scene = new Scene(anchorPanePopup);
    newStage.setTitle("Sales Order Form");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.showAndWait();
}
    @FXML JFXComboBox cmbSalesListCategory; 
    @FXML JFXComboBox cmbSalesListStatus;

    @FXML JFXDatePicker dtSalesListDatePickerTo;
    @FXML JFXDatePicker dtSalesListDatePickerFrom;
    @FXML JFXTextField txtSalesListCategory;

    @FXML Text lblSalesListCategory;
    @FXML Text lblSalesListFrom;
    @FXML Text lblSalesListTo;
    @FXML Text lblUserName;

     @FXML
    private void ComboBoxCategory (Event event) throws IOException { 
        if (cmbSalesListCategory.getValue().equals("Name")){
            txtSalesListCategory.setVisible(true);
            lblSalesListCategory.setVisible(true);
            lblSalesListCategory.setText("Search Name");
            
            dtSalesListDatePickerFrom.setVisible(false);
            lblSalesListFrom.setVisible(false);
            dtSalesListDatePickerTo.setVisible(false);
            lblSalesListTo.setVisible(false);
        }
        if (cmbSalesListCategory.getValue().equals("Order No")){
            txtSalesListCategory.setVisible(true);
            lblSalesListCategory.setVisible(true);
            lblSalesListCategory.setText(" Order No");
           
            dtSalesListDatePickerFrom.setVisible(false);
            lblSalesListFrom.setVisible(false);
            dtSalesListDatePickerTo.setVisible(false);
            lblSalesListTo.setVisible(false);
        }
        if (cmbSalesListCategory.getValue().equals("Date")){
            dtSalesListDatePickerFrom.setVisible(true);
            lblSalesListFrom.setVisible(true);
            dtSalesListDatePickerTo.setVisible(true);
            lblSalesListTo.setVisible(true);
            
            txtSalesListCategory.setVisible(false);
            lblSalesListCategory.setVisible(false);
        }
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new H2_Db_Connection().Connectivity();
        
        cmbSalesListCategory.getItems().addAll("Name","Order No","Date");
        cmbSalesListStatus.getItems().addAll("Full Paid","Half Paid","Unpaid");
        
        String User=null;
        try {
            rs=stmt.executeQuery("Select user_name from current_user");
            while (rs.next()){ User=rs.getString(1); }
            lblUserName.setText(User);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }    
}