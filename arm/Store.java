package arm;

public class Store implements ARMInstruction {
  private final Operand target;
  private final Operand source;

  public Store(Operand target, Operand source) {
    this.target = target;
    this.source = source;
  }

  public String toString() {
    return String.format("str %s, [%s]", this.target.toString(), this.source.toString());
  }
}
