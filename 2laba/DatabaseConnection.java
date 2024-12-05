class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private String dbName;
    private String user;
    private String password;
    private String host;
    private String port;

    private DatabaseConnection(String dbName, String user, String password, String host, String port) {
        this.dbName = dbName;
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;

        connect();  // Устанавливаем соединение при создании объекта
    }

  private void connect() {
//Приватный метод connect, который устанавливает соединение с базой данных.
    try {
        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);
//Создание строки URL для подключения к базе данных PostgreSQL, используя форматирование строк. Переменные host, port и dbName должны быть определены в классе.
        connection = DriverManager.getConnection(url, user, password);
//Использование DriverManager для получения соединения с базой данных, передавая URL, имя пользователя и пароль.
        System.out.println("Соединение установлено.");
//Вывод сообщения в консоль, указывающего на успешное установление соединения.
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Ошибка подключения к базе данных");
//Обработка исключения SQLException, которое может возникнуть при попытке подключения. В случае ошибки выводится стек вызовов и выбрасывается исключение RuntimeException с сообщением об ошибке.
    }
}
//Закрытие блока try-catch и метода connect.
public static DatabaseConnection getInstance(String dbName, String user, String password, String host, String port) {
//Статический метод getInstance, который возвращает единственный экземпляр класса DatabaseConnection. Это реализует паттерн проектирования "Одиночка" (Singleton).
    if (instance == null) {
        instance = new DatabaseConnection(dbName, user, password, host, port);
    }
//Если экземпляр instance еще не создан, создается новый экземпляр DatabaseConnection с переданными параметрами (имя базы данных, пользователь, пароль, хост и порт).
    return instance;
}
//Возврат существующего или только что созданного экземпляра DatabaseConnection.
protected Connection getConnection() {
//Защищенный метод getConnection, который возвращает текущее соединение с базой данных.
    try {
        if (connection == null || connection.isClosed()) {
//Проверка, существует ли соединение (connection) и не закрыто ли оно. Если соединение отсутствует или закрыто, необходимо повторное подключение.
            System.out.println("Соединение закрыто или отсутствует. Пытаюсь подключиться снова...");
            connect();  // Повторное подключение при необходимости
//Вывод сообщения в консоль, указывающего на закрытое или отсутствующее соединение, и попытка повторного подключения с помощью метода connect.
        } catch (SQLException e) {
            e.printStackTrace();
        }
//Обработка исключения SQLException, которое может возникнуть при проверке состояния соединения.
    return connection;
}
//Возврат текущего соединения с базой данных. Если оно было закрыто, вызовется метод connect, чтобы установить новое соединение перед возвратом.
}
