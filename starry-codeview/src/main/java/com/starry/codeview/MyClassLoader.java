package com.starry.codeview;

import com.starry.codeview.reflactors.A;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    private final String classPath = "D:/test/";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(classPath, name.replaceAll(".", "/").concat(".class"));
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            fileInputStream = new FileInputStream(file);

            int b = 0;
            while ((b = fileInputStream.read()) != 0) {
                byteArrayOutputStream.write(b);
            }

            byte[] bytes = byteArrayOutputStream.toByteArray();

            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.loadClass(name);
    }

    public static final Integer SecretKey = "ABCJSI#KLS@1".hashCode();

    /**
     * 加密class字节码文件,变成自己定义的字节码文件
     *
     * @param name
     * @throws IOException
     */
    public void encodeClass(String name) throws IOException {
        File file = new File(classPath, name.replaceAll(".", "/").concat(".class"));
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(
                new File(classPath, name.replaceAll(".", "/").concat(".dzhclass")));

        int b = 0;
        while ((b = fileInputStream.read()) != -1) {
            // 通过异或加密  再次异或 即可解密
            fileOutputStream.write(b ^ SecretKey);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }


    /**
     * 重写findClass方法
     * <p>
     * 如果不会写, 可以参考URLClassLoader中是如何加载AppClassLoader和ExtClassLoader的
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    protected Class<?> findClass1(String name) throws ClassNotFoundException {
        try {
            byte[] data = loadBytes(name);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private byte[] loadBytes(String name) throws Exception {
        // 我们需要读取类的路径
        String path = name.replace('.', '/').concat(".class");
        //String path = "";
        // 去路径下查找这个类
        FileInputStream fileInputStream = new FileInputStream(classPath + "/" + path);
        int len = fileInputStream.available();

        byte[] data = new byte[len];
        fileInputStream.read(data);
        fileInputStream.close();

        return data;
    }

    /**
     * 重写这个方法即可，去掉那个向上递归parent的逻辑即可打破双亲委托
     * @param name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                /**
                 * 直接执行findClass()...什么意思呢? 首先会使用自定义类加载器加载类, 不在向上委托, 直接由
                 * 自己执行
                 *
                 * jvm自带的类还是需要由引导类加载器自动加载
                 */
                if (!name.startsWith("com.lxl.jvm")) {
                    c = this.getParent().loadClass(name);
                } else {
                    c = findClass(name);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    /**
     * 此方法需要确认是不是可写可不写。 不写一定可以！
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        MyClassLoader cl = new MyClassLoader();
        Class aClass = cl.loadClass("com.starry.codeview.reflactors.A");
        A o = (A) aClass.newInstance();
    }
}
