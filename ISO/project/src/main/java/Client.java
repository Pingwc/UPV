import java.io.*;
import java.net.*;

public class Client {
    private static String host = "localhost";
    private static int port =6666 ;


    public static void main(String[] args) {
        try{
            System.out.println(" ***  Running Client  *** ");
            Socket connection = serverConnection();


            DataOutputStream dout=new DataOutputStream(connection.getOutputStream());
            DataInputStream din=new DataInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String sessionID = "-1";
            String str = "", str2;
            while(!str.equals("exit")){

                System.out.print(">");
                str = br.readLine();
                dout.writeUTF( sessionID + " " + str);
                dout.flush();
                str2=din.readUTF();


                if(str2.contains("Successfully login -")){
                    sessionID = str2.replace("Successfully login - ", "");
                    str2 = "Successfully login";
                    System.out.println ("ClientSessionID changed to: " + sessionID);
                }

                System.out.println(str2);

            }
            System.out.println("Connection Server: END");
            connection.close();

        }catch(Exception e){System.out.println(e);}
    }
    public static Socket serverConnection() throws IOException {
        //Control de errores + timeout

        System.out.println("Connecting to Server " + host + "at port: " + port);
        Socket s=new Socket(host,port);
        System.out.println("Connection Server DONE");
        return s;
    }
}


