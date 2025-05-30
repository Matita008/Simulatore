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
    * Constructs a Load instance with a file chooser configured to accept directories and files with .txt, .bat, or .sim extensions.
    *
    * The file chooser is set up with a custom file filter and registers an action listener to handle file selection events.
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
    * Displays a file chooser dialog for loading files, configuring the UI and disabling input methods.
    *
    * Opens a new frame containing the file chooser, applies the cross-platform look and feel, and ensures only single file selection is allowed. Input methods in the display are temporarily disabled while the dialog is active.
    *
    * @param e the action event that triggered this method
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
    * Handles the completion of a file selection event and loads the selected file.
    *
    * If the user approves the file selection, the selected file is passed to {@code Utils.loadMC} for processing.
    * The method also manages the visibility and disposal of the file chooser frame and restores input methods in the display.
    * No action is taken if the selection is canceled or if the frame is not present.
    *
    * @param e the action event triggered by the file chooser
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
