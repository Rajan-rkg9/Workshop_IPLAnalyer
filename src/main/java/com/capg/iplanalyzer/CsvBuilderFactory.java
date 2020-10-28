package com.capg.iplanalyzer;

public class CsvBuilderFactory {
	public static ICsvBuilder createBuilder() {
		return new OpenCsvBuilder();
	}
}
