package weather;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import aircraft.*;

public class Simulation {

    public static int weatherChanges;
    public static ArrayList<String> toFile = new ArrayList<>();

    public static String decryptedString(String url) throws Exception {
        URL api = new URL(url);
        URLConnection connection = api.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        String decrypted = "";
        while ((line = bufferedReader.readLine()) != null) {
            decrypted = decrypted + line;
        }
        bufferedReader.close();
        return decrypted;
    }

    public static void hash(ArrayList<String> lines) throws Exception {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("scenario_hash.txt"));
            while ((line = reader.readLine()) != null)
                lines.add(line);
        }
        catch (Exception e) {
            System.out.println("Can't read the file!\n");
            System.exit(1);
        }
        String hash = "";
        for (String l: lines) {
            hash = hash + l + ";";
        }
        hash = hash.substring(0, hash.length() - 1);
        String get = decryptedString("http://md5decrypt.net/Api/api.php?hash=" + hash + "&hash_type=md5&email=kit.maksym@gmail.com&code=a7a71bc2ce74de6c");
        String getSplit[] = get.split(";");
        PrintWriter writer = new PrintWriter("scenario_decrypted.txt", "UTF-8");
        for (String st: getSplit) {
            writer.println(st);
        }
        writer.close();
        lines.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("scenario_decrypted.txt"));
            while ((line = reader.readLine()) != null)
                lines.add(line);
        }
        catch (Exception e) {
            System.out.println("Can't read the file!\n");
            System.exit(1);
        }
    }

    public static void validation(ArrayList<String> lines, WeatherTower weatherTower) throws Exception {
        try {
            if (lines.size() < 1)
                throw new Exception();

        } catch (Exception e) {
            System.out.println("Empty file!\n");
            System.exit(1);
        }
        try {
            weatherChanges = Integer.valueOf(lines.get(0));
            if (weatherChanges < 0)
                throw new Exception();
        } catch (Exception e) {
            System.out.println("The first line contains a positive integer number\n");
            System.exit(1);
        }
        AircraftFactory aircraftFactory = new AircraftFactory();
        for (int i = 1; i < lines.size(); i++) {
            String parts[] = lines.get(i).split(" ");
            if (!parts[0].toLowerCase().equals("baloon") && !parts[0].toLowerCase().equals("jetplane") && !parts[0].toLowerCase().equals("helicopter"))
                throw new Exception("Wrong type of flyable at " + ++i + " line\n");
            if (parts.length != 5)
                throw new Exception("Wrong number of arguments at " + ++i + " line\n");
            try {
                int longitude = Integer.parseInt(parts[2]);
                int leititude = Integer.parseInt(parts[3]);
                int height = Integer.parseInt(parts[4]);
                if (longitude < 0 || leititude < 0 || (height < 0 || height > 100))
                    throw new Exception();
                aircraftFactory.newAircraft(parts[0], parts[1], longitude, leititude, height).registerTower(weatherTower);
            }
            catch (Exception e) {
                System.out.println("Arguments must be positive integers and height can be 0...100!\n");
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1 || !args[0].equals("scenario.txt") && !args[0].equals("scenario_hash.txt")) {
            System.out.println("Usage: java ... scenario.txt\n");
            System.exit(1);
        }

        String line;
        ArrayList<String> lines = new ArrayList<String>();

        if (args[0].equals("scenario_hash.txt")) {
            hash(lines);
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("scenario.txt"));
                while ((line = reader.readLine()) != null)
                    lines.add(line);
            }
            catch (Exception e) {
                System.out.println("Can't read the file!\n");
                System.exit(1);
            }
        }
        WeatherTower weatherTower = new WeatherTower();

        validation(lines, weatherTower);

        for (; weatherChanges > 0; weatherChanges--) {
            weatherTower.changeWeather();
        }

        PrintWriter writer = new PrintWriter("simulation.txt", "UTF-8");
        for (String st: toFile) {
            writer.println(st);
        }
        writer.close();
    }
}
