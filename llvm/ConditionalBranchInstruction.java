package llvm;

import java.util.Map;

public class ConditionalBranchInstruction implements Instruction {
  private Value compValue;
  private final String trueLabel;
  private final String falseLabel;

  public ConditionalBranchInstruction(Value compValue, String trueLabel, String falseLabel) {
    this.compValue = compValue;
    this.trueLabel = trueLabel;
    this.falseLabel = falseLabel;
  }

  public Value getCompValue() {
    return this.compValue;
  }

  public String getTrueLabel() {
    return this.trueLabel;
  }

  public String getFalseLabel() {
    return this.falseLabel;
  }

  public String toString() {
    return String.format("  br i1 %s, label %%%s, label %%%s\n", this.compValue, this.trueLabel, this.falseLabel);
  }

  public void usingValue(Map<String, Boolean> used) {
    if (this.compValue instanceof RegisterValue) {
      used.put(this.compValue.toString(), true);
    }
  }

  public void propagate(Map<String, Integer> constants) {
    if (constants.containsKey(this.compValue.toString())) {
      this.compValue = new IntValue(constants.get(this.compValue.toString()));
    }
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
