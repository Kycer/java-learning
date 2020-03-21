package site.kycer.learning.se.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 键盘输入
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-19
 */
public class KeyboardInput {
    public static void main(String[] args) {
        // test1();
        test2();
    }

    public static void test1() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("请输入一个字符");
            char c;
            c = (char) reader.read();
            System.out.println("你输入的字符为" + c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test2() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入一个字符");
        String s = input.next();
        System.out.println("你输入的字符为" + s);
        input.close();
    }
}
