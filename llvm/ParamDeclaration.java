package llvm;

public class ParamDeclaration {
  private final LLVMType type;
  private final Value value;

  public ParamDeclaration(LLVMType type, Value value) {
    this.type = type;
    this.value = value;
  }

  public String toString() {
    return String.format("%s %s", this.type.toString(), this.value.toString());
  }

  public LLVMType getType() {
    return this.type;
  }

  public Value getValue() {
    return this.value;
  }
}
