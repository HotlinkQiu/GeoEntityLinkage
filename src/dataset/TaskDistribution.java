package dataset;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import datastructure.Task;

public class TaskDistribution {
	private static volatile TaskDistribution inst = null;
	private List<Task> taskList;
	private int index;
	private ReadWriteLock lock;
	
	public static TaskDistribution getInstance() {
		if(inst == null) {
			synchronized(TaskDistribution.class) {
				if(inst == null) {
					inst = new TaskDistribution();
				}
			}
		}
		
		return inst;
	}

	private TaskDistribution() {
		taskList = DatasetManager.getInstance().getTaskList();
		index = 0;
		lock = new ReentrantReadWriteLock();
	}
	
	public Task getATask() {
		int index = getAIndex();
		return taskList.get(index);
	}
	
	private int getAIndex() {
		lock.writeLock().lock();
		index = (index+1)%taskList.size();
		lock.writeLock().unlock();
		return index;
	}
}