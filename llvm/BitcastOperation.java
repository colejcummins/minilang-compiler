package llvm;

import java.util.Map;

public class BitcastOperation implements Operation {
  private final LLVMType sourceType;
  private final Value sourceValue;
  private final LLVMType targetType;

  public BitcastOperation(LLVMType sourceType, Value sourceValue, LLVMType targetType) {
    this.sourceType = sourceType;
    this.sourceValue = sourceValue;
    this.targetType = targetType;
  }

  public Value getSourceValue() {
    return this.sourceValue;
  }

  public String toString() {
    return String.format("bitcast %s %s to %s", this.sourceType.toString(), this.sourceValue.toString(), this.targetType.toString());
  }

  public void usingValue(Map<String, Boolean> used) {
    if (this.sourceValue instanceof RegisterValue) {
      used.put(this.sourceValue.toString(), true);
    }
  }

  public void propagate(Map<String, Integer> constants) {
    //
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
