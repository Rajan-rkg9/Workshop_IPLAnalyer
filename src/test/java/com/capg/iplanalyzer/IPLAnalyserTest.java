package com.capg.iplanalyzer;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

public class IPLAnalyserTest {
	public static final String RIGHT_BATSMEN_CSV = "resources/IPL2019FactsheetMostRuns.csv";
	public static final String WRONG_BATSMEN_CSV = "resources/IPL2019FactsheetMostRunssdd.csv";
	public static final String WRONGTYPE_BATSMEN_CSV = "resources/IPL2019FactsheetMostRuns.pdf";
	public static final String RIGHT_BOWLERS_CSV = "resources/IPL2019FactsheetMostWkts.csv";
	public static final String WRONG_BOWLERS_CSV = "resources/IPL2019FactsheetMostwktsss.csv";
	public static final String WRONGTYPE_BOWLERS_CSV = "resources/IPL2019FactsheetMostWkts.pdf";
	
	@Test
	public void givenBatsmenDataCsv_ShouldReturnExactCount() {
		try {
			int recordsCount = new IPLAnalyser().loadBatsmenData(RIGHT_BATSMEN_CSV);
			assertEquals(100, recordsCount);
		}
		catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenWrongBatsmenCsvFile_ShouldThrowIPLAnalyserExceptionOfTypeCensusFileProblem() {
		try {
			new IPLAnalyser().loadBatsmenData(WRONG_BATSMEN_CSV);
		}
		catch(IPLAnalyserException e) {
			assertEquals(IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM, e.exceptionType);
		}
	}
	
	@Test
	public void givenWrongTypeBatsmenCsvFile_ShouldThrowIPLAnalyserExceptionOfTypeIncorrectType() {
		try {
			new IPLAnalyser().loadBatsmenData(WRONGTYPE_BATSMEN_CSV);
		}
		catch(IPLAnalyserException e) {
			assertEquals(IPLAnalyserExceptionType.INCORRECT_TYPE, e.exceptionType);
		}
	}
	
	@Test
	public void givenBowlersDataCsv_ShouldReturnExactCount() {
		try {
			int recordsCount = new IPLAnalyser().loadBatsmenData(RIGHT_BOWLERS_CSV);
			assertEquals(99, recordsCount);
		}
		catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenWrongBowlersCsvFile_ShouldThrowIPLAnalyserExceptionOfTypeCensusFileProblem() {
		try {
			new IPLAnalyser().loadBatsmenData(WRONG_BOWLERS_CSV);
		}
		catch(IPLAnalyserException e) {
			assertEquals(IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM, e.exceptionType);
		}
	}
	
	@Test
	public void givenWrongTypeBowlersCsvFile_ShouldThrowIPLAnalyserExceptionOfTypeIncorrectType() {
		try {
			new IPLAnalyser().loadBatsmenData(WRONGTYPE_BOWLERS_CSV);
		}
		catch(IPLAnalyserException e) {
			assertEquals(IPLAnalyserExceptionType.INCORRECT_TYPE, e.exceptionType);
		}
	}
	
	@Test
	public void givenSortedOnBattingAverageBatsmenList_ShouldReturnBestAveragedBatsman() {
		try {
			String sortedBatsmenJson = new IPLAnalyser().getSortedBatsmenListOnBattingAverage(RIGHT_BATSMEN_CSV);
			CSVIPLBatsmenRecords[] batsmenListCsv=new Gson().fromJson(sortedBatsmenJson, CSVIPLBatsmenRecords[].class);
			assertEquals("MS Dhoni", batsmenListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnStrikingRatesBatsmenList_ShouldReturnTopStrikeRateBatsman() {
		try {
			String sortedBatsmenJson = new IPLAnalyser().getSortedBatsmenListOnTopStrikingRates(RIGHT_BATSMEN_CSV);
			CSVIPLBatsmenRecords[] batsmenListCsv=new Gson().fromJson(sortedBatsmenJson, CSVIPLBatsmenRecords[].class);
			assertEquals("Ishant Sharma", batsmenListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnMostSixesBatsmenList_ShouldReturnWhoHitMostSixes() {
		try {
			String sortedBatsmenJson = new IPLAnalyser().getSortedBatsmenListOnMostSixes(RIGHT_BATSMEN_CSV);
			CSVIPLBatsmenRecords[] batsmenListCsv=new Gson().fromJson(sortedBatsmenJson, CSVIPLBatsmenRecords[].class);
			assertEquals("Andre Russell", batsmenListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnMostFoursBatsmenList_ShouldReturnPlayerWhoHitMostFours() {
		try {
			String sortedBatsmenJson = new IPLAnalyser().getSortedBatsmenListOnMostFours(RIGHT_BATSMEN_CSV);
			CSVIPLBatsmenRecords[] batsmenListCsv=new Gson().fromJson(sortedBatsmenJson, CSVIPLBatsmenRecords[].class);
			assertEquals("Shikhar Dhawan", batsmenListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnMaxRunsBatsmenList_ShouldReturnTopRunsScorer() {
		try {
			String sortedBatsmenJson = new IPLAnalyser().getSortedBatsmenListOnMaxRuns(RIGHT_BATSMEN_CSV);
			CSVIPLBatsmenRecords[] batsmenListCsv=new Gson().fromJson(sortedBatsmenJson, CSVIPLBatsmenRecords[].class);
			assertEquals("David Warner ", batsmenListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnBowlingAverageBowlersList_ShouldReturnBestAveragedBowler() {
		try {
			String sortedBowlersJson = new IPLAnalyser().getSortedBowlersListOnBowlingAverage(RIGHT_BOWLERS_CSV);
			CSVIPLBowlersRecords[] bowlersListCsv=new Gson().fromJson(sortedBowlersJson, CSVIPLBowlersRecords[].class);
			assertEquals("Anukul Roy", bowlersListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnBowlingStrikeRateBowlersList_ShouldReturnBestStrikingBowler() {
		try {
			String sortedBowlersJson = new IPLAnalyser().getSortedBowlersListOnBowlingStrikingRate(RIGHT_BOWLERS_CSV);
			CSVIPLBowlersRecords[] bowlersListCsv=new Gson().fromJson(sortedBowlersJson, CSVIPLBowlersRecords[].class);
			assertEquals("Alzarri Joseph", bowlersListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnBowlingEconomyBowlersList_ShouldReturnMostEconomicBowler() {
		try {
			String sortedBowlersJson = new IPLAnalyser().getSortedBowlersListOnBowlingEconomy(RIGHT_BOWLERS_CSV);
			CSVIPLBowlersRecords[] bowlersListCsv=new Gson().fromJson(sortedBowlersJson, CSVIPLBowlersRecords[].class);
			assertEquals("Shivam Dube", bowlersListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnBowlingEconomyBowlersList_ShouldReturnMostEconomicBowlerWith4wOr5w() {
		try {
			String sortedBowlersJson = new IPLAnalyser().getSortedBowlersListOnBowlingStrikingRateWith4wOr5w(RIGHT_BOWLERS_CSV);
			CSVIPLBowlersRecords[] bowlersListCsv=new Gson().fromJson(sortedBowlersJson, CSVIPLBowlersRecords[].class);
			assertEquals("Alzarri Joseph", bowlersListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnBowlingAverageAndStrikingRatePossibleBowlersList_ShouldReturnBestAveragedBowler() {
		try {
			String sortedBowlersJson = new IPLAnalyser().getSortedBowlersListOnBowlingAverage(RIGHT_BOWLERS_CSV);
			CSVIPLBowlersRecords[] bowlersListCsv=new Gson().fromJson(sortedBowlersJson, CSVIPLBowlersRecords[].class);
			assertEquals("Anukul Roy", bowlersListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedOnMostWicketsBowlersList_ShouldReturnMostWicketsTakingBowler() {
		try {
			String sortedBowlersJson = new IPLAnalyser().getSortedBowlersListOnMostWickets(RIGHT_BOWLERS_CSV);
			CSVIPLBowlersRecords[] bowlersListCsv=new Gson().fromJson(sortedBowlersJson, CSVIPLBowlersRecords[].class);
			assertEquals("Imran Tahir", bowlersListCsv[98].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedBestAllroundersByBatting_BowlingAverage_ShouldReturnBestAllRounder() {
		try {
			List<IPLAllRounder> sortedAllrounderList=new IPLAnalyser().getSortedAllroundersListByBatting_BowlingAvg(RIGHT_BATSMEN_CSV , RIGHT_BOWLERS_CSV);
			assertEquals("Andre Russell",sortedAllrounderList.get(0).getPlayer());
		    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedBestAllroundersByWicketsAndRuns_ShouldReturnBestAllRounder() {
		try {
			List<IPLAllRounder> sortedAllrounderList=new IPLAnalyser().getSoredtAllrounderListByWicketsAndRuns(RIGHT_BATSMEN_CSV , RIGHT_BOWLERS_CSV);
			assertEquals("Hardik Pandya",sortedAllrounderList.get(0).getPlayer());
		    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenSortedPlayersByMax100AndAvg_ShouldReturnCorrectPlayer()  {
		try {
			List<CSVIPLBatsmenRecords> sortedListByAvgand100s = new IPLAnalyser().getBestAvgWithMax100s(RIGHT_BATSMEN_CSV);
			assertEquals("David Warner ", sortedListByAvgand100s.get(0).player);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void givenSortedOnBestAverageWithZero100sAndZero50s_ShouldReturnBestAveragedBatsman() {
		try {
			String sortedBatsmenJson = new IPLAnalyser().getSortedBatsmenListOnZero50sAnd100sButBestAverage(RIGHT_BATSMEN_CSV);
			CSVIPLBatsmenRecords[] batsmenListCsv=new Gson().fromJson(sortedBatsmenJson, CSVIPLBatsmenRecords[].class);
			assertEquals("Marcus Stoinis", batsmenListCsv[0].player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
