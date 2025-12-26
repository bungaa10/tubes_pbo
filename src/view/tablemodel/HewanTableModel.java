package view.tablemodel;

import model.Hewan;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class HewanTableModel extends AbstractTableModel {

    private List<Hewan> data = new ArrayList<>();
    private final String[] columnNames = {
    "ID", "Nama", "Jenis", "Umur", "Pemilik"
};

@Override
public String getColumnName(int column) {
    return columnNames[column];
}

@Override
public int getColumnCount() {
    return columnNames.length;
}

    public void setHewanList(List<Hewan> data) {
        this.data = data;
        fireTableDataChanged(); // ⬅ PENTING
    }

    @Override
    public int getRowCount() {
        return data.size(); // ⬅ BUKAN 0
    }

    @Override
    public Object getValueAt(int row, int col) {
        Hewan h = data.get(row);
        switch (col) {
            case 0: return h.getIdHewan();
            case 1: return h.getNama();
            case 2: return h.getJenis();
            case 3: return h.getUmur();
            case 4: return h.getPemilik();
            default: return null;
        }
    }

    public void setData(List<Hewan> data) {
    this.data = data;
    fireTableDataChanged();
}


}

