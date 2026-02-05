package stepDefinition;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static pages.HomePage.click_hamburger_menu;
import static pages.HomePage.click_signIn_link;
import static pages.LoginPage.*;

public class LoginPage {

    @When("Користувач успішно вводить дані для входу в особистий кабінет")
    public void user_successfully_enters_the_log_in_details() throws InterruptedException {
        sendkeys_username();
        sendkeys_password();
        click_login_btn();
    }

    @When("Користувач натискає на нову кнопку реєстрації")
    public void user_clicks_on_new_registration_button() throws InterruptedException {
        click_NewRegister_btn();
    }

}
