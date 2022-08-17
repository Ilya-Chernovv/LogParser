package LogParser;


import LogParser.query.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public LogParser(Path logDir ){
        this.logDir = logDir;
    }


    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        /**должен возвращать количество уникальных IP адресов за выбранный период. Здесь и далее, если в методе
         есть параметры Date after и Date before, то нужно возвратить данные касающиеся только данного периода
         (включая даты after и before).
         Если параметр after равен null, то нужно обработать все записи, у которых дата меньше или равна before.
         Если параметр before равен null, то нужно обработать все записи, у которых дата больше или равна after.
         Если и after, и before равны null, то нужно обработать абсолютно все записи (без фильтрации по дате).
         */
        Set<String> set = new HashSet<>();
        String line;
        ArrayList<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];


                //преобразование строки в датую

                Date d = formatter.parse(dateLine);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) set.add(ss[0]);
            }

        } catch (Exception e) {

        }
        return set.size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        //возвращать множество, содержащее все не повторяющиеся IP. Тип в котором будем хранить IP будет String.
        Set<String> set = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) set.add(ss[0]);
            }

        } catch (Exception e) {

        }
        return set;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        //должен возвращать IP, с которых работал переданный пользователь.
        Set<String> set = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (userName.equals(user)) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) set.add(ss[0]);
                }
            }

        } catch (Exception e) {

        }
        return set;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        //должен возвращать IP, с которых было произведено переданное событие.
        Set<String> set = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLine = ss[3];
                String eventEnum = null;

                switch (event){
                    case LOGIN: eventEnum = "LOGIN";
                        break;
                    case DONE_TASK: eventEnum = "DONE_TASK";
                        break;
                    case SOLVE_TASK: eventEnum = "SOLVE_TASK";
                        break;
                    case WRITE_MESSAGE: eventEnum = "WRITE_MESSAGE";
                        break;
                    case DOWNLOAD_PLUGIN: eventEnum = "DOWNLOAD_PLUGIN";
                }

                //преобразование строки в датую

                Date d = formatter.parse(dateLine);

                if (eventLine.contains(eventEnum)) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) set.add(ss[0]);
                }
            }

        } catch (Exception e) {

        }
        return set;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        //должен возвращать IP, события с которых закончилось переданным статусом.
        Set<String> set = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();
        String[] date = null;
        ArrayList<String> resultDate = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String statusLine = ss[4];
                String statusEnum = null;

                switch (status){
                    case OK: statusEnum = "OK";
                        break;
                    case FAILED: statusEnum = "FAILED";
                        break;
                    case ERROR: statusEnum = "ERROR";
                        break;
                }

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (statusLine.contains(statusEnum)) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) set.add(ss[0]);
                }

            }

        } catch (Exception e) {

        }
        return set;
    }

    @Override
    public Set<String> getAllUsers() {
        //должен возвращать всех пользователей.
        Set<String> setUserName = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String userName = ss[1];
                setUserName.add(userName);
            }

        } catch (Exception e) {}

        return setUserName;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        //должен возвращать количество уникальных пользователей.
        Set<String> setUserName = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) setUserName.add(userName);
            }


        } catch (Exception e) {

        }
        return setUserName.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        // должен возвращать количество уникальных событий от определенного пользователя.
        Set<String> setEvent = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];


                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (userName.equals(user)) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) setEvent.add(ss[3]);
                }
            }

        } catch (Exception e) {

        }
        return setEvent.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        //должен возвращать пользователей с определенным IP.
        //Несколько пользователей могут использовать один и тот же IP.
        Set<String> setUserName = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];
                String ipLine = ss[0];

                //преобразование строки в датую

                Date d = formatter.parse(dateLine);

                if (ip.equals(ipLine)) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) setUserName.add(userName);
                }
            }

        } catch (Exception e) {}
        return setUserName;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        //должен возвращать пользователей, которые делали логин.
        Set<String> setUserName = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];
                String event = ss[3];

                //преобразование строки в датую

                Date d = formatter.parse(dateLine);

                if ("LOGIN".equals(event)) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) setUserName.add(userName);
                }
            }

        } catch (Exception e) {}
        return setUserName;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        //должен возвращать пользователей, которые скачали плагин.
        Set<String> setUserName = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];
                String event = ss[3];

                //преобразование строки в датую

                Date d = formatter.parse(dateLine);

                if ("DOWNLOAD_PLUGIN".equals(event)) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) setUserName.add(userName);
                }
            }

        } catch (Exception e) {}
        return setUserName;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        //должен возвращать пользователей, которые отправили сообщение.
        Set<String> setUserName = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];
                String event = ss[3];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if ("WRITE_MESSAGE".equals(event)) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) setUserName.add(userName);
                }
            }

        } catch (Exception e) {}
        return setUserName;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        //должен возвращать множество содержащее пользователей, которые решали любую задачу за выбранный период.
        Set<String> setUserName = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];
                String event = ss[3];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (event.contains("SOLVE_TASK")) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) setUserName.add(userName);
                }
            }

        } catch (Exception e) {}
        return setUserName;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        //должен возвращать множество содержащее пользователей, которые решали
        // задачу с номером task за выбранный период.
        Set<String> setUserName = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];
                String event = ss[3];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (event.contains(Integer.toString(task))) {

                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) setUserName.add(userName);
                }
            }

        } catch (Exception e) {}
        return setUserName;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        //должен возвращать пользователей, которые решили любую задачу.
        Set<String> setUserName = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];
                String event = ss[3];
                String result = ss[4];

                //преобразование строки в дату
                Date d = formatter.parse(dateLine);

                if (event.contains("SOLVE_TASK")) {
                    if (result.equals("OK")) {
                        if (after == null) {
                            after = new Date(Long.MIN_VALUE);
                        }
                        if (before == null) {
                            before = new Date(Long.MAX_VALUE);
                        }
                        if (d.after(after) && d.before(before)) setUserName.add(userName);
                    }
                }
            }


        } catch (Exception e) {}
        return setUserName;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        // должен возвращать пользователей, которые решили задачу с номером task.
        Set<String> setUserName = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];
                String event = ss[3];
                String result = ss[4];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (event.contains(Integer.toString(task))) {
                    if (result.equals("OK")) {

                        if (after == null) {
                            after = new Date(Long.MIN_VALUE);
                        }
                        if (before == null) {
                            before = new Date(Long.MAX_VALUE);
                        }
                        if (d.after(after) && d.before(before)) setUserName.add(userName);

                    }
                }
            }

        } catch (Exception e) {}
        return setUserName;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        //должен возвращать множество дат, когда переданный
        //пользователь произвел переданное событие за выбранный период.
        Set<Date> setDate = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String userName = ss[1];
                String eventLog = ss[3];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (userName.equals(user)) {
                    if (event.toString().contains(eventLog)) {
                        if (after == null) {
                            after = new Date(Long.MIN_VALUE);
                        }
                        if (before == null) {
                            before = new Date(Long.MAX_VALUE);
                        }
                        if (d.after(after) && d.before(before)) setDate.add(d);
                    }
                }
            }

        } catch (Exception e) {}
        return setDate;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        //должен возвращать множество дат,
        //когда любое событие не выполнилось за выбранный период.
        Set<Date> setDate = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String status = ss[4];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (status.equals("FAILED")) {
                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) setDate.add(d);
                }

            }

        } catch (Exception e) {}
        return setDate;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        //должен возвращать множество дат, когда любое
        //событие закончилось ошибкой за выбранный период.
        Set<Date> setDate = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String status = ss[4];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (status.equals("ERROR")) {
                    if (after == null) {
                        after = new Date(Long.MIN_VALUE);
                    }
                    if (before == null) {
                        before = new Date(Long.MAX_VALUE);
                    }
                    if (d.after(after) && d.before(before)) setDate.add(d);
                }

            }

        } catch (Exception e) {}
        return setDate;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        //должен возвращать дату, когда переданный пользователь впервые
        //залогинился за выбранный период. Если такой даты в логах нет - null.
        Set<Date> setDate = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();
        Date minDate = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String userName = ss[1];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (userName.equals(user)) {
                    if (eventLog.equals("LOGIN")) {
                        if (after == null) {
                            after = new Date(Long.MIN_VALUE);
                        }
                        if (before == null) {
                            before = new Date(Long.MAX_VALUE);
                        }
                        if (d.after(after) && d.before(before)) setDate.add(d);
                    }
                }

                for(Date result : setDate){
                    if(minDate == null){
                        if(result.before(new Date())) minDate = result;
                    }
                    else if(result.before(minDate)) minDate = result;

                }
            }

        } catch (Exception e) {}
        return minDate;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        //должен возвращать дату, когда переданный пользователь впервые попытался решить задачу
        //с номером task за выбранный период. Если такой даты в логах нет - null.
        Set<Date> setDate = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();
        Date minDate = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String userName = ss[1];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (userName.equals(user)) {
                    if (eventLog.contains(Integer.toString(task))) {
                        if (after == null) {
                            after = new Date(Long.MIN_VALUE);
                        }
                        if (before == null) {
                            before = new Date(Long.MAX_VALUE);
                        }
                        if (d.after(after) && d.before(before)) setDate.add(d);
                    }
                }

                for(Date result : setDate){
                    if(minDate == null){
                        if(result.before(new Date())) minDate = result;
                    }
                    else if(result.before(minDate)) minDate = result;

                }
            }

        } catch (Exception e) {}
        return minDate;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        // должен возвращать дату, когда переданный пользователь впервые решил задачу с
        // номером task за выбранный период. Если такой даты в логах нет - null.
        Set<Date> setDate = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();
        Date minDate = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String userName = ss[1];
                String status = ss[4];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (userName.equals(user)) {
                    if (eventLog.contains(Integer.toString(task))) {
                        if(status.equals("OK")) {
                            if (after == null) {
                                after = new Date(Long.MIN_VALUE);
                            }
                            if (before == null) {
                                before = new Date(Long.MAX_VALUE);
                            }
                            if (d.after(after) && d.before(before)) setDate.add(d);
                        }
                    }
                }

                for(Date result : setDate){
                    if(minDate == null){
                        if(result.before(new Date())) minDate = result;
                    }
                    else if(result.before(minDate)) minDate = result;

                }
            }

        } catch (Exception e) {}
        return minDate;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        //должен возвращать множество дат, когда переданный пользователь написал сообщение за выбранный период.
        Set<Date> setDate = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();
        Date minDate = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String userName = ss[1];
                String status = ss[4];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (userName.equals(user)) {
                    if (eventLog.equals("WRITE_MESSAGE")) {
                        if (after == null) {
                            after = new Date(Long.MIN_VALUE);
                        }
                        if (before == null) {
                            before = new Date(Long.MAX_VALUE);
                        }
                        if (d.after(after) && d.before(before)) setDate.add(d);
                    }
                }

            }

        } catch (Exception e) {}
        return setDate;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        //должен возвращать множество дат, когда переданный пользователь скачал плагин за выбранный период.
        Set<Date> setDate = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();
        Date minDate = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String userName = ss[1];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    if (userName.equals(user) && eventLog.equals("DOWNLOAD_PLUGIN")) {
                        setDate.add(d);
                    }
                }

            }

        } catch (Exception e) {}
        return setDate;
    }

    private boolean dateBetweenDates(Date current, Date after, Date before) {
        if (after == null) {
            after = new Date(0);
        }
        if (before == null) {
            before = new Date(Long.MAX_VALUE);
        }
        return current.after(after) && current.before(before);
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        //должен возвращать количество уникальных событий за выбранный период.
        Set<Event> setEvent = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String[] ee = eventLog.split(" ");
                Event e = Event.valueOf(ee[0]);

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    setEvent.add(e);
                }

            }

        } catch (Exception e) {}

        return setEvent.size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        //должен возвращать множество уникальных событий за выбранный период.
        Set<Event> setEvent = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String[] ee = eventLog.split(" ");
                Event e = Event.valueOf(ee[0]);

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    setEvent.add(e);
                }

            }

        } catch (Exception e) {}

        return setEvent;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        //должен возвращать множество уникальных событий, которые происходили
        // с переданного IP адреса за выбранный период.
        Set<Event> setEvent = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String lineIP = ss[0];
                String[] ee = eventLog.split(" ");
                Event e = Event.valueOf(ee[0]);

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    if(lineIP.equals(ip)) {
                        setEvent.add(e);
                    }
                }

            }

        } catch (Exception e) {}

        return setEvent;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        //должен возвращать множество уникальных событий, которые
        // произвел переданный пользователь за выбранный период.
        Set<Event> setEvent = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String userName = ss[1];
                String[] ee = eventLog.split(" ");
                Event e = Event.valueOf(ee[0]);

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    if(userName.equals(user)) {
                        setEvent.add(e);
                    }
                }

            }

        } catch (Exception e) {}

        return setEvent;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        //должен возвращать множество уникальных событий, у которых статус выполнения FAILED за выбранный период.
        Set<Event> setEvent = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String status = ss[4];
                String[] ee = eventLog.split(" ");
                Event e = Event.valueOf(ee[0]);

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    if(status.equals("FAILED")) {
                        setEvent.add(e);
                    }
                }

            }

        } catch (Exception e) {}

        return setEvent;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        //должен возвращать множество уникальных событий, у которых статус выполнения ERROR за выбранный период.
        Set<Event> setEvent = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String status = ss[4];
                String[] ee = eventLog.split(" ");
                Event e = Event.valueOf(ee[0]);

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    if(status.equals("ERROR")) {
                        setEvent.add(e);
                    }
                }

            }

        } catch (Exception e) {}

        return setEvent;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        //должен возвращать количество попыток решить задачу с номером task за выбранный период.
        int i = 0;
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String[] ee = eventLog.split(" ");

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    if(ee[0].equals("SOLVE_TASK")) {
                        if(eventLog.contains(Integer.toString(task))) i++;
                    }
                }

            }

        } catch (Exception e) {}

        return i;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        //должен возвращать количество успешных решений задачи с номером task за выбранный период.
        int i = 0;
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String[] ee = eventLog.split(" ");

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    if(ee[0].equals("DONE_TASK")) {
                        if(eventLog.contains(Integer.toString(task))) i++;
                    }
                }

            }

        } catch (Exception e) {}

        return i;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        //должен возвращать мапу (номер_задачи : количество_попыток_решить_ее) за выбранный период.
        Map<Integer, Integer> map = new HashMap<>();
        int i = 0;
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String[] ee = eventLog.split(" ");

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    if(ee[0].equals("SOLVE_TASK")) {

                        if(map.containsKey(Integer.valueOf(ee[1]))){
                            map.put(Integer.valueOf(ee[1]), map.get(Integer.valueOf(ee[1])) + 1);
                        }else{

                            map.put(Integer.valueOf(ee[1]), 1);
                        }
                    }
                }

            }

        } catch (Exception e) {}

        return map;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        //должен возвращать мапу (номер_задачи : сколько_раз_ее_решили) за выбранный период.
        Map<Integer, Integer> map = new HashMap<>();
        int i = 0;
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String eventLog = ss[3];
                String[] ee = eventLog.split(" ");

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (dateBetweenDates(d, after, before)) {
                    if(ee[0].equals("DONE_TASK")) {

                        if(map.containsKey(Integer.valueOf(ee[1]))){
                            map.put(Integer.valueOf(ee[1]), map.get(Integer.valueOf(ee[1])) + 1);
                        }else{

                            map.put(Integer.valueOf(ee[1]), 1);
                        }
                    }
                }

            }

        } catch (Exception e) {}

        return map;
    }

    @Override
    public Set<Object> execute(String query) {
        Set<Object> set = new TreeSet<>();
        Set<Event> setEvent = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        switch (query){
            case "get ip": for (Object obj : getUniqueIPs(null, null)){
                set.add(obj);
            }
                break;

            case "get user": for (Object obj : getAllUsers()){
                set.add(obj);
            }
                break;

            case "get date": for(Object obj : getDataResult()){
                set.add(obj);
            }
                break;

            case "get event": for(Object obj : (getAllEvents(null, null))){
                set.add(obj);
            }
                break;

            case " get status": for(Object obj : getAllStatus()){
                set.add(obj);
            }
                break;
        }

        try {

            if (query.contains("get ip for user")) {
                if(query.contains("and date between")){
                    //"get ip for user = "[any_user]" and date between "[after]" and "[before]"" должен возвращать множество уникальных IP адресов,
                    //с которых работал пользователь с именем [any_user] в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");

                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }

                    for (Object obj : getIPsForUser(result[0], d1, d2)) {
                        set.add(obj);
                    }
                }else {
                    //"get ip for user = "[any_user]"" должен возвращать множество уникальных IP адресов, с которых работал пользователь.
                    String[] userName = query.split(" = ");
                    for (Object obj : (getIPsForUser(userName[1], null, null))) {
                        set.add(obj);
                    }
                }
            }


            else if (query.contains("get ip for date")) {
                if(query.contains("and date between")){
                    //Вызов метода execute с параметром "get ip for date = "[any_date]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных IP адресов, события с которых произведены в указанное время [any_date] в
                    //период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");

                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = getIP(formatter.parse(result[0]), d1, d2);
                }else {
                    //"get ip for date = "[any_date]"" должен возвращать множество уникальных IP адресов,
                    // события с которых произведены в указанное время [any_date].
                    String[] dateLine = query.split(" = ");
                    //преобразование строки в датую
                    Date d = formatter.parse(dateLine[1]);
                    set = getIP(d, null, null);
                }
            }

            else if(query.contains("get ip for event")){
                if(query.contains("and date between")){
                    //get ip for event = "[any_event]" and date between "[after]" and "[before]"" должен возвращать
                    //множество уникальных IP адресов, у которых событие равно [any_event] в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");

                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    for (Object obj : getIPsForEvent(Event.valueOf(result[0]), d1, d2)) {
                        set.add(obj);
                    }
                }else {
                    //"get ip for event = "[any_event]"" должен возвращать множество уникальных IP адресов,
                    //у которых событие равно [any_event].
                    String[] eventLine = query.split(" = ");
                    for (Object obj : (getIPsForEvent(Event.valueOf(eventLine[1]), null, null))) {
                        set.add(obj);
                    }
                }
            }


            else if(query.contains("get ip for status")){
                if(query.contains("and date between")){
                    //"get ip for status = "[any_status]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных IP адресов, события с которых закончились со статусом [any_status] в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ",  " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    for (Object obj : getIPsForStatus(Status.valueOf(result[0]), d1, d2)) {
                        set.add(obj);
                    }
                }else {
                    //"get ip for status = "[any_status]"" должен возвращать множество уникальных IP адресов,
                    //события с которых закончились со статусом [any_status].
                    String[] statusLine = query.split(" = ");
                    for (Object obj : (getIPsForStatus(Status.valueOf(statusLine[1]), null, null))) {
                        set.add(obj);
                    }
                }
            }



            else if(query.contains("get user for ip")){
                if(query.contains("and date between")){
                    //"get user for ip = "[any_ip]" and date between "[after]" and "[before]"" должен возвращать
                    // множество уникальных пользователей, которые работали с IP адреса [any_ip] в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    for (Object obj : getUsersForIP(result[0], d1, d2)) {
                        set.add(obj);
                    }
                }else {
                    //"get user for ip = "[any_ip]"" должен возвращать множество уникальных пользователей,
                    //которые работали с IP адреса [any_ip].
                    String[] ipLine = query.split(" = ");
                    for (Object obj : (getUsersForIP(ipLine[1], null, null))) {
                        set.add(obj);
                    }
                }
            }



            else if(query.contains("get user for date")){
                if(query.contains("and date between")){
                    //"get user for date = "[any_date]" and date between "[after]" and "[before]""
                    // должен возвращать множество уникальных пользователей, которые произвели любое действие
                    // в указанное время [any_date] в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }

                    set = getUserForDate(formatter.parse(result[0]), d1, d2);
                }else {
                    //"get user for date = "[any_date]"" должен возвращать множество уникальных пользователей,
                    //которые произвели любое действие в указанное время [any_date].
                    String[] dateLine = query.split(" = ");
                    //преобразование строки в датую
                    Date d = formatter.parse(dateLine[1]);
                    set = getUserForDate(d, null, null);
                }
            }



            else if(query.contains("get user for event")){
                if(query.contains("and date between")){
                    //"get user for event = "[any_event]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных пользователей, у которых событие равно [any_event]
                    //в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Event event = Event.valueOf(result[0]);

                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = getUserForEvent(event, d1, d2);
                }else {
                    //"get user for event = "[any_event]"" должен возвращать множество уникальных пользователей,
                    //у которых событие равно [any_event].
                    String[] eventLine = query.split(" = ");
                    Event event = Event.valueOf(eventLine[1]);
                    set = getUserForEvent(event, null, null);
                }
            }



            else if(query.contains("get user for status")){
                if(query.contains("and date between")){
                    //"get user for event = "[any_event]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных пользователей, у которых событие равно [any_event]
                    //в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = getUserForStatus(Status.valueOf(result[0]), d1, d2);
                }else {
                    //"get user for status = "[any_status]"" должен возвращать множество уникальных пользователей,
                    //у которых статус равен [any_status].
                    String[] statusLine = query.split(" = ");
                    set = getUserForStatus(Status.valueOf(statusLine[1]), null, null);
                }
            }

            else if(query.contains("get date for ip")){
                if(query.contains("and date between")){
                    //"get date for ip = "[any_ip]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных дат, за которые с IP адреса [any_ip]
                    //произведено любое действие в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");

                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = dateForIPs(result[0], d1, d2);

                }else {
                    //"get date for ip = "[any_ip]"" должен возвращать множество уникальных дат,
                    //за которые с IP адреса [any_ip] произведено любое действие.
                    String[] ipLine = query.split(" = ");
                    set = dateForIPs(ipLine[1], null, null);
                }
            }



            else if(query.contains("get date for user")){
                if(query.contains("and date between")){
                    //"get date for user = "[any_user]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных дат, за которые пользователь [any_user]
                    //произвел любое действие в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = dateForUser(result[0], d1, d2);
                }else {
                    //"get date for user = "[any_user]"" должен возвращать множество уникальных дат,
                    //за которые пользователь [any_user] произвел любое действие.
                    String[] userName = query.split(" = ");
                    set = dateForUser(userName[1], null, null);
                }
            }



            else if(query.contains("get date for event")){
                if(query.contains("and date between")){
                    //"get date for event = "[any_event]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных дат, за которые произошло событие равно [any_event] в период
                    //между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = dateForEvent(result[0], d1, d2);
                }else {
                    //"get date for event = "[any_event]"" должен возвращать множество уникальных дат,
                    //за которые произошло событие равно [any_event].
                    String[] event = query.split(" = ");
                    set = dateForEvent(event[1], null, null);
                }
            }




            else if(query.contains("get date for status")){
                if(query.contains("and date between")){
                    //"get date for status = "[any_status]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных дат, за которые произошло любое событие
                    //со статусом [any_status] в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = dateForStatus(result[0], d1, d2);
                }else {
                    //"get date for status = "[any_status]"" должен возвращать множество уникальных дат,
                    //за которые произошло любое событие со статусом [any_status].
                    String[] status = query.split(" = ");
                    set = dateForStatus(status[1], null, null);
                }
            }


            else if(query.contains("get event for ip")){
                if(query.contains("and date between")){
                    //"get event for ip = "[any_ip]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных событий, которые произошли с IP адреса
                    //[any_ip] в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    for (Object obj : getEventsForIP(result[0],  d1, d2)) {
                        set.add(obj);
                    }
                }else {
                    //"get event for ip = "[any_ip]"" должен возвращать множество уникальных событий,
                    //которые произошли с IP адреса [any_ip].
                    String[] ip = query.split(" = ");
                    for (Object obj : getEventsForIP(ip[1], null, null)) {
                        set.add(obj);
                    }
                }
            }



            else if(query.contains("get event for user")){
                if(query.contains("and date between")){
                    //"get event for user = "[any_user]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных событий, которые произвел пользователь [any_user]
                    //в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    for (Object obj : getEventsForUser(result[0],  d1, d2)) {
                        set.add(obj);
                    }
                }else {
                    //"get event for user = "[any_user]"" должен возвращать множество уникальных событий,
                    //которые произвел пользователь [any_user].
                    String[] userName = query.split(" = ");
                    for (Object obj : getEventsForUser(userName[1], null, null)) {
                        set.add(obj);
                    }
                }
            }



            else if(query.contains("get event for date")){
                if(query.contains("and date between")){
                    //"get event for date = "[any_date]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных событий, которые произошли во время [any_date]
                    //в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = eventForDate(result[0], d1, d2);
                }else {
                    //"get event for date = "[any_date]"" должен возвращать множество уникальных событий,
                    //которые произошли во время [any_date].
                    String[] date = query.split(" = ");
                    set = eventForDate(date[1], null, null);
                }
            }



            else if(query.contains("get event for status")){
                if(query.contains("and date between")){
                    //"get event for status = "[any_status]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных событий, которые завершены со статусом [any_status]
                    //в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = eventForStatus(result[0], d1, d2);
                }else {
                    //"get event for status = "[any_status]"" должен возвращать множество уникальных событий,
                    //которые завершены со статусом [any_status].
                    String[] status = query.split(" = ");
                    set = eventForStatus(status[1], null, null);
                }
            }




            else if(query.contains("get status for ip")){
                if(query.contains("and date between")){
                    //"get status for ip = "[any_ip]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных статусов, которые произошли с IP адреса [any_ip]
                    //в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = getStatusForIPs(result[0], d1, d2);
                }else {
                    //"get status for ip = "[any_ip]"" должен возвращать множество уникальных статусов,
                    //которые произошли с IP адреса [any_ip].
                    String[] ip = query.split(" = ");
                    set = getStatusForIPs(ip[1], null, null);
                }
            }




            else if(query.contains("get status for user")){
                if(query.contains("and date between")){
                    //get status for user = "[any_user]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных статусов, которые произвел пользователь [any_user]
                    //в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = getStatusForUser(result[0], d1, d2);
                }else {
                    //"get status for user = "[any_user]"" должен возвращать множество уникальных статусов,
                    //которые произвел пользователь [any_user].
                    String[] userName = query.split(" = ");
                    set = getStatusForUser(userName[1], null, null);
                }
            }



            else if(query.contains("get status for date")){
                if(query.contains("and date between")){
                    //"get status for date = "[any_date]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных статусов, которые произошли во время [any_date]
                    //в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = getStatusForDate(result[0], d1, d2);
                }else {
                    //"get status for date = "[any_date]"" должен возвращать множество уникальных статусов,
                    //которые произошли во время [any_date].
                    String[] dateLine = query.split(" = ");
                    set = getStatusForDate(dateLine[1], null, null);
                }
            }



            else if(query.contains("get status for event")){
                if(query.contains("and date between")) {
                    //"get status for event = "[any_event]" and date between "[after]" and "[before]""
                    //должен возвращать множество уникальных статусов, у которых событие равно [any_event]
                    //в период между датами [after] и [before].
                    String[] eventLine = query.split(" = ");
                    String res = eventLine[1].replaceAll(" and date between ", " g ").replaceAll(" and ", " g ");
                    String[] result = res.split(" g ");
                    Date d1 = null;
                    Date d2 = null;

                    if(!(result[1].contains("null"))){
                        d1 = formatter.parse(result[1]);
                    }
                    if(!(result[2].contains("null"))){
                        d2 = formatter.parse(result[2]);
                    }
                    set = getStatusForEvent(result[0], d1, d2);
                }else {
                    // "get status for event = "[any_event]"" должен возвращать множество уникальных статусов,
                    //у которых событие равно [any_event].
                    String[] eventLine = query.split(" = ");
                    set = getStatusForEvent(eventLine[1], null, null);
                }
            }

        }catch(Exception e){}

        return set;
    }

    public Set<Object> getStatusForEvent(String event, Date after, Date before){
        //должен возвращать множество уникальных статусов,
        //у которых событие равно [any_event].
        Set<Object> setStatus = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String eventLine = ss[3];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if(eventLine.contains(event)){
                        setStatus.add(Status.valueOf(ss[4]));
                    }
                }
            }

        } catch (Exception e) {}

        return setStatus;
    }

    public Set<Object> getStatusForDate(String date, Date after, Date before){
        //должен возвращать множество уникальных статусов,
        //которые произвел пользователь name.
        Set<Object> setStatus = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if(dateLine.equals(date)){
                        setStatus.add(Status.valueOf(ss[4]));
                    }
                }
            }

        } catch (Exception e) {}

        return setStatus;
    }

    public Set<Object> getStatusForUser(String name, Date after, Date before){
        //должен возвращать множество уникальных статусов,
        //которые произвел пользователь name.
        Set<Object> setStatus = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String userName = ss[1];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if(userName.equals(name)){
                        setStatus.add(Status.valueOf(ss[4]));
                    }
                }
            }

        } catch (Exception e) {}

        return setStatus;
    }

    public Set<Object> getStatusForIPs(String ip, Date after, Date before){
        //должен возвращать множество уникальных статусов,
        //которые произошли с IP адреса ip.
        Set<Object> setStatus = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String ipLine = ss[0];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if(ipLine.equals(ip)){
                        setStatus.add(Status.valueOf(ss[4]));
                    }
                }
            }

        } catch (Exception e) {}

        return setStatus;
    }

    public Set<Object> eventForStatus(String status, Date after, Date before){
        //должен возвращать множество уникальных событий,
        //которые завершены со статусом status.
        Set<Object> setEvent = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String statusLine = ss[4];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if(statusLine.equals(status)){
                        setEvent.add(Event.valueOf(ss[3].replaceAll("[0-9]", "").replaceAll(" ", "")));
                    }
                }
            }

        } catch (Exception e) {}

        return setEvent;
    }

    public Set<Object> eventForDate(String date, Date after, Date before){
        //должен возвращать множество уникальных событий,
        //которые произошли во время date.
        Set<Object> setEvent = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if(dateLine.equals(date)){
                        setEvent.add(Event.valueOf(ss[3].replaceAll("[0-9]", "").replaceAll(" ", "")));
                    }
                }
            }

        } catch (Exception e) {}

        return setEvent;
    }

    public Set<Object> dateForStatus(String status, Date after, Date before){
        //должен возвращать множество уникальных дат,
        //за которые произошло любое событие со статусом status.
        Set<Object> setDate = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String statusLine = ss[4];

                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if(statusLine.equals(status)){
                        setDate.add(d);
                    }
                }
            }

        } catch (Exception e) {}

        return setDate;
    }

    public Set<Object> dateForEvent(String event, Date after, Date before){
        //должен возвращать множество уникальных дат,
        //за которые произошло событие event.
        Set<Object> setDate = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String eventLine = ss[3];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if(eventLine.contains(event)){
                        setDate.add(d);
                    }
                }
            }

        } catch (Exception e) {}

        return setDate;
    }

    public Set<Object> dateForUser(String name, Date after, Date before){
        //должен возвращать множество уникальных дат,
        //за которые пользователь name произвел любое действие.
        Set<Object> setDate = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String userNameLine = ss[1];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if(userNameLine.equals(name)){
                        setDate.add(d);
                    }
                }
            }

        } catch (Exception e) {}

        return setDate;
    }

    public Set<Object> dateForIPs(String ip, Date after, Date before){
        //должен возвращать множество уникальных дат,
        //за которые с IP адреса ip произведено любое действие.
        Set<Object> setDate = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String ipLine = ss[0];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if (ipLine.equals(ip)) {
                        setDate.add(d);
                    }
                }
            }

        } catch (Exception e) {}

        return setDate;
    }

    public Set<Object> getUserForStatus(Status status, Date after, Date before){
        //должен возвращать множество уникальных пользователей,
        //у которых Status равно status.
        Set<Object> setUserName = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String userName = ss[1];
                String statusLine = ss[4];
                Status st = Status.valueOf(statusLine);

                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if (st.equals(status)) {
                        setUserName.add(userName);
                    }
                }
            }

        } catch (Exception e) {}

        return setUserName;
    }

    public  Set<Object> getUserForEvent(Event event, Date after, Date before){
        //должен возвращать множество уникальных пользователей,
        //у которых событие равно event.
        Set<Object> setUserName = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String userName = ss[1];
                String[] eventLine = ss[3].split(" ");
                Event ev = Event.valueOf(eventLine[0]);

                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if (ev.equals(event)) {
                        setUserName.add(userName);
                    }
                }
            }

        } catch (Exception e) {}

        return setUserName;
    }

    public  Set<Object> getUserForDate(Date date, Date after, Date before){
        //должен возвращать множество уникальных пользователей,
        //которые произвели любое действие в указанное время [any_date].
        Set<Object> setUserName = new HashSet<>();
        List<String[]> list = new ArrayList<>();
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String userName = ss[1];
                Date d = formatter.parse(ss[2]);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)) {
                    if (d.equals(date)) {
                        setUserName.add(userName);
                    }
                }
            }

        } catch (Exception e) {}

        return setUserName;
    }

    public Set<Status> getAllStatus(){
        Set<Status> setStatus = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {

                String status = ss[4];
                Status e = Status.valueOf(status);
                setStatus.add(e);
            }

        } catch (Exception e) {}

        return setStatus;
    }

    public Set<Date> getDataResult(){
        Set<Date> setDate = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);
                setDate.add(d);
            }

        } catch (Exception e) {}
        return setDate;
    }

    public Set<Object> getIP(Date date, Date after, Date before){
        //должен возвращать IP, с переданной датой.
        Set<Object> set = new HashSet<>();
        String line;
        List<String[]> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logDir.toString())));
            while ((line = reader.readLine()) != null){
                String[] s = line.split("\t");
                list.add(s);
            }

            for (String[] ss : list) {
                String dateLine = ss[2];
                String ipLine = ss[0];

                //преобразование строки в датую
                Date d = formatter.parse(dateLine);

                if (after == null) {
                    after = new Date(Long.MIN_VALUE);
                }
                if (before == null) {
                    before = new Date(Long.MAX_VALUE);
                }
                if (d.after(after) && d.before(before)){
                    if (d.equals(date)) {
                        set.add(ipLine);
                    }
                }
            }

        } catch (Exception e) {}

        return set;
    }
}