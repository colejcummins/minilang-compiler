package llvm;

import java.util.List;

public class LLVMProgram {
  private final List<Instruction> types;
  private final List<Instruction> globals;
  private final List<LLVMFunction> funcs;

  public final String TARGET = "target triple=\"x86_64\"\n";
  public final String FUNC_DECLS = "declare i8* @malloc(i64)\ndeclare void @free(i8*)\ndeclare i64 @printf(i8*, ...)\ndeclare i64 @scanf(i8*, ...)\n";
  public final String FUNC_CONSTANTS = "@.println = private unnamed_addr constant [5 x i8] c\"%ld\\0A\\00\", align 1\n" +
  "@.print = private unnamed_addr constant [5 x i8] c\"%ld \\00\", align 1\n" +
  "@.read = private unnamed_addr constant [4 x i8] c\"%ld\\00\", align 1\n@.read_scratch = common global i64 0, align 4\n";

  public LLVMProgram(List<Instruction> types, List<Instruction> globals, List<LLVMFunction> funcs) {
    this.types = types;
    this.globals = globals;
    this.funcs = funcs;
  }

  public String toString() {
    String out = TARGET;
    for (Instruction type : this.types) {
      out += type.toString();
    }
    out += "\n";
    for (Instruction global : this.globals) {
      out += global.toString();
    }
    out += "\n";
    for (LLVMFunction function : this.funcs) {
      out += function.toString() + "\n";
    }
    return out + FUNC_DECLS + FUNC_CONSTANTS;
  }

  public List<Instruction> getTypes() {
    return this.types;
  }

  public List<Instruction> getGlobals() {
    return this.globals;
  }

  public List<LLVMFunction> getFuncs() {
    return this.funcs;
  }
}
