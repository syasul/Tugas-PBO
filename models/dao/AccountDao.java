/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.entity.Employee;
import util.Queries;

/**
 *
 * @author User
 */
public class AccountDao  {
    private final Connection connection;

    public AccountDao(Connection connection) {
        this.connection = connection;
    }

    public boolean login(Employee employee) {
        try {
            var result = this.find(employee.getUsername());
            if (!result.next()) return false;
            if (!employee.getUsername().equals(result.getString("password"))) return false;
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    public boolean isUsernameExist(String username) {
        try {
            var result = this.find(username);
            return result.next();
        } catch (SQLException exception) {
            return false;
        }
    }

    private ResultSet find(String username) throws SQLException {
        var statement = connection.prepareStatement(Queries.checkUsername);
        statement.setString(1, username);
        return statement.executeQuery();
    }
}
