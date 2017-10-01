package dk.visma.tools.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import dk.visma.tools.model.Artifact;
import dk.visma.tools.model.Build;
import dk.visma.tools.model.Deploy;
import dk.visma.tools.model.Server;
import dk.visma.tools.service.dao.DeploymentDescriptor;

@Stateless
public class ArtifactServiceBean {
	private static final Logger log = Logger.getLogger(ArtifactServiceBean.class);
	 @PersistenceContext
	 private EntityManager em;
	
	public void deployArtifact(DeploymentDescriptor deployment) {
		//Artifact artifact=new Artifact();
	
		Build build=null;
		Artifact artifact=lookupArtifactVersions(deployment.getGroupId(), deployment.getArtifactId(), deployment.getVersion());
		if (artifact==null) {
			artifact=new Artifact(deployment.getGroupId(), deployment.getArtifactId());
			build=deployment.createBuild();
			artifact.addBuild(build);
			em.persist(artifact);	
			em.persist(build);
			em.flush();			
		} else {
			build=deployment.createBuild();
			
			Optional<Build> buildOpt=lookupBuild(artifact, deployment.createBuild());
			if (buildOpt.isPresent()) {
				build=buildOpt.get();
			} else {
				artifact.addBuild(build);
				em.persist(build);
				em.flush();
			}
		}
		
		Server server=lookupServer(deployment.getServerId());
		if (server==null) {
			server=new Server();
			server.setServername(deployment.getServerId());
			em.persist(server);
			em.flush();
		}
		
		Deploy deploy=new Deploy();
		deploy.setBuild(build);
		deploy.setServer(server);
		em.persist(deploy);
		em.flush();
	}

	
	private Optional<Build> lookupBuild(Artifact artifact, Build build) {
		Optional<Build> foundBuild=artifact.findBuild(build);
		return foundBuild;
	}


	private Artifact lookupArtifactVersions(String groupId, String artifactId, String version) {
		String sql="SELECT a FROM Artifact a WHERE a.groupId=:grpId AND a.artifactId=:artId";
		Query query = em.createQuery(sql);
		query.setParameter("grpId", groupId);
		query.setParameter("artId", artifactId);
		List<Artifact> artifacts=(List<Artifact>) query.getResultList();
		
		if (artifacts.size()==1)  {
			return artifacts.get(0);
		} else {
			return null;
		}
	}
	
	private Server lookupServer(String servername) {
		Query query = em.createQuery("SELECT s FROM Server s WHERE s.servername=:srvId");
		query.setParameter("srvId", servername);

		List<Server> servers=(List<Server>) query.getResultList();
		
		if (servers.size()==1)  {
			return servers.get(0);
		} 
		
		return null;
	}


	public List<DeploymentDescriptor> lookupServerDeployment(String servername) {
		String sql="SELECT distinct a FROM Artifact a "
					+ " JOIN FETCH a.builds b "		
					+ " JOIN FETCH b.deploys d "
					+ " JOIN FETCH d.server s "							
					+ "WHERE s.servername=:srvId"
					+ " order by d.deployedDate desc";
		
		Query query = em.createQuery(sql);
		query.setParameter("srvId", servername);
		
		List<Artifact> servers=(List<Artifact>) query.getResultList();
		
		List<DeploymentDescriptor> discript=servers.stream().map(
				new Function<Artifact, DeploymentDescriptor>() {
					@Override
					public DeploymentDescriptor apply(Artifact artifact) {
						return new DeploymentDescriptor(artifact);
					}
				}).collect(Collectors.toList());
		
		return discript;
	}

	
}
