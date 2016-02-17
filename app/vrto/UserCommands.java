package vrto;

import org.springframework.beans.factory.annotation.Autowired;
import vrto.stereotypes.Commands;

import java.util.List;

@Commands
class UserCommands {

    @Autowired
    UserRepository userRepository;

    List<UserEntity> findUsers() {
        System.out.println(userRepository);
        return userRepository.findAll();
    }
}
