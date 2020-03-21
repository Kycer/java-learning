package site.kycer.learning.se.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * 文件读取
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

    @Test
    public void testFiles() {
        File file = new File("hello.txt");
        try {
            final List<String> lines = Files.readAllLines(file.toPath());
            lines.forEach(System.out::println);
            Files.write(new File("hello1.txt").toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Files.readAllBytes()
    }


}
