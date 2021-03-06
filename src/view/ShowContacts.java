package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ShowContacts extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            ScrollPane root = new ScrollPane();
            VBox box = new VBox();
            box.setSpacing(20);

            //Beim CSV Import muss ich die Anzahl der E-Mail-Adressen auf 3 begrenzen
            ContactCSVImport.kontakteEinlesen();
            ArrayList<Contact> contacts = AllContacts.allContacts;

            //Default-Liste "hart" im Code
            //ArrayList<Contact> contacts = AllContacts.getDefaultContacts();

            for (Contact c : contacts) {
                GridPane kontaktePane = kontaktAnzeigen(c);
                box.getChildren().add(kontaktePane);
                Separator separator = new Separator();
                separator.setValignment(VPos.CENTER);
                box.getChildren().add(separator);
            }

            root.setContent(box);

            Scene scene = new Scene(root, 380, 1000);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private GridPane kontaktAnzeigen(Contact contact) {

        GridPane root = new GridPane();

        root.setGridLinesVisible(false);
        root.setHgap(10);
        root.setVgap(10);

        root.setPadding(new Insets(25, 25, 25, 25));

        Label kontaktdaten = new Label(contact.getVname() + " " + contact.getNname() + "\n"
                + contact.getStrasse() + " " + contact.getHausnummer() + "\n"
                + contact.getPlz() + " " + contact.getOrt() + "\n"
                + contact.getNummer());

        root.add(kontaktdaten, 0, 0);

        if (contact.getFoto() == null) {

            Image image = new Image("/resources/nopicture.png");

            ImageView imageview = new ImageView(image);
            imageview.setFitHeight(150);
            imageview.setFitWidth(150);
            root.add(imageview, 1, 0);
        } else {

            Image image = new Image(getClass().
                    getResource("/resources/" + contact.getFoto()).toString());

            ImageView imageview = new ImageView(image);
            imageview.setFitHeight(150);
            imageview.setFitWidth(150);
            root.add(imageview, 1, 0);
        }

        Label labelemail = new Label("-- E-Mail-Adresse(n) --");
        root.add(labelemail, 0, 1);

        root.add(emailsAnzeigen(contact), 0, 2, 2, 1);


        return root;
    }

    private ScrollPane emailsAnzeigen(Contact contact) {

        GridPane root = new GridPane();
        root.setHgap(5);
        root.setVgap(5);
        root.setPadding(new Insets(8, 8, 8, 8));

        int row = 0;
        AllMailAdresses adresses = contact.getAdresses();

        if (contact.getAdresses().getAllMailAdresses().size() != 0) {
            for (MailAdress m : adresses.getAllMailAdresses()) {
                root.add(new Label(m.toString()), 1, row);
                row++;
            }
        } else
            root.add(new Label("keine Mail-Adresse hinterlegt"), 1, 1);

        ScrollPane pane = new ScrollPane();
        pane.setContent(root);
        return pane;
    }
}

