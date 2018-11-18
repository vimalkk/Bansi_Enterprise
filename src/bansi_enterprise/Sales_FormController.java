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
public class Sales_FormController implements Initializable {
    @FXML AnchorPane AnchorPaneSalesReturn;
    @FXML AnchorPane AnchorPaneSales;
    
    @FXML JFXComboBox cmbSalesCategory; 
    @FXML JFXComboBox cmbSalesName; 
    @FXML JFXComboBox cmbSalesStatus;
    @FXML JFXComboBox cmbSalesListStatus;
       
    @FXML JFXDatePicker dtSalesDatePickerFrom;
    @FXML JFXDatePicker dtSalesDatePickerTo;
    @FXML JFXDatePicker dtSalesDatePicker;
  
    @FXML JFXTextField txtSalesCategory;
    @FXML JFXTextField txtSalesContact;
    @FXML JFXTextField txtSalesCity;
    @FXML JFXTextField txtSalesOrderNo;
    @FXML JFXTextField txtSalesDescription;
   
    @FXML Text lblSalesCategory;
    @FXML Text lblSalesFrom;
    @FXML Text lblSalesTo;
    @FXML Text lblUserName;
    
    @FXML JFXTreeTableView tblSalesFormList;
    
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
    private void SalesButton(Event event) {
     
        AnchorPaneSalesReturn.setVisible(false);
        AnchorPaneSales.setVisible(true);
    }
    
    @FXML
    private void SalesReturnButton(Event event) {
        
       AnchorPaneSalesReturn.setVisible(true);
       AnchorPaneSales.setVisible(false);
    }
    
    @FXML
    private void SalesList(Event event) throws IOException {     
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Sales_List.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Sales List");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.showAndWait();
    }
    
    @FXML 
    private void CustomerName(Event event){   
           try {
               Name=(String)cmbSalesName.getValue();
               rs=stmt.executeQuery("select customer_id,customer_mobile_no,customer_city from customer_detail where customer_name='"+Name+"';");
               while(rs.next()){
                   //Id=rs.getInt(1);
                   //Contact=;
                   //City=rs.getString(3);
                txtSalesContact.setText(Long.toString(rs.getLong(2)));
                txtSalesCity.setText(rs.getString(3) );
                } 
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
          
    }
    
    
@FXML
private void CustomerForm(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPaneCustomerForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Customer_Form.fxml"));
    Scene scene = new Scene(anchorPaneCustomerForm);
    newStage.setTitle("Customer Form");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.showAndWait();        
}


    //Insert Button Event (Button Event)[FOR ADD NEW PRODUCT]
    @FXML private void Insert(Event event) throws SQLException{
        //Get values from textField
                
        Name=(String)cmbSalesName.getValue();
        Date = dtSalesDatePicker.getValue();
        Description=txtSalesDescription.getText();
        
        if (Balance==0){
            Status="Full Paid";
        }
        else if (Balance<0){
            Status="Half Paid";
        }
        else if(Balance==Total){
            Status="Unpaid";
        }
        
       int rs1=stmt.executeUpdate("insert into sales_detail(CUSTOMER_ID,SALES_DATE,SALES_STATUS,SALES_TOTAL,PAID,BALANCE,SALES_DESCRPITION) values ('"+Id+"','"+Contact+"','"+City+"','"+Date+"','"+Status+"','"+Total+"','"+Paid+"','"+Balance+"','"+Description+"')");

    }
@FXML
    private void ComboBoxCategory (Event event) throws IOException { 
        if (cmbSalesCategory.getValue().equals("Name")){
            txtSalesCategory.setVisible(true);
            lblSalesCategory.setVisible(true);
            lblSalesCategory.setText("Search Name");
            
            dtSalesDatePickerFrom.setVisible(false);
            lblSalesFrom.setVisible(false);
            dtSalesDatePickerTo.setVisible(false);
            lblSalesTo.setVisible(false);
        }
        if (cmbSalesCategory.getValue().equals("Order No")){
            txtSalesCategory.setVisible(true);
            lblSalesCategory.setVisible(true);
            lblSalesCategory.setText(" Order No");
           
            dtSalesDatePickerFrom.setVisible(false);
            lblSalesFrom.setVisible(false);
            dtSalesDatePickerTo.setVisible(false);
            lblSalesTo.setVisible(false);
        }
        if (cmbSalesCategory.getValue().equals("Date")){
            dtSalesDatePickerFrom.setVisible(true);
            lblSalesFrom.setVisible(true);
            dtSalesDatePickerTo.setVisible(true);
            lblSalesTo.setVisible(true);
            
            txtSalesCategory.setVisible(false);
            lblSalesCategory.setVisible(false);
        }
    }


    class Sales extends RecursiveTreeObject<Sales> {

        StringProperty SalesName;
        StringProperty SalesBalance;

        public Sales(String SalName, String SalBal) {
            this.SalesName = new SimpleStringProperty(SalName);
            this.SalesBalance = new SimpleStringProperty(SalBal);
        }
    }
        
    public void tableview(){
        try 
        {    
            JFXTreeTableColumn<Sales, String> SalName = new JFXTreeTableColumn<>("CUSTOMER NAME");
            SalName.setPrefWidth(150);
            SalName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sales, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sales, String> param) {
                    return param.getValue().getValue().SalesName;
                }
            });

            JFXTreeTableColumn<Sales, String> SalBal = new JFXTreeTableColumn<>("CUSTOMER BALANCE");
            SalBal.setPrefWidth(150);
            SalBal.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sales, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sales, String> param) {
                    return param.getValue().getValue().SalesBalance;
                }
             });

            rs=stmt.executeQuery("select sales_name,sales_balance from sales_detail");

            ObservableList<Sales> saleslist = FXCollections.observableArrayList();
            while(rs.next()){
                saleslist.add(new Sales(rs.getString(1),rs.getString(2)));
            }
            final TreeItem<Sales> root = new RecursiveTreeItem<>(saleslist, RecursiveTreeObject::getChildren);
            tblSalesFormList.getColumns().setAll(SalName, SalBal);
            tblSalesFormList.setRoot(root);
            tblSalesFormList.setShowRoot(false);
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
            
        try {
            new H2_Db_Connection().Connectivity();
            
            rs=stmt.executeQuery("select customer_name from customer_detail");
            while(rs.next()){
            cmbSalesName.getItems().addAll(rs.getString(1));
            }
            
            cmbSalesCategory.getItems().addAll("Name","Order No","Date");
            cmbSalesListStatus.getItems().addAll("Full Paid","Half Paid","Unpaid");
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
