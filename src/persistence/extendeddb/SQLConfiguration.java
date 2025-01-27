/**
 * 
 */
package persistence.extendeddb;

/**
 * SQLConfiguration class
 * 
 * Used to define the configuration of the ExtendedDatabaseAPI.
 */
public class SQLConfiguration {
	private String system;
	private String host;
	private String base;
	private String user;
	private String password;
	
	/**
	 * SQLConfiguration constructor
	 */
	public SQLConfiguration() {
		
	}
	
	/**
	 * SQLConfiguration constructor
	 * 
	 * @param system   The type of database.
	 * @param host     The name of the server hosting the database.
	 * @param base     The name of the database.
	 * @param user     A user of the database.
	 * @param password Its password.
	 */
	public SQLConfiguration(String system, String host, String base, String user, String password) {
		this.system = system;
		this.host = host;
		this.base = base;
		this.user = user;
		this.password = password;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		if (system != null || host != null || base != null) {
			return "jdbc:" + system + "://" + host + "/" + base;
		} else {
			return null;
		}
	}
}
