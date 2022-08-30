package org.umer.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import org.umer.builder.DatabaseConnector;
import org.umer.domain.Fruit;
import org.umer.domain.dao.FruitDao;

@ApplicationScoped
public class FruitService {

    @Inject
    DatabaseConnector databaseConnector;

    public List<Fruit> list() throws Exception {
        return databaseConnector.getJdbi().withExtension(FruitDao.class,
                FruitDao::findAll);
    }

    public Fruit findById(String uuid) throws Exception {
        return databaseConnector.getJdbi().withExtension(FruitDao.class, dao -> {
            return dao.findById(uuid);
        });

    }

    public Fruit add(Fruit fruit) throws Exception {


        Jdbi jdbi = databaseConnector.getJdbi();

        /**
         * A convenience method which opens an extension of the given type,
         * yields it to a callback, and returns the result of the callback.
         * A handle is opened if needed by the
         * extension, and closed before returning to the caller.
         */
        return jdbi.withExtension(FruitDao.class, dao -> {
            return dao.add(fruit.getId(), fruit.getName(), fruit.getDescription());
        });
    }


    public String dummyFeature(){
        return "new Dummy Value";
    }
    public Fruit update(Fruit fruit) throws Exception {


        Jdbi jdbi = databaseConnector.getJdbi();
        return jdbi.withExtension(FruitDao.class, dao -> {
            return dao.update(fruit.getId(), fruit.getName(), fruit.getDescription());
        });
    }

    public Integer delete(String uuid) throws Exception {

        Jdbi jdbi = databaseConnector.getJdbi();
        return jdbi.withExtension(FruitDao.class, dao -> {
            return dao.deleteById(uuid);
        });
    }
}
