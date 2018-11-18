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
public class Purchase_ListController implements Initializable {
       
    @FXML JFXComboBox cmbPurchaseListCategory; 
    @FXML JFXComboBox cmbPurchaseListStatus;

    @FXML JFXTextField txtPurchaseListCategory;

    @FXML Text lblPurchaseListCategory;
    @FXML Text lblPurchaseListFrom;
    @FXML Text lblPurchaseListTo;
    @FXML Text lblUserName;
        
    @FXML JFXDatePicker dtPurchaseListDatePickerTo;
    @FXML JFXDatePicker dtPurchaseListDatePickerFrom;
  
 @FXML
        private void PurchaseForm(Event event) throws IOException {     
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Purchase_Form.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Purchase Form");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.showAndWait(); 
    }
     
        @FXML
    private void ComboBoxCategory (Event event) throws IOException { 
        if (cmbPurchaseListCategory.getValue().equals("Name")){
            txtPurchaseListCategory.setVisible(true);
            lblPurchaseListCategory.setVisible(true);
            lblPurchaseListCategory.setText("Search Name");
            
            dtPurchaseListDatePickerFrom.setVisible(false);
            lblPurchaseListFrom.setVisible(false);
            dtPurchaseListDatePickerTo.setVisible(false);
            lblPurchaseListTo.setVisible(false);
        }
        if (cmbPurchaseListCategory.getValue().equals("Order No")){
            txtPurchaseListCategory.setVisible(true);
            lblPurchaseListCategory.setVisible(true);
            lblPurchaseListCategory.setText(" Order No");
           
            dtPurchaseListDatePickerFrom.setVisible(false);
            lblPurchaseListFrom.setVisible(false);
            dtPurchaseListDatePickerTo.setVisible(false);
            lblPurchaseListTo.setVisible(false);
        }
        if (cmbPurchaseListCategory.getValue().equals("Date")){
            dtPurchaseListDatePickerFrom.setVisible(true);
            lblPurchaseListFrom.setVisible(true);
            dtPurchaseListDatePickerTo.setVisible(true);
            lblPurchaseListTo.setVisible(true);
            
            txtPurchaseListCategory.setVisible(false);
            lblPurchaseListCategory.setVisible(false);
        }
    }
    public void initialize(URL url, ResourceBundle rb) {
              
        cmbPurchaseListCategory.getItems().addAll("Name","Order No","Date");
        cmbPurchaseListStatus.getItems().addAll("Full Paid","Half Paid","Unpaid");
       
       
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

