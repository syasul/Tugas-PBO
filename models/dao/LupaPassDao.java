/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.dao;

import java.sql.Connection;
import java.sql.SQLException;
import models.entity.Employee;
import util.Queries;

/**
 *
 * @author User
 */
public class LupaPassDao {
    private final Connection connection;

    public LupaPassDao(Connection connection) {
        this.connection = connection;
    }

    public boolean updateEmployee(Employee employee) {
        try {
            var statement = connection.prepareStatement(Queries.lupaPass);
            statement.setString(1, employee.getUsername());
            statement.setString(2, employee.getPassword());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Error dao employee : " + exception.getMessage());
            return false;
        }
    }

    
}
