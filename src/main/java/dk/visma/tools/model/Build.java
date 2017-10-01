package dk.visma.tools.model;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Version
 *
 */
@Entity
public class Build implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer major;
	private Integer minor;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Artifact getArtifact() {
		return artifact;
	}
	public List<Deploy> getDeploys() {
		return deploys;
	}

	private Integer revision;
	private Integer patch;
	private String version;
	private Boolean isSnapshot;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="artifact_id")
	private Artifact artifact;
	
	@OneToMany(mappedBy="build")
	private List<Deploy>deploys;

	public Build() {
		super();
	}
	public Build(String version) {
		this();
		this.version=version;
		
		int n=version.indexOf("-SNAPSHOT");
		isSnapshot=(n>0 && n==version.length()-9); 
		
		String str=(n>0)?version.substring(0, n):version;
		String ver[]=str.split("\\.");
		if (ver.length>0){
			major=parseInteger(ver[0]);
		} 
		if (ver.length>1){
			minor=parseInteger(ver[1]);
		}
		if (ver.length>2){
			revision=parseInteger(ver[2]);
		}
		if (ver.length>3){
			patch=parseInteger(ver[3]);
		}

	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public Integer getMajor() {
		return this.major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}   
	public Integer getMinor() {
		return this.minor;
	}

	public void setMinor(Integer minor) {
		this.minor = minor;
	}   
	public Integer getRevision() {
		return this.revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}   
	public Integer getPatch() {
		return this.patch;
	}

	public void setPatch(Integer patch) {
		this.patch = patch;
	}   
	public Boolean getIsSnapshot() {
		return this.isSnapshot;
	}

	public void setIsSnapshot(Boolean isSnapshot) {
		this.isSnapshot = isSnapshot;
	}
	public void setArtifact(Artifact artifact) {
		this.artifact=artifact;
		
	}
	 
   @Override
   public boolean equals(Object obj) {
	   if (obj instanceof Build) {
		   return version.equals(((Build) obj).version);
	   }
	   return false;
   }

   @Override
   public int hashCode() {
       return  version.hashCode();
   }
	
	private Integer parseInteger(String number) {
		Integer.parseInt(number);
		
		return Integer.parseInt(number);
	}

}
