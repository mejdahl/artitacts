package dk.visma.tools.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import dk.visma.tools.service.dao.DeploymentDescriptor;

@RunWith(Arquillian.class)
public class ArtifactServiceBeanIT {
	@EJB ArtifactServiceBean service;
	
	@Deployment
	public static Archive<?> createDeployment() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class)
				.addClasses(ArtifactServiceBean.class, DeploymentDescriptor.class)
				.addPackage("dk.visma.tools.model")
			    .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		// System.out.println(archive.toString(true));
		return archive;
	}

	@Test
	public void test() throws InterruptedException {
		service.deployArtifact(new DeploymentDescriptor("dk.test", "test", "1.0.2", "prod"));
		TimeUnit.SECONDS.sleep(1);
		service.deployArtifact(new DeploymentDescriptor("dk.test", "test", "1.0.0", "prod"));
		service.deployArtifact(new DeploymentDescriptor("dk.test", "test", "1.0.1", "prod"));
		TimeUnit.SECONDS.sleep(1);
		service.deployArtifact(new DeploymentDescriptor("dk.test", "test", "1.0.3", "prod"));		
		service.deployArtifact(new DeploymentDescriptor("dk.test", "test2", "1.0.0", "prod"));
		TimeUnit.SECONDS.sleep(1);
		service.deployArtifact(new DeploymentDescriptor("dk.test", "test", "1.0.4", "prod"));
		
		List<DeploymentDescriptor> deployments=service.lookupServerDeployment("prod");
		
		deployments.forEach( desc -> System.out.println(desc.toString()));
		
		assertEquals(2, deployments.size());
	}

}
