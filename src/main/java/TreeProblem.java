import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TreeProblem {
    public static void main(String[] args) {

        /* Todo:
            - CLI
            - Rest of the stories and test cases
         */

        File file = new File("src");
        TreeNode treeNode = new TreeNode();
        TreeNode<File> dirTree = treeNode.createDirTree(file,false);
        List<StringBuilder> result = treeNode.getDirectoryTree(dirTree, false);
        result.forEach(System.out::println);
        System.out.println(treeNode.getDirCount()+", "+treeNode.getFilesCount());

        // Working on it
        System.out.println("\n-------Listing without Indentation\n");
        ListingOptions listingOptions = new ListingOptions();
        listingOptions.printWithoutIndent("src");
        File allDir = new File("src");
        List<File> siblings = new ArrayList<>();
        if (allDir.isDirectory()) {
            List<File> files = List.of(allDir.listFiles());
            files.forEach(file1 -> {
                siblings.add(file1);
                if (file1.isDirectory()) {
                    List<File> files1 = List.of(file1.listFiles());
                }
            });
        }
    }

}
