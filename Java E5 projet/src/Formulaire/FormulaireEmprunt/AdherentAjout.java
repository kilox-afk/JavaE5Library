package Formulaire.FormulaireAdherent;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdherentAjout {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JFrame frame;

    private JTextField champ1Field;
    private JTextField champ2Field;
    private JTextField champ3Field;
    private JTextField champ4Field;
    private JTextField champ5Field;

    public void initialize() {
        // Création de la fenêtre Ajouter
        frame = new JFrame("Page Ajouter (Adhérent)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Ajouter un Adhérent");
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

        JLabel champ3Label = new JLabel("Email:");
        champ3Field = new JTextField(20);
        addComponent(panel, champ3Label, constraints, 0, 3, 1, 1);
        addComponent(panel, champ3Field, constraints, 1, 3, 1, 1);

        JLabel champ4Label = new JLabel("Adresse:");
        champ4Field = new JTextField(20);
        addComponent(panel, champ4Label, constraints, 0, 4, 1, 1);
        addComponent(panel, champ4Field, constraints, 1, 4, 1, 1);

        JLabel champ5Label = new JLabel("Nombre d'emprunt:");
        champ5Field = new JTextField(20);
        addComponent(panel, champ5Label, constraints, 0, 5, 1, 1);
        addComponent(panel, champ5Field, constraints, 1, 5, 1, 1);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> validerFormulaire());
        addComponent(panel, validerButton, constraints, 0, 6, 1, 1);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre du formulaire
        frame.setSize(400, 300); // Vous pouvez ajuster la taille selon vos besoins

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

    private void validerFormulaire() {
        // Récupérer les valeurs des champs
        String nom = champ1Field.getText();
        String prenom = champ2Field.getText();
        String email = champ3Field.getText();
        String adresse = champ4Field.getText();
        String nb_empruntText = champ5Field.getText();

        // Vérifier si les champs sont vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || adresse.isEmpty() || nb_empruntText.isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car au moins un champ est vide
        }

        // Vérifier si nb_emprunt est un entier valide
        int nb_emprunt;
        try {
            nb_emprunt = Integer.parseInt(nb_empruntText);
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si nb_emprunt n'est pas un entier valide
            JOptionPane.showMessageDialog(null, "Le nombre d'emprunts doit être un entier.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car nb_emprunt n'est pas un entier valide
        }

        // Tous les champs sont remplis et nb_emprunt est un entier valide, procéder
        // avec l'insertion des données dans la base de données
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Création de la requête SQL d'insertion
            String sql = "INSERT INTO adherent (nom, prenom, email, adresse, nb_emprunt) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Remplacer les paramètres ? par les valeurs des champs
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, email);
                stmt.setString(4, adresse);
                stmt.setInt(5, nb_emprunt);

                // Exécuter la requête d'insertion
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Adhérent ajouté avec succès.", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Fermer la fenêtre après l'ajout réussi
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'adhérent.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AdherentAjout adherentAjout = new AdherentAjout();
        adherentAjout.initialize();
    }
}
