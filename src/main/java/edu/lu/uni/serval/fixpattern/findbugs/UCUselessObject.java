package edu.lu.uni.serval.fixpattern.findbugs;

import edu.lu.uni.serval.fixpattern.FixTemplate;
import edu.lu.uni.serval.jdt.tree.ITree;

/**
 * Fix pattern for UC_USELESS_OBJECT violations.
 * 
 * @author kui.liu
 *
 */
public class UCUselessObject extends FixTemplate {

	/*
	 * Fix Pattern:
	 * 
	 * DEL VariableDeclarationStatement@...
	 * 
	 */
	
	@Override
	public void generatePatches() {
		ITree suspStmtTree = this.getSuspiciousCodeTree();
		String varName = identifyVariableName(suspStmtTree);
		int endPosition = suspCodeStartPos + suspStmtTree.getLength();
		if (!"".equals(varName)) { // statements related to variable.
			endPosition = identifyRelatedStatements(suspStmtTree, varName);
		}

		String originStr = this.suspJavaFileCode.substring(suspCodeStartPos, endPosition);
		int originLines = originStr.length() - originStr.replace("\n", "").length();

		StringBuilder fixedCodeStr1 = new StringBuilder("");
		for (int i = 0; i < originLines; i++) {
			fixedCodeStr1.append('\n');
		}

		this.generatePatch(endPosition, endPosition, fixedCodeStr1.toString(), null);
	}

}
