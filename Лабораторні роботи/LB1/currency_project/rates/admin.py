from django.contrib import admin
from .models import CurrencyRate, SavedDailyRate


@admin.register(CurrencyRate)
class CurrencyRateAdmin(admin.ModelAdmin):
    list_display = ('name', 'buy_rate', 'sell_rate', 'created_at')
    search_fields = ('name',)


@admin.register(SavedDailyRate)
class SavedDailyRateAdmin(admin.ModelAdmin):
    list_display = ('name', 'buy_rate', 'sell_rate', 'saved_date')
    search_fields = ('name',)
    list_filter = ('saved_date',)