package org.umer.builder;

import io.agroal.api.AgroalDataSource;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

@Singleton
public class DatabaseConnector {

    @Inject
    AgroalDataSource defaultDataSource;
    /**
     * Jdbi instances are thread-safe and do not own any database resources.
     * <p>
     * Typically applications create a single, shared Jdbi instance,
     * and set up any common configuration there.
     * See Configuration for more details.
     */
    public Jdbi getJdbi() {
        // TODO: monitor if we have connection problem again
        return Jdbi.create(defaultDataSource).installPlugin(new SqlObjectPlugin())
            .installPlugin(new PostgresPlugin());
    }
}
