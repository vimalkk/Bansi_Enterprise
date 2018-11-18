package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import java.io.IOException;
import java.sql.SQLException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Bansi_Enterprise extends Application {
    
    int id=0;
    
    @Override
    public void start(Stage stage){
        try {
            int rs1=stmt.executeUpdate("delete from CURRENT_USER where user_id=1");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            Check();
            if (id!=1){
                
                
                Parent root = FXMLLoader.load(getClass().getResource("Login_Form.fxml"));
                Scene scene = new Scene(root,400,490);
                stage.setTitle("Welcome to Bansi Enterprise");
                stage.setResizable(false);
                stage.setScene(scene);
                
                FadeTransition ft = new FadeTransition(Duration.seconds(3),root);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.setCycleCount(1);
                ft.play();
             
                stage.show();
            }
            else
            {
                Alert dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Error");
                dialog.setHeaderText("User already Sign In");
                dialog.setContentText("Please logout first exist user !!!\nTry again...");
                dialog.showAndWait();
                
                System.exit(1);
            }
        } catch (IOException | SQLException ex) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText("User already use the database");
            dialog.setContentText("Please logout first exist user !!!\nTry again...");
            dialog.showAndWait();

            System.exit(1);
        }
        
    }
 
    public void Check() throws SQLException{
        rs=stmt.executeQuery("select USER_ID from CURRENT_USER");
        while(rs.next()){ id=rs.getInt(1); }
    }
    
    static public String firstLetterCaps ( String data )
    {
        String firstLetter = data.substring(0,1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }
    
    /**
     * @param args the command line arguments
     */ 
    public static void main(String[] args) {
        new H2_Db_Connection().Connectivity();
        launch(args);
    }
}