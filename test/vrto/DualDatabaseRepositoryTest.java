package vrto;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import vrto.read.ReadConfig;
import vrto.write.WriteConfig;

import static org.fest.assertions.Assertions.assertThat;

public class DualDatabaseRepositoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void crudIsSupportedOnMaster() {
        UserRepository repo = new AnnotationConfigApplicationContext(WriteConfig.class).getBean(UserRepository.class);

        assertThat(repo.findOne(1L)).isNotNull();

        UserEntity user = new UserEntity("Master User");
        UserEntity created = repo.save(user);
        assertThat(repo.findOne(created.getId())).isEqualTo(created);

        created.setName("New Name");
        UserEntity modified = repo.save(created);
        assertThat(repo.findOne(modified.getId())).isEqualTo(modified);
    }

    @Test
    public void modifyingQueriesShouldFailInSlave() {
        UserRepository repo = new AnnotationConfigApplicationContext(ReadConfig.class).getBean(UserRepository.class);
        assertThat(repo.findOne(1L)).isNotNull();

        thrown.expect(ReadOnlyDatabaseException.class);
        thrown.expectMessage("Can't modify data in slave database");
        repo.save(new UserEntity("New User"));
    }
}
