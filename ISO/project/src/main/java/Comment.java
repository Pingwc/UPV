import java.sql.Statement;

public class Comment {
    private String content;

    //TODO test if the attributes of the class are necessary for a correct execution
    public static String commentIssue(String user ,String issue_name ,String content, Statement stmt){
        String aux;
        try {
            stmt.executeUpdate("INSERT INTO comments(content, author, fromIssue) VALUES ('" + content + "'" + "," + "'" + user + "'" + "," + "'" + issue_name + "')");
            aux = issue_name + " Has been commented";
        }catch(Exception e){aux = e.toString();}
        return aux;
    }
}
