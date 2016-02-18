package vrto.read;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import vrto.stereotypes.Commands;
import vrto.stereotypes.WritingController;
import vrto.write.MasterDbConfig;
import vrto.write.WriteConfig;

@Configuration
@Import(SlaveDbConfig.class)
@ComponentScan(
        basePackages = {"vrto"},
        excludeFilters = {
                @ComponentScan.Filter(value = {Commands.class, WritingController.class}),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {WriteConfig.class, MasterDbConfig.class})}
)
public class ReadConfig {
}
