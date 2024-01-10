package lk.ijse.coir.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.coir.bo.custom.LoginBO;
import lk.ijse.coir.bo.custom.impl.LoginBOImpl;
import lk.ijse.coir.dto.UserDto;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import static lk.ijse.coir.controller.EmailController.sendEmail;

public class ForgotPasswordFormController {

        @FXML
        private AnchorPane pane;

        @FXML
        private Button btnBack;

        @FXML
        private Button btnReset;

        @FXML
        private TextField txtusername;

        static String username;
        static int otp;

        LoginBO loginBO =new LoginBOImpl();


        @FXML
        void btnBackOnAction (ActionEvent event) throws IOException {

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/loginPageForm.fxml"));
            Stage stage = (Stage) pane.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.centerOnScreen();
        }



        @FXML
        void btnOnActionReset(ActionEvent event) throws MessagingException, SQLException, IOException, ClassNotFoundException {
            username = txtusername.getText();
            System.out.println(username);
            Random random = new Random();
            otp = random.nextInt(9000);
            otp += 1000;

            try {
                UserDto userDto = loginBO.getEmail(username);
                System.out.println(userDto.getEmail());
                sendEmail(userDto.getEmail(), "Login verification", otp + "");


            }catch (SQLException e){
                e.printStackTrace();
            }

            Parent rootNode = FXMLLoader.load(getClass().getResource("/view/otpForm.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.centerOnScreen();
            stage1.setResizable(false);
            stage1.show();


        }

    }


