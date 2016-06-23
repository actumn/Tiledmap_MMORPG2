package userservice;

import com.game.server.db.DBManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
/**
 * Created by Lee on 2016-06-03.
 */
public class DBManagerTest {
    @Test
    public void getConnectionTest() {
        try (
                Connection con = DBManager.getConnection();
        ){
            assertNotNull(con);
        } catch (SQLException e) {
            e.printStackTrace();
           fail();
        };
    }
}
