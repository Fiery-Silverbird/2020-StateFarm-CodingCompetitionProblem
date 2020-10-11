package sf.codingcompetition2020;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import sf.codingcompetition2020.structures.*;

public class CodingCompCsvUtil {

	/* #1
	 * readCsvFile() -- Read in a CSV File and return a list of entries in that file.
	 * @param filePath -- Path to file being read in.
	 * @param classType -- Class of entries being read in.
	 * @return -- List of entries being returned.
	 */
	public <T> List<T> readCsvFile(String filePath, Class<T> classType) {
		String[] reader = new String[0];
		try {
			reader = Files.readAllLines(new File(filePath).toPath()).toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		LinkedList<T> ret = new LinkedList<>();
		if(filePath.toUpperCase().contains("AGENT")) {
			for(int i = 1; i < reader.length; i++) {
				String[] insert = reader[i].split(",");
				ret.add((T) new Agent(Integer.parseInt(insert[0]), insert[1], insert[2], insert[3], insert[4]));
			}
		}
		else if(filePath.toUpperCase().contains("CLAIM")) {
			for(int i = 1; i < reader.length; i++) {
				String[] insert = reader[i].split(",");
				ret.add((T) new Claim(Integer.parseInt(insert[0]), Integer.parseInt(insert[1]), Boolean.parseBoolean(insert[2]), Integer.parseInt(insert[3])));
			}
		}
		else if(filePath.toUpperCase().contains("CUSTOMER")) {
			for(int i = 1; i < reader.length; i++) {
				String[] insert = reader[i].split(",");
				Customer add = new Customer();
				add.setCustomerId(Integer.parseInt(insert[0]));
				add.setFirstName(insert[1]);
				add.setLastName(insert[2]);
				add.setAge(Integer.parseInt(insert[3]));
				add.setArea(insert[4]);
				add.setAgentId(Integer.parseInt(insert[5]));
				add.setAgentRating(Short.parseShort(insert[6]));
				add.setPrimaryLanguage(insert[7]);
				/*LinkedList<Dependent> put = new LinkedList<>();
				String[] dependents = reader[i].split(", ");*/
				ret.add((T) add);
			}
		}
		else if(filePath.toUpperCase().contains("VENDOR")) {
			for(int i = 1; i < reader.length; i++) {
				String[] insert = reader[i].split(",");
				ret.add((T) new Vendor(Integer.parseInt(insert[0]), insert[1], Integer.parseInt(insert[2]), Boolean.parseBoolean(insert[3])));
			}
		}
		return ret;
	}

	
	/* #2
	 * getAgentCountInArea() -- Return the number of agents in a given area.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @return -- The number of agents in a given area
	 */
	public int getAgentCountInArea(String filePath, String area) {
		LinkedList<Agent> counter = (LinkedList<Agent>) readCsvFile(filePath, Agent.class);
		int ret = 0;
		for(int i = 0; i < counter.size(); i++) {
			if(counter.get(i).getArea().equals(area)) {
				ret++;
			}
		}
		return ret;
	}

	
	/* #3
	 * getAgentsInAreaThatSpeakLanguage() -- Return a list of agents from a given area, that speak a certain language.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @param language -- The language spoken by the agent(s).
	 * @return -- The number of agents in a given area
	 */
	public List<Agent> getAgentsInAreaThatSpeakLanguage(String filePath, String area, String language) {
		LinkedList<Agent> matcher = (LinkedList<Agent>) readCsvFile(filePath, Agent.class);
		LinkedList<Agent> ret = new LinkedList<>();
		for(int i = 0; i < matcher.size(); i++) {
			if(matcher.get(i).getArea().equals(area) && matcher.get(i).getLanguage().equals(language)) {
				ret.add(matcher.get(i));
			}
		}
		return ret;
	}
	
	
	/* #4
	 * countCustomersFromAreaThatUseAgent() -- Return the number of individuals from an area that use a certain agent.
	 * @param filePath -- Path to file being read in.
	 * @param customerArea -- The area from which the customers should be counted.
	 * @param agentFirstName -- First name of agent.
	 * @param agentLastName -- Last name of agent.
	 * @return -- The number of customers that use a certain agent in a given area.
	 */
	public short countCustomersFromAreaThatUseAgent(Map<String,String> csvFilePaths, String customerArea, String agentFirstName, String agentLastName) {
		LinkedList<Agent> agents = (LinkedList<Agent>) readCsvFile(csvFilePaths.get("agentList"), Agent.class);
		LinkedList<Customer> customers = (LinkedList<Customer>) readCsvFile(csvFilePaths.get("customerList"), Customer.class);
		short ret = 0;
		for(int i = 0; i < customers.size(); i++) {
			if(customers.get(i).getArea().equals(customerArea)) {
				for(int j = 0; j < agents.size(); j++) {
					Agent comparator = agents.get(j);
					if(comparator.getFirstName().equals(agentFirstName) && comparator.getLastName().equals(agentLastName) && comparator.getAgentId() == customers.get(i).getAgentId()) {
						ret++;
					}
				}
			}
		}
		return ret;
	}

	
	/* #5
	 * getCustomersRetainedForYearsByPlcyCostAsc() -- Return a list of customers retained for a given number of years, in ascending order of their policy cost.
	 * @param filePath -- Path to file being read in.
	 * @param yearsOfServeice -- Number of years the person has been a customer.
	 * @return -- List of customers retained for a given number of years, in ascending order of policy cost.
	 */
	public List<Customer> getCustomersRetainedForYearsByPlcyCostAsc(String customerFilePath, short yearsOfService) {
		LinkedList<Customer> match = (LinkedList<Customer>) readCsvFile(customerFilePath, Customer.class);
		return null;
	}

	
	/* #6
	 * getLeadsForInsurance() -- Return a list of individuals who’ve made an inquiry for a policy but have not signed up.
	 * *HINT* -- Look for customers that currently have no policies with the insurance company.
	 * @param filePath -- Path to file being read in.
	 * @return -- List of customers who’ve made an inquiry for a policy but have not signed up.
	 */
	public List<Customer> getLeadsForInsurance(String filePath) {
		LinkedList<Customer> match = (LinkedList<Customer>) readCsvFile(filePath, Customer.class);
		LinkedList<Customer> ret = new LinkedList<>();
		for(int i = 0; i < match.size(); i++) {
			Customer temp = match.get(i);
			if(!temp.isAutoPolicy() && !temp.isHomePolicy() && !temp.isRentersPolicy()) {
				ret.add(temp);
			}
		}
		return ret;
	}


	/* #7
	 * getVendorsWithGivenRatingThatAreInScope() -- Return a list of vendors within an area and include options to narrow it down by: 
			a.	Vendor rating
			b.	Whether that vendor is in scope of the insurance (if inScope == false, return all vendors in OR out of scope, if inScope == true, return ONLY vendors in scope)
	 * @param filePath -- Path to file being read in.
	 * @param area -- Area of the vendor.
	 * @param inScope -- Whether or not the vendor is in scope of the insurance.
	 * @param vendorRating -- The rating of the vendor.
	 * @return -- List of vendors within a given area, filtered by scope and vendor rating.
	 */
	public List<Vendor> getVendorsWithGivenRatingThatAreInScope(String filePath, String area, boolean inScope, int vendorRating) {
		return null;
	}


	/* #8
	 * getUndisclosedDrivers() -- Return a list of customers between the age of 40 and 50 years (inclusive), who have:
			a.	More than X cars
			b.	less than or equal to X number of dependents.
	 * @param filePath -- Path to file being read in.
	 * @param vehiclesInsured -- The number of vehicles insured.
	 * @param dependents -- The number of dependents on the insurance policy.
	 * @return -- List of customers filtered by age, number of vehicles insured and the number of dependents.
	 */
	public List<Customer> getUndisclosedDrivers(String filePath, int vehiclesInsured, int dependents) {
		return null;
	}	


	/* #9
	 * getAgentIdGivenRank() -- Return the agent with the given rank based on average customer satisfaction rating. 
	 * *HINT* -- Rating is calculated by taking all the agent rating by customers (1-5 scale) and dividing by the total number 
	 * of reviews for the agent.
	 * @param filePath -- Path to file being read in.
	 * @param agentRank -- The rank of the agent being requested.
	 * @return -- Agent ID of agent with the given rank.
	 */
	public int getAgentIdGivenRank(String filePath, int agentRank) {
		return 0;
	}	

	
	/* #10
	 * getCustomersWithClaims() -- Return a list of customers who’ve filed a claim within the last <numberOfMonths> (inclusive). 
	 * @param filePath -- Path to file being read in.
	 * @param monthsOpen -- Number of months a policy has been open.
	 * @return -- List of customers who’ve filed a claim within the last <numberOfMonths>.
	 */
	public List<Customer> getCustomersWithClaims(Map<String,String> csvFilePaths, short monthsOpen) {
		return null;
	}	

}
