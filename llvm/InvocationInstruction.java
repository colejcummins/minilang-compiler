package llvm;

import java.util.Map;

public class InvocationInstruction implements Instruction {
  private final InvocationOperation invocation;

  public InvocationInstruction(InvocationOperation invocation) {
    this.invocation = invocation;
  }

  public String toString() {
    return "  " + this.invocation.toString() + "\n";
  }

  public InvocationOperation getInvocation() {
    return this.invocation;
  }

  public void usingValue(Map<String, Boolean> used) {
    this.invocation.usingValue(used);
  }

  public void propagate(Map<String, Integer> constants) {
    this.invocation.propagate(constants);
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
