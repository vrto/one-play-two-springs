package vrto;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import vrto.stereotypes.ReadingController;

import javax.inject.Provider;

@ReadingController
public class GetController extends Controller {

    @Autowired
    UserQueries queries;

    @Autowired
    Provider<Precondition> preconditionProvider;

    public Result index() {
        preconditionProvider.get().verify("Michal");

        System.out.println("fetching users from slave");

        val json = Json.toJson(queries.findUsers());
        return ok(json);
    }

    public Result writeCrash() {
        queries.userRepository.save(new UserEntity("Should crash"));

        return status(409, "writing shouldn't be allowed at reading controller");
    }

}
