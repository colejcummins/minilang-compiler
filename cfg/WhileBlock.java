package cfg;

import semantics.Visitor;

import ast.Expression;
import ast.Statement;

public class WhileBlock extends BasicBlock {
  private BasicBlock next;
  private Expression guard;

  public WhileBlock(Expression guard, String label, Statement block) {
    super(label, block);
    this.guard = guard;
  }

  public WhileBlock(Expression guard, BasicBlock next, String label, Statement block) {
    super(label, block);
    this.next = next;
    this.guard = guard;
  }

  public String toString() {
    return super.toString() + " %WHILE BLOCK% ->\n\n" + (this.next == null ? "" : this.next.toString());
  }

  public BasicBlock getContinue() {
    return this;
  }

  public Expression getGuard() {
    return this.guard;
  }

  @Override
  public BasicBlock getNext() {
    return this.next;
  }

  @Override
  public void addBlock(BasicBlock block) {
    this.next = block;
  }
}
