package llvm;

public abstract class LLVMVisitor {
  /*
    LLVM Language Forms
  */
  public abstract void visit(LLVMFunction func);

  public abstract void visit(LLVMBlock block);


  /*
    Instructions
  */
  public abstract void visit(AssignmentInstruction inst);

  public abstract void visit(BranchInstruction inst);

  public abstract void visit(ConditionalBranchInstruction inst);

  public abstract void visit(GlobalAssignmentInstruction isnt);

  public abstract void visit(InvocationInstruction inst);

  public abstract void visit(PhiInstruction inst);

  public abstract void visit(PrintInstruction inst);

  public abstract void visit(ReadInstruction inst);

  public abstract void visit(ReturnEmptyInstruction inst);

  public abstract void visit(ReturnInstruction inst);

  public abstract void visit(StoreInstruction inst);


  /*
    Operations
  */
  public abstract void visit(AllocateOperation opr);

  public abstract void visit(BinaryOperation opr);

  public abstract void visit(BitcastOperation opr);

  public abstract void visit(ComparisonOperation opr);

  public abstract void visit(GlobalOperation opr);

  public abstract void visit(InvocationOperation opr);

  public abstract void visit(LoadOperation opr);

  public abstract void visit(PointerOperation opr);

  public abstract void visit(TruncateOperation opr);

  public abstract void visit(TypeOperation opr);

  public abstract void visit(XorOperation opr);

  public abstract void visit(ZextOperation opr);
}
