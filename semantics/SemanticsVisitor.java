package semantics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import ast.*;
import ast.Symbol.DeclarationLocation;
import ast.BinaryExpression.Operator;
import ast.UnaryExpression.UnaryOperator;

public class SemanticsVisitor extends Visitor {

  private Map<String, Symbol> symbolTable;
  private Map<String, StructType> structTable;
  private Map<String, Boolean> returnTable;
  private Map<String, FunctionType> functionTable;
  private int errors;
  private final String CURRENT = "__current__";
  private final String RETURN = "__return__";
  private final String LOCATION = "__location__";

  public SemanticsVisitor() {
    super();
    this.symbolTable = new HashMap<String, Symbol>();
    this.returnTable = new HashMap<String, Boolean>();
    this.functionTable = new HashMap<String, FunctionType>();
    this.structTable = new HashMap<String, StructType>();
  }

  public SemanticsVisitor(Map<String, Symbol> symbolTable, Map<String, FunctionType> functionTable, Map<String, StructType> structTable) {
    super();
    this.symbolTable = symbolTable;
    this.returnTable = new HashMap<String, Boolean>();
    this.functionTable = functionTable;
    this.structTable = structTable;
  }


  public void visit(Program exp) {
    return;
  }


  public void visit(Expression exp) {
    return;
  }


  public void visit(Lvalue l) {
    return;
  }

  public void visit(LvalueDot l) {
    this.visit(new DotExpression(l.getLineNum(), l.getLeft(), l.getId()));
  }

  public void visit(LvalueId l) {
    this.symbolTable.put(CURRENT, new Symbol(this.symbolTable.get(l.getId()).getType(), DeclarationLocation.CURRENT));
  }

  public void visit(Function exp) {
    this.symbolTable.put(LOCATION, new Symbol(new VoidType(), DeclarationLocation.PARAM));
    for (Declaration p : exp.getParams()) {
      p.accept(this);
    }

    this.symbolTable.put(LOCATION, new Symbol(new VoidType(), DeclarationLocation.LOCAL));
    for (Declaration l : exp.getLocals()) {
      l.accept(this);
    }
    if (exp.getRetType() instanceof StructType) {
      this.symbolTable.put(RETURN, new Symbol(this.structTable.get(((StructType)exp.getRetType()).getName()), DeclarationLocation.RETURN));
    } else {
      this.symbolTable.put(RETURN, new Symbol(exp.getRetType(), DeclarationLocation.RETURN));
    }
    this.returnTable.put(CURRENT, false);
    exp.getBody().accept(this);
    if (!this.returnTable.get(CURRENT) && !(this.symbolTable.get(RETURN).getType() instanceof VoidType)) {
      System.out.printf("ERROR: Missing return statement for function %s%n", exp.getName());
      this.errors++;
    }
  }


  public void visit(TypeDeclaration exp) {
    Map<String, Type> fields = new HashMap<String, Type>();
    for (Declaration d : exp.getFields()) {
      if (this.structTable.containsKey(exp.getName())) {
        System.out.printf("ERROR: Struct %s already exists, cannot redeclare%n", exp.getName());
        this.errors++;
      }
      fields.put(d.getName(), d.getType());
    }
    this.structTable.put(exp.getName(), new StructType(exp.getLineNum(), exp.getName(), fields));
  }


  public void visit(Declaration exp) {
    if (this.symbolTable.containsKey(exp.getName()) && (this.symbolTable.get(exp.getName()).getLoc() == this.symbolTable.get(LOCATION).getLoc())) {
      System.out.printf("ERROR: Identifier %s already exists, cannot redeclare, line: %d%n", exp.getName(), exp.getLineNum());
      this.errors++;
    }
    if (exp.getType() instanceof StructType) {
      this.symbolTable.put(exp.getName(), new Symbol(this.structTable.get(((StructType)exp.getType()).getName()), this.symbolTable.get(LOCATION).getLoc()));
    } else {
      this.symbolTable.put(exp.getName(), new Symbol(exp.getType(), this.symbolTable.get(LOCATION).getLoc()));
    }
  }


