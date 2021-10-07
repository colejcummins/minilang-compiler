package llvm;

public class GlobalValue implements Value {
  private final String name;
  private final LLVMType type;

  public GlobalValue(String name, LLVMType type) {
    this.name = name;
    this.type = type;
  }

  public String toString() {
    return "@" + name;
  }

  public String toTypeString() {
    return this.type.toString() + " " + this.toString();
  }

  public String getName() {
    return this.name;
  }

  public LLVMType getType() {
    return this.type;
  }

  public int getValue() {
    return 0;
  }
}
