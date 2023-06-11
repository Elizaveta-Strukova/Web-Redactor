package com.example.html;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class HelloController {
    public WebView webview;
    public HTMLEditor htmleditor;
    public TextArea textarea;

    public TextField urltext;

    public void onOpen(ActionEvent actionEvent) throws FileNotFoundException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open HTML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder htmlContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    htmlContent.append(line).append("\n");
                }
                String html = htmlContent.toString();
                htmleditor.setHtmlText(html);
                webview.getEngine().loadContent(html);
                textarea.setText(html);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onSave(ActionEvent actionEvent) {
        textarea.setText(htmleditor.getHtmlText());
        webview.getEngine().loadContent(htmleditor.getHtmlText());
    }

    public void onURL(ActionEvent actionEvent) {
        String url = urltext.getText();
        if (isValidUrl(url)) {
            try {
                URL pageUrl = new URL(url);
                BufferedReader reader = new BufferedReader(new InputStreamReader(pageUrl.openStream()));
                StringBuilder htmlContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    htmlContent.append(line).append("\n");
                }
                reader.close();
                String html = htmlContent.toString();
                htmleditor.setHtmlText(html);
                webview.getEngine().loadContent(html);
                textarea.setText(html);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isValidUrl(String url) {
        // Add your URL validation logic here
        return url != null && !url.isEmpty();
    }


    public void onChange(ActionEvent actionEvent) {
        String html = htmleditor.getHtmlText();
        textarea.setText(html);
        webview.getEngine().loadContent(html);
    }

}
