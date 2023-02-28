package ru.gb.testing.toyshop.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.gb.testing.toyshop.ToyApplication;
import ru.gb.testing.toyshop.data.Prize;
import ru.gb.testing.toyshop.data.Toy;
import ru.gb.testing.toyshop.services.SqlToyShop;
import ru.gb.testing.toyshop.utils.DialogManager;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    @FXML
    private Button buttonGetPrize;
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
    private TableColumn<Toy, Integer> colToyRange;

    @FXML
    private TableView tableToys;

    @FXML
    private TableView tablePrize;

    private ToyForm toyFormController;
    private Stage toyFormStage;
    private Parent fxmlEdit;

    private SqlToyShop toyShop;


    // вызывается автоматически при загрузке окна
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        toyShop = new SqlToyShop();
        fillData(); // начальная загрузка данных
        //initListeners(); // слушатели изменений данных (можем обновлять компоненты)
        initLoaders(); // инициализируем все другие окна, которые участвуют в приложении
    }

    private void fillData(){
        ObservableList<Toy> toys = toyShop.fillToys();

        colToyID.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("toyId"));
        colToyName.setCellValueFactory(new PropertyValueFactory<Toy, String>("toyName"));
        colToyCnt.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("toyCnt"));
        colToyRange.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("toyRate"));

        tableToys.setItems(toys);
    }


    // какие другие окна будут загружаться
    private void initLoaders() {
        try {
            FXMLLoader editFxmlLoader = new FXMLLoader(ToyApplication.class.getResource("toy-form.fxml")); // окно редактирования
            fxmlEdit = editFxmlLoader.load();
            toyFormController = editFxmlLoader.getController(); // это важно получить, чтобы установить person для редактирования/создания

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // обработчик нажатия кнопок
    @FXML
    private void buttonActionPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        // если нажата не кнопка - выходим из метода
        if (!(source instanceof Button)) {
            return;
        }

        // текущие выбранные объекты из tableView
        Toy selectedToy = (Toy) tableToys.getSelectionModel().getSelectedItem();
        Prize selectedPrize = (Prize) tablePrize.getSelectionModel().getSelectedItem();

        Button pressedButton = (Button) source;

        // смотрим нажатую кнопку
        switch (pressedButton.getId()) {
            case "buttonAdd":
                toyFormController.setToy(new Toy("", 0, 0));
                showToyForm();

                // если выход из формы игрушек по Ok
                if (toyFormController.isOkClicked()) {
                    toyShop.addToy(toyFormController.getToy());
                }

                break;

            case "buttonEdit":
                if (selectedToy == null) {
                    DialogManager.showInfoDialog("Внимание!", "Игрушка не выбрана!");
                    return;
                }

                toyFormController.setToy(selectedToy);
                showToyForm();

                // если выход из формы игрушек по Ok
                if (toyFormController.isOkClicked()) {
                    // TODO: 28.02.2023 добавить обработку изменеия игрушки в списоке
                }
                break;
        }
    }

    // отображение формы окна игрушек
    private void showToyForm() {
        if (toyFormStage == null) {
            toyFormStage = new Stage();
            toyFormStage.setTitle("Редактировать");
            toyFormStage.setMinHeight(150);
            toyFormStage.setMinWidth(300);
            toyFormStage.setResizable(false);
            toyFormStage.setScene(new Scene(fxmlEdit));
            toyFormStage.initModality(Modality.WINDOW_MODAL);
        }

        toyFormStage.showAndWait(); // для ожидания закрытия окна
    }

}
