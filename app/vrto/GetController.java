package vrto;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import vrto.stereotypes.ReadingController;

@ReadingController
public class GetController extends Controller {

    @Autowired UserQueries queries;

    public Result index() {
        System.out.println("fetching users from slave");
        val json = Json.toJson(queries.findUsers());
        return ok(json);
    }

}
