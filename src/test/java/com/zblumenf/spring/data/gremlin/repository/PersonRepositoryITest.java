package com.zblumenf.spring.data.gremlin.repository;

import com.zblumenf.spring.data.gremlin.common.TestConstants;
import com.zblumenf.spring.data.gremlin.common.TestRepositoryConfiguration;
import com.zblumenf.spring.data.gremlin.common.domain.Person;
import com.zblumenf.spring.data.gremlin.common.domain.Project;
import com.zblumenf.spring.data.gremlin.common.repository.PersonRepository;
import com.zblumenf.spring.data.gremlin.common.repository.ProjectRepository;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfiguration.class)
public class PersonRepositoryITest {

    private final Person person = new Person(TestConstants.VERTEX_PERSON_ID, TestConstants.VERTEX_PERSON_NAME);
    private final Person person0 = new Person(TestConstants.VERTEX_PERSON_0_ID, TestConstants.VERTEX_PERSON_0_NAME);
    private final Project project = new Project(TestConstants.VERTEX_PROJECT_ID, TestConstants.VERTEX_PROJECT_NAME,
            TestConstants.VERTEX_PROJECT_URI);

    @Autowired
    private PersonRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    @Before
    public void setup() {
        this.repository.deleteAll();
    }

    @After
    public void cleanup() {
        this.repository.deleteAll();
    }

    @Test
    public void testFindById() {
        this.repository.save(this.person);

        final Person foundPerson = this.repository.findById(this.person.getId()).get();

        Assert.assertNotNull(foundPerson);
        Assert.assertEquals(foundPerson.getId(), this.person.getId());
        Assert.assertEquals(foundPerson.getName(), this.person.getName());

        Assert.assertFalse(this.repository.findById(this.person0.getId()).isPresent());
    }

    @Test
    public void testExistById() {
        Assert.assertFalse(this.repository.existsById(this.person.getId()));

        this.repository.save(this.person);

        Assert.assertTrue(this.repository.existsById(this.person.getId()));
    }
}
