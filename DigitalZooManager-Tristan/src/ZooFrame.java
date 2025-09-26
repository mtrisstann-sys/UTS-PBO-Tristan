import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ZooFrame extends JFrame {
    private final JTextField nameField = new JTextField(15);
    private final JTextField ageField = new JTextField(15);

    // Select animal type
    private final JComboBox<String> animalTypeComboBox = new JComboBox<>(new String[]{"Choose Type", "Generic Mammal", "Generic Bird"});

    // Atribut spesifik untuk Mammal dan Bird
    private final JTextField furColorField = new JTextField(15);
    private final JCheckBox canFlyCheckBox = new JCheckBox("Can Fly");

    private final JButton addButton = new JButton("Add Animal");
    private final JTextArea displayArea = new JTextArea(12, 40);

    // In-memory storage untuk menampung hewan menggunakan Polymorphism
    private final ArrayList<Animal> animals = new ArrayList<>();

    public ZooFrame() {
        super("Digital Zoo Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel();
        form.setBorder(BorderFactory.createTitledBorder("Add New Animal"));
        form.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Name
        gbc.gridx = 0;
        gbc.gridy = row;
        form.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(nameField, gbc);
        row++;

        // Age
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        form.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(ageField, gbc);
        row++;

        // Animal Type
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        form.add(new JLabel("Animal Type:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(animalTypeComboBox, gbc);
        row++;

        // Panel atribut spesifik
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel attributeLabel = new JLabel("");
        form.add(attributeLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel attributePanel = new JPanel(new CardLayout());

        JPanel emptyPanel = new JPanel();
        attributePanel.add(emptyPanel, "Choose Type");

        // Add Mammal attributes
        JPanel mammalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        mammalPanel.add(furColorField);
        attributePanel.add(mammalPanel, "Generic Mammal");

        // Add Bird attributes  
        JPanel birdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        birdPanel.add(canFlyCheckBox);
        attributePanel.add(birdPanel, "Generic Bird");

        form.add(attributePanel, gbc);
        row++;

        // Add Button

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        form.add(addButton, gbc);

        mainPanel.add(form, BorderLayout.NORTH);

        // Log Area
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        displayArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(displayArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Animal Log"));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Event Handling untuk combobox
        animalTypeComboBox.addActionListener((ActionEvent e) -> {
            CardLayout cl = (CardLayout) (attributePanel.getLayout());
            String selectedType = (String) animalTypeComboBox.getSelectedItem();
            cl.show(attributePanel, selectedType);

            if ("Generic Mammal".equals(selectedType)) {
                attributeLabel.setText("Fur Color:");
            } else if ("Generic Bird".equals(selectedType)) {
                attributeLabel.setText("Can Fly:");
            } else {
                attributeLabel.setText("");
            }
        });

        // Event Handling untuk Add Button
        addButton.addActionListener((ActionEvent e) -> {
            try {
                // Read input from fields
                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String selectedType = (String) animalTypeComboBox.getSelectedItem();

                // Validate input
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a name.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (ageText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter an age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int age;
                try {
                    age = Integer.parseInt(ageText);
                    if (age < 0) {
                        JOptionPane.showMessageDialog(this, "Age must be a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid age (number).", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if ("Choose Type".equals(selectedType)) {
                    JOptionPane.showMessageDialog(this, "Please select an animal type.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Animal newAnimal;
                if ("Generic Mammal".equals(selectedType)) {
                    String furColor = furColorField.getText().trim();
                    if (furColor.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter a fur color.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    newAnimal = new Mammal(name, age, furColor);
                } else if ("Generic Bird".equals(selectedType)) {
                    boolean canFly = canFlyCheckBox.isSelected();
                    newAnimal = new Bird(name, age, canFly);
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a valid animal type.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                animals.add(newAnimal);

                String displayType = selectedType.replace("Generic ", "");
                String logEntry = "Added a new " + displayType + "! Info: " + newAnimal.getInfo() + ". Sound: " + newAnimal.makeSound() + "\n";
                displayArea.append(logEntry);

                nameField.setText("");
                ageField.setText("");
                furColorField.setText("");
                canFlyCheckBox.setSelected(false);
                animalTypeComboBox.setSelectedIndex(0); // Reset kembali ke "Choose Type"

                displayArea.setCaretPosition(displayArea.getDocument().getLength());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setMinimumSize(new Dimension(500, 400));
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Fall back ke default look and feel
            }

            ZooFrame frame = new ZooFrame();

            frame.pack();
            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
            frame.toFront();
            frame.requestFocus();
        });
    }
}