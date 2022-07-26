import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public TreeNode<File> rootDirTree(File folder, DirConditions dirConditions) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        TreeNode<File> dirRoot = new TreeNode<File>(folder);
        List<File> files = List.of(folder.listFiles());
        files.forEach(file -> {
            String[] fileNames = file.toString().split("/");
            if (dirConditions.getLevel() > 0 && fileNames.length <= dirConditions.getLevel() + 1) {
                if (file.isDirectory()) {
                    dirCount++;
                    appendDirTree(file, dirRoot, dirConditions);
                } else {
                    filesCount++;
                    appendFile(file, dirRoot, dirConditions.getOnlyDir());
                }
            }
            if (dirConditions.getLevel() == 0) {
                if (file.isDirectory()) {
                    dirCount++;
                    appendDirTree(file, dirRoot, dirConditions);
                } else {
                    filesCount++;
                    appendFile(file, dirRoot, dirConditions.getOnlyDir());
                }
            }
        });
        return dirRoot;
    }

    public void appendDirTree(File folder, TreeNode<File> dirRoot, DirConditions dirConditions) {
        dirRoot.addChild(folder);
//        System.out.println(level);
        List<File> files = List.of(folder.listFiles());
        files.forEach(file -> {
            String[] fileNames = file.toString().split("/");
            if (fileNames.length <= dirConditions.getLevel() + 1) {
                if (file.isDirectory()) {
                    dirCount++;
                    appendDirTree(file, dirRoot.children.get(dirRoot.children.size() - 1), dirConditions);
                } else {
                    filesCount++;
                    appendFile(file, dirRoot.children.get(dirRoot.children.size() - 1), dirConditions.getOnlyDir());
                }
            }
            if (dirConditions.getLevel() == 0) {
                if (file.isDirectory()) {
                    dirCount++;
                    appendDirTree(file, dirRoot.children.get(dirRoot.children.size() - 1), dirConditions);
                } else {
                    filesCount++;
                    appendFile(file, dirRoot.children.get(dirRoot.children.size() - 1), dirConditions.getOnlyDir());
                }
            }
        });
    }

    public void appendFile(File file, TreeNode<File> fileNode, Boolean onlyDirectories) {
        if (onlyDirectories) {
            return;
        }
        fileNode.addChild(file);
    }

    public void addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
    }

    public List<StringBuilder> getDirectoryTree(TreeNode<File> tree, Map<String, Boolean> conditions) {
        List<StringBuilder> result = new LinkedList<>();
        if (conditions.get("relativePath") && conditions.get("viewPermission")) {
            addRelativePathAndPermission(result, tree.data);
        } else if (conditions.get("relativePath") && !conditions.get("viewPermission")) {
            addRelativePath(result, tree.data);
        } else if (!conditions.get("relativePath") && conditions.get("viewPermission")) {
            addPermission(result, tree.data);
        } else {
            result.add(new StringBuilder().append(tree.data.getName()));
        }
        Iterator<TreeNode<File>> iterator = tree.children.iterator();
        while (iterator.hasNext()) {
            List<StringBuilder> subtree = getDirectoryTree(iterator.next(), conditions);
            if (iterator.hasNext()) {
                addSubtree(result, subtree);
            } else {
                addLastSubtree(result, subtree);
            }
        }
        return result;
    }

    public List<StringBuilder> getXmlDirTree(TreeNode<File> tree) {
        List<StringBuilder> result = new LinkedList<>();
        if (tree.data.isDirectory()) {
            if (List.of(tree.data.listFiles()).isEmpty()) {
                result.add(new StringBuilder().append("<directory name=\"").append(tree.data.getName()).append("\">"));
                result.add(new StringBuilder().append("</directory>"));
            } else {
                result.add(new StringBuilder().append("<directory name=\"").append(tree.data.getName()).append("\">"));
            }
        } else {
            result.add(new StringBuilder().append("<file name=\"").append(tree.data.getName()).append("\"></file>"));
        }

        Iterator<TreeNode<File>> iterator = tree.children.iterator();
        while (iterator.hasNext()) {
            List<StringBuilder> subtree = getXmlDirTree(iterator.next());
            if (iterator.hasNext()) {
                addIndent(result, subtree);
            } else {
                addIndentAtLast(result, subtree);
                result.add(new StringBuilder("</directory>"));
            }
        }
        return result;
    }

    public List<StringBuilder> getJsonDirTree(TreeNode<File> tree) {
        List<StringBuilder> result = new LinkedList<>();
        if (tree.data.isDirectory()) {
            if (List.of(tree.data.listFiles()).isEmpty()) {
                result.add(new StringBuilder().append("{\"type\":\"directory\",\"name\":\"").append(tree.data.getName()).append("\"}"));
            } else {
                result.add(new StringBuilder().append("{\"type\":\"directory\",\"name\":\"").append(tree.data.getName()).append("\",\"contents\":["));
            }
        } else {
            result.add(new StringBuilder().append("{\"type\":\"file\",\"name\":\"").append(tree.data.getName()).append("\"}"));
        }
        Iterator<TreeNode<File>> iterator = tree.children.iterator();
        while (iterator.hasNext()) {
            List<StringBuilder> subtree = getJsonDirTree(iterator.next());
            if (iterator.hasNext()) {
                addIndent(result, subtree);
            } else {
                addIndentAtLast(result, subtree);
                result.add(new StringBuilder("]}"));
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

    private void addIndent(List<StringBuilder> result, List<StringBuilder> subtree) {
        Iterator<StringBuilder> iterator = subtree.iterator();
        result.add(iterator.next().insert(0, "\t"));
        while (iterator.hasNext()) {
            result.add(iterator.next().insert(0, "\t"));
        }
    }

    private void addIndentAtLast(List<StringBuilder> result, List<StringBuilder> subtree) {
        Iterator<StringBuilder> iterator = subtree.iterator();
        result.add(iterator.next().insert(0, "\t"));
        while (iterator.hasNext()) {
            result.add(iterator.next().insert(0, "\t"));
        }
    }

    void addRelativePathAndPermission(List<StringBuilder> result, File data) {
        result.add(new StringBuilder().append(addPermissions(data)).append(data));
    }

    void addRelativePath(List<StringBuilder> result, File data) {
        result.add(new StringBuilder().append(data));
    }

    void addPermission(List<StringBuilder> result, File data) {
        result.add(new StringBuilder().append(addPermissions(data)).append(data.getName()));
    }

    private StringBuilder addPermissions(File data) {
        StringBuilder permissions = new StringBuilder();
        if (data.isDirectory()) {
            permissions.append("[d");
            if (data.canRead()) {
                permissions.append("r");
            }
            if (data.canWrite()) {
                permissions.append("w");
            }
            if (data.canExecute()) {
                permissions.append("x");
            }
            permissions.append("]");
        }
        if (data.isFile()) {
            permissions.append("[-");
            if (data.canRead()) {
                permissions.append("r");
            }
            if (data.canWrite()) {
                permissions.append("w");
            }
            if (data.canExecute()) {
                permissions.append("x");
            }
            permissions.append("--]");
        }
        return permissions;
    }

    public String getDirCount() {
        if (dirCount > 1) {
            return dirCount + " directories";
        }
        return dirCount + " directory";
    }

    public String getFilesCount() {
        if (filesCount > 1) {
            return filesCount + " files";
        }
        return filesCount + " file";
    }
}


