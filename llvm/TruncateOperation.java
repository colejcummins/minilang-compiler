package llvm;

import java.util.Map;

public class TruncateOperation implements Operation {
  private Value value;

  public TruncateOperation(Value value) {
    this.value = value;
  }

  public String toString() {
    return String.format("trunc i64 %s to i1", value.toString());
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

  public int getResult() {
    if (this.value instanceof IntValue) {
      return this.value.getValue();
    }
    return -1;
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
