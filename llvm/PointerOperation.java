package llvm;

import java.util.Map;

public class PointerOperation implements Operation {
  private final LLVMStruct type;
  private final Value value;
  private final int field;

  public PointerOperation(LLVMStruct type, Value value, int field) {
    this.type = type;
    this.value = value;
    this.field = field;
  }

  public String toString() {
    return String.format(
      "getelementptr %s, %s %s, i1 0, i32 %d",
      this.type.toString().substring(0, this.type.toString().length() - 1),
      this.type.toString(),
      this.value.toString(),
      this.field
    );
  }

  public LLVMStruct getType() {
    return this.type;
  }

  public Value getValue() {
    return this.value;
  }

  public int getField() {
    return this.field;
  }

  public void usingValue(Map<String, Boolean> used) {
    if (this.value instanceof RegisterValue) {
      used.put(this.value.toString(), true);
    }
  }

  public void propagate(Map<String, Integer> constants) {
    //
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
