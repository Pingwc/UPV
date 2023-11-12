package calculadora;
import java.util.Scanner;
public class Calculadora {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner tec=new Scanner(System.in);
		String formula;
		do {
			System.out.print(">> ");
			formula=tec.nextLine();

			if(!formula.equals("off")) {
				//Quitar los espacios,parentesis y cambio de signos
				do {
					formula=formula.replaceAll(" ", "").replace("((","").replace("))","").replace("(", "").replace(")","").replace("+-", "-")
							.replace("-+", "-").replace("--", "+").replace("++", "+");
				} while(formula.indexOf("++" )!=-1 || formula.indexOf("--" )!=-1 || formula.indexOf("+-" )!=-1 || formula.indexOf("-+" )!=-1 ); 

				//Ver tipo de operación y posición del signo
				String op="";
				int ind=0;

				//System.out.println("Tipo de operación:");
				//op=tec.next();
				if(formula.lastIndexOf("*")!=-1) {
					op="*";
					ind=formula.lastIndexOf("*");
				}else if(formula.lastIndexOf("/")!=-1) {
					op="/";
					ind=formula.lastIndexOf("/");
				}else if(formula.lastIndexOf("-")!=0 && formula.lastIndexOf("-")!=-1) {
					op="-";
					ind=formula.lastIndexOf("-");
				}else if(formula.lastIndexOf("+")!=0 && formula.lastIndexOf("+")!=-1) {
					op="+";
					ind=formula.lastIndexOf("+");
				}else { 
					System.out.println("Operación no válida");
					System.exit(0);
				}

				//Extraer los valores

				double a=0.0;
				double b=0.0;
				boolean s=true;
				try {
					a=Double.parseDouble(formula.substring(0, ind));
					b=Double.parseDouble(formula.substring(ind+1));
				}catch(Exception e) {
					System.out.println("Error");
					s=false;
				}
				//Realizar la operación
				double result=0.0;
				switch(op) {
				case"+": result=a+b;break;
				case"-": result=a-b;break;
				case"*": result=a*b;break;
				case"/": result=a/b;
				}
				if(s) {
					int result1=0;
					if(result%1==0) {
						result1=(int) result;
						System.out.println(result1);
					}else {
						System.out.println(result);
					}
				}
			}
		}while(!formula.equals("off"));
		tec.close();
	}

}
