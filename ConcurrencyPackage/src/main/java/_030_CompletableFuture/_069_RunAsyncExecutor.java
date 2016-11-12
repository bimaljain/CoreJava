package _030_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class _069_RunAsyncExecutor
{
	public static void main( String[] args ) throws InterruptedException, ExecutionException, TimeoutException
	{
		ExecutorService executor = Executors.newFixedThreadPool(3);
		CompletableFuture<Void> task1 = CompletableFuture
				.runAsync(() -> {try{
					System.out.println(Thread.currentThread().getName() + ": firstTask");
					TimeUnit.SECONDS.sleep(2);    								
				} catch (Exception e){}},
						//this task will not run on ForkJoinPool
						executor);				

		TimeUnit.SECONDS.sleep(5);
		System.out.println(Thread.currentThread().getName() + ": My future is complete");
		//we must shutdown custom executor, otherwise it will keep listening for new tasks
		executor.shutdown();
	}
}
