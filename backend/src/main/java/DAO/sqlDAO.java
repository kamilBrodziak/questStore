package DAO;

import Model.Artifact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class sqlDAO {
    private DataBaseConnector dbCon = null;
    public sqlDAO() {
        DataBaseConnector dbCon = new DataBaseConnector ();
    }


    public Artifact getArtifact(String tableName) throws Exception {
        String query = "SELECT * FROM ?;";
        List<String> queryAttr = new ArrayList<String>();
        queryAttr.add(tableName);
        ResultSet rs = dbCon.query(query, queryAttr);


    }
}
