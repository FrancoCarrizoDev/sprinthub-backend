package com.sprinthub.sprinthub.repositories;

import com.sprinthub.sprinthub.models.PersonModel;
import org.springframework.data.repository.ListCrudRepository;

public interface PersonRepository extends ListCrudRepository<PersonModel, Long> {
}
