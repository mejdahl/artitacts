package dk.visma.tools.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Artifact
 *
 */
@Entity
public class Artifact implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String groupId;
	private String artifactId;
	private String description;
	@OneToMany(mappedBy="artifact")
	private Set<Build>builds;
	
	public Set<Build> getBuilds() {
		return builds;
	}
	public Artifact() {
		super();
	}   
	public Artifact(String groupId, String artifactId) {
		this();
		this.groupId = groupId;
		this.artifactId = artifactId;
	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}   
	public String getArtifactId() {
		return this.artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public void addBuild(Build build) {
		if (builds==null) {
			builds=new HashSet<Build>();
		}
		builds.add(build);
		build.setArtifact(this);
	}
	public Optional<Build> findBuild(Build build) {
		return builds.stream().filter(arg -> arg.equals(build)).findFirst();
	}
   
	public boolean equals(Object obj) {
		   if (obj instanceof Artifact) {
			   Artifact artifact=(Artifact)obj;
			   return artifact.artifactId.equals(artifactId) && artifact.groupId.equals(groupId );
		   }
		   return false;
	   }

	   @Override
	   public int hashCode() {
	       return  artifactId.hashCode() + groupId.hashCode();
	   }

}
