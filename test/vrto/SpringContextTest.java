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
        val ctx = new AnnotationConfigApplicationContext(ReadConfig.class);

        // read-only beans
        assertThat(ctx.getBean(GetController.class)).isNotNull();
        assertThat(ctx.getBean(UserQueries.class)).isNotNull();
        assertThat(ctx.getBean(UserRepository.class)).isNotNull();

        // writing beans should be missing
        assertBeanDoesNotExist(ctx, PostController.class);
        assertBeanDoesNotExist(ctx, UserCommands.class);
    }

    @Test
    public void shouldCreateWritingBeans() {
        val ctx = new AnnotationConfigApplicationContext(WriteConfig.class);

        // write-only beans
        assertThat(ctx.getBean(PostController.class)).isNotNull();
        assertThat(ctx.getBean(UserCommands.class)).isNotNull();
        assertThat(ctx.getBean(UserRepository.class)).isNotNull();

        // reading beans should be missing
        assertBeanDoesNotExist(ctx, GetController.class);
        assertBeanDoesNotExist(ctx, UserQueries.class);
    }

    @Test
    public void shouldHaveDifferentInstances_InDifferentSpringContexts() {
        val readingCtx = new AnnotationConfigApplicationContext(ReadConfig.class);
        val writingCtx = new AnnotationConfigApplicationContext(WriteConfig.class);

        assertThat(readingCtx.getBean(UserRepository.class)).isNotSameAs(writingCtx.getBean(UserRepository.class));
    }

    private void assertBeanDoesNotExist(AnnotationConfigApplicationContext ctx, Class<?> beanClass) {
        thrown.expect(NoSuchBeanDefinitionException.class);
        thrown.expectMessage(String.format("No qualifying bean of type [%s] is defined", beanClass.getName()));

        ctx.getBean(beanClass);
    }
}
