package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.LoginForm;
import cn.varfunc.restaurant.domain.model.Address;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    /**
     * Get a store instance by given id.
     */
    public Store findById(long id) throws NoSuchElementException {
        Optional<Store> store = this.storeRepository.findById(id);
        return store.orElseThrow(() -> {
            throw new NoSuchElementException("No such Store!");
        });
    }

    /**
     * Get a store instance by given username
     */
    public Store findByUsername(String username) {
        return storeRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No such store! Please check your username"));
    }

    /**
     * Create a new storeRepository instance.
     */
    public Store create(@NonNull String username, @NonNull String password, @NonNull String name,
                        String phoneNumber, String announcement, Address address, String workingGroup, String uuid) {
        Store newStore = new Store();
        newStore.setName(name)
                .setPhoneNumber(phoneNumber)
                .setAnnouncement(announcement)
                .setAddress(address)
                .setWorkingGroup(workingGroup)
                .setUsername(username)
                .setPassword(password)
                .setImageUUID(UUID.fromString(uuid));
        return storeRepository.save(newStore);
    }

    /**
     * Modify specified storeRepository's information.
     *
     * @param id the id of storeRepository whose information need to be modified
     */
    public Store modifyInformation(long id, String name, String announcement, String workingGroup,
                                   String phoneNumber, Address address) {
        Store pre = findById(id);

        if (nonNull(name) && !Objects.equals(name, pre.getName())) {
            pre.setName(name);
        }

        if (nonNull(announcement)
                && !Objects.equals(announcement, pre.getAnnouncement())) {
            pre.setAnnouncement(announcement);
        }

        if (nonNull(phoneNumber)
                && !Objects.equals(phoneNumber, pre.getWorkingGroup())) {
            pre.setWorkingGroup(phoneNumber);
        }

        if (nonNull(phoneNumber) &&
                !Objects.equals(phoneNumber, pre.getPhoneNumber())) {
            pre.setPhoneNumber(phoneNumber);
        }

        if (nonNull(address) &&
                !Objects.equals(address, pre.getAddress())) {
            pre.setAddress(address);
        }
        // Save changes to database
        return storeRepository.save(pre);
    }

    /**
     * Validate login
     */
    public boolean validateUsernameAndPassword(LoginForm form) {
        final String username = form.getUsername();
        final String password = form.getPassword();

        if (Objects.nonNull(username) && !username.isBlank()) {
            if (Objects.nonNull(password) && !password.isBlank()) {
                Store store = storeRepository.findByUsername(username)
                        .orElseThrow(() -> new NoSuchElementException("No such store! Please check the username"));
                return Objects.equals(store.getPassword(), password);
            }
        }
        return false;
    }
}
