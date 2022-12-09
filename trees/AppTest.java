package trees;

public class AppTest {

    public static void main(String[] args) {
        IAVLTree<Integer> bst = new AVLTree<Integer>();
        bst.insert(12);
        bst.insert(14);
        bst.traverse();
        bst.insert(6);
        bst.traverse();

        bst.insert(16);
        bst.traverse();
        
        bst.insert(23);
        bst.traverse();

        bst.insert(34);
        bst.insert(57);
        bst.insert(44);
        bst.traverse();

    }

}
