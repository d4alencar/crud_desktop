package com.d4alencar.crud.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

  private static final int COL_ID = 0;
  private static final int COL_TITLE = 1;
  private static final int COL_AUTHOR = 2;
  private static final int COL_YEAR = 3;

  List<Book> rows;
  private String[] columns = new String[]{"Id", "Title", "Author", "Year"};

  public TableModel(List<Book> books) {
    this.rows = new ArrayList<>(books);
  }

  public int getRowCount() {
    return rows.size();
  }

  public int getColumnCount() {
    return columns.length;
  }

  public String getColumnName(int columnIndex) {
    return columns[columnIndex];
  }

  public Class<?> getColumnClass(int columnIndex) {
    switch(columnIndex) {
      
      case COL_ID:
        return Integer.class;

      case COL_TITLE:
        return Integer.class;

      case COL_AUTHOR:
        return String.class;

      case COL_YEAR:
        return Integer.class;
    }
    return String.class;
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  public Object getValueAt(int row, int column) {
    Book b = rows.get(row);

    switch(column) {
      
      case COL_ID:
        return b.getId();

      case COL_TITLE:
        return b.getTitle();

      case COL_AUTHOR:
        return b.getAuthor();

      case COL_YEAR:
        return b.getYear();
    }
    return "";
  }

  public void addBook (Book book) {
    rows.add(book);
    int lastIndex = getRowCount();
    fireTableRowsInserted(lastIndex, lastIndex);
  }

  public void editBook (int rowIndex, Book b) {
    rows.set(rowIndex, b);
    fireTableRowsUpdated(rowIndex, rowIndex);
  }

  public void removeBook (int rowIndex) {
    rows.remove(rowIndex);
    fireTableRowsDeleted(rowIndex, rowIndex);
  }

  /*public void searchForBooks (String text) {
    
    List<Book>bs = new ArrayList<>();
    bs = rows;
    rows.clear();
    fireTableRowsDeleted(0, getRowCount());
    for (Book b : rows) {
      if(text.equals(b.getTitle())) {
        rows.add(b);
      }
    }
    fireTableRowsUpdated(0, getRowCount());
    System.out.println(bs);
  }*/

  public void clearModel() {
    rows.clear();
    fireTableRowsDeleted(0, getRowCount());
  }

  public void populateModel (List<Book> books) {
    clearModel();
    for (Book b : books) {
      addBook(b);
      fireTableRowsUpdated(0, getRowCount());
    }
  }
}
