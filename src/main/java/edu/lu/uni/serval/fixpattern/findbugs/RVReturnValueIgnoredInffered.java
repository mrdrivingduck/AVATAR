package edu.lu.uni.serval.fixpattern.findbugs;

import java.util.ArrayList;
import java.util.List;

import edu.lu.uni.serval.fixpattern.FixTemplate;
import edu.lu.uni.serval.jdt.tree.ITree;
import edu.lu.uni.serval.utils.Checker;

/**
 * e.g. for str.substring(), repair it to str = str.substring().
 * 
 * -  var.method(args);
 * +  var = var.method(args);
 * 
 * @author Mr Dk.
 */
public class RVReturnValueIgnoredInffered extends FixTemplate {

	private List<ITree> buggyExps = new ArrayList<>();
	private List<String> invokers = new ArrayList<>();	

	@Override
	public void generatePatches() {
		ITree tree = this.getSuspiciousCodeTree();
		findBuggyExpressions(tree);
		if (buggyExps.isEmpty()) {
			return;
		}

		for (int i = 0; i < buggyExps.size(); i++) {
			ITree buggyExp = buggyExps.get(i);
			StringBuilder sb = new StringBuilder(this.getSubSuspiciouCodeStr(this.suspCodeStartPos, buggyExp.getPos()));
			sb.append(generatedFix(buggyExp, invokers.get(i)));
			sb.append(this.getSubSuspiciouCodeStr(buggyExp.getPos() + buggyExp.getLength(), this.suspCodeEndPos));
			this.generatePatch(sb.toString());
		}
	}

	private void findBuggyExpressions(ITree tree) {
		for (ITree child : tree.getChildren()) {

			if (child.getChildren().size() < 1) {
				continue;
			}

			if (Checker.isMethodInvocation(child.getType())) {
				if (child.getChild(0) != null && Checker.isSimpleName(child.getChild(0).getType())) {
					String invoker = child.getChild(0).getLabel();
					if (invoker.startsWith("Name:")) {
						invoker = invoker.substring("Name:".length());
					}

					buggyExps.add(tree);
					invokers.add(invoker);
				}
			}

			findBuggyExpressions(child);
		}
	}

	private String generatedFix(ITree buggyExp, String invoker) {
		int startPos = buggyExp.getPos();
		int endPos = buggyExp.getPos() + buggyExp.getLength();
		String exp = this.getSubSuspiciouCodeStr(startPos, endPos);
		
		return invoker + " = " + exp;
	}

}
