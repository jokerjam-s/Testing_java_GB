package ru.gb.testing.toyshop.services;


import java.util.List;

/**
 * Интерфейс организации списка игрушек / призов
 */
public interface DateList<E>  {

    /**
     * Получить список
     * @return
     */
    public List<E> getList();

    /**
     * добавление элемента в список
     * @param item добавляемый элемент
     */
    public void addItem(E item);

    /**
     * получение элемента по наименованию
     * @param name название для поиска
     * @return
     */
    public E getItem(String name);

    /**
     * получение элемента по индексу
     * @param index индекс для поиска элемента
     * @return
     */
    public E getItem(int index);

    /**
     * Удаление элемента item из списка
     * @param item удаляемый элемент
     * @return
     */
    public void deleteItem(E item);

    /**
     * Удаление элемента из списка с индексом
     * @param index индекс удаляемого элемента
     * @return
     */
    public void deleteItem(int index);

}
