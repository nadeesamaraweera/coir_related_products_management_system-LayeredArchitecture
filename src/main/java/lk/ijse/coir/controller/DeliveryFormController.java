package lk.ijse.coir.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.coir.dto.DeliveryDto;
import lk.ijse.coir.dto.EmployeeDto;
import lk.ijse.coir.dto.tm.DeliveryTm;
import lk.ijse.coir.model.DeliveryModel;
import lk.ijse.coir.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class DeliveryFormController {


    @FXML
    private AnchorPane pane;


    @FXML
    private TableColumn<?, ?> colDeliveryId;

    @FXML
    private TableColumn<?, ?> colDeliveryStatus;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnEmail;


    @FXML
    private AnchorPane emailpane;

    @FXML
    private ComboBox<String> cmbEmail;



    @FXML
    private TableView<DeliveryTm> tblDelivery;

    @FXML
    private TextField txtDeliveryId;

    @FXML
    private TextField txtDeliveryStatus;

    @FXML
    private ComboBox<String> cmbEmployee;


    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtTel;

    public void initialize() {
        setCellValueFactory();
        loadAllDelivery();
        loadAllEmployeeIds();
        loadAllEmployeeEmails();

    }

    private void loadAllEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> empList = EmployeeModel.loadAllEmployees();

            for (EmployeeDto dto : empList) {
                obList.add(dto.getEmployeeId());
            }
            cmbEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllEmployeeEmails() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> empList = EmployeeModel.loadAllEmployees();

            for (EmployeeDto dto : empList) {
                obList.add(dto.getEmail());
            }
            cmbEmail.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDeliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllDelivery() {
        var model = new DeliveryModel();

        ObservableList<DeliveryTm> obList = FXCollections.observableArrayList();

        try {
            List<DeliveryDto> dtoList = model.getAllDelivery();

            for(DeliveryDto dto : dtoList) {
                obList.add(
                        new DeliveryTm(
                                dto.getDeliveryId(),
                                dto.getOrderId(),
                                dto.getEmployeeId(),
                                dto.getLocation(),
                                dto.getDeliveryStatus(),
                                dto.getEmail()
                        )
                );
            }

            tblDelivery.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String deliveryId = txtDeliveryId.getText();

        var deliveryModel = new DeliveryModel();
        try {
            boolean isDeleted = deliveryModel.deleteDelivery(deliveryId);

            if(isDeleted) {
                tblDelivery.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "delivery deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String deliveryId = txtDeliveryId.getText();
        String orderId = txtOrderId.getText();
        String employeeId = cmbEmployee.getValue();
        String location = txtLocation.getText();
        String deliveryStatus = txtDeliveryStatus.getText();
        String tel = txtTel.getText();

        boolean isValidate = validateDelivery();

        if (isValidate) {


            var dto = new DeliveryDto(deliveryId, orderId, employeeId, location, deliveryStatus, tel);

            var model = new DeliveryModel();
            try {
                boolean isSaved = model.saveDelivery(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "delivery saved!").show();

                    clearFields();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }
    }

    private boolean validateDelivery() {

        String idText = txtDeliveryId.getText();

        boolean isDeliveryIdValidation = Pattern.matches("[D][0-9]{3,}", idText);

        if (!isDeliveryIdValidation) {


            new Alert(Alert.AlertType.ERROR, "INVALID DELIVERY ID").show();
            txtDeliveryId.setStyle("-fx-border-color: Red");
            return  false;
        }
        String orderidText = txtOrderId.getText();

        boolean isOrderIdValidation = Pattern.matches("[O][0-9]{3,}", orderidText);

        if (!isOrderIdValidation) {


            new Alert(Alert.AlertType.ERROR, "INVALID ORDER ID").show();
            txtOrderId.setStyle("-fx-border-color: Red");
            return  false;
        }

        String employeeidText = cmbEmployee.getValue();

        boolean isEmployeeIdValidation = Pattern.matches("[E][0-9]{3,}", employeeidText);

        if (!isEmployeeIdValidation) {


            new Alert(Alert.AlertType.ERROR, "INVALID EMPLOYEE ID").show();
            cmbEmployee.setStyle("-fx-border-color: Red");
            return  false;
        }



        String addressText = txtLocation.getText();

        boolean isLocationValidation = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);

        if (!isLocationValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID Location").show();
            txtLocation.setStyle("-fx-border-color: Red");
            return false;
        }


        String telText = txtTel.getText();

        boolean isDeliveryTelValidation = Pattern.matches("[0-9]{10}", telText);

        if (!isDeliveryTelValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID  TEL").show();
            txtTel.setStyle("-fx-border-color: Red");

            return false;
        }
        return  true;
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String deliveryId = txtDeliveryId.getText();
        String orderId = txtOrderId.getText();
        String employeeId = cmbEmployee.getValue();
        String location = txtLocation.getText();
        String deliveryStatus = txtDeliveryStatus.getText();
        String tel = txtTel.getText();

        var dto = new DeliveryDto(deliveryId, orderId, employeeId, location, deliveryStatus, tel);

        var model = new DeliveryModel();
        try {
            boolean isUpdated = model.updateDelivery(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Delivery updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void txtDeliveryIdSearchOnAction(ActionEvent event) {
        String deliveryId = txtDeliveryId.getText();

        var model = new DeliveryModel();
        try {
            DeliveryDto dto = model.searchDelivery(deliveryId);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void fillFields(DeliveryDto dto) {
        txtDeliveryId.setText(dto.getDeliveryId());
        txtOrderId.setText(dto.getOrderId());
        cmbEmployee.setValue(dto.getEmployeeId());
        txtLocation.setText(dto.getLocation());
        txtDeliveryStatus.setText(dto.getDeliveryStatus());
        txtTel.setText(dto.getEmail());
    }
    void clearFields() {
        txtDeliveryId.setText("");
        txtOrderId.setText("");
        cmbEmployee.setValue("");
        txtLocation.setText("");
        txtDeliveryStatus.setText("");
        txtTel.setText("");

    }

    @FXML
    void cmbEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmailOnAction(ActionEvent event) throws IOException {
        //switchPaging(emailpane, "emailForm.fxml", "Customer Form");

        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/emailForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.setResizable(false);
        stage1.show();
    }

    @FXML
    void cmbEmailOnAction(ActionEvent event) {

    }


}

