package llvm;

import java.util.Map;

public class StoreInstruction implements Instruction {
  private Value source;
  private final Value target;
  private final LLVMType type;

  public StoreInstruction(Value source, Value target, LLVMType type) {
    this.source = source;
    this.target = target;
    this.type = type;
  }

  public String toString() {
    return String.format("  store %s %s, %s* %s\n",
      this.type.toString(),
      this.source.toString(),
      this.type.toString(),
      this.target.toString()
    );
  }

  public Value getSource() {
    return this.source;
  }

  public Value getTarget() {
    return this.target;
  }

  public LLVMType getType() {
    return this.type;
  }

  public void usingValue(Map<String, Boolean> used) {
    if (this.source instanceof RegisterValue) {
      used.put(this.source.toString(), true);
    }
    if (this.target instanceof RegisterValue) {
      used.put(this.target.toString(), true);
    }
  }

  public void propagate(Map<String, Integer> constants) {
    if (constants.containsKey(this.source.toString())) {
      this.source = new IntValue(constants.get(this.source.toString()));
    }
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
