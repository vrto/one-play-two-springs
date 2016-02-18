package vrto.write;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import vrto.read.ReadConfig;
import vrto.read.SlaveDbConfig;
import vrto.stereotypes.Queries;
import vrto.stereotypes.ReadingController;

@Configuration
@Import(MasterDbConfig.class)
@ComponentScan(
        basePackages = {"vrto"},
        excludeFilters = {
                @ComponentScan.Filter(value = {Queries.class, ReadingController.class}),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {ReadConfig.class, SlaveDbConfig.class})}
)
public class WriteConfig {
}
