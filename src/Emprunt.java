import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Emprunt {
        public static void lireEmprunt(Connection conn) throws SQLException {
        String sql = "SELECT * FROM emprunt "; // Remplacez auteur par le nom de votre table
        try (PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Separateur.afficherSeparateur(); // Appel direct à la méthode statique
           // Lire les données de chaque ligne (attribut)
            int id_emprunt = rs.getInt("id_emprunt"); // Remplacez "id" par le nom de votre colonne
            Date date_emprunt = rs.getDate("date_emprunt");
            Date date_retour = rs.getDate("date_retour");
            Boolean statut_emprunt = rs.getBoolean("statut_emprunt");
            int Adh_num = rs.getInt("Adh_num");
            String ISBN = rs.getString("ISBN");

           // Affichage des données récupérées
            System.out.println("- Utilisateur numéro (ID) : " + id_emprunt);
            System.out.println("- Date emprunt: " + date_emprunt + " || Date retour: " + date_retour);
            System.out.println("- Statut: " + statut_emprunt);
            System.out.println("- Adhérent numéro : " + Adh_num + " || ISBN livre: " + ISBN);
            Separateur.afficherSeparateur(); // Appel direct à la méthode statique
            }
        }
    }
}
