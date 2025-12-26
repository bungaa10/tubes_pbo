package worker.hewan;

import api.HewanApiClient;
import model.Hewan;
import view.HewanFrame;

import javax.swing.*;

public class DeleteHewanWorker extends SwingWorker<Void, Void> {

    private final Hewan hewan;
    private final HewanFrame frame;

    public DeleteHewanWorker(Hewan hewan, HewanFrame frame) {
        this.hewan = hewan;
        this.frame = frame;
    }

    @Override
    protected Void doInBackground() throws Exception {
        HewanApiClient.delete(hewan.getIdHewan());
        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            new LoadHewanWorker(frame).execute(); // ✅ realtime reload
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
