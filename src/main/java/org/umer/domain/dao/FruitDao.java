package org.umer.domain.dao;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.umer.domain.Fruit;

public interface FruitDao {

  @SqlUpdate("INSERT into FRUIT ( id, name, description ) " + "VALUES ( :id, :name, :description)")
  @GetGeneratedKeys
  @RegisterBeanMapper(Fruit.class)
  Fruit add(@Bind("id") String id, @Bind("name") String name, @Bind("description") String description);

  @SqlUpdate("UPDATE FRUIT SET name = :name, description = :description " + "WHERE id = :id")
  int update(@Bind("id") String id, @Bind("name") String name, @Bind("description") String description);

  @SqlQuery("SELECT * FROM FRUIT WHERE id = :id")
  Fruit findById(@Bind("id") String id);

  @SqlQuery("SELECT * FROM FRUIT")
  @RegisterBeanMapper(Fruit.class)
  List<Fruit> findAll();

  @SqlUpdate("DELETE FROM FRUIT WHERE id = :id")
  int deleteById(@Bind("id") String id);
}
