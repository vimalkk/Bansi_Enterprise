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
public class Customer_FormController implements Initializable {
    
    //Fetch fields/elements from Customer_Form.fxml file  
    @FXML JFXTextField txtCustomerName;
    @FXML JFXTextField txtCustomerCity;
    @FXML JFXTextField txtCustomerContactNo;
    @FXML JFXTextField txtCustomerCategory;
    @FXML JFXTextField txtCustomerBalance;
    
    @FXML JFXComboBox cmbCustomerCategory;
    @FXML JFXComboBox cmbCustomerStatus;
    
    @FXML JFXDatePicker dtCustomerDatePickerFrom;
    @FXML JFXDatePicker dtCustomerDatePickerTo; 
    
    @FXML Text lblCustomerCategory;
    @FXML Text lblCustomerFrom;
    @FXML Text lblCustomerTo;
    @FXML Text lblUserName;
    
    
    @FXML JFXTreeTableView tblCustomerFormList;
    
    //Create event for redirect Customer Form to Customer List
    @FXML
    private void CustomerList(Event event) throws IOException { 
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Customer_List.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Customer List");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.show();     
    }
   
    @FXML Text navigation1;
    @FXML
    private void navigationHome(Event event) throws IOException { 
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Main Form");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.show();     
        
        Stage stage = (Stage) navigation1.getScene().getWindow();
        stage.close(); 
    }
    
    @FXML Text navigation2;
    @FXML
    private void navigationCustomerForm(Event event) throws IOException { 
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Customer_Form.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Customer Form");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.show();     
        
        Stage stage = (Stage) navigation2.getScene().getWindow();
        stage.close(); 
    }
    
    //Create event for insert Customer's Detail in database
    @FXML
    private void Insert(Event event) throws IOException { 
        try {
            if(txtCustomerCity.getText().equals("") || txtCustomerName.getText().equals("") || txtCustomerContactNo.getLength()!=10 || txtCustomerContactNo.getText().equals("")){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Enter valid input.");
                alert.setContentText("Please feel up fields .\nshould not be empty.\nTry again");
                alert.showAndWait();
                
            }
            else {
               
                    String Name=Bansi_Enterprise.firstLetterCaps(txtCustomerName.getText());
                    String City=Bansi_Enterprise.firstLetterCaps(txtCustomerCity.getText());
                    long MobileNo=Long.parseLong(txtCustomerContactNo.getText());
                    int rs1=stmt.executeUpdate("insert into customer_detail (customer_name,customer_city,customer_mobile_no) values ('"+Name+"','"+City+"','"+MobileNo+"')");
                           
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

                    txtCustomerName.setText(null);
                    txtCustomerCity.setText(null);
                    txtCustomerContactNo.setText(null);
            }
        } catch (SQLException ex) {
           Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Enter valid input.");
            alert.setContentText("Enter valid values.\nTry again");
            alert.showAndWait();
            
            
            txtCustomerName.setText(null);
            txtCustomerCity.setText(null);
            txtCustomerContactNo.setText(null);
        }
    }
    
    //Create event on combobox
    @FXML
    private void ComboBoxCategory (Event event) throws IOException { 
        //When select "Name" in combobox, only show Label and TexFied also change the label text and others related fields are hide
        if (cmbCustomerCategory.getValue().equals("Name")){
            txtCustomerCategory.setVisible(true);
            lblCustomerCategory.setVisible(true);
            lblCustomerCategory.setText("Search Name");
            
            dtCustomerDatePickerFrom.setVisible(false);
            lblCustomerFrom.setVisible(false);
            dtCustomerDatePickerTo.setVisible(false);
            lblCustomerTo.setVisible(false);
        }
        //When select "Order No" in combobox then only show Label and TexFied also change the label text and others related fields are hide
        if (cmbCustomerCategory.getValue().equals("Order No")){
            txtCustomerCategory.setVisible(true);
            lblCustomerCategory.setVisible(true);
            lblCustomerCategory.setText(" Order No");
           
            dtCustomerDatePickerFrom.setVisible(false);
            lblCustomerFrom.setVisible(false);
            dtCustomerDatePickerTo.setVisible(false);
            lblCustomerTo.setVisible(false);
        }
        //When select "Date" in combobox then only show only DatePickers and others related fields are hide
        if (cmbCustomerCategory.getValue().equals("Date")){
            dtCustomerDatePickerFrom.setVisible(true);
            lblCustomerFrom.setVisible(true);
            dtCustomerDatePickerTo.setVisible(true);
            lblCustomerTo.setVisible(true);
            
            txtCustomerCategory.setVisible(false);
            lblCustomerCategory.setVisible(false);
        }
    }
    
    class Customer extends RecursiveTreeObject<Customer> {

        StringProperty CustomerName;
        StringProperty CustomerBalance;

        public Customer(String CustName, String CustBal) {
            this.CustomerName = new SimpleStringProperty(CustName);
            this.CustomerBalance = new SimpleStringProperty(CustBal);
        }
    }
        
    public void tableview(){
        try 
        {    
            JFXTreeTableColumn<Customer, String> CustName = new JFXTreeTableColumn<>("CUSTOMER NAME");
            CustName.setPrefWidth(150);
            CustName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customer, String> param) {
                    return param.getValue().getValue().CustomerName;
                }
            });

            JFXTreeTableColumn<Customer, String> CustBal = new JFXTreeTableColumn<>("CUSTOMER BALANCE");
            CustBal.setPrefWidth(150);
            CustBal.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customer, String> param) {
                    return param.getValue().getValue().CustomerBalance;
                }
             });

            rs=stmt.executeQuery("select customer_name,customer_balance from customer_detail");

            ObservableList<Customer> customers = FXCollections.observableArrayList();
            while(rs.next()){
                customers.add(new Customer(rs.getString(1),rs.getString(2)));
            }
            final TreeItem<Customer> root = new RecursiveTreeItem<>(customers, RecursiveTreeObject::getChildren);
            tblCustomerFormList.getColumns().setAll(CustName, CustBal);
            tblCustomerFormList.setRoot(root);
            tblCustomerFormList.setShowRoot(false);
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
        cmbCustomerCategory.getItems().addAll("Name","Order No","Date");
        cmbCustomerStatus.getItems().addAll("Full Paid","Half Paid","Unpaid");
        
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
