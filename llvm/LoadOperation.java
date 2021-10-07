package llvm;

import java.util.Map;

public class LoadOperation implements Operation {
  private final LLVMType type;
  private final Value source;

  public LoadOperation(LLVMType type, Value source) {
    this.type = type;
    this.source = source;
  }

  public String toString() {
    return String.format("load %s, %s* %s", this.type.toString(), this.type.toString(), this.source.toString());
  }

  public LLVMType getType() {
    return this.type;
  }

  public Value getSource() {
    return this.source;
  }

  public void usingValue(Map<String, Boolean> used) {
    if (this.source instanceof RegisterValue) {
      used.put(this.source.toString(), true);
    }
  }

  public void propagate(Map<String, Integer> constants) {
    //
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
