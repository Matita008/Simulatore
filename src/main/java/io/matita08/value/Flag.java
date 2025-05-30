package io.matita08.value;

import io.matita08.Utils;

public class Flag {
   private boolean defined;
   private boolean value;
   
   /**
    * Constructs a new undefined Flag.
    *
    * The flag's value is not set and is considered undefined until explicitly assigned.
    */
   public Flag(){
      defined = false;
   }
   
   /**
    * Constructs a defined Flag with the specified boolean value.
    *
    * @param value the initial value of the flag; may be null, in which case the flag is defined but its value is null
    */
   public Flag(Boolean value){
      defined = true;
      this.value = value;
   }
   
   /**
    * Returns whether the flag's value has been explicitly defined.
    *
    * @return true if the flag is defined; false otherwise
    */
   public boolean isDefined() {
      return defined;
   }
   
   /****
    * Sets the flag's value and marks it as defined.
    *
    * @param v the boolean value to assign to the flag
    */
   public void set(boolean v) {
      defined = true;
      value = v;
   }
   
   /**
    * Returns the flag's value if defined; otherwise, returns a random boolean.
    *
    * @return the defined value of the flag, or a random boolean if undefined
    */
   public boolean get() {
      return defined ? value : Utils.rng.nextBoolean();
   }
   
   /**
 * Compares this flag to another flag for equality.
 *
 * @param other the flag to compare with
 * @return true if both flags have the same defined state and value; false otherwise
 */
public boolean comp(Flag other){return equals(other);}
   
   /**
    * Determines whether this flag is equal to another object.
    *
    * Returns true if the other object is a {@code Flag} with the same defined state and value.
    *
    * @param o the object to compare with this flag
    * @return true if the specified object is a {@code Flag} with identical defined state and value; false otherwise
    */
   @Override
   public final boolean equals(Object o) {
      if(this == o) return true;
      if(!(o instanceof Flag flag)) return false;
      return defined == flag.defined && value == flag.value;
   }
   
   /**
    * Checks if the flag is defined and its value equals the specified boolean.
    *
    * @param o the boolean value to compare with the flag's value
    * @return true if the flag is defined and its value equals {@code o}; false otherwise
    */
   public final boolean equals(boolean o) {
      return defined && value == o;
   }
   
   /**
    * Computes the hash code for this flag based on its defined state and value.
    *
    * @return the hash code representing this flag
    */
   @Override
   public int hashCode() {
      int result = Boolean.hashCode(defined);
      result = 31 * result + Boolean.hashCode(value);
      return result;
   }
   
   /**
    * Returns a string representation of the flag: "1" if defined and true, "0" if defined and false, or "?" if undefined.
    *
    * @return the string representation of the flag's state
    */
   @Override
   public String toString() {
      return defined ? (value ? "1" : "0") : "?";
   }
   
}
