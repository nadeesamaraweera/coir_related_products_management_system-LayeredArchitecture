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
import lk.ijse.coir.bo.custom.CustomerBO;
import lk.ijse.coir.bo.custom.impl.CustomerBOImpl;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.tm.CustomerTm;
import lk.ijse.coir.entity.Customer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

        CustomerBO customerBO = new CustomerBOImpl();

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
           //var model = new CustomerModel();

            ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

            try {
                ArrayList<CustomerDto> dtoList = customerBO.getAllCustomers();

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
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
            boolean isvalidate =validateCustomer();
            if(isvalidate){
               String id =txtId.getText();
               String name =txtName.getText();
               String address =txtAddress.getText();
               String tel =txtTel.getText();

               var dto =new  CustomerDto(id,name,address,tel);
            }

            CustomerDto customerDto = new CustomerDto(txtId.getText(), txtName.getText(), txtAddress.getText(), txtTel.getText());

               boolean issave = customerBO.saveCustomer(customerDto);
               if (issave) {
                   new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                   clearFields();
                   initialize();
                   generateNextCustomerId();

               }
           }



        private boolean validateCustomer() {

            String id = txtId.getText();

            boolean isCustomerIdValidation = Pattern.matches("[C][0-9]{3,}", id);

            if (!isCustomerIdValidation) {


                new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER ID").show();
                txtId.setStyle("-fx-border-color: Red");
                return  false;
            }


            String name = txtName.getText();

            boolean isCustomerNameValidation = Pattern.matches("[A-Za-z.]{3,}", name);

            if (!isCustomerNameValidation) {

                new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER NAME").show();
                txtName.setStyle("-fx-border-color: Red");
                return  false;
            }

            String address = txtAddress.getText();

            boolean isCustomerAddressValidation = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", address);

            if (!isCustomerAddressValidation) {

                new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER ADDRESS").show();
                txtAddress.setStyle("-fx-border-color: Red");
                return false;
            }


             String tel= txtTel.getText();

              boolean isCustomerTelValidation = Pattern.matches("[0-9]{10}", tel);

               if (!isCustomerTelValidation) {

                  new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER TEL").show();
                   txtTel.setStyle("-fx-border-color: Red");

                   return false;
               }
            return  true;
           }


           @FXML
        void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

               CustomerDto customerDto = new CustomerDto(txtId.getText(), txtName.getText(), txtAddress.getText(), txtTel.getText());
               boolean isupdate = customerBO.updateCustomer(customerDto);
               if (isupdate) {
                   new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                   clearFields();
                   initialize();
               }else {
                   new Alert(Alert.AlertType.ERROR, "customer not updated!").show();

               }
           }

        @FXML
        void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
            String customerId = txtId.getText();
            CustomerDto customerDto = customerBO.searchCustomer(customerId);


            if (customerDto != null) {
                fillFields(customerDto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }

        }

        private void fillFields(CustomerDto dto) {
            txtId.setText(dto.getCustomerId());
            txtName.setText(dto.getCustomerName());
            txtAddress.setText(dto.getAddress());
            txtTel.setText(dto.getTel());
        }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.existCustomer(id);

    }


    @FXML
        void btnDeleteOnAction(ActionEvent event){
            String id = tblCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        try {
            if (existCustomer(id)) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delete Successful!").show();
            }
            customerBO.deleteCustomer(id);
            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
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

    private void generateNextCustomerId() {
        try {
            String customerID = customerBO.generateNewID();
            txtId.setText(customerID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    }

