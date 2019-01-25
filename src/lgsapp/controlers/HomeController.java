package lgsapp.controlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lgsapp.helpers.DbConnect;
import lgsapp.helpers.Secratary;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class HomeController implements Initializable {
    private File selectedFile =null;
    private FileInputStream fis;

    Connection con = DbConnect.getConnection();



    private String imageFile;

    //define row clicked variables
    String clickfname;
    String clicklname;
    String clickoffice;
    String clicksrchyr;

    Secratary secratary;

    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtWOP;

    @FXML
    private ComboBox cmbGender;


    @FXML
    private ComboBox cmbCuryr;

    @FXML
    private ComboBox cmbOffice;

    @FXML
    private DatePicker txtBirthday;

    @FXML
    private DatePicker txtFirstAppdate;

    @FXML
    private DatePicker txtUpgrading;

    @FXML
    private DatePicker txtRetirement;

    @FXML
    private DatePicker txtIncrement;

    @FXML
    private CheckBox chkPb;

    @FXML
    private CheckBox chkPm;

    @FXML
    private CheckBox chkPe;

    @FXML
    private RadioButton radioyes;

    @FXML
    private RadioButton radiono;

    @FXML
    private TableView<Secratary> tblData;

    @FXML
    private TableColumn<Secratary, String> colfname;

    @FXML
    private TableColumn<Secratary, String> collname;

    @FXML
    private TableColumn<Secratary, String> colwop;

    @FXML
    private TableColumn<Secratary, String> coloffice;

    @FXML
    private TableColumn<Secratary, String> colcontact;

    @FXML
    private TableColumn<Secratary, String> colbday;

    @FXML
    private TableColumn<Secratary, String> colfappdate;

    @FXML
    private TableColumn<Secratary, String> colupgdate;

    @FXML
    private TableColumn<Secratary, String> colretdate;

    @FXML
    private TableColumn<Secratary, String> colincdate;

    @FXML
    private TableColumn<Secratary, String> colinc;

    @FXML
    private TableColumn<Secratary, String> colbeg;

    @FXML
    private TableColumn<Secratary, String> colmid;

    @FXML
    private TableColumn<Secratary, String> colend;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtsearchyr;



    ObservableList<Secratary> oblist = FXCollections.observableArrayList(); //get data from model
    FilteredList<Secratary> filteredData;
    SortedList<Secratary> sortedData;




    @FXML
    private ImageView img_frame;








    @FXML
    void uploadPic(MouseEvent event) throws IOException {    //using file chooser for single fie select

        handle_load();

    }




    @FXML
    public void handle_load() throws MalformedURLException {  //to select image on hdd and set it to the image view

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif")); // limit fileChooser options to image files
        selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {

            imageFile = selectedFile.toURI().toURL().toString();
            System.out.println("the path is>>>>>>>"+imageFile+">>>>>>>");

            Image image = new Image(imageFile);
            img_frame.setImage(image);
        } else {


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select valid image File");
            alert.showAndWait();
        }

    }

    @FXML
    void addInfo(MouseEvent event) throws IOException {  // add secretary infos

        try {

            String firstname = txtFirstname.getText();
            String lastname = txtLastname.getText();
            String wop = txtWOP.getText();
            String contact = txtContact.getText();
            String email = txtEmail.getText();
            String gender = cmbGender.getSelectionModel().getSelectedItem().toString();
            String office = cmbOffice.getSelectionModel().getSelectedItem().toString();
            String curyr = cmbCuryr.getSelectionModel().getSelectedItem().toString();
            String bday = txtBirthday.getValue().toString();
            String fappdate = txtFirstAppdate.getValue().toString();
            String upgrade = txtUpgrading.getValue().toString();
            String retdate = txtRetirement.getValue().toString();
            String incdate = txtIncrement.getValue().toString();
            String chpb = getchkboxData(chkPb);
            String chpm = getchkboxData(chkPm);
            String chpe = getchkboxData(chkPe);
            String incremantal = getIncremantal();

            if(selectedFile == null){
                fis = null;
            }

            else {
                fis = new FileInputStream(selectedFile);//get the image file
            }

            boolean emailvalid = isValidEmailAddress(email); //for valid email address
            boolean valid = true;


            //form validations

            if(curyr.isEmpty()){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                String s = "Please enter the Current Year";
                alert.setContentText(s);
                alert.showAndWait();

            }

            else if(firstname.isEmpty() && lastname.isEmpty()){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                String s = "Please enter the name";
                alert.setContentText(s);
                alert.showAndWait();

            }

            else if(!email.isEmpty() && !emailvalid){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Fail");
                String s = "The Email is not valid";
                alert.setContentText(s);
                alert.showAndWait();
            }


            else if(!contact.isEmpty() && contact.length()!=10){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Fail");
                String s = "Please enter the valid phone number";
                alert.setContentText(s);
                alert.showAndWait();

            }

            else if(office.isEmpty()){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Fail");
                String s = "Please enter the Divisional office";
                alert.setContentText(s);
                alert.showAndWait();

            }





            else {

                try {

                    // secratary = new Secratary();
                    // PreparedStatement ps = secratary.addsecretary(firstname,lastname,wop,office,contact,email,gender,bday,fappdate,upgrade,retdate,incdate,incremantal,chpb,chpm,chpe,fis,curyr);

                    PreparedStatement ps = null;

                    String query = "INSERT INTO `secratary`( `fname`, `lname`, `wop`, `office`, `contact`, `email`, `gender`, `bday`, `fappdate`, `upgdate`, `retdate`, `incdate`, `salinc`, `yrbeg`, `yrmid`, `yrend`, `imageid`, `curyr`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";



                    ps = con.prepareStatement(query);
                    ps.setString(1, firstname);
                    ps.setString(2, lastname);
                    ps.setString(3, wop);
                    ps.setString(4, office);
                    ps.setString(5, contact);
                    ps.setString(6, email);
                    ps.setString(7, gender);
                    ps.setString(8, bday);
                    ps.setString(9, fappdate);
                    ps.setString(10, upgrade);
                    ps.setString(11, retdate);
                    ps.setString(12, incdate);
                    ps.setString(13, incremantal);
                    ps.setString(14, chpb);
                    ps.setString(15, chpm);
                    ps.setString(16, chpe);

                    if (selectedFile == null){
                        ps.setBinaryStream(17, null ,0);
                    }
                    else {
                        ps.setBinaryStream(17, (InputStream) fis, (int) selectedFile.length());

                    }
                    ps.setString(18, curyr);


                    if (ps.executeUpdate() > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successfully Saved");
                        String s = "Secretary Saved Successfully.";

                        alert.setContentText(s);
                        alert.showAndWait();


                        String srchyr = txtsearchyr.getText();
                        tblData.getItems().clear();
                        String searchvalue = txtSearch.getText();
                        refreshtable(srchyr,searchvalue); //fill table according to search
                        Image image = new Image("lgsapp/myicons/icons8-administrator-male-80.png");  //refresh image view
                        img_frame.setImage(image);

                        txtFirstname.setText("");
                        txtLastname.setText("");
                        txtWOP.setText("");
                        txtContact.setText("");
                        txtEmail.setText("");
                        cmbCuryr.setValue(null);
                        cmbGender.setValue(null);
                        cmbOffice.setValue(null);

                        txtBirthday.setValue(null);
                        txtFirstAppdate.setValue(null);
                        txtUpgrading.setValue(null);
                        txtRetirement.setValue(null);
                        txtIncrement.setValue(null);

                        chkPb.setSelected(false);
                        chkPm.setSelected(false);
                        chkPe.setSelected(false);


                    }
                } catch (SQLException e) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Registration Fail");
                    String s = "Already added this some informations";
                    alert.setContentText(s);
                    alert.showAndWait();
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Fail");
            String s = "Essential fields are not completed";
            alert.setContentText(s);
            alert.showAndWait();
            e.printStackTrace();
        }

    }




    public String getchkboxData(CheckBox checkBox){  ///to get the data from check box
        String value = "";
        if (checkBox.isSelected()){
            value = "yes";
        }
        else{
            value = "no";
        }
        return value;
    }

    public String getIncremantal(){  //to get the data from radio btn
        String value = "";
        if (radioyes.isSelected()){
            value = "yes";
        }
        else{
            value = "no";
        }
        return value;
    }



    public boolean isValidEmailAddress(String email) {  //for email validation
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }








    //to fill the table view
    public void refreshtable(String year,String searchval){

        String query = "SELECT  `fname`, `lname`, `wop`, `office`, `contact`, `bday`, `fappdate`, `upgdate`, `retdate`, `incdate`, `salinc`, `yrbeg`, `yrmid`, `yrend` FROM `secratary` WHERE `curyr` =? AND   (`fname`  LIKE ? OR `lname`  LIKE ? OR `office`  LIKE ?)";
        try {
            PreparedStatement  ps = con.prepareStatement(query);
            ps.setString(1,year);
            ps.setString(2, "%"+searchval+"%");
            ps.setString(3, "%"+searchval+"%");
            ps.setString(4, "%"+searchval+"%");

            ResultSet rs = ps.executeQuery();


            while (rs.next()){
                oblist.add(new Secratary(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),
                        rs.getString(12),rs.getString(13),rs.getString(14)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        colfname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        collname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        colwop.setCellValueFactory(new PropertyValueFactory<>("wop"));
        coloffice.setCellValueFactory(new PropertyValueFactory<>("office"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colbday.setCellValueFactory(new PropertyValueFactory<>("bday"));
        colfappdate.setCellValueFactory(new PropertyValueFactory<>("fappdate"));
        colupgdate.setCellValueFactory(new PropertyValueFactory<>("upgradedate"));
        colretdate.setCellValueFactory(new PropertyValueFactory<>("retdate"));
        colincdate.setCellValueFactory(new PropertyValueFactory<>("incdate"));
        colinc.setCellValueFactory(new PropertyValueFactory<>("inc"));
        colbeg.setCellValueFactory(new PropertyValueFactory<>("beg"));
        colmid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        colend.setCellValueFactory(new PropertyValueFactory<>("end"));


     //search table

        tblData.setItems(oblist); //set the oblist to table view



    }








    //update secretary information

    @FXML
    void editInfo(MouseEvent event) throws IOException {


        try {

            String firstname = txtFirstname.getText();
            String lastname = txtLastname.getText();
            String wop = txtWOP.getText();
            String contact = txtContact.getText();
            String email = txtEmail.getText();
            String gender = cmbGender.getSelectionModel().getSelectedItem().toString();
            String office = cmbOffice.getSelectionModel().getSelectedItem().toString();
            String curyr = cmbCuryr.getSelectionModel().getSelectedItem().toString();
            String bday = txtBirthday.getValue().toString();
            String fappdate = txtFirstAppdate.getValue().toString();
            String upgrade = txtUpgrading.getValue().toString();
            String retdate = txtRetirement.getValue().toString();
            String incdate = txtIncrement.getValue().toString();
            String chpb = getchkboxData(chkPb);
            String chpm = getchkboxData(chkPm);
            String chpe = getchkboxData(chkPe);
            String incremantal = getIncremantal();

            if(selectedFile == null){
                fis = null;
            }
            else {
                fis = new FileInputStream(selectedFile);//get the image file

            }

            boolean emailvalid = isValidEmailAddress(email); //for valid email address
            boolean valid = true;


            //form validations

            if(curyr.isEmpty()){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify info Error");
                String s = "Please enter the Current Year";
                alert.setContentText(s);
                alert.showAndWait();

            }

            else if(firstname.isEmpty() && lastname.isEmpty()){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify info Error");
                String s = "Please enter the name";
                alert.setContentText(s);
                alert.showAndWait();

            }

            else if(!email.isEmpty() && !emailvalid){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify info Fail");
                String s = "The Email is not valid";
                alert.setContentText(s);
                alert.showAndWait();
            }


            else if(!contact.isEmpty() && contact.length()!=10){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify info Fail");
                String s = "Please enter the valid phone number";
                alert.setContentText(s);
                alert.showAndWait();

            }

            else if(office.isEmpty()){
                valid = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify info Fail");
                String s = "Please enter the Divisional office";
                alert.setContentText(s);
                alert.showAndWait();

            }





            else {

                try {

                    // secratary = new Secratary();
                    // PreparedStatement ps = secratary.addsecretary(firstname,lastname,wop,office,contact,email,gender,bday,fappdate,upgrade,retdate,incdate,incremantal,chpb,chpm,chpe,fis,curyr);

                    PreparedStatement ps = null;

                    String query = "UPDATE `secratary` SET `fname`=?,`lname`=?,`wop`=?,`office`=?,`contact`=?,`email`=?,`gender`=?,`bday`=?,`fappdate`=?,`upgdate`=?,`retdate`=?,`incdate`=?,`salinc`=?,`yrbeg`=?,`yrmid`=?,`yrend`=?,`imageid`=?,`curyr`=? WHERE `fname`=? AND `lname` = ? AND  `office` =? AND `curyr` =?";



                    ps = con.prepareStatement(query);
                    ps.setString(1, firstname);
                    ps.setString(2, lastname);
                    ps.setString(3, wop);
                    ps.setString(4, office);
                    ps.setString(5, contact);
                    ps.setString(6, email);
                    ps.setString(7, gender);
                    ps.setString(8, bday);
                    ps.setString(9, fappdate);
                    ps.setString(10, upgrade);
                    ps.setString(11, retdate);
                    ps.setString(12, incdate);
                    ps.setString(13, incremantal);
                    ps.setString(14, chpb);
                    ps.setString(15, chpm);
                    ps.setString(16, chpe);

                    if (selectedFile == null){
                        ps.setBinaryStream(17, null ,0);
                    }
                    else {
                        ps.setBinaryStream(17, (InputStream) fis, (int) selectedFile.length());

                    }
                    ps.setString(18, curyr);
                    ps.setString(19, clickfname);
                    ps.setString(20, clicklname);
                    ps.setString(21, clickoffice);
                    ps.setString(22, clicksrchyr);


                    if (ps.executeUpdate() > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successfully Modified");
                        String s = "Successfully Modified.";

                        alert.setContentText(s);
                        alert.showAndWait();


                        String srchyr = txtsearchyr.getText();

                        //tblData.getItems().remove(sortedData);
                       //tblData.getItems().removeAll(); //clear all early data
                        tblData.getItems().clear();
                        String searchvalue = txtSearch.getText();
                        refreshtable(srchyr,searchvalue);  //refresh the table view

                        Image image = new Image("lgsapp/myicons/icons8-administrator-male-80.png");  //refresh image view
                        img_frame.setImage(image);

                        txtFirstname.setText("");
                        txtLastname.setText("");
                        txtWOP.setText("");
                        txtContact.setText("");
                        txtEmail.setText("");
                        cmbCuryr.setValue(null);
                        cmbGender.setValue(null);
                        cmbOffice.setValue(null);

                        txtBirthday.setValue(null);
                        txtFirstAppdate.setValue(null);
                        txtUpgrading.setValue(null);
                        txtRetirement.setValue(null);
                        txtIncrement.setValue(null);

                        chkPb.setSelected(false);
                        chkPm.setSelected(false);
                        chkPe.setSelected(false);



                    }
                } catch (SQLException e) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Modify info Fail");
                    String s = "Already added this some informations";
                    alert.setContentText(s);
                    alert.showAndWait();
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify info Fail");
            String s = "Essential fields are not completed";
            alert.setContentText(s);
            alert.showAndWait();
            e.printStackTrace();
        }


    }








    //delete the selected record

    @FXML
    void deleteInfo(MouseEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //confirmation alert
        alert.setTitle("Delete Secretary");
        String s = "Are You Sure Want to delete a secretary..!";
        alert.setContentText(s);
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

            String query = "DELETE FROM `secratary` WHERE `fname` = ? AND `lname` = ? AND `office` = ? AND `curyr` = ?";
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, clickfname);
                ps.setString(2, clicklname);
                ps.setString(3, clickoffice);
                ps.setString(4, clicksrchyr);

                if (ps.executeUpdate() > 0) {


                    String srchyr = txtsearchyr.getText();
                     tblData.getItems().clear(); //clear all early data

                    String searchvalue = txtSearch.getText();
                    refreshtable(srchyr,searchvalue);

                    Image image = new Image("lgsapp/myicons/icons8-administrator-male-80.png");  //refresh image view
                    img_frame.setImage(image);

                    txtFirstname.setText("");
                    txtLastname.setText("");
                    txtWOP.setText("");
                    txtContact.setText("");
                    txtEmail.setText("");
                    cmbCuryr.setValue(null);
                    cmbGender.setValue(null);
                    cmbOffice.setValue(null);

                    txtBirthday.setValue(null);
                    txtFirstAppdate.setValue(null);
                    txtUpgrading.setValue(null);
                    txtRetirement.setValue(null);
                    txtIncrement.setValue(null);

                    chkPb.setSelected(false);
                    chkPm.setSelected(false);
                    chkPe.setSelected(false);


                }
            } catch (SQLException e) {


                e.printStackTrace();
            }

        }
    }







    @FXML
    void go(MouseEvent event) throws IOException {  //load the table

        String srchyr = txtsearchyr.getText();


        if(srchyr.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Table loading fail");
            String s = "Please enter the Year what you want view";
            alert.setContentText(s);
            alert.showAndWait();

        }
        else {

             //load again

            tblData.getItems().clear();
            String searchvalue = txtSearch.getText();
            refreshtable(srchyr,searchvalue);
        }

    }

    @FXML
    void exitTomain(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lgsapp/views/dashbord.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setFullScreen (true);

    }
    //set title bar
    double x = 0, y =0;
    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void dragged(MouseEvent event) {

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }











    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // add items to combo box
        cmbGender.getItems().addAll("Male","Female");
        cmbOffice.getItems().addAll("Galle M/C","Ambalangoda U/C","Hikkaduwa U/C","Benthota R/C","Ambalangoda R/C","Karandeniya R/C","Bope-Podala R/C","Baddegama R/C","Nagoda R/C","Balapitiya R/C","Elpitiya R/C","Imaduwa R/C","Akmeemana R/C","Niyagama R/C","Thawalama R/C","Weliwitiya Divithura R/C","Neluwa R/C","Yakkalamulla R/C","Rajgama R/C","Habaraduwa R/C");
        cmbCuryr.getItems().addAll("2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035");


        //set radio buttons
        ToggleGroup toggleGroup = new ToggleGroup();
        radioyes.setToggleGroup(toggleGroup);
        radioyes.setSelected(true);


        radiono.setToggleGroup(toggleGroup);
        radiono.setSelected(true);

        //set image view
        Image image = new Image("lgsapp/myicons/icons8-administrator-male-80.png");
        img_frame.setImage(image);



        //search table value
        filteredData = new FilteredList<>(oblist, e -> true);
        txtSearch.setOnKeyReleased(e ->{
            txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
                /*filteredData.setPredicate((Predicate<? super Secratary>) secratary ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (secratary.getFname().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    else if (secratary.getLname().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    else if (secratary.getOffice().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }

                    return false;
                });*/

                String srchyr = txtsearchyr.getText();
                tblData.getItems().clear();
               // String searchvalue = txtSearch.getText();
                refreshtable(srchyr,newValue);
            }));

/*
            sortedData = new SortedList<>(filteredData);
           // sortedData.comparatorProperty().bind(tblData.comparatorProperty());
            // tblData.getItems().clear();
            tblData.setItems(sortedData);
*/

        });










        //fill the text fields using database values
        tblData.setRowFactory(tv -> {
            TableRow<Secratary> row = new TableRow<>();  //get te selection values of table view
            row.setOnMouseClicked(event -> {
                if (/*event.getClickCount() == 2 && */(! row.isEmpty()) ) {
                    Secratary rowData = row.getItem();
                    clickfname = rowData.getFname();
                    clicklname = rowData.getLname();
                    clickoffice = rowData.getOffice();
                    clicksrchyr = txtsearchyr.getText();



                    String query = "SELECT * FROM `secratary` WHERE `fname` = ? AND `lname` = ? AND `office` = ? AND `curyr` = ?";
                    try {
                        PreparedStatement  ps = con.prepareStatement(query);
                        ps.setString(1,clickfname);
                        ps.setString(2,clicklname);
                        ps.setString(3,clickoffice);
                        ps.setString(4,clicksrchyr);
                        ResultSet rs = ps.executeQuery();


                        while (rs.next()) {
                            txtFirstname.setText(rs.getString(1));
                            txtLastname.setText(rs.getString(2));
                            txtWOP.setText(rs.getString(3));
                            txtContact.setText(rs.getString(5));
                            txtEmail.setText(rs.getString(6));

                            cmbOffice.setValue(rs.getString(4));//set the values to combobox
                            cmbGender.setValue(rs.getString(7));//set the values to combobox
                            cmbCuryr.setValue(rs.getString(18));//set the values to combobox

                            try { //set the values to date picker

                                txtBirthday.setValue(LocalDate.parse(rs.getString(8)));
                                txtFirstAppdate.setValue(LocalDate.parse(rs.getString(9)));
                                txtUpgrading.setValue(LocalDate.parse(rs.getString(10)));
                                txtRetirement.setValue(LocalDate.parse(rs.getString(11)));
                                txtIncrement.setValue(LocalDate.parse(rs.getString(12)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //set db value to radio button
                            if(null != rs.getString(13))switch (rs.getString(13)){
                                case "yes" :
                                    radioyes.setSelected(true);
                                    break;
                                case "no" :
                                    radiono.setSelected(true);
                                    break;
                                default:
                                    radioyes.setSelected(false);
                                    radiono.setSelected(false);
                                    break;

                            }
                            else {
                                radioyes.setSelected(false);
                                radiono.setSelected(false);
                            }


                            //set db value to check boxses

                            switch (rs.getString(14)){
                                case "yes" :
                                    chkPb.setSelected(true);
                                    break;
                                case "no" :
                                    chkPb.setSelected(false);
                                    break;
                                default:
                                    chkPb.setSelected(false);
                                    break;

                            }

                            switch (rs.getString(15)){
                                case "yes" :
                                    chkPm.setSelected(true);
                                    break;
                                case "no" :
                                    chkPm.setSelected(false);
                                    break;
                                default:
                                    chkPm.setSelected(false);
                                    break;

                            }

                            switch (rs.getString(16)){
                                case "yes" :
                                    chkPe.setSelected(true);
                                    break;
                                case "no" :
                                    chkPe.setSelected(false);
                                    break;
                                default:
                                    chkPe.setSelected(false);
                                    break;

                            }


                            //set image view using db value

                            InputStream is = rs.getBinaryStream(17);
                            OutputStream os = new FileOutputStream(new File("photo.jpg"));
                            byte[] content = new byte[1024];
                            int size = 0;
                            while ((size = is.read(content)) != -1){
                                os.write(content ,0 ,size);
                            }

                            os.close();
                            is.close();

                            Image image1 = new Image("file:photo.jpg");
                            img_frame.setImage(image1);


                        }



                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            return row ;
        });



    }

    //search table value



}
