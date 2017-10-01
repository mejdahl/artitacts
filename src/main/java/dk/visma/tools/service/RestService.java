package dk.visma.tools.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import dk.visma.tools.model.Artifact;
import dk.visma.tools.model.Deploy;
import dk.visma.tools.service.dao.DeploymentDescriptor;

@Path("/artifact")
public class RestService {
	
	private static final Logger log = Logger.getLogger(RestService.class);
	
	@EJB ArtifactServiceBean service;
	
	
	@POST
	@Path("/deployment")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public void deployArtifact(DeploymentDescriptor deployment) {
		log.info("Deployed "+deployment.toString());
		
		service.deployArtifact(deployment);
	}
	
	@GET
	@Path("/deployments/{environment}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deploy> getDeoployList(String environment) {
		List<Deploy> deployments=new ArrayList<>();
		
		return deployments;
	}
}
