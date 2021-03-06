package _030_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class _084_HandleAsyncExecutor
{
	public static void main( String[] args ) throws InterruptedException, ExecutionException, TimeoutException
	{
		ExecutorService executor = Executors.newFixedThreadPool(5);
		CompletableFuture<Integer> task1 = CompletableFuture
				.supplyAsync(() -> {
					try{
						TimeUnit.SECONDS.sleep(2); 
						System.out.println(Thread.currentThread().getName() + ": firstTask");						   								
					} catch (Exception e){}
					return 10/0;})
				.handleAsync((ok,ex)-> {
					if (ok != null){
						System.out.println(Thread.currentThread().getName() + ": secondTask " + ok);
						return ok;
					}
					else if (ex != null){
						System.out.println(Thread.currentThread().getName() + ": secondTask " + ex);
						return null;
					}
					return null; },
				executor);

		TimeUnit.SECONDS.sleep(5);

		System.out.println(Thread.currentThread().getName() + ": " + task1.get());
		executor.shutdown();
	}
}
