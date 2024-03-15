import javax.swing.*;

import Formulaire.AccueilAdmin;
import UserEmprunt.userAccueil;

import java.awt.*;
import java.awt.event.*;

public class Accueil {
    public void initialize() {
        // Création de la fenêtre d'accueil
        JFrame frame = new JFrame("Accueil");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panneau principal avec un gestionnaire de disposition
        // BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Création d'un panneau pour les boutons avec un gestionnaire de disposition
        // FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centrage des boutons

        // Création du premier bouton pour rediriger vers la page userAccueil
        JButton button1 = new JButton("Page Utilisateur");
        // Définition de la taille préférée du bouton
        button1.setPreferredSize(new Dimension(150, 50));
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lorsque le bouton 1 est cliqué (redirection vers la page
                // userAccueil)
                redirectToUserAccueil();
            }
        });

        // Création du deuxième bouton pour rediriger vers la page Admin
        JButton button2 = new JButton("Page Admin");
        // Définition de la taille préférée du bouton
        button2.setPreferredSize(new Dimension(150, 50));
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lorsque le bouton 2 est cliqué (redirection vers la page
                // Admin)
                redirectToAdminPage();
            }
        });

        // Ajout des boutons au panneau des boutons
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Ajout du panneau des boutons au panneau principal
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre
        frame.setSize(400, 200); // Taille agrandie de la fenêtre

        // Centrer la fenêtre sur l'écran
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    // Méthode pour la redirection vers la page Admin
    private void redirectToAdminPage() {
        // Créer une instance de la classe AccueilAdmin et appeler la méthode
        // initialize() pour afficher l'interface Admin
        AccueilAdmin adminPage = new AccueilAdmin();
        adminPage.initialize();
    }

    // Méthode pour la redirection vers la page user
    private void redirectToUserAccueil() {
        // Créer une instance de la classe userAccueil et appeler la méthode
        // initialize() pour afficher l'interface user
        userAccueil userPage = new userAccueil();
        userPage.initialize();
    }

    public static void main(String[] args) {
        // Création d'une instance de la classe Accueil et appel de la méthode
        // initialize()
        Accueil accueil = new Accueil();
        accueil.initialize();
    }
}
