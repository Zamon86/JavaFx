package com.example.foldermaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;

public class HelloController {

    static String folderName;

    static ArrayList<CheckBox> checkboxesLvl1 = new ArrayList<>();
    static ArrayList<CheckBox> checkboxesAll = new ArrayList<>();
    static ArrayList<CheckBox> checkboxesLvl3 = new ArrayList<>();

    @FXML
    private Button checkOrderNumber;

    @FXML
    private Button addFoldersButton;

    @FXML
    private Button createNew;

    @FXML
    private Button createFolders;

    @FXML
    private CheckBox checkbox01;

    @FXML
    private CheckBox checkbox02;

    @FXML
    private CheckBox checkbox03;

    @FXML
    private CheckBox checkbox04;

    @FXML
    private CheckBox checkbox05;

    @FXML
    private CheckBox checkboxCSV;

    @FXML
    private CheckBox checkboxDXF;

    @FXML
    private CheckBox checkboxIGS;

    @FXML
    private CheckBox checkboxPDF;

    @FXML
    private CheckBox checkboxSF;

    @FXML
    private CheckBox checkboxXLSX;


    @FXML
    private TextField orderNumber;

    @FXML
    private TextField newOrderDescription;

    @FXML
    private TextField newOrderNumber;

    @FXML
    private Text textTop;


    @FXML
    void handleCheckButton(ActionEvent event) {
        if (isValidOrderNumber(orderNumber.getText())) {

            textTop.setVisible(false);

            if (doesTheFolderExist(orderNumber.getText())) {
                textTop.setText("Folder with number " + orderNumber.getText() + " exists: " + folderName);
                textTop.setFill(Paint.valueOf("green"));
                textTop.setVisible(true);
                addFoldersButton.setVisible(true);
            }

            else {
                textTop.setText("Folder with number " + orderNumber.getText() + " doesn't exists");
                textTop.setFill(Paint.valueOf("blue"));
                textTop.setVisible(true);
                createNew.setVisible(true);
            }

            checkboxesLvl1.add(checkbox01);
            checkboxesLvl1.add(checkbox02);
            checkboxesLvl1.add(checkbox03);
            checkboxesLvl1.add(checkbox04);
            checkboxesLvl1.add(checkbox05);
            checkboxesLvl3.add(checkboxCSV);
            checkboxesLvl3.add(checkboxDXF);
            checkboxesLvl3.add(checkboxIGS);
            checkboxesLvl3.add(checkboxPDF);
            checkboxesLvl3.add(checkboxXLSX);
            checkboxesAll.addAll(checkboxesLvl1);
            checkboxesAll.addAll(checkboxesLvl3);
            checkboxesAll.add(checkboxSF);

        }
        else {
            textTop.setVisible(false);
            textTop.setText("Wrong order number. Try again.");
            textTop.setFill(Paint.valueOf("red"));
            textTop.setVisible(true);
        }
    }

    @FXML
    void handleAddButton(ActionEvent event) {

    }

    @FXML
    void handleCreateButton(ActionEvent event) {
        orderNumber.setVisible(false);
        checkOrderNumber.setVisible(false);
        textTop.setVisible(false);
        createNew.setVisible(false);
        createFolders.setVisible(true);
        for (CheckBox checkBox : checkboxesAll) {
            checkBox.setVisible(true);
        }
        newOrderDescription.setVisible(true);
        newOrderNumber.setText(orderNumber.getText());
        newOrderNumber.setVisible(true);
    }

    @FXML
    void handleCreateFoldersButtons(ActionEvent event) {
        if (isValidOrderNumber(newOrderNumber.getText()) && newOrderDescription.getText().length()>0) {
            String mainFolderPath = "C:\\OneDrive\\Nooyen Group\\Production Planning - General\\WorkSpace\\Projects\\" + newOrderNumber.getText() + " " + newOrderDescription.getText();
            String folderPathSF;
            File mainFolder = new File(mainFolderPath);
            if (!mainFolder.exists()){
                mainFolder.mkdirs();
            }
            for (CheckBox checkBox : checkboxesLvl1) {
                if (checkBox.isSelected()) {
                    File folderLvl1 = new File(mainFolderPath + "\\" + checkBox.getText());
                    if (!folderLvl1.exists()){
                        folderLvl1.mkdirs();
                    }
                }
            }
            if (checkboxSF.isSelected()){
                folderPathSF = mainFolderPath + "\\03 - PDD\\SF_" + mainFolderPath.substring(mainFolderPath.lastIndexOf("\\") + 1);
                File folderLvl2 = new File(folderPathSF);
                if (!folderLvl2.exists()){
                    folderLvl2.mkdirs();
                }
                for (CheckBox checkBox : checkboxesLvl3) {
                    if (checkBox.isSelected()) {
                        File folderLvl1 = new File(folderPathSF + "\\" + checkBox.getText());
                        if (!folderLvl1.exists()){
                            folderLvl1.mkdirs();
                        }
                    }
                }

            }




        }

    }

    @FXML
    void handleCheckBox03(ActionEvent event) {
        if (!checkbox03.isSelected()) {
            checkboxSF.setSelected(false);
            handleSFCheckBox(event);
        }
    }


    @FXML
    void handleSFCheckBox(ActionEvent event) {
        if (checkboxSF.isSelected()) {
            checkbox03.setSelected(true);
            for (CheckBox checkBox : checkboxesLvl3) {
               checkBox.setDisable(false);
            }

        } else {
            for (CheckBox checkBox : checkboxesLvl3) {
                checkBox.setSelected(false);
                checkBox.setDisable(true);
            }

        }

    }

    private boolean isValidOrderNumber(String orderNumber) {

        try {
            if (orderNumber.length() == 8) {
                Double.parseDouble(orderNumber);
                return true;
            } else {
                return false;
            }
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean doesTheFolderExist(String orderNumber) {
        File[] directories = new File("C:\\OneDrive\\Nooyen Group\\Production Planning - General\\WorkSpace\\Projects").listFiles(File::isDirectory);
        if (directories == null) return false;
        for (File directory : directories) {
            if (directory.getName().contains(orderNumber)) {
                folderName = directory.getName();
                return true;
            }
        }

        return false;
    }




}