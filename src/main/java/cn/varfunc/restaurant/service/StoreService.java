package cn.varfunc.restaurant.service;

import cn.varfunc.restaurant.domain.form.StoreForm;
import cn.varfunc.restaurant.domain.model.Store;
import cn.varfunc.restaurant.domain.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class StoreService {
    private final StoreRepository store;

    @Autowired
    public StoreService(StoreRepository store) {
        this.store = store;
    }

    /**
     * Get a store instance by given id.
     */
    public Store findById(long id) throws NoSuchElementException {
        log.info("Method: findById(), id: {}", id);
        Optional<Store> store = this.store.findById(id);
        return store.orElseThrow(() -> {
            throw new NoSuchElementException("No such Store!");
        });
    }

    /**
     * Create a new store instance.
     *
     * @param form information needed for creating a new store, <code>name</code> field
     *             can not be <code>null</code> or blank.
     */
    public Store createStore(StoreForm form) {
        log.info("Method: createStore(), form: {}", form);
        Store newStore = new Store();
        newStore.setName(form.getName())
                .setPhoneNumber(form.getPhoneNumber())
                .setAnnouncement(form.getAnnouncement())
                .setAddress(form.getAddress())
                .setWorkingGroup(form.getWorkingGroup());
        return store.save(newStore);
    }

    /**
     * Modify specified store's information.
     *
     * @param id   the id of store whose information need to be modified
     * @param form the information to be updated
     */
    public Store modifyInformation(long id, StoreForm form) {
        log.info("Method: modifyInformation(), id: {}, form: {}", id, form);
        Store pre = store.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such store!"));

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
        return store.save(pre);
    }

    /**
     * Return all instances of Store.
     */
    public List<Store> findAll() {
        log.info("Method: findAll()");
        return store.findAll();
    }

    /**
     * Return the number of stores.
     */
    public long numberOfStores() {
        log.info("Method: numberOfStores()");
        return store.count();
    }
}
