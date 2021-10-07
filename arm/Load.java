package arm;

public class Load implements ARMInstruction {
  private final Operand target;
  private final Operand source;

  public Load(Operand target, Operand source) {
    this.target = target;
    this.source = source;
  }

  public String toString() {
    return String.format("ldr %s, [%s]", this.target.toString(), this.source.toString());
  }
}
