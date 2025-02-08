import java.util.ArrayList;
import java.util.List;

class ClientRepositoryAdapter implements IClientRepository {
    private List<ClientObserver> observers = new ArrayList<>();
    private ClientRepository clientRepository;

    public ClientRepositoryAdapter(String filename, ClientStrategy strategy) {
        this.clientRepository = new ClientRepository(filename, strategy);
    }

    public Client getById(int clientId) {
        return  clientRepository.getById(clientId);
    }

    public List<Client> getKNSortList(int k, int n) {
        return clientRepository.getKNSortList(k, n);
    }

    public void addClient(Client client) throws Exception {
        clientRepository.addClient(client);
        clientRepository.saveAllClients();
        notifyObservers();
    }

    public boolean replaceById(int clientId, Client newClient) throws Exception {
        boolean flag = clientRepository.replaceById(clientId, newClient);
        if(flag) {
            clientRepository.saveAllClients();
            return true;
        }
        return false;
    }

    public boolean updateClient(int clientId, Client newClient) throws Exception {
        boolean flag = clientRepository.updateClient(clientId, newClient);
        if(flag) {
            clientRepository.saveAllClients();
            notifyObservers();
            return true;
        }
        return false;
    }

    public void deleteById(int clientId) {
        clientRepository.deleteById(clientId);
        clientRepository.saveAllClients();
        notifyObservers();
    }

    public int getCount() {
        return clientRepository.getCount();
    }
