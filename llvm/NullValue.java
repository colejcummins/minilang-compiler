package llvm;

public class NullValue implements Value {
  public String toString() {
    return "null";
  }

  public LLVMType getType() {
    return new LLVMVoid();
  }

  public String toTypeString() {
    return "null";
  }

  public int getValue() {
    return 0;
  }
}
