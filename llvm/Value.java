package llvm;

public interface Value {
  public LLVMType getType();
  public String toTypeString();
  public int getValue();
}
