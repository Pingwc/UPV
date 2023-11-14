import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;
import java.util.Date;


public class Server {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static void main(String[] args) {

        String dataBase = "jdbc:sqlite:src/iso.db";
        int port =6666;

        logwritter(" [INFO] ***** START RUNNING SERVER ***** DB: " + dataBase + " Listen Port: "+ port);

        try{
            logwritter(" [INFO] - Try to connect to DB  " + dataBase);
            Connection con = DriverManager.getConnection(dataBase);
            Statement stmt = con.createStatement();
            logwritter(" [INFO] - Connect to DB  " + dataBase + " - OK");


            logwritter(" [INFO] - Creating Listen Socket at port: " +port);
            ServerSocket ss=new ServerSocket(port);
            logwritter(" [INFO] - ServerSocket created: " + ss);


            logwritter(" [WAITING FIRST CONNECTION] ");

            Socket s = ss.accept();// wait connection
            logwritter( " [INFO] - New connection established at: "+ ss +  " From: "+ s);
            DataInputStream din=new DataInputStream(s.getInputStream());
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());

            String str;
            String str2;
            String total;

            while(s.isConnected()){
                str=din.readUTF();
                String[] a = str.split(" ");
                System.out.println( java.time.LocalTime.now() + " [INPUT] < "+str + " from: "+s +" >");
                logwritter( " [INPUT] < "+ str + " from: "+s +" >");


                //TODO LOGIN HERE
                //TODO WHILE OF LOGIN
                switch(a[1]){
                    case "login":
                        if(a.length < 4){
                            str2 = "Error: you must provide the correct command arguments";
                        }else{
                            str2 = login(a[2], a[3], stmt);
                        }
                        dout(dout,str2, s);
                        break;
                    case "addUser":
                        if(a.length < 6){
                            str2 = "Error: you must provide the correct command arguments";
                        }else{
                        str2 = addUser(a[0], a[2], a[3], a[4],a[5], stmt);
                        }
                        dout(dout,str2,s);

                        break;
                    case "listUsers":
                        if(a.length != 2){
                            str2 = "Error: you must provide the correct command arguments";
                        }else {
                            str2 = listUsers(a[0], stmt);
                        }
                        dout(dout,str2,s);
                        break;
                    case "help":
                        str2 = help();
                        dout(dout,str2,s);
                        break;
                    case "logout":
                        if(a[0].equals("-1")){
                            str2 = "Good Bye!";
                            dout(dout,str2,s);
                            logwritter(" [INFO] Sesion " + s + "closed");

                        }else {
                            removeActiveSessions(getUserfromToken(a[0], stmt), stmt);
                            str2 = "Good Bye! All your active sessions are removed!";
                            logwritter( " [INFO] Sesion " + s + "closed. User " + getUserfromToken(a[0], stmt)+" purge Active Sessions");
                            dout(dout,str2,s);
                        }
                        try {
                            s.close();
                        }catch (Exception e){
                             logwritter( " [INFO] Sesion close IO" + e);
                        }
                        break;

                        /*START OF OPTNIONS THAN SESIONID IS NEEDED*/
                    case "removeUser":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if(a.length != 3){
                                str2 = "Error: you must provide the correct command arguments";
                            }else {
                                str2 = removeUser(a[0], a[2], stmt);
                            }
                        }
                        dout(dout, str2, s);
                        break;
                    case "infoUser":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if(a.length != 2){
                                str2 = "Error: you must provide the correct command arguments";
                            }else {
                                str2 = infoUser(a[0], stmt);
                            }
                        }
                        dout(dout, str2, s);
                        break;
                    case "addWorkspace":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length > 3) {
                                String b = a[a.length - 1].substring(a[a.length - 1].length() - 1, a[a.length - 1].length());
                                if (a[3].charAt(0) == '\"' && b.equals('\"')) {
                                    a[3] = a[3].replace("[", "");
                                    a[a.length - 1] = a[a.length - 1].replace("]", "");
                                    str2 = addWorkspace(a[0], a, stmt);
                                } else {
                                    str2 = "Please, use the following format:\n" +
                                            "   addWorkspace workspace_name [Description]\n" +
                                            "\"Description\" is an optional parameter\n";
                                }
                            } else {
                                str2 = addWorkspace(a[0], a[2], stmt);
                            }
                        }
                        dout(dout,str2,s);
                        break;

                    case "removeWorkspace":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length != 3) {
                                str2 = "Error: you must provide the correct command arguments";
                            } else {
                                str2 = removeWorkspace(a[0], a[2], stmt);
                            }
                        }
                        dout(dout,str2,s);
                        break;
                    case "listWorkspaces":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length != 2) {
                                str2 = "Error: you must provide the correct command arguments";
                            } else {
                                str2 = listWorkspaces(a[0], stmt);
                            }
                        }
                        dout(dout,str2,s);
                        break;
                    case "infoWorkspace":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length != 3) {
                                str2 = "Error: you must provide the correct command arguments";
                            } else {
                                str2 = infoWorkspace(a[0], a[2], stmt);
                            }
                        }
                        dout(dout,str2,s);
                        break;
                    case "join":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length != 4) {
                                str2 = "Error: you must provide the correct command arguments";
                            } else {
                                str2 = join(a[0], a[2], a[3], stmt);
                            }
                        }
                        dout(dout,str2,s);
                        break;
                    case "leave":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length != 4) {
                                str2 = "Error: you must provide the correct command arguments";
                            } else {
                                str2 = leave(a[0], a[2], a[3], stmt);
                            }
                        }
                        dout(dout,str2,s);
                        break;

                    //ISSUES
                    case "addIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            //assign deadline to 1 year later
                            java.util.Date dueValue = new Date();
                            java.sql.Date due = new java.sql.Date(dueValue.getYear() + 1, dueValue.getMonth(), dueValue.getDate());

                            str2 = addIssue(a[0], a[2], due, a[3], stmt);
                        }
                        dout(dout,str2,s);
                        break;
                    case "listIssues":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length < 3) {
                                str2 = "Error: enter a list name";
                            } else if (a.length == 3) {
                                total = a[2];
                                if (total.equals("not assigned") || total.equals("pending") || total.equals("completed") || total.equals("assigned") || total.equals("rejected")) {
                                    str2 = listIssues(a[0], total, stmt);
                                } else {
                                    str2 = "Error: non-existent list";
                                }
                            }else if (a.length == 4) {
                                    total = a[2] + " " + a[3];
                                if (total.equals("not assigned") || total.equals("pending") || total.equals("completed") || total.equals("assigned") || total.equals("rejected")) {
                                    str2 = listIssues(a[0], total, stmt);
                                } else {
                                    str2 = "Error: non-existent list";
                                }
                            } else {
                                str2 = "Error: you must provide the correct command arguments";
                            }
                        }
                        dout(dout,str2,s);
                        break;
                    case "acceptIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length != 3) {
                                str2 = "Error: you must provide the correct command arguments";
                            } else {
                                str2 = acceptIssue(a[0], a[2], stmt);
                            }
                        }
                        dout(dout,str2,s);
                        break;
                    case "assignIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length != 4) {
                                str2 = "Error: you must provide the correct command arguments";
                            } else {
                                str2 = assignIssue(a[0], a[2], a[3], stmt);
                            }
                        }
                        dout(dout,str2,s);
                        break;
                    case "updateIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length != 5) {
                                str2 = "Error: you must provide the correct command arguments";
                            } else {
                                str2 = UpdateIssue(a[0], a[2], a[3], a[4],stmt);
                            }
                        }
                        dout(dout,str2,s);
                        break;
                    case "commentIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length <= 2) {
                                str2 = "Please introduce the issue name and it's comment";
                            } else if (a.length == 3) {
                                str2 = "Please introduce the issue comment";
                            } else {
                                String comment_start = a[3];
                                String comment_end = a[a.length - 1];
                                String complete_comment = "";

                                // checks if there is an inital " and checks if there is " at the end
                                if (comment_start.charAt(0) == '"' && comment_end.charAt(comment_end.length() - 1) == '"') {
                                    for (int i = 3; i <= a.length - 1; i++) {
                                        if (i != a.length - 1)
                                            complete_comment = complete_comment + a[i] + " ";
                                        else
                                            complete_comment = complete_comment + a[i];
                                    }
                                    complete_comment = complete_comment.replace("\"", "");

                                    str2 = commentIssue(a[0], a[2], complete_comment, stmt);
                                } else {
                                    str2 = "This is not a comment";
                                }
                            }
                        }
                        dout(dout, str2, s);
                        break;

                    case "completeIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length < 3) {
                                str2 = "Please introduce the name of the issue";
                            } else if (a.length == 3) {
                                str2 = completeIssue(a[0], a[2], stmt);
                            } else {
                                str2 = "Incorrect number of arguments, check the help command";
                            }
                        }
                        dout(dout, str2, s);
                        break;

                    case "removeIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length < 3) {
                                str2 = "Please introduce the name of the issue";
                            } else if (a.length == 3) {
                                str2 = removeIssue(a[0], a[2], stmt);
                            } else {
                                str2 = "Incorrect number of arguments, check the help command";
                            }
                        }
                        dout(dout, str2, s);
                        break;

                    case "rejectIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length < 3) {
                                str2 = "Please introduce the name of the issue";
                            } else if (a.length == 3) {
                                str2 = rejectIssue(a[0], a[2], stmt);
                            } else {
                                str2 = "Incorrect number of arguments, check the help command";
                            }
                        }
                        dout(dout, str2, s);
                        break;

                    case "proposeIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length < 3) {
                                str2 = "Please introduce the name of the issue";
                            } else if (a.length == 3) {
                                str2 = proposeIssue(a[0], a[2], stmt);
                            } else {
                                str2 = "Incorrect number of arguments, check the help command";
                            }
                        }
                        dout(dout, str2, s);
                        break;
                    case "showIssue":
                        if(a[0].equals("-1")){
                            str2 = "You need to be logged first" ;
                        }else {
                            if (a.length < 3) {
                                str2 = "Please introduce the name of the issue";
                            } else if (a.length == 3) {
                                str2 = showIssue(a[0], a[2], stmt);
                            } else {
                                str2 = "Incorrect number of arguments, check the help command";
                            }
                        }
                        dout(dout, str2, s);
                        break;

                    default:
                        str2 ="Error: The command you have introduced does not exist at this server.";
                        dout(dout,str2,s);
                }
            }

        }catch(Exception e){logwritter(e.toString());}
    }

    //Look for the user in the DDBB
    private static String login(String user, String psw, Statement stmt) {
        String aux, token;
        String queryReturn;
        try {
            ResultSet rs = stmt.executeQuery("SELECT password FROM User WHERE USER.name = " +
                    "'" + user + "'" + ";");
            queryReturn = rs.getString(1);
            logwritter(" [DEBUG] - User: " + user + " | Pass DB: " + queryReturn + "  | Pass Input:  " + psw  );
            if (queryReturn.equals(psw)){
                token = generateNewToken(); //Generates a token for a user correctly authenticated
                logwritter(" [INFO] - Login Success. User: " + user + " - SessionID : " + token );
                aux = "Successfully login - " + token;
                stmt.executeUpdate("INSERT INTO UserSessions(token,user) VALUES ('" + token +"'" + "," + "'" + user + "');");
                logwritter(" [INFO] - SessionID: "+ token +" assigned to User: " + user);
            }else{
                logwritter(" [ERROR] - Password attempt failed from user " + user );
                aux = "Login failed";
            }
        }catch(Exception e){
            aux = e.toString();
            logwritter(" [ERROR] - Failed connecting with the DB ");
        }
        return aux;
    }

    //Register a user in the DDBB
    private static String addUser(String token, String user, String surname, String email, String psw, Statement stmt){
        String aux;
        try {
        stmt.executeUpdate("INSERT INTO User(name,surname, email, password) VALUES ('" + user +"'," +
                "'" + surname +"','" + email +"', '" + psw+"');");
        aux = user + " Has been created successful";
            logwritter( " [INFO] User "+ getUserfromToken(token, stmt) + " at sesion " + token + " try to delete user: " + user + ". ");
        }catch(Exception e){aux = e.toString();
            logwritter(" [ERROR] - Can't create "+ user );}

        return aux;
    }

    //Remove a user from the DDBB
    private static String removeUser(String token, String user, Statement stmt){
        String aux;
        if (!user.equals (getUserfromToken(token, stmt)) ) {
            aux= "YOU ONLY CAN DELETE YOUR OWN USER";
            logwritter( " [INFO] User "+ getUserfromToken(token, stmt) + " at sesion " + token + " try to delete user: " + user + ". ");
        }
        else {
        try {
            stmt.executeUpdate("DELETE FROM User WHERE name = '" + user + "';");
            logwritter( " [INFO] User "+ getUserfromToken(token, stmt)+ " deleted the Account " + " at sesion " + token );

            aux = user + " Has been removed successful. ";
            // TO-DO hacer loggout y eliminar todos las sesiones activas de ese usuario

        }catch(Exception e){
            aux = e.toString();
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);}

    }
        return aux;
    }


    //Show the description of a User
    private static String infoUser(String token, Statement stmt){
        String aux = "";
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE User.name = '"+ getUserfromToken(token, stmt) + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (i > 1) System.out.print(",  ");
                    aux = aux + (rsmd.getColumnName(i) + ": " + rs.getString(i) );
                    if(i!= rsmd.getColumnCount()) aux+= "\n";
                }
            }
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " reply for user information" + " at sesion " + token);
        }catch(Exception e){
            aux =(e.toString());
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);
        }
        return aux;
    }


    //Register a workspace in the DDBB
    private static String addWorkspace(String token, String workspace_name, Statement stmt){
        String aux;
        try {
            stmt.executeUpdate("INSERT INTO Workspace(title) VALUES ('" + workspace_name + "');");
            aux = workspace_name+ " Has been created successful";
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " added a workspace: "+workspace_name+  " at sesion " + token);
        }catch(Exception e){
            aux = e.toString();
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);}
        return aux;
    }

    //Register a workspace in the DDBB
    private static String addWorkspace(String token, String [] a, Statement stmt){
        String aux;
        boolean val = false;
        StringBuilder sb = new StringBuilder();
        int i = 3;
        while (!val){
            if(i != a.length-1){
                sb.append(a[i]+" ");
                i++;
            }else{
                sb.append(a[i]);
                val = true;
            }
        }
        try {
            stmt.executeUpdate("INSERT INTO Workspace(title, desc) VALUES ('" + a[2] +"'" + "," + "'" + sb + "');");
            aux = a[2] +" Has been created successful";
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " added the workspace: "+a[2]+  " at sesion " + token);
        }catch(Exception e){
            aux = e.toString();
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);}
        return aux;
    }

    //Remove a workspace in the DDBB
    private static String removeWorkspace(String token,String workspace_name, Statement stmt){
        String aux ="";
        try {
            System.out.println("DELETE FROM Workspace WHERE title = '" + workspace_name + "');");
            stmt.executeUpdate("DELETE FROM Workspace WHERE title = '" + workspace_name + "';");
            aux = workspace_name+ " Has been removed successful";
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " removed the workspace: "+workspace_name+  " at sesion " + token);
        }catch(Exception e){
            aux = e.toString();
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);}
        return aux;
    }

    //List all the workspaces in the DDBB
    private static String listWorkspaces(String token, Statement stmt){
        String aux = "";
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Workspace");

            while(rs.next())
            {
                aux = aux+rs.getString(1)+"\n";
            }
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " reply for workspaces information at session " + token);
        }catch(Exception e){
            aux = e.toString();
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);}
        return aux;
    }

    //Show the description of a Workspace
    private static String infoWorkspace(String token, String workspace_name, Statement stmt){
        String aux = "";
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Workspace WHERE title = '"+workspace_name + "'");
            aux = aux+rs.getString(2);
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " reply for "+ workspace_name + " description  at session " + token);
            if(aux.equals("null"))
                aux = "(There is not a description yet)";
        }catch(Exception e){
            aux = e.toString();
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);}
        return aux;
    }

    //List all the user in the DDBB
    private static String listUsers(String token, Statement stmt){
        String aux = "";
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while(rs.next())
            {
                aux = aux+rs.getString(1)+"\n";
            }
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " reply for a list of all users at session " + token);
        }catch(Exception e){
            aux = e.toString();
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);}
        return aux;
    }

    //Add a user to a workspace
    private static String join(String token, String user, String workspace_name, Statement stmt){
    String aux = "";
        try {
            stmt.executeUpdate("INSERT INTO Member(username,workspacetitle) VALUES ('" + user + "','" + workspace_name + "');");
            aux = user + " added successful to " + workspace_name;
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " introduced user: "+ user+ " at the workspace: "+ workspace_name + " at session " + token);
        }catch (Exception e){
            aux = e.toString();
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);}
        return aux;
    }
    //TODO leave
    //Removes a user from a workspace
    private static String leave(String token, String user, String workspace_name, Statement stmt){
        String aux;
        try {
            stmt.executeUpdate("DELETE FROM Member WHERE Member.username = '" + user+
                    "' AND Member.workspacetitle = '" + workspace_name + "';");
            aux = user +" deleted successful from " + workspace_name;
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " deleted from the workspace: "
                    + workspace_name + " at session " + token);
        }catch (Exception e){
            aux = e.toString();
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);}
        return aux;
    }

    private  static  String addIssue(String token, String title,java.sql.Date due,String ws, Statement stmt) {
        int checkOnWS = Issue.checkOnWS(stmt,ws,getUserfromToken(token, stmt));
        if(checkOnWS == 1){
            try {
                java.util.Date iniValues = new Date();
                java.sql.Date ini = new java.sql.Date(iniValues.getYear(), iniValues.getMonth(), iniValues.getDate());
                Issue newIssue = new Issue(title, getUserfromToken(token, stmt), ini, due, ws);
                logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " added the Issue: "+ title + " at session " + token);
                return newIssue.addToDB(stmt,getUserfromToken(token, stmt));

            } catch (Exception e){
                logwritter(" [ERROR]  Failed connecting with the DB: " + e);
                return e.toString();
            }
        }else if(checkOnWS == 0){
            return "You don't have permission to add Issues on this workspace";
        }else return "Error on db";
    }

    private static String listIssues(String token, String condition, Statement stmt){
        String aux = "";
        try {
            System.out.println(condition);
            ResultSet rs = stmt.executeQuery("SELECT * FROM Issues WHERE state = '"+condition+"'");
            while(rs.next()){
                aux = aux+rs.getString(1)+", "+rs.getString(2)+"\n";
            }
            logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " reply for issues information at session " + token);
            return aux;
        }catch(Exception e){
            logwritter(" [ERROR]  Failed connecting with the DB: " + e);
            return e.toString();
           }
    }

    private static String acceptIssue(String token,String idIssue, Statement stmt ){
        try {
            int checkProposedToUser = Issue.checkProposed(stmt,idIssue,getUserfromToken(token, stmt));
            if(checkProposedToUser == 1){
                logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " accepted the issue: " + idIssue + " at session " + token);
                return Issue.acceptIssues(stmt, getUserfromToken(token, stmt), idIssue);
            }else if(checkProposedToUser == 0) return "this Issue is not proposed to " + getUserfromToken(token, stmt);
            else return "Error on db";
        }catch(Exception e){
            logwritter(" [ERROR]  Failed connecting with the DB: " + e);
            return  e.toString();}
        }

    private static String assignIssue(String token, String idIssue,String assignedTo, Statement stmt){
        try {
            int checkOnWS = Issue.checkOnWS(stmt,
                    stmt.executeQuery("SELECT * FROM Issues WHERE idIssue = '"+idIssue+"'").getString(7),assignedTo);
            if(checkOnWS == 1) {

                    logwritter(" [INFO] User " + getUserfromToken(token, stmt) + " assigned the issue: " + idIssue + " to User: " + assignedTo + " at session " + token);
                    return Issue.assignIssue(assignedTo, idIssue,getUserfromToken(token, stmt), stmt);

            }else if(checkOnWS == 0){
                return assignedTo+" is not on this workspace";
            }else return "Error on db";
        } catch (Exception e) {
            logwritter(" [ERROR]  Failed connecting with the DB: " + e);
            return e.toString();
        }
    }

    public static String UpdateIssue(String token,String idIssue,String due,String name,Statement stmt){
        try{
            int checkOnWS = Issue.checkOnWS(stmt,
                    stmt.executeQuery("SELECT * FROM Issues WHERE idIssue = '"+idIssue+"'").getString(7),getUserfromToken(token,stmt));
            if(checkOnWS == 1){
                if(name.equals("-") && due.equals("-")){
                    return "the value name and date mustn't be null at the same time";
                }else{
                    logwritter(" [INFO] User " + getUserfromToken(token, stmt) + " update the issue: " + idIssue + " at session " + token);
                    return Issue.updateIssue(getUserfromToken(token,stmt),idIssue,due,name,stmt);
                }

            }else if(checkOnWS == 0){
            return getUserfromToken(token,stmt)+" is not on this workspace";
        }else return "Error on db";
        }catch (Exception e){
            logwritter(" [ERROR]  Failed connecting with the DB: " + e);
            return e.toString();
        }
    }

    public static String commentIssue(String token, String idIssue ,String content, Statement stmt){
        String aux;
        int result = 0;
        try{
            // comprueba que el estado de la Issue es correcto y si existe el id
            ResultSet rs = stmt.executeQuery("SELECT * FROM Issues WHERE (state='assigned' OR state='accepted' OR state='not assigned') AND idIssue='"+idIssue+"' ");
            if(rs.next()) result = 1; //si existe devuelve 1
        }catch(Exception e){ return e.toString();}
        if(result == 1) { // si existe intentara crear el comentario de la Issue
            try {
                java.util.Date iniValues = new Date();
                java.sql.Date ini = new java.sql.Date(iniValues.getYear(), iniValues.getMonth(), iniValues.getDate());

                // insterta en ela tabla comment la fecha de creacion, el comentario, el usuario que lo ha creado y el idIssue
                stmt.executeUpdate("INSERT INTO ActionIssue(type ,created, content, username, idIssue) VALUES ( '" + "Comment" + "'" + "," + "'" + ini + "'" + "," + "'" + "-" + "'" + "," + "'"
                        + getUserfromToken(token, stmt) + "'" + "," + "'" + idIssue + "')");
                aux = idIssue + " Has been commented";
                logwritter(" [INFO] User " + getUserfromToken(token, stmt) + " commented the issue: " + idIssue + " at session " + token);
            } catch (Exception e) {
                aux = e.toString();
                logwritter(" [ERROR]  Failed connecting with the DB: " + e);
            }
            return aux;
        }else return "Error: the issue is not assigned, accepted or does not exist";
    }

    public static String completeIssue(String token, String idIssue, Statement stmt){
        String aux;
        int result = 0;
        try {
            //comprueba que el estado de la Issue y si la Issue existe
            //cuando no lo encuentra se para la ejecucion del programa INTENTAR SOLUCIONAR
            ResultSet rs = stmt.executeQuery("SELECT * FROM Issues WHERE state='assigned' AND idIssue='" + idIssue + "'");
            if (rs.next()) result = 1;
        }catch (Exception e){return e.toString();}
        if(result == 1) {// si el estado de la Issue es correcto y el idIssue se cambia el estado de la Issue
            try {
                java.util.Date iniValues = new Date();
                java.sql.Date ini = new java.sql.Date(iniValues.getYear(), iniValues.getMonth(), iniValues.getDate());

                stmt.executeUpdate("UPDATE Issues SET state = 'completed' WHERE idIssue = '" + idIssue + "' ");
                // insterta en la tabla comment el tipo, la fecha de creacion, el comentario, el usuario que lo ha creado y el idIssue
                stmt.executeUpdate("INSERT INTO ActionIssue(type ,created, content, username, idIssue) VALUES ( '" + "Completed" + "'" + "," + "'" + ini + "'" + "," + "'" + "-" + "'" + "," + "'"
                        + getUserfromToken(token, stmt) + "'" + "," + "'" + idIssue + "')");
                aux = idIssue + " Has been completed";

                logwritter(" [INFO] User " + getUserfromToken(token, stmt) + " rejected the issue: " + idIssue + " at session " + token);
            } catch (Exception e) {
                aux = e.toString();
                logwritter(" [ERROR]  Failed connecting with the DB: " + e);
            }
            return aux;
        }else return "Error: the state of the issue not accepted or does not exist";
    }

    private static String removeIssue(String token, String idIssue, Statement stmt){
        String aux;
        int result = 0;
        try {
            //comprueba que el estado de la Issue y si la Issue existe
            //cuando no lo encuentra se para la ejecucion del programa INTENTAR SOLUCIONAR
            ResultSet rs = stmt.executeQuery("SELECT * FROM Issues WHERE idIssue='" + idIssue + "'");
            if (rs.next()) result = 1;
        }catch (Exception e){return e.toString();}
        if(result == 1) {// si el estado de la Issue es correcto y el idIssue se cambia el estado de la Issue
            try {
                stmt.executeUpdate("DELETE FROM Issues WHERE idIssue = '" + idIssue + "';");
                aux = "The issue " + idIssue + " has been removed";
                logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " removed the issue: " + idIssue + " at session " + token);
            } catch (Exception e) {
                aux = e.toString();
                logwritter(" [ERROR]  Failed connecting with the DB: " + e);
            }
            return aux;
        }else return "Error: The Issue does not exist";


    }

    public static String rejectIssue(String token, String idIssue, Statement stmt){
        String aux;
        int result = 0;
        try {
            //comprueba que el estado de la Issue y si la Issue existe
            //cuando no lo encuentra se para la ejecucion del programa INTENTAR SOLUCIONAR
            ResultSet rs = stmt.executeQuery("SELECT * FROM Issues WHERE (state='pending') AND idIssue='" + idIssue + "'");
            if (rs.next()) result = 1;
        }catch (Exception e){return e.toString();}
        if(result == 1) {// si el estado de la Issue es correcto y el idIssue se cambia el estado de la Issue
            try {
                java.util.Date iniValues = new Date();
                java.sql.Date ini = new java.sql.Date(iniValues.getYear(), iniValues.getMonth(), iniValues.getDate());
                String content = "";
                stmt.executeUpdate("UPDATE Issues SET state = 'rejected' WHERE idIssue = '" + idIssue + "' ");
                // insterta en la tabla comment el tipo, la fecha de creacion, el comentario, el usuario que lo ha creado y el idIssue
                stmt.executeUpdate("INSERT INTO ActionIssue(type ,created, content, username, idIssue) VALUES ( '" + "rejected" + "'" + "," + "'" + ini + "'" + "," + "'" + content + "'" + "," + "'"
                        + getUserfromToken(token, stmt) + "'" + "," + "'" + idIssue + "')");
                aux = idIssue + " Has been rejected";

                logwritter(" [INFO] User " + getUserfromToken(token, stmt) + " rejected the issue: " + idIssue + " at session " + token);
            } catch (Exception e) {
                aux = e.toString();
                logwritter(" [ERROR]  Failed connecting with the DB: " + e);
            }
            return aux;
        }else return "Error: the state of the issue not accepted or does not exist";
    }

    public static String proposeIssue(String token, String idIssue, Statement stmt){
        String aux;
        int result = 0;
        try {
            //comprueba que el estado de la Issue y si la Issue existe
            //cuando no lo encuentra se para la ejecucion del programa INTENTAR SOLUCIONAR
            ResultSet rs = stmt.executeQuery("SELECT * FROM Issues WHERE (state='rejected' OR state='assigned' OR state='') AND idIssue='" + idIssue + "'");
            if (rs.next()) result = 1;
        }catch (Exception e){return e.toString();}
        if(result == 1) {// si el estado de la Issue es correcto y el idIssue se cambia el estado de la Issue
            try {
                java.util.Date iniValues = new Date();
                java.sql.Date ini = new java.sql.Date(iniValues.getYear(), iniValues.getMonth(), iniValues.getDate());
                String content = "";
                stmt.executeUpdate("UPDATE Issues SET state = 'completed' WHERE idIssue = '" + idIssue + "' ");
                // inserta en la tabla comment el tipo, la fecha de creacion, el comentario, el usuario que lo ha creado y el idIssue
                stmt.executeUpdate("INSERT INTO ActionIssue(type ,created, content, username, idIssue) VALUES ( '" + "proposed" + "'" + "," + "'" + ini + "'" + "," + "'" + content + "'" + "," + "'"
                        + getUserfromToken(token, stmt) + "'" + "," + "'" + idIssue + "')");
                aux = idIssue + " Has been proposed";

                logwritter(" [INFO] User " + getUserfromToken(token, stmt) + " rejected the issue: " + idIssue + " at session " + token);
            } catch (Exception e) {
                aux = e.toString();
                logwritter(" [ERROR]  Failed connecting with the DB: " + e);
            }
            return aux;
        }else return "Error: the state of the issue not accepted or does not exist";

    }

    public static String showIssue(String token, String idIssue, Statement stmt){
        String aux = "";
        try {
            int checkOnWS = Issue.checkOnWS(stmt,
                    stmt.executeQuery("SELECT * FROM Issues WHERE idIssue = '"+idIssue+"'").getString(7),getUserfromToken(token,stmt));
            if(checkOnWS == 1){
                ResultSet rs = stmt.executeQuery("SELECT * FROM ActionIssue WHERE idIssue = '"+ idIssue + "'");
                while (rs.next()) {
                    if(rs.getString(1).equals("created")){
                        aux = aux+rs.getString(5)+","+rs.getString(3)+"\n";
                    }else if(rs.getString(1).equals("Proposed")){
                        aux = aux+"Proposed by "+rs.getString(4)+" at "+rs.getString(2).toString()+"\n";
                    }else if(rs.getString(1).equals("assigned")){
                        aux = aux+"Assigned to "+rs.getString(4)+" by "+rs.getString(3)+" at " +rs.getString(2).toString()+"\n";
                    }else if(rs.getString(1).equals("Updated")){
                        aux = aux+"Updated by " + rs.getString(4) + " at "+rs.getString(2).toString()+"\n";
                    }else if(rs.getString(1).equals("Comment")){
                    aux = aux+"Commented by " + rs.getString(4) + " at "+rs.getString(2).toString()+"\n";
                    }else if(rs.getString(1).equals("Completed")){
                        aux = aux+"Completed by " + rs.getString(4) + " at "+rs.getString(2).toString()+"\n";
                    }
                }
                logwritter(" [INFO] User "+ getUserfromToken(token, stmt) + " reply for Issue:"+ idIssue+" information" + " at sesion " + token);
            }else if(checkOnWS == 0){
                return getUserfromToken(token,stmt)+" is not on this workspace";
            }else return "Error on db";
        }catch(Exception e){
            logwritter(" [ERROR]  Failed connecting with the DB: "+ e);
            return (e.toString());
        }
        return aux;
    }

    //List all the commands
    private static String help(){
        return  "\u001B[1m***USER COMMANDS***\u001B[0m\n" +
                "\033[3mlogin user_name password\033[0m\n" +
                "\033[3mlogout\033[0m\n" +
                "\033[3maddUser user_name user_surname user_email password\033[0m\n" +
                "\033[3mremoveUser user_name\033[0m\n" +
                "\033[3mlistUser\033[0ms\n" +
                //TODO "infoUser\n +
                "\n" +
                "\u001B[1m***WORKSPACE COMMANDS***\u001B[0m\n" +
                "\033[3maddWorkspace workspace_name\033[0m\n" +
                "\033[3mremoveWorkspace workspace_name\033[0m\n" +
                "\033[3mlistWorkspaces\033[0m\n" +
                "\033[3minfoWorkspace workspace_name\033[0m\n" +
                "\033[3mjoin user_name workspace_name\033[0m\n" +
                "\033[3mleave user_name workspace_name\033[0m\n" +
                "\n" +
                "\u001B[1m***ISSUES COMMANDS***\u001B[0m\n" +
                "\033[3maddIssue issue_name workspace_name\033[0m\n" +
                "\033[3mupdateIssue issue_id due_DATE\033[0m\n" +
                "\033[3mlistIssues not assigned|pending|accepted|completed|rejected\033[0m\n" +
                "\033[3macceptIssue issue_id\033[0m\n" +
                "\033[3massignIssue issue_id\033[0m\n" +
                "\033[3mcommentIssue issue_id \"comment\"\033[0m\n" +
                "\033[3mcompleteIssue issue_id\033[0m\n" +
                "\033[3mremoveIssue issue_id\033[0m\n" +
                "\033[3mrejectIssue issue_id\033[0m\n" +
                "\033[3mproposeIssue issue_id\033[0m\n" +
                "\033[3mshowIssue issue_id\033[0m\n";
    }


    //Auxiliar Methods
    private static void removeActiveSessions(String user, Statement stmt){
        try {
            stmt.executeQuery("DELETE FROM UserSessions WHERE UserSessions.user = " + "'" + user + "'" + ";");

            logwritter( " [INFO] - User: "+user+ " Exit. purged all the active sessions");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logwritter(" [ERROR] - Failed connecting with the DB: "+ throwables.toString() );
        }
    }


    private static void dout(DataOutputStream dout,String str2, Socket s){
        try{
            dout.writeUTF(str2);
            dout.flush();
            System.out.println(  System.currentTimeMillis() + " [SEND]  " + str2 + " to: " + s) ;
        }catch(Exception e){System.out.println(e);}
    }

    private static void logwritter(String message){
        String path = "logs/"+java.time.LocalDate.now();
        FileWriter fichero = null;
        PrintWriter pw = null;
        System.out.println(java.time.LocalTime.now() + message);
        try
        {
            fichero = new FileWriter(path,true);
            pw = new PrintWriter(fichero, true);

                pw.println(java.time.LocalTime.now() + message);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

private static String getUserfromToken(String token, Statement stmt) {
    String queryReturn = "";
    try {
        ResultSet rs = stmt.executeQuery("Select user FROM UserSessions WHERE UserSessions.token = " +
                "'" + token + "'" + ";");
        queryReturn = rs.getString(1);
    } catch (SQLException throwables) {
        queryReturn = "Failed connecting with the DB";
        throwables.printStackTrace();
        logwritter( " [ERROR] - Failed connecting with the DB: "+ throwables.toString() );
    }
    return queryReturn;
}


}

