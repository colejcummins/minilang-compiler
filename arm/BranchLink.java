package arm;

public class BranchLink implements ARMInstruction {
  private final String name;

  public BranchLink(String name) {
    this.name = name;
  }

  public String toString() {
    return "bl " + name;
  }
}
