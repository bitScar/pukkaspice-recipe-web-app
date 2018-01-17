package com.pukkaspice.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pukkaspice.web.common.model.recipe.RecipeSummary;
import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.common.security.SecurityController;
import com.pukkaspice.web.common.security.UserRole;
import com.pukkaspice.web.dao.mybatis.mappers.SecurityMapper;

@Controller
public class SecurityService {
    
    @Autowired
    private SecurityController securityController;
    
    @Autowired
    private SecurityMapper securityMapper;
    
    
    public boolean canSave(RecipeSummary recipeSummary) {
        return hasRecipeRights(recipeSummary.getRecipeId());
    }

    public boolean canDeleteRecipe(int recipeId) {
        return hasRecipeRights(recipeId);
    }
    
    private boolean hasRecipeRights(int recipeId) {
        UserSummary summary = securityController.getCurrentUser();
        
        if (securityController.hasRole(UserRole.ROLE_ADMIN) == true) {
            return true;
        } else {
            return securityMapper.isUserRecord("recipe", "recipeId", recipeId, summary.getUserId());
        }
    }
    
    public boolean canSave(UserSummary userSummary) {
        UserSummary summary = securityController.getCurrentUser();
        
        if (securityController.hasRole(UserRole.ROLE_ADMIN) == true) {
            return true;
        } else {
            return securityMapper.isSameUserRecord(summary.getUserId());
        }
    }

}