  public void visit(AssignmentStatement st) {
    st.getTarget().accept(this);
    Type target = this.symbolTable.get(CURRENT).getType();
    st.getSource().accept(this);
    Type source = this.symbolTable.get(CURRENT).getType();

    if ((target instanceof StructType) && !(((StructType)target).structEquals(source))) {
      System.out.printf("ERROR: Type mismatch exception, StructType or VoidType expected, line: %d%n", st.getLineNum());
      this.errors++;
      return;
    }
    if ((target instanceof StructType) && (source instanceof VoidType)) {
      return;
    }
    if (!target.getClass().equals(source.getClass())) {
      System.out.printf("ERROR: Type mismatch exception line: %d%n", st.getLineNum());
      this.errors++;
    }
  }


  public void visit(BlockStatement st) {
    for (Statement s : st.getStatements()) {
      s.accept(this);
    }
  }


  public void visit(ConditionalStatement st) {
    st.getGuard().accept(this);
    if (!(this.symbolTable.get(CURRENT).getType() instanceof BoolType)) {
      System.out.printf("ERROR: Type mismatch, BoolType expected: %d%n", st.getLineNum());
      this.errors++;
    }
    this.returnTable.put(CURRENT, false);
    st.getThenBlock().accept(this);
    Boolean thenB = this.returnTable.get(CURRENT);
    this.returnTable.put(CURRENT, false);
    st.getElseBlock().accept(this);
    Boolean elseB = this.returnTable.get(CURRENT);

    this.returnTable.put(CURRENT, false);
    if (thenB && elseB) {
      this.returnTable.put(CURRENT, true);
    }
  }


  public void visit(DeleteStatement st) {
    return;
  }


  public void visit(InvocationStatement st) {
    st.getExpression().accept(this);
  }


  public void visit(PrintStatement st) {
    st.getExpression().accept(this);
    if (!((this.symbolTable.get(CURRENT).getType()) instanceof IntType)) {
      System.out.printf("ERROR: Print Type mismatch, IntType expected: %d%n", st.getLineNum());
      this.errors++;
    }
  }


  public void visit(PrintLnStatement st) {
    st.getExpression().accept(this);
    if (!((this.symbolTable.get(CURRENT).getType()) instanceof IntType)) {
      System.out.printf("ERROR: PrintLn Type mismatch, IntType expected: %d%n", st.getLineNum());
      this.errors++;
    }
  }


  public void visit(ReturnStatement st) {
    st.getExpression().accept(this);
    if ((this.symbolTable.get(RETURN).getType() instanceof StructType) && (this.symbolTable.get(CURRENT).getType() instanceof VoidType)) {
      this.returnTable.put(CURRENT, true);
      return;
    }
    if (!(this.symbolTable.get(CURRENT).getType().getClass().equals(this.symbolTable.get(RETURN).getType().getClass()))) {
      System.out.printf("ERROR: Return Type mismatch, %s expected: %d%n", this.symbolTable.get(RETURN).getType().toString(), st.getLineNum());
      this.errors++;
    }
    this.returnTable.put(CURRENT, true);
  }


  public void visit(ReturnEmptyStatement st) {
    if (!((this.symbolTable.get(RETURN).getType()) instanceof VoidType)) {
      System.out.printf("ERROR: Return Type mismatch, VoidType expected: %d%n", st.getLineNum());
      this.errors++;
    }
    this.returnTable.put(CURRENT, true);
  }


  public void visit(WhileStatement st) {
    st.getGuard().accept(this);
    if (!(this.symbolTable.get(CURRENT).getType() instanceof BoolType)) {
      System.out.printf("ERROR: Type mismatch, BoolType expected: %d%n", st.getLineNum());
      this.errors++;
    }
    st.getBody().accept(this);
    this.returnTable.put(CURRENT, false);
  }


