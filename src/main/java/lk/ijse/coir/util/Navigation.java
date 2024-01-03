package lk.ijse.coir.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class Navigation {
    public static Parent rootNode;
    private static Scene scene;
    private static Stage stage;

    public static Parent switchNavigation(String path, ActionEvent event) throws IOException {
        rootNode = FXMLLoader.load(Navigation.class.getResource("/view/" + path));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        return null;
    }

    public static void switchPaging(Pane pane, String path,String name) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/view/" + path ));
        Parent root = loader.load();
        pane.getChildren().add(root);
    }
}