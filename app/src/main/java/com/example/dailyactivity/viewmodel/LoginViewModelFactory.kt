package com.example.dailyactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dailyactivity.repository.UserRepository

class LoginViewModelFactory(private val repository: UserRepository) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return  LoginViewModel(repository) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}
/*
LoginViewModelFactory sınıfı, ViewModel sınıflarının örneklerini oluşturmak için kullanılan bir fabrika sınıfıdır. Android Jetpack'in ViewModelProvider.Factory arayüzünü uygular. Bu arayüz, ViewModel'lerin oluşturulması sırasında gereken bağımlılıkları sağlamak için kullanılır.

Bu sınıfın create metodu, belirli bir ViewModel sınıfının örneğini oluşturur. Bu metot, ViewModel'ın sınıfını ve gerekli bağımlılıkları alır ve bir ViewModel örneği döndürür.

İşte create metodunun çalışma mantığı:

1-create metodu, ViewModel'ın sınıfını temsil eden modelClass parametresini alır.
2-isAssignableFrom metoduyla, belirtilen modelClass'ın LoginViewModel sınıfı ile eşleşip eşleşmediği kontrol edilir.
3-Eğer modelClass, LoginViewModel sınıfı ile eşleşiyorsa, LoginViewModel örneği oluşturulur ve bağımlılıkları (burada repository) kullanılarak ViewModel'a geçirilir.
4-create metodu, oluşturulan ViewModel örneğini, ViewModelProvider.Factory arayüzünü uygulayan bir sınıf olduğu için döndürür.
Bu şekilde, her ViewModel için ayrı bir ViewModelFactory oluşturulabilir ve bu fabrika sınıfları, ViewModel'ın gereksinim duyduğu bağımlılıkları sağlamak için kullanılabilir. Bu, ViewModel'ı test etmeyi ve değiştirmeyi kolaylaştırır, ayrıca kodunuzu daha modüler hale getirir.

 */