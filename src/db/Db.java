package db;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * User: Ilo Date: 10/11/2019 Time: 15:13
 */
public final class Db {

    private static Db db;
    private Connection cn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;

    public void getConnexion() {
        try {
            String url = "jdbc:mysql://localhost:3306/dechet_db";
            String username = "root";
            String password = "";
            if (cn == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                cn = DriverManager.getConnection(url, username, password);
                stmt = cn.createStatement();
            } else if (cn.isClosed()) {
                cn = DriverManager.getConnection(url, username, password);
                stmt = cn.createStatement();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }

    public static Db getInstance() throws Exception {
        if (db == null) {
            db = new Db();
        }
        return db;
    }

    public Db() throws Exception {
        getConnexion();
    }

    // Pour l'exécution des requêtes (INSERT, UPDATE, DELETE)
    public int executeCUD(String sql) throws Exception {
        int nbRows;
        getConnexion();
        if (stmt == null || stmt.isClosed()) {
            stmt = cn.createStatement();
        }
        nbRows = stmt.executeUpdate(sql);
        return nbRows;
    }

    // Pour l'exécution des requêtes (SELECT)
    public ResultSet executeSelect(String sql) throws Exception {
        ResultSet rs;
        getConnexion();
        if (stmt == null || stmt.isClosed()) {
            stmt = cn.createStatement();
        }
        rs = stmt.executeQuery(sql);

        return rs;
    }

    // Pour une préparation des requêtes paramétrées
    public void iniPreparedCmd(String sql) throws Exception {
        getConnexion();
        if (sql.toLowerCase().trim().startsWith("insert")) {
            pstmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            pstmt = cn.prepareStatement(sql);
        }
    }

    // Pour l'exécution des requêtes paramétrées (INSERT, UPDATE, DELETE)
    public int executePreparedCUD() throws Exception {
        int nbRows;
        getConnexion();
        nbRows = getPstmt().executeUpdate();
        return nbRows;
    }

    // Pour l'exécution des requêtes paramétrées (SELECT)
    public ResultSet executePreparedSelect() throws Exception {
        ResultSet rs;
        getConnexion();
        rs = getPstmt().executeQuery();
        return rs;
    }

    // Avant de fermer la connexion de verifier si stmt, pstmt et cn sont ouverts
    public void FermerConnexion() throws Exception {
        if (stmt != null || !stmt.isClosed()) {
            stmt.close();
            stmt = null;
        }

        if (getPstmt() != null || !pstmt.isClosed()) {
            getPstmt().close();
            pstmt = null;
        }

        if (cn != null || !cn.isClosed()) {
            cn.close();
            cn = null;
        }
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void displayTable(ResultSet rs, JTable table) {
        try {

            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();

            Vector columns = new Vector(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                columns.add(toTitleCase(md.getColumnName(i)));
            }

            Vector data = new Vector();
            Vector row;

            while (rs.next()) {
                row = new Vector(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            DefaultTableModel tableModel = new DefaultTableModel(data, columns);
            table.setModel(tableModel);

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String toTitleCase(String givenString) {
        String[] arr = givenString.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();

        for (String s : arr) {
            sb.append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    

}
