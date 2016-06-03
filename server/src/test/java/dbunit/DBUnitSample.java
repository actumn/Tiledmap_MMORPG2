package dbunit;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.hamcrest.core.Is;

import javax.sql.DataSource;
import java.io.File;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by Lee on 2016-06-03.
 */
public class DBUnitSample {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";


    @BeforeClass
    public static void createSchema() throws Exception {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "server/src/test/resources/schema.sql", Charset.forName("UTF-8"), false);
    }

    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet();
        cleanlyInsert(dataSet);
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("server/src/test/resources/dataset.xml"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(
                "org.h2.Driver", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @Test
    public void findsAndReadsExistingPersonByFirstName() throws Exception {
        PersonRepository repository = new PersonRepository(dataSource());
        Person charlie = repository.findPersonByFirstName("Charlie");

        assertEquals(charlie.getFirstName(), "Charlie");
        assertEquals(charlie.getLastName(), "Brown");
        assertEquals(charlie.getAge(), 42);

    }


    private DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(JDBC_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }

    class Person {
        private String firstName;
        private String lastName;
        private int age;

        public int getAge() {
            return age;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }


        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        @Override
        public String toString() {
            return firstName + ", " + lastName + ", " + age;
        }
    }
    class PersonRepository {
        private DataSource dataSouce;
        public PersonRepository(DataSource dataSource) {
            this.dataSouce = dataSource;
        }

        public Person findPersonByFirstName(String firstName) {
            Person person = null;
            try {
                Connection conn = dataSouce.getConnection();
                String sql = "SELECT * FROM PERSON WHERE NAME = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, firstName);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                    person = new Person(rs.getString("NAME"), rs.getString("LAST_NAME"), rs.getInt("AGE"));
            } catch (SQLException e) {
                e.printStackTrace();
                person = null;
            }

            return person;
        }
    }
}
