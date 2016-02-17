package vrto;

import lombok.val;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import vrto.read.ReadConfig;
import vrto.write.WriteConfig;

import static org.fest.assertions.Assertions.assertThat;

public class SpringContextTest {

    @Test
    public void shouldCreateReadingBeans() {
        val ctx = new AnnotationConfigApplicationContext(ReadConfig.class);

        // read-only beans
        assertThat(ctx.getBean(GetController.class)).isNotNull();
        assertThat(ctx.getBean(UserQueries.class)).isNotNull();
        assertThat(ctx.getBean(UserRepository.class)).isNotNull();

        // writing beans should be missing
        assertThat(ctx.getBean(PostController.class)).isNull();
        assertThat(ctx.getBean(UserCommands.class)).isNull();
    }

    @Test
    public void shouldCreateWritingBeans() {
        val ctx = new AnnotationConfigApplicationContext(WriteConfig.class);

        // write-only beans
        assertThat(ctx.getBean(PostController.class)).isNotNull();
        assertThat(ctx.getBean(UserCommands.class)).isNotNull();
        assertThat(ctx.getBean(UserRepository.class)).isNotNull();

        // reading beans should be missing
        assertThat(ctx.getBean(GetController.class)).isNull();
        assertThat(ctx.getBean(UserQueries.class)).isNull();
    }

    @Test
    public void shouldHaveDifferentInstances_InDifferentSpringContexts() {
        val readingCtx = new AnnotationConfigApplicationContext(ReadConfig.class);
        val writingCtx = new AnnotationConfigApplicationContext(WriteConfig.class);

        assertThat(readingCtx.getBean(UserRepository.class)).isNotSameAs(writingCtx.getBean(UserRepository.class));
    }
}
