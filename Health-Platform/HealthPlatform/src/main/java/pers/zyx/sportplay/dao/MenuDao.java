package pers.zyx.sportplay.dao;

import org.springframework.stereotype.Repository;
import pers.zyx.sportplay.bean.MainMenu;

import java.util.List;

@Repository
public interface MenuDao {
    public List<MainMenu> getMenus();
}
