package worker.hewan;

import api.HewanApiClient;
import model.Hewan;
import view.HewanFrame;
import view.tablemodel.HewanTableModel;

import javax.swing.*;
import java.util.List;

public class LoadHewanWorker extends SwingWorker<List<Hewan>, Void> {

    private final HewanFrame frame;

    public LoadHewanWorker(HewanFrame frame) {
        this.frame = frame;
    }
    @Override
    protected List<Hewan> doInBackground() throws Exception {
        return HewanApiClient.getAll();
    }

    @Override
    protected void done() {
        try {
            List<Hewan> list = get();

            // isi tabel
            HewanTableModel model = frame.getHewanTableModel();
            model.setData(list); // <- pastikan di HewanTableModel ada method setData(List<Hewan>)

            frame.getTotalRecordsLabel().setText(list.size() + " Data Hewan");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
