package com.example.foldermaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Desktop;
import java.net.URI;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class HelloController {

    static String folderName;

    static ArrayList<CheckBox> checkboxesLvl1 = new ArrayList<>();
    static ArrayList<CheckBox> checkboxesAll = new ArrayList<>();
    static ArrayList<CheckBox> checkboxesLvl3 = new ArrayList<>();
    static ArrayList<Button> buttons = new ArrayList<>();
    static ArrayList<TextField> textFields = new ArrayList<>();

    static ArrayList<String> activities = new ArrayList<>();


    @FXML
    private void initialize(){
        activityChoiceBox.getItems().addAll("General work preparation F&K - VBA","Drawing F&K - VTE","Drawing project Roosters - U300","Drawing production Roosters - U301");
        activityChoiceBox.setValue("General work preparation F&K - VBA");
    }

    @FXML
    public ChoiceBox<String> activityChoiceBox;

    @FXML
    private Button checkOrderNumber;

    @FXML
    private Button addFoldersButton;

    @FXML
    private Button createNewButton;

    @FXML
    private Button createFolders;

    @FXML
    private Button resetButton;

    @FXML
    private Button sendNTTButton;

    @FXML
    private Button createSubFolders;

    @FXML
    private Button openButton;

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
    void handleOrderNumberTextField(KeyEvent event) {
        if (KeyCode.ENTER == event.getCode()) {
           handleCheckButton(new ActionEvent());
        }
    }

    @FXML
    void handleCheckButton(ActionEvent event) {
        if (isValidOrderNumber(orderNumber.getText())) {

            textTop.setVisible(false);
            setArrayLists();
            activityChoiceBox.setVisible(true);
            sendNTTButton.setVisible(true);
            openButton.setVisible(true);

            if (doesTheFolderExist(orderNumber.getText())) {
                textTop.setText("Folder with number " + orderNumber.getText() + " exists: " + folderName);
                textTop.setFill(Paint.valueOf("green"));
                textTop.setVisible(true);
                addFoldersButton.setVisible(true);
                createNewButton.setVisible(false);
            }

            else {
                textTop.setText("Folder with number " + orderNumber.getText() + " doesn't exists");
                textTop.setFill(Paint.valueOf("blue"));
                textTop.setVisible(true);
                createNewButton.setVisible(true);
                addFoldersButton.setVisible(false);
            }

        }
        else {
            textTop.setVisible(false);
            textTop.setText("Wrong order number. Try again.");
            textTop.setFill(Paint.valueOf("red"));
            textTop.setVisible(true);
            addFoldersButton.setVisible(false);
            createNewButton.setVisible(false);
        }
    }

    @FXML
    void handleAddButton(ActionEvent event) {

        activityChoiceBox.setVisible(false);
        sendNTTButton.setVisible(false);
        openButton.setVisible(false);
        orderNumber.setVisible(false);
        checkOrderNumber.setVisible(false);
        createNewButton.setVisible(false);
        addFoldersButton.setVisible(false);
        createSubFolders.setVisible(true);
        for (CheckBox checkBox : checkboxesAll) {
            checkBox.setVisible(true);
            checkBox.setDisable(checkboxesLvl3.contains(checkBox));
        }


        File[] directories = new File("C:\\OneDrive\\Nooyen Group\\Production Planning - General\\WorkSpace\\Projects\\" + folderName).listFiles(File::isDirectory);
        ArrayList<String> namesOfDirectories = new ArrayList<>();
        File sf = new File("C:\\OneDrive\\Nooyen Group\\Production Planning - General\\WorkSpace\\Projects\\" + folderName + "\\03 - PDD\\SF_" + folderName);
        if (sf.exists()) {
            File[] directoriesLvl3 = sf.listFiles((File::isDirectory));

            checkboxSF.setSelected(true);
            checkboxSF.setDisable(true);
            handleSFCheckBox(event);

            if (directoriesLvl3 != null) {
                for (File directory : directoriesLvl3) {
                    namesOfDirectories.add(directory.getName());
                }
            }

        }

        for (CheckBox checkBox : checkboxesLvl3) {

            if (namesOfDirectories.contains(checkBox.getText())) {
                checkBox.setSelected(true);
                checkBox.setDisable(true);
            }
        }

        if (directories != null) {
            for (File directory : directories) {
                namesOfDirectories.add(directory.getName());
            }
            for (CheckBox checkBox : checkboxesLvl1) {

                if (namesOfDirectories.contains(checkBox.getText())) {
                    checkBox.setSelected(true);
                    checkBox.setDisable(true);
                }
            }

        }


    }
    @FXML
    void handleCreateSubFoldersButton(ActionEvent event) {

        String mainFolderPath = "C:\\OneDrive\\Nooyen Group\\Production Planning - General\\WorkSpace\\Projects\\" + folderName;
        String folderPathSF = mainFolderPath + "\\03 - PDD\\SF_" + folderName;

        for (CheckBox checkBox : checkboxesLvl1) {
            if (checkBox.isSelected() && !checkBox.isDisabled()) {
                File folderLvl1 = new File(mainFolderPath + "\\" + checkBox.getText());
                if (!folderLvl1.exists()) {
                    folderLvl1.mkdirs();
                }
            }
        }
        if (checkboxSF.isSelected() && !checkboxSF.isDisabled()) {
            File folderLvl2 = new File(folderPathSF);
            if (!folderLvl2.exists()) {
                folderLvl2.mkdirs();
            }
        }

        for (CheckBox checkBox : checkboxesLvl3) {
            if (checkBox.isSelected() && !checkBox.isDisabled()) {
                File folderLvl1 = new File(folderPathSF + "\\" + checkBox.getText());
                if (!folderLvl1.exists()) {
                    folderLvl1.mkdirs();
                }
            }
        }


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setContentText("The selected subfolders have been created");
        alert.setHeaderText("Success");
        alert.showAndWait();

    }



    @FXML
    void handleResetButton(ActionEvent event) {
        resetAppStatus();

    }

    @FXML
    void handleCreateButton(ActionEvent event) {

        activityChoiceBox.setVisible(false);
        sendNTTButton.setVisible(false);
        openButton.setVisible(false);
        orderNumber.setVisible(false);
        checkOrderNumber.setVisible(false);
        textTop.setVisible(false);
        createNewButton.setVisible(false);
        createFolders.setVisible(true);
        for (CheckBox checkBox : checkboxesAll) {
            checkBox.setVisible(true);
            checkBox.setDisable(checkboxesLvl3.contains(checkBox));

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

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("The selected folders have been created");
            alert.setHeaderText("Success");
            alert.showAndWait();

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
    void handleSFCheckBox(ActionEvent ignore) {
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

    @FXML
    void handleOpenButton(ActionEvent ignore) throws IOException {
        Desktop.getDesktop().open(new File("C:\\OneDrive\\Nooyen Group\\Production Planning - General\\WorkSpace\\Projects\\" + folderName));
    }
    @FXML
    void handleSendToNTT(ActionEvent event) {
        //"General work preparation F&K - VBA","Drawing F&K - VTE","Drawing project Roosters - U300","Drawing production Roosters - U301"


        String activity = "";
        for (String s : activities) {
            if (activityChoiceBox.getValue().contains(s)) {
                activity = s;
            }
        }

        String url = "https://ntt.nooyen.com/WebClient/SelectMachineNr/CreateTimeTrackEntry?empID=1081&orderNr=" + orderNumber.getText() + "&machineNr=" + activity;
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(URI.create(url));

            } catch (Exception e) {
                e.printStackTrace();
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

    void resetAppStatus() {
        for (CheckBox checkBox : checkboxesAll) {
            checkBox.setVisible(false);
            checkBox.setSelected(false);
        }
        for (Button button : buttons) {
            button.setVisible(false);
        }
        for (TextField textField : textFields) {
            textField.setVisible(false);
        }


        checkOrderNumber.setVisible(true);
        orderNumber.setText("");
        orderNumber.setVisible(true);
        newOrderDescription.setText("");
        newOrderNumber.setText("");
        resetButton.setVisible(true);
        textTop.setVisible(false);
        textTop.setText("");
        activityChoiceBox.setVisible(false);

    }

    void setArrayLists() {
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
        activities.addAll(List.of("U303", "DIV", "LAS", "PNM", "U301", "U300", "VTE", "U302", "VBA"));


        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().isAssignableFrom(Button.class)) {
                    buttons.add((Button)field.get(this));
                } else if (field.getType().isAssignableFrom(TextField.class)) {
                    textFields.add((TextField)field.get(this));
                }

            }
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException");
        }

    }




}