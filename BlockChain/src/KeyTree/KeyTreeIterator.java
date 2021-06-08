package KeyTree;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class KeyTreeIterator<DataType> implements Iterator<DataType>
{
    private Node<DataType> cursor;

    @Override
    public boolean hasNext() { return this.cursor!=null; }

    public KeyTreeIterator(Node<DataType> root) { this.cursor = root; }

    @Override
    public DataType next()
    {
        DataType result = this.cursor.getData();
        this.cursor = cursor.childWithHighestMaxLength();
        return result;
    }
}
