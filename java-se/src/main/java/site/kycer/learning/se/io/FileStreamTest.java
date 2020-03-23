package site.kycer.learning.se.io;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 字节流测试
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-23
 */
public class FileStreamTest {

    /**
     * 对于中文会出现乱码
     */
    @Test
    public void testFileInputStream() {
        try (FileInputStream stream = new FileInputStream("hello.txt")) {
            int len;
            byte[] arr = new byte[5];
            while ((len = stream.read(arr)) != -1) {
                String str = new String(arr, 0, len);
                System.out.print(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件读取
     */
    @Test
    public void testFileStream() {
        try (FileInputStream stream = new FileInputStream("1.jpeg");
             FileOutputStream outputStream = new FileOutputStream("2.jpeg")) {
            int len;
            byte[] arr = new byte[1024];
            while ((len = stream.read(arr)) != -1) {
                outputStream.write(arr, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * BufferedInputStream、BufferedOutputStream 文件读取
     */
    @Test
    public void testFileStreamBuffered() {
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream("1.jpeg"));
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("2.jpeg"))) {
            int len;
            byte[] arr = new byte[1024];
            while ((len = stream.read(arr)) != -1) {
                outputStream.write(arr, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Files 测试
     */
    @Test
    public void testFiles() {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get("1.jpeg"));
            Files.write(Paths.get("2.jpeg"), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
