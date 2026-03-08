package com.property.property_frontend;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PrimaryController {

    @FXML
    private WebView webView;

    public void initialize() {

        WebEngine engine = webView.getEngine();

        // VERY IMPORTANT
        engine.setJavaScriptEnabled(true);

        engine.load("http://localhost:9090/");
    }
}