package vrto;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.lang.String.format;

@Component
@Scope("prototype")
class Precondition {

    @Autowired
    UserRepository userRepository;

    void verify(String userName) {
        // we have only one user in this demo anyway
        val usr = userRepository.findAll().get(0);
        if (!Objects.equals(usr.getName(), userName)) {
            throw new IllegalStateException(format("Name was supposed to be %s, but was %s", userName, usr.getName()));
        }
        System.out.println("Precondition passed");
    }
}
