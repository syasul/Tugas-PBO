/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.Arrays;
import javax.swing.JOptionPane;
import models.dao.LupaPassDao;
import models.entity.Employee;
import util.Database;
import view.Login;
import view.LupaPassword;

/**
 *
 * @author User
 */
public class LupaPasswordController {
    private final LupaPassword view;
    private final LupaPassDao LupaPassDao = new LupaPassDao(Database.getConnection());
    private final Employee employee = Employee.create();

    public LupaPasswordController(LupaPassword lupaPassword) {
        this.view = lupaPassword;
    }

    public void registerEvents() {
        view.getUbah().addActionListener(e -> {
             if (!view.getUsername().getText().equals("")) {
                this.setUserInput();
                var result = LupaPassDao.updateEmployee(employee);
                this.reloadAfterAction("merubah data!", result);
            } else {
                JOptionPane.showMessageDialog(view, "Harus memilih data yang akan dirubah!");
            }
        });
        
        view.getReset().addActionListener(e -> {
            this.resetUserInput();
        });
        
        view.getKembali().addActionListener(e -> {
           view.dispose();
           var destination = new Login();
           destination.setVisible(true);
        });
    }
    
    private void prepareDataUpdate(Employee employee) {
        view.getUsername().setText(employee.getUsername());
        view.getPassword().setText(employee.getPassword());
    }
    
    private void setUserInput() {
        employee.setUsername(view.getUsername().getText());
        employee.setPassword(Arrays.toString(view.getPassword().getPassword()));
    }

    private void resetUserInput() {
        view.getUsername().setText("");
        view.getPassword().setText("");
    }
    
    private void reloadAfterAction(String message, boolean isSuccess) {
        String catchMessage = (isSuccess ? "Berhasil " : "Gagal ") + message;
        JOptionPane.showMessageDialog(view, catchMessage);

        if (isSuccess) {
            this.resetUserInput();
            
        }
    }

    
    
}
