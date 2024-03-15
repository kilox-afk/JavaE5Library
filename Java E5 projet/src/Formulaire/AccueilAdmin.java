package Formulaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Formulaire.FormulaireAdherent.AdherentPage;
import Formulaire.FormulaireAuteur.AuteurPage;

public class AccueilAdmin {
    public void initialize() {
        // Création de la fenêtre d'accueil
        JFrame frame = new JFrame("Gestion Bibliothèque");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panneau principal avec BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Ajout d'un espace autour du panneau

        // Création du panneau pour les boutons avec une marge autour
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Ajout d'un espace autour du panneau
                                                                                // de boutons

        // Création des boutons pour chaque catégorie de gestion
        JButton formulaire1Button = new JButton("Gestion Adhérent");
        JButton formulaire2Button = new JButton("Gestion Auteur");
        JButton formulaire3Button = new JButton("Gestion Emprunt");
        JButton formulaire4Button = new JButton("Gestion Livre");

        // Définition des tailles personnalisées des boutons
        Dimension buttonSize = new Dimension(200, 50); // Taille personnalisée des boutons
        formulaire1Button.setPreferredSize(buttonSize);
        formulaire2Button.setPreferredSize(buttonSize);
        formulaire3Button.setPreferredSize(buttonSize);
        formulaire4Button.setPreferredSize(buttonSize);

        // Ajout des écouteurs d'événements aux boutons
        formulaire1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher la page Adhérent
                AdherentPage adherentPage = new AdherentPage();
                adherentPage.initialize();
            }
        });

        // Ajout des écouteurs d'événements aux boutons
        formulaire2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher la page Adhérent
                AuteurPage auteurPage = new AuteurPage();
                auteurPage.initialize();
            }
        });

        // Ajout des boutons au panneau pour les boutons
        buttonPanel.add(formulaire1Button);
        buttonPanel.add(formulaire2Button);
        buttonPanel.add(formulaire3Button);
        buttonPanel.add(formulaire4Button);

        // Ajout du panneau pour les boutons au panneau principal (position CENTER pour
        // l'étendre sur toute la fenêtre)
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajustement de la taille de la fenêtre
        frame.setSize(400, 300); // Taille initiale de la fenêtre

        // Centrer la fenêtre
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        AccueilAdmin accueil = new AccueilAdmin();
        accueil.initialize();
    }

}
