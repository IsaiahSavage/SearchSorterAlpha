package com.searchsorteralpha;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;

public class SecondaryController {

    private static List<Hyperlink> links;
    
    @FXML
    private ListView<Hyperlink> results;

    public void initialize() {
        ObservableList<Hyperlink> resultsList = FXCollections.observableArrayList();
        for (Hyperlink link : links) {
            resultsList.add(link);
        }
        results.setItems(resultsList);
    }
    
    public static List<Hyperlink> getLinks() {
        return links;
    }

    public static void setLinks(List<Hyperlink> links) {
        SecondaryController.links = links;
    }
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}