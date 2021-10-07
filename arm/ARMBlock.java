package arm;

import java.util.ArrayList;

public class ARMBlock {
  private final String label;
  private final ArrayList<ARMInstruction> instructions;

  public ARMBlock(String label, ArrayList<ARMInstruction> instructions) {
    this.label = label;
    this.instructions = instructions;
  }

  public String toString() {
    String out = String.format("%s:%n", this.label);
    for (ARMInstruction inst : this.instructions) {
      out += String.format("  %s%n", inst.toString());
    }
    return out;
  }

  public ArrayList<ARMInstruction> getInstructions() {
    return this.instructions;
  }

  public String getLabel() {
    return this.label;
  }
}
