package edu.lu.uni.serval.fixpattern.findbugs;

import java.util.ArrayList;
import java.util.List;

import edu.lu.uni.serval.fixpattern.FixTemplate;
import edu.lu.uni.serval.jdt.tree.ITree;
import edu.lu.uni.serval.utils.Checker;

public class FETestIfEqualToNan extends FixTemplate {

	/*
	 * d == Double.NaN  --> Double.isNaN(d).
	 * d != Double.NaN --> !Double.isNaN(d).
	 */
	
	private List<ITree> buggyExps = new ArrayList<>();
	private List<String> operators = new ArrayList<>();
	private List<String> floatingExps = new ArrayList<>();
	
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
		fixedCodeStr1.append(generatedFix(operators.get(0), floatingExps.get(0)));
		startPos = startPos + firstBuggyExp.getLength();
		
		for (int index = 1; index < buggyExps.size(); index++) {
			ITree buggyExp = buggyExps.get(index);
			fixedCodeStr1.append(this.getSubSuspiciouCodeStr(startPos, buggyExp.getPos()));
			fixedCodeStr1.append(generatedFix(operators.get(index), floatingExps.get(index)));
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
						ITree exp = null;
						if ("Double.NaN".equals(subChildren.get(2).getLabel())) {
							exp = subChildren.get(0);
						} else if ("Double.NaN".equals(subChildren.get(0).getLabel())) {
							exp = subChildren.get(2);
						}
						if (exp != null) {
							int startPos = exp.getPos();
							int endPos = startPos + exp.getLength();
							String floatingVariable = this.getSubSuspiciouCodeStr(startPos, endPos);

							buggyExps.add(child);
							operators.add(op);
							floatingExps.add(floatingVariable);
						}
					}
				}
				findBuggyExpressions(child);
			} else if (Checker.isStatement(type)) {
				break;
			}
		}
	}

	private String generatedFix(String op, String floatingVariable) {
		StringBuilder sb = new StringBuilder("Double.isNaN(");
		sb.append(floatingVariable);
		sb.append(')');
		if ("!=".equals(op)) {
			sb.insert(0, '!');
		}
		return sb.toString();
	}

}
