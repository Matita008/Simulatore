package io.matita08.GUI.listeners;

import io.matita08.GUI.Display;
import io.matita08.GUI.Registers;
import io.matita08.Utils;
import io.matita08.value.Value;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Load implements ActionListener {
   JFileChooser fc;
   JFrame f;
   
   /**
    * Initializes a Load action handler.
    *
    * Sets up the JFileChooser used by this listener: applies a file filter that accepts directories
    * and files with extensions .txt, .bat, or .sim (description "Text file (.txt, .sim)"), and
    * registers the chooser's approval/cancel events to the instance's {@code load} handler.
    */
   public Load() {
      fc = new JFileChooser();
      fc.setFileFilter(new FileFilter() {//TODO Low priority: can be more clean?
         @Override
         public boolean accept(File f) {
            return f.isDirectory() || f.getName().endsWith(".txt") || f.getName().endsWith(".bat") || f.getName().endsWith(".sim");
         }
         
         @Override
         public String getDescription() {
            return "Text file (.txt, .sim)";
         }
      });
      fc.addActionListener(this::load);
   }
   /**
    * Opens an "Open file" dialog containing the configured JFileChooser and prepares the UI for file selection.
    *
    * The method creates and shows a JFrame that hosts the file chooser, applies the cross-platform LookAndFeel
    * (rethrowing as a RuntimeException if LookAndFeel setup fails), disables application input methods while the
    * chooser is shown, and ensures multi-selection is turned off.
    *
    * @param e the ActionEvent that triggered opening the file dialog
    * @throws RuntimeException if the cross-platform LookAndFeel cannot be instantiated or applied
    */
   @Override
   public void actionPerformed(ActionEvent e) {
      f = new JFrame("Open file");
      f.add(fc);
      f.setVisible(true);
      try {
         UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException ex) {
         //noinspection CallToPrintStackTrace
         ex.printStackTrace();
         throw new RuntimeException(ex);
      }
      f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      f.pack();
      f.setVisible(true);
      Display.instance.enableInputMethods(false);
      fc.setMultiSelectionEnabled(false);
   }
   
   /**
    * Handles the file-chooser action event: closes the chooser UI, re-enables input methods,
    * and if the user approved a selection, delegates loading of the selected file to Utils.loadMC.
    *
    * If the hosting frame is null the method returns immediately. The method ignores both
    * cancel events and any action commands other than "ApproveSelection".
    *
    * Side effects: hides and disposes the chooser frame, sets the internal frame reference to null,
    * and calls Utils.loadMC(...) with the selected file when appropriate.
    *
    * @param e the ActionEvent from the JFileChooser; its action command is inspected
    *          ("ApproveSelection" triggers loading, "CancelSelection" is ignored)
    */
   public void load(ActionEvent e) {
      if(f == null) return;
      Display.instance.enableInputMethods(true);
      System.out.println(e);
      f.setVisible(false);
      f.dispose();
      f = null;
      if (e.getActionCommand().equals("CancelSelection")) return;
      if (!e.getActionCommand().equals("ApproveSelection")) return;
      System.out.println("Opened file: " + fc.getSelectedFile().toString());
      Utils.loadMC(fc.getSelectedFile());
   }
}
