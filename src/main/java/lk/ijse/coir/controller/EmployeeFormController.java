package lk.ijse.coir.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.coir.bo.custom.EmployeeBO;
import lk.ijse.coir.bo.custom.impl.EmployeeBOImpl;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.EmployeeDto;
import lk.ijse.coir.dto.tm.EmployeeTm;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeFormController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJob;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtjOB;

    EmployeeBO employeeBO =new EmployeeBOImpl();


    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    private void loadAllEmployees() {

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = employeeBO.getAllEmployees();

            for (EmployeeDto dto : dtoList) {
                obList.add(
                        new EmployeeTm(
                                dto.getEmployeeId(),
                                dto.getEmployeeName(),
                                dto.getEmail(),
                                dto.getTel(),
                                dto.getJobTitle(),
                                dto.getSalary(),
                                dto.getDate()

                        )
                );
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
        void btnBackOnAction (ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Dashboard");
            stage.centerOnScreen();
        }


        @FXML
        void btnClearOnAction (ActionEvent event){
           clearFields();

        }
        boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
          return employeeBO.existEmployee(id);

        }

        @FXML
        void btnDeleteOnAction (ActionEvent event){
            String id = tblEmployee.getSelectionModel().getSelectedItem().getEmployeeId();
            try {
                if (existEmployee(id)) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Delete Successful!").show();
                }
                employeeBO.deleteEmployee(id);
                tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
                tblEmployee.getSelectionModel().clearSelection();
                clearFields();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the cemployee " + id).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        

        @FXML
        void btnSaveOnAction (ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean isvalidate = validateEmployee();
        if (isvalidate){
            String id =txtId.getText();
            String name =txtName.getText();
            String email=txtEmail.getText();
            String tel = txtTel.getText();
            String job =txtjOB.getText();
            double salary = Double.parseDouble(txtSalary.getText());
            String date = txtDate.getText();

            var dto = new EmployeeDto(id,name,email,tel,job,salary,date);
        }
            EmployeeDto employeeDto = new EmployeeDto(txtId.getText(), txtName.getText(), txtEmail.getText(), txtTel.getText(),txtjOB.getText(),Double.valueOf(txtSalary.getText()),txtDate.getText());
            boolean issave = employeeBO.saveEmployee(employeeDto);
            if (issave) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                clearFields();
                initialize();
                generateNextEmployeeId();
            }
        }


    @FXML
    void btnUpdateOnAction (ActionEvent event) throws SQLException, ClassNotFoundException {
        EmployeeDto employeeDto= new EmployeeDto(txtId.getText(), txtName.getText(), txtEmail.getText(), txtTel.getText(),txtjOB.getText(),Double.valueOf(txtSalary.getText()),txtDate.getText());
        boolean isupdate = employeeBO.updateEmployee(employeeDto);
        if (isupdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
            clearFields();
            initialize();
        }else {
            new Alert(Alert.AlertType.ERROR, "employee not  updated!").show();

        }
    }

    private boolean validateEmployee() {

        String idText = txtId.getText();

        boolean isEmployeeIdValidation = Pattern.matches("[E][0-9]{3,}", idText);

        if (!isEmployeeIdValidation) {


            new Alert(Alert.AlertType.ERROR, "INVALID EMPLOYEE ID").show();
            txtId.setStyle("-fx-border-color: Red");
            return  false;
        }


        String nameText = txtName.getText();

        boolean isEmployeeNameValidation = Pattern.matches("[A-Za-z.]{3,}", nameText);

        if (!isEmployeeNameValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID Employee NAME").show();
            txtName.setStyle("-fx-border-color: Red");
            return  false;
        }

        String emailText = txtEmail.getText();

        boolean isEmployeeEmailValidation = Pattern.matches("[a-z].*(com|lk)", emailText);

        if (!isEmployeeEmailValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID EMAIL").show();
            txtEmail.setStyle("-fx-border-color: Red");
            return false;
        }


      /* String dateText = txtDate.getText();

        boolean isEmployeedateValidation = Pattern.matches("\\d{1,2}\\/\\d{1,2}\\/\\d{2,4}", dateText);

        if (!isEmployeedateValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID DATE").show();
            txtDate.setStyle("-fx-border-color: Red");
            retu*//*rn false;
        }*/

        Double salaryText = Double.valueOf(txtDate.getText());
        String salaryTextString = String.format("%.2f",salaryText);

        boolean isEmployeesalaryValidation = Pattern.matches("[0-9].{3}", salaryTextString);

        if (!isEmployeesalaryValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID SALARY").show();
            txtSalary.setStyle("-fx-border-color: Red");
            return false;
        }


        String telText = txtTel.getText();

        boolean isEmployeeTelValidation = Pattern.matches("[0-9]{10}", telText);

        if (!isEmployeeTelValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID EMPLOYEE TEL").show();
            txtTel.setStyle("-fx-border-color: Red");

            return false;
        }
        return  true;
    }



    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = txtId.getText();
        EmployeeDto employeeDto = employeeBO.searchEmployee(employeeId);


        if (employeeDto != null) {
            fillFields(employeeDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "employee not found!").show();
        }

    }


    private void fillFields(EmployeeDto dto) {
        txtId.setText(dto.getEmployeeId());
        txtName.setText(dto.getEmployeeName());
        txtEmail.setText(dto.getEmail());
        txtTel.setText(dto.getTel());
        txtjOB.setText(dto.getJobTitle());
        txtSalary.setText(String.valueOf(dto.getSalary()));
        txtDate.setText(dto.getDate());

    }


    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtTel.setText("");
        txtjOB.setText("");
        txtSalary.setText("");
        txtDate.setText("");


    }
    private void generateNextEmployeeId() {
        try {
            String employeeID = employeeBO.generateNewID();
            txtId.setText(employeeID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
