package dao;

import java.util.ArrayList;

public interface DaoInterface<T> {
    public int insert(T t);
    public int update(T t);
    public int delete(int id) throws Exception;
    public ArrayList<T> selectAll();
    public T selectById(int id);
}
