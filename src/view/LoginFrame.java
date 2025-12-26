package view;

import api.AdminApiClient;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnLogin = new JButton("Login");
    private JLabel lblStatus = new JLabel("");

    public LoginFrame() {
        setTitle("Login Admin - Klinik Hewan");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color purple = new Color(124, 58, 237);
        Color soft = new Color(245, 243, 255);
        Color textDark = new Color(49, 46, 129);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(purple);
        header.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel title = new JLabel("Login Admin");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(soft);
        form.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(textDark);

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPass.setForeground(textDark);

        styleField(txtUser);
        styleField(txtPass);

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(lblUser, gbc);
        gbc.gridx = 1;
        form.add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(lblPass, gbc);
        gbc.gridx = 1;
        form.add(txtPass, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        btnLogin.setBackground(purple);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        form.add(btnLogin, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        lblStatus.setForeground(Color.RED);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        form.add(lblStatus, gbc);

        add(form, BorderLayout.CENTER);

        // ===== LOGIKA LOGIN =====
        btnLogin.addActionListener(e -> doLogin());
        txtPass.addActionListener(e -> doLogin()); // enter juga login
    }

    private void styleField(JTextField f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(223, 213, 255)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    private void doLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            lblStatus.setText("Username dan Password wajib diisi!");
            return;
        }

        try {
            boolean ok = AdminApiClient.login(user, pass);
            if (ok) {
                lblStatus.setText("");
                JOptionPane.showMessageDialog(this, "Login berhasil!");

                // buka main app
                MainFrame main = new MainFrame();
                main.setVisible(true);

                // tutup login frame
                dispose();
            } else {
                lblStatus.setText("Username / Password salah!");
            }
        } catch (Exception ex) {
            lblStatus.setText("Gagal koneksi ke server login!");
            ex.printStackTrace();
        }
    }
}
