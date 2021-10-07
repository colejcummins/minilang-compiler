package llvm;

import java.util.Map;

public class AllocateOperation implements Operation {
  private final LLVMType type;

  public AllocateOperation(LLVMType type) {
    this.type = type;
  }

  public String toString() {
    return String.format("alloca %s", this.type.toString());
  }

  public LLVMType getType() {
    return this.type;
  }

  public void usingValue(Map<String, Boolean> used) {
    //
  }

  public void propagate(Map<String, Integer> constants) {
    //
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
