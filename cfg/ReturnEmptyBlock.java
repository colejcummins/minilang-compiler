package cfg;

import java.util.List;

import llvm.Instruction;

public class ReturnEmptyBlock extends BasicBlock {
  public ReturnEmptyBlock(String label) {
    super(label);
  }

  public ReturnEmptyBlock(String label, List<Instruction> instructions) {
    super(label, instructions);
  }

  public String toString() {
    return super.toString() + " %RETURN EMPTY BLOCK% #\n\n";
  }
}
