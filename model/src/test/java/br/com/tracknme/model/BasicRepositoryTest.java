package br.com.tracknme.model;

import br.com.tracknme.model.repository.BasicRepository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cleberson on 19/11/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BasicRepositoryTest.Config.class)
@WebAppConfiguration
public class BasicRepositoryTest {

    private static final String KEY = BasicRepositoryTest.class.getSimpleName() + ":";

    @SpringBootApplication
    @Configuration
    static class Config {
    }

    @Autowired
    private BasicRepository<TestEntity> basicRepository;


    @Test
    public void createTest() {
        final long id = 1;
        final String value = "test1";
        TestEntity testEntity = new TestEntity(id, value);
        basicRepository.create(KEY, testEntity, id);
        testEntity = basicRepository.retrieve(KEY, id);
        assertNotNull(testEntity);
        assertEquals(testEntity.getId(), id);
        assertEquals(testEntity.getValue(), value);


        basicRepository.delete(KEY, id);
    }

    @Test
    public void updateTest() {

        final long id = 1;
        TestEntity testEntity = new TestEntity(id, "test1");
        basicRepository.create(KEY, testEntity, id);


        testEntity.setValue("test2");
        basicRepository.update(KEY, testEntity, id);

        testEntity = basicRepository.retrieve(KEY, id);

        assertNotNull(testEntity);
        assertEquals(testEntity.getId(), id);
        assertEquals(testEntity.getValue(), "test2");

        basicRepository.delete(KEY, id);
    }

    @Test
    public void deleteTest() {
        final long id = 1;
        TestEntity testEntity = new TestEntity(id, "test1");
        basicRepository.create(KEY, testEntity, id);
        basicRepository.delete(KEY, id);
        testEntity = basicRepository.retrieve(KEY, id);

        assertNull(testEntity);
    }

    @Test
    public void retrieveAllTest() {
        List<TestEntity> list1 = new ArrayList<>();
        list1.add(new TestEntity(1, "test1"));
        list1.add(new TestEntity(2, "test2"));
        list1.add(new TestEntity(3, "test3"));

        list1.stream().forEach(t -> basicRepository.create(KEY, t, t.getId()));

        List<TestEntity> list2 = basicRepository.retrieveAll(KEY);
        assertEquals(list1.size(), list2.size());

        list1.stream().forEach(t -> basicRepository.delete(KEY, t.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidArgumentsUpdateTest() {
        basicRepository.update(KEY, null, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidArgumentsCreateTest() {
        basicRepository.create(KEY, null, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidArgumentsRetrieve() {
        basicRepository.retrieve(null, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidArgumentsDelete() {
        basicRepository.delete(null, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidArgumentsRetrieveAll() {
        basicRepository.retrieveAll(null);
    }
}
