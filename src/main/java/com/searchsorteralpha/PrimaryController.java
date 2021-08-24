package com.searchsorteralpha;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PrimaryController {
    
    @FXML
    private TextField queryField;
    @FXML
    private VBox OptionBox;
    @FXML
    private CheckBox comAllowedBox;
    @FXML
    private CheckBox netAllowedBox;
    @FXML
    private CheckBox orgAllowedBox;
    @FXML
    private CheckBox eduAllowedBox;
    @FXML
    private CheckBox govAllowedBox;
    @FXML
    private CheckBox usAllowedBox;
    @FXML
    private CheckBox caAllowedBox;
    @FXML
    private Spinner<Integer> numResultsSpinner;
    @FXML
    private Button goBtn;
    
    private CheckBox[] domainBoxes;
    
    public void initialize() {
        numResultsSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
        // Used to determine which domains are allowed per user's request
        CheckBox[] tempDomainBoxes = {comAllowedBox, netAllowedBox, orgAllowedBox, 
            eduAllowedBox, govAllowedBox, usAllowedBox, caAllowedBox};
        domainBoxes = tempDomainBoxes.clone();
    }
    
    @FXML
    private void initSearch() {
        String query = queryField.getText();
        List<String> allowedDomains = new ArrayList<>();
        for (CheckBox box : domainBoxes) {
            if (box.isSelected()) {
                allowedDomains.add(box.getText().substring(1));
            }
        }
        int numResults = numResultsSpinner.getValue();
        SearchFilter filter = new SearchFilter(query, allowedDomains, numResults);
        try {
            List<String> results = filter.search();
            SecondaryController.setLinks(formatAsHyperlinks(results));
            switchToSecondary();
        } catch (Exception e) {
            System.exit(1);
        }
    }
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    private List<Hyperlink> formatAsHyperlinks(List<String> links) {
        List<Hyperlink> hyperlinks = new ArrayList<>();
        for (String s : links) {
            Hyperlink link = new Hyperlink(s);
            link.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(s));
                } catch (IOException | URISyntaxException ex) {
                    System.exit(1);
                }
            });
            hyperlinks.add(link);
        }
        return hyperlinks;
    }
}
