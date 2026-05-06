from django.urls import path
from . import views


urlpatterns = [
    path('', views.index, name='index'),
    path('save-today/', views.save_today_rates, name='save_today_rates'),
    path('saved/', views.saved_rates, name='saved_rates'),
]