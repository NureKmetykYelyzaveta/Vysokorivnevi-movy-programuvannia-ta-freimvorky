from django import forms
from .models import CurrencyRate


class CurrencyRateForm(forms.ModelForm):
    class Meta:
        model = CurrencyRate
        fields = ['name', 'buy_rate', 'sell_rate']

        labels = {
            'name': 'Назва валюти',
            'buy_rate': 'Курс купівлі',
            'sell_rate': 'Курс продажу',
        }

        widgets = {
            'name': forms.TextInput(attrs={
                'class': 'form-control',
                'placeholder': 'Наприклад: USD'
            }),
            'buy_rate': forms.NumberInput(attrs={
                'class': 'form-control',
                'placeholder': 'Наприклад: 39.50',
                'step': '0.01'
            }),
            'sell_rate': forms.NumberInput(attrs={
                'class': 'form-control',
                'placeholder': 'Наприклад: 40.10',
                'step': '0.01'
            }),
        }