package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyDetail

val previewVacancy = VacancyDetail(
    id = "00d1a097-4f31-3e91-bc13-6287faa6f99c",
    name = "iOS-разработчик",
    salary = Salary(
        currency = "RUB",
        from = 50_000,
        to = null,
    ),
    address = Address(
        id = "2",
        city = "Новосибирск",
        street = "Красный проспект",
        building = "3",
        raw = "Новосибирск, Красный проспект, 3",
    ),
    experience = "От 1 года до 3 лет",
    schedule = "Удаленная работа",
    employment = "Полная занятость",
    contacts = Contacts(
        name = "Сидоров Сидор Сидорович",
        email = "",
        phones = listOf(
            Phone(
                comment = null,
                formatted = "+7 (999) 345-67-89",
            ),
            Phone(
                comment = null,
                formatted = "+7 (999) 765-43-21",
            ),
        ),
    ),
    description = """
        <h2>Описание вакансии</h2>
        <p>Команда мобильной платформы ищет iOS-разработчика для развития клиентского приложения.</p>
        <p>Роль подойдёт инженеру, который спокойно работает с архитектурой, качеством кода и релизным циклом.</p>
        <section>
            <h3>Обязанности</h3>
            <ul>
                <li>Разрабатывать новые модули приложения и поддерживать существующие экраны.</li>
                <li>Интегрировать REST API и участвовать в проработке контрактов.</li>
                <li>Писать тесты и участвовать в анализе производительности приложения.</li>
            </ul>
        </section>
        <section>
            <h3>Требования</h3>
            <ul>
                <li>Хорошее знание Swift и UIKit или SwiftUI.</li>
                <li>Понимание жизненного цикла iOS-приложений и подходов к построению архитектуры.</li>
                <li>Опыт работы с Git и сборкой приложений через CI.</li>
            </ul>
        </section>
        <section>
            <h3>Условия</h3>
            <ul>
                <li>Гибкий график и сильная инженерная команда.</li>
                <li>Выстроенные процессы релизов, code review и продуктовых исследований.</li>
                <li>Компенсация обучения и расширенный ДМС.</li>
            </ul>
        </section>
    """.trimIndent(),
    employer = Employer(
        name = "Google",
        logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/" +
            "Google_2015_logo.svg/500px-Google_2015_logo.svg.png",
    ),
    area = FilterArea(
        id = "4",
        parentId = "1202",
        name = "Новосибирск",
        areas = emptyList(),
    ),
    skills = listOf(
        "Swift",
        "UIKit",
        "SwiftUI",
        "REST API",
        "CI/CD",
    ),
    url = "https://example.com/vacancies/" +
        "00d1a097-4f31-3e91-bc13-6287faa6f99c",
    industry = FilterIndustry(
        id = 7,
        name = "Информационные технологии, системная интеграция, интернет",
    ),
)
