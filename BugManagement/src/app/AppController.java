package app;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

public class AppController implements Initializable {
    @FXML private TableView<Bugs> bugs_table_view;    
    @FXML private TableColumn<Bugs, String> project_col, mo_ta_col, status_col, do_nghiem_trong_col, do_uu_tien_col;  
    @FXML private TableColumn<Bugs, String> phan_loai_col, dev_col, due_date_col, id_col, start_date_col;  
    @FXML private Button add_new_bt, logoutbutton;       
    @FXML private TextField project_text,dev_text,id_text;
    @FXML private TextArea mo_ta_text;   
    @FXML private DatePicker due_date_date;  
    @FXML private Label message_label;  
    @FXML private ComboBox do_nghiem_trong_box,do_uu_tien_box,phan_loai_box;
    @FXML private Label id_label;  
    @FXML private TableView<Bugs> user_table;
    @FXML private TableColumn<Bugs, String> id_col_user_table, pj_col_user_table, mo_ta_col_user_table;
    @FXML private TableColumn<Bugs, String> phan_loai_col_user_table, nghiem_trong_col_user_table, uu_tien_col_user_table;
    @FXML private TableColumn<Bugs, String> due_date_col_user_table, start_date_col_user_table, status_col_user_table, dev_col_user_table;
    @FXML private TextField search, user;
    @FXML private Button change_acc_bt, change_pass_bt;
    @FXML private Button my_work_bt, all_pj_bt, home_bt;
    @FXML private AnchorPane My_Work_AnchorPane, All_Pj_AnchorPane;

