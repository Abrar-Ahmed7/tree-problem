import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListingOptions {

    public List<String> getTreeString(List<StringBuilder> result) {
        return result.stream().map(StringBuilder::toString).collect(Collectors.toList());
    }

    public List getTree(File file, DirConditions dirConditions, Map<String, Boolean> conditions) {
        TreeNode treeNode = new TreeNode();
        TreeNode<File> dirTree = treeNode.rootDirTree(file, dirConditions);
        if (conditions.get("xmlTree")) {
            return getTreeString(treeNode.getXmlDirTree(dirTree));
        } else if (conditions.get("jsonTree")) {
            return getTreeString(treeNode.getJsonDirTree(dirTree));
        } else {
            return getTreeString(treeNode.getDirectoryTree(dirTree, conditions));
        }
    }

    public List<String> printWithoutIndent(String path) {
        List<String> a = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(Path.of(path))) {
            stream.forEach(s -> a.add(String.valueOf(s)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
}
