package ServerPart;

import src.ServerPart.AuthService;
import src.ServerPart.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server
{
    private Vector<src.ServerPart.ClientHandler> clients;  // список клиентов.

    public Server()
    {
        ServerSocket server = null;
        Socket socket = null;

        try
        {
            src.ServerPart.AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен. Ожидаем подключения... .");
            clients = new Vector<>();

            while (true)
            {
                socket = server.accept();
                System.out.println("Клиент подключился.");
                new src.ServerPart.ClientHandler(this, socket);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                server.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    // добавление клиента в список.
    public void subscribe(src.ServerPart.ClientHandler client)
    {
        clients.add(client);
        System.out.println("Клиент " + client.getNick() + " авторизовался.");
    }

    // удаление клиента из списка.
    public void unsubscribe(src.ServerPart.ClientHandler client)
    {
        System.out.println("Клиент " + client.getNick() + " устранился.");
        clients.remove(client);
    }


    // метод, проверяющий наличие пользователя
    public boolean isNickAlready(String nick)
    {
        for (ClientHandler o: clients)
        {
            if(o.getNick().equals(nick))
            {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args)
    {
//        Server serv = new Server();
        new src.ServerPart.Server();
    }
}
