package com.capg.iplanalyzer;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class IPLAnalyser {
	
	public int loadBatsmenData(String csvFilePath ) throws IPLAnalyserException 
	{
		if(!(csvFilePath.matches(".*\\.csv$")))
			throw new IPLAnalyserException("Incorrect Type", IPLAnalyserExceptionType.INCORRECT_TYPE);
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();	
			List<CSVIPLBatsmenRecords> batsmenList = csvBuilder.getListFromCsv(reader, CSVIPLBatsmenRecords.class);
			return  batsmenList.size();
			
		}
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
		catch (RuntimeException e) 
		{ 
			throw new IPLAnalyserException("Wrong Delimiter or Header", IPLAnalyserExceptionType.SOME_OTHER_ERRORS);
		}
	}
	public int loadBOwlersData(String csvFilePath ) throws IPLAnalyserException 
	{
		if(!(csvFilePath.matches(".*\\.csv$")))
			throw new IPLAnalyserException("Incorrect Type", IPLAnalyserExceptionType.INCORRECT_TYPE);
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();	
			List<CSVIPLBowlersRecords> bowlersList = csvBuilder.getListFromCsv(reader, CSVIPLBowlersRecords.class);
			return  bowlersList.size();
			
		}
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
		catch (RuntimeException e) 
		{ 
			throw new IPLAnalyserException("Wrong Delimiter or Header", IPLAnalyserExceptionType.SOME_OTHER_ERRORS);
		}
	}
	public  <E> List<E> loadData(String csvFilePath , Class <E>csvClass ) throws IPLAnalyserException 
	{
		if(!(csvFilePath.matches(".*\\.csv$")))
			throw new IPLAnalyserException("Incorrect Type", IPLAnalyserExceptionType.INCORRECT_TYPE);
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();	
			List<E> playerList = csvBuilder.getListFromCsv(reader, csvClass);
			return  playerList;	
		}
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
		catch (RuntimeException e) 
		{ 
			throw new IPLAnalyserException("Wrong Delimiter or Header", IPLAnalyserExceptionType.SOME_OTHER_ERRORS);
		}
	}
	
	public List<IPLAllRounder> loadStats(String batsmanFilePath, String bowlerFilePath) throws IPLAnalyserException{
		List<CSVIPLBatsmenRecords> iplBatsmanList=loadData(batsmanFilePath,CSVIPLBatsmenRecords.class);
		List<CSVIPLBowlersRecords> iplBowlerList= loadData(bowlerFilePath,CSVIPLBowlersRecords.class); 
		List<IPLAllRounder> iplAllRounderList= new ArrayList<IPLAllRounder>();
		iplBatsmanList.stream().forEach(batsman->{
			CSVIPLBowlersRecords bowlers = iplBowlerList.stream()
					.filter(bowler-> bowler.player.equalsIgnoreCase(batsman.player)).findFirst()
					.orElse(null);
			if (bowlers!=null) {
				iplAllRounderList.add(new IPLAllRounder(batsman.player,batsman.average,
						bowlers.average, batsman.runs,bowlers.wickets));
			}
		});
		return iplAllRounderList;
	}
	
	/**
	 * UC1,UC5
	 */
	public String getSortedBatsmenListOnBattingAverage(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBatsmenRecords> batsmenList = csvBuilder.getListFromCsv(reader, CSVIPLBatsmenRecords.class);
			Function<CSVIPLBatsmenRecords, Double> batsmanEntity=record->record.average;
			Comparator<CSVIPLBatsmenRecords> censusComparator=Comparator.comparing(batsmanEntity);
			this.sortBatsmenList(batsmenList, censusComparator);
			String sortedPlayersListToJson=new Gson().toJson(batsmenList);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC2,UC4
	 */
	public String getSortedBatsmenListOnTopStrikingRates(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBatsmenRecords> batsmenList = csvBuilder.getListFromCsv(reader, CSVIPLBatsmenRecords.class);
			Function<CSVIPLBatsmenRecords, Double> batsmanEntity=record->record.strikeRate;
			Comparator<CSVIPLBatsmenRecords> censusComparator=Comparator.comparing(batsmanEntity);
			this.sortBatsmenList(batsmenList, censusComparator);
			String sortedPlayersListToJson=new Gson().toJson(batsmenList);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC3
	 */
	public String getSortedBatsmenListOnMostSixes(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBatsmenRecords> batsmenList = csvBuilder.getListFromCsv(reader, CSVIPLBatsmenRecords.class);
			Function<CSVIPLBatsmenRecords, Integer> batsmanEntity=record->record.sixes;
			Comparator<CSVIPLBatsmenRecords> censusComparator=Comparator.comparing(batsmanEntity);
			this.sortBatsmenList(batsmenList, censusComparator);
			String sortedPlayersListToJson=new Gson().toJson(batsmenList);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC3
	 */
	public String getSortedBatsmenListOnMostFours(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBatsmenRecords> batsmenList = csvBuilder.getListFromCsv(reader, CSVIPLBatsmenRecords.class);
			Function<CSVIPLBatsmenRecords, Integer> batsmanEntity=record->record.fours;
			Comparator<CSVIPLBatsmenRecords> censusComparator=Comparator.comparing(batsmanEntity);
			this.sortBatsmenList(batsmenList, censusComparator);
			String sortedPlayersListToJson=new Gson().toJson(batsmenList);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC6
	 */
	public String getSortedBatsmenListOnMaxRuns(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBatsmenRecords> batsmenList = csvBuilder.getListFromCsv(reader, CSVIPLBatsmenRecords.class);
			Function<CSVIPLBatsmenRecords, Integer> batsmanEntity=record->record.runs;
			Comparator<CSVIPLBatsmenRecords> censusComparator=Comparator.comparing(batsmanEntity);
			this.sortBatsmenList(batsmenList, censusComparator);
			String sortedPlayersListToJson=new Gson().toJson(batsmenList);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC7,UC11
	 */
	public String getSortedBowlersListOnBowlingAverage(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBowlersRecords> bowlersList = csvBuilder.getListFromCsv(reader, CSVIPLBowlersRecords.class);
			Function<CSVIPLBowlersRecords, Double> bowlersEntity=record->record.average;
			Comparator<CSVIPLBowlersRecords> censusComparator=Comparator.comparing(bowlersEntity);
			this.sortBowlersList(bowlersList, censusComparator);
			String sortedPlayersListToJson=new Gson().toJson(bowlersList);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC8
	 */
	public String getSortedBowlersListOnBowlingStrikingRate(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBowlersRecords> bowlersList = csvBuilder.getListFromCsv(reader, CSVIPLBowlersRecords.class);
			Function<CSVIPLBowlersRecords, Double> bowlersEntity=record->record.strikeRate;
			Comparator<CSVIPLBowlersRecords> censusComparator=Comparator.comparing(bowlersEntity);
			this.sortBowlersList(bowlersList, censusComparator);
			String sortedPlayersListToJson=new Gson().toJson(bowlersList);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC9
	 */
	public String getSortedBowlersListOnBowlingEconomy(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBowlersRecords> bowlersList = csvBuilder.getListFromCsv(reader, CSVIPLBowlersRecords.class);
			Function<CSVIPLBowlersRecords, Double> bowlersEntity=record->record.economy;
			Comparator<CSVIPLBowlersRecords> censusComparator=Comparator.comparing(bowlersEntity);
			this.sortBowlersList(bowlersList, censusComparator);
			String sortedPlayersListToJson=new Gson().toJson(bowlersList);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC10
	 */
	public String getSortedBowlersListOnBowlingStrikingRateWith4wOr5w(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBowlersRecords> bowlersList = csvBuilder.getListFromCsv(reader, CSVIPLBowlersRecords.class);
			Function<CSVIPLBowlersRecords, Double> bowlersEntity=record->record.strikeRate;
			Comparator<CSVIPLBowlersRecords> censusComparator=Comparator.comparing(bowlersEntity);
			this.sortBowlersList(bowlersList, censusComparator);
			List<CSVIPLBowlersRecords> list = bowlersList.stream().filter
					(bowler -> (bowler.fourWktHaul>0 ||  bowler.fiveWktHaul>0)).collect(Collectors.toList());
			String sortedPlayersListToJson=new Gson().toJson(list);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC12
	 */
	public String getSortedBowlersListOnMostWickets(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBowlersRecords> bowlersList = csvBuilder.getListFromCsv(reader, CSVIPLBowlersRecords.class);
			Function<CSVIPLBowlersRecords, Integer> bowlersEntity=record->record.wickets;
			Comparator<CSVIPLBowlersRecords> censusComparator=Comparator.comparing(bowlersEntity);
			this.sortBowlersList(bowlersList, censusComparator);
			String sortedPlayersListToJson=new Gson().toJson(bowlersList);
			return sortedPlayersListToJson;
		} 
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	/**
	 * UC13
	 */
	public List<IPLAllRounder>  getSortedAllroundersListByBatting_BowlingAvg(String batsmanFilePath, String bowlerFilePath) throws IPLAnalyserException {
		List<IPLAllRounder> iplAllRounderList = loadStats(batsmanFilePath, bowlerFilePath);
				return iplAllRounderList.stream().
						sorted(Comparator.comparing(IPLAllRounder::getPerformanceByAverage).reversed())
						.collect(Collectors.toList());
	}
	/**
	 * UC14
	 */
	public List<IPLAllRounder> getSoredtAllrounderListByWicketsAndRuns(String batsmanFilePath, String bowlerFilePath) throws IPLAnalyserException {
		List<IPLAllRounder> iplAllRounderList = loadStats(batsmanFilePath, bowlerFilePath);
		return iplAllRounderList.stream()
				.sorted(Comparator.comparing(IPLAllRounder::getPerformanceByRunsAndWickets).reversed())
				.collect(Collectors.toList());
	}
	/**
	 * UC15
	 */
	public List<CSVIPLBatsmenRecords> getBestAvgWithMax100s(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createBuilder();
			List<CSVIPLBatsmenRecords> iplBattingList = csvBuilder.getListFromCsv(reader, CSVIPLBatsmenRecords.class);
			Function<CSVIPLBatsmenRecords, Double> batsmanEntity=record->record.average;
			Comparator<CSVIPLBatsmenRecords> censusComparator=Comparator.comparing(batsmanEntity);
			this.sortBatsmenList(iplBattingList, censusComparator);
			Comparator<CSVIPLBatsmenRecords> a = Comparator.comparing(CSVIPLBatsmenRecords::getCentury)
				.thenComparing(Comparator.comparing(CSVIPLBatsmenRecords::getAverage)).reversed();
		return iplBattingList.stream().sorted(a).collect(Collectors.toList());
		}
		catch (IOException e) {
			throw new IPLAnalyserException("Incorrect CSV File", IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
	}
	public void sortBatsmenList(List<CSVIPLBatsmenRecords> playersList, Comparator<CSVIPLBatsmenRecords> censusComparator) {
		for(int i=0;i<playersList.size()-1;i++) 
		{
			for(int j=0; j<playersList.size()-i-1;j++)
			{
				CSVIPLBatsmenRecords sortKey1=playersList.get(j);
				CSVIPLBatsmenRecords sortKey2=playersList.get(j+1);
				if(censusComparator.compare(sortKey1, sortKey2)<0)
				{
					playersList.set(j, sortKey2);
					playersList.set(j+1, sortKey1);
				}
			}
		}
	}
	public void sortBowlersList(List<CSVIPLBowlersRecords> playersList, Comparator<CSVIPLBowlersRecords> censusComparator) {
		for(int i=0;i<playersList.size()-1;i++) 
		{
			for(int j=0; j<playersList.size()-i-1;j++)
			{
				CSVIPLBowlersRecords sortKey1=playersList.get(j);
				CSVIPLBowlersRecords sortKey2=playersList.get(j+1);
				if(censusComparator.compare(sortKey1, sortKey2)>0)
				{
					playersList.set(j, sortKey2);
					playersList.set(j+1, sortKey1);
				}
			}
		}
	}
}