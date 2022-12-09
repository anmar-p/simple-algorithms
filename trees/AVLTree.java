package trees;

public class AVLTree<T extends Comparable<T>> implements IAVLTree<T> {

    private AVLNode<T> root;
    
    @Override
    public void traverse() {
        if (root == null) {
            System.out.println("Nothing to traverse");
            return;
        } else {
            inOrderTraverse(root);
        }
    }

    private void inOrderTraverse(AVLNode<T> node) {
        if (node.getLeftChild() != null) {
            inOrderTraverse(node.getLeftChild());
        }
        System.out.print(node.getData() + " --> ");
        if (node.getRightChild() != null) {
            inOrderTraverse(node.getRightChild());
        }
    }

    @Override
    public void insert(T data) {
        root = insertNode(data, root);
    }

    private AVLNode<T> insertNode(T data, AVLNode<T> node) {
        if (node == null) {
            return new AVLNode<T>(data);
        }
        if(data.compareTo(node.getData()) < 0) {
            node.setLeftChild(insertNode(data, node.getLeftChild()));
        } else {
            node.setRightChild(insertNode(data, node.getRightChild()));
        }
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) +1);
        node = settleViolation(data, node);
        return node;
    }

    private AVLNode<T> settleViolation(T data, AVLNode<T> node) {
        int balance = getBalance(node);
        if(balance > 1 && data.compareTo(node.getLeftChild().getData()) < 0) {
            return rightRotation(node);
        }
        if(balance < -1 && data.compareTo(node.getRightChild().getData()) > 0) {
            return rightRotation(node);
        }
        if(balance > 1 && data.compareTo(node.getLeftChild().getData()) > 0) {
            node.setLeftChild(leftRotation(node.getLeftChild()));
            return rightRotation(node);
        }
        if(balance < -1 && data.compareTo(node.getRightChild().getData()) < 0) {
            node.setRightChild(rightRotation(node.getRightChild()));
            return leftRotation(node);
        }
        return node;
    }

    private int height(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }
    
    private int getBalance(AVLNode<T> node) {
        if (node == null) {
            return 0;
        }
        return height(node.getLeftChild()) - height(node.getRightChild());
    }
    
    private AVLNode<T> rightRotation(AVLNode<T> node){
        System.out.println("Rotation to the right of node: " +node);
        AVLNode<T> temp = node.getLeftChild();
        AVLNode<T> t = temp.getRightChild();
        temp.setRightChild(node);
        node.setLeftChild(t);
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild()))+1);
        temp.setHeight(Math.max(height(temp.getLeftChild()), height(temp.getRightChild()))+1);
        return temp;
    }
    
    private AVLNode<T> leftRotation(AVLNode<T> node){
        System.out.println("Rotation to the left of node: "+node);
        AVLNode<T> temp = node.getRightChild();
        AVLNode<T> t = temp.getLeftChild();
        temp.setLeftChild(node);
        node.setRightChild(t);
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild()))+1);
        temp.setHeight(Math.max(height(temp.getLeftChild()), height(temp.getRightChild()))+1);
        return temp;
    }
}
