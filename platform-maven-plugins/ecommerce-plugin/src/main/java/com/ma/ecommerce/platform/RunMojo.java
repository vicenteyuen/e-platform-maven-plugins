package com.ma.ecommerce.platform;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @goal run
 * @author ruanweibiao
 *
 */
@Mojo( name = "run")
public class RunMojo extends AbstractMojo {
	
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
	 * @parameter expression="${run.platformHome}"
	 */	
	@Parameter(property="platformHome" , required=true)
	private File platformHome;


	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub
		
		// --- create ofbiz handle ---
		
		
		this.getLog().info("Hello world , vison ruan " + platformHome + " ok ");
		this.getLog().info("sourceDirectory : " + sourceDirectory);

	}

}
