package controller;

import contract.Controller;
import models.dao.EmployeeDao;
import models.entity.Employee;
import models.presenter.EmployeePresenter;
import util.Database;
import view.EmployeeForm;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class EmployeeController implements Controller {
    private final EmployeeForm view;
    private final EmployeeDao employeeDao = new EmployeeDao(Database.getConnection());
    private final Employee employee = Employee.create();
    private String gender;

    public EmployeeController(EmployeeForm view) {
        this.view = view;
    }

    public void load() {
        var model = new EmployeePresenter(employeeDao.getEmployees());
        view.getTable().setModel(model);
    }

    public void registerEvents() {
        view.getAdding().addActionListener(e -> {
            this.setUserInput();
            var result = employeeDao.createEmployee(employee);
            this.reloadAfterAction("menambahkan data!", result);
        });

        view.getDeleting().addActionListener(e -> {
            if (!view.getNik().getText().equals("")) {
                var result = employeeDao.deleteEmployee(employee);
                this.reloadAfterAction("menghapus data!", result);
            } else {
                JOptionPane.showMessageDialog(view, "Harus memilih data yang akan dihapus!");
            }
        });

        view.getChangging().addActionListener(e -> {
            if (!view.getNik().getText().equals("")) {
                this.setUserInput();
                var result = employeeDao.updateEmployee(employee);
                this.reloadAfterAction("merubah data!", result);
            } else {
                JOptionPane.showMessageDialog(view, "Harus memilih data yang akan dirubah!");
            }
        });

        view.getMale().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gender = view.getMale().getText();
            }
        });

        view.getFemale().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gender = view.getFemale().getText();
            }
        });

        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var model = (EmployeePresenter) view.getTable().getModel();
                var selectedEmployee = model.getEmployees(view.getTable().getSelectedRow());
                prepareDataUpdate(selectedEmployee);
            }
        });

        view.getClean().addActionListener(e -> {
            this.resetUserInput();
        });

        view.getClose().addActionListener(e -> {
            var status = JOptionPane.showConfirmDialog(view, "Yakin ingin keluar?");
            if (status == 0) view.dispose();
        });
    }

    private void prepareDataUpdate(Employee employee) {
        view.getFieldName().setText(employee.getName());
        view.getNik().setText(employee.getIdCard());
        view.getUsername().setText(employee.getUsername());
        view.getPassword().setText(employee.getPassword());
        view.getPosition().setText(employee.getPosition());
        view.getAddress().setText(employee.getAddress());
        gender = employee.getGender();

        if (employee.getGender().equals("LAKI-LAKI")) {
            view.getGender().setSelected(view.getMale().getModel(), true);
        } else {
            view.getGender().setSelected(view.getFemale().getModel(), true);
        }
    }

    private void setUserInput() {
        employee.setName(view.getFieldName().getText());
        employee.setIdCard(view.getNik().getText());
        employee.setUsername(view.getUsername().getText());
        employee.setPassword(Arrays.toString(view.getPassword().getPassword()));
        employee.setPosition(view.getPosition().getText());
        employee.setAddress(view.getAddress().getText());
        employee.setGender(gender);
    }

    private void resetUserInput() {
        view.getFieldName().setText("");
        view.getNik().setText("");
        view.getUsername().setText("");
        view.getPassword().setText("");
        view.getPosition().setText("");
        view.getAddress().setText("");
        view.getGender().clearSelection();
    }

    private void reloadAfterAction(String message, boolean isSuccess) {
        String catchMessage = (isSuccess ? "Berhasil " : "Gagal ") + message;
        JOptionPane.showMessageDialog(view, catchMessage);

        if (isSuccess) {
            this.resetUserInput();
            this.load();
        }
    }
}
