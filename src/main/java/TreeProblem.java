import java.io.File;
import java.util.List;

public class TreeProblem {
    public static void main(String[] args) {
        ListingOptions listingOptions = new ListingOptions();
        File directoryPath = new File("src");
        if (directoryPath.exists() && directoryPath.isDirectory()) {
            System.out.println(directoryPath.getName());
            List<File> files  = List.of(directoryPath.listFiles());
            List<File> directories = listingOptions.getDirectories(files);
            listingOptions.printDirectoriesAndFiles(files, 0, 0);
            System.out.println("\n-----Only Directories-----\n");
            System.out.println(directoryPath.getName());
            listingOptions.printDirectories(directories,0,0);
        }
        System.out.println("\n-------Listing with Relative Path\n");
        listingOptions.printWithRelativePath("src");
        System.out.println("\n-------Listing without Indentation\n");
        listingOptions.printWithoutIndent("src");

    }
}
