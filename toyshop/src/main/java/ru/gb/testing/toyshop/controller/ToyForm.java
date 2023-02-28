package ru.gb.testing.toyshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.gb.testing.toyshop.data.Toy;
import ru.gb.testing.toyshop.utils.DialogManager;

import java.util.regex.Pattern;

public class ToyForm {
    // редактируемый объект
    private Toy toy;
    // определение нажатия ввода / изменения данных
    private boolean okClicked = false;

    @FXML
    private TextField txName;

    @FXML
    private TextField txCount;

    @FXML
    private TextField txRange;

    @FXML
    private Button buttonOk;

    @FXML
    private Button buttonCancel;

    /**
     * ввернуть объект
     *
     * @return добавленная/измененная игрушка
     */
    public Toy getToy() {
        return toy;
    }

    /**
     * установить объект
     *
     * @param toy - игрушка для изменения
     */
    public void setToy(Toy toy) {
        if (toy == null) {
            return;
        }

        this.toy = toy;

        this.txName.setText(this.toy.getToyName());
        this.txCount.setText(String.valueOf(this.toy.getToyCount()));
        this.txRange.setText(String.valueOf(this.toy.getToyRate()));
    }

    /**
     * закрывает окно
     *
     * @param actionEvent
     */
    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();   // скрываем внешний контейнер диалогового окна (где находится Scene с UI)
    }

    /**
     * сохранение изменений и закрытие окна
     *
     * @param actionEvent
     */
    public void actionOk(ActionEvent actionEvent) {
        if (!validate()) {
            return;
        }

        try {
            toy.setToyName(txName.getText());
            toy.setToyCount(Integer.parseInt(txCount.getText()));
            toy.setToyRate(Integer.parseInt(txRange.getText()));
        } catch (Exception ex) {
            DialogManager.showErrorDialog("Ошибка!", ex.getMessage());
        }

        okClicked = true;
        actionClose(actionEvent);
    }

    /**
     * если нажали Ok, а не просто закрыли окно, значит есть изменения (условие проверяется в родительском окне)
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * проверка валидности данных
     *
     * @return результат проверки
     */
    private boolean validate() {


        String errTitleData = "Ошибка данных!";
        if (txName.getText().length() == 0) {
            DialogManager.showErrorDialog(errTitleData, "Наименование игрушки не должно быть пустым!");
            return false;
        }

        if (txCount.getText().length() == 0) {
            DialogManager.showErrorDialog(errTitleData, "Количество не должно быть пустым!");
            return false;
        } else if (!checkInt(txCount.getText(), "[0-9]+")) {
            DialogManager.showErrorDialog(errTitleData, "Неверно задано Количество (>=0)!");
            return false;
        }

        if (txRange.getText().length() == 0) {
            DialogManager.showErrorDialog(errTitleData, "Частота не должна быть пустым!");
            return false;
        } else if (!checkInt(txRange.getText(), "^([1-9])|([1-9][0-9])|(100)$")) {
            DialogManager.showErrorDialog(errTitleData, "Неверно задана Частота [1..100]! ");
            return false;
        }

        return true;
    }

    /**
     * проверка на соответствие значений int, использует regexp
     *
     * @param strValue - проверяемое значение
     * @return - результат проверки
     */
    private boolean checkInt(String strValue, String regPattern) {
        Pattern pattern = Pattern.compile(regPattern);

        return pattern.matcher(strValue).matches();
    }

}
