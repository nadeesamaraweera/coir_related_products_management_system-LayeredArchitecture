package lk.ijse.coir.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.ijse.coir.model.UserModel;
import lk.ijse.coir.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class ResetPasswordController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtPassword;


    @FXML
    void btnOnActionLogin(ActionEvent event) throws IOException {
        Navigation.switchNavigation("loginPageForm.fxml",event);
    }

    @FXML
    void btnOnActionResetPassword(ActionEvent event) throws SQLException {

        UserModel userModel = new UserModel();
        if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
            boolean isUpdated = userModel.updatePassword(ForgotPasswordFormController.username, txtPassword.getText());
            if (isUpdated) {
                System.out.println("OK");
                new Alert(Alert.AlertType.CONFIRMATION, "OK!").show();

            } else {
                System.out.println("WRONG");
            }
        }else {
            System.out.println("CONFIRMATION NOT MATCHED!");
            new Alert(Alert.AlertType.ERROR,"CONFIRM PASSWORD NOT MATCHED!").show();
            txtConfirmPassword.setStyle("-fx-border-color: Red");


        }
    }
    public void initialize(){
        System.out.println(ForgotPasswordFormController.username);

    }
}

