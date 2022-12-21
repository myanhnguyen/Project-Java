package app;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AppController implements Initializable { 
    @FXML private AnchorPane Change_Pass_AnchorPane, Menu_AnchorPane, My_Work_AnchorPane, All_Pj_AnchorPane, home_AnchorPane;
    @FXML private PasswordField old_pass_text, new_pass_text, cf_pass_text;
    @FXML private Label old_pass_label, new_pass_label, cf_pass_label, user, message_label, id_label, percent_label;   
    @FXML private TableView<Bugs> user_table, bugs_table_view;
    @FXML private TableColumn<Bugs, String> id_col_user_table, pj_col_user_table, mo_ta_col_user_table, status_col_user_table;
    @FXML private TableColumn<Bugs, String> phan_loai_col_user_table, nghiem_trong_col_user_table, dev_col_user_table;
    @FXML private TableColumn<Bugs, String> uu_tien_col_user_table, start_date_col_user_table, due_date_col_user_table;
    @FXML private TextField name_text, email_text, username_text, project_text, id_text, search;
    @FXML private ComboBox do_nghiem_trong_box, do_uu_tien_box, phan_loai_box, project_box, dev_box;
    @FXML private TableColumn<Bugs, String> id_col, project_col, mo_ta_col, status_col, phan_loai_col, dev_col;
    @FXML private TableColumn<Bugs, String> do_nghiem_trong_col, do_uu_tien_col, start_date_col, due_date_col;
    @FXML private TextArea mo_ta_text;
    @FXML private DatePicker due_date_date;
    @FXML private PieChart pie_chart;
    @FXML private LineChart<String, Number> line_chart;  
  
    private final ObservableList<Bugs> bugsObservableList = FXCollections.observableArrayList();
    @FXML
    private ImageView image;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        try {
            UpdateProjectBox();
            initiateTable();
            initiateLineChart();
        } catch (SQLException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        do_nghiem_trong_box.getItems().addAll("Nghiêm trọng","Cao","Trung bình","Thấp");       
        do_uu_tien_box.getItems().addAll("High","Medium","Low");                   
        phan_loai_box.getItems().addAll("Bug","Deffect","Error","Failure");    
        
        try {
            Statement statement = ((new Database()).getConnection()).createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT * FROM account");
            while(queryResult.next()) {
                String dev = queryResult.getString("username");
                dev_box.getItems().add(dev);
            }
        }catch (SQLException e) {e.printStackTrace();}
        
        initiatePieChart();
        search();
        Menu_AnchorPane.setVisible(true);
        home_AnchorPane.setVisible(true);
        My_Work_AnchorPane.setVisible(false);
        All_Pj_AnchorPane.setVisible(false);
        Change_Pass_AnchorPane.setVisible(false);
        image.setVisible(true);
    }    
    
    public void UpdateProjectBox() {
        try {
            Statement statement = ((new Database()).getConnection()).createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT DISTINCT project FROM bugs");
            while(queryResult.next()) {
                project_box.getItems().add(queryResult.getString("project"));
            }
        }catch (SQLException e) {e.printStackTrace();}
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
                username_text.setText(username);
            } 
        } catch(SQLException e) {e.printStackTrace();}
    } 
    
    @FXML
    public void homebuttonOnAction() {
        home_AnchorPane.setVisible(true);
        My_Work_AnchorPane.setVisible(false);
        All_Pj_AnchorPane.setVisible(false);
        Change_Pass_AnchorPane.setVisible(false);
    }
    
    @FXML
    public void myworkbuttonOnAction() {
        My_Work_AnchorPane.setVisible(true);
        All_Pj_AnchorPane.setVisible(false);
        Change_Pass_AnchorPane.setVisible(false);
        home_AnchorPane.setVisible(false);
    }
    
    @FXML
    public void allpjbuttonOnAction() {
        My_Work_AnchorPane.setVisible(false);
        All_Pj_AnchorPane.setVisible(true);
        Change_Pass_AnchorPane.setVisible(false);
        home_AnchorPane.setVisible(false);
    }
    
    @FXML
    public void logoutbuttonOnAction(ActionEvent event) {Main.ChangeScene(event,"Login.fxml");}
    
    @FXML
    public void changeAccButtonOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Xác nhận thay đổi thông tin");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            try {
                Statement statement = ((new Database()).getConnection()).createStatement();
                statement.executeUpdate("UPDATE account SET name = '"+ name_text.getText() +"', email = '"+ email_text.getText() +"', "
                        + "username = '"+ username_text.getText() +"' WHERE username = '"+ user.getText() +"'");
                statement.executeUpdate("UPDATE bugs SET dev = '"+ username_text.getText() +"' WHERE dev = '"+ user.getText() +"'");
                statement.executeUpdate("UPDATE bugs SET reporter = '"+ username_text.getText() +"' WHERE reporter = '"+ user.getText() +"'");
                Main.ChangeScene(event,"Login.fxml");
            } catch(SQLException e) {e.printStackTrace();} 
        }
    }
    
    @FXML
    public void changePassButtonOnAction() {
        My_Work_AnchorPane.setVisible(false);
        Menu_AnchorPane.setVisible(false);
        Change_Pass_AnchorPane.setVisible(true);
        All_Pj_AnchorPane.setVisible(false);
        home_AnchorPane.setVisible(false);
    }
    
    public int num(String statusQuery) throws Exception {
        try {
            Statement statement = (new Database()).getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM bugs WHERE status = '" + statusQuery + "'");
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {e.printStackTrace();}
        return 0;
    }
    
    public void initiatePieChart() {
        int newbug = 0, open = 0, rejected = 0, duplicate = 0, deferred = 0;
        int assigned = 0, fix = 0, re_testing = 0, closed = 0, re_opened = 0;
        
        try {
            newbug = num("New"); open = num("Open"); rejected = num("Rejected"); fix = num("Fix");
            duplicate = num("Duplicate"); deferred = num("Deferred"); assigned = num("Assigned");
            re_opened = num("Re-opened"); re_testing = num("Re-testing"); closed = num("Closed");        
        } catch (Exception e) {e.printStackTrace();}

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("New", newbug),
                new PieChart.Data("Open", open),
                new PieChart.Data("Rejected", rejected),
                new PieChart.Data("Duplicate", duplicate),
                new PieChart.Data("Deferred", deferred),
                new PieChart.Data("Assigned", assigned),
                new PieChart.Data("Fix", fix),
                new PieChart.Data("Re-testing", re_testing),
                new PieChart.Data("Closed", closed),
                new PieChart.Data("Re-opened", re_opened)          
        );
        pie_chart.setData(pieChartData);    
        
        for (final PieChart.Data data : pie_chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                percent_label.setTranslateX(e.getSceneX() - percent_label.getLayoutX());
                percent_label.setTranslateY(e.getSceneY() - percent_label.getLayoutY());
                percent_label.setText(String.valueOf(data.getPieValue() + "%"));

                //animation
                Bounds b1 = data.getNode().getBoundsInLocal();
                // Make sure pie wedge location is reset
                data.getNode().setTranslateX(0);
                data.getNode().setTranslateY(0);

                TranslateTransition tt = new TranslateTransition(Duration.millis(1500), data.getNode());

                tt.setByX((b1.getWidth()) / 2.0 + b1.getMinX());
                tt.setByY((b1.getHeight()) / 2.0 + b1.getMinY());

                tt.setAutoReverse(true);
                tt.setCycleCount(2);
                tt.play();
            });  
        }     
    }
      
    public void initiateLineChart() throws SQLException {   
        if (project_box.getValue() != null) {
            XYChart.Series series = new XYChart.Series(); 

            try {
                Statement statement = (new Database()).getConnection().createStatement();
                ResultSet r1 = statement.executeQuery("SELECT start_date, COUNT(ID) dem FROM project.bugs WHERE "
                        + "project = '" + project_box.getValue().toString() + "' GROUP BY start_date");
                while (r1.next()) {   
                   series.getData().add(new XYChart.Data(r1.getString(1), r1.getInt(2)));
                }     
            } catch (SQLException e) {e.printStackTrace();}
            //ObservableList<XYChart.Series<String, Nuber>> linechart = FXCollections.observableArrayList();
            line_chart.getData().add(series);
        }
    }
    
    @FXML
    public void ProjectBoxOnAction() throws SQLException {
        line_chart.getData().clear();
        initiateLineChart();
    }
    
    @FXML
    public void ChangeButtonOnAction() throws SQLException {
        Statement statement = ((new Database()).getConnection()).createStatement();
        String old_pass = "";
        try {            
            ResultSet result = statement.executeQuery("SELECT password FROM account WHERE username = '"+ user.getText() +"'");
            if(result.next()) {
                old_pass = result.getString("password");
            } 
        } catch(SQLException e) {e.printStackTrace();}
        
        if(!old_pass_text.getText().equals("")) {
            if(!old_pass_text.getText().equals(old_pass)) {
                old_pass_label.setText("mật khẩu sai");
            }
            else old_pass_label.setText("");
        } else old_pass_label.setText("nhập");
        
        if(!new_pass_text.getText().equals("")) {
            if (Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^_+=<>%\"]).{8,}$", new_pass_text.getText()) == false) {
                new_pass_label.setText("mật khẩu không hợp lệ");
            } else new_pass_label.setText("");
        } else new_pass_label.setText("nhập");
        
        if(!cf_pass_text.getText().equals("")) {
            if(!cf_pass_text.getText().equals(new_pass_text.getText())) {
                cf_pass_label.setText("mật khẩu không khớp");
            } else cf_pass_label.setText("");           
        } else cf_pass_label.setText("nhập");
            
        if(old_pass_text.getText().equals(old_pass)) {
            if(Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^_+=<>%\"]).{8,}$", new_pass_text.getText()) == true) {   
                if(cf_pass_text.getText().equals(new_pass_text.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Xác nhận thay đổi mật khẩu");
                    ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeYes) {
                        try {
                            statement.executeUpdate("UPDATE account SET password = '"+ new_pass_text.getText() +"' WHERE username = '"+ user.getText() +"'");
                        } catch(SQLException e) {e.printStackTrace();}
                        CancelButtonOnAction();
                    }
        }   }   }
    }
    
    @FXML
    public void CancelButtonOnAction() {
        Change_Pass_AnchorPane.setVisible(false);
        My_Work_AnchorPane.setVisible(true);
        Menu_AnchorPane.setVisible(true);
        All_Pj_AnchorPane.setVisible(false);
        home_AnchorPane.setVisible(false);
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
                if(bugs.getDue_date().toLowerCase().contains(lowerCaseFilter) ) {return true;} 
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
                return bugs.getReporter().toLowerCase().contains(lowerCaseFilter);
            });	
        });		           
        SortedList<Bugs> sortData = new SortedList<>(filterData);		
        sortData.comparatorProperty().bind(user_table.comparatorProperty());	
        user_table.setItems(sortData);
    }
    
    public void initiateTable() throws SQLException {
        bugs_table_view.setItems(bugsObservableList);
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        project_col.setCellValueFactory(new PropertyValueFactory<>("project"));
        mo_ta_col.setCellValueFactory(new PropertyValueFactory<>("mo_ta"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
        do_nghiem_trong_col.setCellValueFactory(new PropertyValueFactory<>("do_nghiem_trong"));
        do_uu_tien_col.setCellValueFactory(new PropertyValueFactory<>("do_uu_tien"));
        start_date_col.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        phan_loai_col.setCellValueFactory(new PropertyValueFactory<>("phan_loai"));
        dev_col.setCellValueFactory(new PropertyValueFactory<>("dev"));
        due_date_col.setCellValueFactory(new PropertyValueFactory<>("due_date"));
   
        user_table.setItems(bugsObservableList);
        id_col_user_table.setCellValueFactory(new PropertyValueFactory<>("id"));
        pj_col_user_table.setCellValueFactory(new PropertyValueFactory<>("project"));
        mo_ta_col_user_table.setCellValueFactory(new PropertyValueFactory<>("mo_ta"));
        status_col_user_table.setCellValueFactory(new PropertyValueFactory<>("status"));
        nghiem_trong_col_user_table.setCellValueFactory(new PropertyValueFactory<>("do_nghiem_trong"));
        uu_tien_col_user_table.setCellValueFactory(new PropertyValueFactory<>("do_uu_tien"));
        start_date_col_user_table.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        phan_loai_col_user_table.setCellValueFactory(new PropertyValueFactory<>("phan_loai"));
        due_date_col_user_table.setCellValueFactory(new PropertyValueFactory<>("due_date"));
        dev_col_user_table.setCellValueFactory(new PropertyValueFactory<>("dev"));

        //kết nối database với bugsObservableList  
        Statement statement = ((new Database()).getConnection()).createStatement();  
        try {           
            ResultSet queryResult = statement.executeQuery("SELECT * FROM bugs");
            while(queryResult.next()) {
                bugsObservableList.add(new Bugs(queryResult.getString("ID"),queryResult.getString("project"),
                    queryResult.getString("description"),queryResult.getString("status"),    
                    queryResult.getString("do_nghiem_trong"),queryResult.getString("do_uu_tien"),   
                    queryResult.getString("phan_loai"),queryResult.getString("dev"),    
                    queryResult.getString("start_date"),queryResult.getString("due_date"),
                    queryResult.getString("reporter"))            
                );    
            }
        } catch(SQLException e) {e.printStackTrace();} 
        
        //sửa trực tiếp bảng và database
        due_date_col_user_table.setCellFactory(TextFieldTableCell.forTableColumn());
        due_date_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try { 
                statement.executeUpdate("UPDATE bugs SET due_date = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
                bugsObservableList.clear();
                initiateTable();
            } catch(SQLException e) {e.printStackTrace();}
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDue_date(event.getNewValue());
        });
        
        id_col_user_table.setCellFactory(TextFieldTableCell.forTableColumn());
        id_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {               
                statement.executeUpdate("UPDATE bugs SET ID = '"+event.getNewValue()+"' WHERE ID = '"+event.getOldValue()+"'");
                bugsObservableList.clear();
                initiateTable();
            } catch(SQLException e) {e.printStackTrace();}  
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setId(event.getNewValue());
        });
        
        pj_col_user_table.setCellFactory(TextFieldTableCell.forTableColumn());
        pj_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {               
                statement.executeUpdate("UPDATE bugs SET project = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
                bugsObservableList.clear();
                initiateTable();
            } catch(SQLException e) {e.printStackTrace();}  
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProject(event.getNewValue());
        });
        
        mo_ta_col_user_table.setCellFactory(TextFieldTableCell.forTableColumn());
        mo_ta_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {              
                statement.executeUpdate("UPDATE bugs SET description = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
                bugsObservableList.clear();
                initiateTable();
            } catch(SQLException e) {e.printStackTrace();} 
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setMo_ta(event.getNewValue());
        });
        
        dev_col_user_table.setCellFactory(ComboBoxTableCell.forTableColumn(dev_box.getItems()));
        dev_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {     
                statement.executeUpdate("UPDATE bugs SET dev = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
                bugsObservableList.clear();
                initiateTable();
            } catch(SQLException e) {e.printStackTrace();}   
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDev(event.getNewValue());
        });
        
        status_col_user_table.setCellFactory(ComboBoxTableCell.forTableColumn("New","Open","Rejected","Duplicate",
                "Deferred","Assigned","Fix","Re-testing","Closed","Re-opened"));
        status_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {       
                statement.executeUpdate("UPDATE bugs SET status = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
                initiatePieChart();
                bugsObservableList.clear();
                initiateTable();
            } catch(SQLException e) {e.printStackTrace();} 
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setStatus(event.getNewValue());
        });
        
        nghiem_trong_col_user_table.setCellFactory(ComboBoxTableCell.forTableColumn("Nghiêm trọng","Cao","Trung bình","Thấp"));
        nghiem_trong_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {               
                statement.executeUpdate("UPDATE bugs SET do_nghiem_trong = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
                bugsObservableList.clear();
                initiateTable();
            } catch(SQLException e) {e.printStackTrace();} 
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDo_nghiem_trong(event.getNewValue());
        });
        
        uu_tien_col_user_table.setCellFactory(ComboBoxTableCell.forTableColumn("High","Medium","Low"));
        uu_tien_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {           
                statement.executeUpdate("UPDATE bugs SET do_uu_tien = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
                bugsObservableList.clear();
                initiateTable();
            } catch(SQLException e) {e.printStackTrace();}
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDo_uu_tien(event.getNewValue());
        });
        
        phan_loai_col_user_table.setCellFactory(ComboBoxTableCell.forTableColumn("Bug","Deffect","Error","Failure"));
        phan_loai_col_user_table.setOnEditCommit((TableColumn.CellEditEvent<Bugs, String> event) -> {
            try {               
                statement.executeUpdate("UPDATE bugs SET phan_loai = '"+event.getNewValue()+"' WHERE ID = '"+event.getRowValue().getId()+"'");
                bugsObservableList.clear();
                initiateTable();
            } catch(SQLException e) {e.printStackTrace();}
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPhan_loai(event.getNewValue());
        });
    }
    
    @FXML
    public void deleteRow() {
        Bugs selected = user_table.getSelectionModel().getSelectedItem();
        bugsObservableList.remove(selected);
        try {
            ((new Database()).getConnection()).createStatement().executeUpdate("DELETE FROM bugs WHERE ID = '"+ selected.getId() +"'");
            ProjectBoxOnAction();
            initiatePieChart();
            project_box.getItems().clear();
            UpdateProjectBox();
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
        
        if (!id_text.getText().equals("") && !id_text.getText().equals(trùng_id)) {
            if (dev_box.getValue() != null) {
                if (!project_text.getText().equals("")) {  
                    if (do_nghiem_trong_box.getValue() != null) {
                        if (do_uu_tien_box.getValue() != null) {
                            if (phan_loai_box.getValue() != null) {                                
                                if (due_date_date.getValue() != null) {
                                    String dueDate = due_date_date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                    String startdate = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
                                    PreparedStatement thêm_vào_database = connectDB.prepareStatement("INSERT INTO bugs VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                                    thêm_vào_database.setString(1, id_text.getText());
                                    thêm_vào_database.setString(2, project_text.getText());
                                    thêm_vào_database.setString(3, mo_ta_text.getText());
                                    thêm_vào_database.setString(4, "New");
                                    thêm_vào_database.setString(5, do_nghiem_trong_box.getValue().toString());
                                    thêm_vào_database.setString(6, do_uu_tien_box.getValue().toString());
                                    thêm_vào_database.setString(7, phan_loai_box.getValue().toString());
                                    thêm_vào_database.setString(8, dev_box.getValue().toString());
                                    thêm_vào_database.setString(9, dueDate);
                                    thêm_vào_database.setString(10, user.getText());
                                    thêm_vào_database.setString(11, startdate);
                                    try {
                                        Bugs newbug = new Bugs();
                                        newbug.setId(id_text.getText());
                                        newbug.setProject(project_text.getText());
                                        newbug.setDev(dev_box.getValue().toString());
                                        newbug.setMo_ta(mo_ta_text.getText());
                                        newbug.setDo_nghiem_trong(do_nghiem_trong_box.getValue().toString());
                                        newbug.setDo_uu_tien(do_uu_tien_box.getValue().toString());
                                        newbug.setPhan_loai(phan_loai_box.getValue().toString());
                                        newbug.setStatus("New");
                                        newbug.setDue_date(dueDate);
                                        newbug.setStart_date(startdate);
                                        newbug.setReporter(user.getText());
                                        bugsObservableList.add(newbug);
                                        message_label.setText("Lỗi đã được ghi nhận");    
                                        thêm_vào_database.executeUpdate();
                                        initiatePieChart(); 
                                        ProjectBoxOnAction();
                                        project_box.getItems().clear();
                                        UpdateProjectBox();
                                        id_text.setText("");
                                        id_label.setText("");
                                        project_text.setText("");
                                    } catch(SQLException e) {e.printStackTrace();}                                   
                }   }   }   }   }    
            } else {id_label.setText("trùng");} 
        }
    }
}
