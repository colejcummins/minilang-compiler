package cfg;

import ast.BlockStatement;

public class LineBlock extends BasicBlock {
  private BasicBlock next;
  private int parents;

  public LineBlock(String label) {
    super(label);
  }

  public LineBlock(String label, BlockStatement block) {
    super(label, block);
    this.parents = 0;
  }

  public LineBlock(BasicBlock next, String label, BlockStatement block) {
    super(label, block);
    this.next = next;
    this.parents = 0;
  }

  public String toString() {
    return super.toString() + " %LINE BLOCK% ->\n\n" + ((this.next == null) ? "" : this.next.toString());
  }

  @Override
  public BasicBlock getNext() {
    return this.next;
  }

  @Override
  public void addBlock(BasicBlock block) {
    this.next = block;
  }

  public void addParent() {
    this.parents++;
  }
}
