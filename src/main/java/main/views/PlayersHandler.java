package main.views;

import java.util.ArrayList;

public class PlayersHandler {
    private ArrayList<Player> players;
    private static PlayersHandler playersHandler;

    private PlayersHandler() {
        this.players = new ArrayList<>();
    }

    public static PlayersHandler getPlayersHandler(){
        if(playersHandler == null)
            playersHandler = new PlayersHandler();
        return playersHandler;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void clearPlayers(){
        players.clear();
    }

    public Player getPlayer(int i){
        return players.get(i);
    }
}