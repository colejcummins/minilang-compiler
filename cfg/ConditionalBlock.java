package cfg;

import java.util.List;

import llvm.Instruction;

import ast.Expression;
import ast.BlockStatement;

public class ConditionalBlock extends BasicBlock {
  private BasicBlock thenBlock;
  private BasicBlock elseBlock;
  private Expression guard;
  private Boolean cycle;

  public ConditionalBlock(Expression guard, String label) {
    super(label);
    this.guard = guard;
  }

  public ConditionalBlock(Expression guard, String label, BlockStatement block) {
    super(label, block);
    this.guard = guard;
    this.cycle = false;
  }

  public ConditionalBlock(Expression guard, String label, BlockStatement block, Boolean cycle) {
    super(label, block);
    this.guard = guard;
    this.cycle = cycle;
  }

  public ConditionalBlock(BasicBlock thenBlock, BasicBlock elseBlock, Expression guard, String label, BlockStatement block) {
    super(label, block);
    this.thenBlock = thenBlock;
    this.elseBlock = elseBlock;
    this.guard = guard;
    this.cycle = false;
  }

  public ConditionalBlock(BasicBlock thenBlock, BasicBlock elseBlock, Expression guard, String label, List<Instruction> instructions) {
    super(label, instructions);
    this.thenBlock = thenBlock;
    this.elseBlock = elseBlock;
    this.guard = guard;
    this.cycle = false;
  }



  public String toString() {
    return super.toString() + " %CONDITIONAL BLOCK% ->\n %then block%\n" + ((!this.cycle) ? this.thenBlock.toString() : "") + (this.elseBlock != null ? "%else block%\n" + this.elseBlock.toString() : "") + "\n\n";
  }

  @Override
  public BasicBlock getThen() {
    return this.thenBlock;
  }

  @Override
  public BasicBlock getElse() {
    return this.elseBlock;
  }

  public Expression getGuard() {
    return this.guard;
  }

  public Boolean getCycle() {
    return this.cycle;
  }

  public void addThen(BasicBlock block) {
    this.thenBlock = block;
  }

  public void addElse(BasicBlock block) {
    this.elseBlock = block;
  }
}
