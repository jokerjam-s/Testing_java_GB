package ru.gb.testing.toyshop.services;

import javafx.collections.ObservableList;
import ru.gb.testing.toyshop.data.Prize;
import ru.gb.testing.toyshop.data.SQLiteConnection;
import ru.gb.testing.toyshop.data.Toy;

import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * магазин игрушек на SQLite
 */
public class SqlToyShop {
    private final ToyList toyList = new ToyList();
    private final PrizeList prizeList = new PrizeList();

    /**
     * Чтение списка игруше из БД
     *
     * @return
     */
    public ObservableList<Toy> fillToys() {
        toyList.getList().clear();

        try (Connection connect = SQLiteConnection.getConnection();
             Statement statement = connect.createStatement();
             ResultSet result = statement.executeQuery("SELECT toyId, toyName, toyCnt, toyRate FROM toy")
        ) {
            while (result.next()) {
                Toy toy = new Toy();
                toy.setToyId(result.getInt("toyId"));
                toy.setToyCount(result.getInt("toyCnt"));
                toy.setToyRate(result.getInt("toyRate"));
                toy.setToyName(result.getString("toyName"));

                toyList.addItem(toy);
            }

        } catch (SQLException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
        }

        return (ObservableList<Toy>) toyList.getList();
    }

    public int getPrizeCount(){
        return prizeList.getList().size();
    }

    public int getToyCount(){
        return toyList.getList().size();
    }

    public List<Toy> getToyList(){
        return toyList.getList();
    }

    public List<Prize> getPrizeList(){
        return prizeList.getList();
    }

    /**
     * Чтение списка призов из БД
     *
     * @return
     */
    public ObservableList<Prize> fillPrizes() {
        prizeList.getList().clear();

        try (Connection connect = SQLiteConnection.getConnection();
             Statement statement = connect.createStatement();
             ResultSet result = statement.executeQuery("SELECT prizeId, prizeName, prizeGiven FROM prize")
        ) {
            while (result.next()) {
                Prize prize = new Prize();
                prize.setPrizeId(result.getInt("prizeId"));
                prize.setPrizeName(result.getString("prizeName"));
                prize.setPrizeGiven(result.getBoolean("prizeGiven"));

                prizeList.addItem(prize);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
        }

        return (ObservableList<Prize>) prizeList.getList();
    }

    /**
     * добавить игрушку
     *
     * @param toy игрушка для добавления
     * @return true если добавление в БД успешно, иначе false
     */
    public boolean addToy(Toy toy) {
        try (Connection conect = SQLiteConnection.getConnection();
             PreparedStatement statement = conect.prepareStatement(
                     "INSERT INTO toy (toyName, toyCnt, toyRate) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, toy.getToyName());
            statement.setInt(2, toy.getToyCount());
            statement.setInt(3, toy.getToyRate());

            int result = statement.executeUpdate();
            if (result > 0) {
                int id = statement.getGeneratedKeys().getInt(1);    // подучить новый ключ
                toy.setToyId(id);
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

    public boolean updateToy(Toy toy) {
        if (toy == null) {
            return false;
        }

        try (Connection connect = SQLiteConnection.getConnection();
             PreparedStatement statement = connect.prepareStatement(
                     "UPDATE toy SET toyName=?, toyCnt=?, toyRate=? WHERE toyId=?")
        ) {
            statement.setString(1, toy.getToyName());
            statement.setInt(2, toy.getToyCount());
            statement.setInt(3, toy.getToyRate());
            statement.setInt(4, toy.getToyId());

            int result = statement.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
        }
        return false;
    }

    public boolean deleteToy(Toy toy) {
        if (toy == null) {
            return false;
        }

        try (Connection connect = SQLiteConnection.getConnection();
             PreparedStatement statement = connect.prepareStatement(
                     "DELETE FROM toy WHERE toyId=?")
        ) {
            statement.setInt(1, toy.getToyId());

            int result = statement.executeUpdate();
            if (result > 0) {
                toyList.deleteItem(toy);
                return true;
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
        }
        return false;
    }

    public boolean updatePrize(Prize prize) {
        if (prize == null) {
            return false;
        }

        try (Connection connect = SQLiteConnection.getConnection();
             PreparedStatement statement = connect.prepareStatement(
                     "UPDATE prize SET prizeName=?, prizeGiven=? WHERE prizeId=?")) {
            statement.setString(1, prize.getPrizeName());
            statement.setBoolean(2, prize.isPrizeGiven());
            statement.setInt(3, prize.getPrizeId());

            int result = statement.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
        }
        return false;
    }

    public boolean addPrize(Prize prize) {
        if (prize == null) {
            return false;
        }

        try (Connection connect = SQLiteConnection.getConnection();
             PreparedStatement statement = connect.prepareStatement(
                     "INSERT INTO prize (prizeName, prizeGiven) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, prize.getPrizeName());
            statement.setBoolean(2, prize.isPrizeGiven());

            int result = statement.executeUpdate();
            if (result > 0) {
                int id = statement.getGeneratedKeys().getInt(1);
                prize.setPrizeId(id);
                prizeList.addItem(prize);
                return true;
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
        }
        return false;
    }
}
