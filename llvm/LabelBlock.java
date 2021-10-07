package llvm;

import java.util.List;

public class LabelBlock {
  private final String label;
  private final List<Instruction> instructions;

  public LabelBlock(String label, List<Instruction> instructions) {
    this.label = label;
    this.instructions = instructions;
  }

  public String toString() {
    String out = this.label + ":\n";
    for (Instruction inst : this.instructions) {
      out += inst.toString();
    }
    return out;
  }

  public String getLabel() {
    return this.label;
  }
}
