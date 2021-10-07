package llvm;

import java.util.Map;

import java.util.List;

public class PhiInstruction implements Instruction {
  private Value target;
  private List<Phi> phis;
  private final LLVMType type;

  public PhiInstruction(Value target, LLVMType type, List<Phi> phis) {
    this.target = target;
    this.type = type;
    this.phis = phis;
  }

  public String toString() {
    String out = String.format("  %s = phi %s ", this.target.toString(), this.type.toString());
    for (int i = 0; i < this.phis.size(); i++) {
      out += this.phis.get(i).toString() + ((i < (this.phis.size() - 1)) ? ", " : " ");
    }
    return out + "\n";
  }

  public Value getTarget() {
    return this.target;
  }

  public LLVMType getType() {
    return this.type;
  }

  public List<Phi> getPhis() {
    return this.phis;
  }

  public void addPhi(Phi p) {
    this.phis.add(p);
  }

  public void usingValue(Map<String, Boolean> used) {
    if (!used.containsKey(this.target.toString())) {
      used.put(this.target.toString(), false);
    }
    for (Phi phi : this.phis) {
      if (phi.getValue() instanceof RegisterValue) {
        used.put(phi.getValue().toString(), true);
      }
    }
  }

  public void getResult(Map<String, Integer> constants) {
    int prop = this.canPropagate();
    if (prop == -1) {
      return;
    }
    constants.put(target.toString(), prop);
  }

  private int canPropagate() {
    int current = -1;
    if (this.phis.size() > 0) {
      current = this.phis.get(0).getValue().getValue();
    }

    boolean canPropagate = true;
    for (Phi phi : this.phis) {
      if (!(phi.getValue() instanceof IntValue) || (phi.getValue().getValue() != current)) {
        canPropagate = false;
      }
    }

    if (canPropagate) {
      return current;
    }
    return -1;
  }

  public void propagate(Map<String, Integer> constants) {
    for (Phi phi : this.phis) {
      if (constants.containsKey(phi.getValue().toString())) {
        phi.setValue(new IntValue(constants.get(phi.getValue().toString())));
      }
    }
  }

  public void correctPhis(Map<String, String> newRefs) {
    for (Phi phi : this.phis) {
      if (newRefs.containsKey(phi.getBlockLabel())) {
        phi.setBlockLabel(newRefs.get(phi.getBlockLabel()));
      }
    }
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
