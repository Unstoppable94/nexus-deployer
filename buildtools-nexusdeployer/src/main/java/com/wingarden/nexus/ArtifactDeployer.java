package com.wingarden.nexus;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ArtifactDeployer {
	Logger logger = LoggerFactory.getLogger(ArtifactDeployer.class);

	private String userName;
	private String passwd;
	private String repoUrl;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public void deploy(String basePath, File artifact) throws Exception{
		logger.info("begin to deploy artifact [{}] to nexus!",artifact.getAbsolutePath());
		logger.info("[basePath] {}",basePath);
		String artifactPath = artifact.getAbsolutePath().replace(basePath, "");
		if (artifactPath.startsWith(File.separator)) {
			artifactPath = artifactPath.substring(1);
		}
		if (File.separator.equals("\\")) {
			artifactPath = artifactPath.replaceAll("\\\\", "/");
		}
		
		if (!repoUrl.endsWith("/")) {
			repoUrl += "/";
		}
		String uploadUrl = this.repoUrl + artifactPath;
		logger.info("[uploadurl] {}",uploadUrl);
		HttpResponse<String> jsonResponse = Unirest.put(uploadUrl).basicAuth(this.userName, this.passwd)
				.field("file", artifact).asString();
		logger.info("[{}] [{}]",jsonResponse.getStatus(),jsonResponse.getStatusText());
	}
}
