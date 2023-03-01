package ru.gb.testing.toyshop.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.gb.testing.toyshop.data.Toy;

import java.util.List;
import java.util.NoSuchElementException;

public class ToyList implements DateList<Toy> {
    private final ObservableList<Toy> toys;

    public ToyList() {
        toys = FXCollections.observableArrayList();
    }

    /**
     * Получить список
     *
     * @return
     */
    @Override
    public List<Toy> getList() {
        return toys;
    }

    /**
     * добавление элемента в список
     *
     * @param item добавляемый элемент
     */
    @Override
    public void addItem(Toy item) {
        toys.add(item);
    }

    /**
     * получение элемента по наименованию
     * возвращает первый подходящий элемент либо null, если не существует
     * @param name название игрушки для поиска
     * @return
     */
    @Override
    public Toy getItem(String name){
        Toy item;
        try{
            item = toys.stream().filter(x -> x.getToyName().equals(name)).findFirst().get();
        }catch (NoSuchElementException exception){
            item = null;
        }
        return item;
    }

    /**
     * получение элемента по индексу
     *
     * @param index индекс для поиска
     * @return
     */
    @Override
    public Toy getItem(int index) {
        return toys.get(index);
    }

    /**
     * Удаление элемента item из списка
     *
     * @param item удаляемый элемент
     * @return
     */
    @Override
    public void deleteItem(Toy item) {
        toys.remove(item);
    }

    /**
     * Удаление элемента из списка с индексом
     *
     * @param index
     * @return
     */
    @Override
    public void deleteItem(int index) {
        toys.remove(index);
    }

}
