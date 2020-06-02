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
		int endPos1 = 0;
		List<ITree> children = this.getSuspiciousCodeTree().getChildren();
		int size = children.size();
		for (int index = 0; index < size; index ++) {
			ITree child = children.get(index);
			if (Checker.isStatement(child.getType())) {
				endPos1 = child.getPos();
				break;
			}
		}
		if (endPos1 == 0) {
			// No Statement in the control flow.
			return;
		}
		ITree lastStmt = children.get(size - 1);
		int endPos2 = lastStmt.getPos() + lastStmt.getLength();

		String originStr = this.getSuspiciousCodeStr();
		int originLines = originStr.length() - originStr.replace("\n", "").length();
		
		StringBuilder fixedCodeStr1 = new StringBuilder(this.getSubSuspiciouCodeStr(endPos1, endPos2));
		int fixedLines = fixedCodeStr1.length() - fixedCodeStr1.toString().replace("\n", "").length();
		
		for (int i = 0; i < originLines - fixedLines; i++) {
			fixedCodeStr1.append('\n');
		}

		StringBuilder empty = new StringBuilder("");
		for (int i = 0; i < originLines; i++) {
			empty.append('\n');
		}
		
		this.generatePatch(fixedCodeStr1.toString()); // Just remove the control flow.
		this.generatePatch(empty.toString()); // Remove all code in the control flow.
	}

}
