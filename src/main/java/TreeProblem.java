import org.apache.commons.cli.*;

import java.io.File;
import java.util.List;

public class TreeProblem {
    public static void main(String[] args) {

        /* Todo:
            - CLI
            - Rest of the stories and test cases
         */

        Option tree = new Option("tree", false, "fetches directories and files");
        Option directoryArg = new Option("d",  false, "fetches directories alone");
        Option relativePathArg = new Option("f", false, "fetches relative path of directories and files");
        CommandLineParser clp = new DefaultParser();
        Options options = new Options();
        options.addOption(tree);
        options.addOption(directoryArg);
        options.addOption(relativePathArg);
        File file = new File(args[args.length - 1]);
        Boolean onlyDirectories = false;
        Boolean relativePath = false;
        try {
            CommandLine commandLine = clp.parse(options, args);
            if (!commandLine.hasOption(tree.getOpt())) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("-tree [Mandatory arg]", options);
                return;
            }
            if (commandLine.hasOption(directoryArg.getOpt())) {
                onlyDirectories=true;
            }
            if (commandLine.hasOption(relativePathArg.getOpt())) {
                relativePath=true;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

//        File file = new File("src");
        TreeNode treeNode = new TreeNode();
        TreeNode<File> dirTree = treeNode.rootDirTree(file,onlyDirectories);
        List<StringBuilder> result = treeNode.getDirectoryTree(dirTree, relativePath);
        result.forEach(System.out::println);
        System.out.println(treeNode.getDirCount()+", "+treeNode.getFilesCount());
    }
}
