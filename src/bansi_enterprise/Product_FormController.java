package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
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
public class Product_FormController implements Initializable {
    @FXML JFXComboBox cmbCategoryName;
    @FXML JFXComboBox cmbVendorName;
    @FXML JFXComboBox cmbProductUnit;
    @FXML JFXComboBox cmbTaxRate;
    @FXML JFXComboBox cmbProductCategory;
    @FXML JFXComboBox cmbSelect;
    
    @FXML JFXTextField  txtProductCategory;
    @FXML JFXTextField  txtItemName;
    @FXML JFXTextField  txtCompanyName;
    @FXML JFXTextField  txtSalesPrice;
    @FXML JFXTextField  txtPurchasePrice;
    @FXML JFXTextField  txtAlertPoint;
    @FXML JFXTextField  txtProductDescription;
    
    @FXML Text lblProductCategory;
    @FXML Text lblUserName;
    
    @FXML JFXRadioButton rdInStock;
    @FXML JFXRadioButton rdOutStock;
    @FXML JFXRadioButton rdUpdate;
    @FXML JFXRadioButton rdNew;
    
    @FXML JFXTreeTableView tblProductHistory;
    
    int ProductId=0;
    int VendorId = 0;
    int TaxId = 0;
    int UnitId = 0;
    int CategoryId = 0;
    
    String ItemName=null;
    
    @FXML
    private void ProductList(Event event) throws IOException {     
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Product_List.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Product List");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.showAndWait();
    }
    
     @FXML Text navigationhome;
     @FXML
    private void navigationHome(Event event) throws IOException {     
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Main Form");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.showAndWait();
    
     Stage stage = (Stage) navigationhome.getScene().getWindow();
     stage.close();  
     
    }
    
     @FXML Text navigationpro;
     @FXML
    private void navigationProductForm (Event event) throws IOException {     
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Product_Form.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setTitle("Product Form");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.showAndWait();
    
     Stage stage = (Stage)navigationpro.getScene().getWindow();
     stage.close();  
     
    }
                    
    //Add new Category Detail in Database (Button Event)[IF NOT EXIST]
    @FXML 
    private void CategoryName(Event event) throws IOException {
        Stage newStage = new Stage();
        AnchorPane anchorPaneVendorForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Category_Form.fxml"));
        Scene scene = new Scene(anchorPaneVendorForm);
        newStage.setTitle("Category List"); 
        newStage.setResizable(false);
        newStage.setHeight(400);
        newStage.setWidth(500);
        newStage.setScene(scene); 
        newStage.showAndWait();
    }
    
    //Add new Unit Detail in Database (Button Event)[IF NOT EXIST]
    @FXML 
    private void UnitName(Event event) throws IOException {
        Stage newStage = new Stage();
        AnchorPane anchorPaneVendorForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Unit_Form.fxml"));
        Scene scene = new Scene(anchorPaneVendorForm);
        newStage.setTitle("Unit List");
        newStage.setResizable(false);
        newStage.setHeight(400);
        newStage.setWidth(500);
        newStage.setScene(scene);
        newStage.showAndWait(); 
    }
    
    //Add new Vendor Detail in Database (Button Event)[IF NOT EXIST]
    @FXML 
    private void VendorName(Event event) throws IOException {
        Stage newStage = new Stage();
        AnchorPane anchorPaneVendorForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Vendor_Form.fxml"));
        Scene scene = new Scene(anchorPaneVendorForm);
        newStage.setTitle("Vendor Form");
        newStage.setMaximized(true);
        newStage.setScene(scene);
        newStage.showAndWait(); 
    }
    
    //Add new Tax Detail in Database (Button Event)[IF NOT EXIST]
    @FXML 
    private void TaxRate(Event event) throws IOException {
        Stage newStage = new Stage();
        AnchorPane anchorPaneVendorForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Tax_Form.fxml"));
        Scene scene = new Scene(anchorPaneVendorForm);
        newStage.setResizable(false);
        newStage.setHeight(400);
        newStage.setWidth(500);
        newStage.setTitle("Tax Form");
        newStage.setScene(scene);
        newStage.showAndWait(); 
    }
    
