package com.example.csc325_firebase_webview_auth.view;//package modelview;

import com.example.csc325_firebase_webview_auth.viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AccessFBView {
    //fxml member variables
    @FXML
    private TextArea outputField;
    @FXML
    private Button switchScene;

    private boolean key;
    private ObservableList<Resource> listOfResources = FXCollections.observableArrayList();
    private Resource resource;

    private static boolean authentication = false;

    @FXML
    private TableView<Resource> table;
    @FXML
    private TableColumn<Resource, String> name;
    @FXML
    private TableColumn<Resource, String> address;
    @FXML
    private TableColumn<Resource, String> city;
    @FXML
    private TableColumn<Resource, String> hours;
    @FXML
    private TableColumn<Resource, String> state;
    @FXML
    private TableColumn<Resource, String> zipcode;

    public ObservableList<Resource> getListOfResroucess() {
        return listOfResources;
    }

    /**
     *  initialize sets up the table view
     * */
    @FXML
    void initialize() {
//        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
//        nameField.textProperty().bindBidirectional(accessDataViewModel.resourceNameProperty());
//        addressField.textProperty().bindBidirectional(accessDataViewModel.resourceAddressProperty());
//        cityField.textProperty().bindBidirectional(accessDataViewModel.resourceCityProperty());
//        stateField.textProperty().bindBidirectional(accessDataViewModel.resourceStateProperty());
//        zipField.textProperty().bindBidirectional(accessDataViewModel.resourceZipProperty());
//        hoursField.textProperty().bindBidirectional(accessDataViewModel.resourceHoursProperty());
//        urlField.textProperty().bindBidirectional(accessDataViewModel.resourceUrlProperty());
//        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        hours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        zipcode.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
        table.setItems(listOfResources);

        readFirebase();
    }

    /**
     *  public readRecord to call private method
     * */
    @FXML
    public void readRecord(ActionEvent event) {
        readFirebase();
    }


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("/files/WebContainer.fxml");
    }

    /**
     *  private readFirebase reads records from firebase no sql database
     * */
    private boolean readFirebase() {
        key = false;

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = App.fstore.collection("Resources").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (documents.size() > 0) {
                System.out.println("Outing....");
                for (QueryDocumentSnapshot document : documents) {
                    System.out.println(document.getId() + " => " + document.getData().get("name"));
                    resource = new Resource(String.valueOf(document.getData().get("name")),
                            document.getData().get("address").toString(),
                            document.getData().get("city").toString(),
                            document.getData().get("zipcode").toString(),
                            document.getData().get("state").toString(),
                            document.getData().get("url").toString(),
                            document.getData().get("hours").toString());
                    listOfResources.add(resource);
                }
            } else {
                System.out.println("No data");
            }
            key = true;

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return key;
    }

    /**
     *  Registers user in firebase auth *NOT USED*
     * */
    public boolean registerUser(String email, String password) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setEmailVerified(false)
                .setPassword(password)
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    @FXML
    private void LaunchLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/Login.fxml"));
            Parent parent = fxmlLoader.load();

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void LaunchAddRecord() {
        if (authentication) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/AddResourceUI.fxml"));
                Parent parent = fxmlLoader.load();

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setScene(scene);
                stage.showAndWait();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("ERROR: Administrator Login Required");
        }
    }

    @FXML
    private void LaunchMap() {
        try {
            App.setRoot("/files/Map.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void LaunchForum() {
        try {
            App.setRoot("/files/Forum.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void autheticate(boolean auth) {
        authentication = auth;
        System.out.println(authentication);
    }
}