package llvm;

import java.util.Map;

public class ReturnEmptyInstruction implements Instruction {
  public String toString() {
    return "  ret void\n";
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
