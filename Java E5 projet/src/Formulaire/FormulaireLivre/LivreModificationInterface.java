package Formulaire.FormulaireLivre;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivreModificationInterface {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JFrame frame;

    private JTextField nomTextField;
    private JTextField prenomTextField;
    private JTextField emailTextField;
    private JTextField adresseTextField;
    private JTextField nbEmpruntTextField;

    private int adherentId;

    public LivreModificationInterface(int adherentId) {
        this.adherentId = adherentId;
    }

    public void initialize() {
        // Création de la fenêtre de modification
        frame = new JFrame("Modifier Adhérent");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Modifier Adhérent");
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

        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(20);
        addComponent(panel, emailLabel, constraints, 0, 3, 1, 1);
        addComponent(panel, emailTextField, constraints, 1, 3, 1, 1);

        JLabel adresseLabel = new JLabel("Adresse:");
        adresseTextField = new JTextField(20);
        addComponent(panel, adresseLabel, constraints, 0, 4, 1, 1);
        addComponent(panel, adresseTextField, constraints, 1, 4, 1, 1);

        JLabel nbEmpruntLabel = new JLabel("Nombre d'emprunts:");
        nbEmpruntTextField = new JTextField(20);
        addComponent(panel, nbEmpruntLabel, constraints, 0, 5, 1, 1);
        addComponent(panel, nbEmpruntTextField, constraints, 1, 5, 1, 1);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> modifierAdherent());
        addComponent(panel, validerButton, constraints, 0, 6, 2, 1);

        // Charger les détails de l'adhérent depuis la base de données
        chargerDetailsAdherent();

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

    private void chargerDetailsAdherent() {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT nom, prenom, email, adresse, nb_emprunt FROM adherent WHERE Adh_num = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, adherentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        nomTextField.setText(rs.getString("nom"));
                        prenomTextField.setText(rs.getString("prenom"));
                        emailTextField.setText(rs.getString("email"));
                        adresseTextField.setText(rs.getString("adresse"));
                        nbEmpruntTextField.setText(String.valueOf(rs.getInt("nb_emprunt")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modifierAdherent() {
        // Récupérer les valeurs des champs
        String nouveauNom = nomTextField.getText();
        String nouveauPrenom = prenomTextField.getText();
        String nouvelEmail = emailTextField.getText();
        String nouvelleAdresse = adresseTextField.getText();
        int nouveauNbEmprunt = Integer.parseInt(nbEmpruntTextField.getText());

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE adherent SET nom = ?, prenom = ?, email = ?, adresse = ?, nb_emprunt = ? WHERE Adh_num = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nouveauNom);
                stmt.setString(2, nouveauPrenom);
                stmt.setString(3, nouvelEmail);
                stmt.setString(4, nouvelleAdresse);
                stmt.setInt(5, nouveauNbEmprunt);
                stmt.setInt(6, adherentId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Adhérent modifié avec succès.", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Aucun adhérent trouvé avec l'identifiant spécifié.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
