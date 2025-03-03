package ui.users.user;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UUserController {
     @FXML
    private Button moviebtn;

    @FXML
    private Button tvbtn;

    @FXML
    private Label welcomelabel;

    @FXML
    private Button logoutbutton;

    @FXML
    private void logoutUser(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/ui/login/Login.fxml"));
       Parent parent = loader.load();
       Scene scene = new Scene(parent);
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       stage.setTitle("Pinoy Flix");
       stage.setScene(scene);
       stage.show();
   }
   
    public void displayName(String username){
        welcomelabel.setText("Welcome back, " + username);
    }
    
}
