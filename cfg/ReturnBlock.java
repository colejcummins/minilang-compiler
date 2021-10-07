package cfg;

import java.util.List;

import llvm.Instruction;

import ast.Expression;

public class ReturnBlock extends BasicBlock {
  private Expression expression;

  public ReturnBlock(String label, Expression expr) {
    super(label);
    this.expression = expr;
  }

  public ReturnBlock(String label, Expression expression, List<Instruction> instructions) {
    super(label, instructions);
    this.expression = expression;
  }

  public String toString() {
    return super.toString() + " %RETURN BLOCK% # \n\n";
  }

  public Expression getExpression() {
    return this.expression;
  }
}
