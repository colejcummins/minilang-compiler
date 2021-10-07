package cfg;

import java.util.ArrayList;
import java.util.List;

import ast.*;
import semantics.Visitor;

public class ASTtoCFGVisitor extends Visitor {

  private BasicBlock cfg;
  private BasicBlock current;
  private Conditional conditional;
  private ArrayList<Statement> codeBlock;
  private int count;

  public ASTtoCFGVisitor() {
    this.conditional = Conditional.NONE;
    this.count = 0;
    this.codeBlock = new ArrayList<Statement>();
    this.cfg = new LineBlock(this.getLabel(), null);
    this.current = this.cfg;
  }

  public ASTtoCFGVisitor(int count) {
    this.conditional = Conditional.NONE;
    this.count = count;
    this.codeBlock = new ArrayList<Statement>();
    this.cfg = new LineBlock(this.getLabel(), null);
    this.current = this.cfg;
  }

  public void visit(Program obj) {
    //
  }

  public void visit(TypeDeclaration obj) {
    //
  }

  public void visit(Declaration obj) {
    //
  }

  public void visit(Function obj) {
    obj.getBody().accept(this);
    if ((this.current instanceof LineBlock) && (this.current.getNext() == null)) {
      this.safeAddBlock(new ReturnEmptyBlock(this.getLabel()));
    }
  }

  public void visit(BinaryExpression exp) {
    //
  }

  public void visit(DotExpression exp) {
    //
  }

  public void visit(FalseExpression exp) {
    //
  }

  public void visit(IdentifierExpression exp) {
    //
  }

  public void visit(IntegerExpression exp) {
    //
  }

  public void visit(InvocationExpression exp) {
    //
  }

  public void visit(NewExpression exp) {
    //
  }

  public void visit(NullExpression exp) {
    //
  }

  public void visit(ReadExpression exp) {
    //
  }

  public void visit(TrueExpression exp) {
    //
  }

  public void visit(UnaryExpression exp) {
    //
  }

  public void visit(AssignmentStatement st) {
    this.codeBlock.add(st);
  }

  public void visit(BlockStatement st) {
    for (Statement s : st.getStatements()) {
      s.accept(this);
    }
    if (this.codeBlock.size() > 0) {
      this.safeAddBlock(new LineBlock(this.getLabel(), this.getCodeBlock()));
    }
  }

  public void visit(ConditionalStatement st) {
    this.safeAddBlock(new ConditionalBlock(st.getGuard(), this.getLabel(), this.getCodeBlock()));

    LineBlock end = new LineBlock(this.getLabel(), null);

    BasicBlock temp = this.current;

    this.conditional = Conditional.THEN;
    st.getThenBlock().accept(this);
    this.conditional = Conditional.NONE;
    if (!(this.current instanceof ReturnBlock) && !(this.current instanceof ReturnEmptyBlock)) {
      end.addParent();
    }
    this.safeAddBlock(end);

    this.current = temp;

    this.conditional = Conditional.ELSE;
    st.getElseBlock().accept(this);
    this.conditional = Conditional.NONE;
    if (!(this.current instanceof ReturnBlock) && !(this.current instanceof ReturnEmptyBlock)) {
      end.addParent();
    }
    this.safeAddBlock(end);

  }

  public void visit(DeleteStatement st) {
    this.codeBlock.add(st);
  }

  public void visit(InvocationStatement st) {
    this.codeBlock.add(st);
  }

  public void visit(PrintStatement st) {
    this.codeBlock.add(st);
  }

  public void visit(PrintLnStatement st) {
    this.codeBlock.add(st);
  }

  public void visit(ReturnStatement st) {
    if (this.codeBlock.size() > 0) {
      this.safeAddBlock(new LineBlock(this.getLabel(), this.getCodeBlock()));
      this.safeAddBlock(new ReturnBlock(this.getLabel(), st.getExpression()));
    } else {
      this.safeAddBlock(new ReturnBlock(this.getLabel(), st.getExpression()));
    }
  }

  public void visit(ReturnEmptyStatement st) {
    if (this.codeBlock.size() > 0) {
      this.safeAddBlock(new LineBlock(this.getLabel(), this.getCodeBlock()));
      this.safeAddBlock(new ReturnEmptyBlock(this.getLabel()));
    } else {
      this.safeAddBlock(new ReturnEmptyBlock(this.getLabel()));
    }
  }

  public void visit(WhileStatement st) {
    ConditionalBlock block = new ConditionalBlock(st.getGuard(), this.getLabel(), this.getCodeBlock());
    LineBlock end = new LineBlock(this.getLabel(), this.getCodeBlock());
    this.safeAddBlock(block);

    this.conditional = Conditional.THEN;
    st.getBody().accept(this);
    this.conditional = Conditional.NONE;

    ConditionalBlock cond = new ConditionalBlock(st.getGuard(), this.getLabel(), this.getCodeBlock(), true);
    this.safeAddBlock(cond);
    this.conditional = Conditional.THEN;
    this.safeAddBlock(block.getThen());

    this.conditional = Conditional.ELSE;
    this.current = block;
    this.safeAddBlock(end);
    this.current = cond;
    this.safeAddBlock(end);
    this.conditional = Conditional.NONE;
  }

  public void visit(LvalueId obj) {
    //
  }


  public void visit(LvalueDot obj) {
    //
  }

  private void safeAddBlock(BasicBlock block) {
    if (this.current instanceof ConditionalBlock) {
      if (this.conditional == Conditional.THEN) {
        this.current.addThen(block);
        block.addPredecessor(this.current);
        this.current = this.current.getThen();
      } else {
        this.current.addElse(block);
        block.addPredecessor(this.current);
        this.current = this.current.getElse();
      }
    } else if (this.current instanceof LineBlock) {
      this.current.addBlock(block);
      block.addPredecessor(this.current);
      this.current = this.current.getNext();
    }
  }

  private String getLabel() {
    this.count += 1;
    return "LU" + Integer.toString(this.count);
  }

  private BlockStatement getCodeBlock() {
    BlockStatement block = new BlockStatement(0, (List<Statement>)this.codeBlock.clone());
    this.codeBlock.clear();
    return block;
  }

  public int getCount() {
    return this.count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public BasicBlock getCurrent() {
    return this.current;
  }

  public BasicBlock getCFG() {
    return this.cfg;
  }

  private static enum Conditional {
    THEN, ELSE, NONE;
  }
}
