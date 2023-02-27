package ru.gb.testing.toyshop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.gb.testing.toyshop.data.Toy;

public class MainForm {
    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonGame;

    @FXML
    private Button buttonEdit;

    @FXML
    private TableColumn<Toy, Integer> colToyID;

    @FXML
    private TableColumn<Toy, String> colToyName;

    @FXML
    private TableColumn<Toy, Integer> colToyCnt;

    @FXML
    private TableColumn<Toy, Double> colToyRange;

}
