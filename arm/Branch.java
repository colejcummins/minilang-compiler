package arm;

public class Branch implements ARMInstruction {
  private final String label;

  public Branch(String label) {
    this.label = label;
  }

  public String toString() {
    return String.format("b %s", this.label);
  }
}
