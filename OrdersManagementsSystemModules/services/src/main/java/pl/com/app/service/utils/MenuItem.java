package pl.com.app.service.utils;


import pl.com.app.exceptions.MyException;

import java.util.List;
import java.util.stream.Collectors;


public class MenuItem {
    private String code;
    private String name;

    public static String showMenu(List<MenuItem> menu){
        if (menu == null){
            throw new MyException("MENU IS NULL");
        }

        return menu
                .stream()
                .map(x -> String.join(" - ", x.getCode(), x.getName()))
                .collect(Collectors.joining("\n"));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static MenuItemBuilder builder() {
        return new MenuItemBuilder();
    }

    public static final class MenuItemBuilder {
        private String code;
        private String name;

        public MenuItemBuilder code(String code) {
            this.code = code;
            return this;
        }

        public MenuItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MenuItem build() {
            MenuItem menuItem = new MenuItem();
            menuItem.setCode(code);
            menuItem.setName(name);
            return menuItem;
        }
    }
}
