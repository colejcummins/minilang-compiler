package llvm;

import java.util.List;

public class LLVMStruct implements LLVMType {
  private final String name;
  private final List<StructHelper> fields;

  public LLVMStruct(String name, List<StructHelper> fields) {
    this.name = name;
    this.fields = fields;
  }

  public String toString() {
    return String.format("%%struct.%s*", this.name);
  }

  public String getName() {
    return this.name;
  }

  public List<StructHelper> getFields() {
    return this.fields;
  }

  public int getFieldNum(String name) {
    for (int i = 0; i < this.fields.size(); i++) {
      if (this.fields.get(i).getStr().equals(name)) {
        return i;
      }
    }
    return 0;
  }

  public StructHelper getFieldType(String name) {
    for (StructHelper h : this.fields) {
      if (h.getStr().equals(name)) {
        return h;
      }
    }
    return new StructHelper("void", new LLVMVoid());
  }
}
