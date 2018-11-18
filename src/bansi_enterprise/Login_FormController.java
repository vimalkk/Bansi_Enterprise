package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.control.Alert;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BANSIENTERPRISE
 */
public class Login_FormController implements Initializable {

    @FXML JFXTextField txtUserName;
    @FXML JFXPasswordField txtPassword;
    
    @FXML static AnchorPane AnchorPaneLogin;
    
    @FXML JFXButton btnLogin;
    
    Stage SignUpStage = new Stage();
    
    String UserName=null;
    
    @FXML
    private void SignUpForm(Event event) throws IOException {
        
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Sign_Up.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Sign Up Form");
        newStage.setResizable(false);
        newStage.setMaxHeight(470);
        newStage.setWidth(540);
        newStage.setScene(scene);
        newStage.show();
        
    }
   
    @FXML
    private void ForgotPassword(Event event) throws IOException {
        
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Forgot_Password.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        SignUpStage.setTitle("Forgot Password");
        SignUpStage.setResizable(false);
        SignUpStage.setWidth(520);
        SignUpStage.setMaxHeight(250);
        SignUpStage.setScene(scene);
        SignUpStage.show();    
    }
    
    @FXML
    private void Clear(Event event) throws IOException {     
        txtUserName.clear();
        txtPassword.clear();
    }  
    
    @FXML
    private void Login(Event event) throws IOException {   
        
        try {
            
            UserName=txtUserName.getText();
            String Password=md5Convert.getMD5(txtPassword.getText());
            String UserPassword=null;
            
            if(!txtUserName.getText().equals("") | !txtPassword.getText().equals("")){
                rs=stmt.executeQuery("select user_password from user_detail where user_name='"+UserName+"'");
                while(rs.next()){UserPassword=rs.getString(1);}
                if(Password.equals(UserPassword)){
                    
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    stage.close();  
                    
 
                    int rs1=stmt.executeUpdate("insert into current_user (user_name) values('"+UserName+"')");
                    
                    Stage newStage = new Stage();
                    AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Main.fxml"));
                    Scene scene = new Scene(anchorPanePopup);
                    newStage.setTitle("Main Form");
                    newStage.setMaximized(true);
                    newStage.setScene(scene);
                    newStage.show();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Enter Valid UserName And Password...!");
                    alert.setContentText("Passwod And UserNam e Not Match../retry");
                    alert.show();
                }
            }
            else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid UserName And Password...!");
                    alert.setContentText("Password Should not Blank ");
                    alert.show();
              
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        UserName=txtUserName.getText();
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