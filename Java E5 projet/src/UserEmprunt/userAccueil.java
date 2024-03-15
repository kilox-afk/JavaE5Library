package UserEmprunt;

import javax.swing.*;

import java.awt.*;

public class userAccueil {
    public void initialize() {
        // Création de la fenêtre d'accueil pour l'utilisateur
        JFrame frame = new JFrame("Accueil Utilisateur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panneau principal avec un gestionnaire de disposition BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Création du texte de description pour l'utilisateur
        JTextArea descriptionText = new JTextArea();
        descriptionText.setText("Bienvenue dans notre application de bibliothèque !\n"
                + "Vous pouvez emprunter des livres, les rendre et gérer votre compte.\n"
                + "Profitez de notre large sélection de livres et de fonctionnalités.");
        descriptionText.setEditable(false); // Empêche l'édition du texte par l'utilisateur
        descriptionText.setLineWrap(true); // Permet le retour à la ligne automatique
        descriptionText.setWrapStyleWord(true); // Empêche les mots d'être coupés

        // Ajout du texte de description au panneau principal
        panel.add(descriptionText, BorderLayout.CENTER);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre
        frame.setSize(400, 200);

        // Centrer la fenêtre sur l'écran
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Création d'une instance de la classe userAccueil et appel de la méthode initialize()
        userAccueil userPage = new userAccueil();
        userPage.initialize();
    }
}
