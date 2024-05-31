import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I076097 on 10/15/2016
 */
public class TestGenericWithClassType {

    public interface ResultMapper<T> {
        T mapRow(String dummy);
    }

    @Test

    public void testGenericTypeWithSpecificSubClass() {

        class StringResultMapper implements ResultMapper<String> {

            @Override
            public String mapRow(final String dummy) {
                return "Hello";
            }
        }

        class IntegerResultMapper implements ResultMapper<Integer> {

            @Override
            public Integer mapRow(final String dummy) {
                return 1;
            }
        }

        final List<ResultMapper<?>> list = new ArrayList<>();
        final StringResultMapper a1 = new StringResultMapper();

    }

    public static class Query {
        List<Class<?>> allTypes = new ArrayList<>();
        List<ResultMapper<?>> allMappers = new ArrayList<>();
        private final int counter = 0;

        <T extends ResultMapper<T>> void addResultMapper(final T a, final Class<T> clazz) {
            allMappers.add(a);
            allTypes.add(clazz);
        }

        <T> ResultMapper<T> getResultMapperAtIndexAs(final int index, final Class<T> clazz) {
            final List<T> ret = new ArrayList<>();
            /*Observable<T> ret = Observable.create(new Observable.OnSubscribe<T>() {
            });*/
            return null;
        }

    }

}
