package edu.lu.uni.serval.fixpattern.findbugs;

import java.util.ArrayList;
import java.util.List;

import edu.lu.uni.serval.fixpattern.FixTemplate;
import edu.lu.uni.serval.jdt.tree.ITree;
import edu.lu.uni.serval.utils.Checker;

/**
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
		
		ITree firstBuggyExp = buggyExps.get(0);
		int startPos = firstBuggyExp.getPos();
		StringBuilder fixedCodeStr1 = new StringBuilder(this.getSubSuspiciouCodeStr(this.suspCodeStartPos, startPos));
		fixedCodeStr1.append(generatedFix(operators.get(0), floatingExps1.get(0), floatingExps2.get(0)));
		startPos = startPos + firstBuggyExp.getLength();
		
		for (int index = 1; index < buggyExps.size(); index++) {
			ITree buggyExp = buggyExps.get(index);
			fixedCodeStr1.append(this.getSubSuspiciouCodeStr(startPos, buggyExp.getPos()));
			fixedCodeStr1.append(generatedFix(operators.get(index), floatingExps1.get(index), floatingExps2.get(index)));
			startPos = buggyExp.getPos() + buggyExp.getLength();
		}
		
		fixedCodeStr1.append(this.getSubSuspiciouCodeStr(startPos, this.suspCodeEndPos));
		
		this.generatePatch(fixedCodeStr1.toString());
	}

	private void findBuggyExpressions(ITree tree) {
		List<ITree> children = tree.getChildren();
		
		for (ITree child : children) {
			int type = child.getType();
			if (Checker.isComplexExpression(type)) {
				if (Checker.isInfixExpression(type)) {
					List<ITree> subChildren = child.getChildren();
					String op = subChildren.get(1).getLabel();
					if ("==".equals(op) || "!=".equals(op)) {

						ITree exp1 = subChildren.get(0);
						ITree exp2 = subChildren.get(2);
						String var1Type = varTypesMap.get(exp1.getLabel());
						String var2Type = varTypesMap.get(exp2.getLabel());

						if (var1Type.equals("double") || var1Type.equals("Double") ||
							var1Type.equals("float") || var1Type.equals("Float") ||
							var2Type.equals("double") || var2Type.equals("Double") ||
							var2Type.equals("float") || var2Type.equals("Float")) {
							
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
				}
				findBuggyExpressions(child);
			} else if (Checker.isStatement(type)) {
				break;
			}
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
