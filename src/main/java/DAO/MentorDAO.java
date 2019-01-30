package DAO;

import Model.Mentor;

import java.sql.SQLException;
import java.util.List;

public interface MentorDAO {

    void addMentor(Mentor mentor) throws SQLException;
    void editMentor(Mentor mentor) throws SQLException;
    void deleteMentor(int id) throws SQLException;
    List<Mentor> getMentors() throws SQLException;
    Mentor getMentor(int id) throws SQLException;
    Mentor getMentorByLoginID(int id) throws SQLException;
}
