package io.matita08;

public final class Constants {
   private Constants() {
      throw new IllegalAccessError("Just why?");
   }
   public static int MC_Size = 16;
   public static int Value_max = 16;
   @SuppressWarnings("Unused")//TODO Remove me ;) //TODO Better implement some cfg system
   public static int Value_signed_flag = 1 | 2;//1 is enabled\disabled signed mode, 2 is default read from MC as signed(set) or not(cleared)
}
