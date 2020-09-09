package edu.lu.uni.serval.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtils {

    private static Logger log = LoggerFactory.getLogger(TestUtils.class);

	public static int getFailTestNumInProject(String projectName, String projectRootPath, List<String> failedTests){

        String result = null;

        try {
			// String buggyProject = projectName.substring(projectName.lastIndexOf("/") + 1);
			//which java\njava -version\n
            result = ShellUtils.shellRun(Arrays.asList(
                "cd " + projectRootPath + "\n",
                "java -cp ./target/test-classes:./target/classes:./libs/junit-4.12.jar:./libs/hamcrest-core-1.3.jar org.junit.runner.JUnitCore java_programs." + projectName + "_TEST"
            ), projectName).trim(); //"defects4j " + cmdType + "\n"));//
        } catch (IOException e){
        	e.printStackTrace();
            result = "";
        }

        // log.info(result);

        String lines[] = result.split("\n");
        if (lines.length < 1) {
            return Integer.MAX_VALUE;
        }

        int errorNum = Integer.MAX_VALUE;

        String testResult = lines[1];
        errorNum = testResult.length() - testResult.replace("E", "").length();

        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].matches("\\d+\\)\\s.*")) {
                String[] tokens = lines[i].split(" ");
                failedTests.add(tokens[1]);
            }
        }

        return errorNum;

        // String testResult = getDefects4jResult(projectName, defects4jPath, "test");
        // if (testResult.equals("")){//error occurs in run
        //     return Integer.MAX_VALUE;
        // }
        // if (!testResult.contains("Failing tests:")){
        //     return Integer.MAX_VALUE;
        // }
        // int errorNum = 0;
        // String[] lines = testResult.trim().split("\n");
        // for (String lineString: lines){
        //     if (lineString.startsWith("Failing tests:")){
        //         errorNum =  Integer.valueOf(lineString.split(":")[1].trim());
        //         if (errorNum == 0) break;
        //     } else if (lineString.startsWith("Running ")) {
        //     	continue;
        //     } else {
        //     	failedTests.add(lineString.trim());
        //     }
        // }
        // return errorNum;
	}
	
//	public static int getFailTestNumInProject(String buggyProject, List<String> failedTests, String classPath,
//			String testClassPath, String[] testCasesArray){
//		StringBuilder builder = new StringBuilder();
//		for (String testCase : testCasesArray) {
//			builder.append(testCase).append(" ");
//		}
//		String testCases = builder.toString();
//		
//		String testResult = "";
//		try {
//			testResult = ShellUtils.shellRun(Arrays.asList("java -cp " + PathUtils.buildClassPath(classPath, testClassPath)
//					+ " org.junit.runner.JUnitCore " + testCases), buggyProject);
//		} catch (IOException e) {
////			e.printStackTrace();
//		}
//		
//        if (testResult.equals("")){//error occurs in run
//            return Integer.MAX_VALUE;
//        }
//        if (!testResult.contains("Failing tests:")){
//            return Integer.MAX_VALUE;
//        }
//        int errorNum = 0;
//        String[] lines = testResult.trim().split("\n");
//        for (String lineString: lines){
//            if (lineString.startsWith("Failing tests:")){
//                errorNum =  Integer.valueOf(lineString.split(":")[1].trim());
//                if (errorNum == 0) break;
//            } else if (lineString.startsWith("Running ")) {
//            	break;
//            } else {
//            	failedTests.add(lineString);
//            }
//        }
//        return errorNum;
//	}
	
	public static int compileProjectWithDefects4j(String projectName, String defects4jPath) {
        String result = null;

        try {
			String buggyProject = projectName.substring(projectName.lastIndexOf("/") + 1);
			//which java\njava -version\n
            result = ShellUtils.shellRun(Arrays.asList(
                "cd " + defects4jPath + "\n",
                "mvn compile"
            ), buggyProject); //"defects4j " + cmdType + "\n"));//
        } catch (IOException e){
        	e.printStackTrace();
            result = "";
        }

        // log.info(result);

        String lines[] = result.split("\n");
        if (lines.length < 1) {
            return Integer.MAX_VALUE;
        }

        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("[ERROR]")) {
                return 1;
            }
        }

        return 0;

		// String compileResults = getDefects4jResult(projectName, defects4jPath, "compile");
		// String[] lines = compileResults.split("\n");
		// if (lines.length != 2) return 1;
        // for (String lineString: lines){
        // 	if (!lineString.endsWith("OK")) return 1;
        // }
		// return 0;
	}

	// private static String getDefects4jResult(String projectName, String defects4jPath, String cmdType) {
	// 	try {
	// 		String buggyProject = projectName.substring(projectName.lastIndexOf("/") + 1);
	// 		//which java\njava -version\n
    //         String result = ShellUtils.shellRun(Arrays.asList("cd " + projectName + "\n", defects4jPath + "framework/bin/defects4j " + cmdType + "\n"), buggyProject);//"defects4j " + cmdType + "\n"));//
    //         return result.trim();
    //     } catch (IOException e){
    //     	e.printStackTrace();
    //         return "";
    //     }
	// }

	public static String recoverWithGitCmd(String projectName) {
		try {
			String buggyProject = projectName.substring(projectName.lastIndexOf("/") + 1);
            ShellUtils.shellRun(Arrays.asList("cd " + projectName + "\n", "git checkout -- ."), buggyProject);
            return "";
        } catch (IOException e){
            return "Failed to recover.";
        }
	}

	public static String readPatch(String projectName) {
		try {
			String buggyProject = projectName.substring(projectName.lastIndexOf("/") + 1);
            return ShellUtils.shellRun(Arrays.asList("cd " + projectName + "\n", "git diff"), buggyProject).trim();
        } catch (IOException e){
            return null;
        }
	}

}
