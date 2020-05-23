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
public class IDIVCastToDouble extends FixTemplate {

	/*
	 * intVarExp / 10 --> intVarExp / 10d(or f).
	 * 1 / var --> 1.0 / var.
	 * dividend / divisor --> dividend / (float or double) divisor.
	 * dividend / divisor --> (double or float) dividend / divisor
	 */
	
	private List<ITree> buggyExps = new ArrayList<>();
	private List<String> operators = new ArrayList<>();
	private List<String> buggyExpStr = new ArrayList<>();
	
	@Override
	public void generatePatches() {
		ITree tree = this.getSuspiciousCodeTree();
		findBuggyExpressions(tree);
		if (buggyExps.isEmpty()) return;
		
		ITree firstBuggyExp = buggyExps.get(0);
		int startPos = firstBuggyExp.getPos();
		StringBuilder fixedCodeStr1 = new StringBuilder(this.getSubSuspiciouCodeStr(this.suspCodeStartPos, startPos));
		fixedCodeStr1.append(generatedFix(operators.get(0), buggyExpStr.get(0)));
		startPos = startPos + firstBuggyExp.getLength();
		
		for (int index = 1; index < buggyExps.size(); index++) {
			ITree buggyExp = buggyExps.get(index);
			fixedCodeStr1.append(this.getSubSuspiciouCodeStr(startPos, buggyExp.getPos()));
			fixedCodeStr1.append(generatedFix(operators.get(index), buggyExpStr.get(index)));
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

					if ("/".equals(op)) {
						ITree dividendExp = subChildren.get(0);
						ITree divisorExp = subChildren.get(2);

						if (dividendExp != null && Checker.isNumberLiteral(dividendExp.getType())) {
							int startPos = dividendExp.getPos();
							int endPos = startPos + dividendExp.getLength();
							String collectionExp = this.getSubSuspiciouCodeStr(startPos, endPos);
							if (!collectionExp.contains("d") &&
								!collectionExp.contains("f") &&
								!collectionExp.contains(".")) {
								buggyExps.add(dividendExp);
								operators.add("CastConst");
								buggyExpStr.add(collectionExp);
							}
						} else if (dividendExp != null) {
							int startPos = dividendExp.getPos();
							int endPos = startPos + dividendExp.getLength();
							String collectionExp = this.getSubSuspiciouCodeStr(startPos, endPos);
							buggyExps.add(dividendExp);
							operators.add("CastExpression");
							buggyExpStr.add(collectionExp);
						}

						if (divisorExp != null && Checker.isNumberLiteral(divisorExp.getType())) {
							int startPos = divisorExp.getPos();
							int endPos = startPos + divisorExp.getLength();
							String collectionExp = this.getSubSuspiciouCodeStr(startPos, endPos);
							if (!collectionExp.contains("d") &&
								!collectionExp.contains("f") &&
								!collectionExp.contains(".")) {
								buggyExps.add(divisorExp);
								operators.add("CastConst");
								buggyExpStr.add(collectionExp);
							}
						} else if (divisorExp != null) {
							int startPos = divisorExp.getPos();
							int endPos = startPos + divisorExp.getLength();
							String collectionExp = this.getSubSuspiciouCodeStr(startPos, endPos);
							buggyExps.add(divisorExp);
							operators.add("CastExpression");
							buggyExpStr.add(collectionExp);
						}
					}
				}
				findBuggyExpressions(child);
			} else if (Checker.isStatement(type)) {
				break;
			}
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
