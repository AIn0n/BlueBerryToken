package Trees.KeyTree;

import java.util.ArrayList;

public class Node<DataType> {
    private final DataType data;
    private final String key;
    private final ArrayList<Node<DataType>> children = new ArrayList<>();
    private int maxLength = 0;


    Node(DataType data, String key) {
        this.data = data;
        this.key = key;
    }

    public void addChild(Node<DataType> child) {
        child.setMaxLength(this.maxLength + 1);
        this.children.add(child);
    }

    public Node<DataType> childWithHighestMaxLength()
    {
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
    public DataType getData() { return data; }

    public String getKey() { return key; }

    public ArrayList<Node<DataType>> getChildren() { return children; }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
