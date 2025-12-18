package com.d4alencar.crud.dao;

import com.d4alencar.crud.model.Book;
import com.d4alencar.crud.db.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class BookDAO {

  public int addBook (Book book) throws SQLException {
    String sql = "INSERT INTO books (title, year, author) VALUES (?, ?, ?)";
    int idBuffer = 0;
    try(Connection conn = DatabaseConnection.getConnection()) {
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, book.getTitle());
      stmt.setInt(2, book.getYear());
      stmt.setString(3, book.getAuthor());
      stmt.executeUpdate();
      
      ResultSet rs = stmt.getGeneratedKeys();
      if(rs.next()) {
        idBuffer = rs.getInt(1);
      }
    }
    return idBuffer;

  }

  public List<Book> getAllBooks() throws SQLException {
    String sql = "SELECT * FROM books ORDER BY id";
    List<Book> books = new ArrayList<>();
    try (Connection conn = DatabaseConnection.getConnection()) {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        books.add(new Book(
        rs.getInt("id"),
        rs.getString("title"),
        rs.getInt("year"),
        rs.getString("author")));
      }
    }
    return books;
  }

  public void deleteBook (int id) throws SQLException {
    String sql = "DELETE FROM books WHERE id = ?";
    try(Connection conn = DatabaseConnection.getConnection()) {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, id);
      stmt.executeUpdate();
    }
  }

  public void editTitle (int id, String newTitle) throws SQLException {
    String sql = "UPDATE books SET title = ? WHERE id = ?";
    try(Connection conn = DatabaseConnection.getConnection()) {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newTitle);
      stmt.setInt(2, id);
      stmt.executeUpdate();
    }
  }

  public void editBook (Book book) throws SQLException {
    String sql = "UPDATE books SET title = ?, year = ?, author = ? WHERE id = ?";
    try(Connection conn = DatabaseConnection.getConnection()) {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, book.getTitle());
      stmt.setInt(2, book.getYear());
      stmt.setString(3, book.getAuthor());
      stmt.setInt(4, book.getId());
      stmt.executeUpdate();
    }
  }

  public List<Book> searchBook (String key, String option) throws SQLException {
    List<Book> books = new ArrayList<>();
    try(Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT * FROM books WHERE year = CAST(? AS int) ORDER BY id";
      if(option.equals("year")) {

        if(key.isBlank()) {
          return getAllBooks();
        }

        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, key);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
          books.add(new Book(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getInt("year"),
            rs.getString("author")
          ));
        }
      } else {
        sql = "SELECT * FROM books WHERE " + option + " ILIKE ? ORDER BY id";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, "%"+key+"%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
          books.add(new Book(
          rs.getInt("id"),
          rs.getString("title"),
          rs.getInt("year"),
          rs.getString("author")));
        }
      }
    }
    return books;
  }
}
