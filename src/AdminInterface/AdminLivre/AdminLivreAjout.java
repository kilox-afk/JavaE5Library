package AdminInterface.AdminLivre;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminLivreAjout {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JFrame frame;

    private JTextField champ1Field;
    private JTextField champ2Field;
    private JTextField champ3Field;
    private JTextField champ4Field;
    private JTextField champ5Field;
    private JTextField champ6Field;
    private JTextField champ7Field;

    public void initialize() {
        // Création de la fenêtre Ajouter
        frame = new JFrame("Page Ajouter (Livre)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Ajouter un Livre");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addComponent(panel, titleLabel, constraints, 0, 0, 2, 1);

        JLabel champ1Label = new JLabel("ISBN:");
        champ1Field = new JTextField(20);
        addComponent(panel, champ1Label, constraints, 0, 1, 1, 1);
        addComponent(panel, champ1Field, constraints, 1, 1, 1, 1);

        JLabel champ2Label = new JLabel("Titre:");
        champ2Field = new JTextField(20);
        addComponent(panel, champ2Label, constraints, 0, 2, 1, 1);
        addComponent(panel, champ2Field, constraints, 1, 2, 1, 1);

        JLabel champ3Label = new JLabel("Prix:");
        champ3Field = new JTextField(20);
        addComponent(panel, champ3Label, constraints, 0, 3, 1, 1);
        addComponent(panel, champ3Field, constraints, 1, 3, 1, 1);

        JLabel champ4Label = new JLabel("Genre:");
        champ4Field = new JTextField(20);
        addComponent(panel, champ4Label, constraints, 0, 4, 1, 1);
        addComponent(panel, champ4Field, constraints, 1, 4, 1, 1);

        JLabel champ5Label = new JLabel("Disponibilité:");
        champ5Field = new JTextField(20);
        addComponent(panel, champ5Label, constraints, 0, 5, 1, 1);
        addComponent(panel, champ5Field, constraints, 1, 5, 1, 1);

        JLabel champ6Label = new JLabel("nb Livre:");
        champ6Field = new JTextField(20);
        addComponent(panel, champ6Label, constraints, 0, 6, 1, 1);
        addComponent(panel, champ6Field, constraints, 1, 6, 1, 1);

        JLabel champ7Label = new JLabel("ID Auteur:");
        champ7Field = new JTextField(20);
        addComponent(panel, champ7Label, constraints, 0, 7, 1, 1);
        addComponent(panel, champ7Field, constraints, 1, 7, 1, 1);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> validerFormulaire());
        addComponent(panel, validerButton, constraints, 0, 8, 1, 1);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre du formulaire
        frame.setSize(500, 350); // Vous pouvez ajuster la taille selon vos besoins

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
        String ISBN = champ1Field.getText();
        String titre = champ2Field.getText();
        String prix = champ3Field.getText();
        String genre = champ4Field.getText();
        String disponible = champ5Field.getText();
        String nbLivre = champ6Field.getText();
        String IdAuteur = champ7Field.getText();

        // Vérifier si les champs sont vides
        if (ISBN.isEmpty() || titre.isEmpty() || prix.isEmpty() || genre.isEmpty() || disponible.isEmpty() || nbLivre.isEmpty() || IdAuteur.isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car au moins un champ est vide
        }

        // Tous les champs sont remplis et nb_emprunt est un entier valide, procéder
        // avec l'insertion des données dans la base de données
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Création de la requête SQL d'insertion
            String sql = "INSERT INTO adherent (ISBN, titre, prix, genre, disponible, nb_livre, Aut_num) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Remplacer les paramètres ? par les valeurs des champs
                stmt.setString(1, ISBN);
                stmt.setString(2, titre);
                stmt.setString(3, prix);
                stmt.setString(4, genre);
                stmt.setString(5, disponible);
                stmt.setString(6, nbLivre);
                stmt.setString(7, IdAuteur);

                // Exécuter la requête d'insertion
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Livre ajouté avec succès.", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Fermer la fenêtre après l'ajout réussi
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de le livre.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AdminLivreAjout adherentAjout = new AdminLivreAjout();
        adherentAjout.initialize();
    }
}
