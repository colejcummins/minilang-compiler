package llvm;

import java.util.Map;

public class PrintInstruction implements Instruction {
  private Value value;
  private final Boolean newLine;

  public PrintInstruction(Value value, Boolean newLine) {
    this.value = value;
    this.newLine = newLine;
  }

  public String toString() {
    return String.format("  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* %s, i32 0, i32 0), i64 %s)%n", this.newLine ? "@.println" : "@.print" ,value.toString());
  }

  public Value getValue() {
    return this.value;
  }

  public void usingValue(Map<String, Boolean> used) {
    if (this.value instanceof RegisterValue) {
      used.put(this.value.toString(), true);
    }
  }

  public void propagate(Map<String, Integer> constants) {
    if (constants.containsKey(this.value.toString())) {
      this.value = new IntValue(constants.get(this.value.toString()));
    }
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
