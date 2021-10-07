package llvm;

import java.util.Map;

import java.util.List;

public class InvocationOperation implements Operation {
  private final String name;
  private final LLVMType type;
  private List<Value> params;
  private List<LLVMType> paramTypes;

  public InvocationOperation(String name, LLVMType type, List<Value> params, List<LLVMType> paramTypes) {
    this.name = name;
    this.type = type;
    this.params = params;
    this.paramTypes = paramTypes;
  }

  public String toString() {
    String out = String.format("call %s @%s(", this.type.toString(), this.name);
    for (int i = 0; i < this.params.size(); i++) {
      out += this.paramTypes.get(i).toString() + " " + this.params.get(i).toString() + ((i < this.params.size() - 1) ? ", " : "");
    }
    return out + ")";
  }

  public String getName() {
    return this.name;
  }

  public LLVMType getType() {
    return this.type;
  }

  public List<Value> getParams() {
    return this.params;
  }

  public void usingValue(Map<String, Boolean> used) {
    for (Value v : this.params) {
      if (v instanceof RegisterValue) {
        used.put(v.toString(), true);
      }
    }
  }

  public void propagate(Map<String, Integer> constants) {
    for (int i = 0; i < this.params.size(); i++) {
      if (constants.containsKey(this.params.get(i).toString())) {
        this.params.set(i, new IntValue(constants.get(this.params.get(i).toString())));
      }
    }
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
