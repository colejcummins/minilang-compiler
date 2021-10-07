package llvm;

import java.util.Map;

public class BranchInstruction implements Instruction {
  private final String label;

  public BranchInstruction(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }

  public String toString() {
    return String.format("  br label %%%s%n", this.label);
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
