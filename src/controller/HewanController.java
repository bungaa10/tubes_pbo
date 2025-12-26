package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import api.AntrianWebSocketClient;
import model.Hewan;
import view.HewanDialog;
import view.HewanFrame;
import worker.hewan.DeleteHewanWorker;
import worker.hewan.LoadHewanWorker;
import worker.hewan.SaveHewanWorker;
import worker.hewan.UpdateHewanWorker;

public class HewanController {

    public final HewanFrame frame;

    public List<Hewan> allHewans = new ArrayList<>();
    public List<Hewan> displayedHewans = new ArrayList<>();

    public AntrianWebSocketClient wsClient;

    public HewanController(HewanFrame frame) {
        this.frame = frame;
        setupEventListeners();
        setupWebSocket();
        loadAllHewans();
    }

    // ===================== WEBSOCKET =====================
    public void setupWebSocket() {
        try {
            URI uri = new URI("ws://localhost:3000/api/hewan");
            wsClient = new AntrianWebSocketClient(uri, (Consumer<String>) message -> {
                System.out.println("📩 Realtime update: " + message);
                handleWebSocketMessage(message);
            });
            wsClient.connect();
        } catch (URISyntaxException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Gagal koneksi realtime server: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void handleWebSocketMessage(String message) {
    try {
        // Contoh message: {"event":"create","data":{...}}
        if (message.contains("\"event\"")) {
            if (
                message.contains("\"create\"") ||
                message.contains("\"update\"") ||
                message.contains("\"delete\"")
            ) {
                SwingUtilities.invokeLater(this::loadAllHewans);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    // ===================== EVENT =====================
    public void setupEventListeners() {

        frame.getAddButton().addActionListener(e -> openHewanDialog(null));
        frame.getRefreshButton().addActionListener(e -> loadAllHewans());
        frame.getDeleteButton().addActionListener(e -> deleteSelectedHewan());

        frame.getHewanTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = frame.getHewanTable().getSelectedRow();
                    if (row >= 0) {
                        openHewanDialog(displayedHewans.get(row));
                    }
                }
            }
        });

        frame.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { applyFilter(); }
            @Override public void removeUpdate(DocumentEvent e) { applyFilter(); }
            @Override public void changedUpdate(DocumentEvent e) { applyFilter(); }

            public void applyFilter() {
                String keyword = frame.getSearchField().getText().toLowerCase().trim();
                displayedHewans = new ArrayList<>();

                for (Hewan h : allHewans) {
                    if (h.getNama().toLowerCase().contains(keyword)
                            || h.getJenis().toLowerCase().contains(keyword)
                            || h.getPemilik().toLowerCase().contains(keyword)) {
                        displayedHewans.add(h);
                    }
                }

                frame.getHewanTableModel().setHewanList(displayedHewans);
                updateTotalLabel();
            }
        });
    }

    // ===================== DIALOG =====================
    public void openHewanDialog(Hewan hewanToEdit) {

    HewanDialog dialog = (hewanToEdit == null)
            ? new HewanDialog(frame)
            : new HewanDialog(frame, hewanToEdit);

    dialog.setVisible(true);

    // ===== CEK DIALOG =====
    if (!dialog.ok) {
        System.out.println("Dialog dibatalkan / belum klik Simpan");
        return;
    }

    // ===== AMBIL DATA DARI DIALOG =====
Hewan hewan = dialog.getData();

SwingWorker<Void, Void> worker =
        (hewanToEdit == null)
                ? new SaveHewanWorker(hewan, frame)
                : new UpdateHewanWorker(hewan, frame);

    // ===== LOADING ON =====
    setLoading(true);

    // ===== LISTENER SELESAI =====
    worker.addPropertyChangeListener(evt -> {
        if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
            setLoading(false);
            loadAllHewans();
        }
    });

    // ===== JALANKAN WORKER =====
    worker.execute();
}


    // ===================== DELETE =====================
    public void deleteSelectedHewan() {
        int row = frame.getHewanTable().getSelectedRow();
        if (row == -1) {
    JOptionPane.showMessageDialog(frame, "Pilih data dulu!");
    return;
}


        Hewan hewan = displayedHewans.get(row);

        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Hapus data hewan: " + hewan.getNama() + "?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            SwingWorker<Void, Void> worker = new DeleteHewanWorker(hewan, frame);
            worker.addPropertyChangeListener(evt -> {
                if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
                    loadAllHewans();
                }
            });
            worker.execute();
        }
    }

    // ===================== LOAD =====================
    public void loadAllHewans() {
        frame.getProgressBar().setIndeterminate(true);
        frame.getProgressBar().setString("Loading data...");

        LoadHewanWorker worker = new LoadHewanWorker(frame);
        worker.addPropertyChangeListener(evt -> {
            if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
                try {
                    allHewans = worker.get();
                    displayedHewans = new ArrayList<>(allHewans);
                    frame.getHewanTableModel().setHewanList(displayedHewans);
                    updateTotalLabel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Gagal load data!");
                } finally {
                    frame.getProgressBar().setIndeterminate(false);
                    frame.getProgressBar().setString("Ready");
                }
            }
        });

        worker.execute();
    }

    public void updateTotalLabel() {
        frame.getTotalRecordsLabel().setText(displayedHewans.size() + " Data Hewan");
    }
    private void setLoading(boolean loading) {
    frame.getAddButton().setEnabled(!loading);
    frame.getRefreshButton().setEnabled(!loading);
    frame.getDeleteButton().setEnabled(!loading);
    frame.getProgressBar().setIndeterminate(loading);
}

}
