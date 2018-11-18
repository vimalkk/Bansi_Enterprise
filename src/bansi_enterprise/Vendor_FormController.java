package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author BANSIENTERPRISE
 */
public class Vendor_FormController implements Initializable {
    
    //Fetch fields/elements from Vendor_Form.fxml file  
    @FXML JFXTextField txtVendorName;
    @FXML JFXTextField txtVendorCity;
    @FXML JFXTextField txtVendorContactNo;
    @FXML JFXTextField txtVendorCategory;
    @FXML JFXTextField txtVendorBalance;
    
    @FXML JFXComboBox cmbVendorCategory;
    @FXML JFXComboBox cmbVendorStatus;
    
    @FXML JFXDatePicker dtVendorDatePickerFrom;
    @FXML JFXDatePicker dtVendorDatePickerTo; 
    
    @FXML Text lblVendorCategory;
    @FXML Text lblVendorFrom;
    @FXML Text lblVendorTo;
    @FXML Text lblUserName;
    
    @FXML JFXTreeTableView tblVendorFormList;
    
    //Create event for redirect Vendor Form to Vendor List
    @FXML
    private void VendorList(Event event) throws IOException { 
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Vendor_List.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Vendor List");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.show();     
    }
   
    //Create event for insert Vendor's Detail in database
    @FXML
    private void Insert(Event event) throws IOException { 
        try {
            if(txtVendorCity.getText().equals("") || txtVendorName.getText().equals("") || txtVendorContactNo.getLength()!=10 || txtVendorContactNo.getText().equals("")){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Enter valid input.");
                alert.setContentText("Please feel up fields .\nshould not be empty.\nTry again");
                alert.showAndWait();
                
            }
            else {
               
                    String Name=Bansi_Enterprise.firstLetterCaps(txtVendorName.getText());
                    String City=Bansi_Enterprise.firstLetterCaps(txtVendorCity.getText());
                    long MobileNo=Long.parseLong(txtVendorContactNo.getText());
                    int rs1=stmt.executeUpdate("insert into vendor_detail (vendor_name,vendor_city,vendor_mobile_no) values ('"+Name+"','"+City+"','"+MobileNo+"')");
                           
                    Alert alert= new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("DONE");
                    alert.setContentText("RECORD ADDED");
                    alert.show();

                    long mTime = System.currentTimeMillis();
                    long end = mTime + 1000; // 1 seconds 

                    while (mTime < end){
                        mTime = System.currentTimeMillis();
                    } 
                    alert.close();

                    txtVendorName.setText(null);
                    txtVendorCity.setText(null);
                    txtVendorContactNo.setText(null);
            }
        } catch (SQLException ex) {
           Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Enter valid input.");
            alert.setContentText("Enter valid values.\nTry again");
            alert.showAndWait();
            
            txtVendorName.setText(null);
            txtVendorCity.setText(null);
            txtVendorContactNo.setText(null);
        }
    }
    
    //Create event on combobox
    @FXML
    private void ComboBoxCategory (Event event) throws IOException { 
        //When select "Name" in combobox, only show Label and TexFied also change the label text and others related fields are hide
        if (cmbVendorCategory.getValue().equals("Name")){
            txtVendorCategory.setVisible(true);
            lblVendorCategory.setVisible(true);
            lblVendorCategory.setText("Search Name");
            
            dtVendorDatePickerFrom.setVisible(false);
            lblVendorFrom.setVisible(false);
            dtVendorDatePickerTo.setVisible(false);
            lblVendorTo.setVisible(false);
        }
        //When select "Order No" in combobox then only show Label and TexFied also change the label text and others related fields are hide
        if (cmbVendorCategory.getValue().equals("Order No")){
            txtVendorCategory.setVisible(true);
            lblVendorCategory.setVisible(true);
            lblVendorCategory.setText(" Order No");
           
            dtVendorDatePickerFrom.setVisible(false);
            lblVendorFrom.setVisible(false);
            dtVendorDatePickerTo.setVisible(false);
            lblVendorTo.setVisible(false);
        }
        //When select "Date" in combobox then only show only DatePickers and others related fields are hide
        if (cmbVendorCategory.getValue().equals("Date")){
            dtVendorDatePickerFrom.setVisible(true);
            lblVendorFrom.setVisible(true);
            dtVendorDatePickerTo.setVisible(true);
            lblVendorTo.setVisible(true);
            
            txtVendorCategory.setVisible(false);
            lblVendorCategory.setVisible(false);
        }
    }
    
    class Vendor extends RecursiveTreeObject<Vendor> {

        StringProperty VendorName;
        StringProperty VendorBalance;

        public Vendor(String VenName, String VenBal) {
            this.VendorName = new SimpleStringProperty(VenName);
            //this.userName = new SimpleStringProperty(userName);
            this.VendorBalance = new SimpleStringProperty(VenBal);
        }
    }
        
    public void tableview(){
        try 
        {    
            JFXTreeTableColumn<Vendor, String> VenName = new JFXTreeTableColumn<>("VENDOR NAME");
            VenName.setPrefWidth(150);
            VenName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Vendor, String> param) {
                    return param.getValue().getValue().VendorName;
                }
            });

            JFXTreeTableColumn<Vendor, String> VenBal = new JFXTreeTableColumn<>("VENDOR BALANCE");
            VenBal.setPrefWidth(150);
            VenBal.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Vendor, String> param) {
                    return param.getValue().getValue().VendorBalance;
                }
             });

            rs=stmt.executeQuery("select vendor_name,vendor_balance from vendor_detail");

            ObservableList<Vendor> vendors = FXCollections.observableArrayList();
            while(rs.next()){
                vendors.add(new Vendor(rs.getString(1),rs.getString(2)));
            }
            final TreeItem<Vendor> root = new RecursiveTreeItem<>(vendors, RecursiveTreeObject::getChildren);
            tblVendorFormList.getColumns().setAll(VenName, VenBal);
            tblVendorFormList.setRoot(root);
            tblVendorFormList.setShowRoot(false);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Call Connectivity() function from H2_Db_Connection.java Class File to connect Database.
        new H2_Db_Connection().Connectivity();
        
        //Add Common values in comboBox.
        cmbVendorCategory.getItems().addAll("Name","Order No","Date");
        cmbVendorStatus.getItems().addAll("Full Paid","Half Paid","Unpaid");
        
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
