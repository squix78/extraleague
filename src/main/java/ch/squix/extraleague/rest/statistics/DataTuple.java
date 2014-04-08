package ch.squix.extraleague.rest.statistics;

public class DataTuple<K, V> {
	
	private K key;
	private V value;
	private String label;
	
	public DataTuple() {
		
	}
	
	public DataTuple(K key, V value, String label) {
		this.key = key;
		this.value = value;
		this.label = label;
	}
	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(K key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(V value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
