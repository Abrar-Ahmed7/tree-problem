import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TreeProblemTest {

    @Test
    public void itShouldReturnDirectoriesAndFiles() {
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("resources",
                "|--dummy",
                "|\t|--dummy.txt",
                "|\t|--for",
                "|\t|\t|--test",
                "|\t|\t\t|--dummy.txt",
                "|\t|--dummy2.txt",
                "|--dummy.txt");

        //When
        File file = new File("src/test/resources");
        TreeNode<File> dirTree = treeNode.rootDirTree(file,false);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, false);

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
        List<String> expectedOutput = Arrays.asList("resources",
                "|--dummy",
                "\t|--for",
                "\t\t|--test");

        //When
        File file = new File("src/test/resources");
        TreeNode<File> dirTree = treeNode.rootDirTree(file,true);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, false);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnDirectoriesAndFilesRelativePath(){
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("src/test/resources",
                "|--src/test/resources/dummy",
                "|\t|--src/test/resources/dummy/dummy.txt",
                "|\t|--src/test/resources/dummy/for",
                "|\t|\t|--src/test/resources/dummy/for/test",
                "|\t|\t\t|--src/test/resources/dummy/for/test/dummy.txt",
                "|\t|--src/test/resources/dummy/dummy2.txt",
                "|--src/test/resources/dummy.txt");

        //When
        File file = new File("src/test/resources");
        TreeNode<File> dirTree = treeNode.rootDirTree(file,false);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, true);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnDirectoriesRelativePath(){

        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("src/test/resources",
                "|--src/test/resources/dummy",
                "\t|--src/test/resources/dummy/for",
                "\t\t|--src/test/resources/dummy/for/test");

        //When
        File file = new File("src/test/resources");
        TreeNode<File> dirTree = treeNode.rootDirTree(file,true);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, true);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    public void createDirectories(){
        File theDir = new File(String.valueOf(Path.of("src","test","resources","dummy","for","test")));
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        try {
            File newFile = new File("src/test/resources/dummy.txt");
            File newFile1 = new File("src/test/resources/dummy/dummy.txt");
            File newFile2 = new File("src/test/resources/dummy/dummy2.txt");
            File newFile3 = new File("src/test/resources/dummy/for/test/dummy.txt");
            newFile.createNewFile();
            newFile1.createNewFile();
            newFile2.createNewFile();
            newFile3.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDirectory(File file){
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }
}
