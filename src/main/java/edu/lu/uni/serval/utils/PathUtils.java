package edu.lu.uni.serval.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class PathUtils {

	public static ArrayList<String> getSrcPath(String bugProject) {
		ArrayList<String> path = new ArrayList<String>();
		String[] words = bugProject.split("-");
		String projectName = words[0];
		String bugID = words[1];

		if (projectName.equals("ACCUMULO")) {
			path.add("/src/core/target/classes/");
			path.add("/src/core/target/test-classes/");
			path.add("/src/core/src/main/java/");
			path.add("/src/core/src/test/java/");
		} else if (projectName.equals("FLINK")) {
			if (bugID.equals("2817_5dfc897b")) {
				path.add("/flink-staging/flink-streaming/flink-streaming-core/target/classes/");
				path.add("/flink-staging/flink-streaming/flink-streaming-core/target/test-classes/");
				path.add("/flink-staging/flink-streaming/flink-streaming-core/src/main/java/");
				path.add("/flink-staging/flink-streaming/flink-streaming-core/src/test/java/");
			} else if (bugID.equals("3011_a402002d")) {
				path.add("/flink-runtime/target/classes/");
				path.add("/flink-runtime/target/test-classes/");
				path.add("/flink-runtime/src/main/java/");
				path.add("/flink-runtime/src/test/java/");
			} else if (bugID.equals("3189_a5b05566")) {
				path.add("/src/core/target/classes/");
				path.add("/src/core/target/test-classes/");
				path.add("/flink-clients/src/main/java/");
				path.add("/flink-clients/src/test/java/");
			} else {
				path.add("/src/core/target/classes/");
				path.add("/src/core/target/test-classes/");
				path.add("/flink-core/src/main/java/");
				path.add("/flink-core/src/test/java/");
			}
		} else if (projectName.equals("LOG4J2")) {
			if (bugID.equals("114_afcf92eb") || bugID.equals("127_029e79da")) {
				path.add("/api/target/classes/");
				path.add("/api/target/test-classes/");
				path.add("/api/src/main/java/");
				path.add("/api/src/test/java/");
			} else if (bugID.equals("813_0bea17d7")) {
				path.add("/log4j-api/target/classes/");
				path.add("/log4j-api/target/test-classes/");
				path.add("/log4j-api/src/main/java/");
				path.add("/log4j-api/src/test/java/");
			} else if (bugID.equals("1061_86d8944f") || bugID.equals("1062_4cf831b6")) {
				path.add("/log4j-slf4j-impl/target/classes/");
				path.add("/log4j-slf4j-impl/target/test-classes/");
				path.add("/log4j-slf4j-impl/src/main/java/");
				path.add("/log4j-slf4j-impl/src/test/java/");
			} else {
				path.add("/log4j-core/target/classes/");
				path.add("/log4j-core/target/test-classes/");
				path.add("/log4j-core/src/main/java/");
				path.add("/log4j-core/src/test/java/");
			}
		} else if (projectName.equals("MATH")) {
			path.add("/target/classes/");
			path.add("/target/test-classes/");
			path.add("/src/main/java/");
			path.add("/src/test/java/");
		} else if (projectName.equals("OAK")) {
			if (bugID.equals("738_8ed779dc") || bugID.equals("3517_24f7f60a")) {
				path.add("/oak-jcr/target/classes/");
				path.add("/oak-jcr/target/test-classes/");
				path.add("/oak-jcr/src/main/java/");
				path.add("/oak-jcr/src/test/java/");
			} else if (bugID.equals("3367_06812d25")) {
				path.add("/oak-lucene/target/classes/");
				path.add("/oak-lucene/target/test-classes/");
				path.add("/oak-lucene/src/main/java/");
				path.add("/oak-lucene/src/test/java/");
			} else {
				path.add("/oak-core/target/classes/");
				path.add("/oak-core/target/test-classes/");
				path.add("/oak-core/src/main/java/");
				path.add("/oak-core/src/test/java/");
			}
		} else {
			path.add("/build/");
			path.add("/build-tests/");
			path.add("/source/");
			path.add("/tests/");
		}

		// if (projectName.equals("Chart")) {
		// 	path.add("/build/");
		// 	path.add("/build-tests/");
		// 	path.add("/source/");
		// 	path.add("/tests/");
		// } else if (projectName.equals("Cli")) {
		// 	if (bugId <= 29) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/java/");
		// 		path.add("/src/test/");
		// 	} else {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/main/java/");
		// 		path.add("/src/test/java/");
		// 	}
		// } else if (projectName.equals("Closure")) {
		// 	path.add("/build/classes/");
		// 	path.add("/build/test/");
		// 	path.add("/src/");
		// 	path.add("/test/");
		// } else if (projectName.equals("Codec")) {
		// 	if (bugId <= 10) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/tests/");
		// 		path.add("/src/java/");
		// 		path.add("/src/test/");
		// 	} else if (bugId <= 16) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/tests/");
		// 		path.add("/src/main/java/");
		// 		path.add("/src/test/java/");
		// 	} else {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/main/java/");
		// 		path.add("/src/test/java/");
		// 	}
		// } else if (projectName.equals("Collections")) {
		// 	path.add("/target/classes/");
		// 	path.add("/target/tests/");
		// 	path.add("/src/main/java/");
		// 	path.add("/src/test/java/");
		// } else if (projectName.equals("Compress")) {
		// 	path.add("/target/classes/");
		// 	path.add("/target/test-classes/");
		// 	path.add("/src/main/java/");
		// 	path.add("/src/test/java/");
		// } else if (projectName.equals("Csv")) {
		// 	path.add("/target/classes/");
		// 	path.add("/target/test-classes/");
		// 	path.add("/src/main/java/");
		// 	path.add("/src/test/java/");
		// } else if (projectName.equals("Gson")) {
		// 	path.add("/target/classes/");
		// 	path.add("/target/test-classes/");
		// 	path.add("/gson/src/main/java/");
		// 	path.add("/gson/src/test/java/");
		// } else if (projectName.equals("JacksonCore")) {
		// 	path.add("/target/classes/");
		// 	path.add("/target/test-classes/");
		// 	path.add("/src/main/java/");
		// 	path.add("/src/test/java/");
		// } else if (projectName.equals("JacksonDatabind")) {
		// 	path.add("/target/classes/");
		// 	path.add("/target/test-classes/");
		// 	path.add("/src/main/java/");
		// 	path.add("/src/test/java/");
		// } else if (projectName.equals("JacksonXml")) {
		// 	path.add("/target/classes/");
		// 	path.add("/target/test-classes/");
		// 	path.add("/src/main/java/");
		// 	path.add("/src/test/java/");
		// } else if (projectName.equals("Jsoup")) {
		// 	path.add("/target/classes/");
		// 	path.add("/target/test-classes/");
		// 	path.add("/src/main/java/");
		// 	path.add("/src/test/java/");
		// } else if (projectName.equals("JxPath")) {
		// 	path.add("/target/classes/");
		// 	path.add("/target/test-classes/");
		// 	path.add("/src/java/");
		// 	path.add("/src/test/");
		// } else if (projectName.equals("Lang")) {
		// 	if (bugId <= 20) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/tests/");
		// 		path.add("/src/main/java/");
		// 		path.add("/src/test/java/");
		// 	} else if (bugId >= 21 && bugId <= 35) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/main/java/");
		// 		path.add("/src/test/java/");
		// 	} else if (bugId >= 36 && bugId <= 41) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/java/");
		// 		path.add("/src/test/");
		// 	} else if (bugId >= 42 && bugId <= 65) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/tests/");
		// 		path.add("/src/java/");
		// 		path.add("/src/test/");
		// 	}
		// } else if (projectName.equals("Math")) {
		// 	if (bugId <= 84) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/main/java/");
		// 		path.add("/src/test/java/");
		// 	} else {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/java/");
		// 		path.add("/src/test/");
		// 	}
		// } else if (projectName.equals("Mockito")) {
		// 	if (bugId <= 11) {
		// 		path.add("/build/classes/main/");
		// 		path.add("/build/classes/test/");
		// 		path.add("/src/");
		// 		path.add("/test/");
		// 	} else if (bugId <= 17) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/");
		// 		path.add("/test/");
		// 	} else if (bugId <= 21) {
		// 		path.add("/build/classes/main/");
		// 		path.add("/build/classes/test/");
		// 		path.add("/src/");
		// 		path.add("/test/");
		// 	} else {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/");
		// 		path.add("/test/");
		// 	}
		// } else if (projectName.equals("Time")) {
		// 	if (bugId <= 11) {
		// 		path.add("/target/classes/");
		// 		path.add("/target/test-classes/");
		// 		path.add("/src/main/java/");
		// 		path.add("/src/test/java/");
		// 	} else {
		// 		path.add("/build/classes/");
		// 		path.add("/build/tests/");
		// 		path.add("/src/main/java/");
		// 		path.add("/src/test/java/");
		// 	}
		// }

		return path;
	}

	public static String getJunitPath() {
		return System.getProperty("user.dir")+"/target/dependency/junit-4.12.jar";
	}
	
	private static String getHamcrestPath() {
		return System.getProperty("user.dir")+"/target/dependency/hamcrest-all-1.3.jar";
	}

	public static String buildCompileClassPath(List<String> additionalPath, String classPath, String testClassPath){
		String path = "\"";
		path += classPath;
		path += System.getProperty("path.separator");
		path += testClassPath;
		path += System.getProperty("path.separator");
		path += JunitRunner.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		path += System.getProperty("path.separator");
		path += StringUtils.join(additionalPath,System.getProperty("path.separator"));
		path += "\"";
		return path;
	}
	
	public static String buildTestClassPath(String classPath, String testClassPath) {
		String path = "\"";
		path += classPath;
		path += System.getProperty("path.separator");
		path += testClassPath;
		path += System.getProperty("path.separator");
		path += JunitRunner.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		path += System.getProperty("path.separator");
	    path += getJunitPath();
	    path += System.getProperty("path.separator");
	    path += getHamcrestPath();
	    path += System.getProperty("path.separator");
		path += "\"";
		return path;
    }

}
