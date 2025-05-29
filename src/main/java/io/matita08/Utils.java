package io.matita08;

import java.io.File;

public class Utils {
   private final static ThreadGroup tg = new ThreadGroup("Utils-threads");
   
   private Utils() {throw new RuntimeException("You're an idiot");}
   
   public static void loadMC(File f){runOnNewThread(()->loadMCImpl(f));}
   
   private static void loadMCImpl(File f){
   
   }
   
   public static Thread runOnNewThread(Runnable run){
      Thread t = new Thread(tg, run);
      t.start();
      return t;
   }
}
