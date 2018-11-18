package bansi_enterprise;

import static bansi_enterprise.H2_Db_Connection.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author BANSIENTERPRISE
 */
public class Tax_FormController implements Initializable {
    @FXML JFXTreeTableView tblTaxHistory;
    
    @FXML JFXTextField txtTaxName;
    @FXML JFXTextField txtTaxRate;
    
    @FXML JFXButton btnTaxAdd;
    
    @FXML JFXComboBox cmbSelect;
    
    @FXML JFXRadioButton rdNew;
    @FXML JFXRadioButton rdUpdate;
    @FXML JFXRadioButton rdDelete;
    
    @FXML Text lblTaxRate;
    @FXML Text lblTaxName;
    
    String TaxName= null;
    String name=null;
    
    float rate=0;
    
    int count=0;
    int TaxId = 0;
    
      @FXML private void AddTax(Event event){
        try {
            if(btnTaxAdd.getText().equals("Add")){    
                if (!txtTaxName.getText().equals("")){
                    tableview();

                    String name=txtTaxName.getText();
                    String description=txtTaxRate.getText();
                    int rs1=stmt.executeUpdate("insert into tax_detail(tax_name,tax_rate) values ('"+name+"','"+description+"');");

                    Alert alert= new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("DONE");
                    alert.setContentText("RECORD ENTERED! ! !");
                    alert.show();

                    long mTime = System.currentTimeMillis();
                    long end = mTime + 1000; // 1 seconds 

                    while (mTime < end){
                        mTime = System.currentTimeMillis();
                    } 
                    alert.close();

                    Stage stage = (Stage) btnTaxAdd.getScene().getWindow();
                    stage.close();
                }
                else
                {
                    Alert dialog=new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error");
                    dialog.setContentText("Please enter value of tax.\nTax name and tax rate is required.");
                    dialog.showAndWait();

                    txtTaxName.setText(null);
                    txtTaxRate.setText(null);
                }
            }
            else if(btnTaxAdd.getText().equals("Update")){
                int rs1=stmt.executeUpdate("update tax_detail set tax_name='"+txtTaxName.getText()+"',tax_rate='"+txtTaxRate.getText()+"' where tax_id='"+TaxId+"'");
                tableview();
            }
            else if(btnTaxAdd.getText().equals("Delete")){
                int rs1=stmt.executeUpdate("delete from tax_detail where tax_id='"+TaxId+"'");
                tableview();
            }
        } catch (SQLException ex) {
            Alert dialog=new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setContentText("Please enter unique name.\nTry again.");
            dialog.showAndWait();

            txtTaxName.setText(null);
            txtTaxRate.setText(null);
        }   
    }
      
    class Tax extends RecursiveTreeObject<Tax> {

        StringProperty TaxName;
        StringProperty TaxRate;

        public Tax(String TaxName, String TaxRate) {
            this.TaxName = new SimpleStringProperty(TaxName);
            this.TaxRate = new SimpleStringProperty(TaxRate);
        }
    }
        
    public void tableview(){
        try 
        {    
            JFXTreeTableColumn<Tax, String> TaxName = new JFXTreeTableColumn<>("TAX NAME");
            TaxName.setPrefWidth(150);
            TaxName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Tax, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Tax, String> param) {
                    return param.getValue().getValue().TaxName;
                }
            });

            JFXTreeTableColumn<Tax, String> TaxRate = new JFXTreeTableColumn<>("TAX RATE");
            TaxRate.setPrefWidth(150);
            TaxRate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Tax, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Tax, String> param) {
                    return param.getValue().getValue().TaxRate;
                }
             });
            
            rs=stmt.executeQuery("select tax_name,tax_rate from tax_detail");

            ObservableList<Tax> taxes = FXCollections.observableArrayList();
            while(rs.next()){
                taxes.add(new Tax(rs.getString(1),rs.getString(2)));
            }
            final TreeItem<Tax> root = new RecursiveTreeItem<>(taxes, RecursiveTreeObject::getChildren);
            tblTaxHistory.getColumns().setAll(TaxName, TaxRate);
            tblTaxHistory.setRoot(root);
            tblTaxHistory.setShowRoot(false);
        } 
        catch (SQLException ex) {
            Alert dialog=new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setContentText("Entered tax detail already exist.\nPlease check then add.");
            dialog.showAndWait();
        }
    }
    
    public void comboClear(){
        cmbSelect.getItems().clear();
    }
    
    public void ComboData(){
        try {
            TaxName=(String)cmbSelect.getValue();
            rs=stmt.executeQuery("select tax_id,tax_rate from tax_detail where tax_name='"+TaxName+"'");
            while(rs.next()){
                TaxId=rs.getInt(1);
                txtTaxName.setText(TaxName);
                txtTaxRate.setText(rs.getString(2));
            }
        } catch (SQLException ex) {
        }
    }
    
    @FXML public void Radio(Event event){
        if (rdUpdate.isSelected()==true){
            cmbSelect.setVisible(true);
            btnTaxAdd.setText("Update");
            txtTaxName.setVisible(true);
            txtTaxRate.setVisible(true);
            lblTaxName.setVisible(true);
            lblTaxRate.setVisible(true);
        }
        else if(rdNew.isSelected()==true){
            cmbSelect.setVisible(false);
            btnTaxAdd.setText("Add");
            txtTaxName.setVisible(true);
            txtTaxRate.setVisible(true);
            lblTaxName.setVisible(true);
            lblTaxRate.setVisible(true);
        }
        else if(rdDelete.isSelected()==true){
            cmbSelect.setVisible(true);
            btnTaxAdd.setText("Delete");
            txtTaxName.setVisible(false);
            txtTaxRate.setVisible(false);
            lblTaxName.setVisible(false);
            lblTaxRate.setVisible(false);
        }
        
    }
   
    public void combo(){
        try {
            rs=stmt.executeQuery("select tax_name from tax_detail");
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
        new H2_Db_Connection().Connectivity();
        tableview();
    }    
    
}