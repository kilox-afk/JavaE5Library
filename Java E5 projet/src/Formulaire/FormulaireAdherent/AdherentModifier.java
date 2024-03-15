package Formulaire.FormulaireAdherent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdherentModifier {
    static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    /**
     * 
     */
    public void initialize() {
        // Création de la fenêtre Modifier
        JFrame frame = new JFrame("Page Modifier (Adhérent)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Ajout d'une JComboBox pour permettre à l'utilisateur de sélectionner l'adhérent à modifier
        JComboBox<String> comboBox = new JComboBox<>();
        // Remplissez le JComboBox avec les adhérents à modifier
        remplirComboBox(comboBox);
        // Ajout du JComboBox au panneau principal
        panel.add(comboBox, BorderLayout.CENTER);

        // Ajout d'un bouton de modification
        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer l'adhérent sélectionné dans le JComboBox
                String adhSelectionne = (String) comboBox.getSelectedItem();
                // Appel de la méthode pour afficher le formulaire de modification
                afficherFormulaireModificationAdherent(adhSelectionne);
            }
        });
        // Ajout du bouton de modification au panneau principal
        panel.add(modifierButton, BorderLayout.SOUTH);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre
        frame.setSize(400, 200); // Taille initiale de la fenêtre

        // Centrer la fenêtre
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    private void remplirComboBox(JComboBox<String> comboBox) {
        // Ajouter la valeur par défaut au début de la liste
        comboBox.addItem("--- Choisissez un adhérent à modifier ---");
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT id_adherent, nom, prenom FROM adherent";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String idAdherent = rs.getString("id_adherent");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    // Ajouter l'ID et le nom/prénom à l'élément de la liste dans le JComboBox
                    comboBox.addItem(idAdherent + ": " + nom + " " + prenom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherFormulaireModificationAdherent(String adhSelectionne) {
        // Extraire l'ID de l'adhérent sélectionné
        String[] parts = adhSelectionne.split(":");
        String idAdherent = parts[0];

        // Création d'une instance de la classe FormulaireModificationAdherent pour afficher le formulaire de modification
        FormulaireModificationAdherent FormulaireModificationAdherent = new FormulaireModificationAdherent(idAdherent);
        FormulaireModificationAdherent.initialize();
    }
}

