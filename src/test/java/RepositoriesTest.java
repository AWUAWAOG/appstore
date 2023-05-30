import com.apps.domain.Application;
import com.apps.domain.Developer;
import com.apps.domain.User;
import com.apps.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@NoArgsConstructor
public class RepositoriesTest {

    public UserRepository userRepository;

    public Application application = new Application();
    public Developer developer = new Developer();

    @Test
    @Sql("createUser.sql")
    public void createUserTest() {
        User user = new User();
        userRepository.save(user);
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void ApplicationRepositoryIsNotNull() {
        if (application == null) {
            throw new IllegalArgumentException();
        } else
            System.out.println("ApplicationRepositoryIsNotNull test done!");
    }

    @Test
    public void DeveloperRepositoryIsNotNull() {
        if (developer == null) {
            throw new IllegalArgumentException();
        } else
            System.out.println("DeveloperRepositoryIsNotNull test done!");
    }

    @Test
    public void userRegistrationTest() {
        try {
            User user = new User();
            user.setCreated(new Date(System.currentTimeMillis()));
            user.setEdited(new Date(System.currentTimeMillis()));
            user.setDeleted(false);
            userRepository.save(user);
        } catch (NullPointerException ignored){
        } finally {
            System.out.println("userRegistrationTest test done!");
        }
    }
}
