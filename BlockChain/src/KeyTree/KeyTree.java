package KeyTree;

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
    public Iterator<DataType> iterator() {
        return new KeyTreeIterator<DataType>(this.root);
    }

    public Node<DataType> getNodeWithKey(String key) throws Exception
    {
        Node<DataType> result = searchForKeyRecursive(this.root.getChildren(), key);
        if(result == null) throw new Exception("node with this key don't exist");
        return  result;
    }

    private Node<DataType> searchForKeyRecursive(List<Node<DataType>> nodes, String key)
    {
        for(Node<DataType> node: nodes)
        {
            if(node.getKey().equals(key)) return node;
            if(!node.isLeaf()) return searchForKeyRecursive(node.getChildren(), key);
        }
        return null;
    }

    public void addChildToKeyNode(Node<DataType> child, String key) throws Exception
    {
        getNodeWithKey(key).addChild(child);
    }
}