    //Insert Button Event (Button Event)[FOR ADD NEW PRODUCT]
    @FXML private void InsertProduct(Event event){
        if(txtItemName .getText().equals("") || cmbCategoryName .getValue().equals("") || txtCompanyName .getText().equals("") || cmbVendorName .getValue().equals("") || cmbProductUnit .getValue().equals("")){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Fields should not be empty.");
             alert.setContentText("Please feel up all required fields.\nTry again.");
             alert.showAndWait();
                
            }
        else
        {
            try {

                //Get Vendor id using Combobox Selected item
                String VendorName=(String)cmbVendorName.getValue();
                rs=stmt.executeQuery("select vendor_id from vendor_detail where vendor_name='"+VendorName+"';");
                while (rs.next()){VendorId=rs.getInt(1);}

                //Get Tax id using Combobox Selected item
                float TaxRate=Float.parseFloat((String) cmbTaxRate.getValue());
                rs=stmt.executeQuery("select tax_id from tax_detail where tax_rate="+TaxRate+";");
                while (rs.next()){TaxId=rs.getInt(1);}

                //Get Unit id using Combobox Selected item
                String UnitName=(String)cmbProductUnit.getValue();
                rs=stmt.executeQuery("select unit_id from unit_detail where unit_name='"+UnitName+"';");
                while (rs.next()){UnitId=rs.getInt(1);}

                //Get Category id using Combobox Selected item
                String CategoryName=(String)cmbCategoryName.getValue();
                rs=stmt.executeQuery("select category_id from category_detail where category_name='"+CategoryName+"';");
                while (rs.next()){CategoryId=rs.getInt(1);}

                //Get values from textField
                String ItemName=txtItemName.getText();
                String CompanyName=txtCompanyName.getText();
                String Description=txtProductDescription.getText();
                int SalesPrice=Integer.parseInt(txtSalesPrice.getText());
                int PurchasePrice=Integer.parseInt(txtPurchasePrice.getText());
                int AlertPoint=Integer.parseInt(txtAlertPoint.getText());

                //Fire the insert query for stor data in database
                int rs1=stmt.executeUpdate("insert into product_detail (product_category_id,product_vendor_id,product_unit_id,product_tax_id,product_sales_price,product_purchase_price,product_minimum_alert_point,product_name,product_company_name,product_description)values ('"+CategoryId+"','"+VendorId+"','"+UnitId+"','"+TaxId+"','"+SalesPrice+"','"+PurchasePrice+"','"+AlertPoint+"','"+ItemName+"','"+CompanyName+"','"+Description+"');");
                tableview();
            }
            catch (SQLException ex) {
                    Alert dialog=new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error");
                    dialog.setContentText("Please valid value.\nTry again.");
                    dialog.showAndWait();
                    
                    txtItemName.setText(null);
                    txtAlertPoint.setText(null);
                    txtCompanyName.setText(null);
                    txtSalesPrice.setText(null);       
                    txtPurchasePrice.setText(null);
            }
        }
    }
    
    //Update Button Event (Button Event)[FOR ADD NEW PRODUCT]
    @FXML private void UpdateProduct(Event event){
        if(txtItemName .getText().equals("") || cmbCategoryName .getValue().equals("") || txtCompanyName .getText().equals("") || cmbVendorName .getValue().equals("") || cmbProductUnit .getValue().equals("")){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Fields should not be empty.");
             alert.setContentText("Please feel up all required fields.\nTry again.");
             alert.showAndWait();
                
            }
        else
        {
            try {
                
                //Get Vendor id using Combobox Selected item
                String VendorName=(String)cmbVendorName.getValue();
                rs=stmt.executeQuery("select vendor_id from vendor_detail where vendor_name='"+VendorName+"';");
                while (rs.next()){VendorId=rs.getInt(1);}

                //Get Tax id using Combobox Selected item
                String TaxRate=((String)cmbTaxRate.getValue());
                rs=stmt.executeQuery("select tax_id from tax_detail where tax_name='"+TaxRate+"';");
                while (rs.next()){TaxId=rs.getInt(1);}

                //Get Unit id using Combobox Selected item
                String UnitName=(String)cmbProductUnit.getValue();
                rs=stmt.executeQuery("select unit_id from unit_detail where unit_name='"+UnitName+"';");
                while (rs.next()){UnitId=rs.getInt(1);}

                //Get Category id using Combobox Selected item
                String CategoryName=(String)cmbCategoryName.getValue();
                rs=stmt.executeQuery("select category_id from category_detail where category_name='"+CategoryName+"';");
                while (rs.next()){CategoryId=rs.getInt(1);}

                //Get values from textField
                String ItemName=txtItemName.getText();
                String CompanyName=txtCompanyName.getText();
                String Description=txtProductDescription.getText();
                int SalesPrice=Integer.parseInt(txtSalesPrice.getText());
                int PurchasePrice=Integer.parseInt(txtPurchasePrice.getText());
                int AlertPoint=Integer.parseInt(txtAlertPoint.getText());

                //Fire the insert query for stor data in database
               //int rs1=stmt.executeUpdate("update from product_detail set PRODUCT_VENDOR_ID='"+VendorId+"',PRODUCT_UNIT_ID='"+UnitId+"',PRODUCT_TAX_ID='"+TaxId+"',PRODUCT_SALES_PRICE='"+SalesPrice+"',PRODUCT_PURCHASE_PRICE='"+PurchasePrice+"',PRODUCT_MINIMUM_ALERT_POINT='"+AlertPoint+"',PRODUCT_NAME='"+ItemName+"',PRODUCT_COMPANY_NAME='"+CompanyName+"',PRODUCT_DESCRIPTION=’"+Description+"’ where product_id=’"+ProductId+"’");
            }
            catch (SQLException ex) {
                Alert dialog=new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Error");
                dialog.setContentText("Enter or select valid values.\nTry again.");
                dialog.showAndWait();

                txtItemName.setText(null);
                txtAlertPoint.setText(null);
                txtCompanyName.setText(null);
                txtSalesPrice.setText(null);
                txtPurchasePrice.setText(null);       
            }

        }
    }
    
