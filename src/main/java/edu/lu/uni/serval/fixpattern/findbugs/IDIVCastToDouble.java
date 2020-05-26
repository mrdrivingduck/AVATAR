package edu.lu.uni.serval.fixpattern.findbugs;

import java.util.ArrayList;
import java.util.List;

import edu.lu.uni.serval.fixpattern.FixTemplate;
import edu.lu.uni.serval.jdt.tree.ITree;
import edu.lu.uni.serval.utils.Checker;

/**
 * Useful for Math_11.
 * 
 * @author Mr Dk.
 */
public class IDIVCastToDouble extends FixTemplate {

	/*
	 * intVarExp / 10 --> intVarExp / 10d(or f).
	 * 1 / var --> 1.0 / var.
	 * dividend / divisor --> dividend / (float or double) divisor.
	 * dividend / divisor --> (double or float) dividend / divisor
	 */
	
	private List<ITree> buggyExps = new ArrayList<>();
	private List<String> operators = new ArrayList<>();
	private List<String> buggyStrList = new ArrayList<>();

	@Override
	public void generatePatches() {
		ITree tree = this.getSuspiciousCodeTree();
		findBuggyExpressions(tree);
		if (buggyExps.isEmpty()) return;

		StringBuilder fixCode = new StringBuilder();
		int startPos = this.suspCodeStartPos;
		for (int i = 0; i < buggyExps.size(); i++) {
			ITree buggyExp = buggyExps.get(i);
			if (startPos > buggyExp.getPos()) {
				return;
			}
			fixCode.append(this.getSubSuspiciouCodeStr(startPos, buggyExp.getPos()));
			fixCode.append(generatedFix(operators.get(i), buggyStrList.get(i)));
			startPos = buggyExp.getPos() + buggyExp.getLength();
		}
		
		fixCode.append(this.getSubSuspiciouCodeStr(startPos, this.suspCodeEndPos));
		
		this.generatePatch(fixCode.toString());
	}

	private void findBuggyExpressions(ITree tree) {
		List<ITree> children = tree.getChildren();
		
		for (ITree child : children) {
			if (Checker.isInfixExpression(child.getType())) {
				List<ITree> subChildren = child.getChildren();
				String op = subChildren.get(1).getLabel();

				if ("/".equals(op)) {
					ITree dividendExp = subChildren.get(0);
					ITree divisorExp = subChildren.get(2);

					if (dividendExp != null && Checker.isNumberLiteral(dividendExp.getType())) {
						int startPos = dividendExp.getPos();
						int endPos = startPos + dividendExp.getLength();
						String buggyStr = this.getSubSuspiciouCodeStr(startPos, endPos);
						if (!buggyStr.contains("d") &&
							!buggyStr.contains("f") &&
							!buggyStr.contains(".")) {
							buggyExps.add(dividendExp);
							operators.add("CastConst");
							buggyStrList.add(buggyStr);
						}
					} else if (dividendExp != null) {
						int startPos = dividendExp.getPos();
						int endPos = startPos + dividendExp.getLength();
						String buggyStr = this.getSubSuspiciouCodeStr(startPos, endPos);
						buggyExps.add(dividendExp);
						operators.add("CastExpression");
						buggyStrList.add(buggyStr);
					}

					if (divisorExp != null && Checker.isNumberLiteral(divisorExp.getType())) {
						int startPos = divisorExp.getPos();
						int endPos = startPos + divisorExp.getLength();
						String buggyStr = this.getSubSuspiciouCodeStr(startPos, endPos);
						if (!buggyStr.contains("d") &&
							!buggyStr.contains("f") &&
							!buggyStr.contains(".")) {
							buggyExps.add(divisorExp);
							operators.add("CastConst");
							buggyStrList.add(buggyStr);
						}
					} else if (divisorExp != null) {
						int startPos = divisorExp.getPos();
						int endPos = startPos + divisorExp.getLength();
						String buggyStr = this.getSubSuspiciouCodeStr(startPos, endPos);
						buggyExps.add(divisorExp);
						operators.add("CastExpression");
						buggyStrList.add(buggyStr);
					}
				}
			}

			findBuggyExpressions(child);
		}
	}

	private String generatedFix(String op, String floatingExp) {
		if ("CastConst".equals(op)) {
			return floatingExp + "d";
		} else {
			return "((double) " + floatingExp + ")";
		}
	}

}
