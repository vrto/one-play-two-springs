package vrto;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.test.Helpers;
import play.test.TestServer;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.running;

public class EndpointsTest {

    TestServer testServer;
    private long timeout = 1000;

    @Before
    public void setUp() {
        testServer = Helpers.testServer(9090);
    }

    @Test
    public void readEndpoint_ShouldPointToSlaveDatabase() {
        running(testServer, () -> {
            WSResponse response = WS.url("http://localhost:9090/read").get().get(timeout);
            assertThat(response.getStatus()).isEqualTo(200);

            // data from slave db
            JsonNode json = response.asJson();
            assertThat(json.get(0).path("name").textValue()).isEqualTo("Michal");
        });
    }

    @Test
    public void writeEdnpoint_ShouldPointToMasterDatabase() {
        running(testServer, () -> {
            WSResponse response = WS.url("http://localhost:9090/create").post("").get(timeout);
            assertThat(response.getStatus()).isEqualTo(200);

            // data from master db
            JsonNode json = response.asJson();
            assertThat(json.get(0).path("name").textValue()).isEqualTo("Jacob");
        });
    }
}
