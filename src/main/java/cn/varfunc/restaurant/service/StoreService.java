package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.LoginForm;
import cn.varfunc.restaurant.domain.form.StoreForm;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    /**
     * Get a storeRepository instance by given id.
     */
    public Store findById(long id) throws NoSuchElementException {
        Optional<Store> store = this.storeRepository.findById(id);
        return store.orElseThrow(() -> {
            throw new NoSuchElementException("No such Store!");
        });
    }

    /**
     * Create a new storeRepository instance.
     *
     * @param form information needed for creating a new storeRepository, <code>name</code> field
     *             can not be <code>null</code> or blank.
     */
    public Store create(StoreForm form) {
        Store newStore = new Store();
        newStore.setName(form.getName())
                .setPhoneNumber(form.getPhoneNumber())
                .setAnnouncement(form.getAnnouncement())
                .setAddress(form.getAddress())
                .setWorkingGroup(form.getWorkingGroup());
        return storeRepository.save(newStore);
    }

    /**
     * Modify specified storeRepository's information.
     *
     * @param id   the id of storeRepository whose information need to be modified
     * @param form the information to be updated
     */
    public Store modifyInformation(long id, StoreForm form) {
        Store pre = storeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such storeRepository!"));

        if (form.getName() != null && !form.getName().equals(pre.getName())) {
            pre.setName(form.getName());
        }

        if (form.getAnnouncement() != null && !form.getAnnouncement().equals(pre.getAnnouncement())) {
            pre.setAnnouncement(form.getAnnouncement());
        }

        if (form.getWorkingGroup() != null && !form.getWorkingGroup().equals(pre.getWorkingGroup())) {
            pre.setWorkingGroup(form.getWorkingGroup());
        }

        if (form.getPhoneNumber() != null && !form.getPhoneNumber().equals(pre.getPhoneNumber())) {
            pre.setPhoneNumber(form.getPhoneNumber());
        }

        if (form.getAddress() != null && !form.getAddress().equals(pre.getAddress())) {
            pre.setAddress(form.getAddress());
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
