package DAO;

import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private DataBaseConnector dataBaseConnector;

    public AdminDAO(){
        this.dataBaseConnector = new DataBaseConnector();
    }

    public void addMentor(String name, String surname, String email, String city, String beginWork, int logins_id) throws Exception{
        List<String> parametersQueries = new ArrayList<String>();

        parametersQueries.add(name);
        parametersQueries.add(surname);
        parametersQueries.add(email);
        parametersQueries.add(city);
        parametersQueries.add(beginWork);
        parametersQueries.add(String.valueOf(logins_id));

        dataBaseConnector.query("INSERT INTO mentors (name, surname, email, city, beginWork, logins_id) VALUES (?, ?, ?, ?, ?);", parametersQueries);
    }

    public void editMentor(String name, String surname, String email, String city, String beginWork, String logins_id) throws Exception{
        List<String> parametersQuery = new ArrayList<String>();

        parametersQuery.add(name);
        parametersQuery.add(surname);
        parametersQuery.add(email);
        parametersQuery.add(city);
        parametersQuery.add(beginWork);
        parametersQuery.add(logins_id);

        dataBaseConnector.query("UPDATE mentors SET name = ?, surname = ?, email = ?, city = ?, beginWork = ?, logins_id = ? WHERE id = ?", parametersQuery);
    }

    public void deleteMentor(int id) throws Exception{
        List<String> parametersQuery = new ArrayList<String>();

        parametersQuery.add(String.valueOf(id));
        
        dataBaseConnector.query("DELETE FROM mentors WHERE id = ?", parametersQuery);
    }
}
