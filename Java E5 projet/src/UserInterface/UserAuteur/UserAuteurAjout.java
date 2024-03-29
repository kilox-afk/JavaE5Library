package UserInterface.UserAuteur;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAuteurAjout {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JFrame frame;

    private JTextField champ1Field;
    private JTextField champ2Field;
    private JTextField champ3Field;
    private JTextField champ4Field;

    public void initialize() {
        // Création de la fenêtre Ajouter
        frame = new JFrame("Page Ajouter (Auteur)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Ajouter un Auteur");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addComponent(panel, titleLabel, constraints, 0, 0, 2, 1);

        JLabel champ1Label = new JLabel("Nom:");
        champ1Field = new JTextField(20);
        addComponent(panel, champ1Label, constraints, 0, 1, 1, 1);
        addComponent(panel, champ1Field, constraints, 1, 1, 1, 1);

        JLabel champ2Label = new JLabel("Prenom:");
        champ2Field = new JTextField(20);
        addComponent(panel, champ2Label, constraints, 0, 2, 1, 1);
        addComponent(panel, champ2Field, constraints, 1, 2, 1, 1);

        JLabel champ3Label = new JLabel("Date de naissance (yyyy-mm-dd):");
        champ3Field = new JTextField(20);
        addComponent(panel, champ3Label, constraints, 0, 3, 1, 1);
        addComponent(panel, champ3Field, constraints, 1, 3, 1, 1);

        JLabel champ4Label = new JLabel("Description (42 caractères):");
        champ4Field = new JTextField(20);
        addComponent(panel, champ4Label, constraints, 0, 4, 1, 1);
        addComponent(panel, champ4Field, constraints, 1, 4, 1, 1);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> validerAjoutAuteur());
        addComponent(panel, validerButton, constraints, 0, 6, 1, 1);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre du formulaire
        frame.setSize(450, 300); // Taille ajustée

        // Centrer la fenêtre
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    private void addComponent(JPanel panel, Component component, GridBagConstraints constraints, int gridx, int gridy,
            int gridwidth, int gridheight) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        panel.add(component, constraints);
    }

    private void validerAjoutAuteur() {
        // Récupérer les valeurs des champs
        String nom = champ1Field.getText();
        String prenom = champ2Field.getText();
        String date = champ3Field.getText();
        String description = champ4Field.getText();

        // Vérifier si les champs sont vides
        if (nom.isEmpty() || prenom.isEmpty() || date.isEmpty() || description.isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car au moins un champ est vide
        }

        // Vérifier le format de la date
        if (!verifierFormatDate(date)) {
            JOptionPane.showMessageDialog(null, "Le format de la date doit être 'yyyy-mm-dd'.",
                    "Format de date incorrect",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car le format de la date est incorrect
        }
        
        // Vérifier la longueur de la description
        if (description.length() > 42) {
            JOptionPane.showMessageDialog(null, "La description ne doit pas dépasser 42 caractères.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car la description dépasse la limite
        }

        // Tous les champs sont remplis et la description est valide, procéder avec l'insertion des données dans la base de données
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Création de la requête SQL d'insertion
            String sql = "INSERT INTO auteur (nom, prenom, date_naissance, description) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Remplacer les paramètres ? par les valeurs des champs
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, date);
                stmt.setString(4, description);

                // Exécuter la requête d'insertion
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Auteur ajouté avec succès.", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Fermer la fenêtre après l'ajout réussi
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'auteur.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean verifierFormatDate(String date) {
        // Vérifier si la date correspond au format 'yyyy-MM-dd'
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public static void main(String[] args) {
        UserAuteurAjout auteurAjout = new UserAuteurAjout();
        auteurAjout.initialize();
    }
}
