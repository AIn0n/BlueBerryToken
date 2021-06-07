package KeyTree;

import java.util.ArrayList;

public class Node<DataType> {
    private final DataType data;
    private final String key;
    private final ArrayList<Node<DataType>> children = new ArrayList<>();
    private Node<DataType> parent = null;
    private int maxLength = 0;


    Node(DataType data, String key) {
        this.data = data;
        this.key = key;
    }

    public void addChild(Node<DataType> child) {
        child.setParent(this);
        child.setMaxLength(this.maxLength + 1);
        this.children.add(child);
    }

    public Node<DataType> childWithHighestMaxLength() throws Exception
    {
        if(isLeaf()) throw new Exception("Node is leaf");

        int max =0;
        Node<DataType> result = null;
        for(Node<DataType> child: this.children)
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
    //spytaj o to czy jest sens stawiac gettera i settera zamiast zrobc zmienna publiczna
    public void setParent(Node<DataType> parent) { this.parent = parent; }

    public Node<DataType> getParent() { return parent; }

    public DataType getData() { return data; }

    public String getKey() { return key; }

    public ArrayList<Node<DataType>> getChildren() { return children; }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
    public void getMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
