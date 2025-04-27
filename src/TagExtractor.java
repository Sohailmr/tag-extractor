import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class TagExtractor extends JFrame {

    private JTextArea textArea;
    private TagProcessor processor;
    private File selectedTextFile;
    private File selectedStopWordFile;

    public TagExtractor() {
        super("Tag/Keyword Extractor");
        processor = new TagProcessor();

        setupGUI();
    }

    private void setupGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel topPanel = new JPanel();
        JButton loadTextButton = new JButton("Load Text File");
        JButton loadStopWordsButton = new JButton("Load Stop Words File");
        JButton processButton = new JButton("Process File");
        JButton saveButton = new JButton("Save Results");

        topPanel.add(loadTextButton);
        topPanel.add(loadStopWordsButton);
        topPanel.add(processButton);
        topPanel.add(saveButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        loadTextButton.addActionListener(this::loadTextFile);
        loadStopWordsButton.addActionListener(this::loadStopWordsFile);
        processButton.addActionListener(this::processFiles);
        saveButton.addActionListener(this::saveResults);

        setVisible(true);
    }

    private void loadTextFile(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedTextFile = chooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Selected text file: " + selectedTextFile.getName());
        }
    }

    private void loadStopWordsFile(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedStopWordFile = chooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Selected stop words file: " + selectedStopWordFile.getName());
        }
    }

    private void processFiles(ActionEvent e) {
        if (selectedTextFile == null || selectedStopWordFile == null) {
            JOptionPane.showMessageDialog(this, "Please select both text and stop words files first!");
            return;
        }

        try {
            processor.loadStopWords(selectedStopWordFile);
            processor.processFile(selectedTextFile);
            textArea.setText(processor.getFormattedResults());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error processing files: " + ex.getMessage());
        }
    }

    private void saveResults(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = chooser.getSelectedFile();
            try {
                processor.saveResultsToFile(saveFile);
                JOptionPane.showMessageDialog(this, "Results saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TagExtractor::new);
    }
}
