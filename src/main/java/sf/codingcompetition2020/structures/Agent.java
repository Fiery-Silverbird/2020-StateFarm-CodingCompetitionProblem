package sf.codingcompetition2020.structures;

public class Agent {
	
	private int agentId;
	private String area;
	private String language;
	private String firstName;
	private String lastName;

	public Agent(int agentId, String area, String language, String firstName, String lastName) {
		this.agentId = agentId;
		this.area = area;
		this.language = language;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public int getAgentId() {
		return agentId;
	}
}
