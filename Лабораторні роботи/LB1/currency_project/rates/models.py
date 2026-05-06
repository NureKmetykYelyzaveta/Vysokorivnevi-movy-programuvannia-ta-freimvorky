from django.db import models
from django.utils import timezone


class CurrencyRate(models.Model):
    name = models.CharField(
        max_length=100,
        verbose_name="Назва валюти"
    )

    buy_rate = models.DecimalField(
        max_digits=10,
        decimal_places=2,
        verbose_name="Курс купівлі"
    )

    sell_rate = models.DecimalField(
        max_digits=10,
        decimal_places=2,
        verbose_name="Курс продажу"
    )

    created_at = models.DateTimeField(
        auto_now_add=True,
        verbose_name="Дата додавання"
    )

    def __str__(self):
        return self.name

    class Meta:
        verbose_name = "Курс валюти"
        verbose_name_plural = "Курси валют"


class SavedDailyRate(models.Model):
    name = models.CharField(
        max_length=100,
        verbose_name="Назва валюти"
    )

    buy_rate = models.DecimalField(
        max_digits=10,
        decimal_places=2,
        verbose_name="Курс купівлі"
    )

    sell_rate = models.DecimalField(
        max_digits=10,
        decimal_places=2,
        verbose_name="Курс продажу"
    )

    saved_date = models.DateField(
        default=timezone.now,
        verbose_name="Дата збереження"
    )

    def __str__(self):
        return f"{self.name} — {self.saved_date}"

    class Meta:
        verbose_name = "Збережений курс дня"
        verbose_name_plural = "Збережені курси дня"