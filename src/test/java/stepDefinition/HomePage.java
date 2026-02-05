package stepDefinition;


import io.cucumber.java.en.Given;
import io.qameta.allure.Allure;

import static pages.HomePage.*;

public class HomePage {

    @Given("Користувач переходить на сторінку авторизації")
    public void user_navigates_to_the_login_page() throws InterruptedException {
        Allure.feature("Given");
        Allure.addAttachment("HTML Snapshot","text/html", driver.getPageSource(), "html");
        click_hamburger_menu();
        click_signIn_link();

    }

    @Given("Користувач відкриває сторінку продуктів")
    public void user_navigates_to_the_Online_product_page() throws InterruptedException {
        Allure.getLifecycle().getCurrentTestCase();
        click_hamburger_menu();
        click_OnlineProducts_link();

    }

}
