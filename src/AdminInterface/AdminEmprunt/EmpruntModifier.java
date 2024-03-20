package AdminInterface.AdminEmprunt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpruntModifier {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void initialize() {
        // Création de la fenêtre Modifier
        JFrame frame = new JFrame("Page Modifier (Emprunt)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Ajout d'un texte au-dessus du menu déroulant
        JLabel label = new JLabel("Sélectionnez l'emprunt à modifier :", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16)); // Agrandir le texte
        panel.add(label, BorderLayout.NORTH);

        // Ajout d'un JPanel pour contenir le JComboBox et réduire l'espace occupé
        JPanel comboBoxPanel = new JPanel(new GridBagLayout()); // Utilisation d'un GridBagLayout pour le centrage
        comboBoxPanel.setBackground(Color.WHITE); // Pour que le fond soit visible

        // Ajout d'un JComboBox pour permettre à l'utilisateur de sélectionner l'élément à modifier
        JComboBox<String> comboBox = new JComboBox<>();
        // Remplir le JComboBox avec les emprunts, par exemple en récupérant depuis la base de données
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
                String selectedEmprunt = (String) comboBox.getSelectedItem();
                if (!selectedEmprunt.equals("--- Sélectionnez un emprunt à modifier ---")) {
                    // Extraire l'ID de l'emprunt sélectionné
                    String[] parts = selectedEmprunt.split(":");
                    int empruntId = Integer.parseInt(parts[0].trim());
                    // Ouvrir l'interface de modification avec l'emprunt sélectionné
                    ouvrirInterfaceModification(empruntId);
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
        frame.setSize(400, 200); // Taille initiale de la fenêtre

        // Centrer la fenêtre
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    private void remplirComboBox(JComboBox<String> comboBox) {
        // Ajouter la valeur par défaut au début de la liste
        comboBox.addItem("--- Sélectionnez un emprunt à modifier --- ");
    
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT id_emprunt, Adh_num, ISBN FROM emprunt";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idEmprunt = rs.getInt("id_emprunt");
                    String Adh_num = rs.getString("Adh_num");
                    String ISBN = rs.getString("ISBN");
                    // Ajouter l'ID, le numéro d'adhérent et l'ISBN du livre à l'élément de la liste dans le JComboBox
                    comboBox.addItem(idEmprunt + ": " + Adh_num + " " + ISBN);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ouvrirInterfaceModification(int empruntId) {
        // Ouvrir l'interface de modification avec l'ID d'emprunt correct
        EmpruntModificationInterface modificationInterface = new EmpruntModificationInterface(empruntId);
        modificationInterface.initialize();
    }
    

    public static void main(String[] args) {
        EmpruntModifier empruntModifier = new EmpruntModifier();
        empruntModifier.initialize();
    }
}
