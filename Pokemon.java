import java.util.Scanner;
public class Pokemon
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("<Atk type> -> <Def Types>");
		String input = scan.nextLine().toLowerCase();
		while(!input.equals(""))
		{
			String move = input.split(" ")[0];
			String[] defender = input.replaceAll(".*-> ", "").split(" ");
			if(getID(move) == -1){System.out.println("Invalid."); System.exit(1);}
			for(String d : defender)
			{
				if(getID(d) == -1){System.out.println("Invalid."); System.exit(1);}
			}
			double multiply = 1.00;
			for(String d : defender)
			{
				multiply = multiply * ((double)multiplier(move, d)/100);
			}
			System.out.println(multiply + "x damage!");
			System.out.println("<Atk type> -> <Def Types>");
			input = scan.nextLine();
		}
	}

	public static int multiplier(String atk, String def)
	{
		//0=0xdmg
		//1=1xdmg
		//2=2xdmg
		//.=.5xdmg
		String[] multipliers = new String[18];
		multipliers[0] =  "111111111111.011.1"; //nor
		multipliers[1] =  "1..122111112.1.121"; //fir
		multipliers[2] =  "12.1.111211121.111"; //wat
		multipliers[3] =  "112..111021111.111"; //ele
		multipliers[4] =  "1.21.11.2.1.21.1.1"; //gra
		multipliers[5] =  "1..12.1122111121.1"; //ice
		multipliers[6] =  "2111121.1...20122."; //fig
		multipliers[7] =  "1111211..111..1102"; //poi
		multipliers[8] =  "1212.112101.211121"; //gro
		multipliers[9] =  "111.21211112.111.1"; //fly
		multipliers[10] = "1111112211.11110.1"; //psy
		multipliers[11] = "1.1121..1.211.12.."; //bug
		multipliers[12] = "121112.1.2121111.1"; //roc
		multipliers[13] = "011111111121121.11"; //gho
		multipliers[14] = "1111111111111121.0"; //dra
		multipliers[15] = "111111.11121121.1."; //dar
		multipliers[16] = "1...121111112111.2"; //ste
		multipliers[17] = "1.11112.11111122.1"; //fai
		char mul = multipliers[getID(atk)].charAt(getID(def));
		if(mul == '.'){return 50;}
		else{return Integer.parseInt(""+mul)*100;}
	}

	public static int getID(String type)
	{
		String[] types = new String[]{"normal","fire","water","electric","grass","ice","fighting","poison","ground","flying","psychic","bug","rock","ghost","dragon","dark","steel","fairy"};
		for(int i = 0; i<types.length;i++)
		{
			if(type.equals(types[i])){return i;}
		}
		return -1;
	}
}