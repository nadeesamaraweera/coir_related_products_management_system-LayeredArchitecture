package lk.ijse.coir.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.coir.bo.custom.ItemBO;
import lk.ijse.coir.bo.custom.impl.ItemBOImpl;
import lk.ijse.coir.dto.ItemDto;
import lk.ijse.coir.dto.RawMaterialDto;
import lk.ijse.coir.dto.tm.ItemTm;
import lk.ijse.coir.model.ItemModel;
import lk.ijse.coir.model.RawMaterialModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ItemFormController {

    @FXML
    private ComboBox<String> cmbRawMaterialId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colRawMaterialId;

    @FXML
    private AnchorPane pagingPane;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private Label txtRawMaterialId;

    private final ItemModel itemModel =new ItemModel();
    ItemBO itemBO = new ItemBOImpl();



    public void initialize() throws SQLException {
        setCellValueFactory();
        LoadAllItems();
        setListener();
        loadRawMaterialsIds();

    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colRawMaterialId.setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void LoadAllItems()  {
        try {
            List<ItemDto> dtoList = itemModel.loadAllItems();

            ObservableList<ItemTm> obList = FXCollections.observableArrayList();

            for(ItemDto dto : dtoList) {
                Button btn = new Button("remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblItem.getSelectionModel().getSelectedIndex();

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tblItem.refresh();
                    }
                });

                var tm = new ItemTm(
                        dto.getItemId(),
                        dto.getItemName(),
                        dto.getUnitPrice(),
                        dto.getQtyOnHand(),
                        dto.getRawMaterialId(),
                        btn
                );
                obList.add(tm);
            }
            tblItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRawMaterialsIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<RawMaterialDto> rawList = RawMaterialModel.loadAllMaterials();

            for (RawMaterialDto dto : rawList) {
                obList.add(dto.getRawMaterialId());
            }
            cmbRawMaterialId.setItems(obList);
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
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ItemDto itemDto = new ItemDto(txtItemId.getText(), txtItemName.getText(),Double.parseDouble(String.valueOf(txtUnitPrice.getText())),Integer.parseInt(String.valueOf(txtQtyOnHand.getText())),cmbRawMaterialId.getValue());
        boolean issave = itemBO.saveItem(itemDto);
        if (issave) {
            new Alert(Alert.AlertType.CONFIRMATION, "item saved!").show();
            clearFields();
            initialize();
        }

    }

    private boolean validateItem() {

        String itemIdText = txtItemId.getText();

        boolean isItemIDValidation = Pattern.matches("[I][0-9]{3,}", itemIdText);

        if (!isItemIDValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID ITEM ID").show();
            txtItemId.setStyle("-fx-border-color: Red");
            return false;
        }


        String itemNameText = txtItemName.getText();

        boolean isItemNameValidation = Pattern.matches("[A-Za-z.]{3,}", itemNameText);

        if (!isItemNameValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID ITEM name").show();
            txtItemName.setStyle("-fx-border-color: Red");
            return false;
        }

       /* Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String unitPriceString = String.format("%.2f",unitPrice);
        boolean isUnitPriceValidation = Pattern.matches("[0-9].{3}", unitPriceString);

        if (!isUnitPriceValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID ITEM UNIT PRICE").show();
            txtUnitPrice.setStyle("-fx-border-color: Red");
            return false;
        }*/




        String qtyOnHandText = txtQtyOnHand.getText();

        boolean isQtyOnHandValidation = Pattern.matches("[-+]?[0-9]*\\.?[0-9]+", qtyOnHandText);

        if (!isQtyOnHandValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID ITEM QTY").show();
            txtQtyOnHand.setStyle("-fx-border-color: Red");
            return false;
        }

        return  true;
    }





    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ItemDto itemDto = new ItemDto(txtItemId.getText(),txtItemName.getText(),Double.parseDouble(String.valueOf(txtUnitPrice.getText())),Integer.parseInt(String.valueOf(txtQtyOnHand.getText())),cmbRawMaterialId.getValue());
        boolean isupdate = itemBO.updateItem(itemDto);
        if (isupdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "item updated!").show();
            clearFields();
            initialize();
        }

    }
    boolean existItemId(String id) throws SQLException, ClassNotFoundException {
        return itemBO.existItem(id);

    }

    private void setListener() {
        tblItem.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new ItemDto(

                    );
                    setFields(dto);
                });
    }

    private void setFields(ItemDto dto) {
        txtItemId.setText(dto.getItemId());
        txtItemName.setText(dto.getItemName());
        txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
        cmbRawMaterialId.setValue(dto.getRawMaterialId());
    }

    private void clearFields() {
        txtItemId.setText("");
        txtItemName.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
        cmbRawMaterialId.setValue("");
    }


    public void itemIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String itemId = txtItemId.getText();
        ItemDto itemDto = itemBO.searchItem(itemId);


        if (itemDto != null) {
            setFields(itemDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "rawMaterial not found!").show();
        }

        }

    @FXML
    void cmbRawMaterialIdOnAction(ActionEvent event) {

    }

}