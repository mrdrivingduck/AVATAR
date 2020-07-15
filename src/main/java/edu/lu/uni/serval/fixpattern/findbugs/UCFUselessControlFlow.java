package edu.lu.uni.serval.fixpattern.findbugs;

import java.util.List;

import edu.lu.uni.serval.fixpattern.FixTemplate;
import edu.lu.uni.serval.jdt.tree.ITree;
import edu.lu.uni.serval.utils.Checker;

public class UCFUselessControlFlow extends FixTemplate {

	/*
	 * Fix Pattern:
	 * 
	 * 1. DEL IfStatement@...
	 * 2. DEL SwithStatement@...
	 */
	
	@Override
	public void generatePatches() {
		// The buggy statement is an IfStatement.
		if (Checker.isIfStatement(this.getSuspiciousCodeTree().getType())) {
			ITree parentTree = this.getSuspiciousCodeTree().getParent();
			if (Checker.isIfStatement(parentTree.getType())) {
				/* 
				 * Its parent is an IfStatement as well.
				 * It needs to check whether the buggy IfStatement is a statement in the block of its parent IfStatement or an IfStatement in the Else branch.
				 */
				List<ITree> siblingTrees = parentTree.getChildren();
				int index = siblingTrees.indexOf(this.getSuspiciousCodeTree());
				if (index == (siblingTrees.size() - 1)) {// The buggy IfStatement is the last child statement of its parent IfStatement.
					ITree previousSiblingTree = siblingTrees.get(index-1);
					int startPos_ = previousSiblingTree.getPos() + previousSiblingTree.getLength();
					int endPos_ = this.getSuspiciousCodeTree().getPos();
					String codeFrag = this.getSubSuspiciouCodeStr2(startPos_, endPos_);
					int elseIndex = codeFrag.lastIndexOf("else");
					if (elseIndex > 0) {
						// if (...) {statement(s)} else buggy_if (...) {...} ...
						int startPos = startPos_ + elseIndex;
						int endPos = endPos_ + this.getSuspiciousCodeTree().getLength();
						this.generatePatch(startPos, endPos, "/* ", " */");
						
						List<ITree> suspChildren = this.getSuspiciousCodeTree().getChildren();
						int size_ = suspChildren.size();
						for (int i = size_ - 1; i > 1; i --) {
							/*
							 * if (...) {statement(s)} else buggy_If (...) { ... } else if (...) {...}
							 * or 
							 * if (...) {statement(s)} else buggy_If (...) { ... } else {...if(...){}}
							 * or
							 * if (...) {statement(s)} else buggy_If (...) { ... } else {...}
							 */
							ITree child = suspChildren.get(i-1);
							ITree child_ = suspChildren.get(i);
							startPos = child.getPos() + child.getLength();
							codeFrag = this.getSubSuspiciouCodeStr2(startPos, child_.getPos());
							elseIndex = codeFrag.indexOf("else");
							if (elseIndex >= 0) {
								endPos = startPos + elseIndex + 4;
								startPos = endPos_;
								this.generatePatch(startPos, endPos, " /* ", " */ ");
								break;
							}
						}
						return;
//					} else {// if (...) { ... buggy_if (...) {}}
					}
//				} else {// if (...) { ... buggy_if (...) {} ... }
				}
			} 
			removeTheIfControlFlow();
		}
		this.generatePatch(""); // Remove all code in the control flow.
	}

	private void removeTheIfControlFlow() {
		int startPos = 0;
		List<ITree> children = this.getSuspiciousCodeTree().getChildren();
		int size = children.size();
		for (int index = 0; index < size; index ++) {
			ITree child = children.get(index);
			if (Checker.isStatement(child.getType())) {
				startPos = child.getPos();// first statement in the block of the control flow.
				break;
			}
		}
		if (startPos != 0) {
			List<ITree> suspChildren = this.getSuspiciousCodeTree().getChildren();
			int size_ = suspChildren.size();
			boolean isIfElse = false;
			for (int i = size_ - 1; i > 1; i --) {
				// buggy_if () {...} else if (...) {}  or buggy_if () {...} else { ... }
				ITree child = suspChildren.get(i-1);
				ITree child_ = suspChildren.get(i);
				int startPos_ = child.getPos() + child.getLength();
				String codeFrag = this.getSubSuspiciouCodeStr2(startPos_, child_.getPos());
				int elseIndex = codeFrag.indexOf("else");
				if (elseIndex >= 0) {
					isIfElse = true;
					if (i == size_ - 1) { // buggy_if () {...} else if (...) {} 
						int startPos2 = child_.getPos();
						int endPos = startPos2 + child_.getLength();
						String fixedCodeStr1 = this.getSubSuspiciouCodeStr2(startPos2, endPos);
						this.generatePatch(fixedCodeStr1);// Just its else-if branch. 
						endPos = startPos_;
						String fixedCodeStr2 = this.getSubSuspiciouCodeStr2(startPos, endPos);
						this.generatePatch(fixedCodeStr2 + fixedCodeStr1);// Just removing the If control flow but keeping its block and its else-if branch.
					} else { // buggy_if () {...} else { ... }
						int endPos = startPos_;
						String fixedCodeStr1 = this.getSubSuspiciouCodeStr2(startPos, endPos);
						this.generatePatch(fixedCodeStr1); // Removing the IfStatement and its else block.
						startPos = child_.getPos();
						ITree lastChild = suspChildren.get(size_ - 1);
						endPos = lastChild.getPos() + lastChild.getLength();
						fixedCodeStr1 = this.getSubSuspiciouCodeStr2(startPos, endPos);
						this.generatePatch(fixedCodeStr1); // Keep its else block.
					}
					break;
				}
			}
			
			if (!isIfElse) {// buggy_if () {...}
				ITree lastStmt = children.get(size - 1);
				int endPos = lastStmt.getPos() + lastStmt.getLength();
				String fixedCodeStr1 = this.getSubSuspiciouCodeStr2(startPos, endPos);
				this.generatePatch(fixedCodeStr1); // Just removing the IfStatement but keeping its block.
			}
		}
	}

}