package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class artifactDAO {
    private DataBaseConnector dbCon = null;
    public artifactDAO(DataBaseConnector dbCon) {
        this.dbCon = dbCon;
    }

    public List<Artifact> getArtifact(String tableName) throws Exception {
        String query = "SELECT * FROM ?;";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);
        ResultSet rs = dbCon.query(query, queryAttr);
        List<Artifact> artifactList = new ArrayList<>();

        while(rs.next()) {
            Artifact artifact = new Artifact(rs.getInt("id_artifact"), rs.getString("name"),
                    rs.getString("description"), rs.getInt("price"),
                    rs.getInt("creator_id"), rs.getInt("modified_by"));
            artifactList.add(artifact);
        }

        return artifactList;
    }

    public void addArtifact(Artifact artifact) throws Exception{
        Connection connection = dbCon.connect();

        String query = "INSERT INTO artifacts(name, description, creator_id) VALUES(?, ?, ?::integer, ?::integer);";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, artifact.getName());
        pstmt.setString(2, artifact.getDescription());
        pstmt.setString(3, artifact.getPrice() + "");
        pstmt.setString(4, artifact.getCreatorId() + "");

        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }

    public void updateArtifact(Artifact artifact) throws Exception{
        Connection connection = dbCon.connect();
        String query = "UPDATE artifacts SET name=?, description=?, price=?, modified_by=? WHERE id_artifact=?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, artifact.getName());
        pstmt.setString(2, artifact.getDescription());
        pstmt.setString(3, artifact.getPrice() + "");
        pstmt.setString(4, artifact.getModifierId() + "");
        pstmt.setString(5, artifact.getId() + "");

        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }

    public void deleteArtifact(int id) throws Exception {
        Connection connection = dbCon.connect();
        String query = "DELETE artifacts WHERE id_artifact=?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, id + "");
        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }

    public void unlockArtifact(int studentId, int artifactId) throws Exception {
        Connection connection = dbCon.connect();
        String query = "INSERT INTO artifacts_unlocked(id_student, id_artifact) VALUES(?::integer, ?::integer);";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, studentId + "");
        pstmt.setString(2, artifactId + "");
        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }
}
