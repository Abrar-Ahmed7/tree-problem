import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> {
    public T data;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;
    public int dirCount;
    public int filesCount;

    public TreeNode() {
    }

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
        this.dirCount = 0;
        this.filesCount = 0;
    }

    public TreeNode<File> createDirTree(File folder, Boolean onlyDirectories) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        TreeNode<File> dirRoot = new TreeNode<File>(folder);
        List<File> files = List.of(folder.listFiles());
        files.forEach(file -> {
            if (file.isDirectory()) {
                appendDirTree(file, dirRoot, onlyDirectories);
                dirCount++;
            } else {
                appendFile(file, dirRoot, onlyDirectories);
                filesCount++;
            }
        });
        return dirRoot;
    }

    public void appendDirTree(File folder, TreeNode<File> dirRoot, Boolean onlyDirectories) {
        dirRoot.addChild(folder);
        List<File> files = List.of(folder.listFiles());
        files.forEach(file -> {
            if (file.isDirectory()) {
                appendDirTree(file, dirRoot.children.get(dirRoot.children.size() - 1), onlyDirectories);
                dirCount++;
            } else {
                appendFile(file, dirRoot.children.get(dirRoot.children.size() - 1), onlyDirectories);
                filesCount++;
            }
        });
    }

    public void appendFile(File file, TreeNode<File> fileNode, Boolean onlyDirectories) {
        if (onlyDirectories)
            return;
        fileNode.addChild(file);
    }

    public void addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
//        System.out.println(childNode.data);
    }

    public List<StringBuilder> getDirectoryTree(TreeNode<File> tree, Boolean relativePath) {
        List<StringBuilder> result = new LinkedList<>();
        if (relativePath)
            result.add(new StringBuilder().append(tree.data));
        else
            result.add(new StringBuilder().append(tree.data.getName()));
        Iterator<TreeNode<File>> iterator = tree.children.iterator();
        while (iterator.hasNext()) {
            List<StringBuilder> subtree = getDirectoryTree(iterator.next(), relativePath);
            if (iterator.hasNext()) {
                addSubtree(result, subtree);
            } else {
                addLastSubtree(result, subtree);
            }
        }
        return result;
    }

    private void addSubtree(List<StringBuilder> result, List<StringBuilder> subtree) {
        Iterator<StringBuilder> iterator = subtree.iterator();
        result.add(iterator.next().insert(0, "|--"));
        while (iterator.hasNext()) {
            result.add(iterator.next().insert(0, "|\t"));
        }
    }

    private void addLastSubtree(List<StringBuilder> result, List<StringBuilder> subtree) {
        Iterator<StringBuilder> iterator = subtree.iterator();
        result.add(iterator.next().insert(0, "|--"));
        while (iterator.hasNext()) {
            result.add(iterator.next().insert(0, "\t"));
        }
    }

    public String getDirCount() {
        if (dirCount > 1)
            return dirCount + " directories";
        return dirCount + " directory";
    }

    public String getFilesCount() {
        if (filesCount > 1)
            return filesCount + " files";
        return filesCount + " file";
    }
}


