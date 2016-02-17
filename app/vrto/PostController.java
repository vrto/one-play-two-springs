package vrto;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import vrto.stereotypes.WritingController;

@WritingController
public class PostController extends Controller {

    @Autowired  UserCommands commands;

    public Result modifyingEndpoint() {
        System.out.println("fetching users from master");
        val json = Json.toJson(commands.findUsers());
        return ok(json);
    }
}
