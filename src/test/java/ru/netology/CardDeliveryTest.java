package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {


    @BeforeEach
    public void openPage() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSendForm() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Брянск");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Белкова Екатерина");
        $("[data-test-id=phone] input").setValue("+79037621234");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(Condition.text
                ("Успешно! Встреча успешно забронирована на " + deliveryDate), Duration.ofSeconds(15));

    }

    @Test
    public void shouldNotSendCity() {
        Configuration.holdBrowserOpen = true;

        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Беккер Валентина");
        $("[data-test-id=phone] input").setValue("+79679900004");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        String expectedText = "Поле обязательно для заполнения";
        String actualText = $("[data-test-id=city] .input__sub").getText().trim();
        assertEquals(expectedText, actualText);

    }

}