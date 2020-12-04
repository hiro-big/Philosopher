/*
 * The implementation of Forks
 */
public class Fork {
	int fid;
	boolean occupied = false;
	
	
	Fork (int i) {
		fid = i;
	}
	
	synchronized boolean hold(int i) {
		if(!occupied) {
			occupied = true;
			System.out.println("--Fork "+fid+": is occupied by Phil "+i);
			return true;
		}
		else 
			return false;
	}
	
	synchronized boolean drop(int i) {
		if(occupied) {
			occupied = false;
			System.out.println("--Fork "+fid+": is released by Phil "+i);			
			return true;
		}
		else
			return false;
	}

	public int getID() {
		return fid;
	}
}
