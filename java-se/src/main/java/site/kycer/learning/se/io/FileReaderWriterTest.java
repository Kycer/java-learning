package site.kycer.learning.se.io;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 文件读取
 * <p>
 * FileReader、FileWriter 文件编码格式与项目不一致时会出现乱码，可使用 BufferedReader 解决
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-21
 */
public class FileReaderWriterTest {

    @Test
    public void testFileReader() {
        File file = new File("hello.txt");
        try (FileReader reader = new FileReader(file)) {
            int data;
            while ((data = reader.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * read() 升级
     */
    @Test
    public void testFileReader1() {
        File file = new File("hello.txt");
        try (FileReader reader = new FileReader(file)) {
            int len;
            char[] arr = new char[1024];
            while ((len = reader.read(arr)) != -1) {
                System.out.println("方式一：");
                for (int i = 0; i < len; i++) {
                    System.out.print(arr[i]);
                }
                System.out.println("\r\n\n方式二：");
                String str = new String(arr, 0, len);
                System.out.print(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFileWriter() {
        File file = new File("hello.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write("hello java!\n");
            writer.write("你好角蛙!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testFileReaderWriter() {
        File file = new File("hello.txt");
        File file1 = new File("hello1.txt");
        try (FileReader reader = new FileReader(file); FileWriter writer = new FileWriter(file1)) {
            int len;
            char[] arr = new char[1024];
            while ((len = reader.read(arr)) != -1) {
                String str = new String(arr, 0, len);
                writer.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * BufferedReader、BufferedWriter 测试 数组读写
     */
    @Test
    public void testFileReaderWriterBuffered() {
        File file = new File("hello.txt");
        File file1 = new File("hello1.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file)); BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
            if (!reader.ready()) {
                System.out.println("文件流暂时无法读取");
                return;
            }
            int len;
            char[] arr = new char[1024];
            while ((len = reader.read(arr)) != -1) {
                String str = new String(arr, 0, len);
                writer.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * BufferedReader、BufferedWriter 测试 一行行读
     * reader.readLine()方法返回的一行字符中不包含换行符
     */
    @Test
    public void testFileReaderWriterBuffered1() {
        File file = new File("hello.txt");
        File file1 = new File("hello1.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
            if (!reader.ready()) {
                System.out.println("文件流暂时无法读取");
                return;
            }
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * BufferedReader、BufferedWriter 测试 指定编码
     */
    @Test
    public void testFileReaderWriterBuffered2() {
        File file = new File("hello.txt");
        File file1 = new File("hello1.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
            if (!reader.ready()) {
                System.out.println("文件流暂时无法读取");
                return;
            }
            int len;
            char[] arr = new char[1024];
            while ((len = reader.read(arr)) != -1) {
                String str = new String(arr, 0, len);
                writer.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Files 测试
     * <p>
     * 编码问题报错 Charset.forName("GBK")
     */
    @Test
    public void testFiles() {
        try {
            final List<String> lines = Files.readAllLines(Paths.get("hello.txt"), Charset.forName("GBK"));
            lines.forEach(System.out::println);
            Files.write(Paths.get("hello1.txt"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
