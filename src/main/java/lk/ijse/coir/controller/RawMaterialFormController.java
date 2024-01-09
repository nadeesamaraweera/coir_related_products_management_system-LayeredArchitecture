
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
import lk.ijse.coir.bo.custom.RawMaterialBO;
import lk.ijse.coir.bo.custom.impl.RawMaterialBOImpl;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.RawMaterialDto;
import lk.ijse.coir.dto.tm.RawTm;
import lk.ijse.coir.model.RawMaterialModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class RawMaterialFormController {

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitprice;


    @FXML
    private TableView<RawTm> tblRaw;

    RawMaterialBO rawMaterialBO = new RawMaterialBOImpl();



    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        loadAllMaterials();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnStock"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

    }


private void loadAllMaterials() throws SQLException, ClassNotFoundException {
    ObservableList<RawTm> obList = FXCollections.observableArrayList();

    List<RawMaterialDto> dtoList = rawMaterialBO.getAllMaterials();

    for (RawMaterialDto dto : dtoList) {
        obList.add(
                new RawTm(
                        dto.getRawMaterialId(),
                        dto.getMaterialName(),
                        dto.getQtyOnStock(),
                        dto.getUnitPrice()


                )
        );
    }

    tblRaw.setItems(obList);
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

    boolean existRaw(String id) throws SQLException, ClassNotFoundException {
        return rawMaterialBO.existRawMaterial(id);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String id = tblRaw.getSelectionModel().getSelectedItem().getRawMaterialId();
        try {
            if (existRaw(id)) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delete Successful!").show();
            }
            rawMaterialBO.deleteRawMaterial(id);
            tblRaw.getItems().remove(tblRaw.getSelectionModel().getSelectedItem());
            tblRaw.getSelectionModel().clearSelection();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the rawMaterial " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String rawMaterialId = txtId.getText();
        RawMaterialDto rawMaterialDto = rawMaterialBO.searchRawMaterial(rawMaterialId);


        if (rawMaterialDto != null) {
            fillFields(rawMaterialDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "rawMaterial not found!").show();
        }
    }

    private void fillFields(RawMaterialDto dto) {
        txtId.setText(dto.getRawMaterialId());
        txtName.setText(dto.getMaterialName());
        txtQty.setText(String.valueOf(dto.getQtyOnStock()));
        txtUnitprice.setText(String.valueOf(dto.getUnitPrice()));
       
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        RawMaterialDto rawMaterialDto = new RawMaterialDto(txtId.getText(), txtName.getText(),Double.parseDouble(String.valueOf(txtQty.getText())), BigDecimal.valueOf(Long.parseLong(txtUnitprice.getText())));
        boolean issave = rawMaterialBO.saveRawMaterial(rawMaterialDto);
        if (issave) {
            new Alert(Alert.AlertType.CONFIRMATION, "rawMaterial saved!").show();
            clearFields();
            initialize();
        }

    }


    private boolean validateRawMaterial() {

        String id = txtId.getText();

        boolean isRawMaterialIdValidation = Pattern.matches("[R][0-9]{3,}", id);

        if (!isRawMaterialIdValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID RAW MATERIAL  ID").show();
            txtId.setStyle("-fx-border-color: Red");
            return false;

        }


        String name = txtName.getText();

        boolean isMaterialNameValidation = Pattern.matches("[A-Za-z.]{3,}", name);

        if (!isMaterialNameValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID RAW MATERIAL NAME").show();
            txtName.setStyle("-fx-border-color: Red");
            return false;
        }

        Double qty = Double.parseDouble(txtQty.getText());
        String qtyString = String.format("%.2f", qty);

        boolean isMaterialqtyValidation = Pattern.matches("[-+]?[0-9]*\\.?[0-9]+", qtyString);

        if (!isMaterialqtyValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID QTY ON STOCK").show();
            txtQty.setStyle("-fx-border-color: Red");
            return false;
        }
        return true;
    }



/*Double unitPrice = Double.parseDouble(txtUnitprice.getText());
            String unitPriceString = String.format("%.2f", unitPrice);


            boolean isMaterialpriceValidation = Pattern.matches("^[1-9]\\\\d{0,6}\\\\.\\\\d{2}$", unitPriceString);

            if (!isMaterialpriceValidation) {

                new Alert(Alert.AlertType.ERROR, "INVALID RAW MATERIAL UNIT PRICE").show();
                txtUnitprice.setStyle("-fx-border-color: Red");

                return false;
            }
            return true;
        }*/





    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        RawMaterialDto rawMaterialDto = new RawMaterialDto(txtId.getText(), txtName.getText(),Double.valueOf(txtQty.getText()),BigDecimal.valueOf(Long.parseLong(txtUnitprice.getText())));
        boolean isupdate = rawMaterialBO.updateRawMaterial(rawMaterialDto);
        if (isupdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "rawMaterial updated!").show();
            clearFields();
            initialize();
        }
    }
    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtQty.setText("");
        txtUnitprice.setText("");
       
    }
}

