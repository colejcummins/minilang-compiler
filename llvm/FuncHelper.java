package llvm;

import java.util.List;

public class FuncHelper {
  private final String name;
  private final LLVMType retType;
  private final List<LLVMType> paramTypes;

  public FuncHelper(String name, LLVMType retType, List<LLVMType> paramTypes) {
    this.name = name;
    this.retType = retType;
    this.paramTypes = paramTypes;
  }

  public String getName() {
    return this.name;
  }

  public LLVMType getRetType() {
    return this.retType;
  }

  public List<LLVMType> getParamTypes() {
    return this.paramTypes;
  }
}
