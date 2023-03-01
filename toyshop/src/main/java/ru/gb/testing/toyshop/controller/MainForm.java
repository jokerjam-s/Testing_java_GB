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
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
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
    private Button buttonDelete;

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

    @FXML
    private TableColumn<Prize, Integer> colPrizeId;
    @FXML
    private TableColumn<Prize, String> colPrizeName;
    @FXML
    private TableColumn<Prize, Boolean> colPrizeGiven;


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

    /**
     * Заполнение таблиц данными
     */
    private void fillData() {
        ObservableList<Toy> toys = toyShop.fillToys();

        colToyID.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("toyId"));
        colToyName.setCellValueFactory(new PropertyValueFactory<Toy, String>("toyName"));
        colToyCnt.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("toyCount"));
        colToyRange.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("toyRate"));

        tableToys.setItems(toys);

        ObservableList<Prize> prizes = toyShop.fillPrizes();

        colPrizeId.setCellValueFactory(new PropertyValueFactory<Prize, Integer>("prizeId"));
        colPrizeName.setCellValueFactory(new PropertyValueFactory<Prize, String>("prizeName"));
        colPrizeGiven.setCellValueFactory(new PropertyValueFactory<Prize, Boolean>("prizeGiven"));

        tablePrize.setItems(prizes);
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
                toyFormController.setToy(new Toy());
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
                    toyShop.updateToy(selectedToy);
                }
                break;

            case "buttonDelete":
                if (selectedToy == null) {
                    DialogManager.showInfoDialog("Внимание!", "Игрушка не выбрана!");
                    return;
                }

                Optional<ButtonType> result =
                        DialogManager.showConfirmDialog("Удаление", "Удалить выбранную запись?");
                if (result.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                    toyShop.deleteToy(selectedToy);
                }

                break;

            case "buttonGame":
                Game();
                break;

            case "buttonGetPrize":
                if(selectedPrize == null ){
                    DialogManager.showInfoDialog("Внимание!", "Приз не выбран!");
                    return;
                }

                if(!selectedPrize.isPrizeGiven()) {
                    selectedPrize.setPrizeGiven(true);
                    toyShop.updatePrize(selectedPrize);
                }else {
                    DialogManager.showInfoDialog("Внимание!", "Приз уже выдан!");
                }
                break;

        }
    }

    /**
     * розыгрыш призов
     */
    private void Game() {
        if (toyShop.getToyCount() == 0) {
            DialogManager.showInfoDialog("Розыгрыш", "Cписок игрушек пуст! Розыгрыш невозможен.");
            return;
        }

        Queue<Toy> toyQueue = new PriorityQueue<>();
        toyQueue.addAll(toyShop.getToyList());

        if (!toyQueue.isEmpty()) {
            Toy prizeToy = toyQueue.poll();
            Prize prize = new Prize(prizeToy.getToyName(), false);
            prizeToy.setToyCount(prizeToy.getToyCount() - 1);
            toyShop.addPrize(prize);
            if (prizeToy.getToyCount() == 0) {
                toyShop.deleteToy(prizeToy);
            } else {
                toyShop.updateToy(prizeToy);
            }
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
