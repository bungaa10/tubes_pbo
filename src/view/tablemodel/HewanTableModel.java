package view.tablemodel;

import model.Hewan;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class HewanTableModel extends AbstractTableModel {

    private List<Hewan> data = new ArrayList<>();

    private final String[] columnNames = {
        "ID", "Nama", "Jenis", "Umur", "Pemilik"
    };

    private List<Hewan> listHewan;

    public void setData(List<Hewan> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public List<Hewan> getHewanList() {
        return data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Hewan h = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return h.getIdHewan();
            case 1: return h.getNama();
            case 2: return h.getJenis();
            case 3: return h.getUmur();
            case 4: return h.getPemilik();
            default: return null;
        }
    }
    public void setHewanList(List<Hewan> listHewan) {
    this.listHewan = listHewan;
    fireTableDataChanged();
}

    public HewanTableModel getModel() {
    return this;
    }

}
