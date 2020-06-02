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
public class ECBadArrayCompare extends FixTemplate {
    
	/*
	 * array1 == array2 --> Arrays.equals(array1, array2).
	 * array1.equals(array2) --> Arrays.equals(array1, array2).
	 */

	List<String> varA = new ArrayList<>();
	List<String> varB = new ArrayList<>();
	List<String> operators = new ArrayList<>();
	
	@Override
	public void generatePatches() {
		ITree tree = this.getSuspiciousCodeTree();
		allVarNamesMap = readAllVariableNames(tree, varTypesMap, allVarNamesList);
		List<ITree> buggyExps = findBuggyExpressions(tree);
		if (buggyExps.isEmpty()) {
			return;
		}

		StringBuilder fixCode = new StringBuilder();
		int startPos = this.suspCodeStartPos;
		for (int i = 0; i < buggyExps.size(); i++) {
			ITree buggyExp = buggyExps.get(i);
			fixCode.append(this.getSubSuspiciouCodeStr(startPos, buggyExp.getPos()));
			fixCode.append(generatedFix(varA.get(i), varB.get(i), operators.get(i)));
			startPos = buggyExp.getPos() + buggyExp.getLength();
		}

		// StringBuilder sb = new StringBuilder(suspJavaFileCode);
		// sb.insert(sb.indexOf("import "), "import java.util.Arrays;");
		// suspJavaFileCode = sb.toString();
		
		fixCode.append(this.getSubSuspiciouCodeStr(startPos, this.suspCodeEndPos));
		this.generatePatch(fixCode.toString());
	}

	private List<ITree> findBuggyExpressions(ITree tree) {
		List<ITree> children = tree.getChildren();
		List<ITree> buggyExps = new ArrayList<>();
		
		for (ITree child : children) {
			int type = child.getType();

			if (Checker.isInfixExpression(type)) {
				if (child.getChild(1).getLabel().equals("==") ||
					child.getChild(1).getLabel().equals("!=")) {
					
					String var1 = child.getChild(0).getLabel();
					String var2 = child.getChild(2).getLabel();

					if (var1 != null && var2 != null) {
						buggyExps.add(child);
						varA.add(var1);
						varB.add(var2);
						operators.add(child.getChild(1).getLabel());
					}

				}
			} else if (Checker.isMethodInvocation(type)) {
				if (child.getChild(1) != null &&
					child.getChild(1).getLabel().startsWith("MethodName:equals") &&
					Checker.isSimpleName(child.getChild(0).getType())) {
					String var1 = child.getChild(0).getLabel();
					String var2 = child.getChild(1).getChild(0).getLabel();
					
					if (var1 != null && var1.startsWith("Name:")) {
						var1 = var1.substring("Name:".length());
					}
					if (var2 != null && var2.startsWith("Name:")) {
						var2 = var2.substring("Name:".length());
					}

					if (var1 != null && var2 != null) {
						buggyExps.add(child);
						varA.add(var1);
						varB.add(var2);
						operators.add("==");
					}
				}
			}
		}
		
		return buggyExps;
	}

	private String generatedFix(String var1, String var2, String op) {
		StringBuilder sb = new StringBuilder("java.util.Arrays.equals(");
		sb.append(var1);
		sb.append(',');
		sb.append(var2);
		sb.append(")");

		if (op.equals("!=")) {
			sb.insert(0, '!');
		}

		return sb.toString();
	}
}