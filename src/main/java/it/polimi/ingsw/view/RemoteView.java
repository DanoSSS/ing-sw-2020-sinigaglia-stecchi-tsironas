package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.ReturnMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoteView extends View{
    private ClientConnection clientConnection;
    private int numberRW;

    private class MessageReceiver implements Observer<Object> {
        private Player player;

        private MessageReceiver(Player player) {
            this.player=player;
        }

        /**
         * update that send message from client to server controller
         * @param m
         */
        @Override
        public void update(Object m) {
            Action a =((Message) m).getAction();switch (a) {
                case INIT_WORKERS:
                case SELECT_ACTIVE_WORKER:
                case SELECT_COORDINATE_MOVE:
                case MOVE_AND_COORDINATE_BUILD:
                case ARTEMIS_FIRST_MOVE:
                case BUILD_ATLAS:
                case BUILD_EPHAESTUS:
                case FIRST_BUILD_DEMETER:
                case PROMETHEUS_CHOOSE:
                case END_GAME:
                case ARES_POWER:
                    handleMessage(m, player.getIdPlayer());  //CREARE getId() in Player, inizializzare questo valore id dei players
                    break;
                case NOT_YOUR_TURN:
                    clientConnection.asyncSend(new ReturnMessage(2,"It's not your turn!wait"));
                    break;
                case WIN:
                case LOSE:
                    if(((Message) m).getSentence().equals("q")){
                        clientConnection.closeConnection();
                    }else{
                        clientConnection.asyncSend(new ReturnMessage(4,"press q to exit"));
                    }
            }
        }
    }

    /**
     * constructor
     * @param player
     * @param c
     * @param i
     */
    public RemoteView(Player player, ClientConnection c, int i) {
        super(player);
        this.clientConnection = c;
        this.numberRW = i;
        c.addObserver(new MessageReceiver(player));
    }

    /**
     * update that received message from controller
     * @param message
     */
    @Override
    public void update(Object message) {                //WORKERSET non stampa al secondo client in 2 giocatori da provare in 3
        Action a = ((ReturnMessage) message).getAction();
        Map<Worker,Coordinates> m;

        switch (a) {
            case STRING:
                break;
            case WORKER_SET:
                m=((ReturnMessage)message).getWorkerPosition();
                List<Worker> workers = new ArrayList<>(m.keySet());
                String[] messageSettingWorkersPositions = workers.size()==4 ? new String[4] : new String[6];
                String[] players = ((ReturnMessage)message).getNicknames();
                Integer[] idPlayers = ((ReturnMessage)message).getIdPlayers();
                int currentPlayer= ((ReturnMessage)message).getnCurrentPlayer();
                String nickname= players[numberRW-1];
                int idPlayer= idPlayers[numberRW-1];
                int NPlayer = workers.size()/2;
                ArrayList<Player> playersList= ((ReturnMessage)message).getPlayersList();
                ClientController clientController= new ClientController(nickname,idPlayer,NPlayer,idPlayers,players,currentPlayer,playersList);
                for(int i=0;i<workers.size();i++) {
                    messageSettingWorkersPositions[i]= workers.get(i).getIdWorker() + " set in cell " + m.get(workers.get(i)).getX() + "," + m.get(workers.get(i)).getY();
                    int x=(m.get(workers.get(i)).getX()*2)+1;
                    int y=m.get(workers.get(i)).getY();
                }
                clientConnection.asyncSend(new ReturnMessage(3,messageSettingWorkersPositions,clientController,m));
                break;
            case SELECT_COORDINATE_MOVE:
            case MOVE_AND_COORDINATE_BUILD:
            case BUILD_END_TURN:
            case ARTEMIS_FIRST_MOVE:
            case ARTEMIS_SECOND_MOVE:
            case BUILD_ATLAS:
            case BUILD_EPHAESTUS:
            case END_TURN:
            case FIRST_BUILD_DEMETER:
            case PROMETHEUS_CHOOSE:
            case FIRST_BUILD_PROMETHEUS:
            case LOSE:
            case LOSE3P:
            case WIN:
            case END_GAME:
            case ARES_POWER:
            case ARES_END_TURN:
                clientConnection.asyncSend((ReturnMessage)message);
                break;

        }
    }

}
