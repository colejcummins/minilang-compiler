package llvm;

public class TempValue implements Value {
  private final String value;
  private final LLVMType type;

  public TempValue(String value) {
    this.value = value;
    this.type = new LLVMVoid();
  }

  public TempValue(String value, LLVMType type) {
    this.value = value;
    this.type = type;
  }

  public String toString() {
    return "%" + this.value;
  }

  public String toTypeString() {
    return this.type.toString() + " " + this.toString();
  }

  public LLVMType getType() {
    return this.type;
  }

  public int getValue() {
    return 0;
  }
}
