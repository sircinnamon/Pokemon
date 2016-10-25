import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.lang.StringBuilder;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.*;
public class Pokemon
{
	public static final String API = "https://pokeapi.co/api/v2";
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("<Atk type/move> -> <Def Types/Pokemon>");
		String input = scan.nextLine().toLowerCase();
		while(!input.equals(""))
		{
			String atk = input.split(" ->")[0];
			String def = input.split(" -> ")[1];
			//check if atk is type or move
			//if move, replace with type
			if(!isType(atk))
			{
				try
				{
					JSONObject move = getObject("/move/"+atk.replaceAll(" ","-")+"/");
					atk = move.getJSONObject("type").getString("name");
				}catch(Exception e)
				{
					System.out.println("Error in atk");
					e.printStackTrace();
				}
			}
			//check if def is type or pokemon
			//if pokemon, replace with type(s)
			if(!isType(def))
			{
				try
				{
					JSONObject pokemon = getObject("/pokemon/"+def.replaceAll(" ","-")+"/");
					def = "";
					JSONArray arr = pokemon.getJSONArray("types");
					for(int i = 0; i < arr.length();i++)
					{
						def = def + " " + arr.getJSONObject(i).getJSONObject("type").getString("name");
					}
				}catch(Exception e)
				{
					System.out.println("Error in def");
					e.printStackTrace();
				}
			}
			System.out.println("atk: "+atk);
			System.out.println("def: "+def);
			//compare atk types to def types and get multipliers
			double multiply = -1;
			try
			{
				multiply = calculateMultiplier(atk, def.trim().split(" "));
			}catch(JSONException e)
			{
				e.printStackTrace();
			}
			System.out.println(multiply + "x damage!");
			System.out.println("<Atk type/move> -> <Def Types/Pokemon>");
			input = scan.nextLine();
		}
	}

	public static JSONObject getObject(String subaddr)
	{
		StringBuilder page = new StringBuilder();
		try
		{
			URL url = new URL(API+subaddr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line = read.readLine()) != null)
			{
				page.append(line);
			}
			read.close();
			return new JSONObject(page.toString());
		}catch(Exception e)
		{
			System.out.println("Error in "+subaddr);
			e.printStackTrace();
		}
		return new JSONObject();
	}

	public static boolean isType(String type)
	{
		String[] types = new String[]{"normal","fire","water","electric","grass","ice","fighting","poison","ground","flying","psychic","bug","rock","ghost","dragon","dark","steel","fairy"};
		for(String t : types)
		{
			if(type.equals(t)){return true;}
		}
		return false;
	}
	
	public static double calculateMultiplier(String atk, String[] def) throws JSONException
	{
		double m = 1;
		JSONObject attackType = getObject("/type/"+atk+"/");
		attackType = attackType.getJSONObject("damage_relations");
		for(String s : def)
		{
			m = m*parseJsonMultiplier(attackType, s);
		}
		return m;
	}

	public static double parseJsonMultiplier(JSONObject relations, String defType) throws JSONException
	{
		JSONArray zerodmg = relations.getJSONArray("no_damage_to");
		for(int i = 0; i<zerodmg.length();i++)
		{
			if(zerodmg.getJSONObject(i).getString("name").equals(defType)){return 0;}
		}
		JSONArray halfdmg = relations.getJSONArray("half_damage_to");
		for(int i = 0; i<halfdmg.length();i++)
		{
			if(halfdmg.getJSONObject(i).getString("name").equals(defType)){return 0.5;}
		}
		JSONArray doubledmg = relations.getJSONArray("double_damage_to");
		for(int i = 0; i<doubledmg.length();i++)
		{
			if(doubledmg.getJSONObject(i).getString("name").equals(defType)){return 2;}
		}
		return 1;
	}
}