package dk.visma.tools.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Deployment
 *
 */
@Entity
public class Deploy implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="build_id")
	private Build build;

	public Build getBuild() {
		return build;
	}

	public void setBuild( Build build) {
		this.build = build;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="server_id")
	private Server server;
	private ZonedDateTime deployedDate;
	private Boolean active;

	public Deploy() {
		super();
		deployedDate=ZonedDateTime.now();
	}

	
	
	public ZonedDateTime getDeployedDate() {
		return this.deployedDate;
	}

	public void setDeployedDate(ZonedDateTime deployedDate) {
		this.deployedDate = deployedDate;
	}

}
