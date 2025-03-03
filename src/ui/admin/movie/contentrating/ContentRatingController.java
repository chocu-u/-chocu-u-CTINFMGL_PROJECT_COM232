package ui.admin.movie.contentrating;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import data.MovieCR;
import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ContentRatingController implements Initializable {

    ObservableList<MovieCR> contentraitingList = FXCollections.observableArrayList();

    @FXML
    private JFXButton addclassificationbutton;

    @FXML
    private JFXButton backbutton;

    @FXML
    private TableColumn<MovieCR, String> classificationcolumn;

    @FXML
    private TextField classificationtextfield;

    @FXML
    private TableColumn<MovieCR, String> contentratingidcolumn; 

    @FXML
    private TableView<MovieCR> contentratingtable;

    @FXML
    private JFXButton deletebutton;

    @FXML
    private Button moviebtn, tvbtn, usersbtn, logoutbtn;

    @FXML
    private JFXButton updatebutton;

    @FXML
    private Label welcomelabel;

    public void displayName(String fname){
        welcomelabel.setText("Welcome back, " + fname);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeCol();
        displayMovieCR();
    }

    private void initializeCol(){
        contentratingidcolumn.setCellValueFactory(new  PropertyValueFactory<>("contentratingid"));
        classificationcolumn.setCellValueFactory(new  PropertyValueFactory<>("classification"));
    }

   private void displayMovieCR(){
        contentraitingList.clear();
        ResultSet result = DatabaseHandler.getMoviesCR();
    
        if (result == null) {
            System.err.println("Error: ResultSet is null. Check database connection.");
            return;
        }
        try {
            while (result.next()) {
                String contentraitingid = result.getString("ContentRatingID");
                String classification = result.getString("Classification");

                contentraitingList.add(new MovieCR(contentraitingid, classification));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        contentratingtable.setItems(contentraitingList);
    }


    @FXML
    public void addClassification(ActionEvent event) {
        String classification = classificationtextfield.getText().trim();

        if (classification.length() == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Empty Classification");
            alert.showAndWait();
            return; // Prevent further execution if classification is empty
        }
    
        MovieCR movieCR = new MovieCR("", classification);
    
        if(DatabaseHandler.addClassification(movieCR)){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Classification Added");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Adding of Classification Failed");
            alert.showAndWait();
        }
        displayMovieCR();
    }
    

    @FXML
    public void deleteClassification(ActionEvent event) { 
        MovieCR movieCR = contentratingtable.getSelectionModel().getSelectedItem();

    if (movieCR == null) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Please select a classification to delete.");
        alert.showAndWait();
        return;
    }

    if (DatabaseHandler.deleteClassification(movieCR)) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Classification Deleted");
        alert.showAndWait();
        displayMovieCR();
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Cannot delete classification. It is being used by a movie.");
        alert.showAndWait();
    }
}

    @FXML
    public void updateClassification(ActionEvent event) { 
        MovieCR selectedClassification = contentratingtable.getSelectionModel().getSelectedItem();
        String newClassification = classificationtextfield.getText().trim();

    if (selectedClassification == null || newClassification.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Please select a classification and enter a new classification.");
        alert.showAndWait();
        return;
    }

    if (DatabaseHandler.updateClassification(selectedClassification, newClassification)) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Classification Updated");
        alert.showAndWait();
        displayMovieCR();
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Can't Update Classification");
        alert.showAndWait();
    }
}

    @FXML 
    public void backbutton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/admin/movie/Movie.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Pinoy Flix");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void logoutUser(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login/Login.fxml"));
       Parent parent = loader.load();
       Scene scene = new Scene(parent);
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       stage.setScene(scene);
       stage.show();
   }

   @FXML
    private void usersBtnHandler(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/admin/user/AUser.fxml"));
       Parent parent = loader.load();
       Scene scene = new Scene(parent);
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       stage.setTitle("Pinoy Flix");
       stage.setScene(scene);
       stage.show();
   }

   @FXML
    private void moviebuttonHandler(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/admin/movie/Movie.fxml"));
       Parent parent = loader.load();
       Scene scene = new Scene(parent);
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       stage.setTitle("Pinoy Flix");
       stage.setScene(scene);
       stage.show();
   }

}
