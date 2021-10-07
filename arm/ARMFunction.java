package arm;

import java.util.ArrayList;

public class ARMFunction {
  private final String name;
  private final ArrayList<ARMBlock> blocks;

  public ARMFunction(String name, ArrayList<ARMBlock> blocks) {
    this.name = name;
    this.blocks = blocks;
  }

  public String toString() {
    String out = String.format("  .align 2%n  .global %s%n%s:%n", this.name, this.name);
    for (ARMBlock block : this.blocks) {
      out += block.toString();
    }
    return out + String.format("  .size %s, .-%s%n", this.name, this.name);
  }

  public ArrayList<ARMBlock> getBlocks() {
    return this.blocks;
  }
}
