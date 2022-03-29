package github.heyxhh.concurrency.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;


public class ShowConditionUsing {

    public static void main(String[] args) {

        Lounge lounge =  new Lounge();

        lounge.playDog("DogPlayer");

        lounge.playCat("CatPlayer");

        lounge.provideDog("DogProvider");

        lounge.provideCat("CatProvider");
        
    }
    
}


/**
 * 一个可以撸猫和撸狗的休闲室，假设只有一个房间
 */
@Slf4j(topic = "c.Lounge")
class Lounge {

    private ReentrantLock loungeLock = new ReentrantLock();

    private boolean hasDog;
    private Condition hasDogCondition = loungeLock.newCondition();

    private boolean hasCat;
    private Condition hasCatCondition = loungeLock.newCondition();

    public void playDog(String consumer) {

        new Thread(() -> {
            loungeLock.lock();
            try {
                log.debug("有狗狗玩否: {}", hasDog);
                while(!hasDog) {
                    try {
                        hasDogCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    log.debug("开始撸狗。。。");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    log.debug("释放狗。。。"); 
                    hasDog = false;
                }

            } finally {
                loungeLock.unlock();
            }
        }, consumer).start();;

    }

    public void playCat(String consumer) {

        new Thread(() -> {
            loungeLock.lock();
            try {
                log.debug("有猫猫玩否: {}", hasCat);
                while(!hasCat) {
                    try {
                        hasCatCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    log.debug("开始撸猫。。。");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    log.debug("释放猫。。。"); 
                    hasCat = false;
                }

            } finally {
                loungeLock.unlock();
            }
        }, consumer).start();;

    }

    public void provideDog(String provider) {

        new Thread(() -> {
            loungeLock.lock();

            try {
                log.debug("狗狗来了。。。"); 
                hasDog = true;
                hasDogCondition.signal();
            } finally {
                loungeLock.unlock();
            }
        }, provider).start();;

    }

    public void provideCat(String provider) {

        new Thread(() -> {
            loungeLock.lock();

            try {
                log.debug("猫猫来了。。。"); 
                hasCat = true;
                hasCatCondition.signal();
            } finally {
                loungeLock.unlock();
            }
        }, provider).start();;

    }
        

}