    @FXML
    private void ComboBoxCategory(Event event) throws IOException { 
        if (cmbProductCategory.getValue().equals("Category Name")){
            lblProductCategory.setText("Category Name");
             rdInStock.setVisible(true);
            rdOutStock.setVisible(true);
        }
        else if (cmbProductCategory.getValue().equals("Item Name")){
            lblProductCategory.setText("Item Name");  
             rdInStock.setVisible(true);
            rdOutStock.setVisible(true);
        }
        else if (cmbProductCategory.getValue().equals("Vendor Name")){
            rdInStock.setVisible(false);
            rdOutStock.setVisible(false);
            lblProductCategory.setText("Vendor Name");    
        }
    }
    
    public void vendorClear(){
        cmbVendorName.getItems().clear();
    }
    public void vendor(){
        try {
            //Store combobox values(vendor_name) from databse
            rs=stmt.executeQuery("select vendor_name from vendor_detail order by vendor_name ASC");
            while(rs.next()){
                cmbVendorName.getItems().addAll(rs.getString(1));       
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     public void categoryClear(){
        cmbCategoryName.getItems().clear();
     }
    
    public void category(){
        try {
            //Store combobox values(category_name) from databse
            rs=stmt.executeQuery("select category_name from category_detail  order by category_name ASC");
            while(rs.next()){
                cmbCategoryName.getItems().addAll(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     public void unitClear(){
         cmbProductUnit.getItems().clear();
     }
    
    public void unit(){
        try {
            //Store combobox values(unit_name) from databse
            rs=stmt.executeQuery("select unit_name from unit_detail order by unit_name ASC");
            while(rs.next()){
                cmbProductUnit.getItems().addAll(rs.getString(1));       
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     public void taxClear(){
         cmbTaxRate.getItems().clear();
     }
    
    public void tax (){
        try {
            rs=stmt.executeQuery("select tax_name from tax_detail order by tax_name ASC");
            while(rs.next()){
                cmbTaxRate.getItems().addAll(rs.getString(1));
            }
        } catch (SQLException ex) {
            
        }
    }
    
    class Product extends RecursiveTreeObject<Product> {

        StringProperty ItemName;
        StringProperty ProductQuantity;

        public Product(String Item, String Quantity) {
            this.ItemName = new SimpleStringProperty(Item);
            this.ProductQuantity = new SimpleStringProperty(Quantity);
        }
    }
        
    public void tableview(){
        try 
        {    
            JFXTreeTableColumn<Product, String> Item = new JFXTreeTableColumn<>("PRODUCT");
            Item.setPrefWidth(150);
            Item.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().ItemName;
                }
            });

            JFXTreeTableColumn<Product, String> Quantity = new JFXTreeTableColumn<>("PRODUCT QUANTITY");
            Quantity.setPrefWidth(150);
            Quantity.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().ProductQuantity;
                }
             });

            rs=stmt.executeQuery("select product_name,product_quantity from product_detail");

            ObservableList<Product> products = FXCollections.observableArrayList();
            while(rs.next()){
                products.add(new Product(rs.getString(1),rs.getString(2)));
            }
            final TreeItem<Product> root = new RecursiveTreeItem<>(products, RecursiveTreeObject::getChildren);
            tblProductHistory.getColumns().setAll(Item, Quantity);
            tblProductHistory.setRoot(root);
            tblProductHistory.setShowRoot(false);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
        public void comboClear(){
        cmbSelect.getItems().clear();
    }
    
    public void ComboData(){
        try {
            ItemName=(String)cmbSelect.getValue();
            rs=stmt.executeQuery("select product_name,product_category_id,product_vendor_id,product_unit_id,product_tax_id,product_sales_price,product_purchase_price,product_minimum_alert_point,product_name,product_company_name,product_description from product_detail where product_item_name='"+ItemName+"'");
            while(rs.next()){
//                txtItemName.setText(rs.getString(1));
//                cmbCategoryName.setValue(2);
//                txtCompanyName.setText(3);
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML public void Radio(Event event){
        if (rdUpdate.isSelected()==true){
            cmbSelect.setVisible(true);
        }
        else if(rdNew.isSelected()==true){
            cmbSelect.setVisible(false);
        }
    }
   
    public void combo(){
        try {
            rs=stmt.executeQuery("select product_name from product_detail");
            while(rs.next()){
                cmbSelect.getItems().addAll(rs.getString(1));
            }
        }catch (SQLException ex) {
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        tableview();
        //Call Connectivity() function from H2_Db_Connection.java Class File to connect Database.
        new H2_Db_Connection().Connectivity();
        category();
        tax();
        unit();
        
        //Store common values in combobox
        cmbProductCategory.getItems().addAll("Category Name","Item Name","Vendor Name");

        String User=null;
        try {
            rs=stmt.executeQuery("Select user_name from current_user");
            while (rs.next()){ User=rs.getString(1); }
            lblUserName.setText(User);
        } catch (SQLException ex) {
            
        }
    }
}