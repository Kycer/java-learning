package site.kycer.learning.se.io;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 序列化测试
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-23
 */
public class SerializeTest {

    /**
     * JDK 序列化
     */
    @Test
    public void serialize() {
        Person person = new Person();
        person.setName("Kycer");
        person.setAge(25);
        person.setSex("男");

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Serialize.txt"))) {
            outputStream.writeObject(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * JDK 反序列化
     */
    @Test
    public void deserialization() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Serialize.txt"))) {
            Person person = (Person) inputStream.readObject();
            System.out.println(person);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * kryo 序列化
     */
    @Test
    public void kryoSerialize() {
        Person1 person = new Person1();
        person.setName("Kycer");
        person.setAge(25);
        person.setSex("男");

        try (FileOutputStream outputStream = new FileOutputStream("Serialize.txt");
             Output output = new Output(outputStream)) {
            //获取kryo对象
            Kryo kryo = new Kryo();
            Class<Person1> type = Person1.class;
            kryo.register(type, new BeanSerializer<>(kryo, type));
            kryo.writeObject(output, person);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * kryo 反序列化
     */
    @Test
    public void kryoDeserialize() {
        try (final FileInputStream inputStream = new FileInputStream("Serialize.txt");
             Input input = new Input(inputStream)) {
            Kryo kryo = new Kryo();
            Class<Person1> type = Person1.class;
            kryo.register(type, new BeanSerializer<>(kryo, type));
            Person1 person = kryo.readObject(input, Person1.class);
            System.out.println(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Cache<Class<?>, Schema<Person1>> cache = CacheBuilder.newBuilder()
            .maximumSize(1024).expireAfterWrite(1, TimeUnit.MINUTES).build();

    private Schema<Person1> getSchema(Class<Person1> cls) throws ExecutionException {
        return cache.get(cls, () -> RuntimeSchema.createFrom(cls));
    }

    /**
     * protostuff 序列化
     */
    @Test
    public void protostuffSerialize() {
        Person1 person = new Person1();
        person.setName("Kycer");
        person.setAge(25);
        person.setSex("男");

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try (final FileOutputStream outputStream = new FileOutputStream("Serialize.txt")) {
            Class<Person1> cls = Person1.class;
            Schema<Person1> schema = getSchema(cls);
            ProtostuffIOUtil.writeTo(outputStream, person, schema, buffer);
        } catch (IOException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * protostuff 反序列化
     */
    @Test
    public void protostuffDeserialize() {
        try (final FileInputStream inputStream = new FileInputStream("Serialize.txt")) {
            Class<Person1> cls = Person1.class;
            Schema<Person1> schema = getSchema(cls);
            Person1 person = cls.newInstance();
            ProtostuffIOUtil.mergeFrom(inputStream, person, schema);
            System.out.println(person);
        } catch (IOException | ExecutionException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * jackson 序列化
     */
    @Test
    public void jacksonSerialize() {
        Person1 person = new Person1();
        person.setName("Kycer");
        person.setAge(25);
        person.setSex("男");

        try (final FileOutputStream outputStream = new FileOutputStream("Serialize.txt")) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream, person);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * jackson 反序列化
     */
    @Test
    public void jacksonDeserialize() {
        try (FileInputStream inputStream = new FileInputStream("Serialize.txt")) {
            ObjectMapper mapper = new ObjectMapper();
            Person1 person = mapper.readValue(inputStream, Person1.class);
            System.out.println(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class Person implements Serializable {

    private int age;
    private String name;
    private String sex;

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}


class Person1 {

    private int age;
    private String name;
    private String sex;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
