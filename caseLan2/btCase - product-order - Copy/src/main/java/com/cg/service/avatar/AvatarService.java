package com.cg.service.avatar;

import com.cg.model.Avatar;
import com.cg.service.IGeneralService;

public interface AvatarService extends IGeneralService<Avatar> {
    Avatar getById();
}
