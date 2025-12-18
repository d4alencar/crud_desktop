package com.d4alencar.crud.view;

import com.d4alencar.crud.service.LibraryService;
import com.d4alencar.crud.model.Book;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;

import javax.swing.*;

public class MainWindow extends JFrame {

  private JPanel mainPanel;
  private JPanel menuBar;
  private JPanel dialogPanel;

  private GridBagConstraints gridCons;

  private JDialog dialog;

  private JTable table;

  private JButton btnAddBook;
  private JButton btnEditBook;
  private JButton btnDeleteBook;
  private JButton btnSearchBook;
  private JButton btnSyncDB;
  private JTextField fldSearch;
  private JComboBox<String> CBOption;
  private String[] comboOptions = {"title", "author", "year"};

  private JLabel lblTitle;
  private JTextField fldBookTitle;
  private JTextField fldBookAuthor;
  private JTextField fldBookYear;
  private JButton btnCloseDialog;
  private JButton btnDialogFunction;

  private final LibraryService libraryService = new LibraryService();
  private final ViewFormController controller = new ViewFormController(libraryService, this);

  public MainWindow() {
    super("crud");
    createMenuBar();
    createTable();
    createWindow();

    SwingUtilities.invokeLater(() -> fldSearch.requestFocusInWindow());
  }

