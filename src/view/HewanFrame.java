package view;

import view.tablemodel.HewanTableModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import controller.HewanController;

import java.awt.*;

public class HewanFrame extends JFrame {
    private HewanController controller;

    // ===== COMPONENTS YANG DIPAKAI CONTROLLER =====
    private JButton btnAdd = new JButton("Tambah");
    private JButton btnRefresh = new JButton("Refresh");
    private JButton btnDelete = new JButton("Hapus");

    private JTextField txtSearch = new JTextField(20);

    private HewanTableModel tableModel = new HewanTableModel();
    private JTable table = new JTable(tableModel);

    private JProgressBar progressBar = new JProgressBar();
    private JLabel lblTotal = new JLabel("0 Records");

    public HewanFrame() {
        controller = new HewanController(this);
        loadData();

        setTitle("Sistem Klinik Hewan");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // ===== UI THEME COLORS =====
        Color purple = new Color(124, 58, 237);
        Color purpleDark = new Color(109, 40, 217);
        Color purpleSoft = new Color(245, 243, 255);
        Color textDark = new Color(49, 46, 129);
        Color borderSoft = new Color(223, 213, 255);

        getContentPane().setBackground(purpleSoft);

        // ===== TOP PANEL =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(purpleSoft);
        top.setBorder(BorderFactory.createEmptyBorder(12, 14, 6, 14));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setBackground(purpleSoft);

        JLabel lblCari = new JLabel("Cari:");
        lblCari.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCari.setForeground(textDark);

        txtSearch.setPreferredSize(new Dimension(260, 34));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setBackground(Color.WHITE);
        txtSearch.setForeground(textDark);
        txtSearch.setCaretColor(purple);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderSoft),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        left.add(lblCari);
        left.add(txtSearch);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setBackground(purpleSoft);

        // Style buttons (UI only)
        stylePurpleButton(btnAdd, purple, purpleDark);
        styleOutlineButton(btnRefresh, borderSoft, textDark);
        stylePurpleButton(btnDelete, new Color(239, 68, 68), new Color(220, 38, 38));

        right.add(btnAdd);
        right.add(btnRefresh);
        right.add(btnDelete);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        // ===== CENTER =====
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
        scroll.getViewport().setBackground(Color.WHITE);

        // Table Styling
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(purple);
        table.setSelectionForeground(Color.WHITE);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(purple);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 36));

        // ===== BOTTOM =====
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(purpleSoft);
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 14, 12, 14));

        progressBar.setBorderPainted(false);
        progressBar.setBackground(purpleSoft);
        progressBar.setForeground(purple);
        progressBar.setPreferredSize(new Dimension(200, 10));

        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotal.setForeground(textDark);

        bottom.add(progressBar, BorderLayout.CENTER);
        bottom.add(lblTotal, BorderLayout.EAST);

        // ===== ADD TO FRAME =====
        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadData() {
        controller.loadAllHewans();
    }

    // ===== UI HELPERS ONLY (TIDAK MENGUBAH LOGIKA) =====
    private void stylePurpleButton(JButton btn, Color bg, Color hover) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.getModel().addChangeListener(e -> {
            ButtonModel m = btn.getModel();
            if (m.isRollover() || m.isPressed()) {
                btn.setBackground(hover);
            } else {
                btn.setBackground(bg);
            }
        });
    }

    private void styleOutlineButton(JButton btn, Color border, Color fg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(Color.WHITE);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }

    // ===== GETTERS (WAJIB UNTUK CONTROLLER) =====
    public JButton getAddButton() {
        return btnAdd;
    }

    public JButton getRefreshButton() {
        return btnRefresh;
    }

    public JButton getDeleteButton() {
        return btnDelete;
    }

    public JTable getHewanTable() {
        return table;
    }

    public JTextField getSearchField() {
        return txtSearch;
    }

    public HewanTableModel getHewanTableModel() {
        return tableModel;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getTotalRecordsLabel() {
        return lblTotal;
    }
}
