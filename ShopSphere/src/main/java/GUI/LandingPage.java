package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LandingPage extends JFrame {
    private JFrame mainFrame;

    public LandingPage() {
        showMainScreen();
    }

    private void showMainScreen() {
        mainFrame = new JFrame("ShopSphere E-commerce Application");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);

        // Custom JPanel for background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("ninyy.jpg");

                Image img = background.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);


        JLabel label = new JLabel("Welcome to ShopSphere");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(199, 227, 225));
        label.setBounds(0, 50, 800, 50);
        backgroundPanel.add(label);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(250, 200, 300, 200);
        buttonPanel.setBackground(new Color(80, 114, 123, 200));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createLineBorder(new Color(120, 160, 131), 2)
        ));
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton customerButton = new JButton("Customer Login");
        customerButton.setFont(new Font("Arial", Font.BOLD, 16));
        customerButton.setBackground(new Color(0, 120, 215));
        customerButton.setForeground(Color.WHITE);
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new CustomerGUI();
            }
        });

        JButton adminButton = new JButton("Admin Login");
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.setBackground(new Color(0, 120, 215));
        adminButton.setForeground(Color.WHITE);
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new AdminGUI();
            }
        });

        buttonPanel.add(customerButton);
        buttonPanel.add(adminButton);

        backgroundPanel.add(buttonPanel);
        mainFrame.setContentPane(backgroundPanel);
        mainFrame.setVisible(true);
    }

}