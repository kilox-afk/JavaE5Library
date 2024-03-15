package Formulaire.FormulaireAdherent;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormulaireModificationAdherent {
    private String idAdherent;
    static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public FormulaireModificationAdherent(String idAdherent) {
        this.idAdherent = idAdherent;
    }

    public void initialize() {
        // Création de la fenêtre de modification avec les champs à modifier préremplis
        JFrame frame = new JFrame("Modifier Adhérent");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec un GridLayout
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        // Ajout de composants pour les champs à modifier, par exemple :
        panel.add(new JLabel("Nom:"));
        JTextField nomField = new JTextField();
        panel.add(nomField);

        panel.add(new JLabel("Prénom:"));
        JTextField prenomField = new JTextField();
        panel.add(prenomField);

        // Ajout d'un bouton de validation
        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> {
            validerModification(nomField.getText(), prenomField.getText());
            frame.dispose(); // Fermer la fenêtre après validation
        });
        panel.add(validerButton);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre
        frame.setSize(400, 200);

        // Centrer la fenêtre
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    private void validerModification(String nouveauNom, String nouveauPrenom) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Création de la requête SQL pour mettre à jour les informations de l'adhérent
            String sql = "UPDATE adherent SET nom = ?, prenom = ? WHERE id_adherent = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nouveauNom);
                stmt.setString(2, nouveauPrenom);
                stmt.setString(3, idAdherent);

                // Exécution de la requête de mise à jour
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Adhérent modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la modification de l'adhérent.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
