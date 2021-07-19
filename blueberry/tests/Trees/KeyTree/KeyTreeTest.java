package Trees.KeyTree;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTreeTest {

    @DisplayName("Check insertion of key into a empty tree")
    @Test
    public void CheckRootKeyInsertion() throws Exception
    {
        KeyTree<Integer, String> tree = new KeyTree<>(1, "foo");
        assertEquals(tree.getNodeWithKey("foo").getData(), 1);
    }

    @DisplayName("check insertion for childs and structure integrity")
    @Test
    public void CheckKeyInsertion() throws Exception
    {
        KeyTree<String, Integer> tree = new KeyTree<>("sic ", 10);
        tree.add("mundus ", 23, 10);
        tree.add("creatus ", 4, 23);
        tree.add("est", 14, 4);
        StringBuilder testcase = new StringBuilder();
        for (String data: tree) testcase.append(data);
        assertEquals("sic mundus creatus est", testcase.toString());
    }

    @DisplayName("Check for Exception throw")
    @Test
    public void CheckForInvalidKey()
    {
        KeyTree<String, Integer> tree = new KeyTree<>("sic ", 10);

        Exception exception = assertThrows(
                Exception.class,
                () -> tree.add("mundus ", 23, 11));
    }
}