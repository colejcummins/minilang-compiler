package llvm;

public class StructHelper {
  private String str;
  private LLVMType type;

  public StructHelper(String str, LLVMType type) {
    this.str = str;
    this.type = type;
  }

  public String toString() {
    return String.format("%s %s", this.str, this.type.toString());
  }

  public String getStr() {
    return this.str;
  }

  public LLVMType getType() {
    return this.type;
  }
}
