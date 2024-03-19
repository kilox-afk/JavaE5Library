package Formulaire.FormulaireLivre;

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

public class LivrePage {
    private JTable table;
    private DefaultTableModel tableModel;

    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void initialize() {
        JFrame frame = new JFrame("Page Adhérent");
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
                LivreAjout ajoutPage = new LivreAjout();
                ajoutPage.initialize();
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LivreSupprimer supprimerPage = new LivreSupprimer();
                supprimerPage.initialize();
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LivreModifier modifierPage = new LivreModifier();
                modifierPage.initialize();
            }
        });

        rafraichirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherDonneesAdherents(); // Rafraîchir les données
            }
        });

        buttonPanel.add(ajouterButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(rafraichirButton); // Ajout du bouton Rafraîchir

        panel.add(buttonPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Adhérent");
        tableModel.addColumn("Nom");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("Email");
        tableModel.addColumn("Adresse");
        tableModel.addColumn("Nb Emprunt");
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
            if (i == 0 || i == 5) {
                column.setPreferredWidth(80); // Largeur de la première colonne et de la dernière colonne
            } else if (i == 3 || i == 4) {
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

        // Afficher les données de la table adherent dans le tableau lors de
        // l'initialisation
        afficherDonneesAdherents();
    }

    private void afficherDonneesAdherents() {
        // Effacer les données existantes du tableau
        tableModel.setRowCount(0);

        try {
            // Connexion à la base de données
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Création de la requête SQL pour sélectionner toutes les colonnes de la table
            // Adherent
            String sql = "SELECT * FROM adherent";

            // Création de la déclaration PreparedStatement pour exécuter la requête
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                // Itération sur les résultats de la requête
                while (rs.next()) {
                    // Récupération des valeurs de chaque colonne
                    int Adh_num = rs.getInt("Adh_num");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String email = rs.getString("email");
                    String adresse = rs.getString("adresse");
                    int nb_emprunt = rs.getInt("nb_emprunt");

                    // Ajout des valeurs au tableau
                    tableModel.addRow(new Object[] { Adh_num, nom, prenom, email, adresse, nb_emprunt });
                }
            }

            // Fermeture de la connexion
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LivrePage adherentPage = new LivrePage();
        adherentPage.initialize();
    }
}
