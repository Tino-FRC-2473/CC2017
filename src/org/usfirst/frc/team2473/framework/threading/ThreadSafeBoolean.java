package org.usfirst.frc.team2473.framework.threading;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Stores a boolean value as a threadsafe value.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class ThreadSafeBoolean{
	private volatile boolean value; //the value itself, being stored in main memory
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true); //lock used to protect the value from threads

	/**
	 * Returns the value
	 * @return the <code>boolean</code> value being stored by this ThreadSafeBoolean
	 */
	public boolean getValue() { //unlock the lock to let the value out and lock it back
		try {
			lock.readLock().lock();
			return value;
		} finally {
			lock.readLock().unlock(); 
		}

	}

	/**
	 * Assigns the value to this <code>ThreadSafeBoolean</code>
	 * @param newValue <code>boolean</code> value representing the new value to be assigned to this ThreadSafeBoolean
	 */
	public void setValue(boolean newValue) { //unlock the lock to allow for value editing and then lock it back
		try {
			lock.writeLock().lock();
			value = newValue;
		} finally {
			lock.writeLock().unlock();
		}
	}
}