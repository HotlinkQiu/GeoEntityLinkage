package dataset;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import datastructure.Task;

public class DatasetManager {
	private static volatile DatasetManager inst = null;
	private List<Task> taskList;
	
	public static DatasetManager getInstance() {
		if(inst == null) {
			synchronized(DatasetManager.class) {
				if(inst == null) {
					inst = new DatasetManager();
				}
			}
		}
		
		return inst;
	}
	
	private DatasetManager() {
		taskList = new ArrayList<Task>();
		readDataset();
	}
	
	private void readDataset() {
		String path = getDataPath();
		try {
			BufferedReader bufR = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "utf-8"));
			
			String line = "";
			Task task = null;
			while((line = bufR.readLine()) != null) {
				if(!line.startsWith(" ")) {
					String word = line.substring(0, line.indexOf(':'));
					String sentence = line.substring(line.indexOf(':')+1);
					sentence = sentence.trim();

					task = new Task(word);
					task.addSentence(sentence);
					taskList.add(task);
				} else {
					String sentence = line.trim();
					task.addSentence(sentence);
				}
			}

			bufR.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getDataPath() {
		String path = "";
		try {
			path = DatasetManager.class.getResource("").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		path += "data/linkresul_sample.txt";

		return path;
	}
	
	public List<Task> getTaskList() {
		return taskList;
	}
	
	public static void main(String[] args) {
		List<Task> taskList = DatasetManager.getInstance().getTaskList();
		for(Task task : taskList) {
			System.out.println(task.getWord());
			List<String> sentenceList = task.getSentenceList();
			for(String sentence : sentenceList) {
				System.out.println(sentence);
			}
		}
	}
}