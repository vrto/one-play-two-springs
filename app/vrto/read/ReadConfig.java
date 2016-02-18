package vrto.read;

import org.springframework.context.annotation.*;
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
@EnableAspectJAutoProxy
public class ReadConfig {

    @Bean
    public SlaveDatabaseGuardian slaveDatabaseGuardian() {
        return new SlaveDatabaseGuardian();
    }

}
