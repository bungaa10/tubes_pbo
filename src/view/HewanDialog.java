package view;

import model.Hewan;

import javax.swing.*;
import java.awt.*;

public class HewanDialog extends JDialog {

    private Hewan hewan;
    private boolean editMode;
    public boolean ok = false;

    private JTextField txtNama = new JTextField();
    private JTextField txtJenis = new JTextField();
    private JTextField txtUmur = new JTextField();
    private JTextField txtPemilik = new JTextField();

    private JButton btnSave = new JButton("Simpan");

    public HewanDialog(HewanFrame parent) {
        this(parent, null);
    }

    // ================= EDIT HEWAN ==================
    public HewanDialog(HewanFrame parent, Hewan hewan) {
        super(parent, true);
        this.hewan = hewan;
        this.editMode = hewan != null;

        // ✅ LOGIKA TETAP (JANGAN DIUBAH)
        btnSave.addActionListener(e -> {
            ok = true;
            dispose();
        });

        // =================== UI ONLY ===================
        setTitle(editMode ? "Edit Hewan" : "Tambah Hewan");

        // ======= UI THEME (UNGU) =======
        Color purple = new Color(124, 58, 237);
        Color purpleDark = new Color(109, 40, 217);
        Color bg = new Color(250, 247, 255);
        Color fieldBg = Color.WHITE;

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBackground(bg);
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setContentPane(root);

        // Header ungu
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(purple);
        header.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel lblTitle = new JLabel(editMode ? "Edit Hewan" : "Tambah Hewan");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(lblTitle, BorderLayout.WEST);

        root.add(header, BorderLayout.NORTH);

        // Form input
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        java.util.function.BiConsumer<String, JTextField> addRow = (label, field) -> {
            JLabel l = new JLabel(label);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            l.setForeground(new Color(49, 46, 129));

            field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            field.setBackground(fieldBg);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(223, 213, 255)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

            gbc.gridx = 0;
            gbc.weightx = 0;
            form.add(l, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            form.add(field, gbc);

            gbc.gridy++;
        };

        addRow.accept("Nama", txtNama);
        addRow.accept("Jenis", txtJenis);
        addRow.accept("Umur", txtUmur);
        addRow.accept("Pemilik", txtPemilik);

        root.add(form, BorderLayout.CENTER);

        // Footer tombol
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footer.setOpaque(false);

        btnSave.setText("Simpan");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(purple);
        btnSave.setFocusPainted(false);
        btnSave.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSave.getModel().addChangeListener(e -> {
            ButtonModel m = btnSave.getModel();
            if (m.isPressed()) btnSave.setBackground(purpleDark);
            else if (m.isRollover()) btnSave.setBackground(purpleDark);
            else btnSave.setBackground(purple);
        });

        footer.add(btnSave);
        root.add(footer, BorderLayout.SOUTH);

        if (editMode) {
            loadData();
        }

        pack();
        setLocationRelativeTo(parent);
    }

    private void loadData() {
        txtNama.setText(hewan.getNama());
        txtJenis.setText(hewan.getJenis());
        txtUmur.setText(hewan.getUmur());
        txtPemilik.setText(hewan.getPemilik());
    }

    public boolean isOk() {
        return ok;
    }

    public Hewan getData() {
        Hewan h = new Hewan();

        if (editMode) {
            h.setIdHewan(hewan.getIdHewan());
        }

        h.setNama(txtNama.getText().trim());
        h.setJenis(txtJenis.getText().trim());
        h.setUmur(txtUmur.getText().trim());
        h.setPemilik(txtPemilik.getText().trim());

        return h;
    }

    public Hewan getHewan() {
        return getData();
    }
}
