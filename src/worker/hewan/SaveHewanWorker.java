package worker.hewan;

import api.HewanApiClient;
import model.Hewan;
import view.HewanFrame;

import javax.swing.*;

public class SaveHewanWorker extends SwingWorker<Void, Void> {

    private final Hewan hewan;
    private final HewanFrame frame;

    public SaveHewanWorker(Hewan hewan, HewanFrame frame) {
        this.hewan = hewan;
        this.frame = frame;
    }

    @Override
    protected Void doInBackground() throws Exception {
        HewanApiClient.save(hewan);
        return null;
    }

    @Override
    protected void done() {
        try {
            get(); // biar error ketangkep kalau gagal
            new LoadHewanWorker(frame).execute();  // ✅ realtime reload
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
