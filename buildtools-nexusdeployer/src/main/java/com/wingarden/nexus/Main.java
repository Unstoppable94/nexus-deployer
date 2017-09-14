package com.wingarden.nexus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.beust.jcommander.JCommander;

public class Main {
	public static void main(String[] args) {
		DeployArgs deployArgs = new DeployArgs();
		JCommander jCommander = JCommander.newBuilder().addObject(deployArgs).build();
		jCommander.parse(args);
		
		if (deployArgs.help) {
			jCommander.usage();
			return ;
		}
		
		List<File> artifacts = new ArrayList<>();
		if (null != deployArgs.configFile && deployArgs.configFile.exists() && deployArgs.configFile.isFile()) {
			artifacts.addAll(getConfigFiles(deployArgs.configFile));
		}
		if (null == deployArgs.baseDir || !deployArgs.baseDir.exists()) {
			System.out.println("baseDir is not set,use the default path");
			deployArgs.baseDir = new File("");
		}

		if (null != deployArgs.artiftDirs) {
			for (String dirPath : deployArgs.artiftDirs) {
				File dir = new File(dirPath);
				if (dir.exists() && dir.isDirectory()) {
					artifacts.addAll(FileUtils.listFiles(dir, null, true));
				}
			}
		}
		
		System.out.println("Source artifacts:" + artifacts.toString());
		deploy(artifacts, deployArgs);
		System.out.println("finished!");
	}

	private static List<File> getConfigFiles(File configFile) {
		List<File> files = new ArrayList<>();
		try {
			List<String> paths = FileUtils.readLines(configFile);
			for (String filePath : paths) {
				File file = new File(filePath);
				if (file.exists() && file.isFile()) {
					files.add(file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}

	private static void deploy(List<File> artifacts, DeployArgs args) {
		System.err.println(args);
		ArtifactDeployer deployer = new ArtifactDeployer();
		String[] upwd = args.upwd.split(":");
		if (upwd.length != 2) {
			System.out.println("Should be format:-u userid:password,but -u " + upwd);
			return ;
		}
		deployer.setPasswd(upwd[1]);
		deployer.setUserName(upwd[0]);
		deployer.setRepoUrl(args.repoUrl);
		
		if (null == artifacts || artifacts.size() < 1) {
			System.out.println("No artifacts to deploy!!");
			return;
		}
		
		for (File artifact : artifacts) {
			if (artifact.exists() && artifact.getAbsolutePath().startsWith(args.baseDir.getAbsolutePath())) {
				try {
					deployer.deploy(args.baseDir.getAbsolutePath(), artifact);
				} catch (Exception e) {
					System.out.println(String.format("Artifact [] deploy Error!", artifact.getAbsolutePath()));
				}
			}
		}
	}
}
