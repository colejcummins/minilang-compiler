package arm;

import llvm.*;

import ast.BinaryExpression.Operator;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class LLVMtoARMVisitor extends LLVMVisitor {

  private ArrayList<ARMInstruction> currentBlock;
  private ArrayList<ARMBlock> currentFunction;
  private ArrayList<ARMFunction> functions;

  private Map<String, Map<String, Operand>> phis;
  private int phiNum;

  private String currentBlockName;
  private Operand currentTarget;


  public LLVMtoARMVisitor() {
    this.currentBlock = new ArrayList<ARMInstruction>();
    this.currentFunction = new ArrayList<ARMBlock>();
    this.functions = new ArrayList<ARMFunction>();
  }


  public void visit(LLVMProgram prog) {
    for (LLVMFunction func : prog.getFuncs()) {
      this.visit(func);
    }
  }


  public void visit(LLVMFunction func) {
    this.phiNum = 0;

    this.constructPhis(func);

    this.currentBlock.add(new Push((List<Register>)Arrays.asList(new Register("fp"), new Register("pc"))));
    this.currentBlock.add(new Add(new Register("fp"), new Register("sp"), new Immediate(4)));
    for (int i = 0; i < func.getParams().size(); i++) {
      this.currentBlock.add(new Move(this.valueToOperand(func.getParams().get(i)), new Register("r" + Integer.toString(i))));
    }

    for (LLVMBlock block : func.getBlocks()) {
      this.visit(block);
    }
    this.functions.add(new ARMFunction(func.getName(), this.getFunction()));
  }


  private void constructPhis(LLVMFunction func) {
    int numPhi = 0;

    this.phis = new HashMap<String, Map<String, Operand>>();
    for (LLVMBlock block : func.getBlocks()) {
      for (Instruction inst : block.getInstructions()) {
        if (inst instanceof PhiInstruction) {
          numPhi = makePhiMap((PhiInstruction)inst, numPhi);

        } else {
          continue;
        }
      }
    }
  }


  private int makePhiMap(PhiInstruction inst, int numPhi) {
    for (Phi phi : inst.getPhis()) {
      if (!this.phis.containsKey(phi.getBlockLabel())) {
        this.phis.put(phi.getBlockLabel(), new HashMap<String, Operand>());
      }
      this.phis.get(phi.getBlockLabel()).put("%phi" + Integer.toString(numPhi), this.valueToOperand(phi.getValue()));
    }
    numPhi++;
    return numPhi;
  }


  private void addPhisToBlock() {
    if (this.phis.containsKey(this.currentBlockName)) {
      TreeMap<String, Operand> sorted = new TreeMap<>();
      sorted.putAll(this.phis.get(this.currentBlockName));

      for (String key : sorted.keySet()) {
        this.currentBlock.add(new Move(new Register(key), sorted.get(key)));
      }
    }
  }


  public void visit(LLVMBlock block) {
    this.currentBlockName = block.getLabel();
    for (Instruction inst : block.getInstructions()) {
      inst.accept(this);
    }
    this.currentFunction.add(new ARMBlock(block.getLabel(), this.getBlock()));
  }


  public void visit(AssignmentInstruction inst) {
    this.currentTarget = this.valueToOperand(inst.getTarget());
    inst.source().accept(this);
  }


  public void visit(BranchInstruction inst) {
    this.addPhisToBlock();
    this.currentBlock.add(new Branch(inst.getLabel()));
  }


  public void visit(ConditionalBranchInstruction inst) {
    this.addPhisToBlock();
    this.currentBlock.add(new Compare(this.valueToOperand(inst.getCompValue()), new Immediate(1)));
    this.currentBlock.add(new BranchConditional(Operator.EQ, inst.getTrueLabel()));
    this.currentBlock.add(new Branch(inst.getFalseLabel()));
  }


  public void visit(GlobalAssignmentInstruction inst) {
    //
  }


  public void visit(InvocationInstruction inst) {
    InvocationOperation opr = inst.getInvocation();
    for (int i = 0; i < opr.getParams().size(); i++) {
      this.currentBlock.add(
        new Move(
          new Register("r" + String.valueOf(i)),
          this.valueToOperand(opr.getParams().get(i))
        )
      );
    }
    this.currentBlock.add(new BranchLink(opr.getName()));
  }


  public void visit(PhiInstruction inst) {
    this.currentBlock.add(new Move(this.valueToOperand(inst.getTarget()), new Register("%phi" + String.valueOf(phiNum))));
    this.phiNum++;
  }


  public void visit(PrintInstruction inst) {
    //
  }


  public void visit(ReadInstruction inst) {
    //
  }


  public void visit(ReturnEmptyInstruction inst) {
    this.currentBlock.add(new Pop((List<Register>)Arrays.asList(new Register("fp"), new Register("pc"))));
  }


  public void visit(ReturnInstruction inst) {
    this.currentBlock.add(new Move(new Register("r0"), this.valueToOperand(inst.getValue())));
    this.currentBlock.add(new Pop((List<Register>)Arrays.asList(new Register("fp"), new Register("pc"))));
  }


  public void visit(StoreInstruction inst) {
    this.currentBlock.add(new Store(this.valueToOperand(inst.getSource()), this.valueToOperand(inst.getTarget())));
  }


  public void visit(AllocateOperation opr) {
    //
  }


  public void visit(BinaryOperation opr) {
    Operand left = this.valueToOperand(opr.getLeft());
    Operand right = this.valueToOperand(opr.getRight());
    if (opr.getOperator() == Operator.DIVIDE) {
      this.currentBlock.add(new Move(new Register("r0"), left));
      this.currentBlock.add(new Move(new Register("r1"), right));
      this.currentBlock.add(new BranchLink("__aeabi_idiv"));
      this.currentBlock.add(new Move(this.currentTarget, new Register("r0")));
    } else {
      this.currentBlock.add(new BinaryARMInstruction(opr.getOperator(), this.currentTarget, left, right));
    }
  }


  public void visit(BitcastOperation opr) {
    this.currentBlock.add(new Move(this.currentTarget, this.valueToOperand(opr.getSourceValue())));
  }


  public void visit(ComparisonOperation opr) {
    this.currentBlock.add(new Move(this.currentTarget, new Immediate(0)));
    this.currentBlock.add(new Compare(this.valueToOperand(opr.getLeft()), this.valueToOperand(opr.getRight())));
    this.currentBlock.add(new MoveConditional(opr.getOperator(), this.currentTarget, new Immediate(1)));
  }


  public void visit(GlobalOperation opr) {
    //
  }


  public void visit(InvocationOperation opr) {
    for (int i = 0; i < opr.getParams().size(); i++) {
      this.currentBlock.add(
        new Move(
          new Register("r" + String.valueOf(i)),
          this.valueToOperand(opr.getParams().get(i))
        )
      );
    }
    this.currentBlock.add(new BranchLink(opr.getName()));
    this.currentBlock.add(new Move(this.currentTarget, new Register("r0")));
  }


  public void visit(LoadOperation opr) {
    this.currentBlock.add(new Load(this.currentTarget, this.valueToOperand(opr.getSource())));
  }


  public void visit(PointerOperation opr) {
    //
  }


  public void visit(TruncateOperation opr) {
    //
  }


  public void visit(TypeOperation opr) {
    //
  }


  public void visit(XorOperation opr) {
    //
  }


  public void visit(ZextOperation opr) {
    //
  }


  public ArrayList<ARMFunction> getFunctions() {
    return this.functions;
  }


  private Operand valueToOperand(Value v) {
    if (v instanceof IntValue) {
      return new Immediate(v.getValue());
    } else {
      return new Register(v.toString());
    }
  }


  private ArrayList<ARMBlock> getFunction() {
    ArrayList<ARMBlock> blocks = (ArrayList<ARMBlock>)this.currentFunction.clone();
    this.currentFunction.clear();
    return blocks;
  }


  private ArrayList<ARMInstruction> getBlock() {
    ArrayList<ARMInstruction> instrs = (ArrayList<ARMInstruction>)this.currentBlock.clone();
    this.currentBlock.clear();
    return instrs;
  }
}
