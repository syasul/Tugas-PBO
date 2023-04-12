package controller;

import contract.Controller;
import models.dao.StuffDao;
import models.entity.Stuff;
import models.presenter.StuffPresenter;
import util.Database;
import view.StuffForm;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StuffController implements Controller {

    private final StuffForm view;
    private final StuffDao stuffDao = new StuffDao(Database.getConnection());
    private final Stuff stuff = Stuff.create();

    public StuffController(StuffForm view) {
        this.view = view;
    }

    public void load() {
        var model = new StuffPresenter(stuffDao.getStuff());
        view.getTable().setModel(model);
    }

    private void prepareDataUpdate(Stuff stuff) {
        view.getCode().setText(stuff.getCode());
        view.getFieldName().setText(stuff.getName());
        view.getPrice().setText(stuff.getPrice().toString());
        view.getCount().setText(stuff.getCount().toString());
        view.getBrand().setText(stuff.getBrand());
    }

    private void setUserInput() {
        stuff.setCode(view.getCode().getText());
        stuff.setName(view.getFieldName().getText());
        stuff.setPrice(Double.valueOf(view.getPrice().getText()));
        stuff.setCount(Integer.valueOf(view.getCount().getText()));
        stuff.setBrand(view.getBrand().getText());
    }

    private void resetUserInput() {
        view.getFieldName().setText("");
        view.getCount().setText("");
        view.getCode().setText("");
        view.getPrice().setText("");
        view.getBrand().setText("");
    }

    private void reloadAfterAction(String message, boolean isSuccess) {
        String catchMessage = (isSuccess ? "Berhasil " : "Gagal ") + message;
        JOptionPane.showMessageDialog(view, catchMessage);

        if (isSuccess) {
            this.resetUserInput();
            this.load();
        }
    }

    @Override
    public void registerEvents() {
        view.getAdding().addActionListener(e -> {
            this.setUserInput();
            var result = stuffDao.createStuff(stuff);
            this.reloadAfterAction("menambah data!", result);
        });

        view.getChangging().addActionListener(e -> {
            if (!view.getCode().getText().equals("")) {
                this.setUserInput();
                var result = stuffDao.updateStuff(stuff);
                this.reloadAfterAction("merubah data!", result);
            } else {
                JOptionPane.showMessageDialog(view, "Harus memilih data yang akan dirubah!");
            }
        });

        view.getDeleting().addActionListener(e -> {
            if (!view.getCode().getText().equals("")) {
                var result = stuffDao.deleteStuff(stuff);
                this.reloadAfterAction("menghapus data!", result);
            } else {
                JOptionPane.showMessageDialog(view, "Harus memilih data yang akan dihapus!");
            }
        });

        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var model = (StuffPresenter) view.getTable().getModel();
                var selectedStuff = model.getStuff(view.getTable().getSelectedRow());
                prepareDataUpdate(selectedStuff);
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
}
