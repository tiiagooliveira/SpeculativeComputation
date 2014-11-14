import java.util.List;

/**
 * 
 */

/**
 * @author Filipe Gonçalves
 * 
 */
public class map_res {
	double score_probability;
	double score_engine_probability;
	List<String[]> list_variables;

	/**
	 * @param score_probability
	 * @param score_engine_probability
	 * @param list_variables
	 */
	public map_res(double score_probability, double score_engine_probability,
			List<String[]> list_variables) {
		super();
		this.score_probability = score_probability;
		this.score_engine_probability = score_engine_probability;
		this.list_variables = list_variables;
	}

	public double getScore_probability() {
		return score_probability;
	}

	public void setScore_probability(double score_probability) {
		this.score_probability = score_probability;
	}

	public double getScore_engine_probability() {
		return score_engine_probability;
	}

	public void setScore_engine_probability(double score_engine_probability) {
		this.score_engine_probability = score_engine_probability;
	}

	public List<String[]> getList_variables() {
		return list_variables;
	}

	public void setList_variables(List<String[]> list_variables) {
		this.list_variables = list_variables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((list_variables == null) ? 0 : list_variables.hashCode());
		long temp;
		temp = Double.doubleToLongBits(score_engine_probability);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(score_probability);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		map_res other = (map_res) obj;
		if (list_variables == null) {
			if (other.list_variables != null)
				return false;
		} else if (!list_variables.equals(other.list_variables))
			return false;
		if (Double.doubleToLongBits(score_engine_probability) != Double
				.doubleToLongBits(other.score_engine_probability))
			return false;
		if (Double.doubleToLongBits(score_probability) != Double
				.doubleToLongBits(other.score_probability))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("map_res [score_probability=").append(score_probability)
				.append(", score_engine_probability=")
				.append(score_engine_probability).append(", list_variables=[");
				for(String[] a : this.list_variables){
					builder.append("{").append(a[0]).append("=").append(a[1]).append("}");
				}
				builder.append("]");
		return builder.toString();
	}
	
	

}
