package io.matita08.value;

public abstract class Value {
   
   @SuppressWarnings("StaticInitializerReferencesSubClass") public static final UndefinedValue nullValue = new UndefinedValue();
   
   public static Value getNew() {return nullValue.clone();}
   
   public static Value create(int n) {return new SingleValue(n);}
   
   public static Value create(int n, boolean signed) {return new SingleValue(n, signed);}
   
   public boolean isUndefined() {return false;}
   
   public abstract Value set(int n);
   
   public abstract Value add(Value v2);
   
   public abstract Value sub(Value v2);
   
   public abstract Value mul(Value v2);
   
   @Override
   public String toString() {
      return "Obj: " + super.toString() + "  With default value = " + get() + "     Signed: " + getSigned() + "     Unsigned: " + getUnsigned();
   }
   
   public int get() {
      return getSigned();
   }
   
   public static String unset(){return "";}
   
   public abstract int getSigned();
   
   public abstract int getUnsigned();
}