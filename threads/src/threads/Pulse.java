package threads;

public class Pulse extends TransientSignal {
	// Sveglia tutti i thread in attesa del segnale
	public synchronized void sendAll() {
	if(waiting>0) {
	arrived = true;
	notifyAll();
	}
	}}