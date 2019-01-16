package DAO;

import Model.Artifact;
import Model.ComponentJoin;
import Model.ComponentsCompleted;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ComponentUnlockDAO {
    private DataBaseConnector dbCon = null;
    public ComponentUnlockDAO(DataBaseConnector dbCon) {
        this.dbCon = dbCon;
    }

    public List<ComponentsCompleted> getComponents(String tableName, String componentCol) throws Exception {
        String query = "SELECT * FROM ?;";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);

        ResultSet rs = dbCon.query(query, queryAttr);
        List<ComponentsCompleted> componentList = new ArrayList<>();

        while(rs.next()) {
            ComponentsCompleted component = new ComponentsCompleted(rs.getInt("id"), rs.getInt("id_student"),
                    rs.getInt(componentCol), rs.getDate("date"), rs.getBoolean("approved"));
            componentList.add(component);
        }

        return componentList;
    }

    public List<ComponentJoin> getStudentToApproveList(String tableName, String componentTable, String componentCol) throws Exception {
        String query = "SELECT a.id, a.name, a.description, b.first_name, b.last_name" +
                " FROM " + tableName + " c JOIN students b ON b.id = c.id_student" +
                " JOIN " + componentTable + " a ON a.id = c." + componentCol + " WHERE c.approved=false;";
        ResultSet rs = dbCon.query(query, new ArrayList<>());

        List<ComponentJoin> componentJoins = new ArrayList<>();
        while(rs.next()) {
            ComponentJoin componentJoin = new ComponentJoin(rs.getInt("a.id"), rs.getString("a.name"),
                    rs.getString("a.description"), rs.getString("b.first_name"), rs.getString("b.last_name"));
            componentJoins.add(componentJoin);
        }

        return componentJoins;
    }

    public<E> List<E> getStudentApprovedList(String tableName, String componentTable, String componentCol, int studentId, Class cls) throws Exception{
        String query = "SELECT a." + componentCol + ", a.name, a.description, a.price, a.creator_id, a.modified_by FROM " + tableName + " b" + " JOIN " +
                componentTable + " a ON .id = b." + componentCol + " WHERE b.approved=true AND id_student=" + studentId + ";";
        ResultSet rs = dbCon.query(query, new ArrayList<>());

        List<E> components = new ArrayList<>();
        while(rs.next()) {
            Class[] classes = {Integer.class, String.class, String.class, Integer.class, Integer.class, Integer.class};
            E component =(E)( cls.getDeclaredConstructor(classes).newInstance(rs.getInt("a." + componentCol),
                    rs.getString("a.name"), rs.getString("a.description"), rs.getInt("a.price"),
                    rs.getInt("a.creator_id"), rs.getInt("a.modified_by")));
            components.add(component);
        }

        return components;
    }

    public ComponentsCompleted getComponent(String tableName, String componentCol, int id) throws Exception {
        String query = "SELECT * FROM ? WHERE id=?;";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);
        queryAttr.add(id + "");
        ResultSet rs = dbCon.query(query, queryAttr);

        if(rs.next()) {
            return new ComponentsCompleted(rs.getInt("id"), rs.getInt("id_student"),
                    rs.getInt(componentCol), rs.getDate("date"), rs.getBoolean("approved"));
        }
        return null;
    }

    public void completeComponent(String tableName, String colName, int studentId, int componentId) throws Exception{
        String query = "INSERT INTO " + tableName + "(id_student, " + colName + ") VALUES(?::integer, ?::integer);";
        String[] queryAttr = {studentId + "", componentId + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void approveComponent(int id,  String tableName) throws Exception{
        String query = "UPDATE " + tableName + " SET approved=? WHERE id=?;";
        String[] queryAttr = {"true", id + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteArtifact(int id, int studentId, String tableName, String colName) throws Exception {
        String query = "DELETE " + tableName + " WHERE " + colName + "=? AND id_student=?;";
        String[] queryAttr = {id + "", studentId + ""};
        dbCon.updateSQL(query, queryAttr);
    }
}
