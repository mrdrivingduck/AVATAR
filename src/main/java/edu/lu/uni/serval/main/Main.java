package edu.lu.uni.serval.main;

import java.io.File;

import edu.lu.uni.serval.avatar.AbstractFixer;
import edu.lu.uni.serval.avatar.Avatar;
import edu.lu.uni.serval.config.Configuration;

/**
 * Fix bugs with Fault Localization results.
 * 
 * @author kui.liu
 *
 */
public class Main {
	
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Arguments: <Buggy_Project_Path> <defects4j_Path> <Bug_ID> <FL_Metric>");
			System.exit(0);
		}
		String buggyProjectsPath = args[0];// "../Defects4JData/"
		String defects4jPath = args[1]; // "../defects4j/"
		String projectName = args[2]; // "Chart_1"
		Configuration.faultLocalizationMetric = args[3];
		Configuration.outputPath += "FL/";
		System.out.println(projectName);
		fixBug(buggyProjectsPath, defects4jPath, projectName);
	}

	public static void fixBug(String buggyProjectsPath, String defects4jPath, String buggyProjectName) {
		String suspiciousFileStr = Configuration.suspPositionsFilePath;
		
		String dataType = "AVATAR";
		String[] elements = buggyProjectName.split("-");
		String projectName = elements[0];
		String bugId = elements[1];
		// try {
		// 	bugId = Integer.valueOf(elements[1]);
		// } catch (NumberFormatException e) {
		// 	System.err.println("Please input correct buggy project ID, such as \"Chart_1\".");
		// 	return;
		// }
		
		AbstractFixer fixer = new Avatar(buggyProjectsPath, projectName, bugId, defects4jPath);
		fixer.metric = Configuration.faultLocalizationMetric;
		fixer.dataType = dataType;
		fixer.suspCodePosFile = new File(suspiciousFileStr);
		if (Integer.MAX_VALUE == fixer.minErrorTest) {
			System.out.println("Failed to defects4j compile bug " + buggyProjectName);
			return;
		}
		
		fixer.fixProcess();
		
		int fixedStatus = fixer.fixedStatus;
		switch (fixedStatus) {
		case 0:
			System.out.println("Failed to fix bug " + buggyProjectName);
			break;
		case 1:
			System.out.println("Succeeded to fix bug " + buggyProjectName);
			break;
		case 2:
			System.out.println("Partial succeeded to fix bug " + buggyProjectName);
			break;
		}
	}

}
