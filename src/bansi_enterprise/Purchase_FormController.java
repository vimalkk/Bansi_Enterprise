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
import java.time.LocalDate;
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
public class Purchase_FormController implements Initializable {
    @FXML AnchorPane AnchorPanePurchaseReturn;
    @FXML AnchorPane AnchorPanePurchase;
    
    @FXML JFXComboBox cmbPurchaseCategory; 
    @FXML JFXComboBox cmbPurchaseName; 
    @FXML JFXComboBox cmbPurchaseStatus;
    @FXML JFXComboBox cmbPurchaseListStatus;
       
    @FXML JFXDatePicker dtPurchaseDatePickerFrom;
    @FXML JFXDatePicker dtPurchaseDatePickerTo;
    @FXML JFXDatePicker dtPurchaseDatePicker;
  
    @FXML JFXTextField txtPurchaseCategory;
    @FXML JFXTextField txtPurchaseContact;
    @FXML JFXTextField txtPurchaseCity;
    @FXML JFXTextField txtPurchaseOrderNo;
    @FXML JFXTextField txtPurchaseDescription;
   
    @FXML Text lblPurchaseCategory;
    @FXML Text lblPurchaseFrom;
    @FXML Text lblPurchaseTo;
    @FXML Text lblUserName;
    
    @FXML JFXTreeTableView tblPurchaseForm;
    
    long Contact=0; 
    int Id=0;
    int Total=0;
    int Paid=0;
    int Balance=(Total-Paid);
    String City=null;
    String Name=null;
    String Status=null;
    String Description=null;
    LocalDate Date=null;
       
    @FXML
    private void PurchaseButton(Event event) {
     
        AnchorPanePurchaseReturn.setVisible(false);
        AnchorPanePurchase.setVisible(true);
    }
    
    @FXML
    private void PurchaseReturnButton(Event event) {
        
       AnchorPanePurchaseReturn.setVisible(true);
       AnchorPanePurchase.setVisible(false);
    }
    
