package dk.visma.tools.service.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import dk.visma.tools.model.Artifact;
import dk.visma.tools.model.Build;

public class DeploymentDescriptorTest {

	@Test
	public void testCreateVersion() {
		
		Build version=new Build("1.0.0-SNAPSHOT");
		
		assertEquals(true, version.getIsSnapshot());
		assertEquals(1, version.getMajor().intValue());
		assertEquals(0, version.getMinor().intValue());
		assertEquals(0, version.getRevision().intValue());
		assertNull(version.getPatch());
	}

}
