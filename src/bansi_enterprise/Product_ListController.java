package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import static bansi_enterprise.H2_Db_Connection.rs;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
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
public class Product_ListController implements Initializable {
    
    @FXML JFXComboBox cmbProductListCategory;
    
    @FXML Text lblProductListCategory;
    @FXML Text lblUserName;
    
    @FXML JFXRadioButton rdInStock;
    @FXML JFXRadioButton rdOutStock;
    
    @FXML JFXTreeTableView tblProductList;
    
    String Cat;
    String Vend;
    String Uni;
    String Tax;
        
    @FXML
    private void ProductForm(Event event) throws IOException {     
    Stage newStage = new Stage();
    AnchorPane anchorPanePopup = (AnchorPane) FXMLLoader.load(getClass().getResource("Product_Form.fxml"));
    Scene scene = new Scene(anchorPanePopup);
    newStage.setTitle("Product Form");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.showAndWait(); 
    }
  
    
@FXML
private void Main(Event event) throws IOException {     
     Stage newStage = new Stage();
    AnchorPane anchorPaneProductForm = (AnchorPane) FXMLLoader.load(getClass().getResource("Main.fxml"));
    Scene scene = new Scene(anchorPaneProductForm);
    newStage.setTitle("Main Page");
    newStage.setMaximized(true);
    newStage.setScene(scene);
    newStage.showAndWait();  
}    
@FXML
    private void ComboBoxCategory(Event event) throws IOException { 
        if (cmbProductListCategory.getValue().equals("Category Name")){
            lblProductListCategory.setText("Category Name");
        }
        else if (cmbProductListCategory.getValue().equals("Item Name")){
            lblProductListCategory.setText("Item Name");    
        }
        else if (cmbProductListCategory.getValue().equals("Product Name")){
            rdInStock.setVisible(false);
            rdOutStock.setVisible(false);
            lblProductListCategory.setText("Product Name");    
        }
    }

    class Product extends RecursiveTreeObject<Product> {

        StringProperty Name;
        StringProperty Category;
        StringProperty Company;
        StringProperty Vendor;
        StringProperty Unit;
        StringProperty Tax;
        StringProperty Sales;
        StringProperty Purchase;
        StringProperty AlertPoint;
        StringProperty Description;
        
        
        public Product(String Name, String Category,String Company,String Vendor,String Unit,String Tax,String Sales,String Purchase,String AlertPoint,String Description) {
            this.Name = new SimpleStringProperty(Name);
            this.Category = new SimpleStringProperty(Category);
            this.Company = new SimpleStringProperty(Company);
            this.Vendor = new SimpleStringProperty(Vendor);
            this.Unit = new SimpleStringProperty(Unit);
            this.Tax = new SimpleStringProperty(Tax);
            this.Sales = new SimpleStringProperty(Sales);
            this.Purchase = new SimpleStringProperty(Purchase);
            this.AlertPoint = new SimpleStringProperty(AlertPoint);
            this.Description = new SimpleStringProperty(Description);
        }
    }
        
    public void tableview(){
        try 
        {    
            JFXTreeTableColumn<Product, String> ProdName = new JFXTreeTableColumn<>("PRODUCT NAME");
            ProdName.setPrefWidth(150);
            ProdName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().Name;
                }
            });

            JFXTreeTableColumn<Product, String> ProdCat = new JFXTreeTableColumn<>("PRODUCT CATEGORY");
            ProdCat.setPrefWidth(150);
            ProdCat.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().Category;
                }
             });

            JFXTreeTableColumn<Product, String> CompName = new JFXTreeTableColumn<>("COMANY NAME");
            CompName.setPrefWidth(150);
            CompName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().Name;
                }
            });

            JFXTreeTableColumn<Product, String> Ven = new JFXTreeTableColumn<>("VENDOR");
            Ven.setPrefWidth(150);
            Ven.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().Vendor;
                }
             });
            JFXTreeTableColumn<Product, String> Unit = new JFXTreeTableColumn<>("UNIT");
            Unit.setPrefWidth(150);
            Unit.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().Unit;
                }
            });

            JFXTreeTableColumn<Product, String> TaxRate = new JFXTreeTableColumn<>("TAX RATE");
            TaxRate.setPrefWidth(150);
            TaxRate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().Tax;
                }
             });
            JFXTreeTableColumn<Product, String> SalPri = new JFXTreeTableColumn<>("SALES PRICE");
            SalPri.setPrefWidth(150);
            SalPri.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().Sales;
                }
            });

            JFXTreeTableColumn<Product, String> PurPri = new JFXTreeTableColumn<>("PURCHASE PRICE");
            PurPri.setPrefWidth(150);
            PurPri.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().Purchase;
                }
             });
            JFXTreeTableColumn<Product, String> Alert = new JFXTreeTableColumn<>("ALERT POINT");
            Alert.setPrefWidth(150);
            Alert.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().AlertPoint;
                }
            });

            JFXTreeTableColumn<Product, String> ProdDes = new JFXTreeTableColumn<>("PRODUCT DESCRIPTION");
            ProdDes.setPrefWidth(150);
            ProdDes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Product, String> param) {
                    return param.getValue().getValue().Description;
                }
             });
            rs=stmt.executeQuery("select product_name,product_category_id,product_vendor_id,product_unit_id,product_tax_id,product_sales_price,product_purchase_price,product_minimum_alert_point,product_name,product_company_name,product_description from product_detail");
            
            ObservableList<Product> Products = FXCollections.observableArrayList();
            while(rs.next()){
                Products.add(new Product(rs.getString(1),Cat,Vend,Uni,Tax,rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)));
            }
            final TreeItem<Product> root = new RecursiveTreeItem<>(Products, RecursiveTreeObject::getChildren);
            tblProductList.getColumns().setAll(ProdName,ProdCat,CompName,Ven,Unit,TaxRate,SalPri,PurPri,Alert,ProdDes);
            tblProductList.setRoot(root);
            tblProductList.setShowRoot(false);
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
       
        new H2_Db_Connection().Connectivity();
        tableview();
        //Store common values in combobox
        cmbProductListCategory.getItems().addAll("Category Name","Item Name","Product Name");
        
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
