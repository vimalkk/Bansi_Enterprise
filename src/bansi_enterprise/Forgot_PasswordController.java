package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import static bansi_enterprise.md5Convert.getMD5;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author BANSIENTERPRISE
 */

public class Forgot_PasswordController implements Initializable {
    
    @FXML JFXTextField txtUserName;
    @FXML JFXTextField txtMobileNo;
    
    @FXML JFXPasswordField txtPassword;
    @FXML JFXPasswordField txtConfirmPassword;
    
    @FXML AnchorPane anchorPaneResetPassword;
    @FXML AnchorPane anchorPaneForgotPassword;
    String User=null;
    long Mobile=0;
    
    @FXML void Confirm(Event event) throws IOException {
        if (txtUserName.getText().equals("") || txtMobileNo.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Field should not empty.");
          
            alert.setContentText("Enter user namd or password.");
            alert.showAndWait();
        }
        else {
            try {
            User=txtUserName.getText();
            long MobileNo=Long.parseLong(txtMobileNo.getText());
            //long MobileNo=Long.parseLong(txtMobileNo.getText());
            
            rs=stmt.executeQuery("select user_mobile_no from user_detail where user_name='"+User+"'");
            while(rs.next()){ Mobile=rs.getLong(1); }

            if (MobileNo==Mobile){
                 anchorPaneForgotPassword.setVisible(false);
                 anchorPaneResetPassword.setVisible(true);
            }
            }
            catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please try again.");
                alert.showAndWait();
            }
        }     
    }
        
    @FXML void Reset(Event event) throws IOException {
        
        if (txtPassword.getText().equals("") || txtConfirmPassword.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Field should not empty.");
            alert.setContentText("Enter user namd or password.");
            alert.showAndWait();
        }
        else
        {
             if(txtConfirmPassword.getText().equals(txtPassword.getText()))
            {
                String Password=getMD5(txtPassword.getText());
                try {
                    int rs1=stmt.executeUpdate("update user_detail set user_password='"+Password+"' WHERE user_name='"+User+"'");
                }
                catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please try again.");
                    alert.showAndWait();
                }
            }
        }
    }
   
    @FXML public void Clear(Event event){
        txtUserName.setText(null);
        txtMobileNo.setText(null);
        
        txtPassword.setText(null);
        txtConfirmPassword.setText(null);
    }
    
    /**
    * Initializes the controller class.
    * @param url
    * @param rb
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new H2_Db_Connection().Connectivity();
    }    
    
}