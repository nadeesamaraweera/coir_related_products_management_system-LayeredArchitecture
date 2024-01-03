
package lk.ijse.coir.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.coir.model.UserModel;
import lk.ijse.coir.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginPageFormController implements Initializable {


    public TextField show;
    //public AnchorPane pane;
    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtusername;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        if (UserModel.verifyCredential(txtusername.getText(), txtpassword.getText())) {
            try {
                Navigation.switchNavigation("dashboardForm.fxml", event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Credentials").show();
        }

    }


    @FXML
    void hypforgotpasswordOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("forgotPasswordForm.fxml", event);

    }

    @FXML
    void hypsignupOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("signUpForm.fxml", event);
    }

    public void check(ActionEvent event) {
        String pass = txtpassword.getText();
        txtpassword.setVisible(false);
        show.setVisible(true);
        show.setText(pass);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show.setVisible(false);
    }
}

