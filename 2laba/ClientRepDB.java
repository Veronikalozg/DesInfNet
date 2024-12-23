class ClientRepDB {
//Определение класса ClientRepDB, который будет управлять операциями с клиентами в базе данных.
    private DatabaseConnection db;
//Приватное поле для хранения объекта DatabaseConnection, который управляет соединением с базой данных.
    public ClientRepDB(String dbName, String user, String password, String host, String port) {
//Конструктор класса, принимающий параметры для подключения к базе данных: имя базы данных, имя пользователя, пароль, хост и порт.
        this.db = DatabaseConnection.getInstance(dbName, user, password, host, port);
//Инициализация поля db с помощью метода getInstance класса DatabaseConnection, который создает или возвращает существующее соединение с базой данных.
    public Client getById(int clientId) {
//A..Метод для получения клиента из базы данных по его идентификатору (clientId).
        Client client = null;
//Инициализация переменной client, которая будет хранить найденного клиента.
        String query = "SELECT * FROM clients WHERE id = ?";
//SQL-запрос для получения данных клиента по идентификатору.
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
//Открытие соединения с базой данных и подготовка SQL-запроса с использованием PreparedStatement.
            stmt.setInt(1, clientId);
//Установка значения идентификатора клиента в подготовленный запрос.
            ResultSet rs = stmt.executeQuery();
//Выполнение запроса и получение результата в виде ResultSet.
            if (rs.next()) {
//Проверка, есть ли хотя бы одна запись в результате запроса.
                return new Client(
//Если запись найдена, создается новый объект Client с данными из результата запроса.
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getInt("total_services"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("gender")
                );
//Извлечение данных клиента из ResultSet и передача их в конструктор Client.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//Обработка исключений SQLException, если что-то пошло не так при выполнении запроса.
        return null;
//Если клиент не найден, возвращаем null.
    public List<Client> getKNSortList(int k, int n) {
//B..Метод для получения списка клиентов с пагинацией, где k – это номер страницы, а n – количество клиентов на странице.
        List<Client> clients = new ArrayList<>();
//Создание списка для хранения клиентов.
        String sql = "SELECT * FROM clients ORDER BY id OFFSET ? LIMIT ?";
//SQL-запрос для получения клиентов с сортировкой и пагинацией.
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
//Открытие соединения и подготовка SQL-запроса.
            stmt.setInt(1, (k - 1) * n);
            stmt.setInt(2, n);
//Установка значений для пагинации: смещение (OFFSET) и лимит (LIMIT).
            ResultSet rs = stmt.executeQuery();
//Выполнение запроса и получение результата.
            while (rs.next()) {
//Обработка результата запроса: цикл, который проходит по всем записям.
                clients.add(new Client(
//Создание объекта Client для каждой записи и добавление его в список.
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getInt("total_services"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("gender")
                ));
//Извлечение данных из ResultSet и передача их в конструктор Client.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//Обработка исключений.
        return clients;
//Возвращение списка клиентов.
    public void addClient(Client client) throws SQLException {
//C..Метод для добавления нового клиента в базу данных.
        if (!isUnique(client.getPhone())) {
            throw new SQLException("Клиент с таким телефоном уже существует!");
        }
//Проверка, уникален ли номер телефона клиента. Если нет, выбрасывается исключение.
        String sql = "INSERT INTO clients (name, surname, patronymic, total_services, phone, email, gender) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
//SQL-запрос для добавления нового клиента с возвратом его id.
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
//Открытие соединения и подготовка SQL-запроса.
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getSurname());
            stmt.setString(3, client.getPatronymic());
            stmt.setObject(4, client.getServices(), Types.INTEGER); // Используем setObject для обработки null
//Установка значений из объекта Client в подготовленный запрос.
            stmt.setString(5, client.getPhone());
            stmt.setString(6, client.getEmail());
            stmt.setString(7, client.getGender());
//Установка оставшихся значений.
            ResultSet rs = stmt.executeQuery();
//Выполнение запроса и получение результата.
            if (rs.next()) {
                int generatedId = rs.getInt("id");
                System.out.println("Клиент добавлен с ID: " + generatedId);
            }
//Если клиент успешно добавлен, извлекается сгенерированный id и выводится сообщение.
        }
    }
//Закрытие блока try.
    public boolean replaceById(int clientId, Client newClient) throws SQLException {
//D..Метод для замены информации о клиенте по идентификатору.
        if (!isUnique(newClient.getPhone())) {
            throw new SQLException("Нельзя заменить клиента: клиент с таким телефоном уже существует!");
        }
//Проверка уникальности номера телефона для нового клиента.
        String sql = "UPDATE clients SET name = ?, surname = ?, patronymic = ?, total_services = ?, phone = ?, email = ?, gender = ? WHERE id = ?";
//SQL-запрос для обновления информации о клиенте.
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
//Открытие соединения и подготовка SQL-запроса.
            stmt.setString(1, newClient.getName());
            stmt.setString(2, newClient.getSurname());
            stmt.setString(3, newClient.getPatronymic());
            stmt.setObject(4, newClient.getServices(), Types.INTEGER); // Используем setObject для обработки null
            stmt.setString(5, newClient.getPhone());
            stmt.setString(6, newClient.getEmail());
            stmt.setString(7, newClient.getGender());
            stmt.setInt(8, clientId);
//Установка значений для обновления, включая идентификатор клиента.
            return stmt.executeUpdate() > 0;
        }
//Выполнение обновления и возврат true, если обновление произошло успешно (изменено больше 0 записей).
    public void deleteById(int clientId) {
//E..Метод для удаления клиента по его идентификатору.
        String sql = "DELETE FROM clients WHERE id = ?";
//SQL-запрос для удаления клиента из базы данных.
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
//Открытие соединения и подготовка SQL-запроса.
            stmt.setInt(1, clientId);
            stmt.executeUpdate();
//Установка идентификатора клиента и выполнение удаления.
        } catch (SQLException e) {
            e.printStackTrace();
        }
//Обработка исключений.
    public int getCount() {
//F..Метод для получения количества клиентов в базе данных.
        String sql = "SELECT COUNT(*) FROM clients";
//SQL-запрос для подсчета клиентов.
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Если 0, значит уникален
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
