package client;

import commands.*;
import commands.base.*;
import exception.CommandException;
import manager.CollectionManager;
import manager.CollectionManagerImplClient;
import manager.ConsoleManager;
import manager.FileManager;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

import static manager.ConsoleManager.print;

public class Client {
    public static void main(String[] args) {
        boolean success = false;

        User user = null;

        while (!success) {
            try {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Client-Server application for collection managing");
                System.out.println("Enter 'help' to see available commands or 'exit' to leave the program");


                ConsoleManager consoleManager = new ConsoleManager(scanner);
                CollectionManager manager = new CollectionManagerImplClient(consoleManager);
                HashMap<String, Command> commandHashMap = new HashMap<>();
                String file_name = "FILE_NAME";
                System.setOut(new PrintStream(System.out, true, "UTF-8"));
                String fileName = System.getenv(file_name);
                new FileManager(fileName);
                commandHashMap.put("add", new AddCommand(manager));
                commandHashMap.put("info", new Info(manager));
                commandHashMap.put("show", new Show(manager));
                commandHashMap.put("exit", new Exit(manager));
                commandHashMap.put("clear", new Clear(manager));
                commandHashMap.put("filter_starts_with_name", new FilterStartsWithName(manager));
                commandHashMap.put("print_field_ascending_students_count", new PrintFieldAscendingStudentsCount(manager));
                commandHashMap.put("remove_last", new RemoveLast(manager));
//                commandHashMap.put("remove_at", new RemoveAt(manager));
                commandHashMap.put("remove_by_id", new RemoveByIdCommand(manager));
                commandHashMap.put("remove_greater", new RemoveGreater(manager));
                commandHashMap.put("sum_of_expelled_students", new SumOfExpelledStudents(manager));
                commandHashMap.put("update", new UpdateIdCommand(manager));
                commandHashMap.put("login", new LoginCommand(manager));
                commandHashMap.put("register", new RegisterCommand(manager));

                InetAddress hostIP = InetAddress.getLocalHost();
                InetSocketAddress myAddress = new InetSocketAddress(hostIP, 4324);
                DatagramChannel datagramChannel = DatagramChannel.open();
                datagramChannel.bind(null);
                datagramChannel.connect(myAddress);

                ByteBuffer buffer = ByteBuffer.allocate(4096*4);

                String line;
                String[] input;
                while (true) {
                    line = scanner.nextLine();
                    input = line.split(" ");

                    try {
                        if (input[0].equals("execute_script")) {
                            if (input.length == 1) {
                                System.out.println("No script name specified");
                                continue;
                            }
                            Scanner scriptScanner = new Scanner(new FileReader(input[1]));
                            consoleManager.changeScanner(scriptScanner);


                            while (scriptScanner.hasNextLine()) {
                                String[] scriptInput = scriptScanner.nextLine().split(" ");
                                if (commandHashMap.containsKey(scriptInput[0])) {
                                    CommandResult result = commandHashMap.get(scriptInput[0]).execute(Arrays.copyOfRange(scriptInput, 1, scriptInput.length), user);
                                    CommandRequestContainer commandRequestContainer = new CommandRequestContainer(scriptInput[0], Arrays.copyOfRange(scriptInput, 1, scriptInput.length), user, result.getInput());

                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
                                    objectOutputStream.writeObject(commandRequestContainer);

                                    if (!result.getResult().isEmpty())
                                        System.out.println(result.getResult());
                                    buffer.put(Base64.getMimeEncoder().encode(baos.toByteArray()));
                                    buffer.flip();
                                    datagramChannel.send(buffer, myAddress);
                                    buffer.clear();
                                    buffer.rewind();
                                    int bytesRead = datagramChannel.read(buffer);
                                    if (bytesRead != -1) {
                                        buffer.rewind();
                                        byte[] b = new byte[bytesRead];
                                        for (int i = 0; i < bytesRead; i++) {
                                            b[i] = buffer.get();
                                        }

                                        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getMimeDecoder().decode(b));
                                        ObjectInputStream objectInputStream = new ObjectInputStream(bais);
                                        CommandResponseContainer inputContainer = (CommandResponseContainer) objectInputStream.readObject();
                                        System.out.println(inputContainer.getResult());
                                    }

                                    buffer.clear();
                                } else {
                                    System.err.println("Command " + Arrays.toString(input) + " not found");
                                }
                            }
                            System.out.println("Script execution finished");
                            consoleManager.changeScanner(scanner);

                        } else if (input[0].equals("help")) {
                            print("help : prints available commands.");
                            print("execute_script file_name : reads and executes script from the specified file, " +
                                    "the script contains commands in the same form in which they are entered " +
                                    "by the user in the console.");
                            for (String key : commandHashMap.keySet()) {
                                print(key + " : " + commandHashMap.get(key).getDescription());
                            }
                        } else if (commandHashMap.containsKey(input[0])) {
                            Command c = commandHashMap.get(input[0]);
                            CommandRequestContainer commandRequestContainer;
                            CommandResult result;
                            if(c.isAuthorizationNeeded()) {
                                if(user != null) {
                                    result = c.execute(Arrays.copyOfRange(input, 1, input.length), user);
                                } else {
                                    throw new CommandException("Authorization required!");
                                }
                            } else {
                                result = c.execute(Arrays.copyOfRange(input, 1, input.length), user);
                            }

                            commandRequestContainer = new CommandRequestContainer(input[0], Arrays.copyOfRange(input, 1, input.length), user, result.getInput());
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
                            objectOutputStream.writeObject(commandRequestContainer);

                            if (!result.getResult().isEmpty())
                                System.out.println(result.getResult());
                            buffer.put(Base64.getMimeEncoder().encode(baos.toByteArray()));
                            buffer.flip();
                            datagramChannel.send(buffer, myAddress);
                            buffer.clear();
                            buffer.rewind();
                            int bytesRead = datagramChannel.read(buffer);
                            if (bytesRead != -1) {
                                buffer.rewind();
                                byte[] b = new byte[bytesRead];
                                for (int i = 0; i < bytesRead; i++) {
                                    b[i] = buffer.get();
                                }
                                ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getMimeDecoder().decode(b));
                                ObjectInputStream objectInputStream = new ObjectInputStream(bais);
                                CommandResponseContainer inputContainer = (CommandResponseContainer) objectInputStream.readObject();
                                if(c.getName().equals("login")) {
                                    if(!inputContainer.getResult().isEmpty()) {
                                        String[] data = inputContainer.getResult().split(":");
                                        user = new User(data[0], data[1]);
                                        System.out.println("==>> Login successfully! <<==");
                                    } else {
                                        System.out.println("==>> Login failed. Try again! <<==");
                                    }
                                } else {
                                    System.out.println(inputContainer.getResult());
                                }
                            }

                            buffer.clear();
                        } else {
                            System.err.println("Command '" + input[0] + "' not found");
                        }
                    } catch (CommandException | ClassNotFoundException e) {
                        System.err.println(e.getMessage());
                    } catch (PortUnreachableException e) {
                        System.err.println("Server is unavailable");
                    } catch (FileNotFoundException e1) {
                        System.err.println("File not found");
                    } catch (IndexOutOfBoundsException e2) {
                        System.err.println("Incorrect input. Try again");
                    }
                }

            } catch (NoSuchElementException e) {
                System.err.println("What are you doing? Stop that! Don't Ctrl+D my program, you are not good..." + "\n" +
                        "Restart the program and be polite.");
                break;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
