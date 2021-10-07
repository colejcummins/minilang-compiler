package llvm;

import java.util.Map;

import java.util.List;

public class TypeOperation implements Operation {
  private final List<StructHelper> fields;

  public TypeOperation(List<StructHelper> fields) {
    this.fields = fields;
  }

  public String toString() {
    String out = "type {";
    for (int i = 0; i < this.fields.size(); i++) {
      out += this.fields.get(i).getType().toString() + ((i < this.fields.size() - 1) ? ", " : "");
    }
    return out + "}";
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
