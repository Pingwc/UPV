import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

public class Issue {
    private String title; //the title of the issue
    private String createdBy; //the name of the person who created the issue
    private Date ini; //the date when the issue was created
    private Date due; //the date when the has to end
    private String state; // the state of the issue
    private String ws; // the workspace by which it was created
    public static int identifier = 0;


    //constructor
    public Issue(String title, String createdBy, Date ini, Date due, String ws){
        this.title = title;
        this.createdBy = createdBy;
        this.ini = ini;
        this.due = due;
        state = "not assigned";
        this.ws = ws;
    }

    public String addToDB(Statement stmt,String user){

            try {
                identifier = retakeId(stmt);
                if(identifier == -1){
                 return "Error on db1";
                }else{
                    String valueId = "I"+identifier;
                    stmt.executeUpdate("INSERT INTO Issues(idIssue,title,created,ini,due,state,workspacetitle) VALUES ('"+valueId+"','" + this.title +"', '" +
                            this.createdBy +"', '" + this.ini+"', '"+ this.due +"', '"+this.state+"', '"+this.ws+"');");
                    identifier++;
                    stmt.executeUpdate("INSERT INTO ActionIssue(type,created,content,username,idIssue) VALUES ('created','"+this.ini+"','"+this.title+"','"+user+"','"+valueId+"');");
                    return "Issue created successful";
                }
            }catch(Exception e){return e.toString();}
    }

