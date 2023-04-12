package models.presenter;

import models.entity.Employee;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EmployeePresenter extends AbstractTableModel {
    private final List<Employee> employees;

    public EmployeePresenter(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> employees.get(rowIndex).getIdCard();
            case 1 -> employees.get(rowIndex).getName();
            case 2 -> employees.get(rowIndex).getAddress();
            case 3 -> employees.get(rowIndex).getPosition();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "NIK";
            case 1 -> "Nama";
            case 2 -> "Alamat";
            case 3 -> "Jabatan";
            default -> null;
        };
    }

    public Employee getEmployees(int rowIndex) {
        return employees.get(rowIndex);
    }
}
