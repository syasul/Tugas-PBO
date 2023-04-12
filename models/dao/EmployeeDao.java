package models.dao;

import models.entity.Employee;
import util.Queries;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    private final Connection connection;

    public EmployeeDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createEmployee(Employee employee) {
        try {
            var statement = connection.prepareStatement(Queries.createEmployee);
            statement.setString(1, employee.getIdCard());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getAddress());
            statement.setString(4, employee.getPosition());
            statement.setString(5, employee.getGender());
            statement.setString(6, employee.getUsername());
            statement.setString(7, employee.getPassword());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Error dao employee : " + exception.getMessage());
            return false;
        }
    }

    public boolean updateEmployee(Employee employee) {
        try {
            var statement = connection.prepareStatement(Queries.updateEmployee);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getAddress());
            statement.setString(3, employee.getPosition());
            statement.setString(4, employee.getGender());
            statement.setString(5, employee.getUsername());
            statement.setString(6, employee.getPassword());
            statement.setString(7, employee.getIdCard());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Error dao employee : " + exception.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(Employee employee) {
        try {
            var statement = connection.prepareStatement(Queries.deleteEmployee);
            statement.setString(1, employee.getIdCard());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Error dao employee : " + exception.getMessage());
            return false;
        }
    }

    public List<Employee> getEmployees() {
        List<Employee> employeeList = new ArrayList<>();

        try {
            var statement = connection.prepareStatement("SELECT * FROM employee");
            var result = statement.executeQuery();

            while (result.next()) {
                var employee = Employee.create();
                employee.setIdCard(result.getString("id"));
                employee.setAddress(result.getString("address"));
                employee.setName(result.getString("name"));
                employee.setPosition(result.getString("position"));
                employee.setUsername(result.getString("username"));
                employee.setPassword(result.getString("password"));
                employee.setGender(result.getString("gender"));
                employeeList.add(employee);
            }

            result.close();
            statement.close();
        } catch (SQLException exception) {
            System.out.println("Error get employee : " + exception.getMessage());
        }

        return employeeList;
    }
}
