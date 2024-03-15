package Formulaire.FormulaireAdherent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormulaireAdherent {
    private JTextField champ1Field;
    private JTextField champ2Field;
    private JTextField champ3Field;
    private JTextField champ4Field;
    private JTextField champ5Field;

    private void addComponent(JPanel panel, Component component, GridBagConstraints constraints, int gridx, int gridy, int gridwidth, int gridheight) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        panel.add(component, constraints);
    }

    public void initialize() {
        JFrame frame = new JFrame("Formulaire Adhérent");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Formulaire Adhérent");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addComponent(panel, titleLabel, constraints, 0, 0, 2, 1);

        JLabel champ1Label = new JLabel("Champ 1:");
        champ1Field = new JTextField(20);
        addComponent(panel, champ1Label, constraints, 0, 1, 1, 1);
        addComponent(panel, champ1Field, constraints, 1, 1, 1, 1);

        JLabel champ2Label = new JLabel("Champ 2:");
        champ2Field = new JTextField(20);
        addComponent(panel, champ2Label, constraints, 0, 2, 1, 1);
        addComponent(panel, champ2Field, constraints, 1, 2, 1, 1);

        JLabel champ3Label = new JLabel("Champ 3:");
        champ3Field = new JTextField(20);
        addComponent(panel, champ3Label, constraints, 0, 3, 1, 1);
        addComponent(panel, champ3Field, constraints, 1, 3, 1, 1);

        JLabel champ4Label = new JLabel("Champ 4:");
        champ4Field = new JTextField(20);
        addComponent(panel, champ4Label, constraints, 0, 4, 1, 1);
        addComponent(panel, champ4Field, constraints, 1, 4, 1, 1);

        JLabel champ5Label = new JLabel("Champ 5:");
        champ5Field = new JTextField(20);
        addComponent(panel, champ5Label, constraints, 0, 5, 1, 1);
        addComponent(panel, champ5Field, constraints, 1, 5, 1, 1);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique de validation ici
            }
        });
        addComponent(panel, validerButton, constraints, 0, 6, 1, 1);

        frame.getContentPane().add(panel);

        // Ajuster la taille de la fenêtre du formulaire
        frame.setSize(400, 300); // Vous pouvez ajuster la taille selon vos besoins

        // Centrer la fenêtre
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }
}
