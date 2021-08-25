package Appello_07_09_2018_es1;


public interface PoolInterface {
	void require(int userId, int qty);
	void release(int userId, int qty);
}
