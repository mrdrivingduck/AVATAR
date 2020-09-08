package edu.lu.uni.serval.config;

public class Configuration {
	
	/*
	 * Data path of Defects4J bugs.
	 */
	public static final String BUGGY_PROJECTS_PATH = "/home/mrdrivingduck/Desktop/bearData/";
	
	public static final String TEMP_FILES_PATH = ".temp/";
	public static final long SHELL_RUN_TIMEOUT = 300L;
	
	public static String knownBugPositions = "BugPosition/D4jPatchPosition.txt";
	public static String suspPositionsFilePath = "/home/mrdrivingduck/Desktop/FL-VS-APR/FaultLocalization/GZoltar-0.1.1/SuspiciousCodePositions/";
	public static String failedTestCasesFilePath = "FailedTestCases/";
	public static String faultLocalizationMetric = "Ochiai";
	public static String outputPath = "OUTPUT/";

}