  public void visit(BinaryExpression exp) {
    String operandError = "ERROR: Operand type error, IntType expected, line: %d%n";
    exp.getLeft().accept(this);
    Type left = this.symbolTable.get(CURRENT).getType();
    exp.getRight().accept(this);
    Type right = this.symbolTable.get(CURRENT).getType();
    switch(exp.getOperator()) {
      case AND: case OR:
        if (!((left instanceof BoolType) && (right instanceof BoolType))) {
          System.out.printf("ERROR: Operand type error, BoolType expected, line: %d%n", exp.getLineNum());
          this.errors++;
        }
        this.symbolTable.put(CURRENT, new Symbol(new BoolType(), DeclarationLocation.CURRENT));
        break;
      case LT: case GT: case LE: case GE:
        if (!((left instanceof IntType) && (right instanceof IntType))) {
          System.out.printf(operandError, exp.getLineNum());
          this.errors++;
        }
        this.symbolTable.put(CURRENT, new Symbol(new BoolType(), DeclarationLocation.CURRENT));
        break;
      case EQ: case NE:
        if ((left instanceof IntType) && (!(right instanceof IntType))) {
          System.out.printf(operandError, exp.getLineNum());
          this.errors++;
        } else if (((left instanceof BoolType) && (!(right instanceof BoolType)))) {
          System.out.printf("ERROR: Operand type error, BoolType expected, line: %d%n", exp.getLineNum());
          this.errors++;
        } else if ((left instanceof StructType) && (!((right instanceof StructType) || (right instanceof VoidType)))) {
          System.out.printf("ERROR: Operand type error, StructType or VoidType expected, line: %d%n", exp.getLineNum());
          this.errors++;
        } else if ((left instanceof VoidType) && (!((right instanceof StructType) || (right instanceof VoidType)))) {
          System.out.printf("ERROR: Operand type error, StructType or VoidType expected, line: %d%n", exp.getLineNum());
          this.errors++;
        }
        this.symbolTable.put(CURRENT, new Symbol(new BoolType(), DeclarationLocation.CURRENT));
        break;
      default:
        if (!((left instanceof IntType) && (right instanceof IntType))) {
          System.out.printf(operandError, exp.getLineNum());
          this.errors++;
        }
        this.symbolTable.put(CURRENT, new Symbol(new IntType(), DeclarationLocation.CURRENT));
    }
  }


  public void visit(DotExpression exp) {
    exp.getLeft().accept(this);
    Type left = this.symbolTable.get(CURRENT).getType();
    if (left instanceof VoidType) {
      if (!this.symbolTable.containsKey(exp.getId())) {
        System.out.printf("ERROR: Identifier not found, line: %d%n", exp.getLineNum());
        this.errors++;
        System.exit(0);
      }
      this.symbolTable.put(CURRENT, new Symbol(this.symbolTable.get(exp.getId()).getType(), DeclarationLocation.CURRENT));
      return;
    }
    if (!(left instanceof StructType)) {
      System.out.printf("ERROR: StructType expected, line: %d%n", exp.getLineNum());
      this.errors++;
      System.exit(0);
    }
    if (!((StructType)left).getFields().containsKey(exp.getId())) {
      System.out.printf("ERROR: StructType %s does not contain field %s, line: %d%n", ((StructType)left).getName(), exp.getId(), exp.getLineNum());
      this.errors++;
      System.exit(0);
    }
    if (((StructType)left).getFields().get(exp.getId()) instanceof StructType) {
      this.symbolTable.put(CURRENT, new Symbol(this.structTable.get(((StructType)((StructType)left).getFields().get(exp.getId())).getName()), DeclarationLocation.CURRENT));
    } else {
      this.symbolTable.put(CURRENT, new Symbol(((StructType)left).getFields().get(exp.getId()), DeclarationLocation.CURRENT));
    }
  }


  public void visit(FalseExpression exp) {
    this.symbolTable.put(CURRENT, new Symbol(new BoolType(), DeclarationLocation.CURRENT));
  }


