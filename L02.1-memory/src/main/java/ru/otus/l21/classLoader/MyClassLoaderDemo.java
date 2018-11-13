package ru.otus.l21.classLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;

/**
 * @author sergey
 * created on 12.11.18.
 */
public class MyClassLoaderDemo {
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        new MyClassLoaderDemo().start();
    }

    private void start() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MyClassLoader loader = new MyClassLoader();
        Class<?> clazz = loader.defineClass("ru.otus.l21.classLoader.ClassForLoading");
        Method[] methods = clazz.getMethods();
        System.out.println("methods:");
        for(Method method: methods) {
            System.out.println(method.getName());
        }

        Constructor<?> constructor = clazz.getConstructor();
        Object object = constructor.newInstance();
        System.out.println("------");

        clazz.getMethod("action").invoke(object);
    }


    private class MyClassLoader extends ClassLoader {
        Class<?> defineClass(String className) throws IOException {
            File file = new File(getFileName(className));
            byte[] bytecode = Files.readAllBytes(file.toPath());
            return super.defineClass(className, bytecode, 0, bytecode.length);
        }

        String getFileName(String className) {
            return "myClass" + File.separator + className.substring(className.lastIndexOf(".") + 1) + ".class";
        }

    };

}
