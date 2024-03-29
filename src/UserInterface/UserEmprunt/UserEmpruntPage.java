package UserInterface.UserEmprunt;

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

public class UserEmpruntPage {
    private JTable table;
    private DefaultTableModel tableModel;

    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void initialize(String adherentId) {
        JFrame frame = new JFrame("Page Vos Emprunt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton ajouterButton = new JButton("Ajouter");
        JButton supprimerButton = new JButton("Supprimer");
        JButton modifierButton = new JButton("Modifier");
        JButton rafraichirButton = new JButton("Rafraîchir");

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserEmpruntAjout ajoutPage = new UserEmpruntAjout();
                ajoutPage.initialize(adherentId);
            }
        });

        // supprimerButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         EmpruntSupprimer supprimerPage = new EmpruntSupprimer();
        //         supprimerPage.initialize(adherentId);
        //     }
        // });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserEmpruntModifier modifierPage = new UserEmpruntModifier();
                modifierPage.initialize(adherentId);
            }
        });

        rafraichirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherDonneesEmprunts(adherentId); // Utilisation de l'identifiant de l'adhérent
            }
        });

        buttonPanel.add(ajouterButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(rafraichirButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Emprunt N°");
        tableModel.addColumn("Date de l'emprunt");
        tableModel.addColumn("Date de retour");
        tableModel.addColumn("Statut de l'emprunt");
        tableModel.addColumn("Adh_num");
        tableModel.addColumn("ISBN");
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0 || i == 4) {
                column.setPreferredWidth(80);
            } else if (i == 1 || i == 2) {
                column.setPreferredWidth(200);
            } else {
                column.setPreferredWidth(150);
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Afficher les données de la table Emprunt dans le tableau lors de
        // l'initialisation
        afficherDonneesEmprunts(adherentId);
    }

    private void afficherDonneesEmprunts(String adherentId) {
        // Effacer les données existantes du tableau
        tableModel.setRowCount(0);

        try {
            // Connexion à la base de données
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Création de la requête SQL pour sélectionner toutes les colonnes de la table Emprunt
            // pour un adhérent spécifique
            String sql = "SELECT * FROM emprunt WHERE Adh_num = ?";

            // Création de la déclaration PreparedStatement pour exécuter la requête
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Paramétrage de la valeur de l'identifiant de l'adhérent dans la requête
                stmt.setString(1, adherentId);

                // Exécution de la requête
                try (ResultSet rs = stmt.executeQuery()) {
                    // Itération sur les résultats de la requête
                    while (rs.next()) {
                        // Récupération des valeurs de chaque colonne
                        int id_emprunt = rs.getInt("id_emprunt");
                        String date_emprunt = rs.getString("date_emprunt");
                        String date_retour = rs.getString("date_retour");
                        String statut_emprunt = rs.getString("statut_emprunt");
                        int Adh_num = rs.getInt("Adh_num");
                        String ISBN = rs.getString("ISBN");

                        // Ajout des valeurs au tableau
                        tableModel.addRow(new Object[]{id_emprunt, date_emprunt, date_retour, statut_emprunt, Adh_num, ISBN});
                    }
                }
            }

            // Fermeture de la connexion
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserEmpruntPage empruntPage = new UserEmpruntPage();
        // Vous devez fournir l'identifiant de l'adhérent ici, ou bien récupérer l'identifiant à partir d'une autre source
        String adherentId = "votre_id_adherent";
        empruntPage.initialize(adherentId);
    }
}
