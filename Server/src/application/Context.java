package application;

import collection.HumanList;
import network.AddressedRequest;
import network.AddressedResponse;
import network.HandlerClient;
import commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.BindException;
import java.sql.SQLException;
import java.util.concurrent.*;

public class Context {
    public final int quantityThread = 1;
    public final int historySize = 7;

    public HandlerClient handlerClient;
    public HandlerCommands handlerCommands;
    public HumanList humanList;
    public HandlerDatabase handlerDatabase;

    public Logger logger;

    public BlockingQueue<AddressedRequest> queueAddressedRequests;
    public BlockingQueue<AddressedResponse> queueAddressedResponse;

    public Context() {
        handlerClient = new HandlerClient();
        handlerCommands = new HandlerCommands(this, historySize);
        handlerDatabase = new HandlerDatabase(this);

        logger = LoggerFactory.getLogger(Context.class);

        queueAddressedRequests = new LinkedBlockingQueue<>();
        queueAddressedResponse = new LinkedBlockingQueue<>();

        handlerCommands.setCommand(new CommandAdd())
                       .setCommand(new CommandClear())
                       .setCommand(new CommandHelp())
                       .setCommand(new CommandHistory())
                       .setCommand(new CommandInfo())
                       .setCommand(new CommandLogin())
                       .setCommand(new CommandPrintDescending())
                       .setCommand(new CommandPrintFieldDescendingCar())
                       .setCommand(new CommandPrintUniqueCar())
                       .setCommand(new CommandRegistration())
                       .setCommand(new CommandRemoveById())
                       .setCommand(new CommandRemoveGreater())
                       .setCommand(new CommandRemoveHead())
                       .setCommand(new CommandUpdateById());
    }

    public void initialization(String[] args) {
        if(args.length != 1) {
            logger.error("Некорректный ввод порта!");
            System.exit(1);
        }

        int port = 0;
        try {
           port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            logger.error("Некорректный ввод порта!");
            System.exit(1);
        }
        try {
            handlerDatabase.initialization("jdbc:postgresql://localhost:5432/postgres", "postgres", "1111"); //handlerDatabase.initialization("jdbc:postgresql://localhost:5432/lab6_prob");
        } catch (Exception e) {
            logger.error("Не удалось подключиться к базе данных!");
            System.exit(1);
        }

        try {
            humanList = handlerDatabase.getHumanList();
        } catch (SQLException e) {
            logger.error("Не удалось прочесть коллекцию из базы данных!");
            System.exit(0);
        }
        logger.info("Коллекция заполнена.");

        try {
            handlerClient.bind(port);
            logger.info("Сервер инициализирован.");
        }
        catch (BindException e) {
            logger.error("Этот порт занят! Выберите другой порт и перезапустите программу.");
            System.exit(0);
        }
        catch (IOException e) {
            logger.error("Запустить сервер не удалось: " + e.getMessage());
            System.exit(0);
        }
    }

    private void execution() {
        logger.info("Работа сервера запущенна.");

        new Thread(new Receiver(this)).start();

        ThreadPoolExecutor executorRequests = (ThreadPoolExecutor) Executors.newFixedThreadPool(quantityThread);
        while (true) {

            if(!queueAddressedRequests.isEmpty()) {
                executorRequests.submit(new HandlerRequest(this));
            }

            if(!queueAddressedResponse.isEmpty()) {
                new Thread(new Transmitter(this)).start();
            }
        }
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.info("Завершение программы.");
            }
        });
        execution();
    }
}
