package ru.schedule;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {

    private final String homeTitle = "Главная страница";
    private final String studentsTitle = "Студенты";
    private final String professorsTitle = "Профессора";
    private final String classroomsTitle = "Аудитории";
    private final String coursesTitle = "Курсы";

    @Test
    void MainTest() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--remote-allow-origins=*");

        ChromeDriver driver = new ChromeDriver(opt);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1845, 1200));
        driver.get("http://localhost:8080/");

        WebElement Button = driver.findElement(By.id("students"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(studentsTitle, driver.getTitle());

        driver.get("http://localhost:8080/");

        Button = driver.findElement(By.id("professors"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(professorsTitle, driver.getTitle());

        driver.get("http://localhost:8080/");

        Button = driver.findElement(By.id("classrooms"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(classroomsTitle, driver.getTitle());

        driver.get("http://localhost:8080/");

        Button = driver.findElement(By.id("courses"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(coursesTitle, driver.getTitle());

        driver.quit();
    }
    @Test
    void HeaderTest() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--remote-allow-origins=*");

        ChromeDriver driver = new ChromeDriver(opt);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1845, 1200));
        driver.get("http://localhost:8080/");

        WebElement homeButton = driver.findElement(By.id("Home"));
        homeButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(homeTitle, driver.getTitle());

        driver.quit();
    }

    @Test
    void StudentsTest() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--remote-allow-origins=*");

        ChromeDriver driver = new ChromeDriver(opt);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1845, 1200));
        driver.get("http://localhost:8080/students");

        WebElement Button = driver.findElement(By.id("add"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Добавление студента", driver.getTitle());

        driver.findElement(By.id("first_name")).sendKeys("Тестовое имя");
        driver.findElement(By.id("lastName")).sendKeys("Тестовая фамилия");
        driver.findElement(By.id("patronymic")).sendKeys("Тестовое отчество");
        Select slcr = new Select(driver.findElement(By.id("year")));
        slcr.selectByVisibleText("1");
        slcr = new Select(driver.findElement(By.id("stream")));
        slcr.selectByVisibleText("1");
        driver.findElement(By.id("group")).sendKeys("100");
        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("Студенты" ,driver.getTitle());

        Button = driver.findElement(By.id("find"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Поиск студента", driver.getTitle());

        driver.findElement(By.id("first_name")).sendKeys("Тестовое имя");
        driver.findElement(By.id("lastName")).sendKeys("Тестовая фамилия");
        driver.findElement(By.id("patronymic")).sendKeys("Тестовое отчество");
        slcr = new Select(driver.findElement(By.id("year")));
        slcr.selectByVisibleText("1");
        slcr = new Select(driver.findElement(By.id("stream")));
        slcr.selectByVisibleText("1");
        driver.findElement(By.id("group")).sendKeys("101");
        Button = driver.findElement(By.id("find"));
        Button.click();
        assertEquals("error2" ,driver.getTitle());
        driver.navigate().back();
        driver.findElement(By.id("group")).clear();
        driver.findElement(By.id("group")).sendKeys("100");
        Button = driver.findElement(By.id("find"));
        Button.click();
        assertEquals("Студент" ,driver.getTitle());

        Button = driver.findElement(By.id("update"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Редактирование студента", driver.getTitle());

        driver.findElement(By.id("group")).clear();
        driver.findElement(By.id("group")).sendKeys("101");
        Button = driver.findElement(By.id("update"));
        Button.click();
        assertEquals("Студент" ,driver.getTitle());

        driver.findElement(By.id("start")).sendKeys("1");
        driver.findElement(By.id("end")).sendKeys("5");
        Button = driver.findElement(By.id("schedule"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Расписание", driver.getTitle());
        driver.navigate().back();

        driver.findElement(By.id("special")).sendKeys("Функан");
        Button = driver.findElement(By.id("add_special"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("error1", driver.getTitle());
        driver.navigate().back();

        driver.findElement(By.id("special")).clear();
        driver.findElement(By.id("special")).sendKeys("linal");
        Button = driver.findElement(By.id("add_special"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Студенты", driver.getTitle());
        driver.navigate().back();

        Button = driver.findElement(By.id("remove"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Студенты", driver.getTitle());

        driver.quit();
    }

    @Test
    void ProfessorsTest() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--remote-allow-origins=*");

        ChromeDriver driver = new ChromeDriver(opt);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1845, 1200));
        driver.get("http://localhost:8080/professors");

        WebElement Button = driver.findElement(By.id("add"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Добавление профессора", driver.getTitle());

        driver.findElement(By.id("first_name")).sendKeys("Тестовое имя");
        driver.findElement(By.id("lastName")).sendKeys("Тестовая фамилия");
        driver.findElement(By.id("patronymic")).sendKeys("Тестовое отчество");
        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("Профессора" ,driver.getTitle());

        Button = driver.findElement(By.id("find"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Поиск профессора", driver.getTitle());

        driver.findElement(By.id("first_name")).sendKeys("Тестовое имя");
        driver.findElement(By.id("lastName")).sendKeys("Тестовая фамилия");
        driver.findElement(By.id("patronymic")).sendKeys("Тестовое отчество1");
        Button = driver.findElement(By.id("find"));
        Button.click();
        assertEquals("error3" ,driver.getTitle());
        driver.navigate().back();
        driver.findElement(By.id("patronymic")).clear();
        driver.findElement(By.id("patronymic")).sendKeys("Тестовое отчество");
        Button = driver.findElement(By.id("find"));
        Button.click();
        assertEquals("Профессор" ,driver.getTitle());

        Button = driver.findElement(By.id("update"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Редактирование профессора", driver.getTitle());

        driver.findElement(By.id("patronymic")).clear();
        driver.findElement(By.id("patronymic")).sendKeys("Тестовое отчество1");
        Button = driver.findElement(By.id("update"));
        Button.click();
        assertEquals("Профессор" ,driver.getTitle());

        driver.findElement(By.id("start")).sendKeys("1");
        driver.findElement(By.id("end")).sendKeys("5");
        Button = driver.findElement(By.id("schedule"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Расписание", driver.getTitle());
        driver.navigate().back();

        Button = driver.findElement(By.id("remove"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Профессора", driver.getTitle());

        driver.quit();
    }

    @Test
    void ClassroomsTest() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--remote-allow-origins=*");

        ChromeDriver driver = new ChromeDriver(opt);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1845, 1200));
        driver.get("http://localhost:8080/classrooms");

        driver.findElement(By.id("startF")).sendKeys("1");
        driver.findElement(By.id("endF")).sendKeys("5");
        WebElement Button = driver.findElement(By.id("findF"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Свободные аудитории", driver.getTitle());
        driver.navigate().back();

        driver.findElement(By.id("id")).sendKeys("666");
        driver.findElement(By.id("start")).sendKeys("1");
        driver.findElement(By.id("end")).sendKeys("5");
        Button = driver.findElement(By.id("schedule"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Расписание", driver.getTitle());

        driver.quit();
    }

    @Test
    void ClassesTest() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--remote-allow-origins=*");

        ChromeDriver driver = new ChromeDriver(opt);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1845, 1200));
        driver.get("http://localhost:8080/courses");

        WebElement Button = driver.findElement(By.id("add"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Добавление курса", driver.getTitle());

        driver.findElement(By.id("name")).sendKeys("Тестовый курс");
        Select slcr = new Select(driver.findElement(By.id("coverage")));
        slcr.selectByVisibleText("stream");
        driver.findElement(By.id("intensity")).sendKeys("2");
        slcr = new Select(driver.findElement(By.id("year")));
        slcr.selectByVisibleText("1");
        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("Курсы" ,driver.getTitle());

        Button = driver.findElement(By.id("find"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Поиск курса", driver.getTitle());

        driver.findElement(By.id("name")).sendKeys("Тестовый курс");
        slcr = new Select(driver.findElement(By.id("coverage")));
        slcr.selectByVisibleText("stream");
        driver.findElement(By.id("intensity")).sendKeys("1");
        slcr = new Select(driver.findElement(By.id("year")));
        slcr.selectByVisibleText("1");
        Button = driver.findElement(By.id("find"));
        Button.click();
        assertEquals("error4" ,driver.getTitle());
        driver.navigate().back();
        driver.findElement(By.id("intensity")).clear();
        driver.findElement(By.id("intensity")).sendKeys("2");
        Button = driver.findElement(By.id("find"));
        Button.click();
        assertEquals("Курс" ,driver.getTitle());

        Button = driver.findElement(By.id("update"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Редактирование курса", driver.getTitle());

        driver.findElement(By.id("intensity")).clear();
        driver.findElement(By.id("intensity")).sendKeys("1");
        Button = driver.findElement(By.id("update"));
        Button.click();
        assertEquals("Курс" ,driver.getTitle());

        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("Добавление занятия" ,driver.getTitle());

        driver.findElement(By.id("classroom")).sendKeys("105");
        slcr = new Select(driver.findElement(By.id("time")));
        slcr.selectByVisibleText("08:45:00");
        slcr = new Select(driver.findElement(By.id("day_of_week")));
        slcr.selectByVisibleText("6");
        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("error5" ,driver.getTitle());
        driver.navigate().back();
        driver.findElement(By.id("classroom")).clear();
        driver.findElement(By.id("classroom")).sendKeys("666");
        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("error8" ,driver.getTitle());
        driver.navigate().back();
        driver.findElement(By.id("classroom")).clear();
        driver.findElement(By.id("classroom")).sendKeys("106");
        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("Курс" ,driver.getTitle());

        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("Добавление занятия" ,driver.getTitle());

        driver.findElement(By.id("classroom")).sendKeys("106");
        slcr = new Select(driver.findElement(By.id("time")));
        slcr.selectByVisibleText("08:45:00");
        slcr = new Select(driver.findElement(By.id("day_of_week")));
        slcr.selectByVisibleText("6");
        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("error6" ,driver.getTitle());
        driver.navigate().back();

        slcr = new Select(driver.findElement(By.id("day_of_week")));
        slcr.selectByVisibleText("1");
        Button = driver.findElement(By.id("add"));
        Button.click();
        assertEquals("error9" ,driver.getTitle());
        driver.navigate().back();

        driver.navigate().back();

        Button = driver.findElement(By.id("del"));
        Button.click();
        assertEquals("Удаление занятия" ,driver.getTitle());

        driver.findElement(By.id("classroom")).sendKeys("105");
        slcr = new Select(driver.findElement(By.id("time")));
        slcr.selectByVisibleText("08:45:00");
        slcr = new Select(driver.findElement(By.id("day_of_week")));
        slcr.selectByVisibleText("5");
        Button = driver.findElement(By.id("del"));
        Button.click();
        assertEquals("error5" ,driver.getTitle());
        driver.navigate().back();
        driver.findElement(By.id("classroom")).clear();
        driver.findElement(By.id("classroom")).sendKeys("106");
        Button = driver.findElement(By.id("del"));
        Button.click();
        assertEquals("error7" ,driver.getTitle());
        driver.navigate().back();
        slcr = new Select(driver.findElement(By.id("day_of_week")));
        slcr.selectByVisibleText("6");
        Button = driver.findElement(By.id("del"));
        Button.click();
        assertEquals("Курс" ,driver.getTitle());

        Button = driver.findElement(By.id("remove"));
        Button.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals("Курсы", driver.getTitle());

        driver.quit();
    }
}
