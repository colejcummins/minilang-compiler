package cfg;

import java.util.ArrayList;
import java.util.List;

import ast.BlockStatement;
import ast.Statement;
import llvm.Instruction;

public class BasicBlock {
  private String label;
  private Statement block;
  private List<BasicBlock> predecessors;
  private List<Instruction> instructions;
  private boolean isSealed;

  public BasicBlock(String label) {
    this.label = label;
    this.block = null;
    this.isSealed = false;
    this.instructions = new ArrayList<Instruction>();
    this.predecessors = new ArrayList<BasicBlock>();
  }

  public BasicBlock(String label, Statement block) {
    this.block = block;
    this.label = label;
    this.isSealed = false;
    this.instructions = new ArrayList<Instruction>();
    this.predecessors = new ArrayList<BasicBlock>();
  }

  public BasicBlock(String label, List<Instruction> instructions) {
    this.block = null;
    this.label = label;
    this.isSealed = false;
    this.instructions = instructions;
    this.predecessors = new ArrayList<BasicBlock>();
  }

  public String toString() {
    String out = String.format("| %s |  preds:", this.label);
    for (BasicBlock b : this.predecessors) {
      out += b.getLabel() + " ";
    }
    out += " " + this.predecessors.size();
    return out;
  }

  public String getLabel() {
    return this.label;
  }

  public Statement getBlock() {
    return this.block;
  }

  public List<BasicBlock> getPredecessors() {
    return this.predecessors;
  }

  public List<Instruction> getInstructions() {
    return this.instructions;
  }

  public Boolean getIsSealed() {
    return this.isSealed;
  }

  public void seal() {
    this.isSealed = true;
  }

  public void setInstructions(List<Instruction> instructions) {
    this.instructions = instructions;
  }

  public void addInstruction(Instruction i) {
    this.instructions.add(i);
  }

  public void addBlock(BasicBlock block) {
    //
  }

  public void addThen(BasicBlock block) {
    //
  }

  public void addElse(BasicBlock block) {
    //
  }

  public void addPredecessor(BasicBlock pred) {
    this.predecessors.add(pred);
  }

  public void clearPredecessors() {
    this.predecessors.clear();
  }

  public void removePredecessor(BasicBlock block) {
    this.predecessors.remove(block);
  }

  public Boolean allPredecessorsSealed() {
    for (BasicBlock pred : this.predecessors) {
      if (!pred.getIsSealed()) {
        return false;
      }
    }
    return true;
  }

  public BasicBlock getNext() {
    //
    return null;
  }

  public BasicBlock getThen() {
    //
    return null;
  }

  public BasicBlock getElse() {
    //
    return null;
  }
}
