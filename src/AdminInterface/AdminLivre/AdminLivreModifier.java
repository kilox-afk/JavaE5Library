package AdminInterface.AdminLivre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLivreModifier {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void initialize() {
        // Création de la fenêtre Modifier
        JFrame frame = new JFrame("Page Modifier (Livre)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Ajout d'un texte au-dessus du menu déroulant
        JLabel label = new JLabel("Sélectionnez le livre à modifier :", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16)); // Agrandir le texte
        panel.add(label, BorderLayout.NORTH);

        // Ajout d'un JPanel pour contenir le JComboBox et réduire l'espace occupé
        JPanel comboBoxPanel = new JPanel(new GridBagLayout()); // Utilisation d'un GridBagLayout pour le centrage
        comboBoxPanel.setBackground(Color.WHITE); // Pour que le fond soit visible

        // Ajout d'un JComboBox pour permettre à l'utilisateur de sélectionner l'élément à modifier
        JComboBox<String> comboBox = new JComboBox<>();
        // Remplir le JComboBox avec les livres, par exemple en récupérant depuis la base de données
        remplirComboBox(comboBox);

        // Ajout du JComboBox au panneau
        comboBoxPanel.add(comboBox);

        // Ajout du JPanel au centre du panneau principal
        panel.add(comboBoxPanel, BorderLayout.CENTER);

        // Ajout d'un bouton pour ouvrir l'interface de modification
        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer l'élément sélectionné dans le JComboBox
                String selectedLivre = (String) comboBox.getSelectedItem();
                if (!selectedLivre.equals("--- Sélectionnez un livre à modifier ---")) {
                    // Extraire l'ISBN du livre sélectionné
                    String[] parts = selectedLivre.split(":");
                    String ISBN = parts[0].trim();
                    // Ouvrir l'interface de modification avec l'ISBN sélectionné
                    ouvrirInterfaceModification(ISBN);
                    // Fermer la fenêtre après l'ouverture de l'interface de modification
                    frame.dispose();
                }
            }
        });

        // Ajout du bouton de modification au panneau principal
        panel.add(modifierButton, BorderLayout.SOUTH);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre
        frame.setSize(500, 250); // Taille initiale de la fenêtre

        // Centrer la fenêtre
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    private void remplirComboBox(JComboBox<String> comboBox) {
        // Ajouter la valeur par défaut au début de la liste
        comboBox.addItem("--- Sélectionnez un livre à modifier --- ");

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT ISBN, titre FROM livre";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String ISBN = rs.getString("ISBN");
                    String titre = rs.getString("titre");
                    // Ajouter l'ISBN et le titre à l'élément de la liste dans le JComboBox
                    comboBox.addItem(ISBN + ": " + titre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ouvrirInterfaceModification(String ISBN) {
        // Créer une nouvelle instance de votre interface de modification
        AdminLivreModificationInterface modificationInterface = new AdminLivreModificationInterface(ISBN);
        // Appeler la méthode initialize() pour afficher l'interface de modification
        modificationInterface.initialize();
    }

    public static void main(String[] args) {
        AdminLivreModifier livreModifier = new AdminLivreModifier();
        livreModifier.initialize();
    }
}
