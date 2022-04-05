package by.itechart.film2night.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

public interface Command {

    void execute(HttpServletRequest request, HttpServletResponse response) throws ParseException;
}
