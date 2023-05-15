package util;

public class Queries {
    public static String
            createEmployee = "INSERT INTO employee " +
            "(id, name, address, position, gender, username, password)" +
            " VALUES (?,?,?,?,?,?,?)",
            updateEmployee = "UPDATE employee " +
                    "SET name = ?, address = ?, position = ?, gender = ?, username = ?, password = ?" +
                    "WHERE id = ?",
            deleteEmployee = "DELETE FROM employee " +
                    "WHERE id = ?",
            checkUsername = "SELECT username, password FROM employee" + 
                    "WHERE username = ?",
            createStuff = "INSERT INTO stuff " +
                    "(code, name, price, quantity, brand)" +
                    " VALUES (?,?,?,?,?)",
            updateStuff = "UPDATE stuff " +
                    "SET name = ?, price = ?, quantity = ?, brand = ?" +
                    " WHERE code = ?",
            deleteStuff = "DELETE FROM stuff " +
                    "WHERE code = ?",
            lupaPass = "UPDATE employee " +
                    "SET password = ?" +
                    "WHERE username = ?";
}
