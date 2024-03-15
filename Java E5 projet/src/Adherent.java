import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Adherent {
    // Lecture de données (Adherent)
    public static void lireAdherent(Connection conn) throws SQLException {

        String sql = "SELECT * FROM adherent "; // Requete SQL
        try (PreparedStatement stmt = conn.prepareStatement(sql); // exécution de la requête
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Separateur.afficherSeparateur(); // Appel direct à la méthode statique
                // Lire les données de chaque ligne (attribut)
                int Adh_num = rs.getInt("Adh_num"); // Remplacez "id" par le nom de votre colonne
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String adresse = rs.getString("adresse");
                int nb_emprunt = rs.getInt("nb_emprunt");

                // Affichage des données récupérées
                System.out.println(
                        "- Utilisateur numéro (ID) : " + Adh_num + " || Nom : " + nom + " || Prénom : " + prenom);
                System.out.println("- Adresse : " + adresse);
                System.out.println("- Email : " + email + " || nombre d'emprunt : " + nb_emprunt);
                Separateur.afficherSeparateur(); // Appel direct à la méthode statique
            }
        }
    }
}
