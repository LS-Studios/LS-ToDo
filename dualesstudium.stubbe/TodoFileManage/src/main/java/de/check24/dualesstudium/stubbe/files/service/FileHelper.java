package de.check24.dualesstudium.stubbe.files.service;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileHelper {

    private FileHelper() {}

    public static boolean checkPathValidity(String path) {
        File file = new File(path);

        if (!file.isDirectory()) {
            file = file.getParentFile();
        }

        return file != null && file.exists() && !file.getPath().equals("/");
    }

    public static boolean checkFileValidity(String filePath) {
        File myObj = new File(filePath);

        try (Scanner scanner = new Scanner(myObj)) {
            return true;
        }
        catch (FileNotFoundException e) {
            return false;
        }
    }

    public static boolean checkFileFormatValidity(String enteredFilePath, List<TodoFileHandler> fileHandlers) {
        String[] split = enteredFilePath.split("\\.");

        for (TodoFileHandler fileHandler : fileHandlers) {
            String fileFormat = fileHandler.getFileFormat();

            if (split[split.length - 1].equals(fileFormat)) {
                return true;
            }
        }

        return false;
    }

    public static List<TodoFileHandler> getTodoFileHandlers(String sourceDir) {
        List<TodoFileHandler> fileHandlers = new ArrayList<>();

        File dir = new File(sourceDir);
        File[] filesInDir = dir.listFiles();

        if (filesInDir != null) {
            for (File file : filesInDir) {
                String fileFormat = FilenameUtils.getExtension(file.getAbsolutePath());
                if (fileFormat.equals("jar")) {
                    fileHandlers.addAll(getTodoFileHandlersFromFile(file));
                }
            }
        }

        return fileHandlers;
    }

    private static List<TodoFileHandler> getTodoFileHandlersFromFile(File file) {
        List<TodoFileHandler> fileHandlers = new ArrayList<>();

        for (Class<TodoFileHandler> cls : getClassesFromJarFile(file)) {
            if (cls.getSuperclass() == TodoFileHandler.class) {
                try {
                    TodoFileHandler fileHandler = cls.getConstructor().newInstance();
                    fileHandlers.add(fileHandler);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileHandlers;
    }

    private static Set<Class<TodoFileHandler>> getClassesFromJarFile(File givenFile) {
        Set<String> classNames = getClassNamesFromJarFile(givenFile);

        Set<Class<TodoFileHandler>> classes = new HashSet<>(classNames.size());

        try (URLClassLoader cls = URLClassLoader.newInstance(new URL[]{givenFile.toURI().toURL()}, ClassLoader.getSystemClassLoader())){
            for (String name : classNames) {
                try {
                    Class<TodoFileHandler> todoClass = (Class<TodoFileHandler>) Class.forName(name, true, cls);
                    classes.add(todoClass);
                } catch (NoClassDefFoundError | IllegalAccessError e) {
                    System.out.print("");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static Set<String> getClassNamesFromJarFile(File givenFile) {
        Set<String> classNames = new HashSet<>();
        try (JarFile jarFile = new JarFile(givenFile)) {
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName()
                            .replace("/", ".")
                            .replace(".class", "");
                    classNames.add(className);
                }
            }
            return classNames;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Set.of();
    }
}
