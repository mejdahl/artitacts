package dk.visma.tools.service.dao;

import java.time.ZonedDateTime;

import dk.visma.tools.model.Artifact;
import dk.visma.tools.model.Build;
import dk.visma.tools.model.Deploy;
import dk.visma.tools.model.Server;

public class DeploymentDescriptor {
	private String groupId;
	private String artifactId;
	private String version;
	private String serverId;
	private ZonedDateTime deployDate;
	
	
	public DeploymentDescriptor() {
		
	}
	
	public DeploymentDescriptor(String groupId, String artifactId, String version, String serverId) {
		this.groupId=groupId;
		this.artifactId=artifactId;
		this.version=version;
		this.serverId=serverId;
	}
	
	public DeploymentDescriptor(Artifact artifact) {		
		for (Build build : artifact.getBuilds()) {
			if (build.getDeploys().size()>0) {
				Deploy deploy=build.getDeploys().get(0);
				this.deployDate=deploy.getDeployedDate();
				this.version=build.getVersion();
				this.groupId=artifact.getGroupId();
				this.artifactId=artifact.getArtifactId();
				this.serverId=deploy.getServer().getServername();
			}
		}
	}

	public Build createBuild() {
		return new Build(version);
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Override
	public String toString() {
		return "DeploymentDescriptor [groupId=" + groupId + ", artifactId=" + artifactId + ", version=" + version
				+ ", serverId=" + serverId + "] "+ deployDate.toString();
	}
	
}
