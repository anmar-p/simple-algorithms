package trees;

public interface IBinarySearchTree<T> extends ITree<T> {
    
    void delete(T data);
    
    T getMax();
    
    T getMin();
}
