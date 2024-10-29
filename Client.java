class Client extends ClientShort {
    private String patronymic;
    private String gender;
    private String email;


    public Client(String surname, String name, String patronymic, String phone, String email, String gender) {
        super(surname,name,phone);
        this.setPatronymic(patronymic);
        this.setEmail(email);
        this.setGender(gender);
    }

    

    public Client(Integer id, String surname, String name, String patronymic,  String phone, String email, String gender) {
        this(surname,name, patronymic, phone, email, gender);
        this.setId(id);
    }

    // Перегруженный конструктор, принимающий строку
    public Client(String clientData) {
        // Ожидаемый формат строки: "id,surname,name,patronymic,total_services,phone,email,gender"
        String[] data = clientData.split(",");

        if (data.length < 6) {
            throw new IllegalArgumentException("Неверный формат данных. Ожидается минимум 6 значений.");
        }

        if (data[0].trim().matches("\\d+")) {
            this.setId(Integer.parseInt(data[0].trim()));
            this.setSurname(data[1].trim());
            this.setName(data[2].trim());
            this.setPatronymic(data[3].trim());
           
                this.setPhone(data[4].trim());
                this.setEmail(data[5].trim());
                this.setGender(data[6].trim());
            }
        } else {
            this.setSurname(data[0].trim());
            this.setName(data[1].trim());
            this.setPatronymic(data[2].trim());
           
                this.setPhone(data[3].trim());
                this.setEmail(data[4].trim());
                this.setGender(data[5].trim());
            }
        }
    }


    // Геттеры
    public String getPatronymic() {
        return patronymic;
    }

  
    public String getEmail() {return email;}
    public String getGender() {return gender;}

    // Сеттеры
    public void setPatronymic(String patronymic) {
        if (validateS(patronymic)==true)
            this.patronymic = patronymic;
        else
            throw new IllegalArgumentException("Неверный формат отчества.");
    }



    public void setEmail(String email) {
        if (validateEmail(email)==true)
            this.email = email;
        else
            throw new IllegalArgumentException("Неверный формат почты.");
    }

    public void setGender(String gender) {
        if (validateS(gender)==true)
            this.gender = gender;
        else
            throw new IllegalArgumentException("Пол не может быть пустым.");
    }

    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex != email.lastIndexOf('@')) {
            return false;
        }
        String domain = email.substring(atIndex + 1);
        if (domain.isEmpty() || !domain.contains(".")) {
            return false;
        }

        String topLevelDomain = domain.substring(domain.lastIndexOf('.') + 1);
        if (topLevelDomain.length() < 2) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Client {" +
                "id=" + getId() +
                ", surname='" + getSurname() + '\'' +
                ", name='" + getName() + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }

    public String toShortString() {
        return "Client {" +
                "surname = '" + getSurname() + '\'' +
                ", name = '" + getName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;  
        Client client = (Client) o;
        return getPhone() == client.getPhone();
    }    
}
