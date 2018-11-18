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
public class Unit_FormController implements Initializable {
    
    @FXML JFXTextField txtUnitName;
    @FXML JFXTextField txtUnitDescription;
    
    @FXML JFXButton btnUnitAdd;
    
    @FXML JFXTreeTableView tblUnitDetail;
    
    @FXML JFXComboBox cmbSelect;
    
    @FXML JFXRadioButton rdNew;
    @FXML JFXRadioButton rdUpdate;
    @FXML JFXRadioButton rdDelete;
    
    @FXML Text lblDescription;
    @FXML Text lblName;
    
    String UnitName=null;
    int UnitId=0;
    
    @FXML private void AddUnit(Event event){
        try {
            if(btnUnitAdd.getText().equals("Add")){
                if (!txtUnitName.getText().equals("")){
                    tableview();

                    String name=txtUnitName.getText();
                    String description=txtUnitDescription.getText();
                    int rs1=stmt.executeUpdate("insert into unit_detail(unit_name,unit_description) values ('"+name+"','"+description+"');");

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

                    Stage stage = (Stage) btnUnitAdd.getScene().getWindow();
                    stage.close();
                }
                else
                {
                    Alert dialog=new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error");
                    dialog.setContentText("Please enter unit name in the text field.");
                    dialog.showAndWait();

                    txtUnitName.setText(null);
                    txtUnitDescription.setText(null);
                }
            }
            else if(btnUnitAdd.getText().equals("Update")){
                int rs1=stmt.executeUpdate("update unit_detail set unit_name='"+txtUnitName.getText()+"',unit_description='"+txtUnitDescription.getText()+"' where unit_id='"+UnitId+"'");
                tableview();
            }
            else if(btnUnitAdd.getText().equals("Delete")){
                int rs1=stmt.executeUpdate("delete from unit_detail where unit_id='"+UnitId+"'");
                tableview();
            }
        } catch (SQLException ex) {
            Alert dialog=new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setContentText("Please enter unique name.\nTry again.");
            dialog.showAndWait();

            txtUnitName.setText(null);
            txtUnitDescription.setText(null);
        }
    }
     
    class Unit extends RecursiveTreeObject<Unit> {

        StringProperty UnitName;
        StringProperty UnitDes;

        public Unit(String UnitName, String UnitDes) {
            this.UnitName = new SimpleStringProperty(UnitName);
            this.UnitDes = new SimpleStringProperty(UnitDes);
        }
    }
        
    public void tableview(){
        try 
        {    
            JFXTreeTableColumn<Unit, String> UnitName = new JFXTreeTableColumn<>("UNIT NAME");
            UnitName.setPrefWidth(150);
            UnitName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Unit, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Unit, String> param) {
                    return param.getValue().getValue().UnitName;
                }
            });

            JFXTreeTableColumn<Unit, String> UnitDes = new JFXTreeTableColumn<>("UNIT DESCRIPTION");
            UnitDes.setPrefWidth(150);
            UnitDes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Unit, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Unit, String> param) {
                    return param.getValue().getValue().UnitDes;
                }
             });

            rs=stmt.executeQuery("select unit_name,unit_description from unit_detail");

            ObservableList<Unit> units = FXCollections.observableArrayList();
            while(rs.next()){
                units.add(new Unit(rs.getString(1),rs.getString(2)));
            }
            final TreeItem<Unit> root = new RecursiveTreeItem<>(units, RecursiveTreeObject::getChildren);
            tblUnitDetail.getColumns().setAll(UnitName, UnitDes);
            tblUnitDetail.setRoot(root);
            tblUnitDetail.setShowRoot(false);
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
            UnitName=(String)cmbSelect.getValue();
            rs=stmt.executeQuery("select unit_id,unit_description from unit_detail where unit_name='"+UnitName+"'");
            while(rs.next()){
                UnitId=rs.getInt(1);
                txtUnitName.setText(UnitName);
                txtUnitDescription.setText(rs.getString(2));
            }
        } catch (SQLException ex) {
        }
    }
    
    @FXML public void Radio(Event event){
        if(rdNew.isSelected()==true){
            cmbSelect.setVisible(false);
            btnUnitAdd.setText("Add");
            txtUnitDescription.setVisible(true);
            txtUnitName.setVisible(true);
            lblDescription.setVisible(true);
            lblName.setVisible(true);
            
        }else if (rdUpdate.isSelected()==true){
            cmbSelect.setVisible(true);
            btnUnitAdd.setText("Update");
            txtUnitDescription.setVisible(true);
            txtUnitName.setVisible(true);
            lblDescription.setVisible(true);
            lblName.setVisible(true);
            
        } else if(rdDelete.isSelected()==true){
            cmbSelect.setVisible(true);
            btnUnitAdd.setText("Delete");
            txtUnitDescription.setVisible(false);
            txtUnitName.setVisible(false);
            lblDescription.setVisible(false);
            lblName.setVisible(false);
        }
        
    }
    
    public void combo(){
        try {
            rs=stmt.executeQuery("select unit_name from unit_detail");
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
        new H2_Db_Connection().Connectivity();
    }    
    
}