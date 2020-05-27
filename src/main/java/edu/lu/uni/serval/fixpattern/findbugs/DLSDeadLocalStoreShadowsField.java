package edu.lu.uni.serval.fixpattern.findbugs;

import java.util.ArrayList;
import java.util.List;

import edu.lu.uni.serval.fixpattern.FixTemplate;
import edu.lu.uni.serval.jdt.tree.ITree;
import edu.lu.uni.serval.utils.Checker;

/**
 * 
 * 
 * @author Mr Dk.
 */
public class DLSDeadLocalStoreShadowsField extends FixTemplate {

	/*
	 * var = var --> this.var = var;
	 */
	
	private List<ITree> buggyExps = new ArrayList<>();
	private List<String> classMembers = new ArrayList<>();
	private List<String> ops = new ArrayList<>();

	@Override
	public void generatePatches() {
		ITree tree = this.getSuspiciousCodeTree();
		findBuggyExpressions(tree);
		if (buggyExps.isEmpty()) {
			return;
		}

		StringBuilder fixCode = new StringBuilder();
		int startPos = this.suspCodeStartPos;
		for (int i = 0; i < buggyExps.size(); i++) {
			ITree buggyExp = buggyExps.get(i);
			fixCode.append(this.getSubSuspiciouCodeStr(startPos, buggyExp.getPos()));
			fixCode.append(generatedFix(ops.get(i), classMembers.get(i)));
			startPos = buggyExp.getPos() + buggyExp.getLength();
		}
		
		fixCode.append(this.getSubSuspiciouCodeStr(startPos, this.suspCodeEndPos));
		this.generatePatch(fixCode.toString());
	}

	private void findBuggyExpressions(ITree tree) {
		for (ITree child : tree.getChildren()) {
			int type = child.getType();
			
			// log.debug(child.getLabel() + " " + child.getType() + " \\");
			// findBuggyExpressions(child);
			// log.debug(child.getLabel() + " " + child.getType() + " /");

			if (Checker.isInfixExpression(type)) {
				if (child.getChildren().size() >= 3) {
					if (Checker.isSimpleName(child.getChild(0).getType()) &&
						Checker.isSimpleName(child.getChild(2).getType()) &&
						Checker.isOperator(child.getChild(1).getType())) {
							if ("==".equals(child.getChild(1).getLabel()) ||
								"!=".equals(child.getChild(1).getLabel())) {
								String var1 = child.getChild(0).getLabel();
								String var2 = child.getChild(2).getLabel();
		
								if (var1 != null && var2 != null) {
									if (var1.startsWith("Name:")) {
										var1 = var1.substring("Name:".length());
									}
									if (var2.startsWith("Name:")) {
										var2 = var2.substring("Name:".length());
									}
								}

								if (var1.equals(var2)) {
									buggyExps.add(child);
									classMembers.add(var1);
									ops.add(child.getChild(1).getLabel());
								}
							}
						}
				}
			}

			findBuggyExpressions(child);
		}
	}

	private String generatedFix(String op, String var) {
		return var + " " + op + " this." + var;
	}

}
