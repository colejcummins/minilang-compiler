package llvm;

public class IntValue implements Value {
  private final int value;
  private final LLVMType type;

  public IntValue(int value) {
    this.value = value;
    this.type = new LLVMI64();
  }

  public String toString() {
    return Integer.toString(this.value);
  }

  public String toTypeString() {
    return type.toString() + " " + this.toString();
  }

  public LLVMType getType() {
    return this.type;
  }

  public int getValue() {
    return this.value;
  }
}
