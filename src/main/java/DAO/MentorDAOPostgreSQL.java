package DAO;

import Model.Mentor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MentorDAOPostgreSQL implements MentorDAO{

    private DataBaseConnector dataBaseConnector;

    public MentorDAOPostgreSQL(){
        this.dataBaseConnector = new DataBaseConnector();
    }

    public void addMentor(Mentor mentor) throws SQLException{
        String query = "INSERT INTO mentors (name, surname, email, city, beginWork, logins_id) VALUES (?, ?, ?, ?, ?, ?::integer);";
        String[] queryAttr = {mentor.getName(), mentor.getSurname(), mentor.getEmail(), mentor.getCity(), mentor.getBeginWork(),
                Integer.toString(mentor.getLoginsID())};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void editMentor(Mentor mentor) throws SQLException{
        String query = "UPDATE mentors SET name = ?, surname = ?, email = ?, city = ?, beginWork = ?, logins_id = ? WHERE id = ?;";
        String[] queryAttr = {mentor.getName(), mentor.getSurname(), mentor.getEmail(), mentor.getCity(), mentor.getBeginWork(),
                Integer.toString(mentor.getLoginsID()), Integer.toString(mentor.getId())};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void deleteMentor(int id) throws SQLException{
        String query = "DELETE FROM mentors WHERE id = ?";
        String[] queryAttr = {Integer.toString(id)};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public List<Mentor> getMentors() throws SQLException{
        String query = "SELECT name, surname, email, city, begin_work FROM mentors;";
        List<Mentor> mentorsList = new ArrayList<>();

        ResultSet rs = dataBaseConnector.query(query, null);

        while(rs.next()){
            Mentor mentor = new Mentor(rs.getString("name"), rs.getString("surname"),
                    rs.getString("email"), rs.getString("city"), rs.getString("begin_work"));

            mentorsList.add(mentor);
        }
        return mentorsList;
    }

    public Mentor getMentor(int id) throws SQLException{
        String query = "SELECT name, surname, email, city, begin_work FROM mentors WHERE id = ?;";
        String[] queryAttr = {Integer.toString(id)};

        ResultSet rs = dataBaseConnector.query(query, queryAttr);

        if(rs.next()){
            return new Mentor(rs.getString("name"), rs.getString("surname"), rs.getString("email"),
                    rs.getString("city"), rs.getString("begin_work"));
        }
        return null;
    }
}
