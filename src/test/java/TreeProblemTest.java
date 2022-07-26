import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TreeProblemTest {

    @Test
    public void itShouldReturnDirectoriesAndFiles() {
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("resources", "|--dummy", "|\t|--dummy.txt", "|\t|--dummy2.txt", "|--dummy.txt");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(false);
        conditions.put("relativePath", false);
        conditions.put("viewPermission", false);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        TreeNode<File> dirTree = treeNode.rootDirTree(file, dirConditions);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, conditions);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnDirectories() {
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("resources", "|--dummy");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(true);
        conditions.put("relativePath", false);
        conditions.put("viewPermission", false);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        TreeNode<File> dirTree = treeNode.rootDirTree(file, dirConditions);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, conditions);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnDirectoriesAndFilesRelativePath() {
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("src/test/resources", "|--src/test/resources/dummy", "|\t|--src/test/resources/dummy/dummy.txt", "|\t|--src/test/resources/dummy/dummy2.txt", "|--src/test/resources/dummy.txt");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(false);
        conditions.put("relativePath", true);
        conditions.put("viewPermission", false);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        TreeNode<File> dirTree = treeNode.rootDirTree(file, dirConditions);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, conditions);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnDirectoriesRelativePath() {

        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("src/test/resources", "|--src/test/resources/dummy");

        //When

        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(true);
        conditions.put("relativePath", true);
        conditions.put("viewPermission", false);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        TreeNode<File> dirTree = treeNode.rootDirTree(file, dirConditions);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, conditions);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnPermissionsOfDirectoriesAndFiles() {
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("[drwx]resources", "|--[drwx]dummy", "|\t|--[-rw--]dummy.txt", "|\t|--[-rw--]dummy2.txt", "|--[-rw--]dummy.txt");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(false);
        conditions.put("relativePath", false);
        conditions.put("viewPermission", true);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        TreeNode<File> dirTree = treeNode.rootDirTree(file, dirConditions);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, conditions);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnXmlTree() {
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("<directory name=\"resources\">", "\t<directory name=\"dummy\">", "\t\t<file name=\"dummy.txt\"></file>", "\t\t<file name=\"dummy2.txt\"></file>", "\t</directory>", "\t<file name=\"dummy.txt\"></file>", "</directory>");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        dirConditions.setOnlyDir(false);
        TreeNode<File> dirTree = treeNode.rootDirTree(file, dirConditions);
        List<StringBuilder> resultantOutput = treeNode.getXmlDirTree(dirTree);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    public void createDirectories() {
        File theDir = new File(String.valueOf(Path.of("src", "test", "resources", "dummy")));
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        try {
            File newFile = new File("src/test/resources/dummy.txt");
            File newFile1 = new File("src/test/resources/dummy/dummy.txt");
            File newFile2 = new File("src/test/resources/dummy/dummy2.txt");
            newFile.createNewFile();
            newFile1.createNewFile();
            newFile2.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDirectory(File file) {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }
}
