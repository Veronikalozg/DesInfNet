class Client {
    private Integer id;
    private String name;
    private String surname;
    private String patronymic;
    private Integer total_survices;
    private String phone;
    private String email;
    private String gender;

    public Client(Integer id, String name, String surname, String patronymic, Integer total_survices, String phone, String email, String gender) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setPatronymic(patronymic);
        this.setSurvices(total_survices);
        this.setPhone(phone);
        this.setEmail(email);
        this.setGender(gender);
    }

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
            if (data[4].trim().matches("\\d+")) {
                this.setServices(Integer.parseInt(data[4].trim()));
                this.setPhone(data[5].trim());
                this.setEmail(data[6].trim());
                this.setGender(data[7].trim());
            } else {
                this.setPhone(data[4].trim());
                this.setEmail(data[5].trim());
                this.setGender(data[6].trim());
            }
        } else {
            this.setSurname(data[0].trim());
            this.setName(data[1].trim());
            this.setPatronymic(data[2].trim());
            if (data[3].trim().matches("\\d+")) {
                this.setServices(Integer.parseInt(data[3].trim()));
                this.setPhone(data[4].trim());
                this.setEmail(data[5].trim());
                this.setGender(data[6].trim());
            } else {
                this.setPhone(data[3].trim());
                this.setEmail(data[4].trim());
                this.setGender(data[5].trim());
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        if (id == null || validateI(id)==true)
            this.id = id;
        else
            throw new IllegalArgumentException("ID должен быть положительным числом.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (validateS(name)==true)
            this.name = name;
        else
            throw new IllegalArgumentException("Неверный формат имени.");
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (validateS(surname)==true)
            this.surname = surname;
        else
            throw new IllegalArgumentException("Неверный формат фамилии.");
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        if (validateS(patronymic)==true)
            this.patronymic = patronymic;
        else
            throw new IllegalArgumentException("Неверный формат отчества.");
    }

    public Integer getSurvices() {
        return total_survices;
    }

    public void setSurvices(int total_survices) {
        if (total_services==null || validateI(total_services)==true)
            this.total_services = total_services;
        else
            throw new IllegalArgumentException("Количество услуг не может быть отрицательным.");
    }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    if (validatePhone(phone)==true)
            this.phone = phone;
        else
            throw new IllegalArgumentException("Неверный формат телефона.");
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    if (validateEmail(email)==true)
            this.email = email;
        else
            throw new IllegalArgumentException("Неверный формат почты.");
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    if (validateS(gender)==true)
            this.gender = gender;
        else
            throw new IllegalArgumentException("Пол не может быть пустым.");
  }

    public static boolean validateS(String value) {
        if (value == null || value.trim().isEmpty() || !value.matches("^[A-Za-zА-Яа-яЁё\\s]+$"))
            return false;
        else
            return true;
    }

    public static boolean validateI(int val) {
        if (val < 0)
            return false;
        else
            return true;
    }

    public static boolean validatePhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }

        int digitCount = 0;
        for (char c : phone.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
            else if (c != ' ' && c != '(' && c != ')' && c != '-' && c != '+') {
                return false;
            }
        }
        return digitCount >= 7 && digitCount <= 15;
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
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", total_services='" + total_survices + '\'' +
                '}';
    }
}
