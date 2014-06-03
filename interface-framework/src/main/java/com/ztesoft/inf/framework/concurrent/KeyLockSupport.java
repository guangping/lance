package com.ztesoft.inf.framework.concurrent;

import org.apache.log4j.Logger;

import java.util.*;

public class KeyLockSupport {

	private static Logger logger = Logger.getLogger(KeyLockSupport.class);
	private boolean single;

	public KeyLockSupport() {
		this(false);
	}

	public KeyLockSupport(boolean single) {
		this.single = single;
	}

	private static class LockEntry {
		Object key;
		Set waitingThreads = new HashSet();
		Thread owner;
	}

	private ThreadLocal threadLocal = new ThreadLocal();
	private Map keyLockMap = new HashMap();

	public void lock(Object key) {
		if (key == null) {
			throw new RuntimeException("lock key cannot be null!");
		}
		LockEntry entry = getLockEntry(key);
		synchronized (entry) {
			try {
				Thread currentThread = Thread.currentThread();
				if (entry.owner == null) {
					entry.owner = currentThread;
					logger.debug("entry:" + entry.key + " locked by thread:"
							+ currentThread.getName());
				}
				Set entries = (Set) threadLocal.get();
				if (entries == null) {
					entries = new HashSet();
					threadLocal.set(entries);
				}
				if (currentThread != entry.owner) {
					if (single) {
						throw new LockedException(key);
					}
					// 死锁检查
					Iterator iter = entries.iterator();
					while (iter.hasNext()) {
						LockEntry lockedEntry = (LockEntry) iter.next();
						if (lockedEntry.waitingThreads.contains(entry.owner)) {
							throw new RuntimeException(
									"would be dead lock!!!! "
											+ currentThread.getName()
											+ " cannot be waiting to lock "
											+ key + " owned by "
											+ entry.owner.getName()
											+ " waiting to lock "
											+ lockedEntry.key + " locked by  "
											+ currentThread.getName());
						}
					}
					logger.debug(currentThread.getName() + " waiting on entry:"
							+ entry.key);
					entry.waitingThreads.add(currentThread);
					entry.wait();
					entry.waitingThreads.remove(currentThread);
					entry.owner = Thread.currentThread();
					logger.debug("entry:" + entry.key + " locked by thread:"
							+ currentThread.getName());
				}

				entries.add(entry);
			} catch (InterruptedException e) {
			}
		}
	}

	private synchronized LockEntry getLockEntry(Object key) {
		LockEntry entry = (LockEntry) keyLockMap.get(key);
		if (entry == null) {
			entry = new LockEntry();
			entry.key = key;
			keyLockMap.put(key, entry);
		}
		return entry;
	}

	public synchronized void unlock() {
		Set entries = (Set) threadLocal.get();
		if (entries != null) {
			Iterator iter = entries.iterator();
			while (iter.hasNext()) {
				LockEntry entry = (LockEntry) iter.next();
				synchronized (entry) {
					if (entry.waitingThreads.size() == 0) {
						keyLockMap.remove(entry.key);
						logger.debug("remove entry:" + entry.key);
					}
					entry.notify();
					logger.debug("release entry:" + entry.key + " by thread:"
							+ entry.owner.getName());
				}
			}
			entries.clear();
		}

	}

	public static void main(String[] args) {
		final KeyLockSupport lock = new KeyLockSupport();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep((long) (Math.random() * 1000));
						if ((Math.random() * 10) > 5) {
							lock.lock("1a1");
							Thread.sleep((long) (Math.random() * 1000));
							lock.lock("1b1");
						} else {
							lock.lock("1b1");
							Thread.sleep((long) (Math.random() * 1000));
							lock.lock("1a1");
						}
					} catch (Exception e) {
						logger.error(e);
					} finally {
						lock.unlock();
					}
				}
			}).start();
		}
	}
}

