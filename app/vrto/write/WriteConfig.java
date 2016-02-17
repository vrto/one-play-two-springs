package vrto.write;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import vrto.stereotypes.Queries;
import vrto.stereotypes.ReadingController;

@Configuration
@Import(MasterDbConfig.class)
@ComponentScan(
        basePackages = {"vrto"},
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Queries.class, ReadingController.class})}
        excludeFilters = {@ComponentScan.Filter(value = {Queries.class, ReadingController.class})}
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Queries"), @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*GetController")}
)
public class WriteConfig {
}
