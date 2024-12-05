class ClientRepository {
//Определение класса ClientRepository, который будет управлять коллекцией клиентов.
    protected String filename;
    protected List<Client> clients;
    private ClientStrategy strategy;
//Объявление полей класса:
//filename: имя файла, в котором могут храниться данные клиентов.
//clients: список клиентов.
//strategy: объект, реализующий интерфейс ClientStrategy, который определяет, как будут сохраняться и загружаться клиенты.
    public ClientRepository(String filename, ClientStrategy strategy) {
//Конструктор класса, принимающий имя файла и стратегию хранения клиентов.
        this.filename = filename;
        this.setStrategy(strategy);
        this.clients = readAllClients();
//Инициализация полей: установка имени файла, установка стратегии и чтение всех клиентов из источника данных.
    public void setStrategy(ClientStrategy strategy) {
        this.strategy = strategy;
    }
//Метод для установки стратегии хранения клиентов.
    public void saveAllClients() {
        strategy.saveAll(clients);
    }
//Метод для сохранения всех клиентов, используя стратегию сохранения.
    public List<Client> readAllClients() {
        return strategy.readAll();
    }
//Метод для чтения всех клиентов, используя стратегию чтения.
    public Client getById(int clientId) {
//Метод для получения клиента по его идентификатору (clientId).
        for (Client client : clients) {
            if (client.getId() == clientId) {
                return client;
            }
        }
//Перебор списка клиентов: если идентификатор клиента совпадает с переданным, возвращаем найденного клиента.
        return null;
//Если клиент не найден, возвращаем null.
    public List<Client> getKNSortList(int k, int n) {
//Метод для получения подсписка клиентов с пагинацией, где k – номер страницы, а n – количество клиентов на странице.
        int start = (k - 1) * n;
        int end = Math.min(start + n, clients.size());
//Вычисление начального и конечного индексов для извлечения подсписка клиентов.
        return clients.subList(start, end);
///Возвращаем подсписок клиентов на основе вычисленных индексов.
    public void sortByField() {
//Метод для сортировки списка клиентов по идентификатору.
        clients.sort(Comparator.comparing(Client::getId));
//Использование компаратора для сортировки клиентов по их идентификатору.
    protected boolean isUnique(String phone) {
//Метод для проверки уникальности номера телефона клиента.
        for (Client client : clients) {
            if (client.getPhone().equals(phone)) {
                return false;
            }
        }
//Перебор списка клиентов: если найден клиент с таким же номером телефона, возвращаем false.
        return true;
//Если номер телефона уникален, возвращаем true.
    public void addClient(Client client) throws Exception {
//Метод для добавления нового клиента.
        int newId = clients.stream().mapToInt(Client::getId).max().orElse(0) + 1;
//Определение нового идентификатора для клиента как максимального идентификатора в списке клиентов плюс один. Если список пуст, идентификатор будет равен 1.
        if (!isUnique(client.getPhone())) {
            throw new Exception("Клиент с таким телефоном уже существует!");
        }
//Проверка уникальности номера телефона. Если номер уже существует, выбрасывается исключение.
        Client newClient = new Client(newId, client.getName(), client.getSurname(), client.getPatronymic(), client.getServices(), client.getPhone(), client.getEmail(), client.getGender());
//Создание нового объекта Client с установленным идентификатором и данными из переданного клиента.
        clients.add(newClient);
//Добавление созданного клиента в список.
    public boolean replaceById(int clientId, Client newClient) throws Exception {
//Метод для замены информации о клиенте по его идентификатору.
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == clientId) {
//Перебор списка клиентов для поиска клиента с указанным идентификатором.
                if (!isUnique(newClient.getPhone())) {
                    throw new Exception("Нельзя заменить клиента: клиент с таким телефоном уже существует!");
                }
//Проверка уникальности номера телефона нового клиента. Если номер уже существует, выбрасывается исключение.
                clients.set(i, newClient);
                return true;
            }
        }
//Если клиент найден, замена старого клиента на нового и возврат true.
        return false;
//Если клиент с указанным идентификатором не найден, возвращаем false.
    public void deleteById(int clientId) {
//Метод для удаления клиента по его идентификатору.
        clients = clients.stream()
                .filter(customer -> customer.getId() != clientId)
                .collect(Collectors.toList());
//Использование стрима для фильтрации списка клиентов: оставляем только тех, у кого идентификатор не совпадает с удаляемым. Результат сохраняем обратно в список клиентов.
    public int getCount() {
//Метод для получения количества клиентов.
        return clients.size();
//Возвращение размера списка клиентов, то есть их количества.;
    }
}
