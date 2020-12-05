/*
 * The implementation of Philosophers that are running as processes.
 */
public class Philosopher extends Process {
	int id = 0;
	int total = 0;
	Fork rFork, lFork;
	public Philosopher(int id, MessageQueue mq) {
		super(id, mq);
		this.id = id;
	}

	public void run () {
		this.total = super.getMessageQueue().getTotalNum();

		/*
		* process 0 creates Forks and send them to proper processes.
		*/
		if(this.id == 0) {
			for(int count = 0; count < this.total; count++) {
				Fork f = new Fork(count); // create a Fork
				send(count, new DefaultMessage(0, f)); // send it to a process with a certain id (count).
				System.out.println("send to "+count);
				send(((count-1)+total)%total, new DefaultMessage(0, f)); // send it to another process.
				System.out.println("send to "+((count-1)+total)%total);
			}


			try {
				Thread.sleep(1000); // DO NOT REMOVE!!!
			} catch (InterruptedException e) {
			}


		}

		Fork f;
		while((this.rFork == null) || (this.lFork == null)) {
			if((f = receiveFork()) != null) {
				if(f.getID() == this.id) {
					this.lFork = f;
					System.out.println("set lFork "+f.getID()+" at Phil. "+this.id);
				}
				else {
					this.rFork = f;
					System.out.println("set rFork "+f.getID()+" at Phil. "+this.id);
				}
			}
			try {
				Thread.sleep(1000); // DO NOT REMOVE!!!
			} catch (InterruptedException e) {
			}
		}


		for (int i = 0; i < 3; i++) {
			right();
			Thread.yield(); // release the CPU resource.
			left();
			Thread.yield(); // release the CPU resource.
			eating();
		}

		System.out.println("Phil "+id+": I'm full.");
	}

	/*
	* Receiving a Fork
	*/
	private Fork receiveFork() {
		Object c;
		if((c = receive()) != null) {
			Fork f = (Fork) ((Message)c).getContent();

			return f;
		}
		else {
			return null;
		}
	}

	/*
	* Holding a Fork with a right hand.
	*/
	public void right() {
		Fork f;
		boolean get = f.hold(id);
		if(get){
			System.out.println("Phil " +id ": holds right fork");
		} else {
			System.out.println("Phil " +id ": faild to get a fork");
		}
	}

	/*
	* Holding a Fork with a left hand.
	*/
	public void left() {
		boolean get = f.hold(id);
		if(get){
			System.out.println("Phil " +id ": holds left fork");
		} else {
			System.out.println("Phil " +id ": faild to get a fork");
		}
	}

	/*
	* Release Forks of both hands.
	*/
	public void release(){

	}

	/*
	* Eating a dish with two Forks.
	*/
	public void eating() {
		System.out.println("Phil "+id+": is eating...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		release();
	}
}
