package UserInterface.UserAuteur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AuteurSupprimer {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void initialize() {
        // Création de la fenêtre Supprimer
        JFrame frame = new JFrame("Page Supprimer (Auteur)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Ajout d'un texte au-dessus du menu déroulant
        JLabel label = new JLabel("Sélectionnez l'auteur à supprimer :", SwingConstants.CENTER); // Centrage horizontal
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16)); // Agrandir le texte
        panel.add(label, BorderLayout.NORTH);

        // Ajout d'un JPanel pour contenir le JComboBox et réduire l'espace occupé
        JPanel comboBoxPanel = new JPanel(new GridBagLayout()); // Utilisation d'un GridBagLayout pour le centrage
        comboBoxPanel.setBackground(Color.WHITE); // Pour que le fond soit visible

        // Ajout d'un JComboBox pour permettre à l'utilisateur de sélectionner l'élément à supprimer
        JComboBox<String> comboBox = new JComboBox<>();
        // Remplissez le JComboBox avec les éléments à supprimer, par exemple en les récupérant depuis la base de données
        remplirComboBox(comboBox);

        // Ajout du JComboBox au panneau
        comboBoxPanel.add(comboBox);

        // Ajout du JPanel au centre du panneau principal
        panel.add(comboBoxPanel, BorderLayout.CENTER);

        // Ajout d'un bouton de suppression
        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer l'élément sélectionné dans le JComboBox
                String elementASupprimer = (String) comboBox.getSelectedItem();

                // Appel de la méthode pour supprimer l'élément de la base de données
                try {
                    supprimerAuteur(elementASupprimer);
                    // Fermer la fenêtre après la suppression réussie
                    frame.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'auteur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ajout du bouton de suppression au panneau principal
        panel.add(supprimerButton, BorderLayout.SOUTH);

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
        comboBox.addItem("--- Choisissez un auteur à supprimer ICI --- ");
    
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM auteur";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String aut_num = rs.getString("Aut_num");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    // Ajoutez le nom et le prénom à l'élément de la liste dans le JComboBox
                    comboBox.addItem(aut_num + ": " + nom + " " + prenom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void supprimerAuteur(String selectedItem) throws SQLException {
        // Extraire l'ID de l'élément sélectionné dans le JComboBox
        String autNum = selectedItem.split(":")[0].trim(); // Récupérer l'ID en tant que première partie de l'élément sélectionné
        
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM auteur WHERE Aut_num = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, autNum);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Auteur supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun auteur avec l'ID spécifié trouvé.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
