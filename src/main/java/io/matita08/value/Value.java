package io.matita08.value;

public abstract class Value {
   
   @SuppressWarnings("StaticInitializerReferencesSubClass") public static final UndefinedValue nullValue = new UndefinedValue();
   
   /**
 * Returns a new Value instance cloned from the internal undefined sentinel.
 *
 * The returned object is a fresh clone of the shared `nullValue` (an UndefinedValue)
 * and may be used and mutated independently of the sentinel.
 *
 * @return a new Value cloned from the internal undefined value
 */
public static Value getNew() {return nullValue.clone();}
   
   /**
 * Creates a fresh composite Value suitable for representing an address.
 *
 * Returns a new DoubleValue composed of two distinct Value instances obtained from getNew(),
 * ensuring a fresh, independent pair of components.
 *
 * @return a new DoubleValue containing two fresh Value instances
 */
public static Value getNewAddress() {return new DoubleValue(getNew(), getNew());}
   
   /**
 * Create a new SingleValue initialized with the given integer.
 *
 * @param n the initial integer value for the new SingleValue
 * @return a new Value wrapping the provided integer
 */
public static Value create(int n) {return new SingleValue(n);}
   
   /**
 * Creates a new SingleValue initialized to the given integer with the specified signedness.
 *
 * @param n the initial integer value
 * @param signed true to treat the value as signed, false to treat it as unsigned
 * @return a new Value wrapping the specified integer and signedness
 */
public static Value create(int n, boolean signed) {return new SingleValue(n, signed);}
   
   public boolean isUndefined() {return false;}
   
   /**
 * Set this Value from the given integer and return this instance.
 *
 * Implementations should update the receiver to represent the integer `n`
 * (respecting any subclass-specific width or signedness) and return the same
 * Value object to allow call chaining.
 *
 * @param n the integer to store in this Value
 * @return this Value instance after mutation
 */
public abstract Value set(int n);
   
   /**
    * Set this value to the integer representation of another Value.
    *
    * <p>Copies the integer returned by {@code v.get()} into this value (delegates to {@link #set(int)}).
    *
    * @param v the source Value whose integer representation will be used
    */
   public void set(Value v) {
      set(v.get());
   }
   
   /**
 * Adds the numeric value of the given Value to this Value and returns the result.
 *
 * @param v2 the value to add
 * @return a Value representing the result of the addition
 */
public abstract Value add(Value v2);
   
   /**
 * Returns the numeric result of subtracting the given value from this value.
 *
 * Implementations must return a Value representing (this - v2); concrete subclasses define precise semantics (signed vs. unsigned interpretation, width and overflow behavior).
 *
 * @param v2 the value to subtract from this value
 * @return a Value representing the result of the subtraction
 */
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
   
   /**
 * Returns this value interpreted as a signed 32-bit integer.
 *
 * Implementations must provide the concrete signed integer representation for the specific Value subtype.
 *
 * @return the signed int representation of this Value
 */
public abstract int getSigned();
   
   /**
 * Returns this value interpreted as an unsigned integer.
 *
 * Implementations should provide the unsigned interpretation of the value's bits
 * and return it as an int (i.e., the numeric value when treated as unsigned).
 *
 * @return the unsigned interpretation of this Value
 */
public abstract int getUnsigned();
   
   /**
    * Checks whether this Value equals the given integer when interpreted as either signed or unsigned.
    *
    * Compares the supplied integer against this value's unsigned representation and its signed representation;
    * returns true if either comparison matches.
    *
    * @param n the integer to compare against this Value's signed and unsigned representations
    * @return true if `n` equals either `getUnsigned()` or `getSigned()`, otherwise false
    */
   public boolean equals(int n) {
      return getUnsigned() == n || getSigned() == n;
   }
}