  private void createWindow() {
    mainPanel = new JPanel(new BorderLayout());

    mainPanel.add(menuBar, BorderLayout.NORTH);
    mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
    getContentPane().add(mainPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setVisible(true);
  }

  private void createMenuBar() {
    menuBar = new JPanel();
    menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

    btnAddBook = new JButton("Add");
    btnEditBook = new JButton("Edit");
    btnDeleteBook = new JButton("Delete");
    btnSearchBook = new JButton("Search");
    btnSyncDB = new JButton("Sync");
    fldSearch = new JTextField();
    CBOption = new JComboBox<>(comboOptions);

    btnAddBook.addActionListener(e -> openDialog(this, "Add"));
    btnEditBook.addActionListener(e -> openDialog(this, "Edit"));
    btnDeleteBook.addActionListener(e -> openDialog(this, "Delete"));
    btnSearchBook.addActionListener(controller.searchBook());

    menuBar.add(btnAddBook);
    menuBar.add(btnEditBook);
    menuBar.add(btnDeleteBook);
    menuBar.add(fldSearch);
    menuBar.add(CBOption);
    menuBar.add(btnSearchBook);
    //menuBar.add(btnSyncDB);
    //
  }

  private void createTable() {
    table = new JTable(libraryService.getModel());
    table.getColumnModel().getColumn(0).setPreferredWidth(1);
    table.getColumnModel().getColumn(1).setPreferredWidth(120);
    table.getColumnModel().getColumn(2).setPreferredWidth(120);
    table.getColumnModel().getColumn(3).setPreferredWidth(1);
  }

  private void openDialog(JFrame frame, String title) {
    dialog = new JDialog(frame, title, true);
    dialogPanel = new JPanel(new GridBagLayout());
    gridCons = new GridBagConstraints();
    gridCons.insets = new Insets(3, 3, 3, 3);

    if (!title.equals("Delete")) {
      gridCons.gridy = 0;
      gridCons.gridx = 0;
      gridCons.weightx = 0;
      gridCons.fill = GridBagConstraints.NONE;
      gridCons.anchor = GridBagConstraints.LINE_START;
      dialogPanel.add(new JLabel("Title"), gridCons);

      fldBookTitle = new JTextField();
      gridCons.gridy = 0;
      gridCons.gridx = 1;
      gridCons.weightx = 1.0f;
      gridCons.fill = GridBagConstraints.HORIZONTAL;
      gridCons.anchor = GridBagConstraints.CENTER;
      dialogPanel.add(fldBookTitle, gridCons);

      gridCons.gridy = 1;
      gridCons.gridx = 0;
      gridCons.weightx = 0;
      gridCons.fill = GridBagConstraints.NONE;
      gridCons.anchor = GridBagConstraints.LINE_START;
      dialogPanel.add(new JLabel("Author"), gridCons);

      fldBookAuthor = new JTextField();
      gridCons.gridy = 1;
      gridCons.gridx = 1;
      gridCons.weightx = 1.0f;
      gridCons.fill = GridBagConstraints.HORIZONTAL;
      gridCons.anchor = GridBagConstraints.CENTER;
      dialogPanel.add(fldBookAuthor, gridCons);

      gridCons.gridy = 2;
      gridCons.gridx = 0;
      gridCons.weightx = 0;
      gridCons.fill = GridBagConstraints.NONE;
      gridCons.anchor = GridBagConstraints.LINE_START;
      dialogPanel.add(new JLabel("Publish year"), gridCons);

      fldBookYear = new JTextField();
      gridCons.gridy = 2;
      gridCons.gridx = 1;
      gridCons.weightx = 1.0f;
      gridCons.fill = GridBagConstraints.HORIZONTAL;
      gridCons.anchor = GridBagConstraints.CENTER;
      dialogPanel.add(fldBookYear, gridCons);

    } else {
      lblTitle = new JLabel("You really want delete this item?");
      gridCons.gridy = 0;
      gridCons.gridx = 0;
      gridCons.weightx = 0f;
      gridCons.gridwidth = 3;
      gridCons.fill = GridBagConstraints.VERTICAL;
      gridCons.anchor = GridBagConstraints.CENTER;
      dialogPanel.add(lblTitle, gridCons);
    }

    btnCloseDialog = new JButton("Close");
    gridCons.gridy = 3;
    gridCons.gridx = 0;
    gridCons.gridwidth = 1;
    gridCons.weightx = 0.5f;
    gridCons.fill = GridBagConstraints.HORIZONTAL;
    gridCons.anchor = GridBagConstraints.LINE_END;
    dialogPanel.add(btnCloseDialog, gridCons);
    
    btnDialogFunction = new JButton(title);
    gridCons.gridy = 3;
    gridCons.gridx = 1;
    gridCons.weightx = 0.5f;
    gridCons.fill = GridBagConstraints.HORIZONTAL;
    gridCons.anchor = GridBagConstraints.LINE_START;
    dialogPanel.add(btnDialogFunction, gridCons);

    if(title.equals("Edit")) {
      setFields();
    }

    btnCloseDialog.addActionListener(e -> dialog.dispose());
    btnDialogFunction.addActionListener(e -> controller.triggerDialogFunctionBtn(title));

    JRootPane rootPane = dialog.getRootPane();
    KeyStroke escape = KeyStroke.getKeyStroke("ESCAPE");

    rootPane.setDefaultButton(btnDialogFunction);
    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "closeDialog");
    rootPane.getActionMap().put("closeDialog", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dialog.dispose();
      }
    });

    dialog.setSize(300, 150);
    dialog.setLocationRelativeTo(frame);
    dialog.add(dialogPanel);
    dialog.setVisible(true);

    fldSearch.requestFocusInWindow();
  }

  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  public void setFields () { 
    Book b = controller.getBook(getSelectedRow());
    fldBookTitle.setText(b.getTitle());
    fldBookYear.setText(String.valueOf(b.getYear()));
    fldBookAuthor.setText(b.getAuthor());
  }

  public void disposeDialog() {dialog.dispose();}

  public String retrieveTitle() {return fldBookTitle.getText();}
  public String getAuthor(){return fldBookAuthor.getText();}
  public String getYear(){return fldBookYear.getText();}

  public int getSelectedRow() {return table.getSelectedRow();}
  public String getSearchKey() {return fldSearch.getText();}
  public String getComboOption() {return String.valueOf(CBOption.getSelectedItem());}
}
