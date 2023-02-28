package ru.gb.testing.toyshop.services;

import javafx.collections.ObservableList;
import ru.gb.testing.toyshop.data.Prize;
import ru.gb.testing.toyshop.data.SQLiteConnection;
import ru.gb.testing.toyshop.data.Toy;

import java.sql.*;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * магазин игрушек на SQLite
 */
public class SqlToyShop {
    private final ToyList toyList = new ToyList();
    private final PrizeList prizeList = new PrizeList();

    public ObservableList<Toy> fillToys(){
        toyList.getList().clear();

        try(Connection connect = SQLiteConnection.getConnection();
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery("SELECT toyId, toyName, toyCnt, toyRate FROM toy")
        ){
            while (result.next()){
                Toy toy = new Toy();
                toy.setToyId(result.getInt("toyId"));
                toy.setToyCount(result.getInt("toyCnt"));
                toy.setToyRate(result.getInt("toyRate"));
                toy.setToyName(result.getString("toyName"));

                toyList.addItem(toy);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (ObservableList<Toy>) toyList.getList();
    }

    /**
     * добавить игрушку
     *
     * @param toy игрушка для добавления
     * @return true если добавление в БД успешно, иначе false
     */
    public boolean addToy(Toy toy) {
        try(Connection conect = SQLiteConnection.getConnection();
            PreparedStatement statement = conect.prepareStatement(
                    "INSERT INTO toy (toyName, toyCnt, toyRate) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, toy.getToyName());
            statement.setInt(2, toy.getToyCount());
            statement.setInt(3, toy.getToyRate());

            int result = statement.executeUpdate();
            if(result > 0) {
                toy.setToyId(result);
                toyList.addItem(toy);
                return true;
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
        }

        return false;
    }

    /**
     * получить игрушку по ее id
     *
     * @param toyId
     * @return
     */
    public Toy getToy(int toyId) {
        Toy toy;
        try {
            toy = toyList.getList().stream().filter(t -> t.getToyId() == toyId).findFirst().get();
        } catch (NoSuchElementException exception) {
            toy = null;
        }

        return toy;
    }

    /**
     * Получить
     *
     * @param prizeId
     * @return
     */
    public Prize getPrize(int prizeId) {
        Prize prize;
        try {
            prize = prizeList.getList().stream().filter(t -> t.getPrizeId() == prizeId).findFirst().get();
        } catch (NoSuchElementException exception) {
            prize = null;
        }

        return prize;
    }

    public void updateToy(Toy toy) {

    }

    public void runGame() {

    }

    public void prizeGive(int priseId) {

    }
}
