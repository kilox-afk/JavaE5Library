package UserInterface.UserEmprunt;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntModificationInterface {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JFrame frame;

    private JTextField dateEmpruntTextField;
    private JTextField dateRetourTextField;
    private JComboBox<String> statutEmpruntComboBox;
    private JComboBox<String> adhNumComboBox;
    private JComboBox<String> ISBNComboBox;

    private int empruntId;

    public EmpruntModificationInterface(int empruntId) {
        this.empruntId = empruntId;
    }

    public void initialize() {
        // Création de la fenêtre de modification
        frame = new JFrame("Modifier Emprunt");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Modifier Emprunt");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addComponent(panel, titleLabel, constraints, 0, 0, 2, 1);

        // Création du champ de texte de la date d'emprunt
        JLabel dateEmpruntLabel = new JLabel("Date de l'emprunt (yyyy-mm-dd):");
        dateEmpruntTextField = new JTextField(20);
        dateEmpruntTextField.setEditable(false); // Rendre le champ non modifiable
        addComponent(panel, dateEmpruntLabel, constraints, 0, 1, 1, 1);
        addComponent(panel, dateEmpruntTextField, constraints, 1, 1, 1, 1);

        JLabel dateRetourLabel = new JLabel("Date de retour (yyyy-mm-dd):");
        dateRetourTextField = new JTextField(20);
        addComponent(panel, dateRetourLabel, constraints, 0, 2, 1, 1);
        addComponent(panel, dateRetourTextField, constraints, 1, 2, 1, 1);

        JLabel statutEmpruntLabel = new JLabel("Statut de l'emprunt:");
        addComponent(panel, statutEmpruntLabel, constraints, 0, 3, 1, 1);

        // Ajouter les choix de statut emprunt
        statutEmpruntComboBox = new JComboBox<>(new String[] { "rendu", "emprunter" });
        statutEmpruntComboBox.setEnabled(false); // Désactiver le JComboBox
        addComponent(panel, statutEmpruntComboBox, constraints, 1, 3, 1, 1);

        JLabel adhNumLabel = new JLabel("Numéro de l'adhérent:");
        addComponent(panel, adhNumLabel, constraints, 0, 4, 1, 1);

        // Charger les Adh_num depuis la table "adherent"
        chargerAdhNum(empruntId);
        addComponent(panel, adhNumComboBox, constraints, 1, 4, 1, 1);

        JLabel ISBNLabel = new JLabel("ISBN du livre:");
        addComponent(panel, ISBNLabel, constraints, 0, 5, 1, 1);

        // Charger les ISBN depuis la table "livre"
        chargerISBN(empruntId);
        addComponent(panel, ISBNComboBox, constraints, 1, 5, 1, 1);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> modifierEmprunt());
        addComponent(panel, validerButton, constraints, 0, 6, 2, 1);

        // Remplir les champs avec les données de l'emprunt correspondant à l'ID
        // spécifié
        remplirChamps(empruntId);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre
        frame.setSize(450, 300);

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

    private void chargerISBN(int empruntId) {
        List<String> ISBNList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT ISBN FROM emprunt WHERE id_emprunt = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, empruntId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        ISBNList.add(rs.getString("ISBN"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ISBNComboBox = new JComboBox<>(ISBNList.toArray(new String[0]));
    }

    private void chargerAdhNum(int empruntId) {
        List<String> adhNumList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT Adh_num FROM emprunt WHERE id_emprunt = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, empruntId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        adhNumList.add(rs.getString("Adh_num"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adhNumComboBox = new JComboBox<>(adhNumList.toArray(new String[0]));
    }

    private void remplirChamps(int empruntId) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT date_emprunt, date_retour, statut_emprunt, Adh_num, ISBN FROM emprunt WHERE id_emprunt = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, empruntId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) { // Ajout de cette condition pour s'assurer qu'il y a des résultats à récupérer
                        dateEmpruntTextField.setText(rs.getString("date_emprunt"));
                        dateRetourTextField.setText(rs.getString("date_retour"));
                        // Mettre à jour le statut de l'emprunt en fonction de la date de retour
                        if (rs.getString("date_retour") != null) {
                            statutEmpruntComboBox.setSelectedItem("rendu");
                        } else {
                            statutEmpruntComboBox.setSelectedItem(rs.getBoolean("statut_emprunt") ? "rendu" : "emprunter");
                        }
                        adhNumComboBox.setSelectedItem(rs.getString("Adh_num"));
                        ISBNComboBox.setSelectedItem(rs.getString("ISBN"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void modifierEmprunt() {
        // Récupérer les valeurs des champs
        String nouveauDateEmprunt = dateEmpruntTextField.getText();
        String nouveauDateRetour = dateRetourTextField.getText().isEmpty() ? null : dateRetourTextField.getText();
        String nouvelStatutEmprunt = (String) statutEmpruntComboBox.getSelectedItem();
        String nouvelleAdhNum = (String) adhNumComboBox.getSelectedItem();
        String nouveauISBN = (String) ISBNComboBox.getSelectedItem();

        // Vérifier si les champs sont vides
        if (nouveauDateEmprunt.isEmpty() || nouvelStatutEmprunt.isEmpty()
                || nouvelleAdhNum == null || nouveauISBN == null) {
            // Afficher un message d'erreur si un champ est vide
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car au moins un champ est vide
        }

        // Vérifier si les champs de date sont au format valide (yyyy-mm-dd)
        if (!isValidDateFormat(nouveauDateEmprunt)
                || (nouveauDateRetour != null && !isValidDateFormat(nouveauDateRetour))) {
            JOptionPane.showMessageDialog(null, "Les dates doivent être au format yyyy-mm-dd.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car le format de date est invalide
        }

        // Vérifier si statut_emprunt est un booléen valide
        if (!nouvelStatutEmprunt.equalsIgnoreCase("rendu")
                && !nouvelStatutEmprunt.equalsIgnoreCase("emprunter")) {
            JOptionPane.showMessageDialog(null, "Le statut de l'emprunt doit être 'rendu' ou 'emprunter'.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter le processus car le statut de l'emprunt n'est pas valide
        }

        // Convertir la valeur de statut_emprunt en un booléen
        boolean statutEmpruntBool = nouvelStatutEmprunt.equalsIgnoreCase("rendu");

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE emprunt SET date_emprunt = ?, date_retour = ?, statut_emprunt = ?, Adh_num = ?, ISBN = ? WHERE id_emprunt = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nouveauDateEmprunt);
                if (nouveauDateRetour != null) {
                    stmt.setString(2, nouveauDateRetour);
                } else {
                    stmt.setNull(2, Types.DATE);
                }
                stmt.setBoolean(3, statutEmpruntBool);
                stmt.setString(4, nouvelleAdhNum);
                stmt.setString(5, nouveauISBN);
                stmt.setInt(6, empruntId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Emprunt modifié avec succès.", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Aucun emprunt trouvé avec l'identifiant spécifié.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour valider le format de date (yyyy-mm-dd)
    private boolean isValidDateFormat(String date) {
        // Utilisation d'une expression régulière pour vérifier le format de date
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public static void main(String[] args) {
        // Vous devez obtenir l'empruntId de la manière appropriée pour votre
        // application
        int empruntId = 1; // Par exemple
        EmpruntModificationInterface empruntModificationInterface = new EmpruntModificationInterface(empruntId);
        empruntModificationInterface.initialize();
    }
}