    @FXML
    private void PurchaseList(Event event) throws IOException {     
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Purchase_List.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Purchase List");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.showAndWait();
    }
    
    
    
@FXML
private void VendorForm(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPaneCustomerForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Vendor_Form.fxml"));
    Scene scene = new Scene(anchorPaneCustomerForm);
    newStage.setTitle("Vendor Form");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.showAndWait();        
}

    
    @FXML 
    private void VendorName(Event event){   
           try {
               Name=(String)cmbPurchaseName.getValue();
               rs=stmt.executeQuery("select vendor_id,vendor_mobile_no,vendor_city from vendor_detail where vendor_name='"+Name+"';");
               while(rs.next()){
                   //Id=rs.getInt(1);
                   //Contact=;
                   //City=rs.getString(3);
                
                txtPurchaseContact.setText(String.valueOf(rs.getLong(2)));
                txtPurchaseCity.setText(rs.getString(3) );
                } 
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
          
    }
    //Insert Button Event (Button Event)[FOR ADD NEW PRODUCT]
    @FXML private void Insert(Event event) throws SQLException{
        //Get values from textField
                
        Name=(String)cmbPurchaseName.getValue();
        Date = dtPurchaseDatePicker.getValue();
        Description=txtPurchaseDescription.getText();
        
        if (Balance==0){
            Status="Full Paid";
        }
        else if (Balance<0){
            Status="Half Paid";
        }
        else if(Balance==Total){
            Status="Unpaid";
        }
        
       int rs1=stmt.executeUpdate("insert into purchase_detail(VENDOR_ID,PURCHASE_DATE,PURCHASE_STATUS,SALES_TOTAL,PAID,BALANCE,PURCHASE_DESCRPITION) values ('"+Id+"','"+Contact+"','"+City+"','"+Date+"','"+Status+"','"+Total+"','"+Paid+"','"+Balance+"','"+Description+"')");

    }
    
    
    
    @FXML
    private void ComboBoxCategory (Event event) throws IOException { 
        if (cmbPurchaseCategory.getValue().equals("Name")){
            txtPurchaseCategory.setVisible(true);
            lblPurchaseCategory.setVisible(true);
            lblPurchaseCategory.setText("Search Name");
            
            dtPurchaseDatePickerFrom.setVisible(false);
            lblPurchaseFrom.setVisible(false);
            dtPurchaseDatePickerTo.setVisible(false);
            lblPurchaseTo.setVisible(false);
        }
        if (cmbPurchaseCategory.getValue().equals("Order No")){
            txtPurchaseCategory.setVisible(true);
            lblPurchaseCategory.setVisible(true);
            lblPurchaseCategory.setText(" Order No");
           
            dtPurchaseDatePickerFrom.setVisible(false);
            lblPurchaseFrom.setVisible(false);
            dtPurchaseDatePickerTo.setVisible(false);
            lblPurchaseTo.setVisible(false);
        }
        if (cmbPurchaseCategory.getValue().equals("Date")){
            dtPurchaseDatePickerFrom.setVisible(true);
            lblPurchaseFrom.setVisible(true);
            dtPurchaseDatePickerTo.setVisible(true);
            lblPurchaseTo.setVisible(true);
            
            txtPurchaseCategory.setVisible(false);
            lblPurchaseCategory.setVisible(false);
        }
    }
    
    @FXML 
    public void Add(Event event)
    {
        tableview();

    }
        
    class User extends RecursiveTreeObject<User> {

        StringProperty SrNo;
        StringProperty ItemName;
        StringProperty Quantity;
        StringProperty Price;
        StringProperty Tax;
        StringProperty SubTotal;

        public User(String sr,String item,String qty,String taxrate,String total) {
            this.SrNo = new SimpleStringProperty(sr);
            this.ItemName = new SimpleStringProperty(item);
            this.Quantity = new SimpleStringProperty(qty);
            this.Tax = new SimpleStringProperty(taxrate);
            this.SubTotal = new SimpleStringProperty(total);
        }
    }
        
    public void tableview(){
//        try 
//        {    
            tblPurchaseForm.setEditable(true);
            JFXTreeTableColumn<User, String> SrNo = new JFXTreeTableColumn<>("Sr. No.");
            SrNo.setPrefWidth(150);
            SrNo.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().SrNo;
                }
            });

            JFXTreeTableColumn<User, String> ItemName = new JFXTreeTableColumn<>("ITEM NAME");
            ItemName.setEditable(true);
            ItemName.setPrefWidth(150);
            ItemName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().ItemName;
                }
             });
            
            
            JFXTreeTableColumn<User, String> Quantity = new JFXTreeTableColumn<>("QUANTITY");
            Quantity.setPrefWidth(150);
            Quantity.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().Quantity;
                }
             });
            
            
            JFXTreeTableColumn<User, String> Price = new JFXTreeTableColumn<>("PRICE");
            Price.setPrefWidth(150);
            Price.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().Price;
                }
             });
            
            
            JFXTreeTableColumn<User, String> Tax = new JFXTreeTableColumn<>("TAX AMOUNT");
            Tax.setPrefWidth(150);
            Tax.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().Tax;
                }
             });
            
            
            JFXTreeTableColumn<User, String> SubTotal = new JFXTreeTableColumn<>("SUB TOTAL");
            SubTotal.setPrefWidth(150);
            SubTotal.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().SubTotal;
                }
             });
//            
            String s=" ";
//            rs=stmt.executeQuery("select category_name,category_description from category_detail");

            ObservableList<User> users = FXCollections.observableArrayList();
//            while(rs.next()){
                users.add(new User(s,s,s,s,s));
//            }
            final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
            tblPurchaseForm.getColumns().setAll(SrNo,ItemName,Quantity,Tax,SubTotal);
            tblPurchaseForm.setRoot(root);
            tblPurchaseForm.setShowRoot(false);
//        } 
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        try {
            new H2_Db_Connection().Connectivity();
            
            rs=stmt.executeQuery("select vendor_name from vendor_detail");
            while(rs.next()){
            cmbPurchaseName.getItems().addAll(rs.getString(1));
            }
            
            cmbPurchaseCategory.getItems().addAll("Name","Order No","Date");
            cmbPurchaseListStatus.getItems().addAll("Full Paid","Half Paid","Unpaid");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
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