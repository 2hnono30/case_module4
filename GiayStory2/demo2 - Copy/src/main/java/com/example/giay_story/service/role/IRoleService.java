package com.example.giay_story.service.role;

import com.example.giay_story.model.Role;
import com.example.giay_story.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
