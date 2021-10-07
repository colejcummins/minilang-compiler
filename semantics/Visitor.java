package semantics;

import ast.*;

public abstract class Visitor {
  public Visitor() {}

  public abstract void visit(Program obj);
  public abstract void visit(TypeDeclaration obj);
  public abstract void visit(Declaration obj);
  public abstract void visit(Function obj);

  public abstract void visit(BinaryExpression exp);
  public abstract void visit(DotExpression exp);
  public abstract void visit(FalseExpression exp);
  public abstract void visit(IdentifierExpression exp);
  public abstract void visit(IntegerExpression exp);
  public abstract void visit(InvocationExpression exp);
  public abstract void visit(NewExpression exp);
  public abstract void visit(NullExpression exp);
  public abstract void visit(ReadExpression exp);
  public abstract void visit(TrueExpression exp);
  public abstract void visit(UnaryExpression exp);

  public abstract void visit(AssignmentStatement st);
  public abstract void visit(BlockStatement st);
  public abstract void visit(ConditionalStatement st);
  public abstract void visit(DeleteStatement st);
  public abstract void visit(InvocationStatement st);
  public abstract void visit(PrintStatement st);
  public abstract void visit(PrintLnStatement st);
  public abstract void visit(ReturnStatement st);
  public abstract void visit(ReturnEmptyStatement st);
  public abstract void visit(WhileStatement st);

  public abstract void visit(LvalueId obj);
  public abstract void visit(LvalueDot obj);

}
