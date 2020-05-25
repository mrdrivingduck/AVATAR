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
public class SALocalSelfComparison extends FixTemplate {

	/*
	 * -    var.equals(var);
	 * +    var.equals(this.var); 
	 */
	
	@Override
	public void generatePatches() {
		ITree tree = this.getSuspiciousCodeTree();
		List<ITree> buggyExps = findBuggyExpressions(tree);
		if (buggyExps.isEmpty()) return;
		
		ITree firstBuggyExp = buggyExps.get(0);
		int startPos = firstBuggyExp.getPos();
		StringBuilder fixedCodeStr1 = new StringBuilder(this.getSubSuspiciouCodeStr(this.suspCodeStartPos, startPos));
		fixedCodeStr1.append(generatedFix(firstBuggyExp));
		startPos = startPos + firstBuggyExp.getLength();
		
		for (int index = 1, size = buggyExps.size(); index < size; index ++) {
			ITree buggyExp = buggyExps.get(index);
			fixedCodeStr1.append(this.getSubSuspiciouCodeStr(startPos, buggyExp.getPos()));
			fixedCodeStr1.append(generatedFix(buggyExp));
			startPos = buggyExp.getPos() + buggyExp.getLength();
		}
		
		fixedCodeStr1.append(this.getSubSuspiciouCodeStr(startPos, this.suspCodeEndPos));
		
		this.generatePatch(fixedCodeStr1.toString());
	}

	private List<ITree> findBuggyExpressions(ITree tree) {
		List<ITree> children = tree.getChildren();
		List<ITree> buggyExps = new ArrayList<>();
		
		for (ITree child : children) {
			int type = child.getType();
			if (Checker.isComplexExpression(type)) {
				if (Checker.isMethodInvocation(type)) {
					if (child.getLabel().startsWith("MethodName:equals:")) {
						List<ITree> args = child.getChildren();
						if (args.size() == 1) {
							
							ITree equalsMethod = children.get(children.size() - 1);
							String varInEquals = equalsMethod.getChild(0).getLabel();
							int startPos = tree.getPos();
							int endPos = equalsMethod.getPos();
							String varExp = this.getSubSuspiciouCodeStr(startPos, endPos);
							varExp = varExp.substring(0, varExp.length() - 1);

							if (varInEquals.equals(varExp)) {
								buggyExps.add(tree);
								continue;
							}
						}
					}
				}
				buggyExps.addAll(findBuggyExpressions(child));
			} else if (Checker.isSimpleName(type)) {
				String childLabel = child.getLabel();
				if (childLabel.startsWith("MethodName:")) {
					List<ITree> args = child.getChildren();
					if (childLabel.startsWith("MethodName:equals:")) {
						if (args.size() == 1) {

							ITree equalsMethod = children.get(children.size() - 1);
							String varInEquals = equalsMethod.getChild(0).getLabel();
							int startPos = tree.getPos();
							int endPos = equalsMethod.getPos();
							String varExp = this.getSubSuspiciouCodeStr(startPos, endPos);
							varExp = varExp.substring(0, varExp.length() - 1);

							if (varInEquals.equals(varExp)) {
								buggyExps.add(tree);
								continue;
							}
						}
					}
					for (ITree arg : args) {
						buggyExps.addAll(findBuggyExpressions(arg));
					}
				}
			} else if (Checker.isStatement(type)) {
				break;
			}
		}
		
		return buggyExps;
	}

	private String generatedFix(ITree buggyExp) {
		List<ITree> children = buggyExp.getChildren();
		ITree equalsMethod = children.get(children.size() - 1);
		String thisVarExp = equalsMethod.getChild(0).getLabel();

		return thisVarExp + ".equals(this." + thisVarExp + ")";
	}

}
