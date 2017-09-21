package hp.smart.whole.structure;

import gnu.trove.map.hash.THashMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/**
 * @Author: SMA
 * @Date: 2017-09-14 23:02
 * @Explain:
 */
public class Tree<T> implements Serializable {
    // N叉树
    protected TreeNode<T> root = null;

    public Tree() {
        root = new TreeNode<T>();
    }

    public Tree(Map<String, T> map) {
        root = new TreeNode<T>();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            insert(entry.getKey(), entry.getValue());
        }
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void insert(String var, T value) {
        TreeNode<T> now = root;
        now.addChildren(var, new TreeNode<T>(value));
    }

    public void insert(T value, TreeSet<String> vars) {
        String[] varsStr = new String[vars.size()];
        Iterator<String> iterator = vars.iterator();
        int post = 0;
        while (iterator.hasNext()) {
            varsStr[post] = iterator.next();
            post++;
        }
        insert(value, varsStr);
    }

    public void insert(T value, String... vars) {
        if (vars != null) {
            TreeNode<T> now = root;
            for (String var : vars) {
                now = now.nextBuild(var);
            }
            now.setValue(value);
        }
    }

    public T find(String var) {
        TreeNode<T> now = root;
        String[] vars = var.split("\\&");
        for (String key : vars) {
            if (now != null) {
                now = now.next(key);
            } else {
                return null;
            }
        }
        if (now != null) {
            return now.value;
        }
        return null;
    }

    public T find(String... vars) {
        TreeNode<T> now = root;
        for (String key : vars) {
            if (now != null) {
                now = now.next(key);
            } else {
                return null;
            }
        }
        if (now != null) {
            return now.value;
        }
        return null;
    }

    public T find(Collection<String> vars) {
        TreeNode<T> now = root;
        for (String key : vars) {
            if (now != null) {
                now = now.next(key);
            } else {
                return null;
            }
        }
        if (now != null) {
            return now.value;
        }
        return null;
    }

    public TreeNode<T> get(String var) {
        TreeNode<T> now = root;
        if (now != null) {
            now = now.next(var);
        }
        return now;

    }

    /**
     * -------------------------------------
     * TreeNode
     *
     * @param <T>
     */
    public static class TreeNode<T> implements Serializable {
        private Map<String, TreeNode<T>> children;
        private T value;

        public TreeNode() {
            children = new THashMap<String, TreeNode<T>>();
            value = null;
        }

        public TreeNode(T value) {
            children = new THashMap<String, TreeNode<T>>();
            this.value = value;
        }

        public void addChildren(String var, TreeNode<T> treeNode) {
            this.children.put(var, treeNode);
        }

        public TreeNode<T> nextBuild(String var) {
            if (!this.children.containsKey(var)) {
                TreeNode<T> node = new TreeNode<T>();
                children.put(var, node);
                return node;
            }
            return this.children.get(var);
        }

        public TreeNode<T> next(String var) {
            return children.get(var);
        }

        @Override
        public String toString() {
            return "TrieNode{" +
                    "children=" + children +
                    ", value=" + value +
                    '}';
        }

        public Map<String, TreeNode<T>> getChildren() {
            return children;
        }

        public void setChildren(Map<String, TreeNode<T>> children) {
            this.children = children;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Tree<String> tree = new Tree<>();
        tree.insert("123", "1", "2", "3");
        tree.insert("13", "1", "3");
        tree.insert("22", "2", "2");
        tree.insert("32", "3", "2");
        tree.insert("124", "1", "2", "4");
        System.out.println(tree);
        System.out.println(tree.find("1", "2"));
    }
}
