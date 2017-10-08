package se.skogsbrynet.iceandfire.tmp;

/**
 * Create new Threads
 */
public class IceAndFireThreadFactory implements java.util.concurrent.ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable);
    }
}