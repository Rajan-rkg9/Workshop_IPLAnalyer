package com.capg.iplanalyzer;

import java.io.Reader;
import java.util.List;

public interface ICsvBuilder {
	public <T> List<T> getListFromCsv(Reader reader, Class<T> csvBindedClass) throws IPLAnalyserException;
}
