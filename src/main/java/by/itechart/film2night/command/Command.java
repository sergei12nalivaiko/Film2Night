package by.itechart.film2night.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

public interface Command {

    void execute(HttpServletRequest request, HttpServletResponse response) throws ParseException;
    void execute();
    default void execute(List<Integer> allIdFilms){
    }
}
