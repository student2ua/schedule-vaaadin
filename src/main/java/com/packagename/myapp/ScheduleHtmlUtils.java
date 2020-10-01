package com.packagename.myapp;

import com.packagename.myapp.service.IScheduleService;
import com.packagename.myapp.service.RestScheduleService;
import com.packagename.myapp.util.XSLTransformer;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.logging.Logger;

/**
 * User: tor
 * Date: 25.02.2020
 * Time: 21:06
 */
class ScheduleHtmlUtils {
    /**
     * Single transformer instance for all transformations
     */
    private static XSLTransformer transformer = XSLTransformer.newInstance("stylesheet/schedule2html.xsl");

    static String renderGroupSheduleHtml(String stringGroup, String studentString, String weekString) {
        IScheduleService service = new RestScheduleService();
        Integer week = null;
        if (weekString != null && !weekString.isEmpty() && weekString.matches("[0-9]+")) {
            week = Integer.parseInt(weekString);
        }
        Integer studId =null;
        if (studentString != null && !studentString.isEmpty() && studentString.matches("[0-9]+")) {
            studId = Integer.parseInt(studentString);;
        }

        final String studentSchedule = service.getStudentSchedule(Integer.parseInt(stringGroup), studId, week);
        String ins = "Error";
        Logger.getLogger(ScheduleHtmlUtils.class.getName()).info(studentSchedule);
        try {
            final Document xmlDocument = transformer.loadXMLFromString(studentSchedule);
            if (week == null) {
                XPath xPath = XPathFactory.newInstance().newXPath();
                Double node = (Double) xPath.evaluate("/schedule/week/@number", xmlDocument, XPathConstants.NUMBER);
                if (node != null) week = node.intValue();
                Logger.getLogger(ScheduleHtmlUtils.class.getName()).info("week=" + week);
            }

            ins = transformer.transformToString(xmlDocument).toString();


        } catch (Exception e) {
            e.printStackTrace();
        }
//        String.format("/student.html?group=%s&student=%s&week", stringGroup, studentString, weekString);
        return "<!Doctype html> <html>" +
                ins +
//                language=HTML
                "<table border=\"0\" style=\"width: 100%; margin-top: 10px;\">\n" +
                "    <tr>\n" +
                "        <td width=\"10%\" align=\"left\" nowrap=\"nowrap\">\n" +
                "            <a title=\"Перейти к страничке выбора группы/преподавателя\"\n" +
                "               href=\"\\\">&larr; Главная страница</a>&nbsp;&nbsp;&nbsp;\n" +
                "        </td>\n" +
                "        <td width=\"80%\" align=\"center\" nowrap=\"nowrap\">\n " +
                (week != null ? getNavlink(week, "/student.html?group=" + stringGroup + "&student=" + studentString + "&week=%d") : "") +
                "        </td>\n" +
                "        <td width=\"10%\" nowrap=\"nowrap\">&nbsp;\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</html>";
    }

    static String renderEmployeeSheduleHtml(String employeeString, String weekString) {
        IScheduleService service = new RestScheduleService();
        Integer week = null;
        if (weekString != null && !weekString.isEmpty() && weekString.matches("[0-9]+")) {
            week = Integer.parseInt(weekString);
        }
        final String employeeSchedule = service.getEmployeeSchedule(Integer.parseInt(employeeString), week);
        String ins = "Error";
        Logger.getLogger(ScheduleHtmlUtils.class.getName()).info(employeeSchedule);
        try {
            final Document xmlDocument = transformer.loadXMLFromString(employeeSchedule);
            if (week == null) {
                XPath xPath = XPathFactory.newInstance().newXPath();
                Double node = (Double) xPath.evaluate("/schedule/week/@number", xmlDocument, XPathConstants.NUMBER);
                if (node != null) week = node.intValue();
                Logger.getLogger(ScheduleHtmlUtils.class.getName()).info("week=" + week);
            }

            ins = transformer.transformToString(xmlDocument).toString();


        } catch (Exception e) {
            e.printStackTrace();
        }
//        String.format("/student.html?group=%s&student=%s&week", stringGroup, studentString, weekString);
        return "<!Doctype html> <html>" +
                ins +
//                language=HTML
                "<table border=\"0\" style=\"width: 100%; margin-top: 10px;\">\n" +
                "    <tr>\n" +
                "        <td width=\"10%\" align=\"left\" nowrap=\"nowrap\">\n" +
                "            <a title=\"Перейти к страничке выбора группы/преподавателя\"\n" +
                "               href=\"selection.jsf\">&larr; Главная страница</a>&nbsp;&nbsp;&nbsp;\n" +
                "        </td>\n" +
                "        <td width=\"80%\" align=\"center\" nowrap=\"nowrap\">\n " +
                (week != null ? getNavlink(week, "/employee.html?employee=" + employeeString + "&week=%d"):"") +
                "        </td>\n" +
                "        <td width=\"10%\" nowrap=\"nowrap\">&nbsp;\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</html>";
    }

    private static String getNavlink(@NotNull Integer week, @NotNull String linkTmp) {
        StringBuilder rez = new StringBuilder();
        Integer forwardweekInt = week + 1;
        Integer backWeekInt = week - 1;
        if (backWeekInt < 0) backWeekInt = 0;
        if (backWeekInt == 0) {
            //                language=HTML
            rez.append("<span style=\"color:gray\">[Первая неделя]</span>");
        } else {
            //                language=HTML
            rez
                    .append("<a title=\"Перейти к предыдущей неделе расписания\" href=\"")
                    .append(String.format(linkTmp, backWeekInt))
                    .append("\">&larr; Предыдущая неделя</a>");
        }

        rez.append(" &nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;");
        if (forwardweekInt >= 52) rez.append("<span style=\"color:gray\">[Последняя неделя]</span>");
        else
            rez
                    .append("<a title=\"Перейти к следующей неделе расписания\" href=\"")
                    .append(String.format(linkTmp, forwardweekInt))
                    .append("\">Следующая неделя &rarr;</a>");
        return rez.toString();
    }
}