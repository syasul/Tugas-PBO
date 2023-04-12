package models.presenter;

import models.entity.Stuff;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StuffPresenter extends AbstractTableModel {

    private List<Stuff> stuffList;

    public StuffPresenter(List<Stuff> stuffList) {
        this.stuffList = stuffList;
    }

    @Override
    public int getRowCount() {
        return stuffList.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> stuffList.get(rowIndex).getCode();
            case 1 -> stuffList.get(rowIndex).getName();
            case 2 -> stuffList.get(rowIndex).getCount();
            case 3 -> stuffList.get(rowIndex).getPrice();
            case 4 -> stuffList.get(rowIndex).getBrand();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Kode Barang";
            case 1 -> "Nama Barang";
            case 2 -> "Jumlah";
            case 3 -> "Harga";
            case 4 -> "Merek";
            default -> null;
        };
    }

    public Stuff getStuff(int rowIndex) {
        return stuffList.get(rowIndex);
    }
}
