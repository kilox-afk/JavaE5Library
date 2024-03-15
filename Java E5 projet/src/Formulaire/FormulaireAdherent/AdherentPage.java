package Formulaire.FormulaireAdherent;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdherentPage {
    private JTextArea textArea;

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
                AdherentAjout ajoutPage = new AdherentAjout();
                ajoutPage.initialize();
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdherentSupprimer supprimerPage = new AdherentSupprimer();
                supprimerPage.initialize();
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdherentModifier modifierPage = new AdherentModifier();
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

        textArea = new JTextArea();

        // Définition d'un style de bordure pour le JTextArea
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        // Application du style de bordure au JTextArea
        textArea.setBorder(border);

        // Définition d'une police de caractères différente pour le JTextArea
        Font font = new Font("Arial", Font.PLAIN, 14);
        textArea.setFont(font);

        // Insertion d'une ligne vide entre chaque enregistrement pour améliorer la
        // lisibilité
        textArea.setLineWrap(true); // Assurez-vous que le texte se remplit à la ligne suivante si nécessaire
        textArea.setWrapStyleWord(true); // Assurez-vous que les mots sont enveloppés correctement

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Afficher les données de la table adherent dans le JTextArea lors de
        // l'initialisation
        afficherDonneesAdherents();
    }

    private void afficherDonneesAdherents() {
        try {
            // Connexion à la base de données
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Création de la requête SQL pour sélectionner toutes les colonnes de la table
            // Adherent
            String sql = "SELECT * FROM adherent";

            // Création de la déclaration PreparedStatement pour exécuter la requête
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                // Initialisation d'une variable pour stocker les données à afficher dans le
                // JTextArea
                StringBuilder donneesAdherents = new StringBuilder();

                // Itération sur les résultats de la requête
                while (rs.next()) {
                    // Récupération des valeurs de chaque colonne
                    int Adh_num = rs.getInt("Adh_num");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String email = rs.getString("email");
                    String adresse = rs.getString("adresse");
                    int nb_emprunt = rs.getInt("nb_emprunt");

                    // Ajout des valeurs à la chaîne de données à afficher
                    donneesAdherents
                            .append(" ---------------------- Utilisateur numéro : " + Adh_num
                                    + " ----------------------" + "\n")
                            .append(" - Nom : ").append(nom).append("\n")
                            .append(" - Prénom : ").append(prenom).append("\n")
                            .append(" - Adresse : ").append(adresse).append("\n")
                            .append(" - Email : ").append(email).append("\n")
                            .append(" - Nombre d'emprunt : ").append(nb_emprunt).append("\n\n");
                }

                // Affichage des données dans le JTextArea
                textArea.setText(donneesAdherents.toString());
            }

            // Fermeture de la connexion
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AdherentPage adherentPage = new AdherentPage();
        adherentPage.initialize();
    }
}
