package llvm;

import java.util.Map;

import java.util.ListIterator;
import java.util.List;

public class LLVMBlock {
  private final String label;
  private final List<Instruction> instructions;

  public LLVMBlock(String label, List<Instruction> instructions) {
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

  public List<Instruction> getInstructions() {
    return this.instructions;
  }

  public void usingValue(Map<String, Boolean> used) {
    for (Instruction i : this.instructions) {
      i.usingValue(used);
    }
  }

  public void clearUnused(Map<String, Boolean> used) {
    ListIterator<Instruction> itr = this.instructions.listIterator();
    while(itr.hasNext()) {
      Instruction inst = itr.next();
      if ((inst instanceof PhiInstruction) && (!used.get(((RegisterValue)((PhiInstruction)inst).getTarget()).toString()))) {
        itr.remove();
      }
      if ((inst instanceof AssignmentInstruction) && (!used.get(((RegisterValue)((AssignmentInstruction)inst).getTarget()).toString()))) {
        itr.remove();
      }
    }
  }

  public void setConstants(Map<String, Integer> constants) {
    for (Instruction inst : this.instructions) {
      if (inst instanceof AssignmentInstruction) {
        ((AssignmentInstruction)inst).getResult(constants);
      } else if (inst instanceof PhiInstruction) {
        ((PhiInstruction)inst).getResult(constants);
      }
    }
  }

  public void propagate(Map<String, Integer> constants) {
    for (Instruction inst : this.instructions) {
      inst.propagate(constants);
    }
  }

  public void correctPhis(Map<String, String> newRef) {
    for (Instruction inst : this.instructions) {
      if (inst instanceof PhiInstruction) {
        ((PhiInstruction)inst).correctPhis(newRef);
      }
    }
  }
}
