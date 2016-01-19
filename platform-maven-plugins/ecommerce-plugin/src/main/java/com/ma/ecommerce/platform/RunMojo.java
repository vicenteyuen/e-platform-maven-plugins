package com.ma.ecommerce.platform;

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
	
	@Parameter(property="eplatform.home")
	private String platformHomePath;
	

	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub
		
		this.getLog().info("Hello world , vison ruan ");

	}

}
