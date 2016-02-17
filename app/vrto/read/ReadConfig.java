package vrto.read;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import vrto.stereotypes.Commands;
import vrto.stereotypes.WritingController;

@Configuration
@Import(SlaveDbConfig.class)
@ComponentScan(
        basePackages = {"vrto"},
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Commands.class, WritingController.class})}
        excludeFilters = {@ComponentScan.Filter(value = {Commands.class, WritingController.class})}
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Commands"), @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*PostController")}
)
public class ReadConfig {
}
