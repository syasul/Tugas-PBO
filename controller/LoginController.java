/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import contract.Controller;
import javax.swing.JOptionPane;
import models.dao.AccountDao;
import models.entity.Employee;
import util.Database;
import view.EmployeeForm;
import view.Login;
import view.LupaPassword;


/**
 *
 * @author User
 */
public class LoginController implements Controller {
    private final Login view;
    private final AccountDao accountDao = new AccountDao(Database.getConnection());
    private final Employee account = Employee.create();

    public LoginController(Login login) {
        this.view = login;
    }

    @Override
    public void registerEvents() {
        view.getMasuk().addActionListener(evt -> {
            setUserInput();
            System.out.println(account.getUsername());
            System.out.println(account.getPassword());
            var loginStatus = accountDao.login(account);
            if (loginStatus) {
                view.dispose();
                var destination = new EmployeeForm();
                destination.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(view, "Username/Password Salah!");
            }
        });

        view.getBatal().addActionListener(evt -> {
            clearUserInput();
        });
        
        view.getLupaPass().addActionListener(evt -> {
           view.dispose() ;
           var destination = new LupaPassword();
           destination.setVisible(true);
        });

        view.getKeluar().addActionListener(evt -> {
            var status = JOptionPane.showConfirmDialog(view, "Yakin ingin keluar?");
            if (status == 0) view.dispose();
        });
    }

    private void setUserInput() {
        account.setUsername(view.getUsername().getText());
        account.setPassword(String.valueOf(view.getPassword().getPassword()));
    }

    private void clearUserInput() {
        view.getUsername().setText("");
        view.getPassword().setText("");
        setUserInput();
    }
}
