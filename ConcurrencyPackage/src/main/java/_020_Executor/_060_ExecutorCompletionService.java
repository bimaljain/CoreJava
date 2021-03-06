/*Demo on ExecutorService vs. ExecutorCompletionService

A CompletionService that uses a supplied Executor to execute tasks. This class arranges that submitted tasks are, upon completion, 
placed on a queue accessible using take().
take() retrieves and removes the Future representing the next completed task, waiting if none are yet present.	
*/
package _020_Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class _060_ExecutorCompletionService {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		_060_ExecutorCompletionService demo = new _060_ExecutorCompletionService();
		demo.completionServiceDemo();
		demo.executorServiceDemo();
	}

	class MyRunnable implements Callable<Long> {
		private final long sleepTime;
		MyRunnable(long sleepTime) {
			this.sleepTime = sleepTime;
		}
		public Long call() {
			try {
				Thread.sleep(sleepTime);
			} catch (Exception e) {}
			return sleepTime;
		}
	}

	public void executorServiceDemo(){
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		List<Future<Long>> futures = new ArrayList<Future<Long>>();
		futures.add((Future<Long>) executorService.submit(new MyRunnable(2000)));
		futures.add((Future<Long>) executorService.submit(new MyRunnable(1000)));
		futures.add((Future<Long>) executorService.submit(new MyRunnable(500)));
		futures.add((Future<Long>) executorService.submit(new MyRunnable(100)));

		Long result = null;
		//Then we can iterate over the list to get the computed result of each future:
		for (Future<Long> future:futures) {			
			try {
				//Suppose task 4 finished first followed by task 3 and followed by task 2. 
				//But task 1 was still going on. In that case when using ExecutorService the for loop would be waiting for the result of 
				//task 1 to be available. So in case of ExecutorService tasks will be processed in the same order in which they were submitted.
				result = (Long) future.get();
			} catch (Exception e) {}
			if (result != null)
				break;
		}		
		for (Future<Long> f : futures)
			f.cancel(true);
		System.out.println("executorService: " + result);
		executorService.shutdownNow();
	}

	public void completionServiceDemo () throws InterruptedException, ExecutionException{
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		CompletionService<Long> executorCompletionService= new ExecutorCompletionService<>(executorService );
		List<Future<Long>> futures = new ArrayList<Future<Long>>();
		futures.add((Future<Long>) executorCompletionService.submit(new MyRunnable(2000)));
		futures.add((Future<Long>) executorCompletionService.submit(new MyRunnable(3000)));
		futures.add((Future<Long>) executorCompletionService.submit(new MyRunnable(4000)));
		futures.add((Future<Long>) executorCompletionService.submit(new MyRunnable(100)));

		//the tasks will be processed in order the result becomes available, the order tasks are completed.
		//ie. take() will get the first available result.	
		Long result = (Long) executorCompletionService.take().get();
		System.out.println("completionService: " + result);
//		System.out.println((Long) executorCompletionService.take().get()); //returns 2nd completed task
//		System.out.println((Long) executorCompletionService.take().get()); //returns 3rd completed task
//		System.out.println((Long) executorCompletionService.take().get()); //returns 4th completed task
		//cancel out all the other futures.
		for (Future<Long> f : futures)
			f.cancel(true);
		executorService.shutdownNow();
	}
}
