package com.ma.ecommerce.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @goal comp-package
 * @author ruanweibiao
 *
 */
@Mojo( name = "comp-package")
public class CompnentPackageMojo extends AbstractMojo {
	
	/**
	 * @parameter expression="${project.basedir}"
	 * @required
	 * @readonly
	 */
	private File basedir;
	
	/**
	 * @parameter expression="${project.build.sourceDirectory}"
	 * @readonly
	 */	
	private File  sourceDirectory;
	
	
	/**
	 * @parameter expression="${project.build.directory}"
	 * @readonly
	 */		
	private File targetDirectory;
	
    /** 
     * @parameter expression="${project.resources}" 
     * @readonly 
     */ 	
	private List<Resource> resources;	
	
	
    /** 
     * @parameter expression="${project.testResources}" 
     * @readonly 
     */ 	
	private List<Resource> testResources;
	
	/**
	 * @parameter expression="${basedir}/src/main/webapp"
	 * @readonly
	 */		
	private File warSourceDirectory;
	
	
	/**
	 * @parameter expression="${run.platformHome}"
	 */	
	@Parameter(property="platformHome" , required=true)
	private File platformHome;


	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub
		
		// --- check ofbiz location integrity ---
		boolean pass = checkPlatformFolder(platformHome);
		
		// --- build tmp folder ---
		File confFile = new File(basedir , "conf.json");
		
		try {
			String content = IOUtils.toString(new FileInputStream(confFile));
			
			JSONObject jsonObj = JSON.parseObject(content);
			
			String compModType = (String)jsonObj.get("component-module-type");
			
			String compLoc = (String)jsonObj.get("component-location");
			
			
			// --- create tmp folder ---
			File tmpCompLoc = new File(targetDirectory , compLoc);
			if (tmpCompLoc.exists()) {
				FileUtils.deleteDirectory( tmpCompLoc );	
			}
			tmpCompLoc.mkdirs();			

			
			// --- copy resource folder ---
			for (Resource res : resources) {
				String fs = res.getDirectory();
				FileUtils.copyDirectory( new File(fs) , tmpCompLoc);
			}
			
			// ---- copy war project ---
			if (warSourceDirectory.exists()) {
				
				File folder = new File(tmpCompLoc, "webapp");
				if (!folder.exists() ) {
					folder.mkdirs();
				}
				FileUtils.copyDirectory( warSourceDirectory , folder);
			}
			
			
			// --- copy folder to platform ---
			File hotDeployDir = new File(platformHome,compModType);
			File practiceDir = new File(hotDeployDir , compLoc);
			
			if (practiceDir.exists()) {
				FileUtils.deleteDirectory( practiceDir );					
			}
			practiceDir.mkdirs();
			
			// ---create folder name ---
			FileUtils.copyDirectory( tmpCompLoc , practiceDir);
			


			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException( e.getLocalizedMessage() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException( e.getLocalizedMessage() );
		}


		this.getLog().info("package : " + warSourceDirectory);

	}
	
	
	
	
	
	
	private boolean checkPlatformFolder(File platformHome) throws MojoExecutionException{
		
		boolean passed = true;
		if (!platformHome.exists()) {
			passed = false;
			throw new MojoExecutionException("Platform directory is not exist. please check");
		}
		
		File hotDeployDir = new File(platformHome,"hot-deploy");
		if (!hotDeployDir.exists()) {
			passed = false;
			throw new MojoExecutionException("\"Hot deploy\" directory is not existd. please check");
		}
		
		File applicationsDir = new File(platformHome,"applications");
		if (!applicationsDir.exists()) {
			passed = false;
			throw new MojoExecutionException("\"Applications\" directory is not exist. please check");
		}		
				
		File frameworkDir = new File(platformHome,"framework");
		if (!frameworkDir.exists()) {
			passed = false;
			throw new MojoExecutionException("\"Framework\" directory is not exist. please check");
		}
		
		File themesDir = new File(platformHome,"themes");
		if (!themesDir.exists()) {
			passed = false;
			throw new MojoExecutionException("\"Themes\" directory is not exist. please check");
		}		
		
		return passed;
	}
	

}
