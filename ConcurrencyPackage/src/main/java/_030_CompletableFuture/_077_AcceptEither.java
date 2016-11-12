package _030_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class _077_AcceptEither
{
    public static void main( String[] args ) throws InterruptedException, ExecutionException, TimeoutException
    {
    	CompletableFuture<String> task1 = CompletableFuture
    			.supplyAsync(() -> {try{
    								System.out.println(Thread.currentThread().getName() + ": firstTask");
    								TimeUnit.SECONDS.sleep(2);    								
    								} catch (Exception e){}
    								return "1"; });
    	CompletableFuture<String> task2 = CompletableFuture
    			.supplyAsync(() -> {try{
									System.out.println(Thread.currentThread().getName() + ": secondTask");
									TimeUnit.SECONDS.sleep(3);    								
									} catch (Exception e){}
									return "2"; });
    	//thread which finish first will execute third task.
    	//input-YES, output-NO
    	task1.acceptEither(task2, (x) -> {System.out.println(Thread.currentThread().getName() + ": thirdTask " + x);});

    	TimeUnit.SECONDS.sleep(5); 
    	System.out.println(Thread.currentThread().getName() + ": " + task1.get());
    }
}
