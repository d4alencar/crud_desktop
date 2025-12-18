package com.d4alencar.crud.service;

import com.d4alencar.crud.dao.BookDAO;
import com.d4alencar.crud.db.DatabaseConnection;
import com.d4alencar.crud.model.Book;
import com.d4alencar.crud.model.TableModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
  private final BookDAO bookDAO = new BookDAO();
  
  private List<Book> books;
  private TableModel modelTable;

  public LibraryService () {
    instantiateTable();
  }

  public TableModel getModel() {
    return modelTable;
  }

  private void instantiateTable() {
    try {
      DatabaseConnection.instanceTable();
      books = bookDAO.getAllBooks();
      modelTable = new TableModel(books);
    } catch (SQLException e) {
      System.out.println("Error creating table: " + e.getMessage());
    }
  }

  public boolean addBook(Book book) {
    try {
      int idB = bookDAO.addBook(book);
      book.setId(idB);
      modelTable.addBook(book);
      System.out.println("book added successfully!");
    } catch (SQLException e) {
      System.out.println("Error adding book: " + e.getMessage());
      return false;
    }
    return true;
  }

  public void editBook(int selectedRow, Book b) {
    try {
      bookDAO.editBook(b);
      modelTable.editBook(selectedRow, b);
    } catch (SQLException e) {
      System.out.println("Error editing book: " + e.getMessage());
    }
  }

  public void deleteBook(int selectedRow) {
    try {
      int bookID = (int)modelTable.getValueAt(selectedRow, 0);
      bookDAO.deleteBook(bookID);
      modelTable.removeBook(selectedRow);
    } catch (SQLException e) {
      System.out.println("Error deleting book: " + e.getMessage());
    }
  }

  public Book getBook(int selectedRow) {
    int idBuff = (int)modelTable.getValueAt(selectedRow, 0);
    String titleBuff = (String) modelTable.getValueAt(selectedRow, 1);
    String authorBuff = (String) modelTable.getValueAt(selectedRow, 2);
    int yearBuff = (int)modelTable.getValueAt(selectedRow, 3);

    Book b = new Book (idBuff, titleBuff, yearBuff, authorBuff);
    return b;
  }

  public void getAllBooks() {

    try {
      books = bookDAO.getAllBooks();
      modelTable = new TableModel(books);
      for (Book b : books) {
        modelTable.addBook(b);
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving books: " + e.getMessage());
    }
  }

  public void searchForBooks (String text, String comboOption) {
    try {
      modelTable.populateModel(bookDAO.searchBook(text, comboOption));
    } catch (SQLException e) {
      System.out.println("Error searching books: " + e.getMessage());
    }
  }
}
