package AdminInterface.AdminLivre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLivrePage {
    private JTable table;
    private DefaultTableModel tableModel;

    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void initialize() {
        JFrame frame = new JFrame("Page Livre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton ajouterButton = new JButton("Ajouter");
        JButton supprimerButton = new JButton("Supprimer");
        JButton modifierButton = new JButton("Modifier");
        JButton rafraichirButton = new JButton("Rafraîchir"); // Nouveau bouton Rafraîchir

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLivreAjout ajoutPage = new AdminLivreAjout();
                ajoutPage.initialize();
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLivreSupprimer supprimerPage = new AdminLivreSupprimer();
                supprimerPage.initialize();
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLivreModifier modifierPage = new AdminLivreModifier();
                modifierPage.initialize();
            }
        });

        rafraichirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherDonneesLivres(); // Rafraîchir les données
            }
        });

        buttonPanel.add(ajouterButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(rafraichirButton); // Ajout du bouton Rafraîchir

        panel.add(buttonPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ISBN Livre");
        tableModel.addColumn("Titre");
        tableModel.addColumn("Prix");
        tableModel.addColumn("Genre");
        tableModel.addColumn("Disponible");
        tableModel.addColumn("nb Livre");
        tableModel.addColumn("ID Auteur");
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non éditables
            }
        };
        
        // Définir la largeur des colonnes
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 2 || i == 6) {
                column.setPreferredWidth(70); // Largeur de la première colonne et de la dernière colonne
            } else if (i == 1) {
                column.setPreferredWidth(250); // Largeur des colonnes des adresses et des emails
            } else {
                column.setPreferredWidth(100); // Largeur par défaut pour les autres colonnes
            }
        }
        

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Afficher les données de la table Livre dans le tableau lors de
        // l'initialisation
        afficherDonneesLivres();
    }

    private void afficherDonneesLivres() {
        // Effacer les données existantes du tableau
        tableModel.setRowCount(0);

        try {
            // Connexion à la base de données
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Création de la requête SQL pour sélectionner toutes les colonnes de la table
            // Livre
            String sql = "SELECT * FROM livre";

            // Création de la déclaration PreparedStatement pour exécuter la requête
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                // Itération sur les résultats de la requête
                while (rs.next()) {
                    // Récupération des valeurs de chaque colonne
                    String ISBN = rs.getString("ISBN");
                    String titre = rs.getString("titre");
                    Float prix = rs.getFloat("prix");
                    String genre = rs.getString("genre");
                    Boolean disponible = rs.getBoolean("disponible");
                    int nb_livre = rs.getInt("nb_livre");
                    int Aut_num = rs.getInt("Aut_num");

                    // Ajout des valeurs au tableau
                    tableModel.addRow(new Object[] { ISBN, titre, prix, genre, disponible, nb_livre , Aut_num });
                }
            }

            // Fermeture de la connexion
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AdminLivrePage LivrePage = new AdminLivrePage();
        LivrePage.initialize();
    }
}