  public void visit(IdentifierExpression exp) {
    if (!(this.symbolTable.containsKey(exp.getId()))) {
      System.out.printf("ERROR: Identifier not found, line: %d%n", exp.getLineNum());
      this.errors++;
      System.exit(0);
    }
    Type id = this.symbolTable.get(exp.getId()).getType();
    if (id instanceof StructType) {
      this.symbolTable.put(CURRENT, new Symbol(this.structTable.get(((StructType)id).getName()), DeclarationLocation.CURRENT));
    } else {
      this.symbolTable.put(CURRENT, new Symbol(this.symbolTable.get(exp.getId()).getType(), DeclarationLocation.CURRENT));
    }
  }


  public void visit(IntegerExpression exp) {
    this.symbolTable.put(CURRENT, new Symbol(new IntType(), DeclarationLocation.CURRENT));
  }


  public void visit(InvocationExpression exp) {
    if (!(this.functionTable.containsKey(exp.getName()))) {
      System.out.printf("ERROR: Function Identifier not found, line: %d%n", exp.getLineNum());
      this.errors++;
      System.exit(0);
    }

    FunctionType func = this.functionTable.get(exp.getName());
    if (func.getParams().size() != exp.getArguments().size()) {
      System.out.printf("ERROR: Function argument count mismatch, %d arguments expected, line: %d%n", func.getParams().size(), exp.getLineNum());
      this.errors++;
      System.exit(0);
    }

    List<Type> types = new ArrayList<Type>();
    for (Expression e : exp.getArguments()) {
      e.accept(this);
      types.add(this.symbolTable.get(CURRENT).getType());
    }

    for (int i = 0; i < func.getParams().size(); i++) {
      if (func.getParams().get(i) instanceof StructType) {
        if ((types.get(i) instanceof VoidType) || (types.get(i) instanceof StructType)) {
          continue;
        }
        if (!((StructType)func.getParams().get(i)).structEquals(types.get(i))) {
          System.out.printf("ERROR: Function argument type mismatch, %s expected, line: %d%n", func.getParams().get(i).toString(), exp.getLineNum());
          this.errors++;
        }
        continue;
      }
      if (!(types.get(i).getClass().equals(func.getParams().get(i).getClass()))) {
        System.out.printf("ERROR: Function argument type mismatch, %s expected, line: %d%n", func.getParams().get(i).toString(), exp.getLineNum());
        this.errors++;
      }
    }
    this.symbolTable.put(CURRENT, new Symbol(func.getReturnType(), DeclarationLocation.CURRENT));
  }


  public void visit(NewExpression exp) {
    if (!(this.structTable.containsKey(exp.getId()))) {
      System.out.printf("ERROR: StructType %s not found, line: %d%n", exp.getId(), exp.getLineNum());
      this.errors++;
    }
    this.symbolTable.put(CURRENT, new Symbol(this.structTable.get(exp.getId()), DeclarationLocation.CURRENT));
  }


  public void visit(NullExpression exp) {
    this.symbolTable.put(CURRENT, new Symbol(new VoidType(), DeclarationLocation.CURRENT));
  }


  public void visit(ReadExpression exp) {
    return;
  }


  public void visit(TrueExpression exp) {
    this.symbolTable.put(CURRENT, new Symbol(new BoolType(), DeclarationLocation.CURRENT));
  }


  public void visit(UnaryExpression exp) {
    exp.getOperand().accept(this);
    Type type = this.symbolTable.get(CURRENT).getType();
    if (exp.getOperator() == UnaryOperator.NOT) {
      if (!(type instanceof BoolType)) {
        System.out.printf("ERROR: Operator type mismatch, BoolType expected, line: %d%n", exp.getLineNum());
        this.errors++;
      }
    } else {
      if (!(type instanceof IntType)) {
        System.out.printf("ERROR: Operator type mismatch, IntType expected, line: %d%n", exp.getLineNum());
        this.errors++;
      }
    }
  }


  public Map<String, Symbol> getSymbols() {
    return this.symbolTable;
  }

  public Map<String, FunctionType> getFunctions() {
    return this.functionTable;
  }

  public Map<String, StructType> getStructs() {
    return this.structTable;
  }

  public int getErrors() {
    return this.errors;
  }

  public void addFunction(String name, FunctionType fun) {
    this.functionTable.put(name, fun);
  }

  public void addError(int num) {
    this.errors += num;
  }
}
