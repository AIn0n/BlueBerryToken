package Trees.KeyTree;

import java.util.ArrayList;

public class Node<G, T extends Comparable<T>> {
    private final G data;
    private final T key;
    private final ArrayList<Node<G, T>> children = new ArrayList<>();
    private int maxLength = 0;


    Node(G data, T key) {
        this.data = data;
        this.key = key;
    }

    public void addChild(Node<G, T> child) {
        child.setMaxLength(this.maxLength + 1);
        this.children.add(child);
    }

    public Node<G, T> childWithHighestMaxLength()
    {
        int max =0;
        Node<G, T> result = null;
        for(Node<G, T> child: this.children)
        {
            if(child.maxLength > max)
            {
                max = child.maxLength;
                result = child;
            }
        }
        return result;
    }

    public boolean isLeaf() { return (this.children.size() == 0); }

//getters and setters
    public G getData() { return data; }

    public T getKey() { return key; }

    public ArrayList<Node<G, T>> getChildren() { return children; }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}