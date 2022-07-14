import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListingOptions {
    public void printDirectoriesAndFiles(List<File> files, int index, int level){
        if (index == files.size())
            return;
        for (int i = 0; i <= level; i++) {
            System.out.print("\t|");
        }
        if (files.get(index).isFile())
            System.out.println("--"+files.get(index).getName());
        else if (files.get(index).isDirectory()) {
            System.out.println("-" + files.get(index).getName());
            printDirectoriesAndFiles(List.of(files.get(index).listFiles()),0,level + 1);
        }
        printDirectoriesAndFiles(files, ++index, level);
    }

    public void printDirectories(List<File> files, int index, int level){
        if (index == files.size())
            return;
        for (int i = 0; i <= level; i++) {
            System.out.print("\t|");
        }
        if (files.get(index).isDirectory()) {
            System.out.println("-" + files.get(index).getName());
            printDirectories(getDirectories(List.of(files.get(index).listFiles())),0,level + 1);
        }
        printDirectories(files, ++index, level);
    }

    public void printWithRelativePath(String path) {
        try (Stream<Path> stream = Files.walk(Path.of(path))) {
            stream.map(fileName->{
                    String[] fileNames = fileName.toString().split("/");
                return "\t|".repeat(Math.max(0, fileNames.length - 1)) +"-"+fileName;
                }).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printWithoutIndent(String path) {
        try (Stream<Path> stream = Files.walk(Path.of(path))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<File> getDirectories(List<File> files) {
        return files.stream().filter(File::isDirectory).collect(Collectors.toList());
    }
}
