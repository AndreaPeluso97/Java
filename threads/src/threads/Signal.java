package threads;

public abstract class Signal implements SignalSender,
SignalWaiter {
protected boolean arrived = false;
public abstract void waits() throws InterruptedException;
public synchronized void send() {
arrived = true;
notify();
}
}
