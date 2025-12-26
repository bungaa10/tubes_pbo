package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistem Klinik Hewan");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color soft = new Color(245, 243, 255);
        Color textDark = new Color(49, 46, 129);

        getContentPane().setBackground(soft);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBackground(soft);
        tabs.setForeground(textDark);

        // ⚠️ Penting: kita pakai contentPane aja biar logic class frame tetap
        HewanFrame hf = new HewanFrame();

        tabs.addTab("🐾 Data Hewan", hf.getContentPane());

        add(tabs, BorderLayout.CENTER);
    }
}
