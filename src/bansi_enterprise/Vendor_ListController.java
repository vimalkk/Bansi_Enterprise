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
public class Vendor_ListController implements Initializable {
   
    @FXML Text lblVendorListCategory;
    @FXML Text lblVendorListTo;
    @FXML Text lblVendorListFrom;
    @FXML Text lblUserName;
    
    @FXML JFXComboBox cmbVendorListCategory;
    @FXML JFXComboBox cmbVendorListStatus;
            
    @FXML JFXDatePicker dtVendorListDatePickerFrom;
    @FXML JFXDatePicker dtVendorListDatePickerTo;
        
    @FXML JFXTextField txtVendorListCategory;
    
    @FXML JFXTreeTableView tblVendorList;
    
    @FXML
    private void VendorForm(Event event) throws IOException {     

        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Vendor_Form.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Vendor Form");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.show();    
    }
    
    @FXML
    private void Search(Event event) throws IOException {     
        tableview();
    }
    
    @FXML
    private void ComboBoxCategoryList (Event event) throws IOException { 
        if (cmbVendorListCategory.getValue().equals("Name")){
            txtVendorListCategory.setVisible(true);
            lblVendorListCategory.setVisible(true);
            lblVendorListCategory.setText("Search Name");
            
            dtVendorListDatePickerFrom.setVisible(false);
            lblVendorListFrom.setVisible(false);
            dtVendorListDatePickerTo.setVisible(false);
            lblVendorListTo.setVisible(false);
        }
        if (cmbVendorListCategory.getValue().equals("Order No")){
            txtVendorListCategory.setVisible(true);
            lblVendorListCategory.setVisible(true);
            lblVendorListCategory.setText(" Order No");
           
            dtVendorListDatePickerFrom.setVisible(false);
            lblVendorListFrom.setVisible(false);
            dtVendorListDatePickerTo.setVisible(false);
            lblVendorListTo.setVisible(false);
        }
        if (cmbVendorListCategory.getValue().equals("Date")){
            dtVendorListDatePickerFrom.setVisible(true);
            lblVendorListFrom.setVisible(true);
            dtVendorListDatePickerTo.setVisible(true);
            lblVendorListTo.setVisible(true);
            
            txtVendorListCategory.setVisible(false);
            lblVendorListCategory.setVisible(false);
        }
    }
    
    class User extends RecursiveTreeObject<User> {
        StringProperty name;
        StringProperty city;
        StringProperty mobile;
        StringProperty bal;
        
        public User(String name, String city, String mobile, String bal) {
            this.name = new SimpleStringProperty(name);
            this.city = new SimpleStringProperty(city);
            this.mobile = new SimpleStringProperty(mobile);
            this.bal = new SimpleStringProperty(bal);
        }

    }
    
    public void tableview(){
        try {
            JFXTreeTableColumn<User, String> Name = new JFXTreeTableColumn<>("NAME");
            Name.setPrefWidth(150);
            Name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().name;
                }
            });
            
            JFXTreeTableColumn<User, String> City = new JFXTreeTableColumn<>("CITY");
            City.setPrefWidth(150);
            City.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().city;
                }
             });
            
            JFXTreeTableColumn<User, String> Mobile = new JFXTreeTableColumn<>("MOBILE");
            Mobile.setPrefWidth(150);
            Mobile.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().mobile;
                }
             });
            
            JFXTreeTableColumn<User, String> Balance = new JFXTreeTableColumn<>("BALANCE");
            Balance.setPrefWidth(150);
            Balance.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().bal;
                }
             });
            
            rs=stmt.executeQuery("select vendor_name,vendor_city,vendor_mobile_no,vendor_balance from vendor_detail");
            
            ObservableList<User> users = FXCollections.observableArrayList();
            while(rs.next()){
                users.add(new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
            tblVendorList.getColumns().setAll(Name,City,Mobile,Balance);
            tblVendorList.setRoot(root);
            tblVendorList.setShowRoot(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    //int rs1=stmt.executeUpdate("INSERT INTO CUSTOMER_DETAIL (CUSTOMER_NAME,CUSTOMER_CITY,CUSTOMER_MOBILE_NO,CUSTOMER_BALANCE) VALUES ('"+Name+"','"+City+"','"+MobileNo+"','"+Balance+"');");
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableview();
        new H2_Db_Connection().Connectivity();
        cmbVendorListCategory.getItems().addAll("Name","Order No","Date");
        cmbVendorListStatus.getItems().addAll("Full Paid","Half Paid","Unpaid");
        
        
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