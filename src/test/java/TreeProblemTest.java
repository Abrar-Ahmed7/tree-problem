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
        File theDir = new File(String.valueOf(Path.of("src","test","resource","dummy","for","test")));
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        try {
            File newFile = new File("src/test/resource/dummy.txt");
            File newFile1 = new File("src/test/resource/dummy/dummy.txt");
            File newFile2 = new File("src/test/resource/dummy/dummy2.txt");
            File newFile3 = new File("src/test/resource/dummy/for/test/dummy.txt");
            newFile.createNewFile();
            newFile1.createNewFile();
            newFile2.createNewFile();
            newFile3.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Given
        List<String> expectedOutput = Arrays.asList("resource",
                "|--dummy",
                "|\t|--dummy.txt",
                "|\t|--for",
                "|\t|\t|--test",
                "|\t|\t\t|--dummy.txt",
                "|\t|--dummy2.txt",
                "|--dummy.txt");

        //When
        File file = new File("src/test/resource");
        TreeNode<File> dirTree = treeNode.createDirTree(file,false);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, false);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resource"));
    }

    @Test
    public void itShouldReturnDirectories() {
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        File theDir = new File(String.valueOf(Path.of("src","test","resource","dummy","for","test")));
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        try {
            File newFile = new File("src/test/resource/dummy.txt");
            File newFile1 = new File("src/test/resource/dummy/dummy.txt");
            File newFile2 = new File("src/test/resource/dummy/dummy2.txt");
            File newFile3 = new File("src/test/resource/dummy/for/test/dummy.txt");
            newFile.createNewFile();
            newFile1.createNewFile();
            newFile2.createNewFile();
            newFile3.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Given
        List<String> expectedOutput = Arrays.asList("resource",
                "|--dummy",
                "\t|--for",
                "\t\t|--test");

        //When
        File file = new File("src/test/resource");
        TreeNode<File> dirTree = treeNode.createDirTree(file,true);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, false);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resource"));
    }

    @Test
    public void itShouldReturnDirectoriesAndFilesRelativePath(){
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        File theDir = new File(String.valueOf(Path.of("src","test","resource","dummy","for","test")));
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        try {
            File newFile = new File("src/test/resource/dummy.txt");
            File newFile1 = new File("src/test/resource/dummy/dummy.txt");
            File newFile2 = new File("src/test/resource/dummy/dummy2.txt");
            File newFile3 = new File("src/test/resource/dummy/for/test/dummy.txt");
            newFile.createNewFile();
            newFile1.createNewFile();
            newFile2.createNewFile();
            newFile3.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Given
        List<String> expectedOutput = Arrays.asList("src/test/resource",
                "|--src/test/resource/dummy",
                "|\t|--src/test/resource/dummy/dummy.txt",
                "|\t|--src/test/resource/dummy/for",
                "|\t|\t|--src/test/resource/dummy/for/test",
                "|\t|\t\t|--src/test/resource/dummy/for/test/dummy.txt",
                "|\t|--src/test/resource/dummy/dummy2.txt",
                "|--src/test/resource/dummy.txt");

        //When
        File file = new File("src/test/resource");
        TreeNode<File> dirTree = treeNode.createDirTree(file,false);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, true);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resource"));
    }

    @Test
    public void itShouldReturnDirectoriesRelativePath(){

        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        File theDir = new File(String.valueOf(Path.of("src","test","resource","dummy","for","test")));
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        try {
            File newFile = new File("src/test/resource/dummy.txt");
            File newFile1 = new File("src/test/resource/dummy/dummy.txt");
            File newFile2 = new File("src/test/resource/dummy/dummy2.txt");
            File newFile3 = new File("src/test/resource/dummy/for/test/dummy.txt");
            newFile.createNewFile();
            newFile1.createNewFile();
            newFile2.createNewFile();
            newFile3.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Given
        List<String> expectedOutput = Arrays.asList("src/test/resource",
                "|--src/test/resource/dummy",
                "\t|--src/test/resource/dummy/for",
                "\t\t|--src/test/resource/dummy/for/test");

        //When
        File file = new File("src/test/resource");
        TreeNode<File> dirTree = treeNode.createDirTree(file,true);
        List<StringBuilder> resultantOutput = treeNode.getDirectoryTree(dirTree, true);

        //Then
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i), resultantOutput.get(i).toString());
        }

        //Deleting directories and files
        deleteDirectory(new File("src/test/resource"));
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
