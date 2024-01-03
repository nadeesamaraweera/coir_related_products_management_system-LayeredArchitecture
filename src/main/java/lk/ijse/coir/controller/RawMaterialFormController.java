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
import lk.ijse.coir.dto.RawMaterialDto;
import lk.ijse.coir.dto.tm.RawTm;
import lk.ijse.coir.model.RawMaterialModel;

import java.io.IOException;
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



    public void initialize() {
        setCellValueFactory();
        loadAllMaterials();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnStock"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

    }

    private void loadAllMaterials() {
        var model = new RawMaterialModel();

        ObservableList<RawTm> obList = FXCollections.observableArrayList();

        try {
            List<RawMaterialDto> dtoList = model.getAllMaterials();

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
        String rawMaterialId = txtId.getText();

        var rawModel = new RawMaterialModel();
        try {
            boolean isDeleted = rawModel.deleteRaw(rawMaterialId);

            if (isDeleted) {
                tblRaw.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "raw material deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String rawMaterialId = txtId.getText();

        var model = new RawMaterialModel();
        try {
            RawMaterialDto dto = model.searchRaw(rawMaterialId);

            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Raw Material not  found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(RawMaterialDto dto) {
        txtId.setText(dto.getRawMaterialId());
        txtName.setText(dto.getMaterialName());
        txtQty.setText(String.valueOf(dto.getQtyOnStock()));
        txtUnitprice.setText(String.valueOf(dto.getUnitPrice()));
       
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String rawMaterialId = txtId.getText();
        String materialName = txtName.getText();
        Double qtyOnStock = Double.parseDouble(String.valueOf(txtQty.getText()));
        Double unitPrice = Double.parseDouble(String.valueOf(txtUnitprice.getText()));

        boolean isValidate = validateRawMaterial();

        if (isValidate) {


            var dto = new RawMaterialDto(rawMaterialId, materialName, qtyOnStock, unitPrice);

            var model = new RawMaterialModel();
            try {
                boolean isSaved = model.saveraw(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "raw Material saved!").show();
                    clearFields();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
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
    void btnUpdateOnAction(ActionEvent event) {
        String rawMaterialId = txtId.getText();
        String materialName = txtName.getText();
        Double qtyOnStock= Double.valueOf(txtQty.getText());
        Double unitPrice= Double.valueOf(txtUnitprice.getText());



        var dto = new RawMaterialDto(rawMaterialId, materialName,qtyOnStock,unitPrice);

        var model = new RawMaterialModel();
        try {
            boolean isUpdated = model.updateraw(dto);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "raw Material updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtQty.setText("");
        txtUnitprice.setText("");
       
    }
}



