package result;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ResultManager {
	private static volatile ResultManager inst = null;
	private ReadWriteLock logFileLock;
	private ReadWriteLock statFileLock;
	private String path;

	public static ResultManager getInstance() {
		if(inst == null) {
			synchronized(ResultManager.class) {
				if(inst == null) {
					inst = new ResultManager();
				}
			}
		}
		
		return inst;
	}
	
	private ResultManager() {
		logFileLock = new ReentrantReadWriteLock();
		statFileLock = new ReentrantReadWriteLock();
		path = getDataPath();
	}
	
	private String getDataPath() {
		String path = "";
		try {
			path = ResultManager.class.getResource("").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		path += "data/";

		return path;
	}
	
	public void handleResult(String word, String resultList) {
		String[] results = resultList.split(";");
		
		int count = 0;
		for(int i = 0; i < results.length; i ++) {
			count += Integer.parseInt(results[i]);
		}
		
		writeLogFile(word, resultList);
		writeStatFile(word, count);
	}
	
	private void writeLogFile(String word, String results) {
		logFileLock.writeLock().lock();
		try {
			BufferedWriter bufW = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path+"logfile.txt", true), "UTF-8"));
			bufW.write(word+":"+results+"\n");
			bufW.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		logFileLock.writeLock().unlock();
	}
	
	private void writeStatFile(String word, int count) {
		statFileLock.writeLock().lock();
		try {
			BufferedWriter bufW = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path+"statfile.txt", true), "UTF-8"));
			bufW.write(word+":"+count+"\n");
			bufW.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		statFileLock.writeLock().unlock();
	}
}