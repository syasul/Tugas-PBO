package models.dao;

import models.entity.Employee;
import models.entity.Stuff;
import util.Queries;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StuffDao {
    private final Connection connection;

    public StuffDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createStuff(Stuff stuff) {
        try {
            var statement = connection.prepareStatement(Queries.createStuff);
            statement.setString(1, stuff.getCode());
            statement.setString(2, stuff.getName());
            statement.setDouble(3, stuff.getPrice());
            statement.setInt(4, stuff.getCount());
            statement.setString(5, stuff.getBrand());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Error dao stuff : " + exception.getMessage());
            return false;
        }
    }

    public boolean updateStuff(Stuff stuff) {
        try {
            var statement = connection.prepareStatement(Queries.updateStuff);
            statement.setString(5, stuff.getCode());
            statement.setString(1, stuff.getName());
            statement.setDouble(2, stuff.getPrice());
            statement.setInt(3, stuff.getCount());
            statement.setString(4, stuff.getBrand());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Error dao stuff : " + exception.getMessage());
            return false;
        }
    }

    public boolean deleteStuff(Stuff stuff) {
        try {
            var statement = connection.prepareStatement(Queries.deleteStuff);
            statement.setString(1, stuff.getCode());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Error dao stuff : " + exception.getMessage());
            return false;
        }
    }

    public List<Stuff> getStuff() {
        List<Stuff> stuffList = new ArrayList<>();

        try {
            var statement = connection.prepareStatement("SELECT * FROM stuff");
            var result = statement.executeQuery();

            while (result.next()) {
                var stuff = Stuff.create();
                stuff.setCode(result.getString("code"));
                stuff.setName(result.getString("name"));
                stuff.setPrice(result.getDouble("price"));
                stuff.setCount(result.getInt("quantity"));
                stuff.setBrand(result.getString("brand"));
                stuffList.add(stuff);
            }

            result.close();
            statement.close();
        } catch (SQLException exception) {
            System.out.println("Error get stuff : " + exception.getMessage());
        }

        return stuffList;
    }
}
