package llvm;

import java.util.Map;

public class XorOperation implements Operation {
  private Value left;
  private Value right;

  public XorOperation(Value left, Value right) {
    this.left = left;
    this.right = right;
  }

  public String toString() {
    return String.format("xor i64 %s, %s", this.left.toString(), this.right.toString());
  }

  public void usingValue(Map<String, Boolean> used) {
    if (this.left instanceof RegisterValue) {
      used.put(this.left.toString(), true);
    }
    if (this.right instanceof RegisterValue) {
      used.put(this.right.toString(), true);
    }
  }

  public int getResult() {
    if ((this.left instanceof IntValue) && (this.right instanceof IntValue)) {
      return ((this.left.getValue() == 1) ^ (this.right.getValue() == 1)) ? 1 : 0;
    }
    return -1;
  }

  public void propagate(Map<String, Integer> constants) {
    if (constants.containsKey(this.left.toString())) {
      this.left = new IntValue(constants.get(this.left.toString()));
    }
    if (constants.containsKey(this.right.toString())) {
      this.right = new IntValue(constants.get(this.right.toString()));
    }
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
