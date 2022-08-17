package LogParser;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("c:/Users/1/Desktop/example.log"));
        System.out.println("");
        System.out.println(logParser.getNumberOfUniqueIPs(null, null));

        System.out.println("возвращать множество, содержащее все не повторяющиеся IP. Тип в котором будем хранить IP будет String.");
        System.out.println(logParser.getUniqueIPs(null, null));
        System.out.println();

        System.out.println("должен возвращать IP, с которых работал переданный пользователь.");
        System.out.println(logParser.getIPsForUser("Amigo",null, null));
        System.out.println();

        System.out.println("должен возвращать IP, с которых было произведено переданное событие.");
        System.out.println(logParser.getIPsForEvent(Event.LOGIN,null, null));
        System.out.println();

        System.out.println("должен возвращать IP, события с которых закончилось переданным статусом.");
        System.out.println(logParser.getIPsForStatus(Status.OK,null, null));
        System.out.println();

        System.out.println("должен возвращать всех пользователей.");
        System.out.println(logParser.getAllUsers());
        System.out.println();

        System.out.println("должен возвращать количество уникальных пользователей.");
        System.out.println(logParser.getNumberOfUsers(null, null));
        System.out.println();

        System.out.println("должен возвращать количество уникальных событий от определенного пользователя.");
        System.out.println(logParser.getNumberOfUserEvents("Amigo",null, null));
        System.out.println();

        System.out.println("должен возвращать пользователей с определенным IP.");
        System.out.println(logParser.getUsersForIP("127.0.0.1", null, null));
        System.out.println();

        System.out.println("должен возвращать пользователей, которые делали логин.");
        System.out.println(logParser.getLoggedUsers(null, null));
        System.out.println();

        System.out.println("должен возвращать пользователей, которые скачали плагин.");
        System.out.println(logParser.getDownloadedPluginUsers(null, null));
        System.out.println();

        System.out.println("должен возвращать пользователей, которые отправили сообщение.");
        System.out.println(logParser.getWroteMessageUsers(null, null));
        System.out.println();

        System.out.println("должен возвращать множество содержащее пользователей, которые решали любую задачу за выбранный период.");
        System.out.println(logParser.getSolvedTaskUsers(null, null));
        System.out.println();

        System.out.println("должен возвращать множество содержащее пользователей, которые решали");
        System.out.println(logParser.getSolvedTaskUsers(null, null, 48));
        System.out.println();

        System.out.println("должен возвращать пользователей, которые решили любую задачу.");
        System.out.println(logParser.getDoneTaskUsers(null, null));
        System.out.println();

        System.out.println("должен возвращать пользователей, которые решили задачу с номером task.");
        System.out.println(logParser.getDoneTaskUsers(null, null, 18));
        System.out.println();

        System.out.println("должен возвращать множество дат, когда переданный\n" +
                "пользователь произвел переданное событие за выбранный период.");
        System.out.println(logParser.getDatesForUserAndEvent("Amigo", Event.LOGIN, null, null));
        System.out.println();

        System.out.println("должен возвращать множество дат,\n" +
                "когда любое событие не выполнилось за выбранный период.");
        System.out.println(logParser.getDatesWhenSomethingFailed(null, null));
        System.out.println();

        System.out.println("должен возвращать множество дат, когда любое\n" +
                "событие закончилось ошибкой за выбранный период.");
        System.out.println(logParser.getDatesWhenErrorHappened(null, null));
        System.out.println();

        System.out.println("должен возвращать дату, когда переданный пользователь впервые\n" +
                "залогинился за выбранный период. Если такой даты в логах нет - null.");
        System.out.println(logParser.getDateWhenUserLoggedFirstTime("Amigo", null, null));
        System.out.println();

        System.out.println("должен возвращать дату, когда переданный пользователь впервые попытался решить задачу\n" +
                "с номером task за выбранный период. Если такой даты в логах нет - null.");
        System.out.println(logParser.getDateWhenUserSolvedTask("Amigo",18, null, null));
        System.out.println();

        System.out.println("должен возвращать дату, когда переданный пользователь впервые решил задачу с\n" +
                "номером task за выбранный период. Если такой даты в логах нет - null.");
        System.out.println(logParser.getDateWhenUserDoneTask("Amigo",18, null, null));
        System.out.println();

        System.out.println("должен возвращать множество дат, когда переданный пользователь написал сообщение за выбранный период.");
        System.out.println(logParser.getDatesWhenUserWroteMessage("Eduard Petrovich Morozko", null, null));
        System.out.println();

        System.out.println("должен возвращать множество дат, когда переданный пользователь скачал плагин за выбранный период.");
        System.out.println(logParser.getDatesWhenUserDownloadedPlugin("Eduard Petrovich Morozko", null, null));
        System.out.println();

        System.out.println("должен возвращать количество уникальных событий за выбранный период.");
        System.out.println(logParser.getNumberOfAllEvents(null, null));
        System.out.println();

        System.out.println("должен возвращать множество уникальных событий за выбранный период.");
        System.out.println(logParser.getAllEvents(null, null));
        System.out.println();

        System.out.println("должен возвращать множество уникальных событий, которые происходили " +
                "с переданного IP адреса за выбранный период.");
        System.out.println(logParser.getEventsForIP("127.0.0.1",null, null));
        System.out.println();

        System.out.println("должен возвращать множество уникальных событий, которые произвел переданный " +
                "пользователь за выбранный период.");
        System.out.println(logParser.getEventsForUser("Amigo",null, null));
        System.out.println();

        System.out.println("должен возвращать множество уникальных событий, у которых статус " +
                "выполнения FAILED за выбранный период.");
        System.out.println(logParser.getFailedEvents(null, null));
        System.out.println();

        System.out.println("должен возвращать множество уникальных событий, у которых статус выполнения ERROR за выбранный период.");
        System.out.println(logParser.getErrorEvents(null, null));
        System.out.println();

        System.out.println("должен возвращать множество уникальных событий, у которых статус выполнения ERROR за выбранный период.");
        System.out.println(logParser.getErrorEvents(null, null));
        System.out.println();

        System.out.println("должен возвращать количество попыток решить задачу с номером task за выбранный период.");
        System.out.println(logParser.getNumberOfAttemptToSolveTask(18,null, null));
        System.out.println();

        System.out.println("должен возвращать количество успешных решений задачи с номером task за выбранный период.");
        System.out.println(logParser.getNumberOfSuccessfulAttemptToSolveTask(15,null, null));
        System.out.println();

        System.out.println("должен возвращать мапу (номер_задачи : количество_попыток_решить_ее) за выбранный период.");
        System.out.println(logParser.getAllSolvedTasksAndTheirNumber(null, null));
        System.out.println();

        System.out.println("должен возвращать мапу (номер_задачи : сколько_раз_ее_решили) за выбранный период.");
        System.out.println(logParser.getAllDoneTasksAndTheirNumber(null, null));
        System.out.println();


        System.out.println("должен возвращать значение в зависимости от запроса");
        System.out.println(logParser.execute("get user"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"" + "get ip for user = \"[any_user]\"\" " +
                "должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].");
        System.out.println(logParser.execute("get ip for user = Amigo"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get ip for date = \"[any_date]\"\" " +
                "должен возвращать множество уникальных IP адресов, события с которых произведены в указанное время [any_date].");
        System.out.println(logParser.execute("get ip for date = 13.09.2013 5:04:50"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get ip for event = \"[any_event]\"\" " +
                "должен возвращать множество уникальных IP адресов, у которых событие равно [any_event].");
        System.out.println(logParser.execute("get ip for event = LOGIN"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get ip for status = \"[any_status]\"\"" +
                "должен возвращать множество уникальных IP адресов, события с которых закончились со статусом [any_status].");
        System.out.println(logParser.execute("get ip for status = FAILED"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get user for ip = \"[any_ip]\"\" " +
                "должен возвращать множество уникальных пользователей, которые работали с IP адреса [any_ip].");
        System.out.println(logParser.execute("get user for ip = 127.0.0.1"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get user for date = \"[any_date]\"\" " +
                "должен возвращать множество уникальных пользователей, которые произвели любое действие в указанное время [any_date].");
        System.out.println(logParser.execute("get user for date = 14.11.2015 07:08:01"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get user for event = \"[any_event]\"\" " +
                "должен возвращать множество уникальных пользователей, у которых событие равно [any_event].");
        System.out.println(logParser.execute("get user for event = SOLVE_TASK"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get user for status = \"[any_status]\"\" " +
                "должен возвращать множество уникальных пользователей, у которых статус равен [any_status].");
        System.out.println(logParser.execute("get user for status = ERROR"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get date for ip = \"[any_ip]\"\" " +
                "должен возвращать множество уникальных дат, за которые с IP адреса [any_ip] произведено любое действие.");
        System.out.println(logParser.execute("get date for ip = 127.0.0.1"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get date for user = \"[any_user]\"\" " +
                "должен возвращать множество уникальных дат, за которые пользователь [any_user] произвел любое действие.");
        System.out.println(logParser.execute("get date for user = Amigo"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get date for event = \"[any_event]\"\"" +
                "должен возвращать множество уникальных дат, за которые произошло событие равно [any_event].");
        System.out.println(logParser.execute("get date for event = SOLVE_TASK"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get date for status = \"[any_status]\"\" " +
                "должен возвращать множество уникальных дат, за которые произошло любое событие со статусом [any_status].");
        System.out.println(logParser.execute("get date for status = FAILED"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get event for ip = \"[any_ip]\"\" " +
                "должен возвращать множество уникальных событий, которые произошли с IP адреса [any_ip].");
        System.out.println(logParser.execute("get event for ip = 127.0.0.1"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get event for user = \"[any_user]\"\" " +
                "должен возвращать множество уникальных событий, которые произвел пользователь [any_user].");
        System.out.println(logParser.execute("get event for user = Amigo"));
        System.out.println();

        System.out.println(" Вызов метода execute с параметром \"get event for date = \"[any_date]\"\" " +
                "должен возвращать множество уникальных событий, которые произошли во время [any_date].");
        System.out.println(logParser.execute("get event for date = 19.03.2016 00:00:00"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get event for status = \"[any_status]\"\" " +
                "должен возвращать множество уникальных событий, которые завершены со статусом [any_status].");
        System.out.println(logParser.execute("get event for status = OK"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get status for ip = \"[any_ip]\"\" " +
                "должен возвращать множество уникальных статусов, которые произошли с IP адреса [any_ip].");
        System.out.println(logParser.execute("get status for ip = 127.0.0.1"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get status for user = \"[any_user]\"\" " +
                "должен возвращать множество уникальных статусов, которые произвел пользователь [any_user].");
        System.out.println(logParser.execute("get status for user = Amigo"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get status for date = \"[any_date]\"\" " +
                "должен возвращать множество уникальных статусов, которые произошли во время [any_date].");
        System.out.println(logParser.execute("get status for date = 19.03.2016 00:00:00"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get status for event = \"[any_event]\"\" " +
                "должен возвращать множество уникальных статусов, у которых событие равно [any_event].");
        System.out.println(logParser.execute("get status for event = SOLVE_TASK"));
        System.out.println();






        System.out.println("_____________________________________________________________________________________________________________");




        System.out.println("Вызов метода execute с параметром \"get ip for user = \"[any_user]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get ip for user = Eduard Petrovich Morozko and date between 11.12.2013 0:00:00 and 03.01.2014 23:59:59"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get ip for date = \"[any_date]\" and date between \"[after]\" and \"[before]\"\"" +
                "должен возвращать множество уникальных IP адресов, события с которых произведены в указанное время [any_date] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get ip for date = 13.09.2013 5:04:50 and date between 13.09.1900 5:04:50 and 13.09.3028 5:04:50"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get ip for event = \"[any_event]\" and date between \"[after]\" and \"[before]\"\"" +
                " должен возвращать множество уникальных IP адресов, у которых событие равно [any_event] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get ip for event = LOGIN and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get ip for status = \"[any_status]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных IP адресов, события с которых закончились со статусом [any_status] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get ip for status = FAILED and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get user for ip = \"[any_ip]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных пользователей, которые работали с IP адреса [any_ip] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get user for ip = 127.0.0.1 and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get user for date = \"[any_date]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных пользователей, которые произвели любое действие в указанное время [any_date] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get user for date = 14.11.2015 07:08:01 and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get user for event = \"[any_event]\" and date between \"[after]\" and \"[before]\"\"" +
                " должен возвращать множество уникальных пользователей, у которых событие равно [any_event] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get user for event = SOLVE_TASK and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get user for status = \"[any_status]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных пользователей, у которых статус равен [any_status] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get user for status = ERROR and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get date for ip = \"[any_ip]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных дат, за которые с IP адреса [any_ip] произведено любое действие в период между датами [after] и [before].");
        System.out.println(logParser.execute("get date for ip = 127.0.0.1 and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get date for user = \"[any_user]\" and date between \"[after]\" and \"[before]\"\"" +
                " должен возвращать множество уникальных дат, за которые пользователь [any_user] произвел любое действие в период между датами [after] и [before].");
        System.out.println(logParser.execute("get date for user = Amigo and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get date for event = \"[any_event]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных дат, за которые произошло событие равно [any_event] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get date for event = SOLVE_TASK and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get date for status = \"[any_status]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных дат, за которые произошло любое событие со статусом [any_status] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get date for status = FAILED and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get event for ip = \"[any_ip]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных событий, которые произошли с IP адреса [any_ip] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get event for ip = 127.0.0.1 and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get event for user = \"[any_user]\" and date between \"[after]\" and \"[before]\"\"" +
                " должен возвращать множество уникальных событий, которые произвел пользователь [any_user] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get event for user = Amigo and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get event for date = \"[any_date]\" and date between \"[after]\" and \"[before]\"\"" +
                " должен возвращать множество уникальных событий, которые произошли во время [any_date] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get event for date = 19.03.2016 00:00:00 and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get event for status = \"[any_status]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных событий, которые завершены со статусом [any_status] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get event for status = OK and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get status for ip = \"[any_ip]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных статусов, которые произошли с IP адреса [any_ip] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get status for ip = 127.0.0.1 and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get status for user = \"[any_user]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных статусов, которые произвел пользователь [any_user] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get status for user = Amigo and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get status for date = \"[any_date]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных статусов, которые произошли во время [any_date] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get status for date = 19.03.2016 00:00:00 and date between null and null"));
        System.out.println();

        System.out.println("Вызов метода execute с параметром \"get status for event = \"[any_event]\" and date between \"[after]\" and \"[before]\"\" " +
                "должен возвращать множество уникальных статусов, у которых событие равно [any_event] в период между датами [after] и [before].");
        System.out.println(logParser.execute("get status for event = SOLVE_TASK and date between null and null"));
        System.out.println();
    }
}