package org.astrientfoundation.threads;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Pool<E> 
{
	private Semaphore  sem         = new Semaphore(0);
	private List<E> freeObjects = new LinkedList<E>();
	private List<E> busyObjects = new LinkedList<E>();

	public Pool()
	{
	}

	protected List<E> freeObjects()
	{
		return freeObjects;
	}

	protected List<E> busyObjects()
	{
		return busyObjects;
	}

	public int put(E item)
	{
		freeObjects.add(0, item);
		sem.release();
		return freeObjects.size();
	}


	public boolean remove(E item)
	{
		return busyObjects.remove(item);
	}

	public synchronized void reset()
	{
		freeObjects.clear();
		busyObjects.clear();
	}


	public int size()
	{
		return (freeObjects.size() + busyObjects.size());
	}

	public E acquire() throws InterruptedException
	{
		sem.acquire();
		synchronized ( freeObjects )
		{
			E item = freeObjects.remove(freeObjects.size()-1);
			busyObjects.add(0,item);
			return item;
		}
	}


	public boolean release(E item)
	{
		if ( busyObjects.remove(item) )
		{
			synchronized ( busyObjects )
			{
				freeObjects.add(0,item);
				sem.release();

				//Prevents releasing thread from dominating resource
				Thread.yield();
			}
			return true;
		}

		return false;
	}


	public static void main(String[] args)
	{
		final Pool<String> p = new Pool<String>();
		p.put("rashid");
		p.put("shehu");

		Runnable rashid = (new Runnable()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						String o = p.acquire();
						System.out.println("Thread3:" + Thread.currentThread().getPriority() + ": " + o);
						Thread.sleep( Math.round((Math.random() * 1045)) );
						p.release(o);
					}
					catch (Exception e) {}
				}
			}
		});


		(new Thread(rashid)).start();
		System.out.println(p.size());
	}

}
