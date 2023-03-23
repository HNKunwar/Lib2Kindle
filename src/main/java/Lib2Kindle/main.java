package Lib2Kindle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class main {
  private JFrame frame;
  private JPanel searchPanel;
  private JTextField searchField;
  private JCheckBox idCheck;
  private JCheckBox authorCheck;
  private JCheckBox titleCheck;
  private JCheckBox publisherCheck;
  private JCheckBox yearCheck;
  private JCheckBox pagesCheck;
  private JCheckBox languageCheck;
  private JCheckBox sizeCheck;
  private JCheckBox extensionCheck;
  private JButton searchButton;
  private JPanel resultsPanel;
  private JScrollPane scrollPane;

  public main() {
    frame = new JFrame("LibGen Search");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);

    searchPanel = new JPanel();
    searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    searchPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.EAST;
    gbc.insets = new Insets(0, 0, 10, 0);
    searchField = new JTextField(20);
    searchField.setPreferredSize(new Dimension(150, 25));
    searchPanel.add(searchField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 9;
    gbc.insets = new Insets(0, 20, 0, 0);
    JPanel checkBoxPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc2 = new GridBagConstraints();
    gbc2.gridx = 0;
    gbc2.gridy = 0;
    gbc2.anchor = GridBagConstraints.WEST;
    gbc2.insets = new Insets(0, 0, 5, 0);
    idCheck = new JCheckBox("ID");
    checkBoxPanel.add(idCheck, gbc2);
    gbc2.gridy++;
    authorCheck = new JCheckBox("Author");
    checkBoxPanel.add(authorCheck, gbc2);
    gbc2.gridy++;
    titleCheck = new JCheckBox("Title");
    checkBoxPanel.add(titleCheck, gbc2);
    gbc2.gridy++;
    publisherCheck = new JCheckBox("Publisher");
    checkBoxPanel.add(publisherCheck, gbc2);
    gbc2.gridy++;
    yearCheck = new JCheckBox("Year");
    checkBoxPanel.add(yearCheck, gbc2);
    gbc2.gridy++;
    pagesCheck = new JCheckBox("Pages");
    checkBoxPanel.add(pagesCheck, gbc2);
    gbc2.gridy++;
    languageCheck = new JCheckBox("Language");
    checkBoxPanel.add(languageCheck, gbc2);
    gbc2.gridy++;
    sizeCheck = new JCheckBox("Size");
    checkBoxPanel.add(sizeCheck, gbc2);
    gbc2.gridy++;
    extensionCheck = new JCheckBox("Extension");
    checkBoxPanel.add(extensionCheck, gbc2);
    searchPanel.add(checkBoxPanel, gbc);

    gbc.gridy = 10;
    gbc.gridwidth = 2;
    gbc.insets = new Insets(20, 0, 0, 0);
    searchButton = new JButton("Search");
    searchPanel.add(searchButton, gbc);

    resultsPanel = new JPanel(new GridBagLayout());
    resultsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    scrollPane = new JScrollPane(resultsPanel);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(searchPanel, BorderLayout.WEST);
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

    frame.setVisible(true);

    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        resultsPanel.removeAll();
        resultsPanel.revalidate();
        resultsPanel.repaint();

        LibgenSearch libgenSearch = new LibgenSearch();
        List<Map<String, String>> results = null;
        try {
          results = libgenSearch.searchTitle(searchField.getText());
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        List<Map<String, String>> filteredResults = new ArrayList<>();

        for (Map<String, String> result : results) {
          if (idCheck.isSelected() && result.get("ID").toLowerCase().contains(searchField.getText().toLowerCase())) {
            filteredResults.add(result);
          } else if (authorCheck.isSelected() && result.get("Author").toLowerCase().contains(searchField.getText().toLowerCase())) {
            filteredResults.add(result);
          } else if (titleCheck.isSelected() && result.get("Title").toLowerCase().contains(searchField.getText().toLowerCase())) {
            filteredResults.add(result);
          } else if (publisherCheck.isSelected() && result.get("Publisher").toLowerCase().contains(searchField.getText().toLowerCase())) {
            filteredResults.add(result);
          } else if (yearCheck.isSelected() && result.get("Year").toLowerCase().contains(searchField.getText().toLowerCase())) {
            filteredResults.add(result);
          } else if (pagesCheck.isSelected() && result.get("Pages").toLowerCase().contains(searchField.getText().toLowerCase())) {
            filteredResults.add(result);
          } else if (languageCheck.isSelected() && result.get("Language").toLowerCase().contains(searchField.getText().toLowerCase())) {
            filteredResults.add(result);
          } else if (sizeCheck.isSelected() && result.get("Size").toLowerCase().contains(searchField.getText().toLowerCase())) {
            filteredResults.add(result);
          } else if (extensionCheck.isSelected() && result.get("Extension").toLowerCase().contains(searchField.getText().toLowerCase())) {
            filteredResults.add(result);
          }
        }

        // Display the filtered results
        int row = 0;
        for (Map<String, String> filteredResult : filteredResults) {
          JLabel resultLabel = new JLabel();
          resultLabel.setText(String.format("ID: %s, Title: %s, Author: %s, Year: %s, Pages: %s, Language: %s, Size: %s, Extension: %s",
                  filteredResult.get("ID"), filteredResult.get("Title"), filteredResult.get("Author"), filteredResult.get("Year"),
                  filteredResult.get("Pages"), filteredResult.get("Language"), filteredResult.get("Size"), filteredResult.get("Extension")));
          resultLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

          GridBagConstraints c = new GridBagConstraints();
          c.gridx = 0;
          c.gridy = row;
          c.anchor = GridBagConstraints.WEST;
          c.fill = GridBagConstraints.HORIZONTAL;
          c.weightx = 1.0;
          c.gridwidth = GridBagConstraints.REMAINDER;
          resultsPanel.add(resultLabel, c);

          row++;

          JSeparator separator = new JSeparator();
          GridBagConstraints sepConstraints = new GridBagConstraints();
          sepConstraints.gridx = 0;
          sepConstraints.gridy = row;
          sepConstraints.fill = GridBagConstraints.HORIZONTAL;
          sepConstraints.weightx = 1.0;
          sepConstraints.gridwidth = GridBagConstraints.REMAINDER;
          resultsPanel.add(separator, sepConstraints);
          row++;

        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
      }
    });
  }

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new main();
      }
    });
  }
}

