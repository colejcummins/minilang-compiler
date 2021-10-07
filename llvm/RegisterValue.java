package llvm;

public class RegisterValue implements Value {
  private final int num;
  private final LLVMType type;

  public RegisterValue(int num) {
    this.num = num;
    this.type = new LLVMI64();
  }

  public RegisterValue(int num, LLVMType type) {
    this.num = num;
    this.type = type;
  }

  public String toString() {
    return "%u" + Integer.toString(this.num);
  }

  public String toTypeString() {
    return this.type.toString() + " " + this.toString();
  }

  public int getNum() {
    return this.num;
  }

  public LLVMType getType() {
    return this.type;
  }

  public int getValue() {
    return 0;
  }
}