    private final ObservableList<Bugs> bugsObservableList = FXCollections.observableArrayList();
    @FXML
    private TextField name_text;
    @FXML
    private TextField email_text;
    @FXML
    private PasswordField old_pass_text;
    @FXML
    private PasswordField new_pass_text;
    @FXML
    private PasswordField cf_pass_text;
    @FXML
    private Label old_pass_label;
    @FXML
    private Label new_pass_label;
    @FXML
    private Label cf_pass_label;
    @FXML
    private Button change_bt;
    @FXML
    private Button cancel_bt;
    @FXML
    private AnchorPane Change_Pass_AnchorPane;
    @FXML
    private AnchorPane Menu_AnchorPane;
    @FXML
    private Label mess_label;
    @FXML
    private Label done_label;
    ArrayList dev = new ArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initiateTable();
        search();
        My_Work_AnchorPane.setVisible(true);
        All_Pj_AnchorPane.setVisible(false);
        Change_Pass_AnchorPane.setVisible(false);
    }    
    
    public void set(String username) throws SQLException {
        user.setText(username);
        try {    
            Statement statement = ((new Database()).getConnection()).createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT * FROM account WHERE username = '"+ user.getText() +"'");
            if(queryResult.next()) {
                String name = queryResult.getString("name");
                String email = queryResult.getString("email");
                name_text.setText(name);
                email_text.setText(email);
            } 
        } catch(SQLException e) {e.printStackTrace();}
    } 
    
    @FXML
    public void myworkbuttonOnAction() {
        My_Work_AnchorPane.setVisible(true);
        All_Pj_AnchorPane.setVisible(false);
        Change_Pass_AnchorPane.setVisible(false);
    }
    
    @FXML
    public void allpjbuttonOnAction() {
        My_Work_AnchorPane.setVisible(false);
        All_Pj_AnchorPane.setVisible(true);
        Change_Pass_AnchorPane.setVisible(false);
    }
    
    @FXML
    public void logoutbuttonOnAction(ActionEvent event) {Main.ChangeScene(event,"Login.fxml");}
    
    @FXML
    public void changeAccButtonOnAction() {
        try {
            Statement statement = ((new Database()).getConnection()).createStatement();
            statement.executeUpdate("UPDATE account SET name = '"+ name_text.getText() +"', email = '"+ email_text.getText() +"'"
                                        + "WHERE username = '"+ user.getText() +"'");
            done_label.setText("Thay đổi thông tin thành công");
        } catch(SQLException e) {e.printStackTrace();}
    }
    
    @FXML
    public void changePassButtonOnAction() {
        My_Work_AnchorPane.setVisible(false);
        Menu_AnchorPane.setDisable(true);
        Change_Pass_AnchorPane.setVisible(true);
    }
    
    @FXML
    public void ChangeButtonOnAction() {
        String old_pass = "";
        try {    
            Statement statement = ((new Database()).getConnection()).createStatement();
            ResultSet result = statement.executeQuery("SELECT password FROM account WHERE username = '"+ user.getText() +"'");
            if(result.next()) {
                old_pass = result.getString("password");
            } 
        } catch(SQLException e) {e.printStackTrace();}
        
        if(!old_pass_text.getText().equals("")) {
            if(!old_pass_text.getText().equals(old_pass)) {old_pass_label.setText("mật khẩu sai");}
        } else {
            old_pass_label.setText("nhập");
        }
        
        if(!new_pass_text.getText().equals("")) {
            if(new_pass_text.getText().length() < 8) {new_pass_label.setText("mật khẩu quá ngắn");}
        } else {
            new_pass_label.setText("nhập");
        }
        
        if(!cf_pass_text.getText().equals("")) {
            if(!cf_pass_text.getText().equals(new_pass_text.getText())) {cf_pass_label.setText("mật khẩu không khớp");}
        } else {
            cf_pass_label.setText("nhập");
        }
            
        if(old_pass_text.getText().equals(old_pass)) {
            if(new_pass_text.getText().length() >= 8) {   
                if(cf_pass_text.getText().equals(new_pass_text.getText())) {
                    try {
                        Statement statement = ((new Database()).getConnection()).createStatement();
                        statement.executeUpdate("UPDATE account SET password = '"+ new_pass_text.getText() +"' WHERE username = '"+ user.getText() +"'");
                    } catch(SQLException e) {e.printStackTrace();}
                    mess_label.setText("Thay đổi mật khẩu thành công");
        }   }   }
    }
    
    @FXML
    public void CancelButtonOnAction() {
        Change_Pass_AnchorPane.setVisible(false);
        My_Work_AnchorPane.setVisible(true);
        Menu_AnchorPane.setDisable(false);
    }

    public void search() {
        // bugs table
        FilteredList<Bugs> filteredData = new FilteredList<>(bugsObservableList, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(bugs -> {							
                if(newValue == null || newValue.isEmpty()) {return true;}
                String lowerCaseFilter = newValue.toLowerCase();
                if(bugs.getId().toLowerCase().contains(lowerCaseFilter) ) {return true;} 
                if(bugs.getDo_nghiem_trong().toLowerCase().contains(lowerCaseFilter) ) {return true;}
                if(bugs.getDo_uu_tien().toLowerCase().contains(lowerCaseFilter) ) {return true;}
                if(bugs.getMo_ta().toLowerCase().contains(lowerCaseFilter) ) {return true;}
                if(bugs.getPhan_loai().toLowerCase().contains(lowerCaseFilter) ) {return true;}
                if(bugs.getStatus().toLowerCase().contains(lowerCaseFilter) ) {return true;} 
                if(bugs.getStart_date().toLowerCase().contains(lowerCaseFilter) ) {return true;} 
                else return bugs.getProject().toLowerCase().contains(lowerCaseFilter);
            });	
        });		
        SortedList<Bugs> sortedData = new SortedList<>(filteredData);		
        sortedData.comparatorProperty().bind(bugs_table_view.comparatorProperty());	
        bugs_table_view.setItems(sortedData); 
            
        // user table
        FilteredList<Bugs> filterData = new FilteredList<>(bugsObservableList, b -> true);
        user.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(bugs -> {							
                String lowerCaseFilter = newValue.toLowerCase();
                if(bugs.getDev().toLowerCase().contains(lowerCaseFilter) ) {return true;}
                return bugs.getPerson().toLowerCase().contains(lowerCaseFilter);
            });	
        });		           
        SortedList<Bugs> sortData = new SortedList<>(filterData);		
        sortData.comparatorProperty().bind(user_table.comparatorProperty());	
        user_table.setItems(sortData);
    }
    
    public void initiateTable() {
        bugs_table_view.setItems(bugsObservableList);
        id_col.setCellValueFactory(data -> data.getValue().idProperty());
        project_col.setCellValueFactory(data -> data.getValue().projectProperty());
        mo_ta_col.setCellValueFactory(data -> data.getValue().mo_taProperty());
        status_col.setCellValueFactory(data -> data.getValue().statusProperty());
        do_nghiem_trong_col.setCellValueFactory(data -> data.getValue().do_nghiem_trongProperty());
        do_uu_tien_col.setCellValueFactory(data -> data.getValue().do_uu_tienProperty());
        start_date_col.setCellValueFactory(data -> data.getValue().start_dateProperty());
        phan_loai_col.setCellValueFactory(data -> data.getValue().phan_loaiProperty());
        dev_col.setCellValueFactory(data -> data.getValue().devProperty());
        due_date_col.setCellValueFactory(data -> data.getValue().due_dateProperty());
        
        do_nghiem_trong_box.getItems().addAll("Nghiêm trọng","Cao","Trung bình","Thấp");       
        do_uu_tien_box.getItems().addAll("High","Medium","Low");                   
        phan_loai_box.getItems().addAll("Bug","Deffect","Error","Failure");
      
        /*try {    
            Statement statement = ((new Database()).getConnection()).createStatement();
            ResultSet result = statement.executeQuery("SELECT username FROM account");
            while(result.next()) {
    
                dev.add(result.getString("username"));
                          
            } 
        } catch(SQLException e) {e.printStackTrace();}
        TextFields.bindAutoCompletion(dev_text, dev); 
*/
       
        
        user_table.setItems(bugsObservableList);
        id_col_user_table.setCellValueFactory(data -> data.getValue().idProperty());
        pj_col_user_table.setCellValueFactory(data -> data.getValue().projectProperty());
        mo_ta_col_user_table.setCellValueFactory(data -> data.getValue().mo_taProperty());
        status_col_user_table.setCellValueFactory(data -> data.getValue().statusProperty());
        nghiem_trong_col_user_table.setCellValueFactory(data -> data.getValue().do_nghiem_trongProperty());
        uu_tien_col_user_table.setCellValueFactory(data -> data.getValue().do_uu_tienProperty());
        start_date_col_user_table.setCellValueFactory(data -> data.getValue().start_dateProperty());
        phan_loai_col_user_table.setCellValueFactory(data -> data.getValue().phan_loaiProperty());
        due_date_col_user_table.setCellValueFactory(data -> data.getValue().due_dateProperty());
        dev_col_user_table.setCellValueFactory(data -> data.getValue().devProperty());

        //Make connection to database and get selected data
        try {
            Statement statement = ((new Database()).getConnection()).createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT * FROM bugs");

            while(queryResult.next()) {
                bugsObservableList.add(new Bugs(queryResult.getString("ID"),queryResult.getString("project"),
                    queryResult.getString("description"),queryResult.getString("status"),    
                    queryResult.getString("do_nghiem_trong"),queryResult.getString("do_uu_tien"),   
                    queryResult.getString("phan_loai"),queryResult.getString("dev"),    
                    queryResult.getString("start_date"),queryResult.getString("due_date"),
                    queryResult.getString("person"))            
                );    
            }
        } catch(SQLException e) {e.printStackTrace();} 
        
        //sửa trực tiếp bảng và database
        due_date_col_user_table.setCellFactory(TextFieldTableCell.forTableColumn());
        due_date_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE bugs SET due_date = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
            } catch(SQLException e) {e.printStackTrace();}
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDue_date(event.getNewValue());
        });
        
        id_col_user_table.setCellFactory(TextFieldTableCell.forTableColumn());
        id_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE bugs SET ID = '"+event.getNewValue()+"' WHERE ID = '"+event.getOldValue()+"'");
            } catch(SQLException e) {e.printStackTrace();}  
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setId(event.getNewValue());
        });
        
        pj_col_user_table.setCellFactory(TextFieldTableCell.forTableColumn());
        pj_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE bugs SET project = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
            } catch(SQLException e) {e.printStackTrace();}  
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProject(event.getNewValue());
        });
        
        mo_ta_col_user_table.setCellFactory(TextFieldTableCell.forTableColumn());
        mo_ta_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE bugs SET description = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
            } catch(SQLException e) {e.printStackTrace();} 
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setMo_ta(event.getNewValue());
        });
        
        dev_col_user_table.setCellFactory(TextFieldTableCell.forTableColumn());
        dev_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE bugs SET dev = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
            } catch(SQLException e) {e.printStackTrace();}   
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDev(event.getNewValue());
        });
        
        status_col_user_table.setCellFactory(ComboBoxTableCell.forTableColumn("New","Open","Rejected","Duplicate",
                "Deferred","Assigned","Fix","Re-testing","Closed","Re-opened"));
        status_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE bugs SET status = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
            } catch(SQLException e) {e.printStackTrace();} 
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setStatus(event.getNewValue());
        });
        
        nghiem_trong_col_user_table.setCellFactory(ComboBoxTableCell.forTableColumn("Nghiêm trọng","Cao","Trung bình","Thấp"));
        nghiem_trong_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE bugs SET do_nghiem_trong = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
            } catch(SQLException e) {e.printStackTrace();} 
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDo_nghiem_trong(event.getNewValue());
        });
        
        uu_tien_col_user_table.setCellFactory(ComboBoxTableCell.forTableColumn("High","Medium","Low"));
        uu_tien_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE bugs SET do_uu_tien = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
            } catch(SQLException e) {e.printStackTrace();}
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDo_uu_tien(event.getNewValue());
        });
        
        phan_loai_col_user_table.setCellFactory(ComboBoxTableCell.forTableColumn("Bug","Deffect","Error","Failure"));
        phan_loai_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE bugs SET phan_loai = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
            } catch(SQLException e) {e.printStackTrace();}
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPhan_loai(event.getNewValue());
        });
    }
    
    @FXML
    public void deleteRow() {
        Bugs selected = user_table.getSelectionModel().getSelectedItem();
        bugsObservableList.remove(selected);
        try {
            Statement statement = ((new Database()).getConnection()).createStatement();
            statement.executeUpdate("DELETE FROM bugs WHERE ID = '"+ selected.getId() +"'");
        } catch(SQLException e) {e.printStackTrace();}   
    }
    
    @FXML
    public void addnewbuttonOnAction() throws SQLException {
        Connection connectDB = (new Database()).getConnection();
        
        String trùng_id = "";
        try {
            ResultSet r1 = connectDB.createStatement().executeQuery("SELECT id FROM bugs WHERE id = '" + id_text.getText() + "'");
            if(r1.next()) {
                trùng_id =  r1.getString("id");
            }
        } catch(SQLException e) {e.printStackTrace();}
        
        if (!id_text.getText().equals("")) {
            if (!id_text.getText().equals(trùng_id)) {
                if (!project_text.getText().equals("")) {  
                    if (do_nghiem_trong_box.getValue() != null) {
                        if (do_uu_tien_box.getValue() != null) {
                            if (phan_loai_box.getValue() != null) {                                
                                if (due_date_date.getValue() != null) {
                                    PreparedStatement thêm_vào_database = connectDB.prepareStatement("INSERT INTO bugs VALUES (?,?,?,?,?,?,?,?,?,?,CURRENT_DATE)");
                                    thêm_vào_database.setString(1, id_text.getText());
                                    thêm_vào_database.setString(2, project_text.getText());
                                    thêm_vào_database.setString(3, mo_ta_text.getText());
                                    thêm_vào_database.setString(4, "New");
                                    thêm_vào_database.setString(5, do_nghiem_trong_box.getValue().toString());
                                    thêm_vào_database.setString(6, do_uu_tien_box.getValue().toString());
                                    thêm_vào_database.setString(7, phan_loai_box.getValue().toString());
                                    thêm_vào_database.setString(8, dev_text.getText());
                                    thêm_vào_database.setString(9, due_date_date.getValue().toString());
                                    thêm_vào_database.setString(10, user.getText());
                                    String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
                                    try {
                                        thêm_vào_database.executeUpdate();
                                        Bugs newbug = new Bugs();
                                        newbug.setId(id_text.getText());
                                        newbug.setProject(project_text.getText());
                                        newbug.setDev(dev_text.getText());
                                        newbug.setMo_ta(mo_ta_text.getText());
                                        newbug.setDo_nghiem_trong(do_nghiem_trong_box.getValue().toString());
                                        newbug.setDo_uu_tien(do_uu_tien_box.getValue().toString());
                                        newbug.setPhan_loai(phan_loai_box.getValue().toString());
                                        newbug.setStatus("New");
                                        newbug.setDue_date(due_date_date.getValue().toString());
                                        newbug.setStart_date(date);
                                        newbug.setPerson(user.getText());
                                        bugsObservableList.add(newbug);
                                        message_label.setText("Lỗi đã được ghi nhận");
                                    } catch(SQLException e) {e.printStackTrace();}                                                  
                }   }   }   }   }    
            } else {id_label.setText("trùng");} 
        }   
    }
}