package KeyTree;

import java.util.Iterator;

public class KeyTreeIterator<DataType> implements Iterator<DataType>
{
    private final Node<DataType> cursor;

    @Override
    public boolean hasNext()
    {
        return this.cursor.isLeaf();
    }

    public KeyTreeIterator(Node<DataType> root)
    {
        this.cursor = root;
    }

    @Override
    public DataType next()
    {
        try { return cursor.childWithHighestMaxLength().getData(); }
        catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
