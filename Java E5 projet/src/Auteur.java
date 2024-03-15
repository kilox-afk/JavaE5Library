import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Importer java.sql.Date

public class Auteur {
    // Lecture de données (auteur)
    public static void lireAuteur(Connection conn) throws SQLException {
        String sql = "SELECT * FROM auteur"; // Remplacez auteur par le nom de votre table
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Separateur.afficherSeparateur(); // Appel direct à la méthode statique
                // Lire les données de chaque ligne (attribut)
                int Aut_num = rs.getInt("Aut_num"); // Remplacez "id" par le nom de votre colonne
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                Date date_naissance = rs.getDate("date_naissance");
                String description = rs.getString("description");

                // Affichage des données récupérées
                System.out.println("- Utilisateur numéro (ID) : " + Aut_num);
                System.out.println("- Nom: " + nom + " || Prénom: " + prenom);
                System.out.println("- Date de naissance: " + date_naissance + " || Description: " + description);
                Separateur.afficherSeparateur(); // Appel direct à la méthode statique
            }
        }
    }
}