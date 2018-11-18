package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author BANSIENTERPRISE
 */
public class MainController implements Initializable {    

    @FXML JFXButton purchase,customer,sales,vendor;
    
@FXML 
public void Logout(Event event) throws IOException, SQLException{
    int rs1=stmt.executeUpdate("delete from CURRENT_USER where USER_ID=1");
    
    Platform.exit();
    System.exit(1);
   
}

/* SALES */
@FXML
private void SalesList(Event event) throws IOException {  
    
    Stage newStage = new Stage();
    AnchorPane anchorPaneSalesList = (AnchorPane) FXMLLoader.load(getClass().getResource("Sales_List.fxml"));
    Scene scene = new Scene(anchorPaneSalesList);
    newStage.setTitle("Sales List");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();        
}  

@FXML
private void SalesForm(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPaneSalesForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Sales_Form.fxml"));
    Scene scene = new Scene(anchorPaneSalesForm);
    newStage.setTitle("Sales Form");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();
}  


/* PURCHASE */
@FXML
private void PurchaseList(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPanePurchaseList = (AnchorPane) FXMLLoader.load(getClass().getResource("Purchase_List.fxml"));
    Scene scene = new Scene(anchorPanePurchaseList);
    newStage.setTitle("Purchase List");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();        
}    

@FXML
private void PurchaseForm(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPanePurchaseForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Purchase_Form.fxml"));
    Scene scene = new Scene(anchorPanePurchaseForm);
    newStage.setTitle("Purchase Form");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();        
}  


/* PRODUCT */
@FXML
private void ProductList(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPaneProductList = (AnchorPane) FXMLLoader.load(getClass().getResource("Product_List.fxml"));
    Scene scene = new Scene(anchorPaneProductList);
    newStage.setTitle("Product List");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();        
}    

@FXML
private void ProductForm(Event event) throws IOException {     
     Stage newStage = new Stage();
    AnchorPane anchorPaneProductForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Product_Form.fxml"));
    Scene scene = new Scene(anchorPaneProductForm);
    newStage.setTitle("Product Form");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();  
}    


/* CUSTOMER */
@FXML
private void CustomerList(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPaneCustomerList = (AnchorPane) FXMLLoader.load(getClass().getResource("Customer_List.fxml"));
    Scene scene = new Scene(anchorPaneCustomerList);
    newStage.setTitle("Customer List");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();        
}

@FXML
private void CustomerForm(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPaneCustomerForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Customer_Form.fxml"));
    Scene scene = new Scene(anchorPaneCustomerForm);
    newStage.setTitle("Customer Form");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();        
}


/* VENDOR */
@FXML
private void VendorList(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPaneVendorList = (AnchorPane) FXMLLoader.load(getClass().getResource("Vendor_List.fxml"));
    Scene scene = new Scene(anchorPaneVendorList);
    newStage.setTitle("Vendor List");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();        
}

@FXML
private void VendorForm(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPaneVendorForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Vendor_Form.fxml"));
    Scene scene = new Scene(anchorPaneVendorForm);
    newStage.setTitle("Vendor Form");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.show();        
}

@FXML public void Main(Event event){
     
        
    
        TranslateTransition Purchase= new TranslateTransition();
        Purchase.setDuration(Duration.seconds(2));
        Purchase.setNode(this.purchase);
        Purchase.setToX(185);
        Purchase.setToY(-185);
        Purchase.play();
        
        TranslateTransition Customer= new TranslateTransition();
        Customer.setDuration(Duration.seconds(2));
        Customer.setNode(this.customer);
        Customer.setToX(185);
        Customer.setToY(185);
        Customer.play();
        
        TranslateTransition Sales= new TranslateTransition();
        Sales.setDuration(Duration.seconds(2));
        Sales.setNode(this.sales);
        Sales.setToX(-185);
        Sales.setToY(185);
        Sales.play();
        
        TranslateTransition Vendor= new TranslateTransition();
        Vendor.setDuration(Duration.seconds(2));
        Vendor.setNode(this.vendor);
        Vendor.setToX(-185);
        Vendor.setToY(-185);
        Vendor.play();
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