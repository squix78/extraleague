package ch.squix.extraleague.rest.timeseries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSeriesDto {
	private List<List<String>> values = new ArrayList<>();
	private String key = "";
	
	public DataSeriesDto() {
		
	}
	
	public DataSeriesDto(String key) {
		this.key = key;
	}
	public DataSeriesDto(List<List<String>> values, String key) {
		this.values = values;
		this.key = key;
	}
	
	public List<List<String>> getValues() {
		return values;
	}
	public String getKey() {
		return key;
	}
	public void setValues(List<List<String>> values) {
		this.values = values;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void addTuple(String timeStamp, String value) {
		values.add(Arrays.asList(timeStamp, value));
	}



}
