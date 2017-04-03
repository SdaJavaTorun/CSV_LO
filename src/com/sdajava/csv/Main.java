package com.sdajava.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Pattern pattern = Pattern.compile(";");
        String filename = "Salaries.csv";

        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            /*
            List<Player> players = in
                .lines()
                .skip(1)
                .map(line -> {
                    String[] field = pattern.split(line);
                    return new Player(Integer.parseInt(field[0]),
                        field[1],
                        field[2],
                        field[3],
                        Integer.parseInt(field[4]));
                }).collect(Collectors.toList());
            //players.forEach(e -> System.out.println(e.year));
*/

            Map<List<String>, List<Player>> grouped = in
                .lines()
                .skip(1)
                .map(line -> {
                    String[] field = pattern.split(line);
                    return new Player(Integer.parseInt(field[0]),
                        field[1],
                        field[2],
                        field[3],
                        Integer.parseInt(field[4]));
                }).collect(Collectors.groupingBy(x -> {
                    return new ArrayList<String>(
                        Arrays.asList(Integer.toString(x.getYear()), x.getTeamID(), x.getTeamID())
                    );
                }));

            grouped.entrySet().stream().forEach(x -> {

                System.out.println(x.getKey());
                x.getValue().stream()
                    .forEach(p -> System.out.printf(" ( %2s %-10s %-10d %-10d) %n",
                        p.getLgID(), p.getPlayerID(), p.getSalary(), p.getYear()));


            });




        }catch (IOException err){
            System.out.println("Error: " + err.getMessage());
        }
    }
}
