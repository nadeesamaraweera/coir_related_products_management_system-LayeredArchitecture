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
import lk.ijse.coir.bo.custom.SupplierBO;
import lk.ijse.coir.bo.custom.impl.SupplierBOImpl;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.SupplierDto;
import lk.ijse.coir.dto.tm.SupplierTm;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    SupplierBO supplierBO = new SupplierBOImpl();

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAllSuppliers();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllSuppliers() throws ClassNotFoundException {
      //  var model = new SupplierBO();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList = supplierBO.getAllSuppliers();

            for (SupplierDto dto : dtoList) {
                obList.add(
                        new SupplierTm(
                                dto.getSupplierId(),
                                dto.getSupplierName(),
                                dto.getAddress(),
                                dto.getTel()
                        )
                );
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean isvalidate =validateSupplier();
        if(isvalidate){
            String id =txtId.getText();
            String name =txtName.getText();
            String address =txtAddress.getText();
            String tel =txtTel.getText();

            var dto =new  SupplierDto(id,name,address,tel);
        }

        SupplierDto supplierDto = new SupplierDto(txtId.getText(), txtName.getText(), txtAddress.getText(), txtTel.getText());
        boolean issave = supplierBO.saveSupplier(supplierDto);
        if (issave) {
            new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
            clearFields();
            initialize();
            generateNextSupplierId();



        }
    }

    private boolean validateSupplier() {

        String idText = txtId.getText();

        boolean isSupplierIdValidation = Pattern.matches("[S][0-9]{3,}", idText);

        if (!isSupplierIdValidation) {


            new Alert(Alert.AlertType.ERROR, "INVALID SUPPLIER ID").show();
            txtId.setStyle("-fx-border-color: Red");
            return  false;
        }


        String nameText = txtName.getText();

        boolean isSupplierNameValidation = Pattern.matches("[A-Za-z.]{3,}", nameText);

        if (!isSupplierNameValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID SUPPLIER NAME").show();
            txtName.setStyle("-fx-border-color: Red");
            return  false;
        }

        String addressText = txtAddress.getText();

        boolean isSupplierAddressValidation = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);

        if (!isSupplierAddressValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID SUPPLIER ADDRESS").show();
            txtAddress.setStyle("-fx-border-color: Red");
            return false;
        }


        String telText = txtTel.getText();

        boolean isSupplierTelValidation = Pattern.matches("[0-9]{10}", telText);

        if (!isSupplierTelValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID SUPPLIER TEL").show();
            txtTel.setStyle("-fx-border-color: Red");

            return false;
        }
        return  true;
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        SupplierDto supplierDto = new SupplierDto(txtId.getText(), txtName.getText(), txtAddress.getText(), txtTel.getText());
        boolean isupdate = supplierBO.updateSupplier(supplierDto);
        if (isupdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
            clearFields();
            initialize();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String supplierId = txtId.getText();
        SupplierDto supplierDto = supplierBO.searchSupplier(supplierId);


        if (supplierDto != null) {
            fillFields(supplierDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "supplier not found!").show();
        }

    }

    private void fillFields(SupplierDto dto) {
        txtId.setText(dto.getSupplierId());
        txtName.setText(dto.getSupplierName());
        txtAddress.setText(dto.getAddress());
        txtTel.setText(dto.getTel());
    }

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierBO.existSupplier(id);

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String id = tblSupplier.getSelectionModel().getSelectedItem().getSupplierId();
        try {
            if (existSupplier(id)) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delete Successful!").show();
            }
            supplierBO.deleteSupplier(id);
            tblSupplier.getItems().remove(tblSupplier.getSelectionModel().getSelectedItem());
            tblSupplier.getSelectionModel().clearSelection();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the supplier " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }

    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }

    private void generateNextSupplierId() {
        try {
            String supplierID = supplierBO.generateNewID();
            txtId.setText(supplierID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

