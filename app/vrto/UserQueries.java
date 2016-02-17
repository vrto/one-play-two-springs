package vrto;

import org.springframework.beans.factory.annotation.Autowired;
import vrto.stereotypes.Queries;

import java.util.List;

@Queries
class UserQueries {

    @Autowired
    UserRepository userRepository;

    List<UserEntity> findUsers() {
        System.out.println(userRepository);
        return userRepository.findAll();
    }
}
