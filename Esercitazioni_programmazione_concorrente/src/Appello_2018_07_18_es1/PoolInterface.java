package Appello_2018_07_18_es1;


public interface PoolInterface {
	void require(int userId, int qty);
	void release(int userId, int qty);
}

