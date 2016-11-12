/*
 Creates a thread pool that maintains enough threads to support the given parallelism level, and may use multiple queues to reduce contention.
 
     public static ExecutorService newWorkStealingPool(int parallelism) {
        return new ForkJoinPool
            (parallelism,
             ForkJoinPool.defaultForkJoinWorkerThreadFactory,
             null, true);
    }
    
   If you want to optimize performance of big computation of recursive tasks, use ForkJoinPool or newWorkStealingPool

 */

package _020_Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.springframework.util.SystemPropertyUtils;

class MyCallable3 implements Callable<Long> {
	private final long countUntil;
	MyCallable3(long countUntil) {
		this.countUntil = countUntil;
	}
	public Long call() throws Exception {
		long sum = 0;
		for (long i = 0; i <= 100+countUntil; i++) {
			sum += i;
		}
		System.out.println(Thread.currentThread().getName() + ": " + sum);
		return sum;
	}
}

public class _022_NewWorkStealingPool {
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
		//using all cores
		ExecutorService executor = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());
		List<Future<Long>> list = new ArrayList<Future<Long>>();
		for (int i = 0; i < 100; i++) {
			Callable<Long> worker = new MyCallable3(i);
			Future<Long> submit = executor.submit(worker);
			list.add(submit);
		}
		long sum = 0;
		System.out.println(list.size());
		// now retrieve the result
		for (Future<Long> future : list) {
			try {
				sum += future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println(sum);
		executor.shutdown();
	}
}