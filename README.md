# nexus-deployer
---------
## 概述
---
本工具用于向sonatype nexus3上传artifact文件。对于在[`mvrepository`](http://mvnrepository.com/)或者其它不位于任何第三方maven repository中的jar包，可以通过本工具上传至nexus3中，该类jar包应上传至[`thirdparty库`](http://192.168.101.93:8081/repository/thirdparty/)。

## 先决条件
---
* 可以访问sonatype nexus环境 http://192.168.101.93:8081

## 使用
---
### 编绎

    git clone http:/10.0.2.50:180/wingrow/nexus-deployer.git  ##下载源代码
    cd buildtools-nexusdeployer                               ##进入工程目录
    mvn clean package appassembler:assemble                   ##打包

    在target目录下生成nexusdeployer软件目录。

### 运行
	
	cd target/nexusdeployer/bin                               
	nexusdeployer -h ,输出如下信息：
		Usage: <main class> [options]
		  Options:
		    -b
		      工作目录,只会上传该工作目录下artifact,当不指定该参数时，工作目录默认为执行程序所处的目录。
		    -f
		      artifact配置文件，文件中每一行标示一个artifact文件的绝对路径。当不指定该文件时，需要指定artifact的库目录，如：nexusdeploy
		      -u user:pwd -repo repourl artifact_dir1 artifact_dir2,系统将从artifact_dir1和artifact_dir2两个目录中扫描artifact文件。需要注意的是：artifact_dir必须是工作目录下的子目录。
		    -h

		  * -repo
		      nexus repository url,如：http://192.168.101.93:8081/repository/thirdparty/
		  * -u
		      认证信息：userid:password

### 工程目录说明
* 本工程目录结构如下：

    ```java
    - nexus-deployer                        ##父工程，pom.xml中定义公共配置
      - buildtools-config                   ##通用配置
      - buildtools-nexusdeployer            ##nexusdeployer工程代码
    ```
