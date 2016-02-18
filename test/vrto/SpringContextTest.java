package vrto;

import lombok.val;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import vrto.read.ReadConfig;
import vrto.write.WriteConfig;

import static org.fest.assertions.Assertions.assertThat;

public class SpringContextTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCreateReadingBeans() {
        val ctx = createContextFromConfig(ReadConfig.class);

        // read-only beans
        assertThat(ctx.getBean(GetController.class)).isNotNull();
        assertThat(ctx.getBean(UserQueries.class)).isNotNull();
        assertThat(ctx.getBean(UserRepository.class)).isNotNull();
        assertThat(ctx.getBean("readingEntityManagerFactory")).isNotNull();
        assertThat(ctx.getBean("readingDataSource")).isNotNull();
        assertThat(ctx.getBean("readingTransactionManager")).isNotNull();

        // writing beans should be missing
        assertBeanDoesNotExist(ctx, PostController.class);
        assertBeanDoesNotExist(ctx, UserCommands.class);
        assertBeanDoesNotExist(ctx, "writingEntitiyManagerFactory");
        assertBeanDoesNotExist(ctx, "writingDataSource");
        assertBeanDoesNotExist(ctx, "writingTransactionManager");
    }

    @Test
    public void shouldCreateWritingBeans() {
        val ctx = createContextFromConfig(WriteConfig.class);

        // write-only beans
        assertThat(ctx.getBean(PostController.class)).isNotNull();
        assertThat(ctx.getBean(UserCommands.class)).isNotNull();
        assertThat(ctx.getBean(UserRepository.class)).isNotNull();
        assertThat(ctx.getBean("writingEntityManagerFactory")).isNotNull();
        assertThat(ctx.getBean("writingDataSource")).isNotNull();
        assertThat(ctx.getBean("writingTransactionManager")).isNotNull();

        // reading beans should be missing
        assertBeanDoesNotExist(ctx, GetController.class);
        assertBeanDoesNotExist(ctx, UserQueries.class);
        assertBeanDoesNotExist(ctx, "readingEntitiyManagerFactory");
        assertBeanDoesNotExist(ctx, "readingDataSource");
        assertBeanDoesNotExist(ctx, "readingTransactionManager");
    }

    @Test
    public void shouldHaveDifferentInstances_InDifferentSpringContexts() {
        val readingCtx = createContextFromConfig(ReadConfig.class);
        val writingCtx = createContextFromConfig(WriteConfig.class);

        assertThat(readingCtx.getBean(UserRepository.class)).isNotSameAs(writingCtx.getBean(UserRepository.class));
        assertThat(readingCtx.getBean(Precondition.class)).isNotSameAs(writingCtx.getBean(Precondition.class));
    }

    private AnnotationConfigApplicationContext createContextFromConfig(Class<?> config) {
        val ctx = new AnnotationConfigApplicationContext();
        ctx.register(config);
        ctx.refresh();
        ctx.start();
        return ctx;
    }

    private void assertBeanDoesNotExist(AnnotationConfigApplicationContext ctx, Class<?> beanClass) {
        thrown.expect(NoSuchBeanDefinitionException.class);
        thrown.expectMessage(String.format("No qualifying bean of type [%s] is defined", beanClass.getName()));

        ctx.getBean(beanClass);
    }

    private void assertBeanDoesNotExist(AnnotationConfigApplicationContext ctx, String beanName) {
        thrown.expect(NoSuchBeanDefinitionException.class);
        thrown.expectMessage(String.format("No bean named '%s' is defined", beanName));

        ctx.getBean(beanName);
    }

}
