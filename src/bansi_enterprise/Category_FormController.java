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
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author BANSIENTERPRISE
 */
public class Category_FormController implements Initializable {
   
    @FXML JFXTextField txtCategoryName;
    @FXML JFXTextField txtCategoryDescription;
    
    @FXML JFXTreeTableView tblCategoryHistory;
    
    @FXML JFXButton btnCategoryAdd;
    
    @FXML JFXRadioButton rdNew;
    @FXML JFXRadioButton rdUpdate;
    @FXML JFXRadioButton rdDelete;
    
    @FXML JFXComboBox cmbSelect;
    
    String CategoryName=null;
    int CategoryId=0;
    
    @FXML private void AddCategory(Event event){
        try {
            if(btnCategoryAdd.getText().equals("Add")){    
                if (!txtCategoryName.getText().equals("")){
                    tableview();

                    String name=txtCategoryName.getText();
                    String description=txtCategoryDescription.getText();
                    int rs1=stmt.executeUpdate("insert into category_detail(category_name,category_description) values ('"+name+"','"+description+"');");

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

                    Stage stage = (Stage) btnCategoryAdd.getScene().getWindow();
                    stage.close();
                }
                else
                {
                    Alert dialog=new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error");
                    dialog.setContentText("Please enter value of category.\nCategory name is required.");
                    dialog.showAndWait();

                    txtCategoryName.setText(null);
                    txtCategoryDescription.setText(null);
                }
            }
            else if(btnCategoryAdd.getText().equals("Update")){
                int rs1=stmt.executeUpdate("update category_detail set category_name='"+txtCategoryName.getText()+"',category_description='"+txtCategoryDescription.getText()+"' where category_id='"+CategoryId+"'");
                tableview();
            }
            else if(btnCategoryAdd.getText().equals("Delete")){
                int rs1=stmt.executeUpdate("delete from category_detail where category_id='"+CategoryId+"'");
                tableview();
            }
        } catch (SQLException ex) {
            Alert dialog=new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setContentText("Please enter unique name.\nTry again.");
            dialog.showAndWait();

            txtCategoryName.setText(null);
            txtCategoryDescription.setText(null);
        }   
    }
 
    class User extends RecursiveTreeObject<User> {

        StringProperty CategoryName;
        StringProperty CategoryDescription;

        public User(String CatName, String CatDes) {
            this.CategoryName = new SimpleStringProperty(CatName);
            this.CategoryDescription = new SimpleStringProperty(CatDes);
        }
    }
        
    public void tableview(){
        try 
        {    
            JFXTreeTableColumn<User, String> CatName = new JFXTreeTableColumn<>("CATEGORY NAME");
            CatName.setPrefWidth(150);
            CatName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().CategoryName;
                }
            });

            JFXTreeTableColumn<User, String> CatDes = new JFXTreeTableColumn<>("CATEGORY DESCRIPTION");
            CatDes.setPrefWidth(150);
            CatDes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                    return param.getValue().getValue().CategoryDescription;
                }
             });

            rs=stmt.executeQuery("select category_name,category_description from category_detail");

            ObservableList<User> users = FXCollections.observableArrayList();
            while(rs.next()){
                users.add(new User(rs.getString(1),rs.getString(2)));
            }
            final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
            tblCategoryHistory.getColumns().setAll(CatName, CatDes);
            tblCategoryHistory.setRoot(root);
            tblCategoryHistory.setShowRoot(false);
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
            CategoryName=(String)cmbSelect.getValue();
            rs=stmt.executeQuery("select category_id,category_description from category_detail where category_name='"+CategoryName+"'");
            while(rs.next()){
                CategoryId=rs.getInt(1);
                txtCategoryName.setText(CategoryName);
                txtCategoryDescription.setText(rs.getString(2));
            }
        } catch (SQLException ex) {
        }
    }
    
    @FXML public void Radio(Event event){
        if (rdUpdate.isSelected()==true){
            cmbSelect.setVisible(true);
            btnCategoryAdd.setText("Update");
        }
        else if(rdNew.isSelected()==true){
            cmbSelect.setVisible(false);
            btnCategoryAdd.setText("Add");
        }
        else if(rdDelete.isSelected()==true){
            cmbSelect.setVisible(true);
            btnCategoryAdd.setText("Delete");
        }
        
    }
    
    public void combo(){
        try {
            rs=stmt.executeQuery("select category_name from category_detail");
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
 