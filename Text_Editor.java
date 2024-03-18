import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Text_Editor extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;
    private JButton openButton, saveButton, themeButton;
    private boolean isDarkTheme = false;
    public Text_Editor() {
        super("Text-Editor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.red);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        getContentPane().add(buttonPanel, BorderLayout.PAGE_START);
        openButton = createStyledButton("Open");
        openButton.addActionListener(this);
        buttonPanel.add(openButton);
        saveButton = createStyledButton("Save");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);
        themeButton = createStyledButton("Dark Theme");
        themeButton.addActionListener(this);
        buttonPanel.add(themeButton);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    FileReader reader = new FileReader(fileChooser.getSelectedFile());
                    BufferedReader br = new BufferedReader(reader);
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    textArea.setText(sb.toString());
                    br.close();
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == saveButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    FileWriter writer = new FileWriter(fileChooser.getSelectedFile());
                    writer.write(textArea.getText());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == themeButton) {
            toggleTheme();
        }
    }

  private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(120, 40)); 
        button.setBackground(new Color(179, 226, 145)); 
        button.setForeground(Color.black);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(130, 207, 88)); 
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(179, 226, 145));
            }
        });
        return button;
    }

    private void toggleTheme() {
        if (isDarkTheme) {
            setLightTheme();
        } else {
            setDarkTheme();
        }
        isDarkTheme = !isDarkTheme;
    }

    private void setDarkTheme() {
        getContentPane().setBackground(Color.black);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.white);
        openButton.setBackground(Color.darkGray);
        saveButton.setBackground(Color.darkGray);
        themeButton.setText("Light Theme");
    }

    private void setLightTheme() {
        getContentPane().setBackground(Color.white);
        textArea.setBackground(Color.white);
        textArea.setForeground(Color.black);
        openButton.setBackground(new Color(179, 226, 145)); 
        saveButton.setBackground(new Color(179, 226, 145)); 
        themeButton.setText("Dark Theme");
    }

    public static void main(String[] args) {
        new Text_Editor();
    }
}
