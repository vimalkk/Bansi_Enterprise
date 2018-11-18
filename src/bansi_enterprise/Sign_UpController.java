package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import static bansi_enterprise.md5Convert.getMD5;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BANSIENTERPRISE
 */
public class Sign_UpController implements Initializable {
   
    @FXML JFXTextField txtUserName;
    @FXML JFXPasswordField txtUserPassword;
    @FXML JFXPasswordField txtUserConfirmPassword;
    @FXML JFXTextField txtUserMobileNo;
    
    @FXML JFXButton btnRegister;
    
     @FXML private void Clear(Event event) throws SQLException{
         txtUserName.setText(null);
         txtUserPassword.setText(null);
         txtUserConfirmPassword.setText(null);
         txtUserMobileNo.setText(null);
         
     }
    
    @FXML private void Register(Event event) throws SQLException{
     
        if (txtUserMobileNo.getLength()!=10 | txtUserMobileNo.getLength()==0 | "".equals(txtUserName.getText()) | "".equals(txtUserConfirmPassword.getText()) | "".equals(txtUserMobileNo.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroor");
            alert.setHeaderText("Enterd invelid value ! ! !");
            alert.setContentText("Please enter valid value in fields..\nRetry....");
            alert.showAndWait();
            
            txtUserName.clear();
            txtUserPassword.clear();
            txtUserConfirmPassword.clear();
            txtUserMobileNo.clear();    
        }
        else if(!txtUserPassword.getText().equals(txtUserConfirmPassword.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password not match");
            alert.setContentText("Enter same password in both field...\nRetry....");
            alert.showAndWait();
            
            txtUserName.clear();
            txtUserPassword.clear();
            txtUserConfirmPassword.clear();
            txtUserMobileNo.clear();    
        }
        else
        {
            String UserName=txtUserName.getText();
            String UserPassword=getMD5(txtUserPassword.getText());  
            long UserMobileNo=Long.parseLong(txtUserMobileNo.getText());
            
            int rs1=stmt.executeUpdate("insert into user_detail(user_name,user_password,user_mobile_no) values ('"+UserName+"','"+UserPassword+"',"+UserMobileNo+")");
            
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DONE");
            alert.setContentText("REGRISTRATION DONE ! ! !");
            alert.show();

            long mTime = System.currentTimeMillis();
            long end = mTime + 1000; // 1 seconds 

            while (mTime < end){
                mTime = System.currentTimeMillis();
            } 
            alert.close();
            
            Stage stage = (Stage) btnRegister.getScene().getWindow();
            stage.close();
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
    } 
}