package com.cg.service.avatar;

import com.cg.model.Avatar;
import com.cg.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AvatarServiceImpl implements AvatarService{

    @Autowired
    private AvatarRepository avatarRepository;
    @Override
    public List<Avatar> findAll() {
        return null;
    }

    @Override
    public Avatar getById(Long id) {
        return null;
    }

    @Override
    public Optional<Avatar> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Avatar save(Avatar avatar) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }


    @Override
    public Avatar getById(String id) {
        return avatarRepository.getById(id);
    }
}
