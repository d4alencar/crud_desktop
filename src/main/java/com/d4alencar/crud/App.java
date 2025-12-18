package com.d4alencar.crud;

import com.d4alencar.crud.view.MainWindow;
import javax.swing.*;

/*
 * BY: Daniel Alencar (@d4alencar) 
 */

public class App extends JFrame{
  public static void main( String[] args ){
    SwingUtilities.invokeLater(MainWindow::new);
  }
} 
