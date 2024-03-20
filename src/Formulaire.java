import javax.swing.*;
import java.awt.*;

public class Formulaire {
    // Déclaration des champs pour pouvoir les récupérer dans la méthode validerFormulaire()
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField ageField;
    private JTextField emailField;
    private JComboBox<String> genreComboBox; // Ajout de la JComboBox pour le genre

    // Méthode pour ajouter un composant à un JPanel avec GridBagLayout
    private void addComponent(JPanel panel, Component component, GridBagConstraints constraints, int gridx, int gridy, int gridwidth, int gridheight) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        panel.add(component, constraints);
    }

    public void initialize() {
        // Création de la fenêtre
        JFrame frame = new JFrame("Formulaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panneau principal avec GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10); // Marge entre les composants

        // Ajout du titre
        JLabel titleLabel = new JLabel("Formulaire saisie");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Personnalisation de la police
        addComponent(panel, titleLabel, constraints, 0, 0, 2, 1); // Span sur 2 colonnes

        // Ajout des champs à saisir
        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField(20);
        addComponent(panel, nomLabel, constraints, 0, 1, 1, 1);
        addComponent(panel, nomField, constraints, 1, 1, 1, 1);

        JLabel prenomLabel = new JLabel("Prénom:");
        prenomField = new JTextField(20);
        addComponent(panel, prenomLabel, constraints, 0, 2, 1, 1);
        addComponent(panel, prenomField, constraints, 1, 2, 1, 1);

        JLabel ageLabel = new JLabel("Âge:");
        ageField = new JTextField(5);
        addComponent(panel, ageLabel, constraints, 0, 3, 1, 1);
        addComponent(panel, ageField, constraints, 1, 3, 1, 1);

        JLabel genreLabel = new JLabel("Genre:"); // Ajout du label pour la liste déroulante
        String[] genres = {"Homme", "Femme", "Autre"}; // Options de la liste déroulante
        genreComboBox = new JComboBox<>(genres); // Initialisation de la JComboBox avec les options
        addComponent(panel, genreLabel, constraints, 0, 4, 1, 1); // Ajout du label
        addComponent(panel, genreComboBox, constraints, 1, 4, 1, 1); // Ajout de la liste déroulante

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        addComponent(panel, emailLabel, constraints, 0, 5, 1, 1);
        addComponent(panel, emailField, constraints, 1, 5, 1, 1);

        // Ajout des boutons en bas
        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> validerFormulaire());
        addComponent(panel, validerButton, constraints, 0, 7, 1, 1);

        JButton annulerButton = new JButton("Annuler");
        annulerButton.addActionListener(e -> System.exit(0));
        addComponent(panel, annulerButton, constraints, 1, 7, 1, 1);

        // Ajout du panneau principal à la fenêtre
        frame.getContentPane().add(panel);

        // Ajuster la taille de la fenêtre
        frame.pack();

        // Centrer la fenêtre
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    private void validerFormulaire() {
        // Récupérer les valeurs des champs et de la liste déroulante et les afficher dans la console
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String age = ageField.getText();
        String genre = (String) genreComboBox.getSelectedItem(); // Récupération de la valeur sélectionnée dans la liste déroulante
        String email = emailField.getText();

        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Âge: " + age);
        System.out.println("Genre: " + genre); // Affichage de la valeur sélectionnée dans la liste déroulante
        System.out.println("Email: " + email);
    }

    public static void main(String[] args) {
        Formulaire formulaire = new Formulaire();
        formulaire.initialize();
    }
}
