package trees;

public class BinarySearchTree<T extends Comparable<T>> implements IBinarySearchTree<T> {

    private Node<T> root;
    
    @Override
    public void traverse() {
        if (root == null) {
            System.out.println("Nothing to traverse");
            return;
        } else {
            inOrderTraverse(root);
        }
    }

    private void inOrderTraverse(Node<T> node) {
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
        if (root == null) {
            root = new Node<T>(data);
        } else {
            insertNode(data, root);
        }
    }

    private void insertNode(T data, Node<T> node) {
        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeftChild() != null) {
                insertNode(data, node.getLeftChild());
            } else {
                node.setLeftChild(new Node<T>(data));
            }
        } else {
            if (node.getRightChild() != null) {
                insertNode(data, node.getRightChild());
            } else {
                node.setRightChild(new Node<T>(data));
            }
        }
    }

    @Override
    public void delete(T data) {
        if (root == null) {
            return;
        } else {
            root = deleteNode(data, root);
        }
    }

    private Node<T> deleteNode(T data, Node<T> node) {
        if (node == null) {
            return node;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftChild(deleteNode(data, node.getLeftChild()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightChild(deleteNode(data, node.getRightChild()));
        } else {  // found the node
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                System.out.println("removing leaf node");
                return null;
            } 
            if (node.getLeftChild() == null) {
                System.out.println("removing right child");
                Node<T> tempNode = node.getRightChild();
                node = null;
                return tempNode;
            } else if (node.getLeftChild() == null) {
                System.out.println("removing left child");
                Node<T> tempNode = node.getLeftChild();
                node = null;
                return tempNode;
            }
            System.out.println("removing node with two children");
            Node<T> tempNode = getPredecessor(node.getLeftChild());
            node.setData(tempNode.getData());
            node.setLeftChild(deleteNode(tempNode.getData(), node.getLeftChild()));
        }
        return node;
    }

    private Node<T> getPredecessor(Node<T> node) {
        if (node.getRightChild() != null) {
            return getPredecessor(node.getRightChild());
        }
        return node;
    }

    @Override
    public T getMax() {
        if (root == null) {
            return null;
        }
        return getMaxValue(root);
    }

    private T getMaxValue(Node<T> node) {
        if (node.getRightChild() != null) {
            return getMaxValue(node.getRightChild());
        } 
        
        return node.getData();
    }
    
    @Override
    public T getMin() {
        if (root == null) {
            return null;
        }
        return getMinValue(root);
    }

    private T getMinValue(Node<T> node) {
        
        if (node.getLeftChild() != null) {
            return getMinValue(node.getLeftChild());
        } 
        
        return node.getData();
    }

    
}
