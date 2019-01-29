package DAO;

import Model.Rank;

import java.sql.SQLException;

public interface RanksDAO {

    void addRanks(Rank rank) throws SQLException;
    void editRanks(Rank rank) throws SQLException;
    void deleteRanks(Rank rank) throws SQLException;
    Rank createRank(int level, int experienceRequired) throws SQLException;
    Rank getRank(int experienceRequired) throws SQLException;
}
