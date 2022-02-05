package com.application.remote.rlul.data.repository;

import com.application.remote.rlul.data.entity.RLULPojo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateLockStatusRepository extends CrudRepository<RLULPojo, String> {


}


