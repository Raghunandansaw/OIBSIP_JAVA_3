import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ATMInterface extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String userId = "user";
    private String pin = "1234";
    private double balance = 1000.0;
    private ArrayList<String> transactions = new ArrayList<>();

    public ATMInterface() {
        setTitle("ATM Machine By Raghu");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panels
        JPanel loginPanel = createLoginPanel();
        JPanel menuPanel = createMenuPanel();
        JPanel withdrawPanel = createWithdrawPanel();
        JPanel depositPanel = createDepositPanel();
        JPanel transferPanel = createTransferPanel();
        JPanel historyPanel = createHistoryPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(withdrawPanel, "Withdraw");
        mainPanel.add(depositPanel, "Deposit");
        mainPanel.add(transferPanel, "Transfer");
        mainPanel.add(historyPanel, "History");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Welcome to ATM");
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel userLabel = new JLabel("User ID:");
        userLabel.setForeground(Color.WHITE);
        JTextField userField = new JTextField(10);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setForeground(Color.WHITE);
        JPasswordField pinField = new JPasswordField(10);

        JButton loginButton = new JButton("Enter");
        loginButton.setBackground(Color.GREEN);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));

        loginButton.addActionListener(e -> {
            String enteredUser = userField.getText();
            String enteredPin = new String(pinField.getPassword());

            if (enteredUser.equals(userId) && enteredPin.equals(pin)) {
                cardLayout.show(mainPanel, "Menu");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID or PIN", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(pinLabel, gbc);
        gbc.gridx = 1;
        panel.add(pinField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(Color.BLACK);

        JButton historyButton = createATMButton("Transaction History", e -> cardLayout.show(mainPanel, "History"));
        JButton withdrawButton = createATMButton("Withdraw", e -> cardLayout.show(mainPanel, "Withdraw"));
        JButton depositButton = createATMButton("Deposit", e -> cardLayout.show(mainPanel, "Deposit"));
        JButton transferButton = createATMButton("Transfer", e -> cardLayout.show(mainPanel, "Transfer"));
        JButton quitButton = createATMButton("Quit", e -> System.exit(0));

        panel.add(historyButton);
        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(transferButton);
        panel.add(quitButton);

        return panel;
    }

    private JPanel createWithdrawPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label = new JLabel("Enter amount to withdraw:");
        label.setForeground(Color.WHITE);
        JTextField amountField = new JTextField(10);
        JButton confirmButton = createATMButton("Confirm", e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > balance) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    balance -= amount;
                    transactions.add("Withdraw: $" + amount);
                    JOptionPane.showMessageDialog(this, "Withdrawn $" + amount);
                    cardLayout.show(mainPanel, "Menu");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(label, gbc);
        gbc.gridy++;
        panel.add(amountField, gbc);
        gbc.gridy++;
        panel.add(confirmButton, gbc);

        return panel;
    }

    private JPanel createDepositPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label = new JLabel("Enter amount to deposit:");
        label.setForeground(Color.WHITE);
        JTextField amountField = new JTextField(10);
        JButton confirmButton = createATMButton("Confirm", e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                balance += amount;
                transactions.add("Deposit: $" + amount);
                JOptionPane.showMessageDialog(this, "Deposited $" + amount);
                cardLayout.show(mainPanel, "Menu");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(label, gbc);
        gbc.gridy++;
        panel.add(amountField, gbc);
        gbc.gridy++;
        panel.add(confirmButton, gbc);

        return panel;
    }

    private JPanel createTransferPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label1 = new JLabel("Enter recipient ID:");
        label1.setForeground(Color.WHITE);
        JTextField idField = new JTextField(10);

        JLabel label2 = new JLabel("Enter amount to transfer:");
        label2.setForeground(Color.WHITE);
        JTextField amountField = new JTextField(10);

        JButton confirmButton = createATMButton("Confirm", e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > balance) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    balance -= amount;
                    transactions.add("Transfer: $" + amount + " to " + idField.getText());
                    JOptionPane.showMessageDialog(this, "Transferred $" + amount + " to " + idField.getText());
                    cardLayout.show(mainPanel, "Menu");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(label1, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(label2, gbc);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        panel.add(confirmButton, gbc);

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JButton backButton = createATMButton("Back", e -> cardLayout.show(mainPanel, "Menu"));

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        // Update transactions list when opening
        panel.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                textArea.setText("");
                for (String t : transactions) {
                    textArea.append(t + "\n");
                }
                textArea.append("Balance: $" + balance);
            }
        });

        return panel;
    }

    private JButton createATMButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBackground(Color.GREEN);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.addActionListener(listener);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMInterface().setVisible(true));
    }
}
