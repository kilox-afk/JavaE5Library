package Formulaire.FormulaireEmprunt;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EmpruntAjout {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JFrame frame;

    private JTextField champ1Field;
    private JTextField champ2Field;
    private JTextField champ3Field;
    private JComboBox<String> adhNumComboBox;
    private JComboBox<String> ISBNComboBox;

    public void initialize() {
        // Création de la fenêtre Ajouter
        frame = new JFrame("Page Ajouter (Emprunt)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Ajouter un Emprunt");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addComponent(panel, titleLabel, constraints, 0, 0, 2, 1);

        JLabel champ1Label = new JLabel("Date d'emprunt (yyyy-mm-dd):");
        champ1Field = new JTextField(20);
        addComponent(panel, champ1Label, constraints, 0, 1, 1, 1);
        addComponent(panel, champ1Field, constraints, 1, 1, 1, 1);

        JLabel champ2Label = new JLabel("Date de retour (yyyy-mm-dd):");
        champ2Field = new JTextField(20);
        addComponent(panel, champ2Label, constraints, 0, 2, 1, 1);
        addComponent(panel, champ2Field, constraints, 1, 2, 1, 1);

        JLabel champ3Label = new JLabel("Statut de l'emprunt (1 pour disponible sinon 0):");
        champ3Field = new JTextField(20);
        addComponent(panel, champ3Label, constraints, 0, 3, 1, 1);
        addComponent(panel, champ3Field, constraints, 1, 3, 1, 1);

        JLabel champ4Label = new JLabel("Numéro de l'adhérent:");
        adhNumComboBox = new JComboBox<>();
        remplirComboBox(adhNumComboBox, "Adh_num", "adherent");
        addComponent(panel, champ4Label, constraints, 0, 4, 1, 1);
        addComponent(panel, adhNumComboBox, constraints, 1, 4, 1, 1);

        JLabel champ5Label = new JLabel("ISBN:");
        ISBNComboBox = new JComboBox<>();
        remplirComboBox(ISBNComboBox, "ISBN", "livre");
        addComponent(panel, champ5Label, constraints, 0, 5, 1, 1);
        addComponent(panel, ISBNComboBox, constraints, 1, 5, 1, 1);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> validerFormulaire());
        addComponent(panel, validerButton, constraints, 0, 6, 1, 1);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre du formulaire
        frame.setSize(550, 300); // Vous pouvez ajuster la taille selon vos besoins

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
        String dateEmprunt = champ1Field.getText();
        String dateRetour = champ2Field.getText();
        String statutEmprunt = champ3Field.getText(); // Récupérer le statut comme une chaîne de caractères
        String adhNum = (String) adhNumComboBox.getSelectedItem();
        String ISBN = (String) ISBNComboBox.getSelectedItem();

        // Vérifier si les champs sont vides
        if (dateEmprunt.isEmpty() || dateRetour.isEmpty() || statutEmprunt.isEmpty() || adhNum == null
                || ISBN == null) {
            // Afficher un message d'erreur si un champ est vide
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car au moins un champ est vide
        }

        // Vérifier si les champs de date sont au format valide (yyyy-mm-dd)
        if (!isValidDateFormat(dateEmprunt) || !isValidDateFormat(dateRetour)) {
            JOptionPane.showMessageDialog(null, "Les dates doivent être au format yyyy-mm-dd.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car le format de date est invalide
        }

        // Vérifier si statut_emprunt est un booléen valide
        if (!statutEmprunt.equalsIgnoreCase("1") && !statutEmprunt.equalsIgnoreCase("0")) {
            JOptionPane.showMessageDialog(null, "Le statut de l'emprunt doit être 'disponible ou 'indisponible'.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car le statut de l'emprunt n'est pas valide
        }

        // Convertir la valeur de statut_emprunt en un booléen
        boolean statutEmpruntBool = statutEmprunt.equalsIgnoreCase("disponible");

        // La logique de validation des autres champs continue ici...

        // Tous les champs sont remplis et les dates sont au format valide, procéder
        // avec l'insertion des données dans la base de données
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Création de la requête SQL d'insertion
            String sql = "INSERT INTO emprunt (date_emprunt, date_retour, statut_emprunt, Adh_num, ISBN) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Remplacer les paramètres ? par les valeurs des champs
                stmt.setString(1, dateEmprunt);
                stmt.setString(2, dateRetour);
                stmt.setBoolean(3, statutEmpruntBool);
                stmt.setString(4, adhNum);
                stmt.setString(5, ISBN);

                // Exécuter la requête d'insertion
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Emprunt ajouté avec succès.", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Fermer la fenêtre après l'ajout réussi
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'emprunt.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void remplirComboBox(JComboBox<String> comboBox, String columnName, String tableName) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT DISTINCT " + columnName + " FROM " + tableName;
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    comboBox.addItem(rs.getString(columnName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidDateFormat(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public static void main(String[] args) {
        EmpruntAjout empruntAjout = new EmpruntAjout();
        empruntAjout.initialize();
    }
}
