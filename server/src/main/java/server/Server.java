package server;

import commands.*;
import commands.base.Command;
import commands.base.CommandRequestContainer;
import commands.base.DatabaseManager;
import manager.CollectionManager;
import manager.CollectionManagerImplServer;
import manager.FileManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static manager.ConsoleManager.print;

public class Server {

    private static final ExecutorService readerPool = Executors.newCachedThreadPool();
    private static final ExecutorService handlerPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseManager.getConnection().close();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started");
        String file_name = "FILE_NAME";
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        if (System.getenv(file_name) == null) {
            print("No file name specified.\n");
        }
        String fileName = System.getenv(file_name);
        FileManager fm = new FileManager(fileName);
        CollectionManager manager = new CollectionManagerImplServer(fm);
        HashMap<String, Command> commandHashMap = new HashMap<>();


        commandHashMap.put("add", new AddCommand(manager));
        commandHashMap.put("info", new Info(manager));
        commandHashMap.put("show", new Show(manager));
        commandHashMap.put("exit", new Exit(manager));
        commandHashMap.put("clear", new Clear(manager));
        commandHashMap.put("filter_starts_with_name", new FilterStartsWithName(manager));
        commandHashMap.put("print_field_ascending_students_count", new PrintFieldAscendingStudentsCount(manager));
        commandHashMap.put("remove_last", new RemoveLast(manager));
//        commandHashMap.put("remove_at", new RemoveAt(manager));
        commandHashMap.put("remove_by_id", new RemoveByIdCommand(manager));
        commandHashMap.put("remove_greater", new RemoveGreater(manager));
        commandHashMap.put("sum_of_expelled_students", new SumOfExpelledStudents(manager));
        commandHashMap.put("update", new UpdateIdCommand(manager));
        commandHashMap.put("login", new LoginCommand(manager));
        commandHashMap.put("register", new RegisterCommand(manager));

        InetAddress hostIP = InetAddress.getLocalHost();
        InetSocketAddress address = new InetSocketAddress(hostIP, 4324);
        DatagramChannel datagramChannel = DatagramChannel.open();
        DatagramSocket datagramSocket = datagramChannel.socket();
        datagramSocket.bind(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                DatabaseManager.close();
                System.out.println("Server stopped.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
        // exit command thread
        Thread exitThread = new Thread(() -> {
            while (true) {
                try {
                    String line = scanner.nextLine();
                    if (line.equals("exit")) {
                        System.exit(0);
                    } else {
                        System.err.println("Wrong command. You can use only 'exit' command to stop server.");
                    }
                } catch (NoSuchElementException e) {
                    System.err.println("What are you doing? Stop that! Don't Ctrl+D my program, you are not good..." + "\n" + "Restart the program and be polite.");
                }
            }
        });
        exitThread.start();

        for (int i = 0; i < 3; i++) {
            readerPool.execute(() -> {
                ByteBuffer buffer = ByteBuffer.allocate(4096 * 4);
                while (true) {
                    SocketAddress from = null;
                    try {
                        from = datagramChannel.receive(buffer);
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getMimeDecoder().decode(bytes));
                        ObjectInputStream objectInputStream = new ObjectInputStream(bais);
                        CommandRequestContainer inputContainer = (CommandRequestContainer) objectInputStream.readObject();

                        handlerPool.execute(new RequestHandler(inputContainer, datagramChannel, commandHashMap, from));

                        buffer.clear();
                    } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
