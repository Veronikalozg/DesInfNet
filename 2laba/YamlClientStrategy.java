class YamlClientStrategy implements ClientStrategy {
//Определение класса YamlClientStrategy, который реализует интерфейс ClientStrategy. Этот класс будет использоваться для чтения и записи данных клиентов в формате YAML.
    private String filename;
//Приватное поле filename для хранения имени файла, в котором будут храниться данные клиентов.
    public YamlClientStrategy(String filename) {
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
            Yaml yaml = new Yaml();
            List<Map<String, Object>> data = yaml.load(reader);
//Создание объекта Yaml для работы с YAML-форматом и загрузка данных из файла в список data, представляющий собой список карт (словарей), где ключи - это строки, а значения - объекты.
            for (Map<String, Object> map : data) {
                try {
                    clients.add(Client.fromYaml(map));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//Перебор каждой карты в списке data и создание экземпляра Client с помощью метода fromYaml, передавая карту как аргумент. Каждый созданный клиент добавляется в список clients. Если происходит ошибка при создании клиента, она обрабатывается и выводится в консоль.
        } catch (IOException e) {
            e.printStackTrace();
        }
//Обработка исключений IOException, которые могут возникнуть при чтении файла.
        for (Client client : clients) {
            System.out.println(client.toYaml());
        }
//Вывод информации о каждом клиенте в формате YAML для отладки.
        return clients;
    }
//Возврат списка клиентов, считанных из файла.
    public void saveAll(List<Client> clients) {
//Метод для сохранения всех клиентов в файл.
        DumperOptions options = new DumperOptions();
        options.setIndent(4);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
//Создание объекта DumperOptions для настройки параметров вывода YAML. Устанавливаются отступы, стиль потока и включение "красивого" вывода.
        Representer representer = new Representer(options);
        representer.addClassTag(Client.class, Tag.MAP);
//Создание объекта Representer, который будет использоваться для преобразования объектов в YAML, и добавление тега класса Client для отображения его как карты.
        Yaml yaml = new Yaml(representer, options);
//Создание объекта Yaml, который будет использоваться для записи данных в YAML с заданными параметрами и представителем.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            yaml.dump(clients, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
//Открытие BufferedWriter для записи в файл. Метод dump используется для записи списка клиентов в файл в формате YAML. Если происходит ошибка при записи, она обрабатывается и выводится в консоль.
        }
    }
}
