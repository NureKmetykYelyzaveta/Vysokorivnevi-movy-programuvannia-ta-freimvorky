from django.shortcuts import render, redirect
from django.utils import timezone

from .models import CurrencyRate, SavedDailyRate
from .forms import CurrencyRateForm


def index(request):
    rates = CurrencyRate.objects.all().order_by('name')

    if request.method == 'POST':
        form = CurrencyRateForm(request.POST)

        if form.is_valid():
            form.save()
            return redirect('index')
    else:
        form = CurrencyRateForm()

    context = {
        'rates': rates,
        'form': form,
    }

    return render(request, 'rates/index.html', context)


def save_today_rates(request):
    rates = CurrencyRate.objects.all()

    today = timezone.now().date()

    for rate in rates:
        SavedDailyRate.objects.create(
            name=rate.name,
            buy_rate=rate.buy_rate,
            sell_rate=rate.sell_rate,
            saved_date=today
        )

    return redirect('saved_rates')


def saved_rates(request):
    saved = SavedDailyRate.objects.all().order_by('-saved_date', 'name')

    context = {
        'saved_rates': saved,
    }

    return render(request, 'rates/saved_rates.html', context)