    public static String acceptIssues(Statement stmt,String user, String idIssue){
        int result =0;
        String by ="";
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM Issues WHERE state='pending' AND idIssue='"+idIssue+"'");
            if(rs.next()) result = 1;
            rs = stmt.executeQuery("SELECT ProposedBy FROM Propose WHERE idIssue='"+idIssue+"'");
            by = rs.getString(1);
        }catch(Exception e){ return e.toString();}
        if(result == 1){
            try{
                stmt.executeUpdate("UPDATE Issues SET state = 'assigned' WHERE idIssue = '"+idIssue+"'");
                stmt.executeUpdate("INSERT INTO Accept(user,idIssue) VALUES ('"+user+"','"+idIssue+"');");
                java.util.Date dueValue = new java.util.Date();
                java.sql.Date dateFinal = new java.sql.Date(dueValue.getYear() , dueValue.getMonth(), dueValue.getDate());
                stmt.executeUpdate("INSERT INTO ActionIssue(type,created,content,username,idIssue) VALUES ('assigned','"+dateFinal+"','"+by+"','"+user+"','"+idIssue+"');");
            }catch(Exception e){return e.toString();}

        }else{
            return "Error:Is not pending";
        }
        return "assigned successful to "+idIssue;
    }

    public static String assignIssue(String user,String idIssue,String assignedBy, Statement stmt){
        int result = 0;
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM Issues WHERE state='not assigned' AND idIssue='"+idIssue+"'");
            if(rs.next()) result=1;
        }catch(Exception e){ return e.toString();}
        if(result == 1){
            try{
                stmt.executeUpdate("INSERT INTO Propose(idIssue,ProposedBy,ProposedTo) VALUES ('"+idIssue+"','"+assignedBy+"','"+user+"');");
                stmt.executeUpdate("UPDATE Issues SET state = 'pending' WHERE idIssue = '"+idIssue+"'");
                java.util.Date dueValue = new java.util.Date();
                java.sql.Date dateFinal = new java.sql.Date(dueValue.getYear() , dueValue.getMonth(), dueValue.getDate());
                stmt.executeUpdate("INSERT INTO ActionIssue(type,created,content,username,idIssue) VALUES ('Proposed','"+dateFinal+"','"+assignedBy+"','"+user+"','"+idIssue+"');");
                return "pending successful to "+idIssue;
            }catch (Exception e){return e.toString();}
        }else return "Error: Is assigned, pending or no exist";
    }


    public static String updateIssue(String nameUser,String idIssue,String due,String nameIssue,Statement stmt){
        try{
            if(nameIssue.equals("-")){
                String[] date = due.split("/");
                java.util.Date dueValue = new java.util.Date();
                dueValue.setYear(Integer.valueOf(date[0])-1900);
                dueValue.setMonth(Integer.valueOf(date[1])-1);
                dueValue.setDate(Integer.valueOf(date[2]));
                java.sql.Date dateFinal = new java.sql.Date(dueValue.getYear() , dueValue.getMonth(), dueValue.getDate());

                stmt.executeUpdate("UPDATE Issues SET due='"+dateFinal+"' WHERE idIssue = '"+idIssue+"'");
                stmt.executeUpdate("INSERT INTO UpdateIssue(idIssue,updatedBy,type) VALUES ('"+idIssue+"','"+nameUser+"','Update date');");
                java.util.Date dueV = new java.util.Date();
                java.sql.Date dateF = new java.sql.Date(dueV.getYear() , dueV.getMonth(), dueV.getDate());
                stmt.executeUpdate("INSERT INTO ActionIssue(type,created,content,username,idIssue) VALUES ('Updated','"+dateF+"','-','"+nameUser+"','"+idIssue+"');");
                return "Update successful to "+idIssue;
            }else if(due.equals("-")){
                stmt.executeUpdate("UPDATE Issues SET title='"+nameIssue+"' WHERE idIssue = '"+idIssue+"'");
                stmt.executeUpdate("INSERT INTO UpdateIssue(idIssue,updatedBy,type) VALUES ('"+idIssue+"','"+nameUser+"','Update title');");
                java.util.Date dueValue = new java.util.Date();
                java.sql.Date dateFinal = new java.sql.Date(dueValue.getYear() , dueValue.getMonth(), dueValue.getDate());
                stmt.executeUpdate("INSERT INTO ActionIssue(type,created,content,username,idIssue) VALUES ('Updated','"+dateFinal+"','-','"+nameUser+"','"+idIssue+"');");
                return "Update successful to "+idIssue;
            }else{
                String[] date = due.split("/");
                java.util.Date dueValue = new java.util.Date();
                dueValue.setYear(Integer.valueOf(date[0])-1900);
                dueValue.setMonth(Integer.valueOf(date[1])-1);
                dueValue.setDate(Integer.valueOf(date[2]));
                java.sql.Date dateFinal = new java.sql.Date(dueValue.getYear() , dueValue.getMonth(), dueValue.getDate());
                stmt.executeUpdate("UPDATE Issues SET due='"+dateFinal+"',title='"+nameIssue+"' WHERE idIssue = '"+idIssue+"'");
                stmt.executeUpdate("INSERT INTO UpdateIssue(idIssue,updatedBy,type) VALUES ('"+idIssue+"','"+nameUser+"','Update date and title');");
                java.util.Date dueV = new java.util.Date();
                java.sql.Date dateF = new java.sql.Date(dueV.getYear() , dueV.getMonth(), dueV.getDate());
                stmt.executeUpdate("INSERT INTO ActionIssue(type,created,content,username,idIssue) VALUES ('Updated','"+dateF+"','-','"+nameUser+"','"+idIssue+"');");
                return "Update successful to "+idIssue;
            }
        }catch (Exception e){return e.toString();}
    }

    public static int checkOnWS(Statement stmt,String ws,String user){
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM Member WHERE username='"+user+"' AND workspacetitle='"+ws+"'");
            if(rs.next()) return 1;
            else return 0;
        }catch(Exception e){ return -1;}
    }

    private static int retakeId(Statement stmt){
        int result = 0;
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM Issues");
            while(rs.next()) result++;
            return result;
        }catch(Exception e){return -1;}


    }

    public static int checkProposed(Statement stmt,String id,String user){
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM Propose WHERE ProposedTo='"+user+"' AND idIssue='"+id+"'");
            if(rs.next()) return 1;
            else return 0;
        }catch(Exception e){ return -1;}
    }

}
