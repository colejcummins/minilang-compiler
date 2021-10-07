package arm;

import java.util.List;

public class Pop implements ARMInstruction {
  public List<Register> registers;

  public Pop(List<Register> registers) {
    this.registers = registers;
  }

  public String toString() {
    String out = "pop {";
    for (int i = 0; i < this.registers.size(); i++) {
      out += this.registers.get(i).toString() + ((i < (this.registers.size() - 1)) ? ", " : "");
    }

    return out + "}";
  }
}
