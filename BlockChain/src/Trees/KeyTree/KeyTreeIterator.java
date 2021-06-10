package Trees.KeyTree;

import java.util.Iterator;

public class KeyTreeIterator<G, T extends Comparable<T>> implements Iterator<G>
{
    private Node<G, T> cursor;

    @Override
    public boolean hasNext() { return this.cursor!=null; }

    public KeyTreeIterator(Node<G, T> root) { this.cursor = root; }

    @Override
    public G next()
    {
        G result = this.cursor.getData();
        this.cursor = cursor.childWithHighestMaxLength();
        return result;
    }
}
