package worker.hewan;

import api.HewanApiClient;
import model.Hewan;
import view.HewanFrame;

import javax.swing.*;

public class UpdateHewanWorker extends SwingWorker<Void, Void> {

    private final Hewan hewan;
    private final HewanFrame frame;

    public UpdateHewanWorker(Hewan hewan, HewanFrame frame) {
        this.hewan = hewan;
        this.frame = frame;
    }

    @Override
    protected Void doInBackground() throws Exception {
        HewanApiClient.update(hewan);
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
