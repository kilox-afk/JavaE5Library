package UserInterface;

import javax.swing.*;

import AdminInterface.AdminAdherent.AdherentAjout;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class userLogin {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public void initialize() {
        // Création de la fenêtre de connexion pour l'utilisateur
        frame = new JFrame("Connexion Utilisateur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panneau principal avec un gestionnaire de disposition
        // BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Création du panneau pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        // Ajout des champs de saisie et des étiquettes au panneau
        JLabel usernameLabel = new JLabel("Numéro utilisateur:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordField = new JPasswordField(20);

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Création du bouton de connexion
        JButton loginButton = new JButton("Se connecter");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seConnecter();
            }
        });

        // Ajout du panneau de saisie et du bouton de connexion au panneau principal
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(loginButton, BorderLayout.SOUTH);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre
        frame.setSize(400, 150);

        // Centrer la fenêtre sur l'écran
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    private void seConnecter() {
        String username = usernameField.getText();
        String adherentId = username;
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM adherent WHERE Adh_num = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String storedPasswordHash = rs.getString("mot_de_passe"); // Récupérer le mot de passe haché
                                                                                  // depuis la base de données
                        String hashedPassword = AdherentAjout.hashPassword(password); // Appeler la méthode hashPassword
                                                                                      // statique
                        if (storedPasswordHash.equals(hashedPassword)) {
                            // Afficher un message de connexion réussie
                            JOptionPane.showMessageDialog(frame, "Connexion réussie !", "Succès",
                                    JOptionPane.INFORMATION_MESSAGE);

                            // Masquer la fenêtre de connexion
                            frame.setVisible(false);

                            // Créer une instance de la page d'accueil et l'initialiser en passant
                            // l'identifiant de l'adhérent
                            AccueilUser accueil = new AccueilUser();
                            accueil.initialize(adherentId); // username est l'identifiant de l'adhérent
                        } else {
                            // Afficher un message d'erreur si le mot de passe est incorrect
                            JOptionPane.showMessageDialog(frame, "Numéro utilisateur ou mot de passe incorrect.",
                                    "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Afficher un message d'erreur si l'utilisateur n'existe pas
                        JOptionPane.showMessageDialog(frame, "Numéro utilisateur ou mot de passe incorrect.",
                                "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            // Afficher un message d'erreur si une erreur SQL se produit
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur de connexion à la base de données.",
                    "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Création d'une instance de la classe UserLogin et appel de la méthode
        // initialize()
        userLogin userLogin = new userLogin();
        userLogin.initialize();
    }
}
