package llvm;

import java.util.Map;

public class AssignmentInstruction implements Instruction {
  private final Value target;
  private final Operation source;

  public AssignmentInstruction(Value target, Operation source) {
    this.target = target;
    this.source = source;
  }

  public String toString() {
    return String.format("  %s = %s\n", this.target, this.source.toString());
  }

  public Value getTarget() {
    return this.target;
  }

  public Operation source() {
    return this.source;
  }

  public void usingValue(Map<String, Boolean> used) {
    if (!used.containsKey(this.target.toString())) {
      used.put(this.target.toString(), false);
    }
    this.source.usingValue(used);
  }

  public void getResult(Map<String, Integer> constants) {
    int result = -1;

    if (this.source instanceof BinaryOperation) {
      result = ((BinaryOperation)this.source).getResult();

    } else if (this.source instanceof XorOperation) {
      result = ((XorOperation)this.source).getResult();

    } else if (this.source instanceof ComparisonOperation) {
      result = ((ComparisonOperation)this.source).getResult();

    } else if (this.source instanceof ZextOperation) {
      result = ((ZextOperation)this.source).getResult();

    } else if (this.source instanceof TruncateOperation) {
      result = ((TruncateOperation)this.source).getResult();

    }

    if (result != -1) {
      constants.put(this.target.toString(), result);
    }
  }

  public void propagate(Map<String, Integer> constants) {
    this.source.propagate(constants);
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
