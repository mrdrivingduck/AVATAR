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
public class RVReturnValueIgnoredInffered extends FixTemplate {

	/*
	 * -  var.method(args);
	 * +  var = var.method(args);
	 */
	
	@Override
	public void generatePatches() {
		ITree tree = this.getSuspiciousCodeTree();
		List<ITree> buggyExps = findBuggyExpressions(tree);
		if (buggyExps.isEmpty()) return;
		
		for (int index = 1, size = buggyExps.size(); index < size; index ++) {
			ITree buggyExp = buggyExps.get(index);
			StringBuilder sb = new StringBuilder(this.getSubSuspiciouCodeStr(0, buggyExp.getPos()));
			sb.append(generatedFix(buggyExp));
			sb.append(this.getSubSuspiciouCodeStr(buggyExp.getPos(), this.suspCodeEndPos));
			this.generatePatch(sb.toString());
		}
	}

	private List<ITree> findBuggyExpressions(ITree tree) {
		List<ITree> children = tree.getChildren();
		List<ITree> buggyExps = new ArrayList<>();
		
		for (ITree child : children) {
			int type = child.getType();
			if (Checker.isComplexExpression(type)) {
				if (Checker.isMethodInvocation(type)) {
					buggyExps.add(tree);
					continue;
				}
				buggyExps.addAll(findBuggyExpressions(child));
			} else if (Checker.isSimpleName(type)) {
				String childLabel = child.getLabel();
				if (childLabel.startsWith("MethodName:")) {
					buggyExps.add(tree);
					continue;
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
		int startPos = buggyExp.getPos();
		int endPos = equalsMethod.getPos();
		String varExp = this.getSubSuspiciouCodeStr(startPos, endPos).trim();
		
		return varExp.substring(0, varExp.length() - 1) + " = "; // remove "."
	}

}
