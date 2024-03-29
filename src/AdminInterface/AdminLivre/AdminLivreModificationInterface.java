package AdminInterface.AdminLivre;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminLivreModificationInterface {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private JFrame frame;

    private JTextField ISBNTextField;
    private JTextField titreTextField;
    private JTextField prixTextField;
    private JTextField genreTextField;
    private JTextField disponibleTextField;
    private JTextField nbLivreTextField;
    private JTextField autNumTextField;

    private String stringLivreId;

    public AdminLivreModificationInterface(String stringLivreId) {
        this.stringLivreId = stringLivreId;
    }

    public void initialize() {
        // Création de la fenêtre de modification
        frame = new JFrame("Modifier Livre");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Modifier Livre");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addComponent(panel, titleLabel, constraints, 0, 0, 2, 1);

        JLabel ISBNLabel = new JLabel("ISBN:");
        ISBNTextField = new JTextField(20);
        ISBNTextField.setEditable(false); // Rendre le champ non modifiable
        addComponent(panel, ISBNLabel, constraints, 0, 1, 1, 1);
        addComponent(panel, ISBNTextField, constraints, 1, 1, 1, 1);

        JLabel titreLabel = new JLabel("Titre:");
        titreTextField = new JTextField(20);
        addComponent(panel, titreLabel, constraints, 0, 2, 1, 1);
        addComponent(panel, titreTextField, constraints, 1, 2, 1, 1);

        JLabel prixLabel = new JLabel("Prix:");
        prixTextField = new JTextField(20);
        addComponent(panel, prixLabel, constraints, 0, 3, 1, 1);
        addComponent(panel, prixTextField, constraints, 1, 3, 1, 1);

        JLabel genreLabel = new JLabel("Genre:");
        genreTextField = new JTextField(20);
        addComponent(panel, genreLabel, constraints, 0, 4, 1, 1);
        addComponent(panel, genreTextField, constraints, 1, 4, 1, 1);

        JLabel disponibleLabel = new JLabel("Disponible:");
        disponibleTextField = new JTextField(20);
        addComponent(panel, disponibleLabel, constraints, 0, 5, 1, 1);
        addComponent(panel, disponibleTextField, constraints, 1, 5, 1, 1);

        JLabel nbLivreLabel = new JLabel("nombre livre:");
        nbLivreTextField = new JTextField(20);
        addComponent(panel, nbLivreLabel, constraints, 0, 6, 1, 1);
        addComponent(panel, nbLivreTextField, constraints, 1, 6, 1, 1);

        JLabel autNumLabel = new JLabel("Auteur ID:");
        autNumTextField = new JTextField(20);
        addComponent(panel, autNumLabel, constraints, 0, 7, 1, 1);
        addComponent(panel, autNumTextField, constraints, 1, 7, 1, 1);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> modifierLivre());
        addComponent(panel, validerButton, constraints, 0, 8, 2, 1);

        chargerDetailsLivre();

        frame.getContentPane().add(panel);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
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

    private void chargerDetailsLivre() {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT ISBN, titre, prix, genre, disponible , nb_livre , Aut_num FROM livre WHERE ISBN = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, stringLivreId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        ISBNTextField.setText(rs.getString("ISBN"));
                        titreTextField.setText(rs.getString("titre"));
                        prixTextField.setText(String.valueOf(rs.getFloat("prix")));
                        genreTextField.setText(rs.getString("genre"));
                        disponibleTextField.setText(String.valueOf(rs.getInt("disponible")));
                        nbLivreTextField.setText(String.valueOf(rs.getInt("nb_livre")));
                        autNumTextField.setText(String.valueOf(rs.getInt("Aut_num")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modifierLivre() {
        String nouveauISBN = ISBNTextField.getText();
        String nouveauTitre = titreTextField.getText();
        String nouvelPrix = prixTextField.getText();
        String nouvelleGenre = genreTextField.getText();
        int nouveauDisponible = Integer.parseInt(disponibleTextField.getText());
        int nouveauNbLivre = Integer.parseInt(nbLivreTextField.getText());
        int nouveauAutNum = Integer.parseInt(autNumTextField.getText());

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE livre SET ISBN = ?, titre = ?, prix = ?, genre = ?, disponible = ?, nb_livre = ?, Aut_num = ?  WHERE ISBN = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nouveauISBN);
                stmt.setString(2, nouveauTitre);
                stmt.setString(3, nouvelPrix);
                stmt.setString(4, nouvelleGenre);
                stmt.setInt(5, nouveauDisponible);
                stmt.setInt(6, nouveauNbLivre);
                stmt.setInt(7, nouveauAutNum);
                stmt.setString(8, stringLivreId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Livre modifié avec succès.", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Aucun livre trouvé avec l'identifiant spécifié.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}