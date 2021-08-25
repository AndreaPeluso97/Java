package Appello_02_06_2019_es1;


public interface PoolInterface {
	void require(int userId, int qty);
	void release(int userId, int qty);
}

