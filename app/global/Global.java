package global;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import play.Application;
import play.GlobalSettings;
import vrto.read.ReadConfig;
import vrto.write.WriteConfig;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Global extends GlobalSettings {

    protected final AnnotationConfigApplicationContext readingContext = new AnnotationConfigApplicationContext();
    protected final AnnotationConfigApplicationContext writingContext = new AnnotationConfigApplicationContext();

    @Override
    public void onStart(Application app) {
        super.onStart(app);

        readingContext.register(ReadConfig.class);
        readingContext.refresh();
        readingContext.start();
        System.out.println("\n================ READING ================");
        System.out.println("Reading beans: " + Arrays.stream(readingContext.getBeanDefinitionNames()).collect(Collectors.joining("\n")));

        writingContext.register(WriteConfig.class);
        writingContext.refresh();
        writingContext.start();
        System.out.println("\n================ WRITING ================");
        System.out.println("Writing beans: " + Arrays.stream(writingContext.getBeanDefinitionNames()).collect(Collectors.joining("\n")));
    }

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        A controller = readingContext.getBean(controllerClass);
        return controller != null ? controller : writingContext.getBean(controllerClass);
    }

    @Override
    public void onStop(Application app) {
        readingContext.close();
        writingContext.close();
        super.onStop(app);
    }

}
