package UserInterface.UserAuteur;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuteurModificationInterface {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JFrame frame;

    private JTextField nomTextField;
    private JTextField prenomTextField;
    private JTextField dateNaissanceTextField;
    private JTextField descriptionTextField;

    private int auteurId;

    public AuteurModificationInterface(int auteurId) {
        this.auteurId = auteurId;
    }

    public void initialize() {
        // Création de la fenêtre de modification
        frame = new JFrame("Modifier Auteur");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Modifier Auteur");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addComponent(panel, titleLabel, constraints, 0, 0, 2, 1);

        JLabel nomLabel = new JLabel("Nom:");
        nomTextField = new JTextField(20);
        addComponent(panel, nomLabel, constraints, 0, 1, 1, 1);
        addComponent(panel, nomTextField, constraints, 1, 1, 1, 1);

        JLabel prenomLabel = new JLabel("Prénom:");
        prenomTextField = new JTextField(20);
        addComponent(panel, prenomLabel, constraints, 0, 2, 1, 1);
        addComponent(panel, prenomTextField, constraints, 1, 2, 1, 1);

        JLabel dateNaissanceLabel = new JLabel("Date de naissance:");
        dateNaissanceTextField = new JTextField(20);
        addComponent(panel, dateNaissanceLabel, constraints, 0, 3, 1, 1);
        addComponent(panel, dateNaissanceTextField, constraints, 1, 3, 1, 1);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionTextField = new JTextField(20);
        addComponent(panel, descriptionLabel, constraints, 0, 4, 1, 1);
        addComponent(panel, descriptionTextField, constraints, 1, 4, 1, 1);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> modifierAuteur());
        addComponent(panel, validerButton, constraints, 0, 6, 2, 1);

        // Charger les détails de l'Auteur depuis la base de données
        chargerDetailsAuteur();

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre
        frame.setSize(400, 300);

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

    private void chargerDetailsAuteur() {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT nom, prenom, date_naissance, description FROM auteur WHERE Adh_num = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, auteurId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        nomTextField.setText(rs.getString("nom"));
                        prenomTextField.setText(rs.getString("prenom"));
                        dateNaissanceTextField.setText(rs.getString("date_naissance"));
                        descriptionTextField.setText(rs.getString("description"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modifierAuteur() {
        // Récupérer les valeurs des champs
        String nouveauNom = nomTextField.getText();
        String nouveauPrenom = prenomTextField.getText();
        String nouvelDateNaissance = dateNaissanceTextField.getText();
        String nouvelleDescription = descriptionTextField.getText();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE auteur SET nom = ?, prenom = ?, date_naissance = ?, description = ? WHERE Aut_num = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nouveauNom);
                stmt.setString(2, nouveauPrenom);
                stmt.setString(3, nouvelDateNaissance);
                stmt.setString(4, nouvelleDescription);
                stmt.setInt(5, auteurId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Auteur modifié avec succès.", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Aucun auteur trouvé avec l'identifiant spécifié.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
