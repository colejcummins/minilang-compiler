package llvm;

import java.util.Map;

public class GlobalAssignmentInstruction implements Instruction {
  private final String target;
  private final Operation source;

  public GlobalAssignmentInstruction(String target, Operation source) {
    this.target = target;
    this.source = source;
  }

  public String toString() {
    return String.format("%s = %s%n", this.target, this.source.toString());
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
