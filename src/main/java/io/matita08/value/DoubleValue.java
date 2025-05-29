package io.matita08.value;

public class DoubleValue extends Value {
   
   private Value tmp;
   private final Value c1 = new SingleValue(1, false);
   
   public DoubleValue() {
      this(0);
   }
   
   public DoubleValue(int n) {
      tmp = new SingleValue(n);
   }
   
   @Override
   public Value set(int n) {
      return tmp.set(n);
   }
   
   @Override
   public int getSigned() {
      return tmp.getSigned();
   }
   
   @Override
   public int get() {
      return tmp.getUnsigned();
   }
   
   @Override
   public int getUnsigned() {
      return tmp.getUnsigned();
   }
   
   @Override
   public Value add(Value v2) {
      return tmp.add(v2);
   }
   
   @Override
   public Value sub(Value v2) {
      return tmp.sub(v2);
   }
   
   @Override
   public Value mul(Value v2) {
      return tmp.mul(v2);
   }
   
   public static String unset(){return SingleValue.unset();}
   
   public Value getAndInc() {
      int before = tmp.get();
      tmp = tmp.add(c1);
      return new DoubleValue(before);
   }
}
