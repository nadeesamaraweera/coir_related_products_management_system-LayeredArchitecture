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
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.tm.CustomerTm;
import lk.ijse.coir.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {
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
        private TableView<CustomerTm> tblCustomer;

        @FXML
        private TextField txtAddress;

        @FXML
        private TextField txtId;

        @FXML
        private TextField txtName;

        @FXML
        private TextField txtTel;

        public void initialize() {
            setCellValueFactory();
            loadAllCustomers();
        }

        private void setCellValueFactory() {
            colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        }

        private void loadAllCustomers() {
            var model = new CustomerModel();

            ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

            try {
                List<CustomerDto> dtoList = model.getAllCustomers();

                for (CustomerDto dto : dtoList) {
                    obList.add(
                            new CustomerTm(
                                    dto.getCustomerId(),
                                    dto.getCustomerName(),
                                    dto.getAddress(),
                                    dto.getTel()
                            )
                    );
                }

                tblCustomer.setItems(obList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void btnSaveOnAction(ActionEvent event) {
            String customerId = txtId.getText();
            String customerName = txtName.getText();
            String address = txtAddress.getText();
            String tel = txtTel.getText();

            boolean isValidate = validateCustomer();

            if (isValidate) {


                var dto = new CustomerDto(customerId, customerName, address, tel);

                var model = new CustomerModel();
                try {
                    boolean isSaved = model.saveCustomer(dto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                        clearFields();
                        initialize();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }


            }
        }

        private boolean validateCustomer() {

            String idText = txtId.getText();

            boolean isCustomerIdValidation = Pattern.matches("[C][0-9]{3,}", idText);

            if (!isCustomerIdValidation) {


                new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER ID").show();
                txtId.setStyle("-fx-border-color: Red");
                return  false;
            }


            String nameText = txtName.getText();

            boolean isCustomerNameValidation = Pattern.matches("[A-Za-z.]{3,}", nameText);

            if (!isCustomerNameValidation) {

                new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER NAME").show();
                txtName.setStyle("-fx-border-color: Red");
                return  false;
            }

            String addressText = txtAddress.getText();

            boolean isCustomerAddressValidation = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);

            if (!isCustomerAddressValidation) {

                new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER ADDRESS").show();
                txtAddress.setStyle("-fx-border-color: Red");
                return false;
            }


             String telText = txtTel.getText();

              boolean isCustomerTelValidation = Pattern.matches("[0-9]{10}", telText);

               if (!isCustomerTelValidation) {

                  new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER TEL").show();
                   txtTel.setStyle("-fx-border-color: Red");

                   return false;
               }
            return  true;
           }


           @FXML
        void btnUpdateOnAction(ActionEvent event) {
            String customerId = txtId.getText();
            String customerName = txtName.getText();
            String address = txtAddress.getText();
            String tel = txtTel.getText();

            var dto = new CustomerDto(customerId, customerName, address, tel);

            var model = new CustomerModel();
            try {
                boolean isUpdated = model.updateCustomer(dto);
                System.out.println(isUpdated);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        @FXML
        void txtSearchOnAction(ActionEvent event) {
            String customerId = txtId.getText();

            var model = new CustomerModel();
            try {
                CustomerDto dto = model.searchCustomer(customerId);

                if (dto != null) {
                    fillFields(dto);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        private void fillFields(CustomerDto dto) {
            txtId.setText(dto.getCustomerId());
            txtName.setText(dto.getCustomerName());
            txtAddress.setText(dto.getAddress());
            txtTel.setText(dto.getTel());
        }

        @FXML
        void btnDeleteOnAction(ActionEvent event) {
            String customerId = txtId.getText();

            var customerModel = new CustomerModel();
            try {
                boolean isDeleted = customerModel.deleteCustomer(customerId);

                if (isDeleted) {
                    tblCustomer.refresh();
                    new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
    }

