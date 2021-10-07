package llvm;

import java.util.Map;

import java.util.List;

public class LLVMFunction {
  private final String name;
  private final LLVMType retType;
  private final List<Value> params;
  private final List<LLVMBlock> blocks;

  public LLVMFunction(String name, LLVMType retType, List<Value> params, List<LLVMBlock> blocks) {
    this.name = name;
    this.retType = retType;
    this.params = params;
    this.blocks = blocks;
  }

  public String toString() {
    String out = String.format("define %s @%s(", this.retType.toString(), this.name);
    for (int i = 0; i < params.size(); i++) {
      out += params.get(i).toTypeString() + ((i < params.size() - 1) ? ", " : "");
    }
    out += ")\n{\n";
    for (LLVMBlock block : this.blocks) {
      out += block.toString();
    }
    return out + "}\n\n";
  }

  public String getName() {
    return this.name;
  }

  public LLVMType getRetType() {
    return this.retType;
  }

  public List<Value> getParams() {
    return this.params;
  }

  public List<LLVMBlock> getBlocks() {
    return this.blocks;
  }

  public void usingValue(Map<String, Boolean> used) {
    for (LLVMBlock block : this.blocks) {
      block.usingValue(used);
    }
  }

  public void clearUnused(Map<String, Boolean> used) {
    for (LLVMBlock block : this.blocks) {
      block.clearUnused(used);
    }
  }

  public void setConstants(Map<String, Integer> constants) {
    for (LLVMBlock block : this.blocks) {
      block.setConstants(constants);
    }
  }

  public void propagate(Map<String, Integer> constants) {
    for (LLVMBlock block : this.blocks) {
      block.propagate(constants);
    }
  }

  public void correctPhis(Map<String, String> newRefs) {
    for (LLVMBlock block : this.blocks) {
      block.correctPhis(newRefs);
    }
  }
}
