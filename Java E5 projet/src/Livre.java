import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Livre {
    // Lecture de données (livre)
    public static void lireLivre(Connection conn) throws SQLException {
        String sql = "SELECT * FROM livre "; // Remplacez auteur par le nom de votre table
        try (PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Separateur.afficherSeparateur(); // Appel direct à la méthode statique
            // Lire les données de chaque ligne (attribut)
            String ISBN = rs.getString("ISBN"); // Remplacez "id" par le nom de votre colonne
            String titre = rs.getString("titre");
            float prix = rs.getFloat("prix");
            String genre = rs.getString("genre");
            Boolean disponible = rs.getBoolean("disponible");
            int nb_livre = rs.getInt("nb_livre");
            int Aut_num = rs.getInt("Aut_num");

           // Affichage des données récupérées
            System.out.println("- Utilisateur numéro (ISBN) : " + ISBN + " || de l'auteur numéro: " + Aut_num);
            System.out.println("- Titre: " + titre + " || Prix: " + prix + " euros");
            System.out.println("- Genre: " + genre + " || Disponible: " + disponible + " || Nombre disponible : " + nb_livre);
            Separateur.afficherSeparateur(); // Appel direct à la méthode statique
            }   
        }
    }
}
