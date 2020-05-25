package edu.lu.uni.serval.fixpattern.findbugs;

import java.util.ArrayList;
import java.util.List;

import edu.lu.uni.serval.fixpattern.FixTemplate;
import edu.lu.uni.serval.jdt.tree.ITree;

/**
 * 
 * 
 * @author Mr Dk.
 */
public class NPNullOnSomePathException extends FixTemplate {

	/*
	 * + if (input/outputStream != null) {
	 *     input/outputStream.close();
	 * + }
	 */
	@Override
	public void generatePatches() {
		ITree suspCodeAst = this.getSuspiciousCodeTree();
		List<String> allSuspVariables_ = identifySuspiciousVariables(suspCodeAst);
		List<String> allSuspVariables = new ArrayList<>();
		for (String var : allSuspVariables_) {
			if (!allSuspVariables.contains(var)) {
				allSuspVariables.add(var);
			}
		}
		allVarNamesMap = readAllVariableNames(suspCodeAst, varTypesMap, allVarNamesList);
		
		for (String varName : allSuspVariables) {
			
			String varType = varTypesMap.get(varName);
			if (!isIOStream(varType) && !isReaderWriter(varType)) {
				continue;
			}
			
			if (!suspCodeAst.getLabel().replace(" ", "").contains(varName + ".close()")) {
				continue;
			}
			if (suspCodeAst.getLabel().replace(" ", "").contains(varName + "!=null") ||
				suspCodeAst.getLabel().replace(" ", "").contains(varName + "==null")) {
				continue;
			}

			String fixedCodeStr1 = "if (" + varName + " != null) { ";
			String fixedCodeStr2 = " } ";
			int suspCodeEndPos = identifyRelatedStatements(suspCodeAst, varName);
			this.generatePatch(suspCodeStartPos, suspCodeEndPos, fixedCodeStr1, fixedCodeStr2);
		}
	}

	private boolean isIOStream(String varType) {
		if (varType.equals("FileInputStream") || varType.equals("PipedInputStream") ||
			varType.equals("FilterInputStream") || varType.equals("ByteArrayInputStream") ||
			varType.equals("SequenceInputSteam") || varType.equals("StringBufferInputStream") ||
			varType.equals("ObjectInputStream") || varType.equals("LineNumberInputStream") ||
			varType.equals("DataInputStream") || varType.equals("BufferedInputStream") ||
			varType.equals("PushbackInputStream")) {
			return true;
		}
		if (varType.equals("FileOutputStream") || varType.equals("FilterOutputStream") ||
			varType.equals("ObjectOutputStream") || varType.equals("PipedOutputStream") ||
			varType.equals("ByteArrayOutputStream") || varType.equals("BufferedOutputStream") ||
			varType.equals("DataOutputStream") || varType.equals("PrintStream")) {
			return true;
		}
		return false;
	}

	private boolean isReaderWriter(String varType) {
		if (varType.equals("BufferedReader") || varType.equals("InputStreamReader") ||
			varType.equals("StringReader") || varType.equals("PipedReader") ||
			varType.equals("CharArrayReader") || varType.equals("FilterReader") ||
			varType.equals("FileReader") || varType.equals("PushbackReader")) {
			return true;
		}
		if (varType.equals("BufferedWriter") || varType.equals("OutputStreamWriter") ||
			varType.equals("PrintWriter") || varType.equals("StringWriter") ||
			varType.equals("PipedWriter") || varType.equals("CharArrayWriter") ||
			varType.equals("FilterWriter") || varType.equals("FileWriter")) {
			return true;
		}
		return false;
	}
}
