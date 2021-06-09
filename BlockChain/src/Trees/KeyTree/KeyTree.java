package Trees.KeyTree;

import java.util.Iterator;
import java.util.List;

public class KeyTree<DataType> implements Iterable<DataType>
{
    Node<DataType> root;

    public KeyTree(DataType data, String key)
    {
        this.root = new Node<>(data, key);
    }

    @Override
    public Iterator<DataType> iterator()
    {
        return new KeyTreeIterator<>(this.root);
    }

    public Node<DataType> getNodeWithKey(String key) throws Exception
    {
        if(this.root.getKey().equals(key)) return root;
        Node<DataType> result = searchForKeyRecursive(this.root.getChildren(), key);
        if(result == null) throw new Exception("node with this key don't exist");
        return  result;
    }

    private Node<DataType> searchForKeyRecursive(List<Node<DataType>> nodes, String key)
    {
        for(Node<DataType> node: nodes)
        {
            if(node.getKey().equals(key)) return node;
            if(!node.isLeaf())
            {
                Node<DataType> result = searchForKeyRecursive(node.getChildren(), key);
                if(result != null) return result;
            }
        }
        return null;
    }

    public void add(DataType data, String childKey, String parentKey) throws Exception
    {
        getNodeWithKey(parentKey).addChild(new Node<>(data, childKey));
    }

    public DataType getLastData()
    {
        DataType result = this.root.getData();
        for(DataType item: this) { result = item; }
        return result;
    }
}
