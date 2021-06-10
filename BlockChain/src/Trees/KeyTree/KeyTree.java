package Trees.KeyTree;

import java.util.Iterator;
import java.util.List;

public class KeyTree<G, T extends Comparable<T>> implements Iterable<G>
{
    private final Node<G, T> root;

    public KeyTree(G data, T key)
    {
        this.root = new Node<>(data, key);
    }

    @Override
    public Iterator<G> iterator()
    {
        return new KeyTreeIterator<>(this.root);
    }

    public Node<G, T> getNodeWithKey(T key) throws Exception
    {
        if(this.root.getKey().equals(key)) return root;
        Node<G, T> result = searchForKeyRecursive(this.root.getChildren(), key);
        if(result == null) throw new Exception("node with this key don't exist");
        return  result;
    }

    private Node<G, T> searchForKeyRecursive(List<Node<G, T>> nodes, T key)
    {
        for(Node<G, T> node: nodes)
        {
            if(node.getKey().equals(key)) return node;
            if(!node.isLeaf())
            {
                Node<G, T> result = searchForKeyRecursive(node.getChildren(), key);
                if(result != null) return result;
            }
        }
        return null;
    }

    public void add(G data, T childKey, T parentKey) throws Exception
    {
        getNodeWithKey(parentKey).addChild(new Node<>(data, childKey));
    }

    public G getLastData()
    {
        G result = this.root.getData();
        for(G item: this) { result = item; }
        return result;
    }
}
