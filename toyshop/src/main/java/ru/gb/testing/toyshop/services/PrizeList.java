package ru.gb.testing.toyshop.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.gb.testing.toyshop.data.Prize;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Список призов
 */
public class PrizeList implements DateList<Prize> {
    private final ObservableList<Prize> prizes;

    public PrizeList() {
        prizes = FXCollections.observableArrayList();;
    }

    /**
     * Получить список
     *
     * @return
     */
    @Override
    public List<Prize> getList() {
        return prizes;
    }

    /**
     * добавление элемента в список
     *
     * @param item добавляемый элемент
     */
    @Override
    public void addItem(Prize item) {
        prizes.add(item);
    }

    /**
     * получение элемента по наименованию приза
     *
     * @param name название для поиска
     * @return
     */
    @Override
    public Prize getItem(String name) {
        Prize prize;

        try{
            prize = prizes.stream().filter(x -> x.getPrizeName().equals(name)).findFirst().get();
        } catch (NoSuchElementException exception){
            prize = null;
        }

        return prize;
    }

    /**
     * получение элемента по индексу
     *
     * @param index индекс для поиска элемента
     * @return
     */
    @Override
    public Prize getItem(int index) {
        return prizes.get(index);
    }

    /**
     * Удаление элемента item из списка
     *
     * @param item удаляемый элемент
     * @return
     */
    @Override
    public void deleteItem(Prize item) {
        prizes.remove(item);
    }

    /**
     * Удаление элемента из списка с индексом
     *
     * @param index индекс удаляемого элемента
     * @return
     */
    @Override
    public void deleteItem(int index) {
        prizes.remove(index);
    }
}
