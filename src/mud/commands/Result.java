package mud.commands;

import java.util.List;
import java.util.ArrayList;

/**
 * Result.java
 * 
 * Wraps the return value of the {@link Command#execute()} function into 
 * a single Java object
 * 
 * @author mcmillhj
 */
public final class Result {
	private List<String> results;
 	
	public Result() {
		results = new ArrayList<String>();
	}
	
	public void addResult(String result) {
		this.results.add(result);
	}
	
	public List<String> getResults() {
		return this.results;
	}
	
	public String toString() {
		return this.results.toString();
	}
}
