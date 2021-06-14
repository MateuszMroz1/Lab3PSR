import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.schema.Drop;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;

import java.util.Random;


import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class CarServiceTableBuilder extends SimpleManager{
    final private static Random r = new Random(System.currentTimeMillis());

    public CarServiceTableBuilder(CqlSession session) {
        super(session);
    }


    public void createTable() {
        CreateTable createTable = SchemaBuilder.createTable("CarService")
                .withPartitionKey("id", DataTypes.INT)
                .withColumn("name", DataTypes.TEXT)
                .withColumn("repair", DataTypes.TEXT)
                .withColumn("price", DataTypes.INT);
        session.execute(createTable.build());
    }

    public void addCarService(String name, String repair, int price) {
        Long id = (long) Math.abs(r.nextInt());

        Insert insert = QueryBuilder.insertInto("CarServices", "CarService")
                .value("id", QueryBuilder.raw(String.valueOf(id)))
                .value("name", QueryBuilder.raw("'" + name + "'"))
                .value("repair", QueryBuilder.raw("'" + repair + "'"))
                .value("price", QueryBuilder.raw(String.valueOf(price)));
        session.execute(insert.build());
    }

    public void updateCarService(int id, String name, String repair, int price) {
        Update update = QueryBuilder.update("CarService")
                .setColumn("name", literal(name))
                .setColumn("repair", literal(repair))
                .setColumn("price", literal(price))
                .whereColumn("id").isEqualTo(literal(id));

        session.execute(update.build());
    }

    public void deleteCarServiceById(int id) {
        Delete delete = QueryBuilder.deleteFrom("CarService").whereColumn("id").isEqualTo(literal(id));
        session.execute(delete.build());
    }

    public void deleteCarServiceByName(String name) {
        Delete delete = QueryBuilder.deleteFrom("CarService").whereColumn("name").isEqualTo(literal(name));
        session.execute(delete.build());
    }

    public void ShowAll() {
        Select query = QueryBuilder.selectFrom("CarService").all();
        SimpleStatement statement = query.build();
        printResultStatement(statement);
    }

    public void ShowCarServiceById(int id) {
        Select query = QueryBuilder.selectFrom("CarService").all().whereColumn("id").isEqualTo(literal(id));
        SimpleStatement simpleStatement = query.build();
        printResultStatement(simpleStatement);

    }

    public void ShowCarServiceByName(String name) {
        Select query = QueryBuilder.selectFrom("CarService").all().whereColumn("name").isEqualTo(literal(name)).allowFiltering();
        SimpleStatement statement = query.build();
        printResultStatement(statement);
    }

    public void printResultStatement(SimpleStatement simpleStatement) {
        ResultSet resultSet = session.execute(simpleStatement);
        for (Row row : resultSet) {
            System.out.print("Serwis samochodowy: ");
            System.out.print("id = " + row.getInt("id") + ", ");
            System.out.print("nazwa = " + row.getString("name") + ", ");
            System.out.print("naprawa = " + row.getString("repair") + ", ");
            System.out.print("cena = " + row.getInt("price") + " zł. ");
            System.out.println();
        }
    }

    public void deleteAllCarService() {
        Drop drop = SchemaBuilder.dropTable("CarService");
        executeSimpleStatement(drop.build());
        createTable();
    }
}
