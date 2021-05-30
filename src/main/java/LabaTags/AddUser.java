package LabaTags;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import Entity.chatMessage;
import LabaPacks.User;
import LabaPacks.UserList;
import LabaPacks.UserListHelper;
import LabaPacks.UserList.UserExistsException;

public class AddUser extends SimpleTagSupport {


    // Поле данных для атрибута user
    private User user;

    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setUser(User user) {
        this.user = user;
    }

    public void doTag() throws JspException, IOException {

        // Изначально описание ошибки = null (т.е. ошибки нет)
        String errorMessage = null;
        // Извлечь из контекста приложения общий список пользователей
        UserList userList = (UserList) getJspContext().getAttribute("users", PageContext.APPLICATION_SCOPE);

        // Проверить, что логин не пустой
        if (user.getLogin() == null || user.getLogin().equals("")) {
            errorMessage = "Логин не может быть пустым!";
        } else {

            // Проверить, что имя не пустое
            if (user.getName() == null || user.getName().equals("")) {
                errorMessage = "Имя пользователя не может быть пустым!";
            }
        }
        // Если ошибки не было - добавить пользователя
        if (errorMessage == null) {
            try {

                // Непосредственное добавление пользователя делает
                userList.addUser(user);

                // Записать обновлѐнный список пользователей в файл
                UserListHelper.saveUserList(userList);

            } catch (UserExistsException e) {

                // Ошибка - пользователь с таким логином уже существует
                errorMessage = "Пользователь с таким логином уже существует!";
            }
        }
        // Сохранить описание ошибки (текст или null) в сессии
        getJspContext().setAttribute("errorMessage", errorMessage, PageContext.SESSION_SCOPE);
    }

	/*protected void doGet(HttpServletRequest request, HttpServletResponse
			response) throws ServletException, IOException {
		// Установить кодировку HTTP-ответа UTF-8
		response.setCharacterEncoding("utf8");

		String type = request.getParameter("type");*/


}

