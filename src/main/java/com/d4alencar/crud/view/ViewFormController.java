package com.d4alencar.crud.view;

import com.d4alencar.crud.service.LibraryService;
import com.d4alencar.crud.model.*;

import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.*;

public class ViewFormController {
  
  private final LibraryService libraryService;
  private final MainWindow view;
  private InputValidation inputValidation = new InputValidation();

  public ViewFormController (LibraryService service, MainWindow view) {
    this.libraryService = service;
    this.view = view;

    JRootPane viewRootPane = view.getRootPane();
    KeyStroke globalEnter = KeyStroke.getKeyStroke("ENTER");

    viewRootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(globalEnter, "globalEnter");
    viewRootPane.getActionMap().put("globalEnter", searchBook());
  }

  public void triggerDialogFunctionBtn (String function) {
    
    int selectedRow = -1;

    if(!function.equals("Delete")) {
      String titleBuff = view.retrieveTitle();
      String authorBuff = view.getAuthor();
      String yearBuff = view.getYear();

      int returnCode = inputValidation.checkBookFields(titleBuff, authorBuff, yearBuff);

      switch (returnCode) {
          case 0:
            Book book = new Book(0, titleBuff, Integer.parseInt(yearBuff), authorBuff);

            if(function.equals("Add")){
              libraryService.addBook(book);
            }else {
              selectedRow = view.getSelectedRow();
              book.setId(getBook(selectedRow).getId());
              libraryService.editBook(selectedRow, book);
            }
            view.disposeDialog();
            break;

          case 1:
            view.showMessage("Title field can't be empty.");
            break;

          case 2:
            view.showMessage("Caracters invalids for title/author fields.");
            break;

          case 3:
            view.showMessage("publish year invalid format!");
        }
    } else {
      selectedRow = view.getSelectedRow();
      if(selectedRow > 0) {
        libraryService.deleteBook(selectedRow);
        view.disposeDialog();
      }
    }
  }

  public Action searchBook() {
    return new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        libraryService.searchForBooks(view.getSearchKey(), view.getComboOption());
      }
    };
  }

  public Book getBook(int selectedRow) {
    return libraryService.getBook(selectedRow);
  }
}
