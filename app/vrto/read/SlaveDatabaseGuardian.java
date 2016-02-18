package vrto.read;

import com.google.common.collect.Lists;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import vrto.ReadOnlyDatabaseException;

@Aspect
public class SlaveDatabaseGuardian {

    @Before("execution(public * org.springframework.data.repository.Repository+.*(..))")
    public void restrictWriteAccess(JoinPoint joinPoint) {
        if (isModifyingOperation(joinPoint.getSignature().getName())) {
            throw new ReadOnlyDatabaseException("Can't modify data in slave database");
        }
    }

    private boolean isModifyingOperation(String name) {
        return Lists.newArrayList("delete", "flush", "save", "saveAndFlush").contains(name.toLowerCase());
    }
}
