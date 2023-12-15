import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class MedicalRecordSystem {
    private JFrame mainFrame;
    private JTable patientsTable;
    private DefaultTableModel tableModel;
    private ArrayList<Patient> patients = new ArrayList<>();

    public MedicalRecordSystem() {
        mainFrame = new JFrame("Medical Record System");
        mainFrame.setLayout(new FlowLayout());

        // Create a JTable to display patient records
        tableModel = new DefaultTableModel(new String[] {"Name", "Age", "Diagnosis"}, 0);
        patientsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientsTable);

        // Add "Add Patient" button
        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddPatientDialog dialog = new AddPatientDialog();
                dialog.setVisible(true);
            }
        });

        // Add "Remove Patient" button
        JButton removePatientButton = new JButton("Remove Patient");
        removePatientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = patientsTable.getSelectedRow();
                if (selectedRow != -1) {
                    patients.remove(selectedRow);
                    tableModel.removeRow(selectedRow);
                }
            }
        });

        // Add "Search Patient" button
        JButton searchPatientButton = new JButton("Search Patient");
        searchPatientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchPatientDialog dialog = new SearchPatientDialog();
                dialog.setVisible(true);
            }
        });

        // Add "Patient List" button
        JButton patientListButton = new JButton("Patient List");
        patientListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display the patient list in a new frame
                JFrame listFrame = new JFrame("Patient List");
                listFrame.setLayout(new FlowLayout());
                listFrame.add(scrollPane);
                listFrame.setSize(600, 400);
                listFrame.setVisible(true);
            }
        });

        mainFrame.add(addPatientButton);
        mainFrame.add(removePatientButton);
        mainFrame.add(searchPatientButton);
        mainFrame.add(patientListButton);
        mainFrame.add(scrollPane);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);
        mainFrame.setVisible(true);
    }

    private class Patient {
        String name;
        int age;
        String diagnosis;

        Patient(String name, int age, String diagnosis) {
            this.name = name;
            this.age = age;
            this.diagnosis = diagnosis;
        }
    }

    private class AddPatientDialog extends JDialog {
        private JTextField nameField;
        private JTextField ageField;
        private JTextField diagnosisField;
    
        public AddPatientDialog() {
            setTitle("Add Patient");
            setLayout(new FlowLayout());
            setSize(400, 200); // Increase the width and height
    
            add(new JLabel("Name:"));
            nameField = new JTextField(30); // Increase the width of the text field
            add(nameField);
    
            add(new JLabel("Age:"));
            ageField = new JTextField(30); // Increase the width of the text field
            add(ageField);
    
            add(new JLabel("Diagnosis:"));
            diagnosisField = new JTextField(30); // Increase the width of the text field
            add(diagnosisField);
    
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String diagnosis = diagnosisField.getText();
    
                    Patient patient = new Patient(name, age, diagnosis);
                    patients.add(patient);
                    tableModel.addRow(new Object[] {name, age, diagnosis});
                    nameField.setText("");
                    ageField.setText("");
                    diagnosisField.setText("");
                    setVisible(false);
                }
            });
            add(saveButton);
        }
    }
    

    private class SearchPatientDialog extends JDialog {
        private JTextField searchNameField;

        public SearchPatientDialog() {
            setTitle("Search Patient");
            setLayout(new FlowLayout());
            setSize(400, 200);

            add(new JLabel("Search Name:"));
            searchNameField = new JTextField(20);
            add(searchNameField);

            JButton searchButton = new JButton("Search");
            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchName = searchNameField.getText();
                    for (Patient patient : patients) {
                        if (patient.name.equalsIgnoreCase(searchName)) {
                            JOptionPane.showMessageDialog(null, "Name: " + patient.name + "\nAge: " + patient.age + "\nDiagnosis: " + patient.diagnosis, "Patient Details", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }
                }
            });
            add(searchButton);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MedicalRecordSystem();
            }
        });
    }
}
