package me.mastadons.rankshowcase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.mastadons.rankshowcase.structure.Rank;
import me.mastadons.rankshowcase.structure.VectorLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class RankShowcaseTest {

    public static void main(String[] arguments) throws IOException {
        List<Rank> rankList = new ArrayList<>();
        rankList.add(new Rank("rank1", 5, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<VectorLocation>()));
        rankList.add(new Rank("rank2", 12, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<VectorLocation>()));
        rankList.add(new Rank("rank3", 33, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<VectorLocation>()));
        rankList.add(new Rank("rank4", 0, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<VectorLocation>()));
        rankList.add(new Rank("rank5", -1, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<VectorLocation>()));

        Collections.sort(rankList, Comparator.comparingInt(rank -> rank.priority));
        rankList.forEach(rank -> System.out.println(rank.name + " " + rank.priority));
    }

    public String getMinecraftName(UUID id) throws IOException {
        URL url = new URL("https://api.mojang.com/user/profiles/" + serializeUUID(id) + "/names");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int status = connection.getResponseCode();
        if (status != 200) throw new IOException("Invalid response code");

        StringBuffer content = new StringBuffer();
        Scanner scanner = new Scanner(new InputStreamReader(connection.getInputStream()));
        while (scanner.hasNextLine()) content.append(scanner.nextLine());

        scanner.close();
        connection.disconnect();

        JsonArray contentJSON = new Gson().fromJson(content.toString(), JsonArray.class);
        return contentJSON.get(0).getAsJsonObject().get("name").getAsString();
    }

    private String serializeUUID(UUID id) {
        return id.toString().replace("-", "");
    }
}
