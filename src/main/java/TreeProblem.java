import org.apache.commons.cli.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TreeProblem {
    public static void main(String[] args) {

//        Option tree = new Option("tree", false, "fetches directories and files");
        Option dirArg = new Option("d", false, "fetches directories alone");
        Option relativePathArg = new Option("f", false, "fetches relative path of directories and files");
        Option viewPermissionsArg = new Option("p", false, "fetches permissions of directories and files");
        Option levelArg = new Option("L", false, "fetches directories and files of given level");
        Option xmlArg = new Option("X", false, "fetches XML tree for directories and files");
        Option jsonArg = new Option("J", false, "fetches fetches JSON directories and files");

        CommandLineParser clp = new DefaultParser();
        Options options = new Options();
//        options.addOption(tree);
        options.addOption(dirArg);
        options.addOption(relativePathArg);
        options.addOption(viewPermissionsArg);
        options.addOption(levelArg);
        options.addOption(xmlArg);
        options.addOption(jsonArg);
        File file = new File(args[args.length - 1]);
        Map<String, Boolean> conditions = new HashMap<>();
        DirConditions dirConditions = new DirConditions();
        dirConditions.setOnlyDir(false);
        conditions.put("relativePath", false);
        conditions.put("viewPermission", false);
        conditions.put("xmlTree", false);
        conditions.put("jsonTree", false);
        try {
            CommandLine commandLine = clp.parse(options, args);
            if (commandLine.hasOption(dirArg.getOpt())) {
                dirConditions.setOnlyDir(true);
            }
            if (commandLine.hasOption(relativePathArg.getOpt())) {
                conditions.put("relativePath", true);
            }
            if (commandLine.hasOption(viewPermissionsArg.getOpt())) {
                conditions.put("viewPermission", true);
            }
            if (commandLine.hasOption(levelArg.getOpt())) {
                dirConditions.setLevel(Integer.parseInt(args[getLevelArgIndex(args) + 1]));
            }
            if (commandLine.hasOption(xmlArg.getOpt())) {
                conditions.put("xmlTree", true);
            }
            if (commandLine.hasOption(jsonArg.getOpt())) {
                conditions.put("jsonTree", true);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        TreeNode treeNode = new TreeNode();
//        TreeNodeXml treeNodeXml = new TreeNodeXml<>();
//        TreeNode<File> dirTree = treeNode.rootDirTree(file, dirConditions);
//        List<StringBuilder> xmlResult = treeNode.getDirectoryTree(dirTree, conditions);
        ListingOptions listingOptions = new ListingOptions();
        listingOptions.getTree(file, dirConditions, conditions).forEach(System.out::println);
//        xmlResult.forEach(System.out::println);
    }

    private static int getLevelArgIndex(String[] args) {
        int index = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-L")) {
                index = i;
                break;
            }
        }
        return index;
    }
}