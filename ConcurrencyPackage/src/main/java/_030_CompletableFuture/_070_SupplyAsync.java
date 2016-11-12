package _030_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class _070_SupplyAsync
{
	public static void main( String[] args ) throws InterruptedException, ExecutionException, TimeoutException
	{
		//supplyAsync() method is similar to ExecutorService.submit(), but returning CompletableFuture
		//supplyAsync() method takes Supplier<U>. Supplier<U> is similar to Callable<U>
		CompletableFuture<Integer> task1 = CompletableFuture
				//output=Yes
				.supplyAsync(() -> {try{
					System.out.println(Thread.currentThread().getName() + ": firstTask");
					TimeUnit.SECONDS.sleep(2);    								
				} catch (Exception e){}
				return 10;
				});				

		TimeUnit.SECONDS.sleep(5);
		System.out.println(Thread.currentThread().getName() + ": " + task1.get());
	}
}

