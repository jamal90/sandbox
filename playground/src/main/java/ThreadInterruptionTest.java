import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by I076097 on 8/12/2016.
 */
public class ThreadInterruptionTest {

    public void  testInterruptionOnSleep() throws InterruptedException {

        final Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
        Thread.sleep(500);
        t.interrupt();

    }

    public void testInterruptionOnExecutor() throws Exception{
        final ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<?> future = exec.submit(new Runnable() {
            public void run() {
                throw new RuntimeException();
            }
        });

        /* TEST CODE
            excepException.expect(ExecutionExcpetion.class)
         */

        future.cancel(true);

        future.get(); // throws ExceutionException.

    }


    public void testInterruptionOnExecutorWithCancel() throws Exception{
        final ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<?> future = exec.submit(new Runnable() {
            public void run() {
                while(!Thread.interrupted()){
                    System.out.println("I'm live");
                    for (int i  = 0; i < 10000 ; i++){
                        System.out.println("In Loop");
                    }
                }
                System.out.println("I'm done!");
            }
        });

        future.cancel(true);

        /*
            IF YOU CALL CANCEL ON A RUNNABLE THAT DOESN'T CHECK FOR isInterrupted(), the thread goes on
         */

        future.get(); // throws ExceutionException.

    }



    private class MyInterrupRunnable implements Runnable {
        public void run() {
            while (!Thread.interrupted()){
                // do stuff
            }
        }
    }

}
