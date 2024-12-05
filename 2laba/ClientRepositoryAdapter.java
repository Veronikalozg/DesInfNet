class ClientRepositoryAdapter {
//Определение класса ClientRepositoryAdapter, который будет служить адаптером для работы с ClientRepository.
    private ClientRepository clientRepository;
//Приватное поле для хранения экземпляра ClientRepository, который будет использоваться для выполнения операций с клиентами.
    public ClientRepositoryAdapter(String filename, ClientStrategy strategy) {
//Конструктор класса, который принимает имя файла и стратегию хранения клиентов.
        this.clientRepository = new ClientRepository(filename, strategy);
//Инициализация поля clientRepository, создавая новый объект ClientRepository с переданными параметрами.
    public Client getById(int clientId) {
//Метод для получения клиента по его идентификатору (clientId).
        return  clientRepository.getById(clientId);
//Вызов метода getById на объекте clientRepository и возврат результата.
    public List<Client> getKNSortList(int k, int n) {
//Метод для получения подсписка клиентов с пагинацией.
        return clientRepository.getKNSortList(k, n);
//Вызов метода getKNSortList на объекте clientRepository и возврат результата.
    public void addClient(Client client) throws Exception {
//Метод для добавления нового клиента.
        clientRepository.addClient(client);
//Вызов метода addClient на объекте clientRepository, чтобы добавить клиента.
        clientRepository.saveAllClients();
//Сохранение всех клиентов после добавления нового.
    public boolean replaceById(int clientId, Client newClient) throws Exception {
//Метод для замены информации о клиенте по его идентификатору.
        boolean flag = clientRepository.replaceById(clientId, newClient);
//Вызов метода replaceById на объекте clientRepository и сохранение результата в переменной flag.
        if(flag) {
            clientRepository.saveAllClients();
            return true;
        }
//Если замена прошла успешно (flag равно true), сохраняем всех клиентов и возвращаем true.
        return false;
//Если замена не прошла успешно, возвращаем false.
    public void deleteById(int clientId) {
//Метод для удаления клиента по его идентификатору.
        clientRepository.deleteById(clientId);
//Вызов метода deleteById на объекте clientRepository, чтобы удалить клиента.
        clientRepository.saveAllClients();
//Сохранение всех клиентов после удаления.
    public int getCount() {
//Метод для получения количества клиентов.
        return clientRepository.getCount();
//Вызов метода getCount на объекте clientRepository и возвращение результата.;
    }
}
