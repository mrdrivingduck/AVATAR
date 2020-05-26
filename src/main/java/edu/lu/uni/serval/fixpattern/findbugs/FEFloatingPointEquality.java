package edu.lu.uni.serval.fixpattern.findbugs;

import java.util.ArrayList;
import java.util.List;

import edu.lu.uni.serval.fixpattern.FixTemplate;
import edu.lu.uni.serval.jdt.tree.ITree;
import edu.lu.uni.serval.utils.Checker;

/**
 * Tested through Math_22.
 * 
 * @author Mr Dk.
 */
public class FEFloatingPointEquality extends FixTemplate {

	/*
	 * For a floating point number.
	 * var1 != var2 --> Math.abs(var1 - var2) < 0.00001.
	 * var1 == var2 --> var1.compareTo(var2) == 0.
	 */
	
	private List<ITree> buggyExps = new ArrayList<>();
	private List<String> operators = new ArrayList<>();

	private List<String> floatingExps1 = new ArrayList<>();
	private List<String> floatingExps2 = new ArrayList<>();

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
			if (startPos > buggyExp.getPos()) {
				return;
			}
			fixCode.append(this.getSubSuspiciouCodeStr(startPos, buggyExp.getPos()));
			fixCode.append(generatedFix(operators.get(i), floatingExps1.get(i), floatingExps2.get(i)));
			startPos = buggyExp.getPos() + buggyExp.getLength();
		}
		
		fixCode.append(this.getSubSuspiciouCodeStr(startPos, this.suspCodeEndPos));
		
		this.generatePatch(fixCode.toString());
	}

	private void findBuggyExpressions(ITree tree) {
		List<ITree> children = tree.getChildren();
		
		for (ITree child : children) {
			int type = child.getType();

			if (Checker.isInfixExpression(type)) {
				List<ITree> subChildren = child.getChildren();
				String op = subChildren.get(1).getLabel();
				if ("==".equals(op) || "!=".equals(op)) {

					ITree exp1 = subChildren.get(0);
					ITree exp2 = subChildren.get(2);
						
					int startPos = exp1.getPos();
					int endPos = startPos + exp1.getLength();
					String var1 = this.getSubSuspiciouCodeStr(startPos, endPos);
					startPos = exp2.getPos();
					endPos = startPos + exp2.getLength();
					String var2 = this.getSubSuspiciouCodeStr(startPos, endPos);

					buggyExps.add(child);
					operators.add(op);
					floatingExps1.add(var1);
					floatingExps2.add(var2);
				}
			}

			findBuggyExpressions(child);
		}
	}

	private String generatedFix(String op, String var1, String var2) {
		if (op.equals("!=")) {
			return "Math.abs(" + var1 + " - " + var2 + ") < 0.0001";
		} else {
			return "new Double((double) " + var1 + ").compareTo((double) " + var2 + ") == 0";
		}
	}

}
