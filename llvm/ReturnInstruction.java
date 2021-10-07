package llvm;

import java.util.Map;

public class ReturnInstruction implements Instruction {
  private final LLVMType type;
  private Value value;

  public ReturnInstruction(LLVMType type, Value value) {
    this.type = type;
    this.value = value;
  }

  public String toString() {
    return String.format("  ret %s %s\n", this.type.toString(), this.value.toString());
  }

  public LLVMType getType() {
    return this.type;
  }

  public Value getValue() {
    return this.value;
  }

  public void usingValue(Map<String, Boolean> used) {
    if (this.value instanceof RegisterValue) {
      used.put(this.value.toString(), true);
    }
  }

  public void propagate(Map<String, Integer> constants) {
    if (constants.containsKey(this.value.toString())) {
      this.value = new IntValue(constants.get(this.value.toString()));
    }
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
