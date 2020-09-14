package edu.lu.uni.serval.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

	public static int getFailTestNumInProject(String projectName, String defects4jPath, List<String> failedTests){

        String result = null;

        try {
			String buggyProject = projectName.substring(projectName.lastIndexOf("/") + 1);
            //which java\njava -version\n
            ShellUtils.shellRun(Arrays.asList(
                "cd " + projectName + " \n",
                "mvn test -V -B -Denforcer.skip=true -Dcheckstyle.skip=true -Dcobertura.skip=true -DskipITs=true -Drat.skip=true -Dlicense.skip=true -Dfindbugs.skip=true -Dgpg.skip=true -Dskip.npm=true -Dskip.gulp=true -Dskip.bower=true > log"
            ), buggyProject);//"defects4j " + cmdType + "\n"));//
            result = FileHelper.readFile(projectName + "/log");
        } catch (IOException e){
        	e.printStackTrace();
            result = "";
        }

        // log.info("Result: " + result);

        String lines[] = result.split("\n");
        if (lines.length < 1) {
            return Integer.MAX_VALUE;
        }

        int res = 0;

        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("Tests run:")) {
                String[] tokens = lines[i].split(" ");
                if (tokens.length == 9) {
                    int failure = Integer.parseInt(tokens[4].substring(0, tokens[4].length() - 1));
                    int error = Integer.parseInt(tokens[6].substring(0, tokens[6].length() - 1));
                    res += failure + error;
                } /*else {
                    int failure = Integer.parseInt(tokens[4].substring(0, tokens[4].length() - 1));
                    int error = Integer.parseInt(tokens[6].substring(0, tokens[6].length() - 1));
                    if (failure + error > 0) {
                        failedTests.add(lines[i - 1].split(" ")[1]);
                    }
                }*/
            } else if (lines[i].startsWith("Failed tests:")) {
                if (lines[i].trim().length() > "Failed tests:".length()) {
                    failedTests.add(lines[i].substring("Failed tests:   ".length()));
                }
                int j = i + 1;
                while (lines[j].startsWith("  ") && j < lines.length) {
                    failedTests.add(lines[j].trim());
                    j++;
                }
            } else if (lines[i].startsWith("Tests in error:")) {
                if (lines[i].trim().length() > "Tests in error:".length()) {
                    failedTests.add(lines[i].substring("Tests in error:   ".length()));
                }
                int j = i + 1;
                while (lines[j].startsWith("  ") && j < lines.length) {
                    failedTests.add(lines[j].trim());
                    j++;
                }
            }
        }

        return res;

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
                "cd " + projectName + " \n",
                "mvn install -V -B -DskipTests=true -Denforcer.skip=true -Dcheckstyle.skip=true -Dcobertura.skip=true -DskipITs=true -Drat.skip=true -Dlicense.skip=true -Dfindbugs.skip=true -Dgpg.skip=true -Dskip.npm=true -Dskip.gulp=true -Dskip.bower=true"
            ), buggyProject).trim();//"defects4j " + cmdType + "\n"));//
            // result = FileHelper.readFile(projectName + "/log");
        } catch (IOException e){
        	e.printStackTrace();
            result = "";
        }

        // log.info("Result: " + result);

        String lines[] = result.split("\n");
        if (lines.length < 1) {
            return 1;
        }

        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].contains("[ERROR]")) {
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
