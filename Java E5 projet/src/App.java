import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    // Information sur la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheques-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Phrase d'introduction
    public static void main(String[] args) throws Exception {
        System.out.println("||| ---------------------------------------- ||| >>> DEBUT <<< ||| ---------------------------------------- |||");
        Separateur.afficherSeparateur(); // Appel direct à la méthode statique
        System.out.println("Projet Java E5 Gestion Bibliothèque");
        Separateur.afficherSeparateur(); // Appel direct à la méthode statique

        // Connexion à la base de données
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Separateur.afficherSeparateur2(); // Appel direct à la méthode statique
            System.out.println("||-|| Connexion à la base de données établie avec succès ! ||-||");
            Separateur.afficherSeparateur2(); // Appel direct à la méthode statique

            // Exécuter une requête pour récupérer les données
            System.out.println("========================== ------------------------ || AUTEUR || ------------------------ ==========================");
            Auteur.lireAuteur(conn); //Appelle de fonction qui se trouve dans un autre fichier
            System.out.println("========================== ------------------------ || ADHERENT || ------------------------ ==========================");
            Adherent.lireAdherent(conn);
            System.out.println("========================== ------------------------ || EMPRUNT || ------------------------ ==========================");
            Emprunt.lireEmprunt(conn);
            System.out.println("========================== ------------------------ || LIVRE || ------------------------ ==========================");
            Livre.lireLivre(conn);
        } catch (SQLException e) {
            Separateur.afficherSeparateur2(); // Appel direct à la méthode statique
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            Separateur.afficherSeparateur2(); // Appel direct à la méthode statique
        }
    }
}
