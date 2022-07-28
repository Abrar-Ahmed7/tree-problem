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

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("resources",
                "|--src",
                "\t|--main",
                "\t\t|--java",
                "\t\t|\t|--in",
                "\t\t|\t\t|--Grader.java",
                "\t\t|\t\t|--Grade.java",
                "\t\t|\t\t|--Student.java",
                "\t\t|--resources");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(false);
        conditions.put("relativePath", false);
        conditions.put("viewPermission", false);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        ListingOptions listingOptions = new ListingOptions();
        List<String> resultantOutput = listingOptions.getTree(file, dirConditions, conditions);

        //Then
        assertEquals(expectedOutput, resultantOutput);

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnDirectories() {

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("resources",
                "|--src",
                "\t|--main",
                "\t\t|--java",
                "\t\t|\t|--in",
                "\t\t|--resources");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(true);
        conditions.put("relativePath", false);
        conditions.put("viewPermission", false);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        ListingOptions listingOptions = new ListingOptions();
        List<String> resultantOutput = listingOptions.getTree(file, dirConditions, conditions);

        //Then
        assertEquals(expectedOutput, resultantOutput);

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnDirectoriesAndFilesRelativePath() {

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("src/test/resources",
                "|--src/test/resources/src",
                "\t|--src/test/resources/src/main",
                "\t\t|--src/test/resources/src/main/java",
                "\t\t|\t|--src/test/resources/src/main/java/in",
                "\t\t|\t\t|--src/test/resources/src/main/java/in/Grader.java",
                "\t\t|\t\t|--src/test/resources/src/main/java/in/Grade.java",
                "\t\t|\t\t|--src/test/resources/src/main/java/in/Student.java",
                "\t\t|--src/test/resources/src/main/resources");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(false);
        conditions.put("relativePath", true);
        conditions.put("viewPermission", false);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        ListingOptions listingOptions = new ListingOptions();
        List<String> resultantOutput = listingOptions.getTree(file, dirConditions, conditions);

        //Then
        assertEquals(expectedOutput, resultantOutput);


        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnDirectoriesRelativePath() {

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("src/test/resources",
                "|--src/test/resources/src",
                "\t|--src/test/resources/src/main",
                "\t\t|--src/test/resources/src/main/java",
                "\t\t|\t|--src/test/resources/src/main/java/in",
                "\t\t|--src/test/resources/src/main/resources");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(true);
        conditions.put("relativePath", true);
        conditions.put("viewPermission", false);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        ListingOptions listingOptions = new ListingOptions();
        List<String> resultantOutput = listingOptions.getTree(file, dirConditions, conditions);

        //Then
        assertEquals(expectedOutput, resultantOutput);

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnPermissionsOfDirectoriesAndFiles() {

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("[drwx]resources",
                "|--[drwx]src",
                "\t|--[drwx]main",
                "\t\t|--[drwx]java",
                "\t\t|\t|--[drwx]in",
                "\t\t|\t\t|--[-rw--]Grader.java",
                "\t\t|\t\t|--[-rw--]Grade.java",
                "\t\t|\t\t|--[-rw--]Student.java",
                "\t\t|--[drwx]resources");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        Map<String, Boolean> conditions = new HashMap<>();
        dirConditions.setOnlyDir(false);
        conditions.put("relativePath", false);
        conditions.put("viewPermission", true);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        ListingOptions listingOptions = new ListingOptions();
        List<String> resultantOutput = listingOptions.getTree(file, dirConditions, conditions);

        //Then
        assertEquals(expectedOutput, resultantOutput);

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    @Test
    public void itShouldReturnXmlTree() {
        TreeNode treeNode = new TreeNode();

        //Creating Directories and Files
        createDirectories();

        //Given
        List<String> expectedOutput = Arrays.asList("<directory name=\"resources\">",
                "\t<directory name=\"src\">",
                "\t\t<directory name=\"main\">",
                "\t\t\t<directory name=\"java\">",
                "\t\t\t\t<directory name=\"in\">",
                "\t\t\t\t\t<file name=\"Grader.java\"></file>",
                "\t\t\t\t\t<file name=\"Grade.java\"></file>",
                "\t\t\t\t\t<file name=\"Student.java\"></file>",
                "\t\t\t\t</directory>",
                "\t\t\t</directory>",
                "\t\t\t<directory name=\"resources\">",
                "\t\t\t</directory>",
                "\t\t</directory>",
                "\t</directory>",
                "</directory>");

        //When
        File file = new File("src/test/resources");
        DirConditions dirConditions = new DirConditions();
        dirConditions.setOnlyDir(false);
        TreeNode<File> dirTree = treeNode.rootDirTree(file, dirConditions);
        List<StringBuilder> xmlTree = treeNode.getXmlDirTree(dirTree);
        ListingOptions listingOptions = new ListingOptions();
        List<String> resultantOutput = listingOptions.getTreeString(xmlTree);

        //Then
        assertEquals(expectedOutput, resultantOutput);

        //Deleting directories and files
        deleteDirectory(new File("src/test/resources"));
    }

    public void createDirectories() {
        File theDir = new File(String.valueOf(Path.of("src", "test", "resources", "src","main","java","in")));
        File theDir2 = new File(String.valueOf(Path.of("src", "test", "resources", "src","main","resources")));
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        if (!theDir2.exists()){
            theDir2.mkdirs();
        }

        try {
            File newFile = new File("src/test/resources/src/main/java/in/Grade.java");
            File newFile1 = new File("src/test/resources/src/main/java/in/Grader.java");
            File newFile2 = new File("src/test/resources/src/main/java/in/Student.java");
            newFile.createNewFile();
            newFile1.createNewFile();
            newFile2.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDirectory(File file) {
        for (File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                deleteDirectory(subFile);
            }
            subFile.delete();
        }
    }
}
