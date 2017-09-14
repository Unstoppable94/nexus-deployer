package com.wingarden.nexus;

import java.io.File;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.internal.Lists;
import com.wingarden.nexus.converter.MyFileconverter;

@Parameters(separators = "=")
public class DeployArgs {
	@Parameter
	public List<String> artiftDirs = Lists.newArrayList();

	@Parameter(names = "-f", converter = MyFileconverter.class, description = "artifact配置文件，文件中每一行标示一个artifact文件的绝对路径。当不指定该文件时，需要指定artifact的库目录，如：nexusdeploy -u user:pwd -repo repourl artifact_dir1 artifact_dir2,系统将从artifact_dir1和artifact_dir2两个目录中扫描artifact文件。需要注意的是：artifact_dir必须是工作目录下的子目录。")
	public File configFile;

	@Parameter(names = "-b", converter = MyFileconverter.class, description = "工作目录,只会上传该工作目录下artifact,当不指定该参数时，工作目录默认为执行程序所处的目录。")
	public File baseDir;

	@Parameter(names = "-repo", description = "nexus repository url,如：http://192.168.101.93:8081/repository/thirdparty/", required = true)
	public String repoUrl;

	@Parameter(names = "-u", description = "认证信息：userid:password", required = true)
	public String upwd;

	@Parameter(names = "-h", help = true)
	public boolean help;

	@Override
	public String toString() {
		return "DeployArgs [artiftDirs=" + artiftDirs + ", configFile="
				+ (null == configFile ? "" : configFile.getAbsolutePath()) + ", baseDir="
				+ (null == baseDir ? "" : baseDir.getAbsolutePath()) + ", repoUrl=" + repoUrl + ", upwd=" + upwd
				+ ", help=" + help + "]";
	}

}
