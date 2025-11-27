package com.simplifyqa.codeeditor.helper;

import com.simplifyqa.pluginbase.codeeditor.annotations.SyncAction;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DuplicateIdChecker {
    private static final Logger logger = Logger.getLogger(DuplicateIdChecker.class.getName());
    private static final Map<String, Method> methodsList = new HashMap<>();
    private static boolean buildStatus = true;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {
        String packageName = "com.simplifyqa.codeeditor";
        List<Class<?>> classes = CustomPackageScanner.getClasses(packageName);
        logger.info("\u001B[1m\u001B[32m" + "[INFO]" + "\u001B[0m"+"Fetching Unique Ids from classes: " + classes);
        registerMethodsFromClass(classes);
        if (!buildStatus) {
            printErrorMessage();
            System.exit(1);
        }
        printSuccessMessage();
        System.exit(0);
    }

    private static void registerMethodsFromClass(List<Class<?>> classes) {
        for (Class<?> eachClass : classes) {
            for (Method method : eachClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(SyncAction.class)) {
                    if (!Modifier.isPublic(method.getModifiers())){
                        logger.log(Level.SEVERE, "\u001B[31m" + "METHOD: "+method.getName()+" is not public, but is annotated with @SyncAction, Please make it public." + "\u001B[0m");
                        buildStatus=false;
                    }
                    SyncAction annotation = method.getAnnotation((SyncAction.class));
                    String uniqueId = annotation.uniqueId();
                    if (methodsList.containsKey(uniqueId)) {
                        Method duplicateMethodInMap = methodsList.get(uniqueId);
                        System.out.println();
                        System.out.println("\u001B[31m" + "\t--------------------------------DUPLICATE UNIQUE ID's are found--------------------------------" + "\u001B[0m");
                        System.out.println("\u001B[36;1m" + "Unique ID : " + "\u001B[0m" + "\u001B[33;1m" + uniqueId + "\u001B[0m");
                        System.out.println("\u001B[36;1m" + "Methods   : " + "\u001B[0m" + "\u001B[33;1m" + duplicateMethodInMap.getName() + "\u001B[0m" + " and " + "\u001B[33;1m" + method.getName() + "\u001B[0m");
                        System.out.println("\u001B[36;1m" + "Classes   : " + "\u001B[0m" + "\u001B[33;1m" + duplicateMethodInMap.getDeclaringClass().getSimpleName() + "\u001B[0m" + " and " + "\u001B[33;1m" + method.getDeclaringClass().getSimpleName() + "\u001B[0m");
                        buildStatus = false;
                    } else {
                        methodsList.put(uniqueId, method);
                    }
                }
            }
        }
    }
    private static void printSuccessMessage() {
        String successBanner = ANSI_GREEN +
                "\t\t\t\t                                                           \n" +
                "\t\t\t\t                                                           \n" +
                "\t\t\t\t  ███████╗██╗   ██╗ ██████╗ ██████╗███████╗███████╗███████╗\n" +
                "\t\t\t\t  ██╔════╝██║   ██║██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝\n" +
                "\t\t\t\t  ███████╗██║   ██║██║     ██║     █████╗  ███████╗███████╗\n" +
                "\t\t\t\t  ╚════██║██║   ██║██║     ██║     ██╔══╝  ╚════██║╚════██║\n" +
                "\t\t\t\t  ███████║╚██████╔╝╚██████╗╚██████╗███████╗███████║███████║\n" +
                "\t\t\t\t  ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝\n" +
                "\t\t\t\t                                                           \n" +
                "\t\t\t\t                      BUILD SUCCESS                        \n" +
                "\t\t\t\t             No duplicate Unique Id is found               \n" +
                "\t\t\t\t                                                           \n" + ANSI_RESET;

        System.out.println(successBanner);
    }

    private static void printErrorMessage() {
        String errorBanner = ANSI_RED +
                "\t\t\t\t                                             \n" +
                "\t\t\t\t                                             \n" +
                "\t\t\t\t  ███████╗ █████╗ ██╗██╗     ███████╗██████╗ \n" +
                "\t\t\t\t  ██╔════╝██╔══██╗██║██║     ██╔════╝██╔══██╗\n" +
                "\t\t\t\t  █████╗  ███████║██║██║     █████╗  ██║  ██║\n" +
                "\t\t\t\t  ██╔══╝  ██╔══██║██║██║     ██╔══╝  ██║  ██║\n" +
                "\t\t\t\t  ██║     ██║  ██║██║███████╗███████╗██████╔╝\n" +
                "\t\t\t\t  ╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝╚═════╝ \n" +
                "\t\t\t\t                                             \n" +
                "\t\t\t\t                BUILD FAILED                 \n" +
                "\t\t\t\t                                             \n" + ANSI_RESET;

        System.out.println(errorBanner);
    }
}
