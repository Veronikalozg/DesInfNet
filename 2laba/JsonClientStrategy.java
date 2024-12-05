class JsonClientStrategy implements ClientStrategy {
//Определение класса JsonClientStrategy, который реализует интерфейс ClientStrategy. Этот класс будет использоваться для чтения и записи данных клиентов в формате JSON.
    private String filename;
//Приватное поле filename для хранения имени файла, в котором будут храниться данные клиентов.
    public JsonClientStrategy(String filename) {
//Конструктор класса, который принимает имя файла в качестве параметра.
        this.filename = filename;
    }
//Инициализация поля filename с переданным значением.
    public List<Client> readAll() {
//Метод для чтения всех клиентов из файла и возврата списка клиентов.
        List<Client> clients = new ArrayList<>();
        File file = new File(filename);
//Создание нового списка clients для хранения объектов Client и создание объекта File для работы с файлом с указанным именем.
        if (!file.exists() || file.length() == 0) {
            System.out.println("Файл не существует или пуст: " + filename);
            return clients;
        }
//Проверка существования файла и его размера. Если файл не существует или пуст, выводится сообщение, и возвращается пустой список клиентов.
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//Открытие BufferedReader для чтения файла. Блок try-with-resources гарантирует, что reader будет закрыт автоматически после завершения работы.
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
//Чтение содержимого файла построчно и добавление его в StringBuilder для дальнейшей обработки.
            System.out.println("Содержимое файла: " + sb.toString());
//Вывод содержимого файла в консоль для отладки.
            JSONArray data = new JSONArray(sb.toString());
//Преобразование содержимого файла в объект JSONArray, который представляет собой массив JSON-объектов.
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                try {
                    clients.add(Client.fromJson(jsonObject));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//Перебор каждого JSON-объекта в массиве и создание экземпляра Client с помощью метода fromJson. Каждый созданный клиент добавляется в список clients. Если происходит ошибка при создании клиента, она обрабатывается и выводится в консоль.
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
//Обработка исключений:
//FileNotFoundException — если файл не найден.
//IOException — для ошибок ввода-вывода при чтении файла.
        return clients;
    }
//Возврат списка клиентов, считанных из файла.
    public void saveAll(List<Client> clients) {
//Метод для сохранения всех клиентов в файл.
        JSONArray jsonArray = new JSONArray();
        for (Client client : clients) {
            jsonArray.put(client.toJson());
        }
//Создание нового объекта JSONArray и добавление в него каждого клиента, преобразованного в JSON-объект с помощью метода toJson.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
//Открытие BufferedWriter для записи в файл. Записываем содержимое jsonArray в файл с отступом в 4 пробела для улучшения читаемости. Если происходит ошибка при записи, она обрабатывается и выводится в консоль.
    }
}
//Закрытие класса и метода.

}
