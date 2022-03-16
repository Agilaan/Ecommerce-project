import java.sql.Connection;

import com.adventnet.db.api.RelationalAPI;

public class mConnection {
	static RelationalAPI api = RelationalAPI.getInstance();
	static Connection con = null;
	public static Connection getAvailableConnection() throws Exception{
	        
	        	return (con!=null ? con : api.getConnection());
	          
			
	}

}
