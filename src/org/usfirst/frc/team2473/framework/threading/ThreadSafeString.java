package org.usfirst.frc.team2473.framework.threading;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Stores a String value as a threadsafe value.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class ThreadSafeString {
	private volatile String value; //the value itself, being stored in main memory
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true); //lock used to protect the value from threads

	/**
	 * Returns the value
	 * @return the <code>String</code> value being stored by this ThreadSafeString
	 */
	public String getValue() { //unlock the lock to let the value out and lock it back
		try {
			lock.readLock().lock();
			return value;
		} finally {
			lock.readLock().unlock();
		}

	}

	/**
	 * Assigns the value to this <code>ThreadSafeString</code>
	 * @param newValue <code>String</code> value representing the new value to be assigned to this ThreadSafeString
	 */
	public void setValue(String newValue) { //unlock the lock to allow for value editing and then lock it back
		try {
			lock.writeLock().lock();
			value = newValue;
		} finally {
			lock.writeLock().unlock();
		}
	}
}