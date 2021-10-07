package arm;

public class Move implements ARMInstruction {
  private final Operand target;
  private final Operand source;

  public Move(Operand target, Operand source) {
    this.target = target;
    this.source = source;
  }

  public String toString() {
    return String.format("mov %s, %s", this.target.toString(), this.source.toString());
  }
}
